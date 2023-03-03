package com.example.e_commerce.Model;

public class Product {

    private int id, quantity, cat_id, sold;
    private String name, image;
    private double price;

    public Product() {}

    public Product(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public Product(int id, String name, String image, double price, int sold, int quantity, int cat_id) {
        this.id = id;
        this.quantity = quantity;
        this.cat_id = cat_id;
        this.sold = sold;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
