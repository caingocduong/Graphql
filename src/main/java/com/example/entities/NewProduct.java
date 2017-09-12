package com.example.entities;

public class NewProduct {
    private String id;
    private String img;
    private String title;
    private String description;
    private int discount;
    private double originalPrice;
    private double price;

    public NewProduct(String img, String title, String description, int discount, double originalPrice, double price) {
        this(null,img, title,description,discount,originalPrice,price);
    }

    public NewProduct(String id, String img, String title, String description, int discount, double originalPrice, double price) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.description = description;
        this.discount = discount;
        this.originalPrice = originalPrice;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
