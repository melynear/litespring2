package org.litespring2.beans.factory.config;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    ClassLoader getClassLoader();
    
    void setClassLoader(ClassLoader classLoader);
}
