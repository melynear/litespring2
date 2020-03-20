package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.core.type.classreading.MetadataReader;
import org.litespring2.core.type.classreading.SimpleMetadataReader;
import org.litespring2.stereotype.Component;

import java.io.IOException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/litespring2/service/v4/PetStoreService.class");
        
        MetadataReader reader = new SimpleMetadataReader(resource);

//        reader.getClassMetadata();
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(annotationMetadata.hasAnnotation(annotation));
        AnnotationAttributes attributes = annotationMetadata.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
        
        Assert.assertFalse(annotationMetadata.isAbstract());
        Assert.assertFalse(annotationMetadata.isFinal());
        Assert.assertEquals("org.litespring2.service.v4.PetStoreService", annotationMetadata.getClassName());
    }
}
