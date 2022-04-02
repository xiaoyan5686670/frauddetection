import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestSlot {

  private static Logger LOGGER = LoggerFactory.getLogger(TestSlot.class);

  public static void main(String[] args) throws Exception {

    StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
    //     DataStreamSource<String> inputStream = KafkaReader.getKafkaSource(env, "dfsawf",
    // "CSLog");
     env.setParallelism(1);
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "127.0.0.1:9093");
    properties.setProperty("group.id", "test");
    properties.setProperty("max.poll.interval.ms", "1000");
    DataStream<String> stream =
        env.addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));
//    KafkaSource<KafkaSimpleStringRecord> kafkaSource =
//        KafkaSource.<KafkaSimpleStringRecord>builder()
//            .setBootstrapServers(bootstrapServer)
//            .setDeserializer(new SimpleKafkaRecordDeserializationSchema())
//            .setStartingOffsets(OffsetsInitializer.latest())
//            .setTopics(topic)
//            .build();
 //   stream.print();
    SingleOutputStreamOperator<String>   resultStream=  AsyncDataStream.unorderedWait(stream, new AsyncRedis("redis://localhost"), 500L, TimeUnit.MILLISECONDS)
        .setParallelism(1);
    resultStream.print();
    env.execute("JFYINTERNAL1");
  }
}


class AsyncRedis extends RichAsyncFunction<String, String> {
    private RedisAsyncCommands<String, String> async;
    private String url;
    private StatefulRedisConnection<String, String> connection;
    private RedisClient redisClient;
    public AsyncRedis(String url) {
        this.url = url;
    }
    @Override
    public void open(Configuration parameters) throws Exception {
        redisClient = RedisClient.create(url);
        connection = redisClient.connect();
        async = connection.async();

    }
    @Override
    public void asyncInvoke(String input, final ResultFuture<String> resultFuture) throws Exception {
        // query string
        // System.out.println(redisFuture.get().toString());
        // query hash
        // async query and get result

        // return result
        CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                String imei = JSON.parseObject(input).get("imei").toString();
                                RedisFuture<String> redisFuture = async.hget("DC_IMEI_APPID",imei);
                                return redisFuture.get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "exception";
                        })
                .thenAccept(result -> {
                            if (result == null){
                                result = "nothing";
                            }
                            //     resultFuture.complete(Collections.singleton(input + "-" + result));
                            resultFuture.complete(Collections.singleton( JSON.parseObject(result).get("userId").toString()));
                        }
                );
    }


}

