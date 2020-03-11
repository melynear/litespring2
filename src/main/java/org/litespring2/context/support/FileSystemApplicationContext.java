package org.litespring2.context.support;

import org.litespring2.core.io.FileSystemResource;
import org.litespring2.core.io.Resource;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public class FileSystemApplicationContext extends AbstractApplicationContext {
    public FileSystemApplicationContext(String configFile) {
        super(configFile);
    }
    
    @Override
    public Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
