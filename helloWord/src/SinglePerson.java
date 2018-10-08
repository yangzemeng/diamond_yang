/**
 * @author diamod
 * @date 2018-09-15:11:41
 */

//单例模式饥汉模式
public class SinglePerson {
    private static final  SinglePerson singlePerson=new SinglePerson();
    //私有化成员变量

    //将构造器私有化，避免new对象
    private SinglePerson(){

    }
    //创建静态方法
    public static SinglePerson getSinglePerson(){
        System.out.println("hahah");
        return singlePerson;
    }
}
