/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import Data.DBConnect;
import Interfaces.IPhieuNhap;
import Models.PhieuNhap;
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
public class PhieuNhapController extends DBConnect implements IPhieuNhap {
    private final java.sql.Connection conn;
    
    public PhieuNhapController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    @Override
    public boolean ThemPhieuNhap(PhieuNhap pn) {
        try {
            String query = "Insert into PhieuNhap(NgayNhap, MaNCC) values(TO_DATE(?, 'DD-MM-YYYY hh24:mi:ss'), ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, pn.getNgayNhap());
            ps.setInt(2, pn.getMaNCC());
            
            if(ps.executeUpdate() > 0) 
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean XoaPhieuNhap(int maPN) {
        String query = "Delete PhieuNhap where MaPN = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maPN);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức xóa phiếu nhập qua mã nhà cung cấp
    public boolean XoaPnQuaMaNCC(int maNCC) {
        String query = "Delete PhieuNhap where MaNCC = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNCC);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Phương thức lấy mã phiếu nhập
    public int getMaPN(PhieuNhap pn) throws SQLException {
        String query = "Select MaPN from PhieuNhap where NgayNhap = TO_DATE(?, 'DD-MM-YYYY hh24:mi:ss') and MaNCC = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, pn.getNgayNhap());
        ps.setInt(2, pn.getMaNCC());
        
        var rs = ps.executeQuery();
        if(rs.next()) 
            return rs.getInt(1);
        return 0;
    }

    @Override
    public LinkedList<Object[]> getData() {
        LinkedList<Object[]> lstPN = new LinkedList<>();
        // Lệnh truy vấn
        String query = "select pn.MaPN, sp.TenSP, ncc.TenNCC, ncc.DiaChi, ncc.DienThoai, sp.DvTinh, pn.NgayNhap, ctpn.SLNhap, ctpn.DGNhap "
                + "from PhieuNhap pn, NhaCungCap ncc, SanPham sp, CTPhieuNhap ctpn where pn.MaPN = ctpn.MaPN and sp.MaSP = ctpn.MaSP and pn.MaNCC = ncc.MaNCC";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            var rs = ps.executeQuery();
            while(rs.next()) {
                int maPN = rs.getInt(1);
                String tenSP = rs.getString(2);
                String tenNCC = rs.getString(3);
                String diaChi = rs.getString(4);
                String dienThoai = rs.getString(5);
                String dvTinh = rs.getString(6);
                String ngayNhap = rs.getString(7).toString();
                int slNhap = rs.getInt(8);
                double getdgNhap = rs.getDouble(9);
                BigDecimal dgNhap = new BigDecimal(getdgNhap);
                
                Object[] data = new Object[] {maPN, tenSP, tenNCC, diaChi, dienThoai, dvTinh, ngayNhap, slNhap, dgNhap};
                lstPN.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPN;
    }
}
