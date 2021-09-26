package com.example.tugaspraktikum;

public class UserCart {
    String namaPelanggan, productId;
    Integer jumlahBarang, totalBelanja;

    public UserCart(String namaPelanggan, String productId, Integer jumlahBarang, Integer totalBelanja) {
        this.namaPelanggan = namaPelanggan;
        this.productId = productId;
        this.jumlahBarang = jumlahBarang;
        this.totalBelanja = totalBelanja;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public Integer getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(Integer totalBelanja) {
        this.totalBelanja = totalBelanja;
    }
}
