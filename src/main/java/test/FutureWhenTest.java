package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 18:46 2022/3/17
 @Modified By:
 **********************************/
public class FutureWhenTest {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () ->{
                    System.out.println("当前线程名称:"+Thread.currentThread().getName());
                    try{
                        Thread.sleep(2000L);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return "捡田螺的小男孩";
                }
                );
        CompletableFuture<String> rstFuture = orgFuture.whenComplete((a,throwable) ->{
            System.out.println("当前线程名称:" + Thread.currentThread().getName());
            System.out.println("上个任务执行完啦,还把" + a + "传过来");
            if ("捡田螺的小男孩".equals(a)){
                System.out.println("666");
            }
            System.out.println("23333");
        });

        System.out.println(rstFuture.get());
        System.out.println(orgFuture.get());

    }

}
