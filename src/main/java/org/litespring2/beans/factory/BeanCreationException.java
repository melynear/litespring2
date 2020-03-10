package org.litespring2.beans.factory;

import org.litespring2.beans.BeansException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class BeanCreationException extends BeansException {
    private String beanName;
    
    public BeanCreationException(String message) {
        super(message);
    }
    
    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName = beanName;
    }
    
    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }
    
    public String getBeanName() {
        return this.beanName;
    }
}
