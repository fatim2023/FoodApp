package com.rajendra.foodapp.model;

import java.io.Serializable;

public class Foods implements Serializable {

    private String _id;
    private String favoriteDish;
    private String imageUrl;
    private float price;
    private int platesAvailable;



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFavoriteDish() {
        return favoriteDish;
    }

    public void setFavoriteDish(String favoriteDish) {
        this.favoriteDish = favoriteDish;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPlatesAvailable() {
        return platesAvailable;
    }

    public void setPlatesAvailable(int platesAvailable) {
        this.platesAvailable = platesAvailable;
    }

    public Foods(String _id, String favoriteDish, String imageUrl, float price, int platesAvailable) {
        this._id = _id;
        this.favoriteDish = favoriteDish;
        this.imageUrl = imageUrl;
        this.price = price;
        this.platesAvailable = platesAvailable;
    }

    public Foods() {
    }
}
