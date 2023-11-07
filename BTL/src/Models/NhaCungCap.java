/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class NhaCungCap {
    private int maNCC;
    private String tenNCC;
    private String diaChi;
    private String sdt;

    public int getMaNCC() {
        return maNCC;
    }
    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }
    public String getTenNCC() {
        return tenNCC;
    }
    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public NhaCungCap() {}
    public NhaCungCap(int maNCC, String tenNCC, String diaChi, String sdt) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }
}
