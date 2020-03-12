package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.factory.config.SingletonBeanRegistry;
import org.litespring2.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();
    
    public void registerSingleton(String beanName, Object object) {
        Assert.notNull(beanName, "beanName must not be null.");
        
        Object singleton = singletonObjects.get(beanName);
        
        if (singleton != null) {
            throw new IllegalStateException("Could not register object [" + object + "] under bean name:" +
                    beanName + ", there is already object [" + singleton + "]");
        }
        
        singletonObjects.put(beanName, object);
    }
    
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }
}
