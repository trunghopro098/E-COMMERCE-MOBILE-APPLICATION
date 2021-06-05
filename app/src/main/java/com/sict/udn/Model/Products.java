package com.sict.udn.Model;

import java.io.Serializable;

public class Products implements Serializable {
    private String id;
    private String name ;
    private String description;
    private String image;
    private String imagedes1;
    private String imagedes2;
    private String price;
    private String promotional;
    private String SoLuong;

    public Products() {
    }

    public Products(String id, String name, String description, String image, String imagedes1, String imagedes2, String price, String promotional) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.imagedes1 = imagedes1;
        this.imagedes2 = imagedes2;
        this.price = price;
        this.promotional = promotional;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagedes1() {
        return imagedes1;
    }

    public void setImagedes1(String imagedes1) {
        this.imagedes1 = imagedes1;
    }

    public String getImagedes2() {
        return imagedes2;
    }

    public void setImagedes2(String imagedes2) {
        this.imagedes2 = imagedes2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPromotional() {
        return promotional;
    }

    public void setPromotional(String promotional) {
        this.promotional = promotional;
    }
}
