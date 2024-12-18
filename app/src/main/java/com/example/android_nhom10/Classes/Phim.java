package com.example.android_nhom10.Classes;

import java.io.Serializable;

public class Phim implements Serializable {
    private int idphim;
    private String tenphim, theloai, ngaychieu, thoiluong, poster, tendaodien, dienvien, noidung, quocgia, giave, releaseDate;

    public int getIdphim() {
        return idphim;
    }

    public String getTenphim() {
        return tenphim;
    }

    public String getTheloai() {
        return theloai;
    }

    public String getNgaychieu() {
        return ngaychieu;
    }

    public String getThoiluong() {
        return thoiluong;
    }

    public String getPoster() {
        return poster;
    }

    public String getTendaodien() {
        return tendaodien;
    }

    public String getDienvien() {
        return dienvien;
    }

    public String getNoidung() {
        return noidung;
    }

    public String getQuocgia() {
        return quocgia;
    }

    public String getGiave() {
        return giave;
    }

    public void setIdphim() {
        this.idphim=idphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim=tenphim;
    }

    public void setTheloai(String theloai) {
        this.theloai=theloai;
    }

    public void setNgaychieu(String ngaychieu) {
        this.ngaychieu=ngaychieu;
    }

    public void setThoiluong(String thoiluong) {
        this.thoiluong=thoiluong;
    }

    public void setPoster(String poster) {
        this.poster=poster;
    }

    public void setTendaodien(String tendaodien) {
        this.tendaodien=tendaodien;
    }

    public void setDienvien(String dienvien) {
        this.dienvien=dienvien;
    }

    public void setNoidung(String noidung) {
        this.noidung=noidung;
    }

    public void setQuocgia(String quocgia) {
        this.quocgia=quocgia;
    }

    public void setGiave(String giave) {
        this.giave=giave;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate=releaseDate;
    }
}