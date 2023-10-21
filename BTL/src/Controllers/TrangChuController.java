/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Models.PieChart;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;

/**
 *
 * @author anhha
 */
public class TrangChuController extends DBConnect{
    private final java.sql.Connection conn;
    
    public TrangChuController() throws SQLException, ClassNotFoundException {
        this.conn = getConnect();
    }
    
    // Phương thức lấy tổng số sản phẩm trong csdl
    public int getTongSanPham() throws SQLException, IOException {
        int TongSP = 0;
        
        String query = "Select Count(*) from SanPham";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next()) {
            TongSP = rs.getInt(1);
        }
        return TongSP;
    }
    
    // Phương thức lấy tổng số tài khoản trong csdl
    public int getTongTaiKhoan() throws SQLException, IOException {
        int TongTK = 0;
        String query = "Select count(*) from TaiKhoan";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next()) {
            TongTK = rs.getInt(1);
        }
        return TongTK;
    }
    
    // Phương thức lấy tổng số phiếu nhập
    public int getTongPhieuNhap() throws SQLException, IOException {
        int TongPN = 0;
        String query = "Select count(*) from PhieuNhap";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next()) {
            TongPN = rs.getInt(1);
        }
        return TongPN;
    }
    
    // Phương thức lấy tổng số phiếu xuất
    public int getTongPhieuXuat() throws SQLException, IOException {
        int TongPX = 0;
        String query = "Select count(*) from PhieuXuat";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        if(rs.next()) {
            TongPX = rs.getInt(1);
        }
        return TongPX;
    }
    
    // Phương thức get dữ liệu sản phẩm mới
    public LinkedList<Object[]> getData() throws SQLException {
        LinkedList<Object[]> lstSP = new LinkedList<>();
        
        String query = "Select MaSP, TenSP, GiaBan from SanPham order by MaSP desc fetch first 10 rows only";
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        while(rs.next()) {
            int maSp = rs.getInt(1);
            String tenSP = rs.getString(2);
            double getGiaBan = rs.getDouble(3);
            BigDecimal giaBan = new BigDecimal(getGiaBan);
            
            Object[] data = new Object[] {maSp, tenSP, giaBan.setScale(2, RoundingMode.HALF_UP)};
            lstSP.add(data);
        }
        return lstSP;
    }
    
    // Phương thức get danh sách 5 sản phẩm bán chạy nhất
    public LinkedList<PieChart> getLstPieChart() throws SQLException {
        LinkedList<PieChart> lstPie = new LinkedList<>();
        String query = "SELECT sp.TenSP, Sum(ctpx.SLXuat) FROM CTPhieuXuat ctpx "
        + "INNER JOIN SanPham sp ON ctpx.MaSP = sp.MaSP GROUP BY sp.TenSP "
        + "ORDER BY Sum(ctpx.SLXuat) DESC FETCH FIRST 5 ROWS ONLY";
        
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        while(rs.next()) {
            PieChart pie = new PieChart(rs.getString(1), rs.getInt(2));
            lstPie.add(pie);
        }
        return lstPie;
    }
}
