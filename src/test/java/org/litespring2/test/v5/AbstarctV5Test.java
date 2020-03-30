package org.litespring2.test.v5;

import org.litespring2.aop.config.AspectInstanceFactory;
import org.litespring2.beans.factory.BeanFactory;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.io.Resource;
import org.litespring2.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月28日
 */
public class AbstarctV5Test {
    protected BeanFactory getBeanFactory(String configFile) {
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
        return defaultBeanFactory;
    }
    
    protected Method getAdviceMethod(String methodName) throws Exception {
        return TransactionManager.class.getMethod(methodName);
    }
    
    protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory factory = new AspectInstanceFactory();
        factory.setAspectBeanName(targetBeanName);
        return factory;
    }
}
