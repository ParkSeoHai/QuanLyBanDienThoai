/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.PhieuXuat;

/**
 *
 * @author anhha
 */
public interface IPhieuXuat {
    public boolean ThemPhieuXuat(PhieuXuat px);
    public boolean SuaPhieuXuat(PhieuXuat px);
    public boolean XoaPhieuXuat(int maPX);
}
