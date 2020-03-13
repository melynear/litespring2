package org.litespring2.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring2.beans.SimpleTypeConverter;
import org.litespring2.beans.TypeConverter;
import org.litespring2.beans.TypeMismatchException;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class TypeConverterTest {
    @Test
    public void typeConvertStringToIntTest() {
        TypeConverter typeConverter = new SimpleTypeConverter();
        
        Integer value = typeConverter.convertIfNecessary("3", Integer.class);
        
        Assert.assertTrue(value.intValue() == 3);
        
        try {
            typeConverter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        
        Assert.fail();
    }
    
    @Test
    public void typeConvertStringToBooleanTest() {
        TypeConverter typeConverter = new SimpleTypeConverter();
        
        Boolean value = typeConverter.convertIfNecessary("false", Boolean.class);
        
        Assert.assertTrue(value.booleanValue() == false);
        
        try {
            typeConverter.convertIfNecessary("xxx", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        
        Assert.fail();
    }
}
