package eshop.spring.tr.service;

import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;

import java.util.List;

public interface OrderServiceInterface {
    public boolean makeOrder(int userId, List<Item> cart) throws ServiceException;

    public List<Item> addItemToCart(List<Item> cart, int itemId, String name, String itemInfo,
                                    int price);

    public List<Item> deleteItemFromCart(List<Item> cart, int itemId);

    public List<Order> getAllOrders(int userId) throws ServiceException;
}
