package org.litespring2.stereotype;

import java.lang.annotation.*;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
