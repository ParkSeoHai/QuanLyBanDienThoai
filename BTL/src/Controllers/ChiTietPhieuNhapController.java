/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.IChiTietPhieuNhap;
import Models.CTPhieuNhap;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class ChiTietPhieuNhapController extends DBConnect implements IChiTietPhieuNhap {
    private final java.sql.Connection conn;
    
    public ChiTietPhieuNhapController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    @Override
    public boolean ThemCTPhieuNhap(CTPhieuNhap ctpn) {
        String query = "Insert into CTPhieuNhap values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ctpn.getMaPN());
            ps.setInt(2, ctpn.getMaSP());
            ps.setInt(3, ctpn.getSlNhap());
            ps.setDouble(4, ctpn.getDgNhap());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean SuaCTPhieuNhap(CTPhieuNhap ctpn) {
        String query = "Update CTPhieuNhap set SlNhap = ?, DgNhap = ? where MaPN = ? and MaSP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ctpn.getSlNhap());
            ps.setDouble(2, ctpn.getDgNhap());
            ps.setInt(3, ctpn.getMaPN());
            ps.setInt(4, ctpn.getMaSP());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean XoaCTPhieuNhap(int maPN, int maSP) {
        try {
            String query = "Delete CTPhieuNhap where MaPN = ? and MaSP = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maPN);
            ps.setInt(2, maSP);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
