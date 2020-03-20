package org.litespring2.core.type;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月20日
 */
public interface ClassMetadata {
    String getClassName();
    
    boolean isInterface();
    
    boolean isAbstract();
    
    boolean isFinal();
    
    boolean hasSuperClass();
    
    String getSuperClassName();
    
    String[] getInterfaceNames();
}
