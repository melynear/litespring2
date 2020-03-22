package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.factory.config.DependencyDescriptor;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.dao.v4.AccountDao;
import org.litespring2.service.v4.PetStoreService;

import java.lang.reflect.Field;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class DependencyDescriptorTest {
    @Test
    public void testResolveDependencyTest() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v4.xml"));
        
        Field field = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
