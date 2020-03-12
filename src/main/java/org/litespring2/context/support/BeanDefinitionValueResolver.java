package org.litespring2.context.support;

import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;
    
    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
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
