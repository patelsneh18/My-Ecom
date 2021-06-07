package com.streamliners.models;

import java.util.HashMap;

public class CartItem {
    String name;                // Name of the Cart Item
    float costPerUnit;          // Cost Per Unit of Item
    float qty;                  // Number of Items
    int type;

    /**
     * @param name   Name of the Cart Item
     * @param costPerUnit   Cost Per Unit of Item
     * @param qty    Number of Items
     */
    public CartItem(String name, float costPerUnit, float qty,int type) {
        this.name = name;
        this.costPerUnit = costPerUnit;
        this.qty = qty;
        this.type = type;
    }

    // Returns Cost of Cart Item
    float cost(){
        return costPerUnit * qty;
    }

    @Override
    public String toString() {
        return "\n" + name + String.format("(%f X %f = %f)",costPerUnit,qty,cost());
    }
}