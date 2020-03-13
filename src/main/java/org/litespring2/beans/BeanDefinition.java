package org.litespring2.beans;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    
    String SCOPE_PROTOTYPE = "prototype";
    
    String SCOPE_DEFAULT = "";
    
    boolean isSingleton();
    
    boolean isPrototype();
    
    String getScope();
    
    void setScope(String scope);
    
    String getBeanClassName();
    
    List<PropertyValue> getPropertyValues();
    
    ConstructorArgument getConstructorArgument();
}
