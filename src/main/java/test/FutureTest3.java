package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 17:30 2022/3/17
 @Modified By:
 **********************************/
public class FutureTest3 {
    public static void main(String[] args){
        //可以自定义线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        //runAsync的使用
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> System.out.println(
                "run,关注公众号:捡田螺的小男孩"),executor);
        //supplyAsync的使用
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() ->{
            System.out.print("supply,关注公众号:捡田螺的小男孩");
            return "捡田螺的小男孩";
            },executor);
        //runAsync的future没有返回值，办出null;
        System.out.println(runFuture.join());
        //supplyAsync的future,有返回值
        System.out.println(supplyFuture.join());
        executor.shutdown();
        }
    }

