/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.INhaCungCap;
import Models.NhaCungCap;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhha
 */
public class NhaCungCapController extends DBConnect implements INhaCungCap {
    private java.sql.Connection conn;
    public NhaCungCapController() throws ClassNotFoundException, SQLException {
        this.conn =  getConnect();
    }

    @Override
    public boolean ThemNCC(NhaCungCap ncc) {
        String query = "Insert into NhaCungCap(TenNCC, DiaChi, DienThoai) values(?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSdt());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean SuaNCC(NhaCungCap ncc) {
        String query = "Update NhaCungCap set TenNCC = ?, DiaChi = ?, DienThoai = ? where MaNCC = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSdt());
            ps.setInt(4, ncc.getMaNCC());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean XoaNCC(int maNCC) {
        try {
            // Kiểm tra phiếu nhập có dữ liệu của nhà cung cấp hay k
            PhieuNhapController controlPN = new PhieuNhapController();
            String queryPn = "Select * from PhieuNhap where MaNCC = ?";
            PreparedStatement psPN = conn.prepareStatement(queryPn);
            psPN.setInt(1, maNCC);;
            var rs = psPN.executeQuery();
            if(rs.next())
                controlPN.XoaPnQuaMaNCC(maNCC);
            
            // Xóa thông tin nhà cung cấp
            String query = "Delete NhaCungCap where MaNCC = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNCC);
            return ps.executeUpdate() > 0;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức lấy mã nhà cung cấp
    public int getMaNCC(NhaCungCap ncc) throws SQLException {
        String query = "Select MaNCC from NhaCungCap where TenNCC = ? and DiaChi = ? and DienThoai = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, ncc.getTenNCC());
        ps.setString(2, ncc.getDiaChi());
        ps.setString(3, ncc.getSdt());
        
        var rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    
    // Phương thức lấy mã nhà cung cấp thông qua mã phiếu nhập
    public int getMaNCC(int maPN) throws SQLException {
        String query = "Select MaNCC from PhieuNhap where MaPN = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, maPN);
        
        var rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    
    // Lấy dữ liệu
    public LinkedList<Object[]> getData() throws SQLException {
        String query = "Select * from NhaCungCap";
        
        LinkedList<Object[]> lstNcc = new LinkedList<>();
        
        PreparedStatement ps = conn.prepareStatement(query);
        var rs = ps.executeQuery();
        while(rs.next()) {
            int MaNCC = rs.getInt(1);
            String TenNCC = rs.getString(2);
            String DiaChi = rs.getString(3);
            String Sdt = rs.getString(4);
            
            Object[] obj = new Object[] {MaNCC, TenNCC, DiaChi, Sdt};
            lstNcc.add(obj);
        }
        return lstNcc;
    }
    
    // Phương thức kiểm tra input Search có phải số ko
    public boolean isSeachInt(String search) {
        try {
            int MaNCC = Integer.parseInt(search);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public LinkedList<Object[]> TimKiemNCC(String searchString) {
        String query = "select * from nhacungcap where mancc like ? or tenncc like ?";
        LinkedList<Object[]> lstNcc = new LinkedList<>();
        
        int MaNCC = 0;
        // Kiểm tra có phải số không
        if(isSeachInt(searchString)) {
            MaNCC = Integer.parseInt(searchString);
        }
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%"+MaNCC+"%");
            ps.setString(2, "%"+searchString+"%");
            
            var rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt(1);
                String TenNCC = rs.getString(2);
                String DiaChi = rs.getString(3);
                String Sdt = rs.getString(4);

                Object[] obj = new Object[] {id, TenNCC, DiaChi, Sdt};
                lstNcc.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhaCungCapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstNcc;
    }
}
