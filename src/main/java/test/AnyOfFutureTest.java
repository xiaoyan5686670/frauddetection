package test;

import java.util.concurrent.CompletableFuture;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:40 2022/3/18
 @Modified By:
 **********************************/
public class AnyOfFutureTest {
    public static  void main(String[] args) {
        CompletableFuture<Void> a = CompletableFuture.runAsync(() ->{
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("我执行完了");
        });
        CompletableFuture<Void> b = CompletableFuture.runAsync(()->{
            System.out.println("我也执行完了");
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a,b).whenComplete((m,k) ->{
            System.out.println("finish");
           // return "捡田螺的小男孩";
        });
        anyOfFuture.join();
    }
}
