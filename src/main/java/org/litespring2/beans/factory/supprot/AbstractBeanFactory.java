package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月30日
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition beanDefinition) throws BeanCreationException;
}
