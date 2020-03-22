package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.factory.annotation.AutowireFieldElement;
import org.litespring2.beans.factory.annotation.InjectionElement;
import org.litespring2.beans.factory.annotation.InjectionMetadata;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class InjectionMetadataTest {
    @Test
    public void testInjection() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v4.xml"));
        
        LinkedList<InjectionElement> injectionElements = new LinkedList<InjectionElement>();
        
        Class<PetStoreService> clazz = PetStoreService.class;
        
        {
            Field accountDao = clazz.getDeclaredField("accountDao");
            AutowireFieldElement element = new AutowireFieldElement(accountDao, true, factory);
            injectionElements.add(element);
        }
        
        {
            Field itemDao = clazz.getDeclaredField("itemDao");
            AutowireFieldElement element = new AutowireFieldElement(itemDao, true, factory);
            injectionElements.add(element);
        }
        
        InjectionMetadata metadata = new InjectionMetadata(clazz, injectionElements);
        PetStoreService service = new PetStoreService();
        
        metadata.inject(service);
        
        Assert.assertNotNull(service.getAccountDao());
        Assert.assertNotNull(service.getItemDao());
    }
}
