package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 10:21 2022/3/25
 @Modified By:
 **********************************/
public class Thread02_WhenComplete {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(()->{
            if (Math.random() < 0.5 ){
                throw new RuntimeException("出错了");
            }
            System.out.println("正常结束");
            return 0.11;
        }).whenComplete(new BiConsumer<Double, Throwable>() {
            @Override
            public void accept(Double aDouble, Throwable throwable) {
                if (aDouble == null){
                    System.out.println("whenComplete aDobule is null");
                } else {
                    System.out.println("whenComplete aDouble is " + aDouble);
                }

                if (throwable == null){
                    System.out.println("whenComplte throwable is null");
                } else{
                    System.out.println("whenComplte throwable is" + throwable.getMessage());
                }
            }
        });
        System.out.println("最终返回的结果 = " +future.get());
    }
}
