package org.litespring2.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring2.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {
    public AspectJAfterThrowingAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            invokeAdviceMethod();
            throw throwable;
        }
    }
}
