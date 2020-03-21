package org.litespring2.core.annotation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanDefinitionStoreException;
import org.litespring2.beans.factory.supprot.BeanDefinitionRegistry;
import org.litespring2.beans.factory.supprot.BeanNameGenerator;
import org.litespring2.context.annotation.AnnotationBeanNameGenerator;
import org.litespring2.context.annotation.ScannedGenericBeanDefinition;
import org.litespring2.core.io.Resource;
import org.litespring2.core.io.support.PackageResourceLoader;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.core.type.classreading.MetadataReader;
import org.litespring2.core.type.classreading.SimpleMetadataReader;
import org.litespring2.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月21日
 */
public class ClassPathBeanDefinitionScanner {
    private final Log logger = LogFactory.getLog(getClass());
    
    private final BeanDefinitionRegistry registry;
    
    private PackageResourceLoader resourceLoader = new PackageResourceLoader();
    
    private BeanNameGenerator generator = new AnnotationBeanNameGenerator();
    
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }
    
    public Set<BeanDefinition> doScan(String packagesToScan) {
        String[] packages = StringUtils.split(packagesToScan, ",");
        
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        
        for (String basePackage : packages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            
            for (BeanDefinition beanDefinition : candidates) {
                registry.registerBeanDefinition(beanDefinition.getBeanID(), beanDefinition);
            }
            
            beanDefinitions.addAll(candidates);
        }
        
        return beanDefinitions;
    }
    
    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Resource[] resources = resourceLoader.getResources(basePackage);
        
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            for (Resource resource : resources) {
                
                MetadataReader reader = new SimpleMetadataReader(resource);
                AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                
                if (annotationMetadata.hasAnnotation(Component.class.getName())) {
                    ScannedGenericBeanDefinition beanDefinition = new ScannedGenericBeanDefinition(annotationMetadata);
                    
                    String beanName = generator.generateBeanName(beanDefinition, registry);
                    beanDefinition.setId(beanName);
                    candidates.add(beanDefinition);
                }
                
            }
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", e);
        }
        
        return candidates;
    }
}
