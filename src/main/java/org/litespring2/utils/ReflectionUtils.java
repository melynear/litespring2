package org.litespring2.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public abstract class ReflectionUtils {
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
}
