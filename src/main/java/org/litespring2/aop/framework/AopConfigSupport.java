package org.litespring2.aop.framework;

import org.litespring2.aop.Advice;
import org.litespring2.aop.Pointcut;
import org.litespring2.utils.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class AopConfigSupport implements AopConfig {
    private boolean proxyTargetClass = false;
    
    private Object targetObject;
    
    private List<Advice> advices = new ArrayList<>();
    
    private List<Class> interfaces = new ArrayList<>();
    
    @Override
    public Object getTargetObject() {
        return this.targetObject;
    }
    
    @Override
    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }
    
    @Override
    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }
    
    @Override
    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }
    
    @Override
    public List<Advice> getAdvices() {
        return this.advices;
    }
    
    @Override
    public List<Advice> getAdvices(Method method) {
        List<Advice> result = new ArrayList<Advice>();
        for (Advice advice : this.getAdvices()) {
            Pointcut pc = advice.getPointcut();
            if (pc.getMethodMatcher().matches(method)) {
                result.add(advice);
            }
        }
        return result;
    }
    
    @Override
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }
    
    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }
    
    public void addInterface(Class<?> intf) {
        Assert.notNull(intf, "Interface must not be null");
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);
        }
    }
    
    @Override
    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[interfaces.size()]);
    }
    
    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) {
                return true;
            }
        }
        return false;
    }
}
