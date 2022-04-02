package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 15:24 2022/3/24
 @Modified By:
 **********************************/
public class Thread01_SupplyAsync {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        CompletableFuture<Void> deptCompletableFuture = CompletableFuture.runAsync(() ->{
            Dept dept = deptService.getById(1);
        //    return dept;
        });

        System.out.println("线程" +  Thread.currentThread().getName() + " 结果:" + deptCompletableFuture.get());

        CompletableFuture<Dept> deptCompletableFuture2 = CompletableFuture.supplyAsync(() ->{
            Dept dept = deptService.getById(1);
               return dept;
        });

        System.out.println("线程" +  Thread.currentThread().getName() + " 结果:" + deptCompletableFuture.get());

    }
}
