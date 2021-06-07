package com.streamliners.models;

// Class For Products having Predefined Variants
public class Variant {
    public String name; // Name of the Variant
    public float price; // Price of Particular Variant

    /**
     * @param name   name of the variant
     * @param price  Price of Particular Variant
     */
    public Variant(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s @ Rs. %s",name,price );
    }
}