package com.test.doodleblue;

public class MenuModel {
    public final static int TYPE_ITEM = 0, TYPE_HEADER = 1, TYPE_FOOTER = 3;
    int id;
    String itemName;
    String itemDescription;
    double itemPrice;
    int itemQuantity;
    int UIType;

    public MenuModel(int id,String itemName, String itemDescription, double itemPrice, int itemQuantity, int UIType) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.UIType = UIType;
    }

    public int getId() {
        return id;
    }

    public MenuModel(String itemName, int UIType) {
        this.itemName = itemName;
        this.UIType = UIType;
    }

    public MenuModel(int UIType) {
        this.UIType = UIType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getUIType() {
        return UIType;
    }
}
