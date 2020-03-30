package org.litespring2.aop.config;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.litespring2.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring2.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring2.aop.aspectj.AspectJBeforeAdvice;
import org.litespring2.aop.aspectj.AspectJExpressionPointcut;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.ConstructorArgument.ValueHolder;
import org.litespring2.beans.PropertyValue;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.supprot.BeanDefinitionReaderUtils;
import org.litespring2.beans.factory.supprot.BeanDefinitionRegistry;
import org.litespring2.beans.factory.supprot.GenericBeanDefinition;

import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月29日
 */
public class ConfigBeanDefinitionParser {
    private static final String ASPECT = "aspect";
    private static final String ID = "id";
    private static final String REF = "ref";
    
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    
    private static final String EXPRESSION = "expression";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_REF = "pointcut-ref";
    
    public void parse(Element element, BeanDefinitionRegistry registry) {
        List<Element> childElements = element.elements();
        for (Element childElement : childElements) {
            String name = childElement.getName();
            
            if (ASPECT.equals(name)) {
                parseAspect(childElement, registry);
            }
        }
    }
    
    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry) {
        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);
        
        List<Element> childElements = aspectElement.elements();
        boolean adviceFoundAlready = false;
        
        for (int i = 0; i < childElements.size(); i++) {
            Element childElement = childElements.get(i);
            if (isAdviceNode(childElement)) {
                if (!adviceFoundAlready) {
                    adviceFoundAlready = true;
                    
                    if (StringUtils.isEmpty(aspectName)) {
                        return;
                    }
                }
                
                parseAdvice(aspectName, i, childElement, registry);
            }
        }
        
        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }
    }
    
    private boolean isAdviceNode(Element element) {
        String elementName = element.getName();
        
        return BEFORE.equals(elementName) || AFTER.equals(elementName) || AFTER_RETURNING_ELEMENT.equals(elementName) ||
                AFTER_THROWING_ELEMENT.equals(elementName) || AROUND.equals(elementName);
    }
    
    private void parseAdvice(String aspectName, int order, Element adviceElement, BeanDefinitionRegistry registry) {
        // create methodLocatingFactory bean definition
        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropertyValues().add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);
        
        // create aspectInstanceFactory bean definition
        GenericBeanDefinition aspectInstanceFactoryDefinition = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectInstanceFactoryDefinition.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectInstanceFactoryDefinition.setSynthetic(true);
        
        // create advice bean definition
        GenericBeanDefinition adviceBeanDefinition = createAdviceBeanDefinition(methodDefinition, aspectInstanceFactoryDefinition, adviceElement);
        
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceBeanDefinition, registry);
    }
    
    private GenericBeanDefinition createAdviceBeanDefinition(GenericBeanDefinition methodDefinition,
                                                             GenericBeanDefinition aspectInstanceFactoryDefinition,
                                                             Element adviceElement) {
        GenericBeanDefinition adviceBeanDefinition = new GenericBeanDefinition(getAdviceClass(adviceElement));
        adviceBeanDefinition.getConstructorArgument().addArgumentValue(new ValueHolder(methodDefinition));
        
        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition) {
            adviceBeanDefinition.getConstructorArgument().addArgumentValue(new ValueHolder(pointcut));
        } else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutReference = new RuntimeBeanReference((String) pointcut);
            adviceBeanDefinition.getConstructorArgument().addArgumentValue(new ValueHolder(pointcutReference));
        }
        
        adviceBeanDefinition.getConstructorArgument().addArgumentValue(new ValueHolder(aspectInstanceFactoryDefinition));
        
        adviceBeanDefinition.setSynthetic(true);
        
        return adviceBeanDefinition;
    }
    
    /**
     * Gets the advice implementation class corresponding to the supplied {@link Element}.
     */
    private Class<?> getAdviceClass(Element adviceElement) {
        String elementName = adviceElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        }
        /*else if (AFTER.equals(elementName)) {
            return AspectJAfterAdvice.class;
        }*/
        else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        } else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        }
        /*else if (AROUND.equals(elementName)) {
            return AspectJAroundAdvice.class;
        }*/
        else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }
    
    private Object parsePointcutProperty(Element element) {
        if ((element.attribute(POINTCUT) == null) && (element.attribute(POINTCUT_REF) == null)) {
            return null;
        } else if (element.attribute(POINTCUT) != null) {
            // Create a pointcut for the anonymous pc and register it.
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            
            return pointcutDefinition;
        } else if (element.attribute(POINTCUT_REF) != null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (StringUtils.isEmpty(pointcutRef)) {
                return null;
            }
            
            return pointcutRef;
        } else {
            return null;
        }
    }
    
    private GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition pointcutBeanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        pointcutBeanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        pointcutBeanDefinition.setSynthetic(true);
        
        pointcutBeanDefinition.getPropertyValues().add(new PropertyValue("expression", expression));
        
        return pointcutBeanDefinition;
    }
    
    private void parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);
        
        GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
        
        String pointcutBeanName = id;
        if (StringUtils.isNotEmpty(pointcutBeanName)) {
            registry.registerBeanDefinition(pointcutBeanName, pointcutDefinition);
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);
        }
    }
}
