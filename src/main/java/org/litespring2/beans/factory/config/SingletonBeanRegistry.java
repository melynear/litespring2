package org.litespring2.beans.factory.config;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanID, Object object);
    
    Object getSingleton(String beanID);
}
