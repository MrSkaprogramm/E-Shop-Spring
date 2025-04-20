package eshop.spring.tr.service.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.UserDaoInterface;
import eshop.spring.tr.dao.impl.UserDao;
import eshop.spring.tr.models.User;
import eshop.spring.tr.service.ServiceException;
import eshop.spring.tr.service.UserServiceInterface;
import eshop.spring.tr.service.validation.impl.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    private final UserDao userDao;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserDao userDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userValidator = userValidator;
    }

    /**
     *
     * User authorization method
     *
     */
    @Override
    public User userAuth(String login, String password) throws ServiceException {
        if (!userValidator.checkUserAuthInfo(login, password)) {
            throw new ServiceException("Wrong authorization data");
        }

        User user;
        try {
            user = userDao.userAuth(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    /**
     *
     * User registration method
     *
     */
    @Override
    public boolean userRegistration(String login, String password, String fio, String email,
                                    String phoneNum, String address, int role) throws ServiceException {
        if (!userValidator.checkUserRegisterInfo(login, password, fio, email, address)) {
            throw new ServiceException("Wrong registration data");
        }

        try {
            if (!userDao.checkUserLogin(login)) {
                return false;
            }
            userDao.userRegistration(login, password, fio, email, phoneNum, address, role);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    /**
     *
     * Method of adding a user to the blacklist
     *
     */
    @Override
    public void addtoBlackList(int userId) throws ServiceException {

        try {
            userDao.addtoBlackList(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     *
     * Method of receiving all defaulters
     *
     */
    @Override
    public List<User> getAllPaymentEvaders() throws ServiceException {
        List<User> paymentEvaders = new ArrayList<User>();
        try {
            paymentEvaders = userDao.getAllPaymentEvaders();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return paymentEvaders;
    }
}
