package test;

import java.util.function.BiFunction;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 15:29 2022/3/18
 @Modified By:
 **********************************/


import java.util.function.BiFunction;

public class JavaBiFunctionFactory {

    public static void main(String[] args) {
        System.out.println(dogFactory("牧羊犬", 1, Dog::new));
        System.out.println(dogFactory("哈士奇", 2, Dog::new));
    }

    public static <R extends Dog> Dog dogFactory(String name, Integer age, BiFunction<String, Integer, R> biFunction) {
        return biFunction.apply(name, age);
    }
}
