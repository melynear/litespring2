package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegsitry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist.");
        }
        
        String beanClassName = beanDefinition.getBeanClassName();
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        
        try {
            Class<?> clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("Create bean for " + beanClassName + "failed.", e);
        }
    }
    
    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }
    
    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }
}
