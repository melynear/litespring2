package org.litespring2.beans.factory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public interface BeanFactory {
    Object getBean(String beanID);
}
