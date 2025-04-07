package eshop.spring.tr.dao;

import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;
import eshop.spring.tr.service.ServiceException;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderDaoInterface {
    public void makeOrder(int userId, List<Item> cart) throws ServiceException, DaoException;

    public List<Order> getAllOrders(int userId) throws DaoException;
}
