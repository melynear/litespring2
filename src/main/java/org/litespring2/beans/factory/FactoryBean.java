package org.litespring2.beans.factory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月28日
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
    
    Class<T> getObjectType();
}
