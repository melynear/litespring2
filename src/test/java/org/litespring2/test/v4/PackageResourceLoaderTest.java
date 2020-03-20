package org.litespring2.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.core.io.Resource;
import org.litespring2.core.io.support.PackageResourceLoader;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring2.dao.v4");
        
        Assert.assertEquals(2, resources.length);
    }
}
