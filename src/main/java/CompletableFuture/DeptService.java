package CompletableFuture;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:10 2022/3/24
 @Modified By:
 **********************************/
public class DeptService {

    public Dept getById(Integer id ){
        System.out.println("线程" + Thread.currentThread().getName() + " getById(" + id + ")");

        if (id == 1){
            return new Dept(1,"开发一部");
        } else if (id == 2){
            return new Dept(2,"研发二部");
        } else {
            throw null;
        }
    }



}
