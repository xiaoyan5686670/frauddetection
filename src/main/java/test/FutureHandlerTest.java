package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 18:55 2022/3/17
 @Modified By:
 **********************************/
public class FutureHandlerTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(()->{
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "捡田螺的小男孩";
           });

        CompletableFuture<String> rstFuture = orgFuture.handle((a,throwable) ->{
            System.out.println("上个任务执行完啦，还把" + a + "传过来");
            if ("捡田螺的小男孩".equals(a)) {
                System.out.println("666");
                return "关注了";
            }
            System.out.println("233333");
            return null;

        });
        System.out.println(rstFuture.get());
    }
}
