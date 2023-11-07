/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class SanPham {
    private int maSP;
    private String tenSP;
    private String dvTinh;
    private String MauSac;
    private String KichThuocManHinh;
    private String Ram;
    private String BoNho;
    private String Pin;
    private String HeDieuHanh;
    private String SoLuongHang;
    private double giaBan;
    
    public int getMaSP() {
        return maSP;
    }
    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }
    public String getTenSP() {
        return tenSP;
    }
    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
    public String getDvTinh() {
        return dvTinh;
    }
    public void setDvTinh(String dvTinh) {
        this.dvTinh = dvTinh;
    }
    public String getMauSac() {
        return MauSac;
    }
    public void setMauSac(String MauSac) {
        this.MauSac = MauSac;
    }
    public String getKichThuocManHinh() {
        return KichThuocManHinh;
    }
    public void setKichThuocManHinh(String KichThuocManHinh) {
        this.KichThuocManHinh = KichThuocManHinh;
    }
    public String getRam() {
        return Ram;
    }
    public void setRam(String Ram) {
        this.Ram = Ram;
    }
    public String getBoNho() {
        return BoNho;
    }
    public void setBoNho(String BoNho) {
        this.BoNho = BoNho;
    }
    public String getPin() {
        return Pin;
    }
    public void setPin(String Pin) {
        this.Pin = Pin;
    }
    public String getHeDieuHanh() {
        return HeDieuHanh;
    }
    public void setHeDieuHanh(String HeDieuHanh) {
        this.HeDieuHanh = HeDieuHanh;
    }
    public String getSoLuongHang() {
        return SoLuongHang;
    }
    public void setSoLuongHang(String SoLuongHang) {
        this.SoLuongHang = SoLuongHang;
    }
    public double getGiaBan() {
        return giaBan;
    }
    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
    public SanPham() {
    }
    public SanPham(int maSP, String tenSP, String dvTinh, String MauSac, String KichThuocManHinh, String Ram, String BoNho, String Pin, String HeDieuHanh, String SoLuongHang, double giaBan) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.dvTinh = dvTinh;
        this.MauSac = MauSac;
        this.KichThuocManHinh = KichThuocManHinh;
        this.Ram = Ram;
        this.BoNho = BoNho;
        this.Pin = Pin;
        this.HeDieuHanh = HeDieuHanh;
        this.SoLuongHang = SoLuongHang;
        this.giaBan = giaBan;
    }
}
