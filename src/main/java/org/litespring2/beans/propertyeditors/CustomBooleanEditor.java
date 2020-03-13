package org.litespring2.beans.propertyeditors;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class CustomBooleanEditor extends PropertyEditorSupport {
    private static final String VALUE_TRUE = "true";
    private static final String VALUE_FALSE = "false";
    private static final String VALUE_ON = "on";
    private static final String VALUE_OFF = "off";
    private static final String VALUE_YES = "yes";
    private static final String VALUE_NO = "no";
    private static final String VALUE_1 = "1";
    private static final String VALUE_0 = "0";
    
    private final boolean allowEmpty;
    
    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
        } else if (VALUE_TRUE.equalsIgnoreCase(text) || VALUE_ON.equalsIgnoreCase(text) ||
                VALUE_YES.equalsIgnoreCase(text) || VALUE_1.equalsIgnoreCase(text)) {
            setValue(Boolean.TRUE);
        } else if (VALUE_FALSE.equalsIgnoreCase(text) || VALUE_OFF.equalsIgnoreCase(text) ||
                VALUE_NO.equalsIgnoreCase(text) || VALUE_0.equalsIgnoreCase(text)) {
            setValue(Boolean.FALSE);
        } else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }
    
    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }
    
    @Override
    public Object getValue() {
        return super.getValue();
    }
}
