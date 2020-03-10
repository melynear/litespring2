package org.litespring2.beans.factory;

import org.litespring2.beans.BeansException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String message) {
        super(message);
    }
    
    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
