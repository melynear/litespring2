package org.litespring2.aop.config;

import org.apache.commons.lang3.StringUtils;
import org.litespring2.beans.BeanUtils;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.beans.factory.BeanFactoryAware;
import org.litespring2.beans.factory.FactoryBean;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware {
    private String targetBeanName;
    
    private String methodName;
    
    private Method method;
    
    @Override
    public void setBeanFactory(BeanFactory factory) {
        if (StringUtils.isEmpty(targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        
        if (StringUtils.isEmpty(methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }
        
        Class<?> beanClass = factory.getType(targetBeanName);
        
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }
        
        this.method = BeanUtils.resolveSignature(methodName, beanClass);
        
        if (this.method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }
    
    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public void setMethod(Method method) {
        this.method = method;
    }
    
    @Override
    public Method getObject() {
        return this.method;
    }
    
    @Override
    public Class<Method> getObjectType() {
        return Method.class;
    }
}
