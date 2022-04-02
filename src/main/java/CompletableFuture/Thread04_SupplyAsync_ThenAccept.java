package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:14 2022/3/25
 @Modified By:
 **********************************/
public class Thread04_SupplyAsync_ThenAccept {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() ->{
            Dept dept = deptService.getById(1);
            return dept;
        }).thenAccept(dept -> {
            //注意这里用到了上个线程的返回值dept
            System.out.println("线程:" + Thread.currentThread().getName() +
                    "假设把dept作为日志记录发给Kafka：" + dept.toString() );
        });

        System.out.println("线程: " + Thread.currentThread().getName() +
                "结果: " + voidCompletableFuture.get());
    }
}
