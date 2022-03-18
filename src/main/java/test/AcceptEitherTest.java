package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:15 2022/3/18
 @Modified By:
 **********************************/
public class AcceptEitherTest {
    public static void main(String[] args){
        //第一个异步任务，休眠2秒，保证它执行晚点
        CompletableFuture<String> first = CompletableFuture.supplyAsync(() ->{
            try {
                Thread.sleep(2000L);
                System.out.println("执行完第一个异步任务");
            } catch (Exception e){
                return "第一个任务异常";
            }
           return "第一个异步任务";
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future  = CompletableFuture
                //第二个异步任务
                .supplyAsync(() -> {
                    System.out.println("执行完第二个任务");
                    return "第二个任务";
                },executor).acceptEitherAsync(first,System.out::println,executor);
        executor.shutdown();
    }
}
