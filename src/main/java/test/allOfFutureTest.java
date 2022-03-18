package test;

import java.util.concurrent.CompletableFuture;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:33 2022/3/18
 @Modified By:
 **********************************/
public class allOfFutureTest {
    public static void main(String[] args) {
        CompletableFuture<Void> a = CompletableFuture.runAsync(() ->{
            System.out.println("我执行完了");
        });
        CompletableFuture<Void> b = CompletableFuture.runAsync(() ->{
            System.out.println("我也执行完了");
        });
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(a,b).whenComplete((m,k) ->{
            System.out.println("finish");
        });
    }
}
