package org.litespring2.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.context.ApplicationContext;
import org.litespring2.context.support.ClassPathXmlApplicationContext;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class ApplicationContextTest {
    @Test
    public void testGetBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
        Assert.assertNotNull(applicationContext.getBean("petStore"));
    }
}
