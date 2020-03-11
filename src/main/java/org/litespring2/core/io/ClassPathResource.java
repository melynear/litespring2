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
    
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = classLoader.getResourceAsStream(path);
        
        if (inputStream == null) {
            throw new FileNotFoundException(path + " can not be opened.");
        }
        
        return inputStream;
    }
    
    public String getDescription() {
        return this.path;
    }
}
