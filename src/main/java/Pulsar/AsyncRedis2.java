package Pulsar;

import com.alibaba.fastjson.JSON;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.hadoop.hdfs.DFSClient;

import javax.naming.TimeLimitExceededException;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;


/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 10:58 2022/4/1
 @Modified By:
 **********************************/
public class AsyncRedis2 extends RichAsyncFunction<String, String> {
    private RedisAsyncCommands<String,String> async;
    private String url;
    private StatefulRedisConnection<String,String> connection;
    private RedisClient redisClient;
    public AsyncRedis2(String url) {
        this.url = url;
    }
    @Override
    public void open(Configuration parameters) throws Exception{
        redisClient = RedisClient.create(url);
        connection = redisClient.connect();
        async  = connection.async();
    }
//    @Override
//    public void asyncInvoke(String input, ResultFuture<String> resultFuture) throws Exception {
//        CompletableFuture.supplyAsync( () ->{
//            String eventId = JSON.parseObject(input).get("eventId").toString();
//            RedisFuture<String> redisFutre = async.hget("userlogin:"+eventId,"eventId");
//            try {
//                return redisFutre.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            return eventId;
//        }).thenAccept(result -> {
//            if (result == null){
//                result = "nothing";
//            }
//            resultFuture.complete(Collections.singleton(JSON.parseObject(result).get("eventId").toString()));
//        });
//
//    }


//    @Override
//    public void asyncInvoke(String input, final ResultFuture<String> resultFuture) throws Exception {
//
//        // return result
//        CompletableFuture.supplyAsync(
//                        () -> {
//                            try {
//                               // String imei = JSON.parseObject(input).get("imei").toString();
//                                String imei = JSON.parseObject(input).get("userId").toString();
//                                System.out.println("userId"+imei);
//                               // RedisFuture<String> redisFuture = async.hget("userlogin",imei);
//                                System.out.println("userlogin:"+imei);
//                              RedisFuture<String> redisFuture = async.hget("userlogin:"+imei,imei);
//                               // RedisFuture<String> redisFuture = async.hget("DC_IMEI_APPID",imei);
//                                //   RedisFuture<String> redisFuture = async.hget("DC_IMEI_APPID:1115757030",imei);
//                                return redisFuture.get(1000L, TimeUnit.SECONDS);
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                                System.out.println("asdfasdfaaaaaaaafffffffffffffffffffffffffff"+getRuntimeContext().getTaskName());
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            } catch (TimeoutException e) {
//                                System.out.println(e.getMessage() + "qxy");
//                            }
//                            return null;
//                        })
//                .thenAccept(result -> {
//                            if (result == null){
//                                result = "nothing";
//                            }
//                            //     resultFuture.complete(Collections.singleton(input + "-" + result));
//                            resultFuture.complete(Collections.singleton( JSON.parseObject(result).get("userId").toString()));
//                        }
//                );
//
//    }

    @Override
    public void asyncInvoke(String input, ResultFuture<String> resultFuture) throws Exception {

        // query string
        String imei = JSON.parseObject(input).get("userId").toString();
      //  RedisFuture<String> redisFuture = async.hget("userlogin",imei);
        RedisFuture<String> redisFuture = async.hget("userlogin:"+imei,imei);
        //  query hash
//        RedisFuture<String> redisFuture = async.hget("key", input);
        // get all
//        async.hgetall(input);

        // async query and get result
        CompletableFuture.supplyAsync(() -> {
            try {
                return redisFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            // if get exception
            return "exception";
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String result) {
                if (result == null) {
                    result = "nothing";
                }
                // return result
                resultFuture.complete(Collections.singleton( JSON.parseObject(result).get("userId").toString()));

            }
        });
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (connection != null) {
            connection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }


}
