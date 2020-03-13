package org.litespring2.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.ConstructorArgument;
import org.litespring2.beans.ConstructorArgument.ValueHolder;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class BeanDefinitionTestV3 {
    @Test
    public void testConstructorArgument() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));
        
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        Assert.assertEquals("org.litespring2.service.v3.PetStoreService",
                beanDefinition.getBeanClassName());
        
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
        List<ValueHolder> valueHolders = constructorArgument.getArgumentValues();
        
        Assert.assertEquals(3, valueHolders.size());
        
        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanName());
        
        TypedStringValue strValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", strValue.getValue());
    }
}
