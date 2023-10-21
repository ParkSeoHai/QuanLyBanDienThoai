/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author anhha
 */
public class PhieuNhap {
    private int maPN;
    private String ngayNhap;
    private int maNCC;

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }
    
    public PhieuNhap() {}

    public PhieuNhap(int maPN, String ngayNhap, int maNCC) {
        this.maPN = maPN;
        this.ngayNhap = ngayNhap;
        this.maNCC = maNCC;
    }

    @Override
    public String toString() {
        return "PhieuNhap{" + "maPN=" + maPN + ", ngayNhap=" + ngayNhap + ", maNCC=" + maNCC + '}';
    }
}
