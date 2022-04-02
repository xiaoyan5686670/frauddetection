package CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:23 2022/3/27
 @Modified By:
 **********************************/
public class Thread10_ThenCombine {
    public static void main(String[] args) throws ExecutionException,InterruptedException{
        DeptService deptService = new DeptService();
        UserService userService = new UserService();

        //第一个任务 ：获取id = 1的部门
        CompletableFuture<Dept> deptFuture = CompletableFuture
                .supplyAsync(() -> deptService.getById(1)
                );

        //第2个任务，获取id=1的人员

        CompletableFuture<User> userFuture = CompletableFuture
                .supplyAsync(() ->{
                    try {
                      //  int a = 1/0;//出了异常就报错
                        return userService.getById(1);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                });

        //将上面2个任务的返回结果dpet和user合并，返回新的user

        CompletableFuture<User> resultFuture = deptFuture
                .thenCombine(userFuture,
                        (dept, user) -> {
                            user.setDeptId(dept.getId());
                            user.setDeptName(dept.getName());
                            return userService.save(user);
                        });
        System.out.println("线程:" + Thread.currentThread().getName() + "结果:"+ resultFuture.get());
    }
}
