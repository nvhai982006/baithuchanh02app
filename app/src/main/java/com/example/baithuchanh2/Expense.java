package com.example.baithuchanh2;

import java.io.Serializable;

public class Expense implements Serializable {
    private String tenKhoanChi;
    private long soTien;
    private String danhMuc;
    private String ngayChiTieu;
    private String ghiChu;

    public Expense(String tenKhoanChi, long soTien, String danhMuc, String ngayChiTieu, String ghiChu) {
        this.tenKhoanChi = tenKhoanChi;
        this.soTien = soTien;
        this.danhMuc = danhMuc;
        this.ngayChiTieu = ngayChiTieu;
        this.ghiChu = ghiChu;
    }

    public String getTenKhoanChi() { return tenKhoanChi; }
    public long getSoTien() { return soTien; }
    public String getDanhMuc() { return danhMuc; }
    public String getNgayChiTieu() { return ngayChiTieu; }
    public String getGhiChu() { return ghiChu; }
}