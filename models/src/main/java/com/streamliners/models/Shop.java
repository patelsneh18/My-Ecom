package com.streamliners.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Shop {
    Scanner sc = new Scanner(System.in);
    public HashMap<String,Product> productsList = new HashMap<>();            //Storing list of products in productList Hashmap

    // Add Product to Shop
    public void addProduct(){
        String menu = "\nSelect type of the product:" +
                "\n0: Add Weight Based Product" +
                "\n1: Add Variant Based Product" +
                "\n2: Go Back" +
                "\nEnter your choice: ";

        int chooseOption = 1;

        // checking to go back from the menu
        while (chooseOption != 2) {
            System.out.print(menu);
            chooseOption = sc.nextInt();
            // To add weight based product
            if (chooseOption == 0){
                System.out.print("Enter name of the product: ");
                String name = sc.nextLine();
                while (name.isEmpty()) name = sc.nextLine();

                if (productsList.containsKey(name)) {
                    System.out.println("Product already exists");
                    continue;
                }
                System.out.print("Enter image URL of the product: ");
                String imageURL = sc.nextLine();

                System.out.print("Enter minimum quantity of the product: ");
                float minQty = sc.nextFloat();

                System.out.print("Enter price per kg: ");
                float pricePerKg = sc.nextFloat();
                Product product = new Product(name,imageURL,minQty,pricePerKg);

                productsList.put(product.name, product);
                System.out.println(Colors.GREEN + "Product Added to Shop!" + Colors.RESET);
            }
            // To add variant based product
            else if (chooseOption == 1){
                System.out.print("Enter name of the product: ");
                String name = sc.nextLine();
                while (name.isEmpty()) name = sc.nextLine();

                System.out.print("Enter image URL of product: ");
                String imageUrl = sc.nextLine();

                System.out.print("Enter variants of your product: ");
                String v = sc.nextLine();
                String[] var = v.split(" ");
                List<Variant> variants = new ArrayList<>();
                for (int i = 0; i< var.length-1;i+=2){
                    Variant variant = new Variant(var[i],Float.parseFloat(var[i+1]));
                    variants.add(variant);
                }
                Product product = new Product(name,imageUrl,variants);
                productsList.put(product.name,product);
                System.out.println(Colors.GREEN + "Product Added to Shop!" + Colors.RESET);
            }

                // if user any incorrect option
            else if (chooseOption!=2)System.out.println("Incorrect Option. (Please select from 0 to 2)");
        }
    }

    //Remove Product from the Shop
    public void removeProduct(){
        if (productsList.isEmpty()) {
            System.out.println(Colors.RED + "Oops! No items in shop to remove" + Colors.RESET);
            return;
        }
        int choice = 1;
        while (choice!=0) {
            if (productsList.isEmpty()) {
                System.out.println(Colors.RED + "Oops! Shop is Empty Now!" + Colors.RESET);
                return;
            }
            System.out.println("\nChoose from following products:");
            Object[] list = productsList.keySet().toArray();
            // Prints current product in the shop
            for (int i = 0; i < productsList.size(); i++) {
                System.out.println((i + 1) + ". " + list[i]);
            }
            System.out.print("Choose product number you want to remove (0 to go back): ");
            choice = sc.nextInt();
            if (choice!=0) {
                productsList.remove(list[choice - 1]);
                System.out.println(Colors.GREEN + "Product removed Successfully!" + Colors.RESET);
            }
        }
    }

    // Edit a Product
    public void editProduct(Cart cart,Shop shop){
        if (productsList.isEmpty()) {
            System.out.println(Colors.RED + "Oops! No items in shop to edit" + Colors.RESET);
            return;
        }
        int choice = 1;
        while (choice!=0) {
            System.out.println("\nChoose from following products:");
            Object[] list = productsList.keySet().toArray();
            for (int i = 0; i < productsList.size(); i++) {
                System.out.println((i + 1) + ". " + list[i]);
            }
            System.out.print("Choose product number you want to edit (0 to go back): ");
            choice = sc.nextInt();
            if (choice==0) return;
            if (choice != 0) {
                //Edit WBP
                if (productsList.get(list[choice-1]).type == 0) {
                    String menu = "\n0: Edit Min Quantity" +
                            "\n1: Edit Price Per KG" +
                            "\n2: Go Back" +
                            "\nEnter Choice: ";
                    int c = 1;
                    while (c != 2) {
                        System.out.print(menu);
                        c = sc.nextInt();
                        if (c == 0) editWBPMinQty(productsList.get(list[choice-1]), cart.cartItems.get(productsList.get(list[choice-1]).name),shop);
                        else if (c == 1)
                            editPriceWBP(productsList.get(list[choice-1]), cart.cartItems.get(productsList.get(list[choice-1]).name));
                        else if (c != 2) System.out.println(Colors.RED + "Incorrect choice (Choose from 0-2)"+ Colors.RESET);
                    }
                }
                //Edit VBP
                else if (productsList.get(list[choice-1]).type == 1) {
                        editVariantsVBP(productsList.get(list[choice-1]), cart);
                }
            }
        }
    }

    // Edit Name of the WBP
    public void editWBPMinQty(Product product, CartItem cartItem,Shop shop){
        System.out.print("Enter new min qty: ");
        float newMinQty = sc.nextFloat();

        // Edit name in cart if product is in the cart
        product.minQty = newMinQty;

        System.out.println(Colors.GREEN + "Min Quantity Changed to " + newMinQty + " Successfully!" + Colors.RESET);
    }

    //Edit WBP Price
    public void editPriceWBP (Product product,CartItem cartItem){
        System.out.print("Enter new price: ");
        float newPrice = sc.nextFloat();
        if (cartItem!=null)
            cartItem.costPerUnit = newPrice;
        product.pricePerKg = newPrice;
        System.out.println(Colors.GREEN + "Price Per KG Changed to " + newPrice + " Successfully!" + Colors.RESET);
    }

    //Edit VBP price
    public void editVariantsVBP(Product product,Cart cart){
        System.out.print("Enter new variant string: ");
        String v = sc.nextLine();
        while (v.isEmpty()) v = sc.nextLine();
        String[] var = v.split(" ");
        List<Variant> variants = new ArrayList<>();
        for (int i = 0; i< var.length-1;i+=2){
            Variant variant = new Variant(var[i],Float.parseFloat(var[i+1]));
            variants.add(variant);
        }
        productsList.get(product.name).variants = variants;
        System.out.println(Colors.GREEN + "Variants Changed Successfully!" + Colors.RESET);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMy Shop:\n");
        Object[] arr = productsList.keySet().toArray();
        for (int i=0;i<productsList.size();i++){
            sb.append(productsList.get(arr[i].toString()) + "\n");
        }
        return sb.toString();
    }
}
