/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Data.DBConnect;
import Interfaces.IDangNhap;
import Models.TaiKhoan;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author anhha
 */
public class DangNhapController extends DBConnect implements IDangNhap {
    private final java.sql.Connection conn;
    
    public DangNhapController() throws ClassNotFoundException, SQLException {
        this.conn = getConnect();
    }
    
    @Override
    public boolean DangNhap(TaiKhoan tk) {
        String query = "Select * from TaiKhoan where TENDN = ? and MATKHAU = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, tk.getTenDN());
            ps.setString(2, tk.getMatKhau());
            var result = ps.executeQuery();
            if(result.next()) {
                closeConnect(conn);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) { 
            Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Close connect
        try {
            closeConnect(conn);
        } catch (IOException ex) {
            Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
