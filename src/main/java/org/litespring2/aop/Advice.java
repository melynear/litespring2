package org.litespring2.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}
