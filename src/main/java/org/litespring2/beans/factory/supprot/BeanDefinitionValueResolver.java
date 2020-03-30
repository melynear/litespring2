package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.FactoryBean;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class BeanDefinitionValueResolver {
    private final AbstractBeanFactory factory;
    
    public BeanDefinitionValueResolver(AbstractBeanFactory factory) {
        this.factory = factory;
    }
    
    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            return factory.getBean(((RuntimeBeanReference) value).getBeanName());
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else if (value instanceof BeanDefinition) {
            BeanDefinition beanDefinition = (BeanDefinition) value;
            
            String innerBeanName = "(inner bean)" + beanDefinition.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(beanDefinition));
            
            return resolveInnerBean(innerBeanName, beanDefinition);
        } else {
            return value;
        }
    }
    
    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBeanDefinition) {
        Object innerBean = factory.createBean(innerBeanDefinition);
        
        if (innerBean instanceof FactoryBean) {
            try {
                return ((FactoryBean) innerBean).getObject();
            } catch (Exception e) {
                throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
            }
        } else {
            return innerBean;
        }
    }
}
