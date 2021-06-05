package com.sict.udn.Model;

public class Cart {
    private String id;
    private String name;
    private String image;
    private String price;
    private String description;
    private String imagedes1;
    private String imagedes2;
    private String promotional;
    private int SoLuong;
    private long Total;

    public Cart(String id, String name, String image, String price, String description, String imagedes1, String imagedes2, String promotional, int soluong, long total) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.imagedes1 = imagedes1;
        this.imagedes2 = imagedes2;
        this.promotional = promotional;
        this.SoLuong = soluong;
        this.Total = total;
    }


    public Cart() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPromotional() {
        return promotional;
    }

    public void setPromotional(String promotional) {
        this.promotional = promotional;
    }

    public int getSoluong() {
        return SoLuong;
    }

    public void setSoluong(int soluong) {
        this.SoLuong = soluong;
    }

    public long getTotal() {
        return Total;
    }

    public void setTotal(long total) {
        this.Total = total;
    }
}
