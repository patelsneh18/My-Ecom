package com.streamliners.models;

import java.util.List;

public class Product {
    //Common for WBP and VBP
    public String name;       // Contains name of the product
    public String imageUrl;   // Location of Image of the Product
    public float type;        // Type of the Product - Weight Based Product and Variant Based Product

    //For VBP Only
    List<Variant> variants;  // List of variants class containing Predefined Variants of a Product

    //For WBP Only
    public float minQty;      // Min Quantity of Purchase
    public float pricePerKg;  // Price Per KG of the Product

    /* Constructor for Product
     *
     * @param name   name of the product
     * @param imageUrl URL of image of the product
     * @param minQty  Minimum Quantity of Purchase
     * @param pricePerKg  Price Per KG of the Product
     * */
    public Product(String name,String imageUrl,float minQty,float pricePerKg) {
        type = ProductType.TYPE_WBP;
        this.name = name;
        this.imageUrl = imageUrl;
        this.minQty = minQty;
        this.pricePerKg = pricePerKg;
    }

    /* Constructor for Product
     *
     * @param name  name of the product
     * @param imageUrl  URL of image of the product
     * @param minQty  Minimum Quantity of Purchase
     * @param pricePerKg  Price Per KG of the Product
     * */
    public Product(String name,String imageUrl,List<Variant> variants) {
        type = ProductType.TYPE_VBP;
        this.name = name;
        this.imageUrl = imageUrl;
        this.variants = variants;
    }

    public void editProductName(String name, CartItem cartItem){
        if (cartItem!=null)
            cartItem.name = name;
        this.name = name;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (type==0){
            return name + " @Rs " + pricePerKg + "/kg";
        }
        else {
            return name + " " + variants ;
        }
    }
}