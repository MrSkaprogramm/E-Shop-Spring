package eshop.spring.tr.service.validation.impl;

import eshop.spring.tr.service.validation.ItemValidatorInterface;

public class ItemValidator implements ItemValidatorInterface {
    private static final String ITEM_NAME_REGEX = "[A-zА-я]{1,15}";
    private static final String PRICE_REGEX = "[0-9]{1,9}";
    private static final String ITEM_INFO_REGEX = "[A-zА-я A-zА-я]{2,255}";

    public boolean checkItemInfo(String bankCardNum, String expiringDate) {
        return false;
    }

    @Override
    public boolean checkItemInfo(String name, String itemInfo, int price) {
        boolean isValid = true;

        if (!validateItemName(name)) {
            isValid = false;
        }

        System.out.println("ItemName " + isValid);

        if (!validateItemInfo(itemInfo)) {
            isValid = false;
        }
        System.out.println("ItemInfo( " + isValid);

        if (!validateItemPrice(price)) {
            isValid = false;
        }
        System.out.println("ItemPrice " + isValid);

        return isValid;
    }

    @Override
    public boolean checkItemInfo(String itemInfo, int price) {
        boolean isValid = true;

        if (!validateItemInfo(itemInfo)) {
            isValid = false;
        }
        System.out.println("ItemInfo( " + isValid);

        if (!validateItemPrice(price)) {
            isValid = false;
        }
        System.out.println("ItemPrice " + isValid);

        return isValid;
    }

    @Override
    public boolean validateItemName(String name) {
        return name.matches(ITEM_NAME_REGEX);
    }

    @Override
    public boolean validateItemInfo(String itemInfo) {
        return itemInfo.matches(ITEM_INFO_REGEX);
    }

    @Override
    public boolean validateItemPrice(int price) {
        return String.valueOf(price).matches(PRICE_REGEX);
    };
}
