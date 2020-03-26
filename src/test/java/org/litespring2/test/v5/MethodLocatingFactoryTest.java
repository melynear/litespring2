package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.aop.config.MethodLocatingFactory;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class MethodLocatingFactoryTest {
    @Test
    public void testGetMethod() throws NoSuchMethodException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        reader.loadBeanDefinition(new ClassPathResource("petstore-v5.xml"));
        
        MethodLocatingFactory mlf = new MethodLocatingFactory();
        mlf.setTargetBeanName("tx");
        mlf.setMethodName("start");
        mlf.setBeanFactory(factory);
        
        Method method = mlf.getObject();
        Assert.assertEquals(TransactionManager.class, method.getDeclaringClass());
        Assert.assertEquals(TransactionManager.class.getDeclaredMethod("start"), method);
    }
}
