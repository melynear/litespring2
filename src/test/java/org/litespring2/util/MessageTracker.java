package org.litespring2.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class MessageTracker {
    private static List<String> messages = new LinkedList<>();
    
    public static void addMsg(String message) {
        messages.add(message);
    }
    
    public static void clearMsgs() {
        messages.clear();
    }
    
    public static List<String> getMsgs() {
        return messages;
    }
}
