package com.example.e_commerce.Model;

import java.util.Date;

public class Order {

    private int order_id, user_id;
    private String date, address, feedback;
    private double rate;

    public Order() {
    }

    public Order(int user_id, String date, String address, String feedback, double rate) {
        this.user_id = user_id;
        this.date = date;
        this.address = address;
        this.feedback = feedback;
        this.rate = rate;
    }

    public Order(int order_id, int user_id, String date, String address, String feedback, double rate) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.date = date;
        this.address = address;
        this.feedback = feedback;
        this.rate = rate;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
