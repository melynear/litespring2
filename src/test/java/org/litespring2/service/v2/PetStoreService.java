package org.litespring2.service.v2;

import org.litespring2.dao.v2.AccountDao;
import org.litespring2.dao.v2.ItemDao;

/**
 * @author 种花家的兔子
 * @version v1.0
 * @date 2020年03月09日
 */
public class PetStoreService {
    private AccountDao accountDao;
    
    private ItemDao itemDao;
    
    private String petName;
    
    public AccountDao getAccountDao() {
        return accountDao;
    }
    
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
    
    public ItemDao getItemDao() {
        return itemDao;
    }
    
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    
    public void setPetName(String petName) {
        this.petName = petName;
    }
}
