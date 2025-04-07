package eshop.spring.tr.dao.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.ItemDaoInterface;
import eshop.spring.tr.models.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao implements ItemDaoInterface {
    private final SessionFactory sessionFactory;

    private static final String GET_ITEMS_FROM_ORDERS_QUERY =
            "SELECT items_id FROM orders_has_items;";
    private static final String GET_ALL_ITEMS_QUERY = "SELECT * FROM items";
    private static final int ITEM_ID_COLUMN_INDEX = 1;
    private static final int ITEM_NAME_COLUMN_INDEX = 2;
    private static final int ITEM_INFO_COLUMN_INDEX = 3;
    private static final int ITEM_PRICE_COLUMN_INDEX = 4;
    private static final String ADD_ITEM_QUERY =
            "INSERT INTO items (name, item_info, price) VALUES (?, ?, ?);";
    private static final String DELETE_ITEM_QUERY = "DELETE FROM items WHERE id =?";
    private static final String UPDATE_ITEM_PRICE_AND_INFO_QUERY =
            "UPDATE items SET item_info=?, price=? WHERE id=?;";

    public ItemDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Method of adding a product to the catalog
     *
     */
    @Override
    public void addItem(String name, String itemInfo, int price) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createQuery(ADD_ITEM_QUERY)
                    .setParameter("name", name)
                    .setParameter("itemInfo", itemInfo)
                    .setParameter("price", price)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * Method of removing an item from the catalog
     *
     */
    @Override
    public boolean deleteItem(int itemId) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Проверка на наличие элемента в заказах
            String checkSql = "SELECT items_id FROM orders_has_items WHERE items_id = :itemId";
            NativeQuery<Integer> checkQuery = session.createNativeQuery(checkSql);
            checkQuery.setParameter("itemId", itemId);
            Integer existingItemId = checkQuery.uniqueResult();

            if (existingItemId != null) {
                return false; // Элемент присутствует в заказах
            }

            // Удаление элемента с использованием вашего запроса
            String deleteSql = DELETE_ITEM_QUERY;
            NativeQuery deleteQuery = session.createNativeQuery(deleteSql);
            deleteQuery.setParameter("itemId", itemId);
            deleteQuery.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage(), e);
        }
        return true;
    }

    /**
     *
     * Method of getting the entire catalog
     *
     */
    @Override
    public List<Item> getAllItems() throws DaoException {
        try (Session session = sessionFactory.openSession()) {
            // Используем нативный SQL-запрос для получения всех элементов
            String sql = GET_ALL_ITEMS_QUERY;
            NativeQuery<Item> query = session.createNativeQuery(sql, Item.class);
            return query.getResultList(); // Возвращает список всех элементов
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * Method of changing product data
     *
     */
    @Override
    public void changeItemsDetails(int itemId, String itemInfo, int price) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Получение элемента по ID
            Item item = session.get(Item.class, itemId);
            if (item != null) {
                // Обновление полей
                item.setItemInfo(itemInfo);
                item.setPrice(price);
                session.update(item); // Сохранение изменений
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage(), e);
        }
    }
}
