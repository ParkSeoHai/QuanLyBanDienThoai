/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class CTPhieuXuat {
    private int maPX;
    private int maSP;
    private int slXuat;
    private Float dgXuat;

    public int getMaPX() {
        return maPX;
    }

    public void setMaPX(int maPX) {
        this.maPX = maPX;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSlXuat() {
        return slXuat;
    }

    public void setSlXuat(int slXuat) {
        this.slXuat = slXuat;
    }

    public Float getDgXuat() {
        return dgXuat;
    }

    public void setDgXuat(Float dgXuat) {
        this.dgXuat = dgXuat;
    }

    public CTPhieuXuat() {
    }

    public CTPhieuXuat(int maPX, int maSP, int slXuat, Float dgXuat) {
        this.maPX = maPX;
        this.maSP = maSP;
        this.slXuat = slXuat;
        this.dgXuat = dgXuat;
    }
}
