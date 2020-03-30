package org.litespring2.test;

import org.junit.Test;
import org.litespring2.service.v5.PetStoreService;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月30日
 */
public class PropertyDescriptorTest {
    @Test
    public void test() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(PetStoreService.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            // name先从get方法上取，再从set方法上取，如果方法名称不以get或者set开头则取不到对应的属性名称；
            // name和属性本身定义的名称无关
            String name = pd.getName();
            System.out.println(name);
        }
    }
}
