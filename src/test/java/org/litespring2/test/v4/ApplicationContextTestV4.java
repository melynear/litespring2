package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.context.ApplicationContext;
import org.litespring2.context.support.ClassPathXmlApplicationContext;
import org.litespring2.service.v4.PetStoreService;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class ApplicationContextTestV4 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");
        
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
