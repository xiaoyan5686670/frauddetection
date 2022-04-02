package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 11:51 2022/3/27
 @Modified By:
 **********************************/
public class Thread04_Handle {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        UserService userService = new UserService();

        CompletableFuture<User> future = CompletableFuture.supplyAsync(() -> {
           // int a = 1 /0 ; //如果出现异常，那么thenApply则不执行
            return deptService.getById(1);
        }
        ).handle(new BiFunction<Dept, Throwable,User>() {
            @Override
            public User apply(Dept dept, Throwable throwable) {
                if (throwable != null){
                    System.out.println(throwable.getMessage());
                    return null;
                } else {
                    User user = new User(1,"winter",32,dept.getId(),dept.getName());
                    return userService.save(user);
                }
            }
        });
        System.out.println("线程：" + Thread.currentThread().getName()
                + "结果：" + future.get());
    }
}
