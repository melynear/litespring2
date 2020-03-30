package org.litespring2.beans.factory.xml;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring2.aop.config.ConfigBeanDefinitionParser;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.ConstructorArgument.ValueHolder;
import org.litespring2.beans.PropertyValue;
import org.litespring2.beans.factory.BeanDefinitionStoreException;
import org.litespring2.beans.factory.config.RuntimeBeanReference;
import org.litespring2.beans.factory.config.TypedStringValue;
import org.litespring2.beans.factory.supprot.BeanDefinitionRegistry;
import org.litespring2.beans.factory.supprot.GenericBeanDefinition;
import org.litespring2.core.annotation.ClassPathBeanDefinitionScanner;
import org.litespring2.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月10日
 */
public class XmlBeanDefinitionReader {
    /**
     * <bean></bean>标签的id属性
     */
    private static final String ID_ATTRIBUTE = "id";
    
    /**
     * <bean></bean>标签的class属性
     */
    private static final String CLASS_ATTRIBUTE = "class";
    
    /**
     * <bean></bean>标签的scope属性
     */
    private static final String SCOPE_ATTRIBUTE = "scope";
    
    /**
     * <bean></bean>标签下的property标签
     */
    private static final String PROPERTY_ELEMENT = "property";
    
    /**
     * <property></property>标签的name属性
     */
    private static final String NAME_ATTRIBUTE = "name";
    
    /**
     * <property></property>标签的value属性
     */
    private static final String VALUE_ATTRIBUTE = "value";
    
    /**
     * <property></property>标签的ref属性
     */
    private static final String REF_ATTRIBUTE = "ref";
    
    /**
     * <bean></bean>标签下的constructor-arg标签
     */
    private static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    
    /**
     * <constructor-arg></constructor-arg>标签的type属性
     */
    private static final String TYPE_ATTRIBUTE = "type";
    
    private static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    
    private static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    
    private static final String AOP_NAMESPACE_URI = "http://www.springframework.org/schema/aop";
    
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    
    private BeanDefinitionRegistry regsitry;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    public XmlBeanDefinitionReader(BeanDefinitionRegistry regsitry) {
        this.regsitry = regsitry;
    }
    
    /**
     * 解析xml配置文件
     *
     * @param resource
     */
    public void loadBeanDefinition(Resource resource) {
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = null;
        
        try {
            inputStream = resource.getInputStream();
            Document document = saxReader.read(inputStream);
            
            // 根元素就是<beans>标签
            Element root = document.getRootElement();
            
            Iterator iterator = root.elementIterator();
            
            // 遍历<beans>标签下的每个标签
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String namespaceURI = element.getNamespaceURI();
                
                if (isDefaultNamespace(namespaceURI)) {
                    parseDefaultElement(element);
                } else if (isContextNamespace(namespaceURI)) {
                    parseComponentElement(element);
                } else if (isAOPNamespace(namespaceURI)) {
                    parseAOPElement(element);
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new BeanDefinitionStoreException("IOException parsing XML document", e);
                }
            }
        }
    }
    
    private void parseDefaultElement(Element element) {
        String id = element.attributeValue(ID_ATTRIBUTE);
        String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
        
        BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
        
        if (element.attribute(SCOPE_ATTRIBUTE) != null) {
            beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
        }
        
        parseConstructorArgElements(element, beanDefinition);
        parsePropertyElement(element, beanDefinition);
        
        regsitry.registerBeanDefinition(id, beanDefinition);
    }
    
    private void parseComponentElement(Element element) {
        String basePackage = element.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(regsitry);
        scanner.doScan(basePackage);
    }
    
    private void parseAOPElement(Element element) {
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(element, this.regsitry);
    }
    
    private boolean isDefaultNamespace(String namespaceURI) {
        return !StringUtils.isNotEmpty(namespaceURI) || BEANS_NAMESPACE_URI.equals(namespaceURI);
    }
    
    private boolean isContextNamespace(String namespaceURI) {
        return !StringUtils.isNotEmpty(namespaceURI) || CONTEXT_NAMESPACE_URI.equals(namespaceURI);
    }
    
    private boolean isAOPNamespace(String namespaceURI) {
        return !StringUtils.isNotEmpty(namespaceURI) || AOP_NAMESPACE_URI.equals(namespaceURI);
    }
    
    private void parseConstructorArgElements(Element beanElement, BeanDefinition beanDefinition) {
        Iterator iterator = beanElement.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element constructorArgElement = (Element) iterator.next();
            parseConstructorArgElement(constructorArgElement, beanDefinition);
        }
    }
    
    private void parseConstructorArgElement(Element constructorArgElement, BeanDefinition beanDefinition) {
        String type = constructorArgElement.attributeValue(TYPE_ATTRIBUTE);
        String name = constructorArgElement.attributeValue(NAME_ATTRIBUTE);
        
        Object value = parsePropertyValue(constructorArgElement, name);
        ValueHolder valueHolder = new ValueHolder(value);
        
        if (StringUtils.isNotEmpty(type)) {
            valueHolder.setType(type);
        }
        
        if (StringUtils.isNotEmpty(name)) {
            valueHolder.setType(name);
        }
        
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }
    
    private void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) {
        Iterator iterator = beanElement.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element propertyElement = (Element) iterator.next();
            String nameAttribute = propertyElement.attributeValue(NAME_ATTRIBUTE);
            if (StringUtils.isEmpty(nameAttribute)) {
                logger.fatal("Tag 'property' must have a 'name' attribute.");
                return;
            }
            
            Object object = parsePropertyValue(propertyElement, nameAttribute);
            PropertyValue propertyValue = new PropertyValue(nameAttribute, object);
            beanDefinition.getPropertyValues().add(propertyValue);
        }
    }
    
    private Object parsePropertyValue(Element propertyElement, String nameAttribute) {
        String elementName = nameAttribute != null ? "<property> element for property '" +
                nameAttribute + "'" : "<constructor-arg> element";
        
        if (propertyElement.attribute(VALUE_ATTRIBUTE) != null) {
            String attributeValue = propertyElement.attributeValue(VALUE_ATTRIBUTE);
            if (StringUtils.isEmpty(attributeValue)) {
                logger.error(elementName + "contains empty 'value' attribute.");
                throw new RuntimeException(elementName + "contains empty 'value' attribute.");
            }
            
            return new TypedStringValue(attributeValue);
        } else if (propertyElement.attribute(REF_ATTRIBUTE) != null) {
            String attributeValue = propertyElement.attributeValue(REF_ATTRIBUTE);
            if (StringUtils.isEmpty(attributeValue)) {
                logger.error(elementName + "contains empty 'ref' attribute.");
                throw new RuntimeException(elementName + "contains empty 'ref' attribute.");
            }
            
            return new RuntimeBeanReference(attributeValue);
        } else {
            throw new RuntimeException(elementName + "must specify a ref or value.");
        }
    }
}
