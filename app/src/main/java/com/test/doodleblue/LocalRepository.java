package com.test.doodleblue;

import java.util.ArrayList;
import java.util.List;

import static com.test.doodleblue.MenuModel.TYPE_HEADER;
import static com.test.doodleblue.MenuModel.TYPE_ITEM;

public class LocalRepository {
    static LocalRepository singleInstance;
    public List<MenuModel> mMenuList;

    public LocalRepository() {
        mMenuList = new ArrayList<>();
        mMenuList.add(new MenuModel("Starters", TYPE_HEADER));
        mMenuList.add(new MenuModel(1, "Item One", "Item Description", 12, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(2, "Item Two", "Item Description", 11, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(3, "Item Three", "Item Description", 10, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(4, "Item Four", "Item Description", 9, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(5, "Item Five", "Item Description", 8, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(6, "Item Six", "Item Description", 7, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(7, "Item Seven", "Item Description", 6, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(8, "Item Eight", "Item Description", 5, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(9, "Item Nine", "Item Description", 4, 0, TYPE_ITEM));
        mMenuList.add(new MenuModel(10, "Item Ten", "Item Description", 3, 0, TYPE_ITEM));
    }

    static LocalRepository getInstance() {
        if (singleInstance != null) {
            return singleInstance;
        }
        singleInstance = new LocalRepository();
        return singleInstance;
    }

    public List<MenuModel> getMenuList() {
        return mMenuList;
    }

    public List<MenuModel> getCartItemList() {
        List<MenuModel> cartItemList = new ArrayList<>();
        for (MenuModel model : mMenuList) {
            if (model.getItemQuantity() != 0) {
                cartItemList.add(model);
            }
        }
        return cartItemList;
    }

    public int getTotalItemCount() {
        int count = 0;
        for (MenuModel model : mMenuList) {
            count = count + model.getItemQuantity();
        }
        return count;
    }

    public double getPayableAmount() {
        double totalAmount = 0;
        for (MenuModel model : mMenuList) {
            double itemAmount = (model.getItemQuantity() * model.getItemPrice());
            totalAmount = totalAmount + itemAmount;
        }
        return totalAmount;
    }

    public void updateItem(int id, int count) {
        int pos = 0;
        for (int i = 0; i < mMenuList.size(); i++) {
            MenuModel model = mMenuList.get(i);
            if (model.getId() == id) {
                pos = i;
                break;
            }
        }
        MenuModel menuModel = mMenuList.get(pos);
        menuModel.setItemQuantity(count);
        mMenuList.set(pos, menuModel);
    }
}
