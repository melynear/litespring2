package org.litespring2.test.v5;

import org.junit.Test;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.tx.TransactionManager;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class CglibTest {
    @Test
    public void testCallBack() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setCallback(new TransactionInterceptor());
        
        PetStoreService proxy = (PetStoreService) enhancer.create();
        
        proxy.placeOrder();
    }
    
    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager tx = new TransactionManager();
        
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object result = methodProxy.invokeSuper(o, objects);
            tx.commit();
            return result;
        }
    }
    
    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();
        
        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        
        Class<?>[] types = new Class[callbacks.length];
        
        for (int i = 0; i < callbacks.length; i++) {
            types[i] = callbacks[i].getClass();
        }
        
        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        
        PetStoreService petStoreService = (PetStoreService) enhancer.create();
        petStoreService.placeOrder();
        petStoreService.toString();
    }
    
    public static class ProxyCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
