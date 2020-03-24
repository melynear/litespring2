package org.litespring2.core.io;

import org.litespring2.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class ClassPathResource implements Resource {
    private String path;
    
    private ClassLoader classLoader;
    
    public ClassPathResource(String path) {
        this(path, null);
    }
    
    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        // ClassLoader查找资源时，是从classpath路径下查找的，不支持以/开头
        // Class查找资源时，如果以/开头，则从classpath路径下查找，否则从当前类所在的包下查找
        InputStream inputStream = classLoader.getResourceAsStream(path);
        
        if (inputStream == null) {
            throw new FileNotFoundException(path + " can not be opened.");
        }
        
        return inputStream;
    }
    
    @Override
    public String getDescription() {
        return this.path;
    }
}
