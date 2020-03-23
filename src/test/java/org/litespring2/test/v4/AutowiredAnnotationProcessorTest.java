package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.factory.annotation.AutowireFieldElement;
import org.litespring2.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring2.beans.factory.annotation.InjectionElement;
import org.litespring2.beans.factory.annotation.InjectionMetadata;
import org.litespring2.beans.factory.config.DependencyDescriptor;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.dao.v4.AccountDao;
import org.litespring2.dao.v4.ItemDao;
import org.litespring2.service.v4.PetStoreService;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class AutowiredAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    
    DefaultBeanFactory factory = new DefaultBeanFactory() {
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class)) {
                return accountDao;
            }
            if (descriptor.getDependencyType().equals(ItemDao.class)) {
                return itemDao;
            }
            
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };
    
    @Test
    public void testGetInjectionMetadata() {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> injectionElements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, injectionElements.size());
        assertFieldExists(injectionElements, "accountDao");
        assertFieldExists(injectionElements, "itemDao");
        
        PetStoreService service = new PetStoreService();
        injectionMetadata.inject(service);
        Assert.assertNotNull(service.getAccountDao());
        Assert.assertNotNull(service.getItemDao());
    }
    
    private void assertFieldExists(List<InjectionElement> injectionElements, String fieldName) {
        for (InjectionElement injectionElement : injectionElements) {
            AutowireFieldElement fieldElement = (AutowireFieldElement) injectionElement;
            if (fieldElement.getField().getName().equals(fieldName)) {
                return;
            }
        }
        
        Assert.fail(fieldName + "does not exist!");
    }
}
