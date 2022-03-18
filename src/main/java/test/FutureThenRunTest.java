package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 17:58 2022/3/17
 @Modified By:
 **********************************/
public class FutureThenRunTest {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        CompletableFuture orgFuture = CompletableFuture.supplyAsync(()->
        {
            System.out.println("先执行第一个CompletableFuture方法任务");
            return "捡田累的小男孩";

        });
        CompletableFuture thenRunFuture = orgFuture.thenRun( () -> {
            System.out.println("接着执行第二个任务");
        });
        System.out.println(thenRunFuture.get());
    }
}
