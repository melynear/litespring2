package org.litespring2.context.support;

import org.litespring2.aop.aspectj.AspectJAutoProxyCreator;
import org.litespring2.beans.factory.NoSuchBeanDefinitionException;
import org.litespring2.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.context.ApplicationContext;
import org.litespring2.core.io.Resource;
import org.litespring2.utils.ClassUtils;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    
    private ClassLoader classLoader;
    
    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        Resource resource = getResourceByPath(configFile);
        reader.loadBeanDefinition(resource);
        factory.setClassLoader(getClassLoader());
        registerBeanPostProcessors(factory);
    }
    
    protected void registerBeanPostProcessors(DefaultBeanFactory factory) {
        {
            AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
            processor.setBeanFactory(factory);
            factory.addBeanPostProcessor(processor);
        }
        
        {
            AspectJAutoProxyCreator processor = new AspectJAutoProxyCreator();
            processor.setBeanFactory(factory);
            factory.addBeanPostProcessor(processor);
        }
    }
    
    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
    
    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
    
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public abstract Resource getResourceByPath(String path);
    
    @Override
    public Class<?> getType(String targetBeanName) throws NoSuchBeanDefinitionException {
        return factory.getType(targetBeanName);
    }
    
    @Override
    public List<Object> getBeansByType(Class<?> type) {
        return factory.getBeansByType(type);
    }
}
