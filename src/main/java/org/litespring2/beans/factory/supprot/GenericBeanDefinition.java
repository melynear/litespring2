package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.ConstructorArgument;
import org.litespring2.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    
    private String beanClassName;
    
    private Class<?> beanClass;
    
    private boolean singleton = true;
    
    private boolean prototype = false;
    
    private String scope = SCOPE_DEFAULT;
    
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
    
    private ConstructorArgument constructorArgument = new ConstructorArgument();
    
    public GenericBeanDefinition() {
    
    }
    
    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getBeanID() {
        return id;
    }
    
    public void setBeanClassName(String className) {
        this.beanClassName = className;
    }
    
    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
    
    @Override
    public boolean isSingleton() {
        return singleton;
    }
    
    @Override
    public boolean isPrototype() {
        return prototype;
    }
    
    @Override
    public String getScope() {
        return scope;
    }
    
    @Override
    public void setScope(String scope) {
        this.scope = scope;
        singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        prototype = SCOPE_PROTOTYPE.equals(scope);
    }
    
    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }
    
    @Override
    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }
    
    @Override
    public boolean hasConstructorArgumentValues() {
        return !constructorArgument.isEmpty();
    }
    
    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }
    
    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        if (this.beanClass == null) {
            throw new IllegalStateException("Bean class name [" + this.getBeanClassName() +
                    "] has not been resolved into an actual Class");
        }
        
        return this.beanClass;
    }
    
    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        if (beanClassName == null) {
            return null;
        }
        
        Class<?> clazz = classLoader.loadClass(beanClassName);
        
        this.beanClass = clazz;
        return clazz;
    }
}