package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.aspectj.AspectJExpressionPointcut;
import org.litespring2.aop.config.AspectInstanceFactory;
import org.litespring2.aop.framework.AopConfig;
import org.litespring2.aop.framework.AopConfigSupport;
import org.litespring2.aop.framework.CglibProxyFactory;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.util.MessageTracker;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class CglibAopProxyTest extends AbstarctV5Test {
    private AspectJBeforeAdvice beforeAdvice;
    private AspectJAfterReturningAdvice afterReturningAdvice;
    private AspectJExpressionPointcut pointcut;
    private BeanFactory factory;
    private AspectInstanceFactory adviceObjectFactory;
    
    @Before
    public void setUp() throws Exception {
        String expression = "execution(* org.litespring2.service.v5.*.placeOrder(..))";
        pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        
        factory = this.getBeanFactory("petstore-v5.xml");
        adviceObjectFactory = this.getAspectInstanceFactory("tx");
        adviceObjectFactory.setBeanFactory(factory);
        beforeAdvice = new AspectJBeforeAdvice(getAdviceMethod("start"), pointcut, adviceObjectFactory);
        afterReturningAdvice = new AspectJAfterReturningAdvice(getAdviceMethod("commit"), pointcut, adviceObjectFactory);
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
