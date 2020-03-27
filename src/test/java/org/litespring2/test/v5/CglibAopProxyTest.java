package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.aspectj.AspectJExpressionPointcut;
import org.litespring2.aop.framework.AopConfig;
import org.litespring2.aop.framework.AopConfigSupport;
import org.litespring2.aop.framework.CglibProxyFactory;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.tx.TransactionManager;
import org.litespring2.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class CglibAopProxyTest {
    private AspectJBeforeAdvice beforeAdvice;
    private AspectJAfterReturningAdvice afterReturningAdvice;
    private AspectJExpressionPointcut pointcut;
    
    private TransactionManager tx;
    
    @Before
    public void setUp() throws NoSuchMethodException {
        tx = new TransactionManager();
        String expression = "execution(* org.litespring2.service.v5.*.placeOrder(..))";
        pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        
        Method method1 = TransactionManager.class.getMethod("start");
        beforeAdvice = new AspectJBeforeAdvice(method1, pointcut, tx);
        Method method2 = TransactionManager.class.getMethod("commit");
        afterReturningAdvice = new AspectJAfterReturningAdvice(method2, pointcut, tx);
    }
    
    @Test
    public void testAopProxy() {
        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterReturningAdvice);
        config.setTargetObject(new PetStoreService());
        
        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();
        
        proxy.placeOrder();
        
        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
        
        proxy.toString();
    }
}
