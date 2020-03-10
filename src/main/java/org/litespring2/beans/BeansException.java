package org.litespring2.beans;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }
    
    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
