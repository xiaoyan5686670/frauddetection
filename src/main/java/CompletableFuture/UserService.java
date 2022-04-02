package CompletableFuture;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:33 2022/3/24
 @Modified By:
 **********************************/
public class UserService {

    public User getById(Integer id) throws Exception {
        System.out.println("线程" + Thread.currentThread().getName() + " getById(" + id + ")");

        if (id == 1){
            return new User(1,"冬哥",31);
        } else if (id == 2){
            return new User(2,"qx",30);
        } else {
            throw new Exception("未能找到人员");
        }
    }
    //保存User
    public User save(User user){
        System.out.println("线程:" + Thread.currentThread().getName() + " save(),"+ user.toString());
        return user;
    }
}
