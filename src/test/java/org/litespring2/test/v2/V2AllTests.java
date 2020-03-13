package org.litespring2.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class, BeanDefinitionTestV2.class,
        CustomBooleanEditorTest.class, CustomNumberEditorTest.class, PropertyValueResolverTest.class,
        TypeConverterTest.class})
public class V2AllTests {
}
