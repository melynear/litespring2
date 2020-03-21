package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry);
}
