import java.util.HashMap;
import java.util.Map;

public class HelloWord {
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<>();
        map.put("name","diamond");
        map.put("age","18");
        SinglePerson singlePerson = SinglePerson.getSinglePerson();
        System.out.println(map);
    }
}
