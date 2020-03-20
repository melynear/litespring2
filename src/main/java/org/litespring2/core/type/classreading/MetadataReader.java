package org.litespring2.core.type.classreading;

import org.litespring2.core.io.Resource;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.core.type.ClassMetadata;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public interface MetadataReader {
    Resource getResource();
    
    ClassMetadata getClassMetadata();
    
    AnnotationMetadata getAnnotationMetadata();
}
