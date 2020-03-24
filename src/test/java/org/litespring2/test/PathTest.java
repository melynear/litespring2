package org.litespring2.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月24日
 */
public class PathTest {
    @Test
    public void testPath() throws IOException {
        // 默认情况下,java.io包中的类总是根据当前用户目录来分析相对路径名。
        // 此目录由系统属性user.dir指定,通常是Java虚拟机的调用目录,即在哪个目录下调用的jvm
        // 在ide中是在项目根路径中调用的jvm
        System.out.println(System.getProperty("user.dir"));
        
        // 在File中的相对路径都是相对于usr.dir的,因此会在项目根路径下创建文件
        File file = new File("a.txt");
        file.createNewFile();
        
        // 在当前类路径下查找资源
        System.out.println(Test.class.getResource(""));
        
        // 在classpath路径下查找资源
        System.out.println(Test.class.getResource("/"));
        
        // 在classpath路径下查找资源
        System.out.println(Test.class.getClassLoader().getResource(""));
        
        // 不支持这种写法
        System.out.println(Test.class.getClassLoader().getResource("/"));
    }
}
