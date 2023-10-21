/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.IThongKe;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.LinkedList;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class ThongKeController extends DBConnect implements IThongKe {
    private final java.sql.Connection conn;
    public ThongKeController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }

    @Override
    public LinkedList<Object[]> ThongKeTongDT() {
        LinkedList<Object[]> lstTongDT = new LinkedList<>();
        String query = "select px.MaPX, TenKH, sp.TenSP, NgayXuat, SLXuat, DGXuat from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp "
                + "where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            var rs = ps.executeQuery();
            while(rs.next()) {
                int maPX = rs.getInt(1);
                String tenKH = rs.getString(2);
                String tenSP = rs.getString(3);
                String ngayXuat = rs.getString(4);
                int slXuat = rs.getInt(5);
                double getDgXuat = rs.getDouble(6);
                BigDecimal dgXuat = new BigDecimal(getDgXuat);
                
                Object[] obj = new Object[] {maPX, tenKH, tenSP, ngayXuat, slXuat, dgXuat.setScale(2, RoundingMode.HALF_UP)};
                lstTongDT.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstTongDT;
    }
    
    public LinkedList<Object[]> ThongKeTongDTTheoNgay(String StartDate, String EndDate) {
        LinkedList<Object[]> lstTongDT = new LinkedList<>();
        String query = "select px.MaPX, TenKH, sp.TenSP, NgayXuat, SLXuat, DGXuat from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp "
                + "where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP and ngayxuat between TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, StartDate);
            ps.setString(2, EndDate);
            
            var rs = ps.executeQuery();
            while(rs.next()) {
                int maPX = rs.getInt(1);
                String tenKH = rs.getString(2);
                String tenSP = rs.getString(3);
                String ngayXuat = rs.getString(4);
                int slXuat = rs.getInt(5);
                double getDgXuat = rs.getDouble(6);
                BigDecimal dgXuat = new BigDecimal(getDgXuat);
                
                Object[] obj = new Object[] {maPX, tenKH, tenSP, ngayXuat, slXuat, dgXuat.setScale(2, RoundingMode.HALF_UP)};
                lstTongDT.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstTongDT;
    }

    @Override
    public LinkedList<Object[]> ThongKeSpBanChay() {
        LinkedList<Object[]> lstSpBanChay = new LinkedList<>();
        String query = "SELECT sp.MaSP, sp.TenSP, Sum(ctpx.SLXuat), sp.GiaBan FROM CTPhieuXuat ctpx " 
                        + "INNER JOIN SanPham sp ON ctpx.MaSP = sp.MaSP GROUP BY sp.MaSP, sp.TenSP, sp.GiaBan "
                        + "ORDER BY Sum(ctpx.SLXuat) DESC FETCH FIRST 10 ROWS ONLY";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            var rs = ps.executeQuery();
            while(rs.next()) {
                int maSP = rs.getInt(1);
                String tenSp = rs.getString(2);
                int slXuat = rs.getInt(3);
                double getGiaBan = rs.getDouble(4);
                BigDecimal giaBan = new BigDecimal(getGiaBan);
                
                Object[] obj = new Object[] {maSP, tenSp, slXuat, giaBan.setScale(2, RoundingMode.HALF_UP)};
                lstSpBanChay.add(obj);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstSpBanChay;
    }
    
    // Phương thức tính tổng doanh thu
    public double getTongDT() throws SQLException {
        String query = "Select Sum(DGXuat*SLXuat) from CTPhieuXuat";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next()) 
            return rs.getDouble(1);
        return 0;
    }
    
    // Phương thức tính tổng doanh thu khi lọc theo ngày
    public double getTongDTTheoNgay(String StartDate, String EndDate) throws SQLException {
        String query = "select Sum(DGXuat*SLXuat) from PhieuXuat px, CTPhieuXuat ctpx, SanPham sp "
                + "where px.MaPX = ctpx.MaPX and ctpx.MaSP = sp.MaSP and ngayxuat between TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, StartDate);
        ps.setString(2, EndDate);
        
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getDouble(1);
        return 0;
    }
}
