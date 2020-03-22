package org.litespring2.beans.factory.annotation;

import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public class InjectionMetadata {
    private Class<?> targetClass;
    private LinkedList<InjectionElement> injectionElements;
    
    public InjectionMetadata(Class<?> targetClass, LinkedList<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }
    
    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }
    
    public void inject(Object target) {
        if (CollectionUtils.isEmpty(injectionElements)) {
            return;
        }
        
        for (InjectionElement element : injectionElements) {
            element.inject(target);
        }
    }
}
