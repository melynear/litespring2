package org.litespring2.aop;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public interface MethodMatcher {
    boolean matches(Method method);
}
