package org.litespring2.beans.factory.config;

import org.litespring2.beans.BeansException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月23日
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
    
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}