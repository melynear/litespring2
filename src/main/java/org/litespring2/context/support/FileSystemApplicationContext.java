package org.litespring2.context.support;

import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.context.ApplicationContext;
import org.litespring2.core.io.FileSystemResource;
import org.litespring2.core.io.Resource;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class FileSystemApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    
    public FileSystemApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        Resource resource = new FileSystemResource(configFile);
        reader.loadBeanDefinition(resource);
    }
    
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
