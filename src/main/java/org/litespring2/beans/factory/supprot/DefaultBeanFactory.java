package org.litespring2.beans.factory.supprot;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.PropertyValue;
import org.litespring2.beans.SimpleTypeConverter;
import org.litespring2.beans.TypeConverter;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.config.ConfigurableBeanFactory;
import org.litespring2.context.support.BeanDefinitionValueResolver;
import org.litespring2.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegsitry {
    private ClassLoader classLoader;
    
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    
    public Object getBean(String beanID) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanID);
        
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist.");
        }
        
        if (beanDefinition.isSingleton()) {
            Object bean = getSingleton(beanID);
            if (bean == null) {
                bean = createBean(beanDefinition);
                registerSingleton(beanID, bean);
            }
            
            return bean;
        }
        
        return createBean(beanDefinition);
    }
    
    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        
        // populateBean(beanDefinition, bean);
        
        populateBeanUseCommonBeanUtils(beanDefinition, bean);
        
        return bean;
    }
    
    /**
     * 实例化bean对象
     *
     * @param beanDefinition
     * @return
     */
    private Object instantiateBean(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        
        if (beanDefinition.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(beanDefinition);
        } else {
            try {
                Class<?> clazz = getClassLoader().loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("Create bean for " + beanClassName + "failed.", e);
            }
        }
    }
    
    /**
     * 填充bean的属性
     *
     * @param beanDefinition
     * @param bean
     */
    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        
        if (CollectionUtils.isEmpty(propertyValues)) {
            return;
        }
        
        TypeConverter converter = new SimpleTypeConverter();
        
        try {
            for (PropertyValue propertyValue : propertyValues) {
                String propertyName = propertyValue.getName();
                Object originValue = propertyValue.getValue();
                
                BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
                Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
                
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : propertyDescriptors) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
                
            }
        } catch (Exception e) {
            new BeanCreationException("Failed to obtain BeanInfo for class [" +
                    beanDefinition.getBeanClassName() + "]");
        }
    }
    
    /**
     * 使用commons-beanutils包提供的功能来设置属性值
     *
     * @param beanDefinition
     * @param bean
     */
    private void populateBeanUseCommonBeanUtils(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        
        if (CollectionUtils.isEmpty(propertyValues)) {
            return;
        }
        
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object originValue = propertyValue.getValue();
            
            Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
            try {
                BeanUtils.setProperty(bean, name, resolvedValue);
            } catch (Exception e) {
                new BeanCreationException("Failed to obtain BeanInfo for class [" +
                        beanDefinition.getBeanClassName() + "]");
            }
        }
    }
    
    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
    
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public BeanDefinition getBeanDefinition(String beanID) {
        return beanDefinitionMap.get(beanID);
    }
    
    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID, beanDefinition);
    }
}
