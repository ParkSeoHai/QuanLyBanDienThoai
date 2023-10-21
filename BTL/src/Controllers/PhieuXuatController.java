/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.IPhieuXuat;
import Models.PhieuXuat;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class PhieuXuatController extends DBConnect implements IPhieuXuat {
    private final java.sql.Connection conn;
    
    public PhieuXuatController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    // Phương thức get dữ liệu
    public LinkedList<Object[]> getData() throws SQLException {
        String query = "select px.MaPX, TenKH, sp.MaSP, sp.TenSP, NgayXuat, SLXuat, DGXuat from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp" 
                + " where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP";
        
        LinkedList<Object[]> lstPX = new LinkedList<>();
        
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        while(rs.next()) {
            int MaPX = rs.getInt(1);
            String TenKH = rs.getString(2);
            int MaSP = rs.getInt(3);
            String TenSP = rs.getString(4);
            String NgayXuat = rs.getString(5);
            int SoLuongXuat = rs.getInt(6);
            double getDgXuat = rs.getFloat(7);
            BigDecimal DgXuat = new BigDecimal(getDgXuat);
            
            Object[] obj = new Object[] {MaPX, TenKH, MaSP, TenSP, NgayXuat, SoLuongXuat, DgXuat};
            lstPX.add(obj);
        }
        return lstPX;
    }

    @Override
    public boolean ThemPhieuXuat(PhieuXuat px) {
        try {
            String query = "Insert into PhieuXuat(NgayXuat, TenKH) values(TO_DATE(?, 'DD-MM-YYYY hh24:mi:ss'), ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, px.getNgayXuat());
            ps.setString(2, px.getTenKH());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean SuaPhieuXuat(PhieuXuat px) {
        try {
            String query = "Update PhieuXuat set TenKH = ? where MaPX = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, px.getTenKH());
            ps.setInt(2, px.getMaPX());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean XoaPhieuXuat(int maPX) {
        try {
            ChiTietPhieuXuatController controlCTPX = new ChiTietPhieuXuatController();
            controlCTPX.Xoa(maPX);
            
            String query = "Delete PhieuXuat where MaPX = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maPX);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức get mã phiếu xuất vừa thêm vào csdl
    public int getMaPxNew() throws SQLException {
        String query = "Select Max(MaPX) from PhieuXuat";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
}
