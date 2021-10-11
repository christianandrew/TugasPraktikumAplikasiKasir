package com.example.tugaspraktikum;

public class OrderedProduct {

    private String namaPelanggan;
    private String productId;
    private int jumlahBarang;

    public OrderedProduct(String namaPelanggan,String productId, int jumlahBarang) {
        this.namaPelanggan = namaPelanggan;
        this.productId = productId;
        this.jumlahBarang = jumlahBarang;
    }
    public String getUser() {
        return namaPelanggan;
    }

    public void setUser(String user) {
        this.namaPelanggan = user;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return jumlahBarang;
    }

    public void setQuantity(int quantity) {
        this.jumlahBarang = quantity;
    }
}
