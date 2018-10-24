import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author diamod
 * @date 2018-09-17:9:10
 */


public class ProxyDemo {
    private Object target;

    public ProxyDemo(Object target) {
        this.target = target;
    }

    public Object creatProxy() {
        Object proxyObject = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("nihao");
                Object invoke = method.invoke(target, args);
                return invoke;
            }
        });
        return proxyObject;
    }

}
