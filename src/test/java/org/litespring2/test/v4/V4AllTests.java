package org.litespring2.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月23日
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({XmlBeanDefinitionReaderTest.class, PackageResourceLoaderTest.class, MetadataReaderTest.class,
        InjectionMetadataTest.class, DependencyDescriptorTest.class, ClassReaderTest.class,
        ClassPathBeanDefinitionScannerTest.class, AutowiredAnnotationProcessorTest.class,
        ApplicationContextTestV4.class})
public class V4AllTests {
}
