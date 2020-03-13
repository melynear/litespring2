package org.litespring2.beans.propertyeditors;

import org.apache.commons.lang3.StringUtils;
import org.litespring2.utils.NumberUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class CustomNumberEditor extends PropertyEditorSupport {
    private final Class<? extends Number> numberClass;
    
    private final NumberFormat numberFormat;
    
    private final boolean allowEmpty;
    
    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }
    
    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        if (numberClass == null || !Number.class.isAssignableFrom(numberClass)) {
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
        } else if (numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, numberClass, numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, numberClass));
        }
    }
    
    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            super.setValue(NumberUtils.convertNumberToTargetClass((Number) value, numberClass));
        } else {
            super.setValue(value);
        }
    }
}
