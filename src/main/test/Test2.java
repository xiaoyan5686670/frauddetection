import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static jdk.nashorn.internal.objects.NativeArray.forEach;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 17:07 2022/3/18
 @Modified By:
 **********************************/
@Slf4j
public class Test2 {
    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    static class User{
        private String name;
        private Integer age;
    }
    public static List<User> userList = new ArrayList<>();
    static {
        userList.add(new User("A",26));
        userList.add(new User("B",18));
        userList.add(new User("C",23));
        userList.add(new User("D",19));
    }
    /**
     * 测试方法引用
     */
    @Test
    public void methodRef(){
        User[] userArr = new User[userList.size()];
        userList.toArray(userArr);
        // User::getAge 调用getAge方法
        Arrays.sort(userArr, Comparator.comparing(User::getAge));
        for (User user :userArr){
            System.out.println(user);
        }
    }
    @Test
    public void foreachTest(){
        List<String> skills = Arrays.asList("java","golang","c++","c","python");
        //使用Lamada之前
        for (String skill :skills){
            System.out.print(skill+",");
        }

        skills.forEach((skill) -> System.out.print(skill + ","));
        System.out.println();
        skills.forEach(System.out::print);
    }
    @Test
    public void Function1(){
        Function<String,String> toUpperCase = str -> str.toUpperCase();
        String result = toUpperCase.apply("www.wdbyte.com");
        System.out.println(result);
    }
    @Test
    public void Function2(){
        Function<String,Integer> lengthFunction = str -> str.length();
        Integer length = lengthFunction.apply("www.wdbyte.com");
        System.out.println(length);
    }
    @Test
    public void Java8FunctionAndThen(){
        Function<String,Integer> lengthFunction = str -> str.length();
        Function<Integer,Integer> doubleFunction = length -> length * 2;
        Integer doubleLength = lengthFunction.andThen(doubleFunction).apply("www.wdbyte.com");
        System.out.println(doubleLength);
    }
    @Test
    public void Java8FunctionListToMap(){
        List<String> list = Arrays.asList("java","nodejs","wdbyte.com");
        //lambda方式
        Function<String,Integer> lengthFunction = str -> str.length();
        Map<String,Integer> listToMap = listToMap(list,lengthFunction);
        System.out.println(listToMap);
    }

    public static <T,R> Map<T,R> listToMap(List<T> list,Function<T,R> function){
        HashMap<T,R> hashMap = new HashMap<>();
        for (T t: list){
            hashMap.put(t,function.apply(t));
        }
        return hashMap;
    }

    @Test
    public void JavaFunctionString(){
        List<String> list = Arrays.asList("Java","NodeJs","wdbyte.com");
        //方法引用方式
        List<String> upperList = map(list,String::toUpperCase);
        List<String> lowerList = map(list,String::toLowerCase);
        System.out.println(upperList);
        System.out.println(lowerList);

        //Lambda方式
        List<String> upperList2 = map(list, x-> x.toUpperCase());
        List<String> lowerList2 = map(list, x-> x.toLowerCase());

        System.out.println(upperList2);
        System.out.println(lowerList2);

    }

    public static <T,R> List<R> map(List<T> list,Function<T,R> function){
        List<R> resultList = new ArrayList<>(list.size());
        for (T t: list){
            resultList.add(function.apply(t));
        }
        return resultList;
    }
@Test
  public void Java8Supplier(){
      Supplier<Integer> supplier = () -> new Random().nextInt(10);
      System.out.println(supplier.get());
      System.out.println(supplier.get());

      Supplier<LocalDateTime> supplier2 = LocalDateTime::now;
      System.out.println(supplier2.get());
      System.out.println(supplier2.get());

    }
@Test
    public void Java8SupplierFactory(){
        Dog dog1 = dogFactory(() -> new Dog("hsq"));
        Dog dog2 = dogFactory(() -> new Dog("myq"));
        System.out.println(dog1);
        System.out.println(dog2);

    }
    public static Dog dogFactory(Supplier<? extends Dog> supplier){
        Dog dog = supplier.get();
        dog.setAge(1);
        return dog;
    }

    @Test
    public void Java8SupplierInt(){
        IntSupplier intSupplier = () -> new Random().nextInt(10);
        System.out.println(intSupplier.getAsInt());
        System.out.println(intSupplier.getAsInt());

    }

    @Test
    public void Java8Consumer(){
        Consumer<String> lengthConsumer = s -> System.out.println(s.length());
        lengthConsumer.accept("www.wdbyte.com");

        Consumer<String> printConsumer = System.out::println;
        printConsumer.accept("www.wdbyte.com");
    }

    public void Java8ConsumerAndThen(){
        Consumer<String> lengthConsumer = s -> System.out.println(s.length());
        Consumer<String> printConsumer  = lengthConsumer.andThen(System.out::println);
        printConsumer.accept("www.wdbyte.com");
    }

    @Test
    public void Java8ConsumerForEach(){
        Consumer<String> printConsumer = System.out::println;
        List<String> list = Arrays.asList("java","node","www.wdbyte.com");

        forEach(list,printConsumer);
        forEach(list,s -> System.out.println(s.length()));
    }
    public static <T> void forEach(List<T> list,Consumer<T> consumer){
        for (T t: list){
            consumer.accept(t);
        }
    }

     @Test
    public void Java8ObjIntConsumer(){
        List<String> list = Arrays.asList("java","node","www.wdbyte.com");
        ObjIntConsumer<String> consumer = (str,i) -> {
            if (str.length() == i ){
                System.out.println(str);
            }
        };
        forEach(list,consumer,4);
    }
    public static <T> void forEach(List<T> list,ObjIntConsumer<T> consumer,int i ){
        for (T t: list){
            consumer.accept(t,i);
        }
    }
@Test
    public void ListforEach(){
        List<String> list = Arrays.asList("java","nodejs","c++","wdbyte.com");
        for (String s :list){
            System.out.println(s);
        }
    }

    @Test
    public void ListForEach(){
        List<String> list = Arrays.asList("java","nodejs","c++","wdbyte.com");
        //方法引用
        list.forEach(System.out::println);
        System.out.println("-----");
        //lambda
        list.forEach(s -> System.out.println(s));
    }

    @Test
    public void Java8ForEachMapNormal(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("java","Java");
        hashMap.put("nodejs","NodeJS");
        hashMap.put("c++","C++");
        hashMap.put("wdbyte","WDBYTE.com");
        hashMap.put(null,"OTHER");

        for (Map.Entry<String,String> entry : hashMap.entrySet()){
            System.out.println(entry.getKey() + ":\t"+ entry.getValue());
        }
        hashMap.forEach((k,v) -> System.out.println(k + ":\t"+ v));

        hashMap.forEach((k,v) -> {
            if (k != null){
                System.out.println(k + ":\t" + v);
            }
        });

    }


    @Test
    public void forEach_Array(){
        String[] array = {"java","nodejs","wdbyte.com"};
        Arrays.stream(array).forEach(System.out::println);
    }

    @Test
    public void forEachOrdered(){
        Stream<String> stream = Stream.of("java","nodejs","c++","wdbyte.com");
        stream.parallel().forEach(System.out::println);
        stream.parallel().forEachOrdered(System.out::println);

    }

    @Test
    public void Java8ForEachDiyConsumer(){
        Stream<String> stream = Stream.of("java","nodejs","wbbyte.com");
        List<String> list = Arrays.asList("java","nodejs","wdbyte.com");
        Consumer<String> consuemr = ( s -> {
            String upperCase = s.toUpperCase();
            System.out.println(upperCase);
        });
        list.forEach(consuemr);
        stream.forEach(consuemr);
    }


}
