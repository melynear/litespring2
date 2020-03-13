package org.litespring2.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月13日
 */
public class ConstructorArgument {
    private final List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();
    
    public void addArgumentValue(ValueHolder valueHolder) {
        argumentValues.add(valueHolder);
    }
    
    public List<ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(argumentValues);
    }
    
    public int getArgumentCount() {
        return argumentValues.size();
    }
    
    public boolean isEmpty() {
        return argumentValues.isEmpty();
    }
    
    public void clear() {
        argumentValues.clear();
    }
    
    public static class ValueHolder {
        private String name;
        private String type;
        private Object value;
        
        public ValueHolder(Object value) {
            this.value = value;
        }
        
        public ValueHolder(String name, String type, Object value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public Object getValue() {
            return value;
        }
        
        public void setValue(Object value) {
            this.value = value;
        }
    }
}
