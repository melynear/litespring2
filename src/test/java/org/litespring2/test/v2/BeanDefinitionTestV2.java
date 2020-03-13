package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.PropertyValue;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class BeanDefinitionTestV2 {
    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
        
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        
        List<PropertyValue> pvs = beanDefinition.getPropertyValues();
        
        Assert.assertTrue(pvs.size() == 4);
        
        {
            PropertyValue propertyValue = getPropertyValue("accountDao", pvs);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
        
        {
            PropertyValue propertyValue = getPropertyValue("itemDao", pvs);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
        
        {
            PropertyValue propertyValue = getPropertyValue("owner", pvs);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof TypedStringValue);
        }
    }
    
    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs) {
        for (PropertyValue pv : pvs) {
            if (name.equals(pv.getName())) {
                return pv;
            }
        }
        
        return null;
    }
}
