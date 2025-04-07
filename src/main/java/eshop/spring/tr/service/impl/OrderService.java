package eshop.spring.tr.service.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.impl.OrderDao;
import eshop.spring.tr.dao.impl.UserDao;
import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;
import eshop.spring.tr.service.OrderServiceInterface;
import eshop.spring.tr.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {
    private final OrderDao orderDao;
    private final UserDao userDao;

    @Autowired
    public OrderService(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    /**
     *
     * Method of placing an order
     *
     */
    @Override
    public boolean makeOrder(int userId, List<Item> cart) throws ServiceException {

        try {
            if (!userDao.checkUserStatus(userId)) {
                return false;
            }
            orderDao.makeOrder(userId, cart);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    /**
     *
     * Method of adding products to the cart
     *
     */
    @Override
    public List<Item> addItemToCart(List<Item> cart, int itemId, String name, String itemInfo,
                                    int price) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setName(name);
        item.setItemInfo(itemInfo);
        item.setPrice(price);
        cart.add(item);
        return cart;
    }

    /**
     *
     * Method of removing items from the cart
     *
     */
    @Override
    public List<Item> deleteItemFromCart(List<Item> cart, int itemId) {
        Iterator<Item> cartIterator = cart.iterator();
        while (cartIterator.hasNext()) {
            Item item = new Item();
            item = cartIterator.next();
            if (item.getItemId() == itemId) {
                cartIterator.remove();
            }
        }
        return cart;
    }

    /**
     *
     * Method of receiving all orders
     *
     */
    @Override
    public List<Order> getAllOrders(int userId) throws ServiceException {
        List<Order> orders = new ArrayList<Order>();

        try {
            orders = orderDao.getAllOrders(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return orders;
    }
}
