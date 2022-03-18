package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 16:53 2022/3/17
 @Modified By:
 **********************************/
public class FutureTest2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException{
        UserInfoService userInfoService = new UserInfoService();
        MedalService medalService = new MedalService();
        long userId = 666L;
        long startTime = System.currentTimeMillis();

        //调用用户服务获取用户基本信息
        CompletableFuture<UserInfo> completableUserInfoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return userInfoService.getUserInfo(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        );
        Thread.sleep(300);//模拟主线程其他操作耗时

        CompletableFuture<MedalInfo> completableMedialInfoFuture = CompletableFuture.supplyAsync(()
                -> {
            try {
                return medalService.getMedalInfo(userId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  null;
        });

        UserInfo userInfo = completableUserInfoFuture.get(2, TimeUnit.SECONDS);
        System.out.println(userInfo);
        MedalInfo medalInfo = completableMedialInfoFuture.get();
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");

    }
}
