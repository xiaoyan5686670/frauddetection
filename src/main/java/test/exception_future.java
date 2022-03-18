package test;

import java.util.concurrent.*;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:41 2022/3/18
 @Modified By:
 **********************************/
public class exception_future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5
                ,10,5L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int a = 0 ;
            int b = 666;
            int c = b/a;
            return true;
            },executorService).thenAccept(System.out::println);
        future.get(); //如果不加 get()方法这一行，看不到异常信息
        }
    }

