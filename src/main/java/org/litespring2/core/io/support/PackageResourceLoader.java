package org.litespring2.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring2.core.io.FileSystemResource;
import org.litespring2.core.io.Resource;
import org.litespring2.utils.Assert;
import org.litespring2.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class PackageResourceLoader {
    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);
    
    private final ClassLoader classLoader;
    
    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }
    
    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ClassLoader must not be null");
        this.classLoader = classLoader;
    }
    
    public Resource[] getResources(String basePackage) {
        Assert.notNull(basePackage, "basePackage must not be null");
        
        String resourcePath = ClassUtils.convertClassNameToResourcePath(basePackage);
        
        URL resource = classLoader.getResource(resourcePath);
        
        File file = new File(resource.getFile());
        
        Set<File> matchingFiles = retrieveMatchingFiles(file);
        
        FileSystemResource[] result = new FileSystemResource[matchingFiles.size()];
        
        int i = 0;
        for (File matchingFile : matchingFiles) {
            result[i++] = new FileSystemResource(matchingFile);
        }
        
        return result;
    }
    
    private Set<File> retrieveMatchingFiles(File file) {
        if (!file.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + file.getAbsolutePath() + "] because it does not exist");
            }
            
            return Collections.emptySet();
        }
        
        if (!file.isDirectory()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Skipping [" + file.getAbsolutePath() + "] because it does not denote a directory");
            }
            
            return Collections.emptySet();
        }
        
        if (!file.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [" + file.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            }
            
            return Collections.emptySet();
        }
        
        Set<File> result = new LinkedHashSet<File>(8);
        
        doRetrieveMatchingFiles(file, result);
        return result;
    }
    
    private void doRetrieveMatchingFiles(File file, Set<File> result) {
        File[] contents = file.listFiles();
        
        if (contents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [" + file.getAbsolutePath() + "]");
            }
            
            return;
        }
        
        for (File content : contents) {
            if (content.isDirectory()) {
                if (content.canRead()) {
                    doRetrieveMatchingFiles(content, result);
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping subdirectory [" + content.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                }
            } else {
                result.add(content);
            }
        }
    }
}
