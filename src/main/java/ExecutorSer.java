import scala.tools.nsc.Global;

import java.util.concurrent.*;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:03 2022/3/15
 @Modified By:
 **********************************/
public class ExecutorSer {
    public static void main(String[] args){
//        int min = 4;
//        int max = 10;
//      //  ExecutorService es = Executors.newFixedThreadPool(4);
//        ExecutorService es = new ThreadPoolExecutor(min,max,
//        60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
//        for (int i = 0 ; i < 6; i++){
//            es.submit(new Task("" + i));
//        }
//        es.shutdown();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);
       ses.schedule(new Task("one-time"),1,TimeUnit.SECONDS);
     //   ses.scheduleWithFixedDelay(new Task("fixed-delay"), 2, 3, TimeUnit.SECONDS);

//        for (int i = 0 ; i < 6; i++){
//            ses.submit(new Task("" + i));
//        }
//        ses.shutdown();
    }
}

class Task implements Runnable {
    private final String name;
    public Task(String name){
        this.name = name;
    }

    @Override
    public void run(){
        System.out.println("Start task" + name);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            System.out.println("end task " + name);
        }
    }

}
