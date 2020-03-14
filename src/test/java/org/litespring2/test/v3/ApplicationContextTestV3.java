package org.litespring2.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.context.ApplicationContext;
import org.litespring2.context.support.ClassPathXmlApplicationContext;
import org.litespring2.service.v3.PetStoreService;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月14日
 */
public class ApplicationContextTestV3 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
        
        Assert.assertEquals(1, petStore.getVersion());
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
