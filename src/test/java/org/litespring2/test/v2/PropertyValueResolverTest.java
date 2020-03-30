package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.beans.factory.supprot.BeanDefinitionValueResolver;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.dao.v2.AccountDao;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class PropertyValueResolverTest {
    DefaultBeanFactory factory;
    XmlBeanDefinitionReader reader;
    BeanDefinitionValueResolver resolver;
    
    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
        
        resolver = new BeanDefinitionValueResolver(factory);
    }
    
    @Test
    public void testResolveRuntimeBeanReference() {
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertTrue(value instanceof AccountDao);
    }
    
    @Test
    public void testResolveTypedStringValue() {
        TypedStringValue reference = new TypedStringValue("petName");
        
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertTrue("petName".equals(value));
    }
}
