package org.litespring2.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.BeanDefinitionStoreException;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class BeanFactoryTest {
    private DefaultBeanFactory beanFactory;
    private XmlBeanDefinitionReader reader;
    
    @Before
    public void setUp() {
        beanFactory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(beanFactory);
    }
    
    
    @Test
    public void testGetBean() {
        reader.loadBeanDefinition("petstore-v1.xml");
        
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");
        
        Assert.assertEquals("org.litespring2.service.v1.PetStoreService", beanDefinition.getBeanClassName());
        Assert.assertNotNull(beanFactory.getBean("petStore"));
    }
    
    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition("petstore-v1.xml");
        
        try {
            beanFactory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        
        Assert.fail("expect BeanCreationException");
    }
    
    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinition("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
