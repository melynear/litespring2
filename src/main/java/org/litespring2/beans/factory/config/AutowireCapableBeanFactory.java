package org.litespring2.beans.factory.config;

import org.litespring2.beans.factory.BeanFactory;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
