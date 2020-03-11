package org.litespring2.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring2.beans.BeanDefinition;
import org.litespring2.beans.factory.BeanDefinitionStoreException;
import org.litespring2.beans.factory.supprot.BeanDefinitionRegsitry;
import org.litespring2.beans.factory.supprot.GenericBeanDefinition;
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
    
    private BeanDefinitionRegsitry regsitry;
    
    public XmlBeanDefinitionReader(BeanDefinitionRegsitry regsitry) {
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
            
            // 遍历每个<bean>标签
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, beanClassName);
                regsitry.registerBeanDefinition(id, beanDefinition);
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
}
