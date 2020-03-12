package org.litespring2.beans;

/**
 * <bean>
 * <property name="" value=""></property>
 * <property name="" ref=""></property>
 * </bean>
 *
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class PropertyValue {
    private final String name;
    
    private final Object value;
    
    private boolean converted = false;
    
    private Object convertedValue;
    
    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public Object getValue() {
        return value;
    }
    
    public synchronized boolean isConverted() {
        return converted;
    }
    
    public synchronized Object getConvertedValue() {
        return convertedValue;
    }
}
