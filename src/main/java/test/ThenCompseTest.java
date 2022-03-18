package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:50 2022/3/18
 @Modified By:
 **********************************/
public class ThenCompseTest {
    public static void main(String[] args){
        CompletableFuture<String> f = CompletableFuture.completedFuture("第一个任务");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> "第二个任务",executor)
                .thenComposeAsync(data -> {
                    System.out.println(data);return f;
                },executor);
        System.out.println(future.join());
        executor.shutdown();





    }
}
