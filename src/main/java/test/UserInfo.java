package test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 16:03 2022/3/17
 @Modified By:
 **********************************/
@Data
@AllArgsConstructor
public class UserInfo {
     String userId;
     String Name;
     int age;

}
