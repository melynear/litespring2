package org.litespring2.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {
    public AspectJAfterReturningAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        super(adviceMethod, pointcut, adviceObject);
    }
    
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object object = methodInvocation.proceed();
        invokeAdviceMethod();
        return object;
    }
}
