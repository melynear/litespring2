package org.litespring2.beans.factory.config;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class RuntimeBeanReference {
    private String beanName;
    
    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }
    
    public String getBeanName() {
        return beanName;
    }
}
