package org.litespring2.beans.factory.supprot;

import org.litespring2.beans.BeanDefinition;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    
    private String beanClassName;
    
    private boolean singleton = true;
    
    private boolean prototype = false;
    
    private String scope = SCOPE_DEFAULT;
    
    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }
    
    public String getBeanClassName() {
        return this.beanClassName;
    }
    
    public boolean isSingleton() {
        return singleton;
    }
    
    public boolean isPrototype() {
        return prototype;
    }
    
    public String getScope() {
        return scope;
    }
    
    public void setScope(String scope) {
        this.scope = scope;
        singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        prototype = SCOPE_PROTOTYPE.equals(scope);
    }
}
