package org.litespring2.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.supprot.ConstructorResolver;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.service.v3.PetStoreService;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月14日
 */
public class ConstructorResolverTest {
    @Test
    public void testConstructorResolver() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        
        reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));
        
        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");
        
        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(beanDefinition);
        
        Assert.assertEquals(1, petStoreService.getVersion());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
