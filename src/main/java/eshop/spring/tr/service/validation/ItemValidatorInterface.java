package eshop.spring.tr.service.validation;

public interface ItemValidatorInterface {
    public boolean checkItemInfo(String name, String itemInfo, int price);

    public boolean validateItemName(String name);

    public boolean validateItemInfo(String itemInfo);

    public boolean validateItemPrice(int price);

    public boolean checkItemInfo(String itemInfo, int price);
}
