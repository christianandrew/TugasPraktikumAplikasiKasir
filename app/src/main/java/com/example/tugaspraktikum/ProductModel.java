package com.example.tugaspraktikum;

public class ProductModel {
    private String namaPelanggan;
    private Integer totalBelanja;

//    public ProductModel() {}
//
//    public ProductModel(String namaPelanggan) {this.namaPelanggan = namaPelanggan;}
//
//    public String getProductName() {return namaPelanggan;}
//
//    public void setProductName(String namaPelanggan) {
//
//        this.namaPelanggan = namaPelanggan;
//    }
//
//    public String getNamaPelanggan() {
//        return namaPelanggan;
//    }
//
//    public void setNamaPelanggan(String namaPelanggan) {
//        this.namaPelanggan = namaPelanggan;
//    }

    public ProductModel() {}

    public ProductModel(String namaPelanggan, Integer totalBelanja) {
        this.namaPelanggan = namaPelanggan;
        this.totalBelanja = totalBelanja;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public Integer getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(Integer totalBelanja) {
        this.totalBelanja = totalBelanja;
    }
}
