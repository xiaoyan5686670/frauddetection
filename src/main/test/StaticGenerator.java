/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 16:49 2022/3/22
 @Modified By:
 **********************************/
public class StaticGenerator<T>{
    /**
     * 如果在类中定义使用泛型的静态方法，需要添加额外的泛型声明（将这个泛型方法定义成泛型方法）
     * 即使静态方法要使用泛型类中已经声明过的泛型也不可以。
     * 如：public static void show(T t){..},此时编译器会提示错误信息：
     * “StaticGenerator cannnot be refrenced from static context"
     */
    public static <T> void show(T t){

    }

}
