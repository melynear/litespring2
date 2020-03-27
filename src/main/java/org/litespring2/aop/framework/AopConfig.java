package org.litespring2.aop.framework;

import org.litespring2.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public interface AopConfig {
    Class<?> getTargetClass();
    
    Object getTargetObject();
    
    boolean isProxyTargetClass();
    
    Class<?>[] getProxiedInterfaces();
    
    void addAdvice(Advice advice);
    
    List<Advice> getAdvices();
    
    List<Advice> getAdvices(Method method);
    
    void setTargetObject(Object targetObject);
}
