package org.litespring2.context.support;

import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class BeanDefinitionValueResolver {
    private final BeanFactory factory;
    
    public BeanDefinitionValueResolver(BeanFactory factory) {
        this.factory = factory;
    }
    
    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            return factory.getBean(((RuntimeBeanReference) value).getBeanName());
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("The value" + value + "has not implemented.");
        }
    }
}
