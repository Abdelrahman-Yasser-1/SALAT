package com.example.e_commerce.Model;

public class Cart {
    private int product_id, quantity, cat_id, user_id;
    private String name, image;
    private double price;

    public Cart() {
    }

    public Cart(int product_id, String name, String image, double price, int user_id, int quantity, int cat_id) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.cat_id = cat_id;
        this.user_id = user_id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
