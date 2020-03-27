package org.litespring2.aop.aspectj;

import org.litespring2.aop.Advice;
import org.litespring2.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public abstract class AbstractAspectJAdvice implements Advice {
    protected Method adviceMethod;
    protected AspectJExpressionPointcut pointcut;
    protected Object adviceObject;
    
    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pointcut = pointcut;
        this.adviceObject = adviceObject;
    }
    
    protected void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);
    }
    
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
