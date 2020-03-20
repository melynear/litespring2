package org.litespring2.stereotype;

import java.lang.annotation.*;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月14日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
