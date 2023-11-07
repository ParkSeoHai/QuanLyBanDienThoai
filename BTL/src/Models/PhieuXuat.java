/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class PhieuXuat {
    private int maPX;
    private String ngayXuat;
    private String tenKH;
    
    public int getMaPX() {
        return maPX;
    }
    public void setMaPX(int maPX) {
        this.maPX = maPX;
    }
    public String getNgayXuat() {
        return ngayXuat;
    }
    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }
    public String getTenKH() {
        return tenKH;
    }
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
    public PhieuXuat() {}
    public PhieuXuat(int maPX, String ngayXuat, String tenKH) {
        this.maPX = maPX;
        this.ngayXuat = ngayXuat;
        this.tenKH = tenKH;
    }
}
