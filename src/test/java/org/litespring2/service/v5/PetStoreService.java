package org.litespring2.service.v5;

import org.litespring2.dao.v5.AccountDao;
import org.litespring2.dao.v5.ItemDao;
import org.litespring2.stereotype.Autowired;
import org.litespring2.stereotype.Component;
import org.litespring2.util.MessageTracker;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private ItemDao itemDao;
    
    public AccountDao getAccountDao() {
        return accountDao;
    }
    
    public ItemDao getItemDao() {
        return itemDao;
    }
    
    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }
}
