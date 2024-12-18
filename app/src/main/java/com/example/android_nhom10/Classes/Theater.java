package com.example.android_nhom10.Classes;

public class Theater {
    private int id;
    private String tenChiNhanh;
    private String diaChi;

    public Theater() {
    }

    public Theater(int id, String tenChiNhanh, String diaChi) {
        this.id=id;
        this.tenChiNhanh=tenChiNhanh;
        this.diaChi=diaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getTenChiNhanh() {
        return tenChiNhanh;
    }

    public void setTenChiNhanh(String tenChiNhanh) {
        this.tenChiNhanh=tenChiNhanh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi=diaChi;
    }

    @Override
    public String toString() {
        return tenChiNhanh;
    }
}