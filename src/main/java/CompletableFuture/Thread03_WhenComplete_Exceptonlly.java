package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 11:23 2022/3/27
 @Modified By:
 **********************************/
public class Thread03_WhenComplete_Exceptonlly {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(()-> {

            if (Math.random() < 0.5){
                throw new RuntimeException("出错了");
            }
            System.out.println("正常结束");
            return 0.11;
        }).whenComplete( (aDouble, throwable) -> {
            if (aDouble == null){
                System.out.println("whenCOmplete aDouble is null");
            } else {
                System.out.println("whenComplete aDouble is " + aDouble);
            }

            if (throwable == null){
                System.out.println("whenComplete throwable is null");
            } else {
                System.out.println("whenComplete throwable is " + throwable.getMessage());
            }
        }).exceptionally(
                new Function<Throwable, Double>() {
                    @Override
                    public Double apply(Throwable throwable) {
                      System.out.println("exceptionally中异常:"+ throwable.getMessage());
                      return 0.0;
                    }
                }
        );
        System.out.println("最终返回的结果" + future.get());
    }
}
