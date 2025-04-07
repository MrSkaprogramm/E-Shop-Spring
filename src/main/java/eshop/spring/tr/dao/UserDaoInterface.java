package eshop.spring.tr.dao;

import eshop.spring.tr.models.User;

import java.util.List;

public interface UserDaoInterface {
    public User userAuth(String login, String password) throws DaoException;

    public void userRegistration(String login, String password, String fio, String email,
                                 String phoneNum, String address, int role) throws DaoException;

    public void addtoBlackList(int userId) throws DaoException;

    public List<User> getAllPaymentEvaders() throws DaoException;

    public boolean checkUserStatus(int userId) throws DaoException;

    public boolean checkUserLogin(String login) throws DaoException;
}
