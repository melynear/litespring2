package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.core.annotation.AnnotationAttributes;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.type.classreading.AnnotationMetadataVisitor;
import org.litespring2.core.type.classreading.ClassMetadataReadingVisitor;
import org.litespring2.stereotype.Component;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class ClassReaderTest {
    @Test
    public void testGetClassMetadate() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/litespring2/service/v4/PetStoreService.class");
        
        ClassReader reader = new ClassReader(resource.getInputStream());
        
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.litespring2.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }
    
    @Test
    public void testGetAnnotation() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/litespring2/service/v4/PetStoreService.class");
        
        ClassReader reader = new ClassReader(resource.getInputStream());
        
        AnnotationMetadataVisitor visitor = new AnnotationMetadataVisitor();
        
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        
        String annotation = Component.class.getName();
        Assert.assertTrue(visitor.hasAnnotation(annotation));
        
        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
    }
}
