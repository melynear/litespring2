package org.litespring2.beans;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType)
            throws TypeMismatchException;
}
