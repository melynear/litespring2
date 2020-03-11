package org.litespring2.core.io;

import org.litespring2.utils.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class FileSystemResource implements Resource {
    private String path;
    
    private File file;
    
    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null.");
        this.path = path;
        this.file = new File(path);
    }
    
    
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
    
    public String getDescription() {
        return "File [" + file.getAbsolutePath() + "]";
    }
}
