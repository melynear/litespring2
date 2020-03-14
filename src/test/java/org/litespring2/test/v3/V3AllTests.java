package org.litespring2.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月14日
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV3.class, BeanDefinitionTestV3.class,
        ConstructorResolverTest.class})
public class V3AllTests {
}
