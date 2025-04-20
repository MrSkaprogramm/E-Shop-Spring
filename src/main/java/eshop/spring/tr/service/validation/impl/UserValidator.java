package eshop.spring.tr.service.validation.impl;

import eshop.spring.tr.service.validation.UserValidatorInterface;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements UserValidatorInterface {
    private static final String FIO_REGEX = "^[]A-Za-z ,-\\\\.`]{2,45}";
    private static final String EMAIL_REGEX =
            "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String LOGIN_REGEX = "[A-zА-я]{1,15}";
    private static final String PASSWORD_REGEX =
            "[A-zА-я0-9]{1,15}";


    public UserValidator() {};

    // регулярные выражения
    public boolean checkUserAuthInfo(String login, String password) {
        boolean isValid = true;

        if (!validateUserLogin(login)) {
            isValid = false;
        }

        /*
         * if (!validateUserPassword(password)) { isValid = false; }
         */
        return isValid;
    };

    public boolean checkUserRegisterInfo(String login, String password, String fio, String email,
                                         String address) {
        boolean isValid = true;

        if (!validateUserLogin(login)) {
            isValid = false;
        }
        System.out.println(login + " isValidlogin " + isValid);

        if (!validateUserPassword(password)) {
            isValid = false;
        }

        System.out.println(password + " isValidpassword " + isValid);

        if (!validateUserFio(fio)) {
            isValid = false;
        }

        System.out.println(fio + " isValidfio " + isValid);

        if (!validateUserEmail(email)) {
            isValid = false;
        }

        System.out.println(email + " isValidemail " + isValid);

        /*
         * if (!validateUserAddress(address)) { isValid = false; }
         */
        return isValid;
    }

    @Override
    public boolean validateUserLogin(String login) {
        return login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean validateUserPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean validateUserFio(String fio) {
        return fio.matches(FIO_REGEX);
    }

    @Override
    public boolean validateUserEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean validateUserAddress(String address) {
        return !address.matches(null);
    };
}
