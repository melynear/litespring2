package org.litespring2.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月11日
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
    
    String getDescription();
}
