package eshop.spring.tr.dao;

import eshop.spring.tr.models.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemDaoInterface {
    public void addItem(String name, String itemInfo, int price)
            throws DaoException;

    public boolean deleteItem(int itemId) throws DaoException;

    public void changeItemsDetails(int itemId, String itemInfo, int price) throws DaoException;

    public List<Item> getAllItems() throws DaoException;
}
