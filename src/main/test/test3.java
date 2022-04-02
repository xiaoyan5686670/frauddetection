import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*********************************
 @Author:xiaoyan.qin
 @Description:
 @Date:Created in 16:29 2022/3/22
 @Modified By:
 **********************************/
@Slf4j
public class test3 {

    public <T> void printMsg(T... args){
        for(T t: args){
            log.debug("泛型测试","t is " + t);
        }
    }
    @Test
    public void forEach_Array(){

        printMsg(1,2);
    }
    public static void main(String[] args){
      //  List<String>[] lsa = new List<String>[10]; //Not reaaly allowed
        List<?>[] lsa = new List<?>[10]; // OK, array of unbounded wildcard type.

        Object o = lsa;
        Object[] oa = (Object[]) o;
        List<Integer> li = new ArrayList<>();
        li.add(new Integer(3));
        oa[1] = li;   // Unsound,but passes run time store check
        //String s= lsa[1].get(0); //Run-time error :classCastException.
        Integer i = (Integer) lsa[1].get(0); // OK

    }

}
