package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:57 2022/3/25
 @Modified By:
 **********************************/
public class Thread01_Exceptionally {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {

            if (Math.random() < 0.5){
                throw new RuntimeException("抛出异常");
            }

            System.out.println("正常结束");
            return 1.1;
        }).thenApply(result -> {
            //注意这里用到了上个线程的返回值dept
            System.out.println("线程:" + Thread.currentThread().getName() +
                    "假设把dept作为日志记录发给Kafka" + result.toString());
           return result;
        }).exceptionally(new Function<Throwable,Double>(){
            @Override
            public Double apply(Throwable throwable){
                System.out.println("异常" + throwable.getMessage());
                return 0.0;
            }
        });
    /** throwable -> { System.out.println("异常" + throwable.getMessage()); return 0.0; } */
    System.out.println("最终返回的结果 = " + future.get());
    }
}
