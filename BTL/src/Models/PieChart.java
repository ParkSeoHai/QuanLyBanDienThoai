/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author anhha
 */
public class PieChart {
    private String tenSP;
    private int slBan;

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSlBan() {
        return slBan;
    }

    public void setSlBan(int slBan) {
        this.slBan = slBan;
    }
    
    public PieChart() {}

    public PieChart(String tenSP, int slBan) {
        this.tenSP = tenSP;
        this.slBan = slBan;
    }
}
