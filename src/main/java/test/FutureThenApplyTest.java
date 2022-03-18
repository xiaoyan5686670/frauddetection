package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 18:23 2022/3/17
 @Modified By:
 **********************************/
public class FutureThenApplyTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("原始CompletableFuture方法任务");
                    return "捡田螺的小男孩";
                });

        CompletableFuture<String> thenApplyFuture = orgFuture.thenApply((a)->
        {
            if ("捡田螺的小男孩".equals(a)){
                return "关注了";
            }
            return "先考虑考虑";
        });

        System.out.println(thenApplyFuture.get());
    }
}
