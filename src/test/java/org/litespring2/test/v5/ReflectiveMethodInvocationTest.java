package org.litespring2.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.framework.ReflectiveMethodInvocation;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.tx.TransactionManager;
import org.litespring2.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class ReflectiveMethodInvocationTest {
    private AspectJBeforeAdvice beforeAdvice;
    private AspectJAfterReturningAdvice afterReturningAdvice;
    private AspectJAfterThrowingAdvice afterThrowingAdvice;
    private TransactionManager manager;
    
    private PetStoreService service;
    
    @Before
    public void setUp() throws NoSuchMethodException {
        service = new PetStoreService();
        manager = new TransactionManager();
        
        MessageTracker.clearMsgs();
        
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),
                null, manager);
        afterReturningAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),
                null, manager);
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(TransactionManager.class.getMethod("rollback"),
                null, manager);
    }
    
    @Test
    public void testMethodInvocation() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        
        List<MethodInterceptor> intercepertors = new ArrayList<>();
        intercepertors.add(beforeAdvice);
        intercepertors.add(afterReturningAdvice);
        
        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(service, targetMethod, new Object[0], intercepertors);
        
        mi.proceed();
        
        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
    
    @Test
    public void testMethodInvocationOutOfOrder() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        
        List<MethodInterceptor> intercepertors = new ArrayList<>();
        intercepertors.add(afterReturningAdvice);
        intercepertors.add(beforeAdvice);
        
        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(service, targetMethod, new Object[0], intercepertors);
        
        mi.proceed();
        
        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
    
    @Test
    public void testAfterThrowing() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");
        
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterReturningAdvice);
        interceptors.add(afterThrowingAdvice);
        
        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(service, targetMethod, new Object[0], interceptors);
        
        try {
            mi.proceed();
        } catch (Throwable throwable) {
            List<String> msgs = MessageTracker.getMsgs();
            Assert.assertEquals(2, msgs.size());
            Assert.assertEquals("start tx", msgs.get(0));
            Assert.assertEquals("rollback tx", msgs.get(1));
            return;
        }
        
        Assert.fail("No Exception thrown");
    }
}
