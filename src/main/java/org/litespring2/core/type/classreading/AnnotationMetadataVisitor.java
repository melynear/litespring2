package org.litespring2.core.type.classreading;

import jdk.internal.org.objectweb.asm.Type;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class AnnotationMetadataVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>();
    
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        
        annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, attributesMap);
    }
    
    @Override
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }
    
    @Override
    public boolean hasAnnotation(String annotationType) {
        return annotationSet.contains(annotationType);
    }
    
    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return attributesMap.get(annotationType);
    }
}
