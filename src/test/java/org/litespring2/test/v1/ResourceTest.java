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
        // ClassLoader查找资源时，是从classpath路径下查找的，不支持以/开头
        // Class查找资源时，如果以/开头，则从classpath路径下查找，否则从当前类所在的包下查找
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
        // 在File中的相对路径都是相对于usr.dir的,
        // usr.dir指定的是jvm的调用目录,因此会在项目根路径下查找文件
        Resource resource = new FileSystemResource("src\\test\\resources\\petstore-v1.xml");
        
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
