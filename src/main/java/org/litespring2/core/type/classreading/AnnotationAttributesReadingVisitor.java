package org.litespring2.core.type.classreading;

import org.litespring2.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    private final String annotationType;
    private final Map<String, AnnotationAttributes> attributesMap;
    
    private AnnotationAttributes attributes = new AnnotationAttributes();
    
    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }
    
    @Override
    public void visit(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }
    
    @Override
    public void visitEnd() {
        attributesMap.put(annotationType, attributes);
    }
}
