package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.context.ApplicationContext;
import org.litespring2.context.support.ClassPathXmlApplicationContext;
import org.litespring2.dao.v2.AccountDao;
import org.litespring2.dao.v2.ItemDao;
import org.litespring2.service.v2.PetStoreService;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) context.getBean("petStore");
        
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        
        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
        
        Assert.assertEquals("lizhi", petStore.getOwner());
    }
}
