package org.litespring2.core.type;

import org.litespring2.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public interface AnnotationMetadata extends ClassMetadata {
    Set<String> getAnnotationTypes();
    
    boolean hasAnnotation(String annotationType);
    
    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
