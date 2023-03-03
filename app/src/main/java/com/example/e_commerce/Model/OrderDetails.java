package com.example.e_commerce.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetails {
    private int order_id;
//    List<String> product_name;
//    List<Integer> product_quantity;

    ArrayList<Product> products;

    public OrderDetails() {
    }

    public OrderDetails(int order_id) {
        this.order_id = order_id;
//        this.product_name = new ArrayList<String>();
//        this.product_quantity = new ArrayList<Integer>();
        this.products = new ArrayList<Product>();
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

//    public List<String> getProduct_name() {
//        return product_name;
//    }
//
//    public void setProduct_name(List<String> product_name) {
//        this.product_name = product_name;
//    }
//
//    public List<Integer> getProduct_quantity() {
//        return product_quantity;
//    }
//
//    public void setProduct_quantity(List<Integer> product_quantity) {
//        this.product_quantity = product_quantity;
//    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
