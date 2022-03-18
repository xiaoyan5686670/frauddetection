import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 15:42 2022/3/15
 @Modified By:
 **********************************/
public class Future_test {
    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(4);
        Future<BigDecimal> future = es.submit(new Task2("601857"));
        System.out.println(future.get());
        es.shutdown();
    }

}

class Task2 implements Callable<BigDecimal> {
  public Task2(String code) {}

  @Override
  public BigDecimal call() throws Exception {
    Thread.sleep(1000);
    double d = 5 + Math.random() * 20;
    return new BigDecimal(d).setScale(2, RoundingMode.DOWN);
  }
    }