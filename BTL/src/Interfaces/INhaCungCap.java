/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.NhaCungCap;
import java.util.LinkedList;

/**
 *
 * @author anhha
 */
public interface INhaCungCap {
    public boolean ThemNCC(NhaCungCap ncc);
    public boolean SuaNCC(NhaCungCap ncc);
    public boolean XoaNCC(int id);
    public LinkedList<Object[]> TimKiemNCC(String searchString);
}
