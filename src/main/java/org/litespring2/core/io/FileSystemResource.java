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
    
    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }
    
    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null.");
        this.path = path;
    
        // 在File中的相对路径都是相对于usr.dir的,
        // usr.dir指定的是jvm的调用目录,因此会在项目根路径下查找文件
        this.file = new File(path);
    }
    
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
    
    @Override
    public String getDescription() {
        return "File [" + file.getAbsolutePath() + "]";
    }
}
