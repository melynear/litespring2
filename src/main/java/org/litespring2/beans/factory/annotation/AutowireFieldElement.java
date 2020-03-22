package org.litespring2.beans.factory.annotation;

import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring2.beans.factory.config.DependencyDescriptor;
import org.litespring2.utils.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class AutowireFieldElement extends InjectionElement {
    private boolean required;
    
    public AutowireFieldElement(Field field, boolean required, AutowireCapableBeanFactory factory) {
        super(field, factory);
        this.required = required;
    }
    
    @Override
    public void inject(Object target) {
        Field field = getField();
        DependencyDescriptor descriptor = new DependencyDescriptor(field, required);
        Object resolveDependency = factory.resolveDependency(descriptor);
        
        if (resolveDependency != null) {
            ReflectionUtils.makeAccessible(field);
            try {
                field.set(target, resolveDependency);
            } catch (IllegalAccessException e) {
                throw new BeanCreationException("Could not autowire field: " + field, e);
            }
        }
    }
    
    private Field getField() {
        return (Field) member;
    }
}
