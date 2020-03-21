package org.litespring2.context.annotation;

import org.litespring2.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring2.beans.factory.supprot.GenericBeanDefinition;
import org.litespring2.core.type.AnnotationMetadata;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;
    
    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(metadata.getClassName());
    }
    
    @Override
    public final AnnotationMetadata getMetadata() {
        return metadata;
    }
}
