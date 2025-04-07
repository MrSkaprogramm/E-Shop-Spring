package eshop.spring.tr.dao.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.UserDaoInterface;
import eshop.spring.tr.models.Role;
import eshop.spring.tr.models.User;
import eshop.spring.tr.models.UserStatus;
import eshop.spring.tr.models.builder.UserBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao implements UserDaoInterface {
    private final SessionFactory sessionFactory;

    private static final String GET_PAYMENT_EVADERS_QUERY =
            "SELECT DISTINCT users.id, users.login, orders.order_status, users.FIO, users.status "
                    + "FROM users, orders WHERE orders.users_id=users.id AND order_status=0;";
    private static final int USER_ID_COLUMN_INDEX = 1;
    private static final int USER_FIO_COLUMN_INDEX = 5;
    private static final int USER_EMAIL_COLUMN_INDEX = 6;
    private static final int USER_ADDRESS_COLUMN_INDEX = 7;
    private static final int USER_ROLE_COLUMN_INDEX = 4;
    private static final int USER_STATUS_COLUMN_INDEX = 8;
    private static final int PAYMENT_EVADERS_LOGIN_COLUMN_INDEX = 2;
    private static final int PAYMENT_EVADERS_FIO_COLUMN_INDEX = 4;
    private static final int PAYMENT_EVADERS_STATUS_COLUMN_INDEX = 5;
    private static final String USER_AUTHORIZATION_QUERY =
            "SELECT * FROM users WHERE login =? AND password =?;";
    private static final String ADD_USER_TO_BLACKLIST_QUERY =
            "UPDATE users SET status = false WHERE id =?";
    private static final String USER_REGISTRATION_QUERY =
            "iNSERT INTO users (login, password, users_role, FIO, email, address, status) VALUES (?, ?, ?, ?, ?, ?, true);";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT * FROM users WHERE id =?";
    private static final String CHECK_UNIQUENESS_USER_LOGIN_QUERY =
            "SELECT * FROM users WHERE login =?";

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * User authorization method
     *
     */
    public User userAuth(String login, String password) throws DaoException {
        Transaction transaction = null;
        User user = null;
        UserBuilder buildUser = new UserBuilder();

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для авторизации
            Query<?> query = session.createNativeQuery(USER_AUTHORIZATION_QUERY)
                    .addEntity(User.class);
            query.setParameter(1, login);
            query.setParameter(2, password);
            List<?> resultList = query.getResultList();

            if (!resultList.isEmpty()) {
                Object[] rs = (Object[]) resultList.get(0);
                int role = (Integer) rs[USER_ROLE_COLUMN_INDEX];
                int status = (Integer) rs[USER_STATUS_COLUMN_INDEX];

                buildUser.setUserId((Integer) rs[USER_ID_COLUMN_INDEX]);
                buildUser.setLogin(login);
                buildUser.setPassword(password);
                buildUser.setFio((String) rs[USER_FIO_COLUMN_INDEX]);
                buildUser.setEmail((String) rs[USER_EMAIL_COLUMN_INDEX]);
                buildUser.setAddress((String) rs[USER_ADDRESS_COLUMN_INDEX]);

                switch (role) {
                    case 1:
                        buildUser.setRole(Role.CLIENT);
                        buildUser.setStatus(status == 1 ? UserStatus.FREE : UserStatus.BLACKLISTED);
                        break;
                    case 2:
                        buildUser.setRole(Role.ADMIN);
                        break;
                }
                user = buildUser.build();
            }

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
        return user;
    }

    /**
     *
     * User registration method
     *
     */
    @Override
    public void userRegistration(String login, String password, String fio, String email,
                                 String phoneNum, String address, int role) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для регистрации пользователя
            Query<?> query = session.createNativeQuery(USER_REGISTRATION_QUERY);
            query.setParameter(1, login);
            query.setParameter(2, password);
            query.setParameter(3, role);
            query.setParameter(4, fio);
            query.setParameter(5, email);
            query.setParameter(6, address);
            query.executeUpdate();

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * Method of adding a user to the blacklist
     *
     */
    @Override
    public void addtoBlackList(int userId) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для добавления пользователя в черный список
            Query<?> query = session.createNativeQuery(ADD_USER_TO_BLACKLIST_QUERY);
            query.setParameter(1, userId);
            query.executeUpdate();

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * Method of receiving all defaulters
     *
     */
    @Override
    public List<User> getAllPaymentEvaders() throws DaoException {
        Transaction transaction = null;
        List<User> paymentEvaders = new ArrayList<>();
        UserBuilder buildUser = new UserBuilder();

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для получения всех неплательщиков
            Query<?> query = session.createNativeQuery(GET_PAYMENT_EVADERS_QUERY);
            List<?> results = query.getResultList();

            for (Object result : results) {
                Object[] row = (Object[]) result;

                buildUser.setUserId((Integer) row[0]);
                buildUser.setLogin((String) row[1]);
                buildUser.setFio((String) row[3]);
                buildUser.setStatus((Integer) row[4] == 0 ? UserStatus.BLACKLISTED : UserStatus.FREE);

                User paymentEvader = buildUser.build();
                paymentEvaders.add(paymentEvader);
            }

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
        return paymentEvaders;
    }

    /**
     *
     * User status verification method
     *
     */
    @Override
    public boolean checkUserStatus(int userId) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для поиска пользователя по ID
            Query<?> query = session.createNativeQuery(FIND_BY_USER_ID_QUERY);
            query.setParameter(1, userId);
            List<?> results = query.getResultList();

            if (!results.isEmpty()) {
                Object[] row = (Object[]) results.get(0);
                int status = (Integer) row[7]; // Индекс 7 соответствует статусу
                return status != 0; // Возвращаем true, если статус не 0
            }

            transaction.commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
        return false; // Если пользователь не найден, возвращаем false
    }

    /**
     *
     * User login verification method
     *
     */
    @Override
    public boolean checkUserLogin(String login) throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Выполняем запрос для проверки уникальности логина
            Query<?> query = session.createNativeQuery(CHECK_UNIQUENESS_USER_LOGIN_QUERY);
            query.setParameter(1, login);
            List<?> results = query.getResultList();

            transaction.commit(); // Подтверждаем транзакцию

            // Если результаты не пусты, логин уже существует
            return results.isEmpty();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
    }
}
