package Thread02;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 9:37 2022/3/28
 @Modified By:
 **********************************/
public class Thread02_Sleep {

        public static void main(String[] args) throws InterruptedException {

           Thread t1 = new Thread("t1"){
               @Override
               public void run(){
                  try{
                      System.out.println("子线程开始Sleep");
                      Thread.sleep(3000);
                      System.out.println("子线程结束Sleep");
               } catch (InterruptedException e){
                      e.printStackTrace();
                  }
               }
               };

            t1.start();

            System.out.println("子线程的状态" + t1.getState());
            Thread.sleep(1000);//这个是主线程的sleep，而不是子线程，为了延时获取到子线程阻塞状态
            System.out.println("子线程的状态" + t1.getState());
        }
    }


