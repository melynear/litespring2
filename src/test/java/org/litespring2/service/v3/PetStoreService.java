package org.litespring2.service.v3;

import org.litespring2.dao.v3.AccountDao;
import org.litespring2.dao.v3.ItemDao;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class PetStoreService {
    private AccountDao accountDao;
    
    private ItemDao itemDao;
    
    private int version;
    
    public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = -1;
    }
    
    public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }
}
