/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.SanPham;

/**
 *
 * @author anhha
 */
public interface ISanPham {
    public boolean ThemSanPham(SanPham sp);
    public boolean SuaSanPham(SanPham sp);
    public boolean XoaSanPham(int maSP);
}
