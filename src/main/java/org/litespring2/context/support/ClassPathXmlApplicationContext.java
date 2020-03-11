package org.litespring2.context.support;


import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.context.ApplicationContext;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.io.Resource;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    
    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        Resource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(resource);
    }
    
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
