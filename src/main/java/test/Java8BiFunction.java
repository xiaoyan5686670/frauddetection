package test;

import java.util.function.BiFunction;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 15:04 2022/3/18
 @Modified By:
 **********************************/
public class Java8BiFunction {
    public static void main(String[] args){
        //两个字符串长度和
        BiFunction<String,String,Integer> lengthBiFun = (s1,s2) -> s1.length() + s2.length();
        Integer length = lengthBiFun.apply("java","www.byte.com");
        System.out.println(length);

        // x 的y次方
        BiFunction<Integer,Integer,Double> powBiFun = (i1,i2) -> Math.pow(i1,i2);
        Double pow = powBiFun.apply(2,10);
        System.out.println(pow);
    }
}
