package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 8:42 2022/3/18
 @Modified By:
 **********************************/
public class ThenCombineTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> first = CompletableFuture.completedFuture("第一个异步任务");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "第二个异步任务",executor)
                .thenCombineAsync(first,(s,w) -> {
                    System.out.println(w+"w");
                    System.out.println(s);
                    return "两个异步任务的组合";
                },executor);
        System.out.println(first.get()+"q");
        System.out.println(future.join());
        executor.shutdown();

    }
}
