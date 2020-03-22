package org.litespring2.beans.factory.annotation;

import org.litespring2.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月22日
 */
public abstract class InjectionElement {
    protected Member member;
    
    protected AutowireCapableBeanFactory factory;
    
    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }
    
    public abstract void inject(Object target);
}

