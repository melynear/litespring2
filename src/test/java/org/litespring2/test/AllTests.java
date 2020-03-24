package org.litespring2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring2.test.v1.V1AllTests;
import org.litespring2.test.v2.V2AllTests;
import org.litespring2.test.v3.V3AllTests;
import org.litespring2.test.v4.V4AllTests;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTests.class, V2AllTests.class, V3AllTests.class, V4AllTests.class,
        PathTest.class})
public class AllTests {
}
