package org.litespring2.beans.factory.config;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月12日
 */
public class TypedStringValue {
    private String value;
    
    public TypedStringValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
