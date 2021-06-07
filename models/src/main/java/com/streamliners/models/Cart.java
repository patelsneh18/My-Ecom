package com.streamliners.models;

import java.util.HashMap;

public class Cart {
    public HashMap<String, CartItem> cartItems = new HashMap<>();      // HashMap to Store CartItem in the Cart corresponding to Product name as its key
    public float totalCost;     // Total Cost of overall Cart
    public float totalItems;    // Total items in our cart

    // Add WBP to the cart
    public void add(Product product, float qty){
        // If item is already in the cart
        if (cartItems.containsKey(product.name)){
            totalCost -= cartItems.get(product.name).cost();
            cartItems.get(product.name).qty = qty;
        }
        // Adding item for the first time to the cart
        else {
            CartItem cartItem = new CartItem(product.name,product.pricePerKg,qty,0);
            cartItems.put(product.name,cartItem);

            totalItems++;
        }
        totalCost += cartItems.get(product.name).cost();
    }

    // Add VBP  to our cart
    public void add(Product product, Variant variant){
        String key = product.name + " " + variant.name;  // Key for VBD concatenating product and variant name
        // If item is already in the cart
        if (cartItems.containsKey(key)){
            cartItems.get(key).qty++;
            totalItems++;
            totalCost+= variant.price;
        }
        // Adding item for the first time to the cart
        else {
            CartItem cartItem = new CartItem(product.name + " " + variant.name,variant.price,1,1);
            cartItems.put(key,cartItem);
            totalItems++;
            totalCost+= variant.price;
        }
    }

    // Remove VBP/WBP from our cart
    public void remove(Product product){
        if (product.type == 0) removeWBP(product);
        else removeAllVBP(product);
    }

    // Remove WBP from the cart
    public void removeWBP(Product product){
        // update cart summary
        totalCost -= cartItems.get(product.name).cost();
        totalItems--;

        cartItems.remove(product.name);
    }

    // Remove all variants of a VBP from the cart
    public void removeAllVBP(Product product){
        // Iterate all variants of our VBP
        for (Variant variant:product.variants) {
            String key = product.name + " " + variant.name;

            if (cartItems.containsKey(key)) {
                // update cart summary
                totalCost -= cartItems.get(key).cost();
                totalItems -= cartItems.get(key).qty;

                cartItems.remove(key);
            }
        }
    }

    // Decrement variant of a VBP
    public void decrementVBP(Product product, Variant variant){
        String key = product.name + " " + variant.name;

        // update qty of variant
        cartItems.get(key).qty--;

        //update cart summary
        totalCost -= variant.price;
        totalItems--;

        // remove from cart if qty = 0
        if (cartItems.get(key).qty == 0) cartItems.remove(key);
    }

    @Override
    public String toString() {
        return "\nYour Cart:\n" + cartItems.values() + "\n" + "Total Items = " + totalItems + "(" + totalCost + ")";
    }
}