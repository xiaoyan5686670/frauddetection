package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:02 2022/3/27
 @Modified By:
 **********************************/
public class Thread06_SupplyAsync_ThenCompose {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        UserService userService = new UserService();

        User user = new User(1,"冬哥",31);

        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Dept dept = deptService.getById(1);
            return dept;
        }).thenCompose( dept -> CompletableFuture.supplyAsync(() -> {
            //注意这里用到了上个线程的返回值dept
            user.setDeptId(dept.getId());
            user.setDeptName(dept.getName());

            return userService.save(user);
        }));

        System.out.println("线程" + Thread.currentThread().getName() +
                "结果:" + userCompletableFuture.get().toString());
    }
}
