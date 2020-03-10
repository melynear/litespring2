package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    
    private String beanClassName;
    
    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }
    
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
