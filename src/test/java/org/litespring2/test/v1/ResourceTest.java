package org.litespring2.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.core.io.ClassPathResource;
import org.litespring2.core.io.FileSystemResource;
import org.litespring2.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class ResourceTest {
    @Test
    public void testClassPathResource() throws IOException {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        InputStream is = null;
        
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    @Test
    public void testFileSystemResource() throws IOException {
        Resource resource = new FileSystemResource("D:\\developdata\\IdeaProjects\\litespring2\\src\\test\\resources\\petstore-v1.xml");
        
        InputStream is = null;
        
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
