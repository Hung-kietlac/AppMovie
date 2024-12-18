package com.example.android_nhom10.Classes;

import java.io.Serializable;

public class Khachhang implements Serializable {
    private static final long serialVersionUID=1L;
    int idkhach;
    private String ten, ngaysinh, gioitinh, diachi, sdt, email, username, password;
    private int isAdmin;

    public Khachhang(int id, String ten, String gioitinh, String ngaysinh, String diachi, String sdt, String email, String usernamedb) {
        this.idkhach=id;
        this.ten=ten;
        this.gioitinh=gioitinh;
        this.ngaysinh=ngaysinh;
        this.diachi=diachi;
        this.sdt=sdt;
        this.email=email;
        this.username=usernamedb;
    }

    public int getIdkhach() {
        return idkhach;
    }

    public String getTen() {
        return ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setIdkhach(int idkhach) {
        this.idkhach=idkhach;
    }

    public void setTen(String ten) {
        this.ten=ten;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh=ngaysinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh=gioitinh;
    }

    public void setDiachi(String diachi) {
        this.diachi=diachi;
    }

    public void setSdt(String sdt) {
        this.sdt=sdt;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin=isAdmin;
    }
}