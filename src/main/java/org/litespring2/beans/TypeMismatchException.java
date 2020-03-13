package org.litespring2.beans;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class TypeMismatchException extends BeansException {
    private transient Object value;
    private Class<?> requiredType;
    
    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value:" + value + "to type" + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }
    
    public Object getValue() {
        return value;
    }
    
    public Class<?> getRequiredType() {
        return requiredType;
    }
}
