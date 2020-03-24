package org.litespring2.beans;

import org.litespring2.beans.propertyeditors.CustomBooleanEditor;
import org.litespring2.beans.propertyeditors.CustomNumberEditor;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class SimpleTypeConverter implements TypeConverter {
    private Map<Class<?>, PropertyEditor> defaultEditors;
    
    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType)
            throws TypeMismatchException {
        if (requiredType.isAssignableFrom(value.getClass())) {
            return (T) value;
        } else {
            if (value instanceof String) {
                PropertyEditor editor = findDefaultEditor(requiredType);
                
                try {
                    editor.setAsText((String) value);
                } catch (IllegalArgumentException e) {
                    throw new TypeMismatchException(value, requiredType);
                }
                
                return (T) editor.getValue();
            } else {
                throw new RuntimeException("Todo: cannot convert value :" + value.getClass());
            }
        }
    }
    
    private <T> PropertyEditor findDefaultEditor(Class<T> requiredType) {
        if (defaultEditors == null) {
            createDefaultEditors();
        }
        
        PropertyEditor propertyEditor = defaultEditors.get(requiredType);
        
        if (propertyEditor == null) {
            throw new RuntimeException("Editor for type:" + requiredType + "has not been implemented");
        }
        
        return propertyEditor;
    }
    
    private void createDefaultEditors() {
        defaultEditors = new HashMap<Class<?>, PropertyEditor>(64);
        defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, true));
        defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        defaultEditors.put(boolean.class, new CustomBooleanEditor(true));
        defaultEditors.put(Boolean.class, new CustomBooleanEditor(true));
    }
}
