/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.PhieuNhap;
import java.util.LinkedList;

/**
 *
 * @author anhha
 */
public interface IPhieuNhap {
    public LinkedList<Object[]> getData();
    public boolean ThemPhieuNhap(PhieuNhap pn);
    public boolean XoaPhieuNhap(int id);
}
