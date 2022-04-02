package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:38 2022/3/25
 @Modified By:
 **********************************/
public class Thread05_SupplyAsync_ThenRun {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Dept dept  = deptService.getById(1);
            return dept ;
        })
                .thenRunAsync(() -> { //注意没有入参
                    System.out.println("线程：" + Thread.currentThread().getName() + " do something");
                    //thenRun注意没有入参,也没有返回值
                });
        System.out.println("线程" + Thread.currentThread().getName() +
                "结果 :" + voidCompletableFuture.get());

    }
}
