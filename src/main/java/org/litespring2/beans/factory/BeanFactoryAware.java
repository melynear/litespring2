package org.litespring2.beans.factory;

import org.litespring2.beans.BeansException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月28日
 */
public interface BeanFactoryAware {
    /**
     * Callback that supplies the owning factory to a bean instance.
     * <p>Invoked after the population of normal bean properties
     * but before an initialization callback such as
     * or a custom init-method.
     *
     * @param beanFactory owning BeanFactory (never {@code null}).
     *                    The bean can immediately call methods on the factory.
     * @throws BeansException in case of initialization errors
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
