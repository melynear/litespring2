package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.aspectj.AspectJExpressionPointcut;
import org.litespring2.aop.config.AspectInstanceFactory;
import org.litespring2.aop.config.MethodLocatingFactory;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.ConstructorArgument;
import org.litespring2.beans.ConstructorArgument.ValueHolder;
import org.litespring2.beans.PropertyValue;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.tx.TransactionManager;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月29日
 */
public class BeanDefinitionTestV5 extends AbstarctV5Test {
    @Test
    public void testAOPBean() {
        DefaultBeanFactory factory = (DefaultBeanFactory) getBeanFactory("petstore-v5.xml");
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("tx");
            Assert.assertEquals(TransactionManager.class.getName(), beanDefinition.getBeanClassName());
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition("placeOrder");
            Assert.assertTrue(beanDefinition.isSynthetic());
            Class<?> beanClass = beanDefinition.getBeanClass();
            Assert.assertEquals(AspectJExpressionPointcut.class, beanClass);
            
            List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
            Assert.assertEquals(1, propertyValues.size());
            
            PropertyValue propertyValue = propertyValues.get(0);
            Assert.assertEquals("expression", propertyValue.getName());
            Assert.assertEquals("execution(* org.litespring2.service.v5.*.placeOrder(..))", propertyValue.getValue());
        }
        
        {
            BeanDefinition beanDefinition = factory.getBeanDefinition(AspectJBeforeAdvice.class.getName() + "#0");
            Assert.assertEquals(AspectJBeforeAdvice.class, beanDefinition.getBeanClass());
            Assert.assertTrue(beanDefinition.isSynthetic());
            
            ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();
            Assert.assertEquals(3, constructorArgument.getArgumentCount());
            List<ValueHolder> argumentValues = constructorArgument.getArgumentValues();
            
            {
                BeanDefinition innerBeanDefinition = (BeanDefinition) argumentValues.get(0).getValue();
                Assert.assertTrue(innerBeanDefinition.isSynthetic());
                Assert.assertEquals(MethodLocatingFactory.class, innerBeanDefinition.getBeanClass());
                
                List<PropertyValue> propertyValues = innerBeanDefinition.getPropertyValues();
                Assert.assertEquals(2, propertyValues.size());
                Assert.assertEquals("targetBeanName", propertyValues.get(0).getName());
                Assert.assertEquals("tx", propertyValues.get(0).getValue());
                Assert.assertEquals("methodName", propertyValues.get(1).getName());
                Assert.assertEquals("start", propertyValues.get(1).getValue());
            }
            
            {
                RuntimeBeanReference reference = (RuntimeBeanReference) argumentValues.get(1).getValue();
                Assert.assertEquals("placeOrder", reference.getBeanName());
            }
            
            {
                BeanDefinition innerBeanDefinition = (BeanDefinition) argumentValues.get(2).getValue();
                Assert.assertTrue(innerBeanDefinition.isSynthetic());
                Assert.assertEquals(AspectInstanceFactory.class, innerBeanDefinition.getBeanClass());
                
                List<PropertyValue> propertyValues = innerBeanDefinition.getPropertyValues();
                Assert.assertEquals(1, propertyValues.size());
                Assert.assertEquals("aspectBeanName", propertyValues.get(0).getName());
                Assert.assertEquals("tx", propertyValues.get(0).getValue());
            }
        }
    }
}
