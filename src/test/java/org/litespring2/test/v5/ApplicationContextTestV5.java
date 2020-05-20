package org.litespring2.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring2.context.ApplicationContext;
import org.litespring2.context.support.ClassPathXmlApplicationContext;
import org.litespring2.service.v5.PetStoreService;
import org.litespring2.util.MessageTracker;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class ApplicationContextTestV5 {
    @Before
    public void setUp() {
        MessageTracker.clearMsgs();
    }
    
    @Test
    public void testPlaceOrder() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v5.xml");
        
        PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
        
        petStore.placeOrder();
        List<String> msgs = MessageTracker.getMsgs();
        
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
}
