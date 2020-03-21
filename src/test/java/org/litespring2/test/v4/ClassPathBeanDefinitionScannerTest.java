package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.context.annotation.ScannedGenericBeanDefinition;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.annotation.ClassPathBeanDefinitionScanner;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.stereotype.Component;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScannedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        String basePackage = "org.litespring2.dao.v4,org.litespring2.service.v4";
        
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        
        scanner.doScan(basePackage);
        
        String annotation = Component.class.getName();
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotation));
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", annotationAttributes.get("value"));
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotation));
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(beanDefinition instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) beanDefinition;
            
            AnnotationMetadata metadata = sbd.getMetadata();
            Assert.assertTrue(metadata.hasAnnotation(annotation));
        }
    }
}
