package eshop.spring.tr.service.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.impl.ItemDao;
import eshop.spring.tr.models.Item;
import eshop.spring.tr.service.ItemServiceInterface;
import eshop.spring.tr.service.ServiceException;
import eshop.spring.tr.service.validation.impl.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService implements ItemServiceInterface {
    private final ItemDao itemDao;
    private final ItemValidator itemValidator;

    @Autowired
    public ItemService(ItemDao itemDao, ItemValidator itemValidator) {
        this.itemDao = itemDao;
        this.itemValidator = itemValidator;
    }

    /**
     *
     * Method of adding a product to the catalog
     *
     */
    @Override
    public void addItem(String name, String itemInfo, int price)
            throws ServiceException {

        if (!itemValidator.checkItemInfo(name, itemInfo, price)) {
            throw new ServiceException("Wrong item data");
        }


        try {
            itemDao.addItem(name, itemInfo, price);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     *
     * Method of removing an item from the catalog
     *
     */
    @Override
    public boolean deleteItem(int itemId) throws ServiceException {
        boolean itemDeleted;
        try {
            itemDeleted = itemDao.deleteItem(itemId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return itemDeleted;
    }

    /**
     *
     * Method of getting the entire catalog
     *
     */
    @Override
    public List<Item> getAllItems() throws ServiceException {
        List<Item> catalog = new ArrayList<Item>();
        try {
            catalog = itemDao.getAllItems();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return catalog;
    }

    /**
     *
     * Method of changing product data
     *
     */
    @Override
    public void changeItemsDetails(int itemId, String itemInfo, int price) throws ServiceException {

        if (!itemValidator.checkItemInfo(itemInfo, price)) {
            throw new ServiceException("Wrong item data");
        }


        try {
            itemDao.changeItemsDetails(itemId, itemInfo, price);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
