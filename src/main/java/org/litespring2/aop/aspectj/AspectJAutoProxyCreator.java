package org.litespring2.aop.aspectj;

import org.apache.commons.collections4.CollectionUtils;
import org.litespring2.aop.Advice;
import org.litespring2.aop.MethodMatcher;
import org.litespring2.aop.Pointcut;
import org.litespring2.aop.framework.AopConfigSupport;
import org.litespring2.aop.framework.AopProxyFactory;
import org.litespring2.aop.framework.CglibProxyFactory;
import org.litespring2.beans.BeansException;
import org.litespring2.beans.factory.config.BeanPostProcessor;
import org.litespring2.beans.factory.config.ConfigurableBeanFactory;
import org.litespring2.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月30日
 */
public class AspectJAutoProxyCreator implements BeanPostProcessor {
    private ConfigurableBeanFactory factory;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果这个bean是Advice的实现类，则不需要生成代理对象
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        
        List<Advice> candidateAdvices = getCandidateAdvices(bean);
        
        if (CollectionUtils.isEmpty(candidateAdvices)) {
            return bean;
        }
        
        return createProxy(bean, candidateAdvices);
    }
    
    private boolean isInfrastructureClass(Class<?> aClass) {
        return Advice.class.isAssignableFrom(aClass);
    }
    
    private List<Advice> getCandidateAdvices(Object bean) {
        List<Object> advices = this.factory.getBeansByType(Advice.class);
        
        List<Advice> result = new ArrayList<>();
        for (Object advice : advices) {
            Pointcut pointcut = ((Advice) advice).getPointcut();
            if (canApply(pointcut, bean.getClass())) {
                result.add((Advice) advice);
            }
        }
        
        return result;
    }
    
    private boolean canApply(Pointcut pointcut, Class<?> targetClass) {
        MethodMatcher methodMatcher = pointcut.getMethodMatcher();
        Set<Class> classes = ClassUtils.getAllInterfacesForClassAsSet(targetClass);
        classes.add(targetClass);
        
        for (Class clazz : classes) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (methodMatcher.matches(method)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private Object createProxy(Object bean, List<Advice> candidateAdvices) {
        AopConfigSupport configSupport = new AopConfigSupport();
        
        for (Advice advice : candidateAdvices) {
            configSupport.addAdvice(advice);
        }
        
        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface : targetInterfaces) {
            configSupport.addInterface(targetInterface);
        }
        
        configSupport.setTargetObject(bean);
        
        AopProxyFactory proxyFactory = null;
        if (configSupport.getProxiedInterfaces().length == 0) {
            proxyFactory = new CglibProxyFactory(configSupport);
        } else {
            // TODO JDK动态代理
            //proxyFactory = new JdkAopProxyFactory(config);
        }
        
        return proxyFactory.getProxy();
    }
    
    public void setBeanFactory(ConfigurableBeanFactory factory) {
        this.factory = factory;
    }
}
