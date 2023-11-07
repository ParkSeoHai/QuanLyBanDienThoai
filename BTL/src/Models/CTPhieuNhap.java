/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class CTPhieuNhap {
    private int maPN;
    private int maSP;
    private int slNhap;
    private double dgNhap;

    public int getMaPN() {
        return maPN;
    }
    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }
    public int getMaSP() {
        return maSP;
    }
    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }
    public int getSlNhap() {
        return slNhap;
    }
    public void setSlNhap(int slNhap) {
        this.slNhap = slNhap;
    }
    public double getDgNhap() {
        return dgNhap;
    }
    public void setDgNhap(double dgNhap) {
        this.dgNhap = dgNhap;
    }
    public CTPhieuNhap() {}
    public CTPhieuNhap(int maPN, int maSP, int slNhap, double dgNhap) {
        this.maPN = maPN;
        this.maSP = maSP;
        this.slNhap = slNhap;
        this.dgNhap = dgNhap;
    }
}
