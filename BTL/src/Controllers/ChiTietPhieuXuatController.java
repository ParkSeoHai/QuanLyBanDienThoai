/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.IChiTietPhieuXuat;
import Models.CTPhieuXuat;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class ChiTietPhieuXuatController extends DBConnect implements IChiTietPhieuXuat{
    private final java.sql.Connection conn;
    public ChiTietPhieuXuatController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    @Override
    public boolean Them(CTPhieuXuat ctpx) {
        try {
            String query = "Insert into CTPhieuXuat values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ctpx.getMaPX());
            ps.setInt(2, ctpx.getMaSP());
            ps.setInt(3, ctpx.getSlXuat());
            ps.setFloat(4, ctpx.getDgXuat());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean Sua(CTPhieuXuat ctpx) {
        try {
            String query = "Update CTPhieuXuat set MaSP = ?, SLXuat = ?, DGXuat = ? where MaPX = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, ctpx.getMaSP());
            ps.setInt(2, ctpx.getSlXuat());
            ps.setFloat(3, ctpx.getDgXuat());
            ps.setInt(4, ctpx.getMaPX());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean Xoa(int maPX) {
        try {
            String query = "Delete CTPhieuXuat where MaPX = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maPX);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietPhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức lấy số lượng xuất
    public int getSoLuongXuat(int maPX) throws SQLException {
        String query = "Select SLXuat from CTPhieuXuat where MaPX = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, maPX);
        
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        
        return -1;
    }
}
