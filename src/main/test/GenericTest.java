import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:23 2022/3/22
 @Modified By:
 **********************************/
@NoArgsConstructor
public class GenericTest {

    public interface Generator<T>{
        public T next();
    }
    public static class Generic<T extends Number>{
        private T key;

        public Generic(T i){
            this.key = key;
        }
        public Generic(String i){
            this.key = key;
        }
        public Generic(Float i){
            this.key = key;
        }
        public Generic(Double i){
            this.key = key;
        }


        //我想说的其实是这个，虽然在方法中使用泛型，但是这并不是一个泛型方法。
        //这只是类中一个普通的成员方法，只不过他的近回值是在声明泛型类已经声明过的泛型
        //所以在这个方法中才可以继续使用T这个泛型

        public T getKey(){
            return key;
        }
        /**
         *  这个方法显然是有问题的，在编译器会给我们提示这样的错误信息”cannot reslove symbol E"
         *  因为在类的声明中并未声明泛型E，所以在使用E做形参和返回值类型时，编译器会无法识别。
         *  public E setKey(E key){
         *     this.key = keu
         *     }
         */
    }
    /**
     *  这才是一个真正的泛型方法。
     *  首先在public与返回值之间的<T>必不可少，这表明是一个泛型方法，并且声明了一个泛型T
     *  这个T可以出现在这个泛型方法的任意位置。
     *  泛型的数量也可以为任意多个
     *  如 : public <T,K> K showKeyName(Generic<T> container){
     *      ...
     *      }
     */
    public <T extends Number> T showKeyName(Generic<T> container){
        log.info("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }

    //这也不是一个泛型方法，这就是一个普通方法，只是使用了Generic<Number>这个泛型类做形参而已。
//    public void showKeyValue1(Generic<Number> obj){
//        log.debug("泛型测试","key value is " + obj.getKey());
//    }

    //这也不是一个泛型方法，这也是一个普通方法，只不过使用了泛型通配符”？“
    //同时这也印证了泛型通配符，？是一种类型实参，可以看做为Number等所有类的父类
//    public void showKeyValue2(Generic<?> obj){
//        log.debug("泛型测试","key value is " + obj.getKey());
//    }

    /**
     *  这个方法是有问题的，编译器会为我们提示错误信息："unknown class 'E'"
     *  虽然我们声明了<T>,也表明了这是一个可以处理泛型的类型的泛型方法。
     *  但是只声明了泛型类型T,并未声明泛型类型E，因为编译器并不知道该如何处理E这个类型。
     */
//    public <T> T showKeyName(Generic<E> container){
//        ...
//    }
//在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界 ，即在泛型声明的时候添加
    //public <T> T showKeyName(Generic<T extends Number> container),编译器会报错："Unexpected bound"
    public <T extends Number> T showKeyName3(Generic<T> container){
        System.out.println("container key " + container.getKey());
        T test = container.getKey();
        return test ;
    }



    public static void showKeyValue1(Generic<? extends Number> obj){
        log.info("泛型测试","key value is " + obj.getKey());
    }
    public static void showKeyValue2(Generic<? extends Number> obj){
        log.debug("泛型测试","key value is " + obj.getKey());
    }
    public static void main(String[] args){
        Integer x = 5;
        Generic<? extends Number> qxy = new Generic<Long>(2222L);


        showKeyValue1(new Generic<Long>(2222L));

       // Generic<String> generic1 = new Generic<>("11111");
        Generic<Integer> generic2 = new Generic<>(2222);
        Generic<Float> generic3 = new Generic<>(2.4f);
        Generic<Double> generic4 = new Generic<>((float) 2.56);
       // showKeyValue2(generic1); 这一行代码也会报错，因为String不是Number的子类
        showKeyValue2(generic2);
        showKeyValue2(generic3);
        showKeyValue2(generic4);

    }
}
