package org.litespring2.context.annotation;

import org.apache.commons.lang3.StringUtils;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring2.beans.factory.supprot.BeanDefinitionRegistry;
import org.litespring2.beans.factory.supprot.BeanNameGenerator;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.utils.ClassUtils;

import java.beans.Introspector;
import java.util.Map;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    private static final String COMPONENT_ANNOTATION_CLASSNAME = "org.litespring2.stereotype.Component";
    
    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) beanDefinition, registry);
            if (StringUtils.isNotEmpty(beanName)) {
                return beanName;
            }
        }
        
        return buildDefaultBeanName(beanDefinition, registry);
    }
    
    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        String beanName = null;
        
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        Set<String> annotationTypes = metadata.getAnnotationTypes();
        for (String annotationType : annotationTypes) {
            AnnotationAttributes annotationAttributes = metadata.getAnnotationAttributes(annotationType);
            if (isStereotypeWithNameValue(annotationType, annotationAttributes)) {
                Object value = annotationAttributes.get("value");
                if (value instanceof String) {
                    String strValue = (String) value;
                    if (StringUtils.isNotEmpty(strValue)) {
                        beanName = strValue;
                    }
                }
            }
        }
        
        return beanName;
    }
    
    protected boolean isStereotypeWithNameValue(String annotationType, Map<String, Object> attributes) {
        boolean isStereotype = COMPONENT_ANNOTATION_CLASSNAME.equals(annotationType);
        return isStereotype && attributes != null && attributes.containsKey("value");
    }
    
    protected String buildDefaultBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(beanDefinition);
    }
    
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
