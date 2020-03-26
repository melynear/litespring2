package org.litespring2.tx;

import org.litespring2.util.MessageTracker;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月26日
 */
public class TransactionManager {
    public void start() {
        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }
    
    public void commit() {
        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }
    
    public void rollback() {
        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}
