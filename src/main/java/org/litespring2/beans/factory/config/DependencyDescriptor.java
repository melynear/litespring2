package org.litespring2.beans.factory.config;

import org.litespring2.utils.Assert;

import java.lang.reflect.Field;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class DependencyDescriptor {
    private Field field;
    
    private boolean required;
    
    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Filed must not be null");
        this.field = field;
        this.required = required;
    }
    
    public Class<?> getDependencyType() {
        if (this.field != null) {
            return this.field.getType();
        }
        
        throw new RuntimeException("only support field dependency");
    }
    
    public boolean isRequired() {
        return isRequired();
    }
}
