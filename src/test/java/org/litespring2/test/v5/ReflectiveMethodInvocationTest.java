package org.litespring2.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.config.AspectInstanceFactory;
import org.litespring2.aop.framework.ReflectiveMethodInvocation;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class ReflectiveMethodInvocationTest extends AbstarctV5Test {
    private AspectJBeforeAdvice beforeAdvice;
    private AspectJAfterReturningAdvice afterReturningAdvice;
    private AspectJAfterThrowingAdvice afterThrowingAdvice;
    
    private BeanFactory factory;
    private AspectInstanceFactory adviceObjectFactory;
    
    private PetStoreService service;
    
    @Before
    public void setUp() throws Exception {
        service = new PetStoreService();
        
        MessageTracker.clearMsgs();
        factory = getBeanFactory("petstore-v5.xml");
        adviceObjectFactory = getAspectInstanceFactory("tx");
        adviceObjectFactory.setBeanFactory(factory);
        
        beforeAdvice = new AspectJBeforeAdvice(getAdviceMethod("start"),
                null, adviceObjectFactory);
        afterReturningAdvice = new AspectJAfterReturningAdvice(getAdviceMethod("commit"),
                null, adviceObjectFactory);
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(getAdviceMethod("rollback"),
                null, adviceObjectFactory);
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
