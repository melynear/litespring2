package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.aop.Advice;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.tx.TransactionManager;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月30日
 */
public class BeanFactoryTest extends AbstarctV5Test {
    private static String expectedExpression = "execution(* org.litespring2.service.v5.*.placeOrder(..))";
    
    @Test
    public void testGetBeanByType() throws Exception {
        BeanFactory beanFactory = getBeanFactory("petstore-v5.xml");
        List<Object> advices = beanFactory.getBeansByType(Advice.class);
        
        Assert.assertEquals(3, advices.size());
        {
            AspectJBeforeAdvice advice = (AspectJBeforeAdvice) getAdvice(advices, AspectJBeforeAdvice.class);
            Assert.assertEquals("start", advice.getAdviceMethod().getName());
            Assert.assertEquals(expectedExpression, advice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());
        }
        
        {
            AspectJAfterReturningAdvice advice = (AspectJAfterReturningAdvice) getAdvice(advices, AspectJAfterReturningAdvice.class);
            Assert.assertEquals("commit", advice.getAdviceMethod().getName());
            Assert.assertEquals(expectedExpression, advice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());
        }
        
        {
            AspectJAfterThrowingAdvice advice = (AspectJAfterThrowingAdvice) getAdvice(advices, AspectJAfterThrowingAdvice.class);
            Assert.assertEquals("rollback", advice.getAdviceMethod().getName());
            Assert.assertEquals(expectedExpression, advice.getPointcut().getExpression());
            Assert.assertEquals(TransactionManager.class, advice.getAdviceInstance().getClass());
        }
    }
    
    private Object getAdvice(List<Object> advices, Class<?> adviceClass) {
        for (Object advice : advices) {
            if (adviceClass.equals(advice.getClass())) {
                return advice;
            }
        }
        
        return null;
    }
}
