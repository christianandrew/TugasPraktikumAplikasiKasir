package com.example.tugaspraktikum;

public class Product {
    String namaBarang;
    Integer hargaBarang;

    public Product(String namaBarang, Integer hargaBarang) {
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    public Product() {

    }

    public String getNamaBarang(String namaBarang) {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getHargaBarang(int hargaBarang) {
        return this.hargaBarang;
    }

    public void setHargaBarang(Integer hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public Integer getHargaBarang() {
        return this.hargaBarang;
    }
}
