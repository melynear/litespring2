package org.litespring2.beans.factory.annotation;

import org.litespring2.beans.BeansException;
import org.litespring2.beans.factory.BeanCreationException;
import org.litespring2.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring2.beans.factory.supprot.DefaultBeanFactory;
import org.litespring2.core.annotation.AnnotationUtils;
import org.litespring2.stereotype.Autowired;
import org.litespring2.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
    private DefaultBeanFactory factory;
    
    private String requiredParameterName = "required";
    
    private boolean requiredParameterValue = true;
    
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();
    
    public AutowiredAnnotationProcessor() {
        autowiredAnnotationTypes.add(Autowired.class);
    }
    
    public void setBeanFactory(DefaultBeanFactory factory) {
        this.factory = factory;
    }
    
    public InjectionMetadata buildAutowiringMetadata(Class<?> targetClass) {
        LinkedList<InjectionElement> injectionElements = new LinkedList<>();
        do {
            LinkedList<InjectionElement> currentElements = new LinkedList<>();
            
            Field[] declaredFields = targetClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Annotation annotation = findAutowiredAnnotation(field);
                
                if (annotation != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    
                    boolean requiredStatus = determineRequiredStatus(annotation);
                    
                    currentElements.add(new AutowireFieldElement(field, requiredStatus, factory));
                }
            }
            
            for (Method method : targetClass.getDeclaredMethods()) {
                //TODO 处理方法注入
            }
            
            injectionElements.addAll(0, currentElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        
        return new InjectionMetadata(targetClass, injectionElements);
    }
    
    protected boolean determineRequiredStatus(Annotation annotation) {
        try {
            Method method = ReflectionUtils.findMethod(annotation.annotationType(), this.requiredParameterName);
            
            if (method == null) {
                return true;
            }
            
            return (boolean) ReflectionUtils.invokeMethod(method, annotation);
        } catch (Exception e) {
            return true;
        }
    }
    
    private Annotation findAutowiredAnnotation(AccessibleObject accessibleObject) {
        for (Class<? extends Annotation> type : autowiredAnnotationTypes) {
            Annotation annotation = AnnotationUtils.getAnnotation(accessibleObject, type);
            
            if (annotation != null) {
                return annotation;
            }
        }
        
        return null;
    }
    
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }
    
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
    
    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata injectionMetadata = buildAutowiringMetadata(bean.getClass());
        
        try {
            injectionMetadata.inject(bean);
        } catch (Exception e) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", e);
        }
    }
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
