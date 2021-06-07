package com.streamliners.models;

import java.util.Scanner;

public class CartFunctions {
    Scanner sc = new Scanner(System.in);
    //Add product to cart
    public void addToCart(Cart cart,Shop shop){
        int choice = 1;
        // Return if shop is empty
        if (shop.productsList.isEmpty()) {
            System.out.println(Colors.RED + "Oops! No items in shop" + Colors.RESET);
            return;
        }
        while (true){
            System.out.println("\nChoose from following products to add: ");
            Object[] list = shop.productsList.keySet().toArray();
            //Print Current items in cart
            for (int i = 0; i < list.length; i++) {
                System.out.println((i + 1) + ". " + list[i]);
            }
            System.out.print("Enter Product Number to Add (Press 0 to Go Back): ");
            choice = sc.nextInt();
            if (choice==0) break;
            choice-=1;
            //Edit WBP
            if (shop.productsList.get(list[choice]).type==0){
                System.out.print("Enter Quantity: ");
                int qty = sc.nextInt();
                cart.add(shop.productsList.get(list[choice]),qty);
                choice+=1;
                System.out.println("Product Added Successfully to your cart");
            }
            //Edit VBP
            else if (shop.productsList.get(list[choice]).type == 1){
                //Print variants of product
                for (int i=0;i<shop.productsList.get(list[choice]).variants.size();i++){
                    System.out.print("\n" + i + ". " + shop.productsList.get(list[choice]).variants.get(i));
                }
                System.out.print("\nChoose Variant to add: ");
                int c = sc.nextInt();

                cart.add(shop.productsList.get(list[choice]),shop.productsList.get(list[choice]).variants.get(c));
                choice+=1;
                System.out.println(Colors.GREEN + "Product Added Successfully to your cart" + Colors.RESET);
            }
        }
    }

    public void removeFromCart(Cart cart,Shop shop){
        // Return if cart is empty
        if (cart.cartItems.isEmpty()) {
            System.out.println(Colors.RED + "Oops! No items in cart to remove" + Colors.RESET);
            return;
        }
        int choice=1;
        while (choice!=0){
            System.out.println("Select from following products to remove:");
            Object[] items = cart.cartItems.keySet().toArray();
            //Print Current items in cart
            for (int i=0;i< items.length;i++){
                System.out.print("\n" + (i+1) + ". " + items[i]);
            }
            System.out.print("\nChoose Product to Remove (0 to Go Back): ");
            choice = sc.nextInt();
            if (choice!=0) {
                String[] arr = items[choice-1].toString().split(" ");
                if (arr.length==1) cart.remove(shop.productsList.get(items[choice-1]));
                else cart.remove(shop.productsList.get(arr[0]));
                System.out.println(Colors.GREEN + "Product Removed from Cart" + Colors.RESET);
            }
        }
    }

    //Edit the Cart
    public void editCart(Cart cart,Shop shop){
        // Return if cart is empty
        if (cart.cartItems.isEmpty()) {
            System.out.println(Colors.RED + "Oops! No items in cart to edit" + Colors.RESET);
            return;
        }
        int choice=1;
        while (choice!=0){
            System.out.println("Select from following products to edit:");
            Object[] items = cart.cartItems.keySet().toArray();
            //Print Current items in cart
            for (int i=0;i< items.length;i++){
                System.out.print("\n" + (i+1) + ". " + items[i]);
            }
            System.out.print("\nChoose Product to Edit (0 to Go Back): ");
            choice = sc.nextInt();

            if (choice==0) continue;
            //Edit WBP
            if (cart.cartItems.get(items[choice-1]).type == 0 ){
                editWBPCart(cart,shop.productsList.get(items[choice-1]));
            }
            //Edit VBP
            else if (cart.cartItems.get(items[choice-1]).type == 1 ){
                String key = items[choice-1].toString();
                String[] arr = key.split(" ");
                int n=0;
                // Finds out which variant to edit
                for (int i=0;i<shop.productsList.get(arr[0]).variants.size();i++){
                    if (shop.productsList.get(arr[0]).variants.get(i).name.equals(arr[1])){
                        n=i;
                    }
                }
                // To get key to access product from shop
                String item[] = items[choice-1].toString().split(" ");
                editVBPCart(cart,shop.productsList.get(item[0]).variants.get(n),shop.productsList.get(item[0]));
            }
        }
    }

    // Edits WBP in cart
    private void editWBPCart(Cart cart, Product product) {
        System.out.print("Enter new quantity: ");
        float newQty = sc.nextFloat();
        cart.add(product,newQty);
        System.out.println(Colors.GREEN + "Quantity Updated to " + newQty + " successfully!" + Colors.RESET);
    }

    //Edits VBP in cart
    public void editVBPCart(Cart cart,Variant variant,Product product){
        int choice = 1;
        while (choice!=0){
            System.out.println("0: Go Back\n1: Increment \n2: Decrement");
            choice = sc.nextInt();
            //Increment
            if (choice==1) {
                cart.add(product,variant);
                System.out.println(Colors.GREEN + "Incremented to " + cart.cartItems.get(product.name + " " + variant.name).qty + Colors.RESET);
            }
            //Decrement
            else if (choice==2) {
                if (cart.cartItems.get(product.name + " " + variant.name).qty==1) {
                    System.out.println(Colors.RED + "Cannot Decrement only 1 Left" + Colors.RESET);
                    continue;
                }
                cart.decrementVBP(product,variant);
                System.out.println(Colors.GREEN + "Decremented to " + cart.cartItems.get(product.name + " " + variant.name).qty + Colors.RESET);
            }
        }
    }
}
