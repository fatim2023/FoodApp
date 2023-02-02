package com.rajendra.foodapp.model;

public class Order {
    private String name;
    private String address;
    private String phone;
    private Foods foods;

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Foods getFoods() {
        return foods;
    }

    public void setFoods(Foods foods) {
        this.foods = foods;
    }

    public Order(String name, String address, String phone, Foods foods) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.foods = foods;
    }
}
