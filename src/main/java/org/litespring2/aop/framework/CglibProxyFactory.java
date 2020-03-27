package org.litespring2.aop.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring2.aop.Advice;
import org.litespring2.utils.Assert;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月27日
 */
public class CglibProxyFactory implements AopProxyFactory {
    /**
     * Constants for CGLIB callback array indices
     */
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;
    
    protected final AopConfig config;
    protected static final Log logger = LogFactory.getLog(CglibProxyFactory.class);
    
    
    public CglibProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be null");
        
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        
        this.config = config;
    }
    
    @Override
    public Object getProxy() {
        return getProxy(null);
    }
    
    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }
        
        Class<?> targetClass = this.config.getTargetClass();
        
        Enhancer enhancer = new Enhancer();
        if (classLoader != null) {
            enhancer.setClassLoader(classLoader);
        }
        
        enhancer.setSuperclass(targetClass);
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setInterceptDuringConstruction(false);
        
        Callback[] callbacks = getCallbacks(targetClass);
        Class<?>[] types = new Class[callbacks.length];
        for (int i = 0; i < callbacks.length; i++) {
            types[i] = callbacks[i].getClass();
        }
        
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);
        
        enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
        
        Object proxy = enhancer.create();
        
        return proxy;
    }
    
    private Callback[] getCallbacks(Class<?> targetClass) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);
        
        return new Callback[]{aopInterceptor};
    }
    
    private static class ProxyCallbackFilter implements CallbackFilter {
        private final AopConfig config;
        
        public ProxyCallbackFilter(AopConfig config) {
            this.config = config;
        }
        
        @Override
        public int accept(Method method) {
            return AOP_PROXY;
        }
    }
    
    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
        private final AopConfig config;
        
        public DynamicAdvisedInterceptor(AopConfig config) {
            this.config = config;
        }
        
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            List<Advice> chain = config.getAdvices(method);
            Object targetObject = config.getTargetObject();
            
            Object result = null;
            
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                methodProxy.invoke(targetObject, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<>(chain.size());
                interceptors.addAll(chain);
                
                ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(targetObject, method, args, interceptors);
                result = mi.proceed();
            }
            
            return result;
        }
    }
}
