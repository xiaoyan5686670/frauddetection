import org.junit.Test;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 16:47 2022/3/18
 @Modified By:
 **********************************/
public class test {
    /**
     * Lambda 的使用，使用 Runnable 例子
     * @throws InterruptedException
     */
    @Test
    public void createLambda() throws InterruptedException {
        // 使用 Lambda 之前
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("JDK8 之前的线程创建");
            }
        };
        new Thread(runnable).start();
        // 使用 Lambda 之后
        Runnable runnable1Jdk8 = () -> System.out.println("JDK8 之后的线程创建");
        new Thread(runnable1Jdk8).start();
        // 更加紧凑的方式
        new Thread(() -> System.out.println("JDK8 之后的线程创建")).start();
    }
@FunctionalInterface
    public interface FunctionInterFaceDemo{
        void say(String name,int age);
    }
    @Test
    public void functionLambdaTest(){
        FunctionInterFaceDemo demo = (name, age) -> System.out.println("我叫" + name + "，我今年" + age + "岁了");
        demo.say("qxy",29);
    }
}
