package org.litespring2.beans.factory.config;

import org.litespring2.beans.factory.BeanFactory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    ClassLoader getClassLoader();
    
    void setClassLoader(ClassLoader classLoader);
}
