import org.apache.flink.api.common.typeinfo.TypeInformation

import java.util.concurrent.TimeUnit
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.scala.{createTypeInformation, _}
import org.apache.flink.streaming.api.scala.async.{AsyncFunction, ResultFuture, RichAsyncFunction}
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows

import org.apache.flink.util.concurrent.Executors
import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}
import org.apache.flink.streaming.api.windowing.time.Time

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object WriteToHbase {
  def main(args: Array[String]): Unit = {

    val parameter = ParameterTool.fromArgs(args)
    val host = parameter.get("host")
    val port = parameter.getInt("port")
    println(host)

    //创建环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //env.setParallelism(1)
    //读取数据source
    val socketDs = env.socketTextStream(host, port)

    socketDs.print()

    val resultDS = socketDs.map(str => {
      val arrayStr = str.split(",")
      val id = arrayStr(0)
      val name = arrayStr(1)
      val age = arrayStr(2)
      (id, name, age)
    })


        val EndDS = AsyncDataStream.orderedWait(resultDS, new AsyncDatabaseRequest, 100000L, TimeUnit.MILLISECONDS)

        EndDS.print()

    env.execute("flink")
  }
  case class WordWithCount(word: String, count: Long)

  /**
   * An implementation of the 'AsyncFunction' that sends requests and sets the callback.
   */
  class AsyncDatabaseRequest extends RichAsyncFunction[(String, String, String), (String, String, String, String)] {

    /** The database specific client that can issue concurrent requests with callbacks */
    println("jedis连接")
    @transient
    private var jedis: Jedis = _
    private lazy  val pool = new JedisPool(new JedisPoolConfig,"localhost",6379)
    /** The context used for the future callbacks */
    implicit lazy val executor: ExecutionContext = ExecutionContext.fromExecutor(Executors.directExecutor())

    override def asyncInvoke(input: (String, String, String), resultFuture: ResultFuture[(String, String, String, String)]): Unit = {
      var result = ""
      val resultFutureRequested: Future[String] = Future {
        jedis = pool.getResource
        result = jedis.hget(input._1,input._2)
        result
      }
      resultFutureRequested.onComplete {
        case Success(result) =>
          resultFuture.complete(Iterable((input._1,input._2,input._3,result)))
          jedis.close()
        case Failure(error) => case e: Exception => println(s"报错了,报错信息是: ${e.getMessage}")
      }

      println("invoke")
    }

    override def close(): Unit = {
      if(jedis != null) jedis.close()
    }
  }

}
