package CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description: https://www.cnblogs.com/cjsblog/p/9267163.html CompletableFuture基本用法
 @Date:Created in 15:59 2022/3/24
 @Modified By:
 **********************************/
public class cn163test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * thenApplyAsync默认是异步执行的。这里所谓的异步指的是不在当前线程内执行
         * thenApply相当于回调函数(callback)
         */
        CompletableFuture.supplyAsync(() -> 1).thenApply(i -> i +1)
                .thenApply(i -> i * i )
                .whenComplete((r,e) -> System.out.println(r)).get();
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World")
                .thenApply(String::toUpperCase)
                .whenComplete((s, throwable) -> System.out.println(s));

        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World")
                .thenApply(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture(" Java"),
                        (s1,s2) -> s1 + s2)
                .thenAccept(System.out::println);
        String[] list = {"a","b","c"};

        List<CompletableFuture> resList = new ArrayList<>();
        for (String str : list){
           resList
                   .add(CompletableFuture.supplyAsync(() -> str)
                           .thenApply( e -> e.toUpperCase()));
        }

        CompletableFuture.allOf(resList.toArray(new CompletableFuture[resList.size()]))
                .whenComplete((s, throwable) -> System.out.println(s)
                );


    }
}
