package org.litespring2.beans.factory.annotation;

import org.litespring2.beans.BeanDefinition;
import org.litespring2.core.type.AnnotationMetadata;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getMetadata();
}
