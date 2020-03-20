package org.litespring2.core.type.classreading;

import jdk.internal.org.objectweb.asm.Type;
import org.litespring2.core.annotation.AnnotationAttributes;
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
public class AnnotationMetadataVisitor extends ClassMetadataReadingVisitor {
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>();
    
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        
        annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, attributesMap);
    }
    
    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }
    
    public boolean hasAnnotation(String annotationType) {
        return annotationSet.contains(annotationType);
    }
    
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return attributesMap.get(annotationType);
    }
}
