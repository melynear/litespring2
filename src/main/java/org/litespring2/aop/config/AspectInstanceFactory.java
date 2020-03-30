package org.litespring2.aop.config;

import org.litespring2.beans.BeansException;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.beans.factory.BeanFactoryAware;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月28日
 */
public class AspectInstanceFactory implements BeanFactoryAware {
    private String aspectBeanName;
    
    private BeanFactory beanFactory;
    
    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    public Object getAspectInstance() throws Exception {
        return this.beanFactory.getBean(aspectBeanName);
    }
}
