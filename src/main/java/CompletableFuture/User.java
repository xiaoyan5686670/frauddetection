package CompletableFuture;

import lombok.*;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 14:27 2022/3/24
 @Modified By:
 **********************************/
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {
    Integer id;
    String name;
    Integer age;

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    Integer DeptId;
    String DeptName;


}
