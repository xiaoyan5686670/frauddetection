package test;

import java.util.function.BiFunction;
import java.util.function.Function;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 15:19 2022/3/18
 @Modified By:
 **********************************/
public class Java8BifunctionAndThen2 {
    public static void main(String[] args){
        String result = convert("java","www.wdbyte.com",
                (a1,a2) -> a1.length() + a2.length(),r1 -> "长度和:" + r1);
        System.out.println(result);
        String covert = convert(1,2,
                (a1,a2) -> a1 + a2,
                r1 -> "和是：" +r1);
        System.out.println(covert);
    }
    public static <T1,T2,R1,R2> R2 convert(T1 t1,
                                           T2 t2,
                                           BiFunction<T1,T2,R1> biFunction,
                                           Function<R1,R2> function){
        return biFunction.andThen(function).apply(t1,t2);
    }

}
