package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 18:37 2022/3/17
 @Modified By:
 **********************************/
public class FutureExceptionTest {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("当前线程名称" + Thread.currentThread().getName());
                    throw new RuntimeException();
                });
        CompletableFuture<String> exceptionFuture = orgFuture.exceptionally( (e) -> {
            e.printStackTrace();
            return "你的程序异常了";
        });
        System.out.println(exceptionFuture.get());
    }
}
