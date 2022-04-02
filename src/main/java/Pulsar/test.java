package Pulsar;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 18:05 2022/3/28
 @Modified By:
 **********************************/
public class test {
    private static Logger LOGGER = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setRestartStrategy();

        PulsarSource<String> pulsarSource = PulsarSource.builder()
                .setServiceUrl("pulsar://localhost:6650")
                .setAdminUrl("http://localhost:8080")
                .setStartCursor(StartCursor.latest())
                .setTopics("test")
                .setDeserializationSchema(PulsarDeserializationSchema.flinkSchema(new SimpleStringSchema()))
                .setSubscriptionName("my-subscription")
                .setSubscriptionType(SubscriptionType.Exclusive)
                .build();

        DataStream<String> stream = env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        SingleOutputStreamOperator<String> resultStream=  AsyncDataStream.unorderedWait(stream, new AsyncRedis2("redis://localhost"), 500L, TimeUnit.MILLISECONDS)
                .setParallelism(1);
        resultStream.print();
       // stream.print();
        env.execute("qxy");
    }
}
