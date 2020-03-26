package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.aop.MethodMatcher;
import org.litespring2.aop.aspectj.AspectJExpressionPointcut;
import org.litespring2.service.v5.PetStoreService;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class PointcutTest {
    @Test
    public void testPointcut() throws NoSuchMethodException {
        String expression = "execution(* org.litespring2.service.v5.*.placeOrder(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        
        MethodMatcher matcher = pointcut.getMethodMatcher();
        
        {
            Class<?> aClass = PetStoreService.class;
            Method method1 = aClass.getMethod("placeOrder");
            Method method2 = aClass.getMethod("getAccountDao");
            
            Assert.assertTrue(matcher.matches(method1));
            Assert.assertFalse(matcher.matches(method2));
        }
        
        {
            Class<?> aClass = org.litespring2.service.v4.PetStoreService.class;
            Method method = aClass.getMethod("getAccountDao");
            
            Assert.assertFalse(matcher.matches(method));
        }
    }
}
