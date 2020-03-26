package org.litespring2.aop;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
    
    String getExpression();
}
