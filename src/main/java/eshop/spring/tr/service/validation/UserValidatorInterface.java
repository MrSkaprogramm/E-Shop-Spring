package eshop.spring.tr.service.validation;

public interface UserValidatorInterface {
    public boolean checkUserAuthInfo(String login, String password);

    public boolean checkUserRegisterInfo(String login, String password, String fio, String email,
                                         String address);

    public boolean validateUserLogin(String login);

    public boolean validateUserPassword(String password);

    public boolean validateUserFio(String password);

    public boolean validateUserEmail(String email);

    public boolean validateUserAddress(String email);
}
