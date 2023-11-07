/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.ISanPham;
import Models.SanPham;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class SanPhamController extends DBConnect implements ISanPham {
    private final java.sql.Connection conn;
    
    public SanPhamController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    @Override
    public boolean ThemSanPham(SanPham sp) {
        try {
            String query = "Insert into SanPham(TenSp, DvTinh, GiaBan, MauSac, KichThuocManHinh, Ram, BoNho, Pin, HeDieuHanh, SoLuongHang) "
                    + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getDvTinh());
            ps.setDouble(3, sp.getGiaBan());
            ps.setString(4, sp.getMauSac());
            ps.setString(5, sp.getKichThuocManHinh());
            ps.setString(6, sp.getRam());
            ps.setString(7, sp.getBoNho());
            ps.setString(8, sp.getPin());
            ps.setString(9, sp.getHeDieuHanh());
            ps.setString(10, sp.getSoLuongHang());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean SuaSanPham(SanPham sp) {
        String query = "Update SanPham set TenSP = ?, DvTinh = ?, GiaBan = ?, MauSac = ?, KichThuocManHinh = ?,"
                + " Ram = ?, BoNho = ?, Pin = ?, HeDieuHanh = ?, SoLuongHang = ? where MaSP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getDvTinh());
            ps.setDouble(3, sp.getGiaBan());
            ps.setString(4, sp.getMauSac());
            ps.setString(5, sp.getKichThuocManHinh());
            ps.setString(6, sp.getRam());
            ps.setString(7, sp.getBoNho());
            ps.setString(8, sp.getPin());
            ps.setString(9, sp.getHeDieuHanh());
            ps.setString(10, sp.getSoLuongHang());
            ps.setInt(11, sp.getMaSP());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean XoaSanPham(int maSP) {
        String query = "Delete SanPham where MaSP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức lấy mã sản phẩm
    public int getMaSP(SanPham sp) throws SQLException {
        String query = "Select MaSP from SanPham where TenSp = ? and DvTinh = ? and GiaBan = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, sp.getTenSP());
        ps.setString(2, sp.getDvTinh());
        ps.setDouble(3, sp.getGiaBan());
        
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
    
    // Phương thức lấy mã sản phẩm thông qua mã phiếu nhập
    public int getMaSP(int maPN) throws SQLException {
        String query = "select sp.masp from sanpham sp, ctphieunhap ct where sp.masp = ct.masp and ct.mapn = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, maPN);
        
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
    
    // Phương thức lấy dữ liệu sản phẩm
    public LinkedList<Object[]> getData() throws SQLException {
        String query = "Select * from SanPham";
        LinkedList<Object[]> lstSP = new LinkedList<>();
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        while(rs.next()) {
            int MaSP = rs.getInt(1);
            String tenSP = rs.getString(2);
            String dvTinh = rs.getString(3);
            double getGiaBan = rs.getFloat(4);
            BigDecimal giaBan = new BigDecimal(getGiaBan);
            
            String mauSac = rs.getString(5);
            String kichThuocMH = rs.getString(6);
            String Ram = rs.getString(7);
            String boNhoTrong = rs.getString(8);
            String pin = rs.getString(9);
            String heDieuHanh = rs.getString(10);
            String soLuong = rs.getString(11);
            
            Object[] obj = new Object[] {MaSP, tenSP, dvTinh, giaBan, mauSac, kichThuocMH, Ram, boNhoTrong, pin, heDieuHanh, soLuong};
            lstSP.add(obj);
        }
        return lstSP;
    }
    
    // Phương thức tìm kiếm theo mã sản phẩm
    public LinkedList<Object[]> timKiemTheoMaSP(int maSpInput) throws SQLException {
        String query = "Select * from SanPham where MaSP like ?";
        LinkedList<Object[]> lstSP = new LinkedList<>();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, "%"+maSpInput+"%");
        
        var rs = ps.executeQuery();
        while(rs.next()) {
            int MaSP = rs.getInt(1);
            String tenSP = rs.getString(2);
            String dvTinh = rs.getString(3);
            double getGiaBan = rs.getFloat(4);
            BigDecimal giaBan = new BigDecimal(getGiaBan);
            String mauSac = rs.getString(5);
            String kichThuocMH = rs.getString(6);
            String Ram = rs.getString(7);
            String boNhoTrong = rs.getString(8);
            String pin = rs.getString(9);
            String heDieuHanh = rs.getString(10);
            String soLuong = rs.getString(11);
            
            Object[] obj = new Object[] {MaSP, tenSP, dvTinh, giaBan, mauSac, kichThuocMH, Ram, boNhoTrong, pin, heDieuHanh, soLuong};
            lstSP.add(obj);
        }
        return lstSP;
    }
    
    // Phương thức tìm kiếm theo tên sản phẩm
    public LinkedList<Object[]> timKiemTheoTenSP(String tenSpInput) throws SQLException {
        String query = "SELECT * FROM SanPham WHERE TenSP Like ?";
        LinkedList<Object[]> lstSP = new LinkedList<>();
        
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, "%"+tenSpInput+"%");
        
        var rs = ps.executeQuery();
        while(rs.next()) {
            int MaSP = rs.getInt(1);
            String tenSP = rs.getString(2);
            String dvTinh = rs.getString(3);
            double getGiaBan = rs.getFloat(4);
            BigDecimal giaBan = new BigDecimal(getGiaBan);
            String mauSac = rs.getString(5);
            String kichThuocMH = rs.getString(6);
            String Ram = rs.getString(7);
            String boNhoTrong = rs.getString(8);
            String pin = rs.getString(9);
            String heDieuHanh = rs.getString(10);
            String soLuong = rs.getString(11);
            
            Object[] obj = new Object[] {MaSP, tenSP, dvTinh, giaBan, mauSac, kichThuocMH, Ram, boNhoTrong, pin, heDieuHanh, soLuong};
            lstSP.add(obj);
        }
        return lstSP;
    }
    
    // Phương thức get danh sách mã sản phẩm
    public LinkedList<Integer> getLstMaSP() throws SQLException{
        LinkedList<Integer> lstMaSP = new LinkedList<>();
        String query = "Select MaSP from SanPham";
        PreparedStatement ps = conn.prepareStatement(query);
        
        var rs = ps.executeQuery();
        while(rs.next()) {
            lstMaSP.add(rs.getInt(1));
        }
        return lstMaSP;
    }
    
    // Phương thức get số lượng hàng
    public int getSoLuong(int MaSP) throws SQLException {
        String query = "Select SoLuongHang from SanPham where MaSP = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, MaSP);
        
        var rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        
        return 0;
    }
    
    // Phương thức thay đổi số lượng sản phẩm khi tạo phiếu xuất
    public boolean updateSoLuongSP(int maSP, int soLuongDau, int soLuongXuat) throws SQLException {
        int soLuongNew = soLuongDau - soLuongXuat;
        String query = "Update SanPham set SoLuongHang = ? where MaSP = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, soLuongNew);
        ps.setInt(2, maSP);
        
        return ps.executeUpdate() > 0;
    }
    
    // Phương thức get thông tin sản phẩm
    public SanPham getSanPham(int maSP) throws SQLException {
        String query = "Select * from SanPham where MaSP = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, maSP);
        
        var rs = ps.executeQuery();
        if(rs.next()) {
            int MaSP = rs.getInt(1);
            String tenSP = rs.getString(2);
            String dvTinh = rs.getString(3);
            double giaBan = rs.getFloat(4);
            String mauSac = rs.getString(5);
            String kichThuocMH = rs.getString(6);
            String Ram = rs.getString(7);
            String boNhoTrong = rs.getString(8);
            String pin = rs.getString(9);
            String heDieuHanh = rs.getString(10);
            String soLuong = rs.getString(11);
            
            SanPham sp = new SanPham(MaSP, tenSP, dvTinh, mauSac, kichThuocMH, Ram, boNhoTrong, pin, heDieuHanh, soLuong, giaBan);
            return sp;
        }
        return null;
    }
}
