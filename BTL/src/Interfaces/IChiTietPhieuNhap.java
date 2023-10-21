/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.CTPhieuNhap;

/**
 *
 * @author anhha
 */
public interface IChiTietPhieuNhap {
    public boolean ThemCTPhieuNhap(CTPhieuNhap ctpn);
    public boolean SuaCTPhieuNhap(CTPhieuNhap ctpn);
    public boolean XoaCTPhieuNhap(int maPN, int maSP);
}
