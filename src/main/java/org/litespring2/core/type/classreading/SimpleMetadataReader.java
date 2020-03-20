package org.litespring2.core.type.classreading;

import org.litespring2.core.io.Resource;
import org.litespring2.core.type.AnnotationMetadata;
import org.litespring2.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class SimpleMetadataReader implements MetadataReader {
    private Resource resource;
    
    private ClassMetadata classMetadata;
    
    private AnnotationMetadata annotationMetadata;
    
    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        
        ClassReader reader;
        
        try {
            reader = new ClassReader(inputStream);
        } finally {
            inputStream.close();
        }
        
        AnnotationMetadataVisitor visitor = new AnnotationMetadataVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        
        this.resource = resource;
        this.classMetadata = visitor;
        this.annotationMetadata = visitor;
    }
    
    @Override
    public Resource getResource() {
        return resource;
    }
    
    @Override
    public ClassMetadata getClassMetadata() {
        return classMetadata;
    }
    
    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return annotationMetadata;
    }
}
