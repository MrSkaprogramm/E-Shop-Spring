package eshop.spring.tr.service;

import eshop.spring.tr.models.User;

import java.util.List;

public interface UserServiceInterface {
    public User userAuth(String login, String password) throws ServiceException;

    public boolean userRegistration(String login, String password, String fio, String email,
                                    String phoneNum, String address, int role) throws ServiceException;

    public void addtoBlackList(int userId) throws ServiceException;

    public List<User> getAllPaymentEvaders() throws ServiceException;
}
