package eshop.spring.tr.service;

import eshop.spring.tr.models.Item;

import java.util.List;

public interface ItemServiceInterface {
    public void addItem(String name, String itemInfo, int price)
            throws ServiceException;

    public boolean deleteItem(int itemId) throws ServiceException;

    public void changeItemsDetails(int itemId, String itemInfo, int price) throws ServiceException;

    public List<Item> getAllItems() throws ServiceException;
}
