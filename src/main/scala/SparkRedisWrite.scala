import com.redislabs.provider.redis._
import org.apache.spark.sql.{DataFrame, SparkSession}
/** *******************************
 * @Author:xiaoyan.qin
 * @Description:
 * @Date:Created in 18:27 2022/3/29
 * @Modified By:
 *           ******************************** */
object SparkRedisWrite {
  case class Person(name: String, age: Int)
  def main(args: Array[String]): Unit = {
      val spark = SparkSession
        .builder()
        .appName("myApp")
        .master("local[*]")
        .config("spark.redis.host","localhost")
        .config("spark.redis.port","6379")
        .getOrCreate()
    val sc = spark.sparkContext
    import com.redislabs.provider.redis._

    val fail_start_time = System.currentTimeMillis()
    val os = System.getProperty("os.name")
    var df: DataFrame = null
    if (os == "Windows Server 2012 R2") {
      df = spark.read.json("file:///C:\\Users\\Administrator\\Desktop\\xiaoyan.json")
      // df = spark.read.json(args(1))
    } else {

      //df = spark.read.schema(schema_2).json(args(1))
      df = spark.read.json("file:///C:\\Users\\Zhaopin\\Desktop\\user.sql")
    }
//    val personSeq = Seq(Person("John", 30), Person("Peter", 45))
//    val df = spark.createDataFrame(personSeq)
    df.show()
//    df.write.format("org.apache.spark.sql.redis")
//      .option("table","person")
//      .save()

    df.write.mode("Overwrite").format("org.apache.spark.sql.redis")
      .option("table","userlogin")
      .option("key.column","uid")
      .save()

  }



  }

