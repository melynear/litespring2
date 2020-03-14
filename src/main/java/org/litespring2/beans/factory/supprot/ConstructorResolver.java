package org.litespring2.beans.factory.supprot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring2.beans.*;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.config.ConfigurableBeanFactory;
import org.litespring2.context.support.BeanDefinitionValueResolver;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月14日
 */
public class ConstructorResolver {
    private final Log logger = LogFactory.getLog(getClass());
    
    private ConfigurableBeanFactory factory;
    
    public ConstructorResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }
    
    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = null;
        
        try {
            beanClass = factory.getClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getBeanID(),
                    "Instantiation of bean failed, can't resolve class", e);
        }
        
        Constructor<?>[] candidates = beanClass.getConstructors();
        ConstructorArgument argument = beanDefinition.getConstructorArgument();
        
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        TypeConverter converter = new SimpleTypeConverter();
        
        for (Constructor candidate : candidates) {
            Class[] parameterTypes = candidate.getParameterTypes();
            
            if (parameterTypes.length != argument.getArgumentCount()) {
                continue;
            }
            
            argsToUse = new Object[parameterTypes.length];
            
            boolean result = valuesMatchTypes(parameterTypes, argument.getArgumentValues(), argsToUse,
                    resolver, converter);
            
            if (result) {
                constructorToUse = candidate;
                break;
            }
        }
        
        if (constructorToUse == null) {
            throw new BeanCreationException(beanDefinition.getBeanID(), "Con not find a appropriate constructor.");
        }
        
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getBeanID(),
                    "Con not find a create instance using" + constructorToUse);
        }
    }
    
    private boolean valuesMatchTypes(Class[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues,
                                     Object[] argsToUse, BeanDefinitionValueResolver resolver,
                                     TypeConverter converter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            
            Object originValue = valueHolder.getValue();
            Object resolvedValue = resolver.resolveValueIfNecessary(originValue);
            
            try {
                Object convertedValue = converter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                
                argsToUse[i] = convertedValue;
            } catch (TypeMismatchException e) {
                logger.error(e);
                return false;
            }
        }
        
        return true;
    }
}
