package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.context.annotation.ScannedGenericBeanDefinition;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.io.Resource;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.stereotype.Component;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public class XmlBeanDefinitionReaderTest {
    @Test
    public void testParseScannedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        
        reader.loadBeanDefinition(resource);
        
        String annotationName = Component.class.getName();
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotationName));
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(annotationName);
            Assert.assertEquals("petStore", annotationAttributes.get("value"));
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotationName));
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotationName));
        }
    }
}
