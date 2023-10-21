/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Controllers.ChiTietPhieuNhapController;
import Controllers.ChiTietPhieuXuatController;
import Controllers.NhaCungCapController;
import Controllers.PhieuNhapController;
import Controllers.PhieuXuatController;
import Controllers.SanPhamController;
import Controllers.ThongKeController;
import Controllers.TrangChuController;
import Models.CTPhieuNhap;
import Models.CTPhieuXuat;
import Models.NhaCungCap;
import Models.PhieuNhap;
import Models.PhieuXuat;
import Models.PieChart;
import Models.SanPham;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author anhha
 */
public class Trangchu extends javax.swing.JFrame {
    // Khai báo Đối tượng controller xử lý trang chủ
    private TrangChuController controlTC;
    // Khai báo Đối tượng controller xử lý nhà cung cấp
    private NhaCungCapController controlNCC;
    // Khai báo Đối tượng controller xử lý sản phẩm
    private SanPhamController controlSP;
    // Khai báo Đối tượng controller xử lý phiếu nhập
    private PhieuNhapController controlPN;
    // Khai báo Đối tượng controler xử lý chi tiết phiếu nhập
    private ChiTietPhieuNhapController controlCTPN;
    // Khai báo Đối tượng controller xử lý phiếu xuất
    private PhieuXuatController controlPX;
    // Khai báo Đối tượng controler xử lý chi tiết phiếu xuất
    private ChiTietPhieuXuatController controlCTPX;
    // Khai báo Đối tượng controler xử lý thống kê
    private ThongKeController controlTK;
    // Lưu tên tài khoản đăng nhập
    private String TenTK = Dangnhap.TenTK;
    // Lưu thông tin mã nhà cung cấp
    private int maNCC;
    // Lưu thông tin mã sản phẩm
    private int maSP;
    // Lưu thông tin mã phiếu xuất
    private int maPX;
    
    public Trangchu() throws SQLException, ClassNotFoundException, IOException {
        initComponents();
        loadForm();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    // Load form
    private void loadForm() throws SQLException, ClassNotFoundException, IOException {
        controlTC = new TrangChuController();
        
        // Hiển thị panel Trang chủ
        panelTrangChu.setVisible(true);
        panelNhapHang.setVisible(false);
        panelXuatHang.setVisible(false);
        panelQuanLyNCC.setVisible(false);
        panelQLSP.setVisible(false);
        panelTKTongDT.setVisible(false);
        panelTKSpBanChay.setVisible(false);
        
        // Hiển thị số liệu
        lbTenDN.setText("Xin chào " + TenTK);
        lbTongSP.setText(Integer.toString(controlTC.getTongSanPham()));
        lbTongTK.setText(Integer.toString(controlTC.getTongTaiKhoan()));
        lbTongPN.setText(Integer.toString(controlTC.getTongPhieuNhap()));
        lbTongPX.setText(Integer.toString(controlTC.getTongPhieuXuat()));
        
        // Hiển thị dữ liệu table
        String[] columnsName = {"Mã sản phẩm", "Tên sản phẩm", "Giá bán"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbSanPham.setModel(model);
        tbSanPham.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbSanPham.setFillsViewportHeight(true);
        
        LinkedList<Object[]> lstSP = controlTC.getData();
        for(var obj : lstSP) {
            model.addRow(obj);
        }
        
        showPieChart();
    }
    
    // Phương thức hiển thị biểu đồ hình tròn
    private void showPieChart(){
       try {
            //create dataset
            DefaultPieDataset barDataset = new DefaultPieDataset();
            // Biến lưu tên sản phẩm
            String tenSP1 = "";
            String tenSP2 = "";
            String tenSP3 = "";
            String tenSP4 = "";
            String tenSP5 = "";
            
            // Get danh sách top 5 sản phẩm
            controlTC = new TrangChuController();
            LinkedList<PieChart> lstPies = controlTC.getLstPieChart();
            
            int i = 0;
            for(var pie : lstPies) {
                barDataset.setValue(pie.getTenSP(), pie.getSlBan());
                if(i == 0) 
                    tenSP1 = pie.getTenSP();
                else if(i == 1) 
                    tenSP2 = pie.getTenSP();
                else if(i == 2)
                    tenSP3 = pie.getTenSP();
                else if(i == 3)
                    tenSP4 = pie.getTenSP();
                else
                    tenSP5 = pie.getTenSP();
                
                ++i;
            }

            //create chart
            JFreeChart piechart = ChartFactory.createPieChart("Top 5 sản phẩm bán nhiều nhất",barDataset, false,true,false);//explain
            PiePlot piePlot =(PiePlot) piechart.getPlot();

            //changing pie chart blocks colors
            piePlot.setSectionPaint(tenSP1, new Color(255,255,102));
            piePlot.setSectionPaint(tenSP2, new Color(102,255,102));
            piePlot.setSectionPaint(tenSP3, new Color(255,102,153));
            piePlot.setSectionPaint(tenSP4, new Color(0,204,204));
            piePlot.setSectionPaint(tenSP5, new Color(255,153,255));
            
            piePlot.setBackgroundPaint(Color.white);
            //create chartPanel to display chart(graph)
            ChartPanel barChartPanel = new ChartPanel(piechart);
            panelBarChart.removeAll();
            panelBarChart.add(barChartPanel, BorderLayout.CENTER);
            panelBarChart.validate();
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField7 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnHome = new javax.swing.JButton();
        btnQLNCC = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnQLSP = new javax.swing.JButton();
        cbQLNX = new javax.swing.JComboBox<>();
        cbThongKe = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbTenDN = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelTrangChu = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        panelHistogram = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbTongSP = new javax.swing.JLabel();
        panelBarChart = new javax.swing.JPanel();
        panelLineChart = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbTongPN = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelLineChart1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbTongTK = new javax.swing.JLabel();
        panelLineChart2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lbTongPX = new javax.swing.JLabel();
        panelNhapHang = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTenNccNhap = new javax.swing.JTextField();
        txtDiaChiNccNhap = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSdtNccNhap = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTenSpNhap = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDvTinhNhap = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtDGNhap = new javax.swing.JTextField();
        txtSoLuongSpNhap = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPhieuNhap = new javax.swing.JTable();
        btnLuuPN = new javax.swing.JButton();
        btnHuyPN = new javax.swing.JButton();
        btnXoaPN = new javax.swing.JButton();
        btnSuaPN = new javax.swing.JButton();
        panelXuatHang = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtSoLuongXuat = new javax.swing.JTextField();
        txtTenKhXuat = new javax.swing.JTextField();
        txtDgXuat = new javax.swing.JTextField();
        cbMaSpPX = new javax.swing.JComboBox<>();
        lbTenSpPX = new javax.swing.JLabel();
        lbSluongSpPX = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbPhieuXuat = new javax.swing.JTable();
        btnThemPX = new javax.swing.JButton();
        btnSuaPX = new javax.swing.JButton();
        btnXoaPX = new javax.swing.JButton();
        btnHuyPX = new javax.swing.JButton();
        panelQuanLyNCC = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        textTenNCC = new javax.swing.JTextField();
        textDcNCC = new javax.swing.JTextField();
        textSdtNCC = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbQuanLyNCC = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtTimKiemNCC = new javax.swing.JTextField();
        btnSuaNCC = new javax.swing.JButton();
        btnXoaNCC = new javax.swing.JButton();
        btnTimKiemNCC = new javax.swing.JButton();
        btnThemNCC = new javax.swing.JButton();
        panelQLSP = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        textTenSP = new javax.swing.JTextField();
        textHeDieuHanh = new javax.swing.JTextField();
        textMauSP = new javax.swing.JTextField();
        textBoNhoRAM = new javax.swing.JTextField();
        textPINSP = new javax.swing.JTextField();
        textDvTinh = new javax.swing.JTextField();
        textBoNhoTrong = new javax.swing.JTextField();
        textSoLuong = new javax.swing.JTextField();
        textGiaBan = new javax.swing.JTextField();
        textKichThuocMH = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnTimKiemSP = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSanPham = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        txtTimKiemSP = new javax.swing.JTextField();
        cbTimKiemSP = new javax.swing.JComboBox<>();
        panelTKTongDT = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbThongKeDT = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        lbTongDT = new javax.swing.JLabel();
        btnPrintThongKeDT = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        txtSearchTkStart = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtSearchTkEnd = new javax.swing.JTextField();
        btnSearchThongKeDT = new javax.swing.JButton();
        btnRefreshThongKeDT = new javax.swing.JButton();
        panelTKSpBanChay = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbThongKeSpBanChay = new javax.swing.JTable();
        btnPrintSpBanChay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý bán điện thoại -Form");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setToolTipText("");
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 153, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-100x100.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 100));

        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHome.setForeground(new java.awt.Color(51, 51, 51));
        btnHome.setText("   Trang chủ");
        btnHome.setToolTipText("");
        btnHome.setAlignmentX(10.0F);
        btnHome.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        jPanel3.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 180, 36));

        btnQLNCC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQLNCC.setForeground(new java.awt.Color(51, 51, 51));
        btnQLNCC.setText("   Quản lý nhà cung cấp");
        btnQLNCC.setToolTipText("");
        btnQLNCC.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        btnQLNCC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQLNCC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQLNCCMouseClicked(evt);
            }
        });
        btnQLNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLNCCActionPerformed(evt);
            }
        });
        jPanel3.add(btnQLNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 180, 36));

        btnThoat.setBackground(new java.awt.Color(255, 153, 255));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(51, 51, 51));
        btnThoat.setText("   Thoát");
        btnThoat.setToolTipText("");
        btnThoat.setAlignmentX(0.5F);
        btnThoat.setBorder(null);
        btnThoat.setBorderPainted(false);
        btnThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThoat.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoatMouseClicked(evt);
            }
        });
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel3.add(btnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 180, 36));

        btnQLSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnQLSP.setForeground(new java.awt.Color(51, 51, 51));
        btnQLSP.setText("   Quản lý sản phẩm");
        btnQLSP.setToolTipText("");
        btnQLSP.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        btnQLSP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQLSP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQLSPMouseClicked(evt);
            }
        });
        btnQLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLSPActionPerformed(evt);
            }
        });
        jPanel3.add(btnQLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 180, 36));

        cbQLNX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbQLNX.setForeground(new java.awt.Color(51, 51, 51));
        cbQLNX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "  Quản lý nhập xuất", "  Nhập hàng", "  Xuất hàng" }));
        cbQLNX.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        cbQLNX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbQLNXItemStateChanged(evt);
            }
        });
        cbQLNX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbQLNXActionPerformed(evt);
            }
        });
        jPanel3.add(cbQLNX, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 180, 36));

        cbThongKe.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cbThongKe.setForeground(new java.awt.Color(51, 51, 51));
        cbThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "  Thống kê báo cáo", "  Tổng doanh thu", "  Sản phẩm bán chạy" }));
        cbThongKe.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        cbThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbThongKeActionPerformed(evt);
            }
        });
        jPanel3.add(cbThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 180, 36));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 180, 570));

        jPanel1.setBackground(new java.awt.Color(255, 204, 255));

        jPanel6.setBackground(new java.awt.Color(255, 204, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/male_user_50px.png"))); // NOI18N
        jLabel3.setAlignmentY(0.0F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel3.setIconTextGap(2);
        jLabel3.setMaximumSize(new java.awt.Dimension(30, 30));
        jLabel3.setMinimumSize(new java.awt.Dimension(30, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(30, 30));
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 50));

        lbTenDN.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbTenDN.setText("Xin chào, ");
        jPanel6.add(lbTenDN, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, -2, -1, 50));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("PHẦN MỀM QUẢN LÝ BÁN ĐIỆN THOẠI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(139, 139, 139)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 750, 50));

        panelTrangChu.setBackground(new java.awt.Color(255, 255, 255));
        panelTrangChu.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, null));
        panelTrangChu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbSanPham.setAutoCreateRowSorter(true);
        tbSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá bán"
            }
        ));
        tbSanPham.setGridColor(new java.awt.Color(153, 153, 153));
        tbSanPham.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbSanPham.setShowGrid(true);
        tbSanPham.setSurrendersFocusOnKeystroke(true);
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSanPham);

        panelTrangChu.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 360, 270));

        panelHistogram.setBackground(new java.awt.Color(255, 204, 255));
        panelHistogram.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tổng sản phẩm");
        panelHistogram.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        lbTongSP.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTongSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTongSP.setText("9");
        panelHistogram.add(lbTongSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 140, 50));

        panelTrangChu.add(panelHistogram, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 144, 93));

        panelBarChart.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusedBackground"));
        panelBarChart.setLayout(new java.awt.BorderLayout());
        panelTrangChu.add(panelBarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 340, 320));

        panelLineChart.setBackground(new java.awt.Color(255, 204, 255));
        panelLineChart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Tổng phiếu nhập");
        panelLineChart.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        lbTongPN.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTongPN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTongPN.setText("28");
        panelLineChart.add(lbTongPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 140, 50));

        panelTrangChu.add(panelLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, -1, 93));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Sản phẩm mới");
        panelTrangChu.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 127, -1));

        panelLineChart1.setBackground(new java.awt.Color(255, 204, 255));
        panelLineChart1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tổng tài khoản");
        panelLineChart1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        lbTongTK.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTongTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTongTK.setText("8");
        panelLineChart1.add(lbTongTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 140, 50));

        panelTrangChu.add(panelLineChart1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 142, 93));

        panelLineChart2.setBackground(new java.awt.Color(255, 204, 255));
        panelLineChart2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Tổng phiếu xuất");
        panelLineChart2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 40));

        lbTongPX.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lbTongPX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTongPX.setText("100");
        panelLineChart2.add(lbTongPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 140, 50));

        panelTrangChu.add(panelLineChart2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, -1, 93));

        jPanel2.add(panelTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        panelNhapHang.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "NHẬP SẢN PHẨM", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 255))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhà cung cấp", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 13))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Tên nhà cung cấp");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtTenNccNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenNccNhap.setForeground(new java.awt.Color(51, 51, 51));
        jPanel7.add(txtTenNccNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 170, -1));

        txtDiaChiNccNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDiaChiNccNhap.setForeground(new java.awt.Color(51, 51, 51));
        jPanel7.add(txtDiaChiNccNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 170, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Địa chỉ");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Số điện thoại");
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        txtSdtNccNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSdtNccNhap.setForeground(new java.awt.Color(51, 51, 51));
        jPanel7.add(txtSdtNccNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 170, -1));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 40, 320, 160));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(51, 51, 51))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Tên sản phẩm");

        txtTenSpNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Đơn vị tính");

        txtDvTinhNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDvTinhNhap.setForeground(new java.awt.Color(51, 51, 51));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Số lượng nhập");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Đơn giá nhập");

        txtDGNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDGNhap.setForeground(new java.awt.Color(51, 51, 51));

        txtSoLuongSpNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSoLuongSpNhap.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDvTinhNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtTenSpNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17)
                    .addComponent(txtDGNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(jLabel16)
                    .addComponent(txtSoLuongSpNhap))
                .addGap(25, 25, 25))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSpNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuongSpNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDGNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDvTinhNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 400, 160));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lịch sử nhập", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 13))); // NOI18N

        tbPhieuNhap.setAutoCreateRowSorter(true);
        tbPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbPhieuNhap);
        if (tbPhieuNhap.getColumnModel().getColumnCount() > 0) {
            tbPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbPhieuNhap.getColumnModel().getColumn(1).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 740, 200));

        btnLuuPN.setBackground(new java.awt.Color(255, 153, 255));
        btnLuuPN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLuuPN.setForeground(new java.awt.Color(255, 255, 255));
        btnLuuPN.setText("Lưu ");
        btnLuuPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLuuPNMouseClicked(evt);
            }
        });
        jPanel5.add(btnLuuPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 90, 40));

        btnHuyPN.setBackground(new java.awt.Color(255, 153, 255));
        btnHuyPN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyPN.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyPN.setText("Hủy");
        btnHuyPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyPNMouseClicked(evt);
            }
        });
        jPanel5.add(btnHuyPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 100, 40));

        btnXoaPN.setBackground(new java.awt.Color(255, 153, 255));
        btnXoaPN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaPN.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaPN.setText("Xóa");
        btnXoaPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaPNMouseClicked(evt);
            }
        });
        btnXoaPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPNActionPerformed(evt);
            }
        });
        jPanel5.add(btnXoaPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 100, 40));

        btnSuaPN.setBackground(new java.awt.Color(255, 153, 255));
        btnSuaPN.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaPN.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaPN.setText("Sửa");
        btnSuaPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaPNMouseClicked(evt);
            }
        });
        btnSuaPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPNActionPerformed(evt);
            }
        });
        jPanel5.add(btnSuaPN, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 100, 40));

        javax.swing.GroupLayout panelNhapHangLayout = new javax.swing.GroupLayout(panelNhapHang);
        panelNhapHang.setLayout(panelNhapHangLayout);
        panelNhapHangLayout.setHorizontalGroup(
            panelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNhapHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        panelNhapHangLayout.setVerticalGroup(
            panelNhapHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNhapHangLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(panelNhapHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 510));

        panelXuatHang.setBackground(new java.awt.Color(255, 255, 255));
        panelXuatHang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "XUẤT HÀNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 255))); // NOI18N
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Tên khách hàng");
        jPanel18.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(102, 102, 102));
        jLabel30.setText("Mã sản phẩm");
        jPanel18.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(102, 102, 102));
        jLabel32.setText("Số lượng xuất");
        jLabel32.setToolTipText("");
        jPanel18.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setText("Đơn giá xuất");
        jPanel18.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        txtSoLuongXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtSoLuongXuat.setForeground(new java.awt.Color(51, 51, 51));
        txtSoLuongXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongXuatActionPerformed(evt);
            }
        });
        jPanel18.add(txtSoLuongXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 140, -1));

        txtTenKhXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTenKhXuat.setForeground(new java.awt.Color(51, 51, 51));
        jPanel18.add(txtTenKhXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 150, -1));

        txtDgXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDgXuat.setForeground(new java.awt.Color(51, 51, 51));
        jPanel18.add(txtDgXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 140, -1));

        cbMaSpPX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbMaSpPX.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMaSpPXItemStateChanged(evt);
            }
        });
        cbMaSpPX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbMaSpPXMouseClicked(evt);
            }
        });
        jPanel18.add(cbMaSpPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 150, -1));

        lbTenSpPX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTenSpPX.setForeground(new java.awt.Color(102, 102, 102));
        lbTenSpPX.setText("Tên sản phẩm: ");
        jPanel18.add(lbTenSpPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        lbSluongSpPX.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbSluongSpPX.setForeground(new java.awt.Color(102, 102, 102));
        lbSluongSpPX.setText("Số lượng hàng: ");
        jPanel18.add(lbSluongSpPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jPanel17.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 540, 190));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lịch sử xuất hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbPhieuXuat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbPhieuXuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbPhieuXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPhieuXuatMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbPhieuXuat);

        jPanel19.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 700, 170));

        jPanel17.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 720, 210));

        btnThemPX.setBackground(new java.awt.Color(255, 153, 255));
        btnThemPX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemPX.setForeground(new java.awt.Color(255, 255, 255));
        btnThemPX.setText("Thêm");
        btnThemPX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemPXMouseClicked(evt);
            }
        });
        jPanel17.add(btnThemPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 100, 40));

        btnSuaPX.setBackground(new java.awt.Color(255, 153, 255));
        btnSuaPX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaPX.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaPX.setText("Sửa");
        btnSuaPX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaPXMouseClicked(evt);
            }
        });
        btnSuaPX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPXActionPerformed(evt);
            }
        });
        jPanel17.add(btnSuaPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 100, 40));

        btnXoaPX.setBackground(new java.awt.Color(255, 153, 255));
        btnXoaPX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaPX.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaPX.setText("Xóa");
        btnXoaPX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaPXMouseClicked(evt);
            }
        });
        jPanel17.add(btnXoaPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 100, 40));

        btnHuyPX.setBackground(new java.awt.Color(255, 153, 255));
        btnHuyPX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyPX.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyPX.setText("Hủy");
        btnHuyPX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyPXMouseClicked(evt);
            }
        });
        btnHuyPX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyPXActionPerformed(evt);
            }
        });
        jPanel17.add(btnHuyPX, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 100, 40));

        panelXuatHang.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 8, 740, 510));

        jPanel2.add(panelXuatHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        panelQuanLyNCC.setBackground(new java.awt.Color(255, 255, 255));
        panelQuanLyNCC.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, null));
        panelQuanLyNCC.setMinimumSize(new java.awt.Dimension(730, 500));
        panelQuanLyNCC.setPreferredSize(new java.awt.Dimension(730, 500));
        panelQuanLyNCC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "QUẢN LÝ THÔNG TIN NHÀ CUNG CẤP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 255))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(51, 51, 51))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Tên nhà cung cấp");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Địa chỉ");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Số điện thoại");

        textTenNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textTenNCC.setForeground(new java.awt.Color(51, 51, 51));

        textDcNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textDcNCC.setForeground(new java.awt.Color(51, 51, 51));

        textSdtNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textSdtNCC.setForeground(new java.awt.Color(51, 51, 51));
        textSdtNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSdtNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textSdtNCC)
                    .addComponent(textDcNCC)
                    .addComponent(textTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(textTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(textDcNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(textSdtNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 350, 170));

        tbQuanLyNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbQuanLyNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tbQuanLyNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbQuanLyNCCMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbQuanLyNCC);

        jPanel10.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 670, 160));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Nhập mã hoặc tên nhà cung cấp");

        txtTimKiemNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTimKiemNCC.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimKiemNCC))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 290, 170));

        btnSuaNCC.setBackground(new java.awt.Color(255, 153, 255));
        btnSuaNCC.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSuaNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaNCC.setText("Sửa");
        btnSuaNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaNCCMouseClicked(evt);
            }
        });
        btnSuaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNCCActionPerformed(evt);
            }
        });
        jPanel10.add(btnSuaNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 110, 40));

        btnXoaNCC.setBackground(new java.awt.Color(255, 153, 255));
        btnXoaNCC.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnXoaNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNCC.setText("Xóa");
        btnXoaNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaNCCMouseClicked(evt);
            }
        });
        btnXoaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNCCActionPerformed(evt);
            }
        });
        jPanel10.add(btnXoaNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, 110, 40));

        btnTimKiemNCC.setBackground(new java.awt.Color(255, 153, 255));
        btnTimKiemNCC.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTimKiemNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemNCC.setText("Tìm kiếm");
        btnTimKiemNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemNCCMouseClicked(evt);
            }
        });
        jPanel10.add(btnTimKiemNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 230, 110, 40));

        btnThemNCC.setBackground(new java.awt.Color(255, 153, 255));
        btnThemNCC.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThemNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNCC.setText("Thêm");
        btnThemNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemNCCMouseClicked(evt);
            }
        });
        btnThemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNCCActionPerformed(evt);
            }
        });
        jPanel10.add(btnThemNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 110, 40));

        panelQuanLyNCC.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 18, 710, 470));

        jPanel2.add(panelQuanLyNCC, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        panelQLSP.setBackground(new java.awt.Color(255, 255, 255));
        panelQLSP.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, null));
        panelQLSP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "QUẢN LÝ SẢN PHẨM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 255))); // NOI18N
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(102, 102, 102));
        jLabel21.setText("Tên sản phẩm");
        jPanel14.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 29, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("Đơn vị tính");
        jPanel14.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 29, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("Giá bán");
        jPanel14.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 128, -1, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("Màu sắc");
        jLabel24.setToolTipText("");
        jPanel14.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 94, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Kích thước mh");
        jPanel14.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 168, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(102, 102, 102));
        jLabel26.setText("Bộ nhớ Ram");
        jPanel14.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("Bộ nhớ trong");
        jPanel14.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 63, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("Pin");
        jPanel14.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(102, 102, 102));
        jLabel29.setText("Hệ điều hành");
        jPanel14.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 63, -1, -1));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("Số lượng");
        jPanel14.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 94, 57, -1));

        textTenSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textTenSP.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 26, 139, -1));

        textHeDieuHanh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textHeDieuHanh.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textHeDieuHanh, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 60, 139, -1));

        textMauSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textMauSP.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textMauSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(113, 91, 139, -1));

        textBoNhoRAM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textBoNhoRAM.setForeground(new java.awt.Color(51, 51, 51));
        textBoNhoRAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBoNhoRAMActionPerformed(evt);
            }
        });
        jPanel14.add(textBoNhoRAM, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 140, -1));

        textPINSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textPINSP.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textPINSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 139, -1));

        textDvTinh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textDvTinh.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textDvTinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 26, 109, -1));

        textBoNhoTrong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textBoNhoTrong.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textBoNhoTrong, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 109, -1));

        textSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textSoLuong.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 91, 109, -1));

        textGiaBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textGiaBan.setForeground(new java.awt.Color(51, 51, 51));
        jPanel14.add(textGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 125, 109, -1));

        textKichThuocMH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        textKichThuocMH.setForeground(new java.awt.Color(51, 51, 51));
        textKichThuocMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textKichThuocMHActionPerformed(evt);
            }
        });
        jPanel14.add(textKichThuocMH, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 109, -1));

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 28, 500, 220));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnThemSP.setBackground(new java.awt.Color(255, 153, 255));
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setText("Thêm");
        btnThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPMouseClicked(evt);
            }
        });
        jPanel16.add(btnThemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 92, 38));

        btnSuaSP.setBackground(new java.awt.Color(255, 153, 255));
        btnSuaSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSuaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSP.setText("Sửa");
        btnSuaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaSPMouseClicked(evt);
            }
        });
        jPanel16.add(btnSuaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 85, 38));

        btnXoaSP.setBackground(new java.awt.Color(255, 153, 255));
        btnXoaSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnXoaSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSP.setText("Xóa");
        btnXoaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaSPMouseClicked(evt);
            }
        });
        jPanel16.add(btnXoaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 85, 38));

        btnTimKiemSP.setBackground(new java.awt.Color(255, 153, 255));
        btnTimKiemSP.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnTimKiemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSP.setText("Tìm kiếm");
        btnTimKiemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemSPMouseClicked(evt);
            }
        });
        jPanel16.add(btnTimKiemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 100, 38));

        jPanel13.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 680, 50));

        tableSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableSanPham);

        jPanel13.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 690, 140));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemSPActionPerformed(evt);
            }
        });
        jPanel15.add(txtTimKiemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 148, -1));

        cbTimKiemSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbTimKiemSP.setForeground(new java.awt.Color(102, 102, 102));
        cbTimKiemSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã sản phẩm", "Tên sản phẩm" }));
        cbTimKiemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTimKiemSPActionPerformed(evt);
            }
        });
        jPanel15.add(cbTimKiemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        jPanel13.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 170, 170));

        panelQLSP.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 710, 480));

        jPanel2.add(panelQLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        panelTKTongDT.setBackground(new java.awt.Color(255, 255, 255));
        panelTKTongDT.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THỐNG KÊ TỔNG DOANH THU", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 153, 255))); // NOI18N
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbThongKeDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbThongKeDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbThongKeDT);

        jPanel20.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 690, 290));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel34.setText("Tổng doanh thu:");
        jPanel20.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        lbTongDT.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbTongDT.setText("999999999");
        jPanel20.add(lbTongDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 450, -1, -1));

        btnPrintThongKeDT.setBackground(new java.awt.Color(255, 153, 255));
        btnPrintThongKeDT.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnPrintThongKeDT.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintThongKeDT.setText("In danh sách");
        btnPrintThongKeDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintThongKeDTMouseClicked(evt);
            }
        });
        jPanel20.add(btnPrintThongKeDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(599, 440, 110, 30));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm theo khoảng thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 13))); // NOI18N
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearchTkStart.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel21.add(txtSearchTkStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 120, -1));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("Từ ngày");
        jPanel21.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("đến ngày");
        jPanel21.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, -1, -1));

        txtSearchTkEnd.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jPanel21.add(txtSearchTkEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 120, -1));

        btnSearchThongKeDT.setBackground(new java.awt.Color(255, 153, 255));
        btnSearchThongKeDT.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSearchThongKeDT.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchThongKeDT.setText("Lọc");
        btnSearchThongKeDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchThongKeDTMouseClicked(evt);
            }
        });
        btnSearchThongKeDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchThongKeDTActionPerformed(evt);
            }
        });
        jPanel21.add(btnSearchThongKeDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 90, 30));

        btnRefreshThongKeDT.setBackground(new java.awt.Color(255, 153, 255));
        btnRefreshThongKeDT.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnRefreshThongKeDT.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshThongKeDT.setText("Refresh");
        btnRefreshThongKeDT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshThongKeDTMouseClicked(evt);
            }
        });
        jPanel21.add(btnRefreshThongKeDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 80, 30));

        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 690, 80));

        panelTKTongDT.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 730, 500));

        jPanel2.add(panelTKTongDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        panelTKSpBanChay.setBackground(new java.awt.Color(255, 255, 255));
        panelTKSpBanChay.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THỐNG KÊ SẢN PHẨM BÁN CHẠY", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 102, 255))); // NOI18N
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbThongKeSpBanChay.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbThongKeSpBanChay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tbThongKeSpBanChay);

        jPanel22.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 690, 360));

        btnPrintSpBanChay.setBackground(new java.awt.Color(255, 153, 255));
        btnPrintSpBanChay.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnPrintSpBanChay.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintSpBanChay.setText("In danh sách");
        btnPrintSpBanChay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintSpBanChayMouseClicked(evt);
            }
        });
        jPanel22.add(btnPrintSpBanChay, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 430, 120, 40));

        panelTKSpBanChay.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 730, 490));

        jPanel2.add(panelTKSpBanChay, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 750, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHomeActionPerformed

    // Chức năng quản lý nhà cung cấp
    private void btnQLNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLNCCActionPerformed
        
    }//GEN-LAST:event_btnQLNCCActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnQLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnQLSPActionPerformed

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
//        int index = tbSanPham.getSelectedRow();
//        TableModel model = tbSanPham.getModel();
//        int maSP = Integer.parseInt(model.getValueAt(index, 0).toString());
//        String tenSP = model.getValueAt(index, 1).toString();
//        
//        System.out.println(maSP + tenSP); 
    }//GEN-LAST:event_tbSanPhamMouseClicked

    // Click button Trang chủ
    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        cbQLNX.setSelectedIndex(0);
        cbThongKe.setSelectedIndex(0);
        
        panelTrangChu.setVisible(true);
        panelNhapHang.setVisible(false);
        panelXuatHang.setVisible(false);
        panelQuanLyNCC.setVisible(false);
        panelTKSpBanChay.setVisible(false);
        panelTKTongDT.setVisible(false);
        
        try {
            loadForm();
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHomeMouseClicked

    // Click button Quản lý sản phẩm
    private void btnQLSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLSPMouseClicked
        cbQLNX.setSelectedIndex(0);
        cbThongKe.setSelectedIndex(0);
        
        panelQLSP.setVisible(true);
        panelTrangChu.setVisible(false);
        panelNhapHang.setVisible(false);
        panelXuatHang.setVisible(false);
        panelQuanLyNCC.setVisible(false);
        panelTKSpBanChay.setVisible(false);
        panelTKTongDT.setVisible(false);
        
        try {
            controlSP = new SanPhamController();
            LinkedList<Object[]> lstSP = controlSP.getData();
            LoadTbSanPham(lstSP);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQLSPMouseClicked

    // Sự kiện button quản lý nhập xuất
    private void cbQLNXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbQLNXActionPerformed
        // Chức năng nhập hàng
        if(cbQLNX.getSelectedItem() == "  Nhập hàng") {
            panelNhapHang.setVisible(true);
            panelTrangChu.setVisible(false);
            panelXuatHang.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            panelTKSpBanChay.setVisible(false);
            panelTKTongDT.setVisible(false);
            try {
                loadTablePhieuNhap();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            
        } else if(cbQLNX.getSelectedItem() == "  Xuất hàng") {  // Chức năng xuất hàng
            panelXuatHang.setVisible(true);
            panelNhapHang.setVisible(false);
            panelTrangChu.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            panelTKSpBanChay.setVisible(false);
            panelTKTongDT.setVisible(false);
            
            LinkedList<Integer> lstMaSp = new LinkedList<>();
            try {
                cbMaSpPX.removeAllItems();
                // Hiển thị danh sách mã sản phẩm lên comboBox
                controlSP = new SanPhamController();
                lstMaSp = controlSP.getLstMaSP();
                for(int i : lstMaSp) {
                    cbMaSpPX.addItem(Integer.toString(i));
                }
                // Hiển thị dữ liệu lên table
                controlPX = new PhieuXuatController();
                LinkedList<Object[]> lstPX = controlPX.getData();
                LoadTbPhieuXuat(lstPX);
            } catch (Exception e) {
                
            }
        } else {
            panelTrangChu.setVisible(true);
            panelNhapHang.setVisible(false);
            panelXuatHang.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            panelTKSpBanChay.setVisible(false);
            panelTKTongDT.setVisible(false);
        }
    }//GEN-LAST:event_cbQLNXActionPerformed

    // Phương thức load dữ liệu table Phiếu nhập
    private void loadTablePhieuNhap() throws ClassNotFoundException, SQLException {
        controlPN = new PhieuNhapController();
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã phiếu nhập", "Tên sản phẩm", "Tên nhà cung cấp", "Địa chỉ",
                                "Số điện thoại", "Đơn vị tính", "Ngày nhập", "Số lượng nhập", "Đơn giá nhập"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbPhieuNhap.setModel(model);
        tbPhieuNhap.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                
        // Danh sách thông tin phiếu nhập
        LinkedList<Object[]> lstPN = controlPN.getData();
        for(var obj : lstPN) {
            model.addRow(obj);
        }
    }
    
    // Sự kiện click button thêm phiếu nhập
    private void btnLuuPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuuPNMouseClicked
        if(txtTenNccNhap.getText().isEmpty() || txtDiaChiNccNhap.getText().isEmpty() || txtSdtNccNhap.getText().isEmpty()
                || txtTenSpNhap.getText().isEmpty() || txtDGNhap.getText().isEmpty() || txtDvTinhNhap.getText().isEmpty()
                || txtSoLuongSpNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        } else {
            try {
                // Thêm thông tin nhà cung cấp
                NhaCungCap ncc = new NhaCungCap();
                ncc.setTenNCC(txtTenNccNhap.getText());
                ncc.setDiaChi(txtDiaChiNccNhap.getText());
                ncc.setSdt(txtSdtNccNhap.getText());
                boolean isThemNCC = ThemNCC(ncc);

                // True thì thêm phiếu nhập
                if(isThemNCC) {
                    // Lấy mã nhà cung cấp mới thêm
                    int maNCC = controlNCC.getMaNCC(ncc);

                    if(maNCC > 0) {
                        // Thêm thông tin phiếu nhập
                        controlPN = new PhieuNhapController();
                        PhieuNhap ph = new PhieuNhap();
                        // Lấy ngày giờ hiện tại
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        ph.setNgayNhap(dateFormat.format(date));
                        ph.setMaNCC(maNCC);
                        boolean isThemPN = controlPN.ThemPhieuNhap(ph);

                        // Thêm thông tin sản phẩm
                        controlSP = new SanPhamController();
                        SanPham sp = new SanPham();
                        sp.setTenSP(txtTenSpNhap.getText());
                        sp.setDvTinh(txtDvTinhNhap.getText());
                        // Giá bán sản phẩm = đơn giá nhập + 15%;
                        Double dgNhap = Double.parseDouble(txtDGNhap.getText());
                        double giaBan = dgNhap + (dgNhap * 0.15f);
                        sp.setGiaBan(giaBan);
                        sp.setMauSac("Trống");
                        sp.setKichThuocManHinh("Trống");
                        sp.setRam("Trống");
                        sp.setBoNho("Trống");
                        sp.setPin("Trống");
                        sp.setHeDieuHanh("Trống");
                        sp.setSoLuongHang(txtSoLuongSpNhap.getText());

                        boolean isThemSP = controlSP.ThemSanPham(sp);

                        // Nếu thêm phiếu nhập & sản phẩm thành công thì thực hiện thêm thông tin chi tiết phiếu nhập
                        if(isThemPN && isThemSP) {
                            // Lấy mã sản phẩm, mã phiếu nhập mới thêm
                            int maPN = controlPN.getMaPN(ph);
                            int maSP = controlSP.getMaSP(sp);

                            if(maPN > 0 && maSP > 0) {
                                try {
                                    controlCTPN = new ChiTietPhieuNhapController();
                                    int slNhap = Integer.parseInt(txtSoLuongSpNhap.getText());
                                    CTPhieuNhap ctpn = new CTPhieuNhap(maPN, maSP, slNhap, dgNhap);

                                    // Gọi phương thức thêm chi tiết phiếu nhập
                                    controlCTPN.ThemCTPhieuNhap(ctpn);
                                    JOptionPane.showMessageDialog(this, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                    loadTablePhieuNhap();
                                    clearTextPhieuNhap();
                                } catch (Exception e) {
                                    controlPN.XoaPhieuNhap(maPN);
                                    controlSP.XoaSanPham(maSP);
                                    controlNCC.XoaNCC(maNCC);
                                    JOptionPane.showMessageDialog(this, e.getMessage());
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnLuuPNMouseClicked

    // Sự kiện click button thoát
    private void btnThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatMouseClicked

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dangnhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(false);
                new Dangnhap().setVisible(true);
            }
        });
    }//GEN-LAST:event_btnThoatMouseClicked

    // Sự kiện click button hủy phiếu nhập
    private void btnHuyPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyPNMouseClicked
        clearTextPhieuNhap();
    }//GEN-LAST:event_btnHuyPNMouseClicked

    // Phương thức xóa text input Phiếu nhập
    private void clearTextPhieuNhap() {
        txtDGNhap.setText("");
        txtDiaChiNccNhap.setText("");
        txtDvTinhNhap.setText("");
        txtTenSpNhap.setText("");
        txtSdtNccNhap.setText("");
        txtTenNccNhap.setText("");
        txtSoLuongSpNhap.setText("");
    }
    
    // Sự kiện click table phiếu nhập (gán dữ liệu vào text input)
    private void tbPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuNhapMouseClicked
        // get dữ liệu khi click row table
        int index = tbPhieuNhap.getSelectedRow();
        TableModel model = tbPhieuNhap.getModel();
        int maPN = Integer.parseInt(model.getValueAt(index, 0).toString());
        String tenSP = model.getValueAt(index, 1).toString();
        String tenNCC = model.getValueAt(index, 2).toString();
        String diaChi = model.getValueAt(index, 3).toString();
        String sdt = model.getValueAt(index, 4).toString();
        String dvTinh = model.getValueAt(index, 5).toString();
        String ngayNhap = model.getValueAt(index, 6).toString();
        String slNhap = model.getValueAt(index, 7).toString();
        String dgNhap = model.getValueAt(index, 8).toString();
        
        // set dữ liệu ra text input
        txtTenSpNhap.setText(tenSP);
        txtTenNccNhap.setText(tenNCC);
        txtSoLuongSpNhap.setText(slNhap);
        txtDGNhap.setText(dgNhap);
        txtSdtNccNhap.setText(sdt);
        txtDiaChiNccNhap.setText(diaChi);
        txtDvTinhNhap.setText(dvTinh);
    }//GEN-LAST:event_tbPhieuNhapMouseClicked

    // Sự kiện click button sửa phiếu nhập
    private void btnSuaPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaPNMouseClicked
        // Lấy mã phiếu nhập
        int index = tbPhieuNhap.getSelectedRow();
        TableModel model = tbPhieuNhap.getModel();
        int maPN = Integer.parseInt(model.getValueAt(index, 0).toString());
        
        if(txtTenNccNhap.getText().isEmpty() || txtDiaChiNccNhap.getText().isEmpty() || txtSdtNccNhap.getText().isEmpty()
                || txtTenSpNhap.getText().isEmpty() || txtDGNhap.getText().isEmpty() || txtDvTinhNhap.getText().isEmpty()
                || txtSoLuongSpNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        } else if(maPN > 0) {
            try {
                // Sửa thông tin nhà cung cấp
                controlNCC = new NhaCungCapController();
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(controlNCC.getMaNCC(maPN));
                ncc.setTenNCC(txtTenNccNhap.getText());
                ncc.setDiaChi(txtDiaChiNccNhap.getText());
                ncc.setSdt(txtSdtNccNhap.getText());
                boolean isSuaNCC = controlNCC.SuaNCC(ncc);
                
                // Sửa thông tin sản phẩm
                controlSP = new SanPhamController();
                // Lấy mã sản phẩm
                int maSP = controlSP.getMaSP(maPN);
                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                sp.setTenSP(txtTenSpNhap.getText());
                sp.setDvTinh(txtDvTinhNhap.getText());
                // Giá bán sản phẩm = đơn giá nhập + 15%;
                Double dgNhap = Double.parseDouble(txtDGNhap.getText());
                double giaBan = dgNhap + (dgNhap * 0.15f);
                sp.setGiaBan(giaBan);
                sp.setMauSac("Trống");
                sp.setKichThuocManHinh("Trống");
                sp.setRam("Trống");
                sp.setBoNho("Trống");
                sp.setPin("Trống");
                sp.setHeDieuHanh("Trống");
                sp.setSoLuongHang(txtSoLuongSpNhap.getText());
                
                boolean isSuaSP = controlSP.SuaSanPham(sp);
                
                
                if(isSuaNCC && isSuaSP) {
                    // Sửa thông tin chi tiết phiếu nhập
                    controlCTPN = new ChiTietPhieuNhapController();
                    CTPhieuNhap ctpn = new CTPhieuNhap(maPN, maSP, Integer.parseInt(txtSoLuongSpNhap.getText()), Double.parseDouble(txtDGNhap.getText()));
                    boolean isSuaCTPN = controlCTPN.SuaCTPhieuNhap(ctpn);
                    if(isSuaCTPN) {
                        JOptionPane.showMessageDialog(this, "Sửa thành công");
                        loadTablePhieuNhap();
                        clearTextPhieuNhap();
                    }
                    else 
                        JOptionPane.showMessageDialog(this, "Sửa không thành công");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else 
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không tồn tại");
    }//GEN-LAST:event_btnSuaPNMouseClicked

    // Sự kiện click xóa phiếu nhập
    private void btnXoaPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaPNMouseClicked
        // Lấy mã phiếu nhập
        int index = tbPhieuNhap.getSelectedRow();
        TableModel model = tbPhieuNhap.getModel();
        int maPN = Integer.parseInt(model.getValueAt(index, 0).toString());
        
        if(maPN > 0) {
            try {
                controlPN = new PhieuNhapController();
                controlNCC = new NhaCungCapController();
                controlSP = new SanPhamController();
                controlCTPN = new ChiTietPhieuNhapController();
                // Lấy mã nhà cung cấp, mã sản phẩm từ mã phiếu nhập
                int maNCC = controlNCC.getMaNCC(maPN);
                int maSP = controlSP.getMaSP(maPN);
                
                // Xóa chi tiết phiếu nhập
                boolean isXoaCTPN = controlCTPN.XoaCTPhieuNhap(maPN, maSP);
                // Xóa phiếu nhập
                boolean isXoaPN = controlPN.XoaPhieuNhap(maPN);
                // Xóa nhà cung cấp
                boolean isXoaNCC = controlNCC.XoaNCC(maNCC);
                // Xóa sản phẩm
                boolean isXoaSP = controlSP.XoaSanPham(maSP);
                
                if(isXoaCTPN && isXoaPN && isXoaNCC & isXoaSP) {
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập thành công");
                    loadTablePhieuNhap();
                    clearTextPhieuNhap();
                }
                else
                    JOptionPane.showMessageDialog(this, "Xóa phiếu nhập không thành công");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnXoaPNMouseClicked

    private void btnXoaPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaPNActionPerformed

    private void btnSuaPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaPNActionPerformed

    private void btnSuaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNCCActionPerformed
        
    }//GEN-LAST:event_btnSuaNCCActionPerformed

    private void btnThemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNCCActionPerformed
        
    }//GEN-LAST:event_btnThemNCCActionPerformed

    // Phương thức xóa text nhà cung cấp
    private void ClearTextNCC() {
        textTenNCC.setText("");
        textDcNCC.setText("");
        textSdtNCC.setText("");
    }
    
    // Phương thức thêm thông tin nhà cung cấp
    private boolean ThemNCC(NhaCungCap ncc) throws ClassNotFoundException, SQLException {
        controlNCC = new NhaCungCapController();
        return controlNCC.ThemNCC(ncc);
    }
    
    // Phương thức sửa thông tin nhà cung cấp
    private boolean SuaNCC(NhaCungCap ncc) throws ClassNotFoundException, SQLException {
        controlNCC = new NhaCungCapController();
        return controlNCC.SuaNCC(ncc);
    }
    
    // Chức năng quản lý nhà cung cấp
    private void btnQLNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLNCCMouseClicked
        cbQLNX.setSelectedIndex(0);
        cbThongKe.setSelectedIndex(0);
        
        panelQuanLyNCC.setVisible(true);
        panelTrangChu.setVisible(false);
        panelNhapHang.setVisible(false);
        panelXuatHang.setVisible(false);
        panelQLSP.setVisible(false);
        panelTKSpBanChay.setVisible(false);
        panelTKTongDT.setVisible(false);
        try {
            LoadTableNCC();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnQLNCCMouseClicked

    // Phương thức load dữ liệu nhà cung cấp lên table
    private void LoadTableNCC() throws ClassNotFoundException, SQLException {
        // Hiển thị dữ liệu lên table Nhà cung cấp
        controlNCC = new NhaCungCapController();
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã", "Tên", "Địa chỉ", "Số điện thoại"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbQuanLyNCC.setModel(model);
        tbQuanLyNCC.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Danh sách thông tin phiếu nhập
        LinkedList<Object[]> lstNCC = controlNCC.getData();
        for(var obj : lstNCC) {
            model.addRow(obj);
        }
    }
    
    private void textSdtNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSdtNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSdtNCCActionPerformed

    // Sự kiện click table nhà cung cấp (gán dữ liệu vào text input)
    private void tbQuanLyNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbQuanLyNCCMouseClicked
        // get dữ liệu khi click row table
        int index = tbQuanLyNCC.getSelectedRow();
        TableModel model = tbQuanLyNCC.getModel();
        maNCC = Integer.parseInt(model.getValueAt(index, 0).toString());
        String tenNCC = model.getValueAt(index, 1).toString();
        String diaChi = model.getValueAt(index, 2).toString();
        String sdt = model.getValueAt(index, 3).toString();
        
        // set dữ liệu ra text input
        textTenNCC.setText(tenNCC);
        textDcNCC.setText(diaChi);
        textSdtNCC.setText(sdt);
    }//GEN-LAST:event_tbQuanLyNCCMouseClicked

    
    private void btnXoaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNCCActionPerformed
        
    }//GEN-LAST:event_btnXoaNCCActionPerformed
    // Sự kiện xóa thông tin nhà cung cấp
    private void btnXoaNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaNCCMouseClicked
        try {
            controlNCC = new NhaCungCapController();
            boolean isXoaNCC = controlNCC.XoaNCC(maNCC);
                
            if(isXoaNCC) { 
                JOptionPane.showMessageDialog(this, "Xóa thông tin nhà cung cấp thành công");
                LoadTableNCC();
                ClearTextNCC();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thông tin nhà cung cấp không thành công");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXoaNCCMouseClicked

    // Sự kiện click tìm kiếm Nhà cung cấp
    private void btnTimKiemNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemNCCMouseClicked
        String searchInput = txtTimKiemNCC.getText();
        if(searchInput.length() == 0) {
            JOptionPane.showMessageDialog(this, "Nhập thông tin tìm kiếm");
        } else {
            try {
                // Xóa dữ liệu table nhà cung cấp
                DefaultTableModel dm = (DefaultTableModel)tbQuanLyNCC.getModel(); 
                int rows = dm.getRowCount(); 
                for(int i = rows - 1; i >=0; i--)
                {
                   dm.removeRow(i); 
                }
                
                // Hiển thị dữ liệu lên table Nhà cung cấp
                controlNCC = new NhaCungCapController();
                // Hiển thị dữ liệu lên table
                String[] columnsName = {"Mã", "Tên", "Địa chỉ", "Số điện thoại"};
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columnsName);
                tbQuanLyNCC.setModel(model);
                tbQuanLyNCC.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                // Danh sách thông tin phiếu nhập
                LinkedList<Object[]> lstNCC = controlNCC.TimKiemNCC(searchInput);
                for(var obj : lstNCC) {
                    model.addRow(obj);
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnTimKiemNCCMouseClicked

    // Sự kiện click thêm nhà cung cấp
    private void btnThemNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemNCCMouseClicked
        try {
            if(textTenNCC.getText() == "" || textDcNCC.getText() == "" || textSdtNCC.getText() == "") {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            } else {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setTenNCC(textTenNCC.getText());
                ncc.setDiaChi(textDcNCC.getText());
                ncc.setSdt(textSdtNCC.getText());
                boolean isThemNCC = ThemNCC(ncc);
                if(isThemNCC) {
                    JOptionPane.showMessageDialog(this, "Thêm thông tin nhà cung cấp thành công");
                    LoadTableNCC();
                    ClearTextNCC();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thông tin nhà cung cấp không thành công", "Lỗi", JOptionPane.ERROR);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemNCCMouseClicked

    // Sự kiện click sửa nhà cung cấp
    private void btnSuaNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaNCCMouseClicked
        try {
            if(textTenNCC.getText() == "" || textDcNCC.getText() == "" || textSdtNCC.getText() == "") {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            } else {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(maNCC);
                ncc.setTenNCC(textTenNCC.getText());
                ncc.setDiaChi(textDcNCC.getText());
                ncc.setSdt(textSdtNCC.getText());
                
                boolean isSuaNCC = SuaNCC(ncc);
                if(isSuaNCC) {
                    JOptionPane.showMessageDialog(this, "Sửa thông tin nhà cung cấp thành công");
                    LoadTableNCC();
                    ClearTextNCC();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thông tin nhà cung cấp không thành công", "Lỗi", JOptionPane.ERROR);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSuaNCCMouseClicked

    private void textKichThuocMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textKichThuocMHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textKichThuocMHActionPerformed

    // Sự kiện thêm thông tin sản phẩm
    private void btnThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseClicked
        if(textTenSP.getText().isEmpty() || textHeDieuHanh.getText().isEmpty() || textMauSP.getText().isEmpty() || textBoNhoRAM.getText().isEmpty()
                || textPINSP.getText().isEmpty() || textDvTinh.getText().isEmpty() || textBoNhoTrong.getText().isEmpty() || textSoLuong.getText().isEmpty()
                || textGiaBan.getText().isEmpty() || textKichThuocMH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        } else {
            try {
                controlSP = new SanPhamController();
                SanPham sp = new SanPham();
                sp.setTenSP(textTenSP.getText());
                sp.setHeDieuHanh(textHeDieuHanh.getText());
                sp.setMauSac(textMauSP.getText());
                sp.setRam(textBoNhoRAM.getText());
                sp.setPin(textPINSP.getText());
                sp.setDvTinh(textDvTinh.getText());
                sp.setBoNho(textBoNhoTrong.getText());
                sp.setSoLuongHang(textSoLuong.getText());
                sp.setGiaBan(Float.parseFloat(textGiaBan.getText()));
                sp.setKichThuocManHinh(textKichThuocMH.getText());
                
                boolean isThemSp = controlSP.ThemSanPham(sp);
                if(isThemSp) {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công");
                    LinkedList<Object[]> lstSP = controlSP.getData();
                    LoadTbSanPham(lstSP);
                    ClearTextSP();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm sản phẩm không thành công");
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnThemSPMouseClicked

    // Phương thức clear text input Sản phẩm
    public void ClearTextSP() {
        textTenSP.setText("");
        textHeDieuHanh.setText("");
        textMauSP.setText("");
        textBoNhoRAM.setText("");
        textPINSP.setText("");
        textDvTinh.setText("");
        textBoNhoTrong.setText("");
        textSoLuong.setText("");
        textGiaBan.setText("");
        textKichThuocMH.setText("");
    }
    
    private void textBoNhoRAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBoNhoRAMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textBoNhoRAMActionPerformed

    // Sự kiện gán dữ liệu vào input sản phẩm khi click vào table
    private void tableSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSanPhamMouseClicked
        // get dữ liệu khi click row table
        int index = tableSanPham.getSelectedRow();
        TableModel model = tableSanPham.getModel();
        maSP = Integer.parseInt(model.getValueAt(index, 0).toString());
        String tenSP = model.getValueAt(index, 1).toString();
        String dvTinh = model.getValueAt(index, 2).toString();
        String giaBan = model.getValueAt(index, 3).toString();
        String mauSac = model.getValueAt(index, 4).toString();
        String kichThuocMH = model.getValueAt(index, 5).toString();
        String boNhoRam = model.getValueAt(index, 6).toString();
        String boNhoTrong = model.getValueAt(index, 7).toString();
        String pin = model.getValueAt(index, 8).toString();
        String heDieuHanh = model.getValueAt(index, 9).toString();
        String soLuong = model.getValueAt(index, 10).toString();
        
        // set dữ liệu ra text input
        textTenSP.setText(tenSP);
        textHeDieuHanh.setText(heDieuHanh);
        textMauSP.setText(mauSac);
        textBoNhoRAM.setText(boNhoRam);
        textPINSP.setText(pin);
        textDvTinh.setText(dvTinh);
        textBoNhoTrong.setText(boNhoTrong);
        textSoLuong.setText(soLuong);
        textGiaBan.setText(giaBan);
        textKichThuocMH.setText(kichThuocMH);
    }//GEN-LAST:event_tableSanPhamMouseClicked

    // Sự kiện click sửa sản phẩm
    private void btnSuaSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaSPMouseClicked
        if(textTenSP.getText().isEmpty() || textHeDieuHanh.getText().isEmpty() || textMauSP.getText().isEmpty() || textBoNhoRAM.getText().isEmpty()
                || textPINSP.getText().isEmpty() || textDvTinh.getText().isEmpty() || textBoNhoTrong.getText().isEmpty() || textSoLuong.getText().isEmpty()
                || textGiaBan.getText().isEmpty() || textKichThuocMH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        } else {
            try {
                controlSP = new SanPhamController();
                SanPham sp = new SanPham();
                sp.setMaSP(maSP);
                sp.setTenSP(textTenSP.getText());
                sp.setHeDieuHanh(textHeDieuHanh.getText());
                sp.setMauSac(textMauSP.getText());
                sp.setRam(textBoNhoRAM.getText());
                sp.setPin(textPINSP.getText());
                sp.setDvTinh(textDvTinh.getText());
                sp.setBoNho(textBoNhoTrong.getText());
                sp.setSoLuongHang(textSoLuong.getText());
                sp.setGiaBan(Float.parseFloat(textGiaBan.getText()));
                sp.setKichThuocManHinh(textKichThuocMH.getText());
                
                boolean isSuaSP = controlSP.SuaSanPham(sp);
                if(isSuaSP) {
                    JOptionPane.showMessageDialog(this, "Sửa thông tin sản phẩm thành công");
                    LinkedList<Object[]> lstSP = controlSP.getData();
                    LoadTbSanPham(lstSP);
                    ClearTextSP();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thông tin sản phẩm không thành công");
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSuaSPMouseClicked

    // Sự kiện xóa sản phầm
    private void btnXoaSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaSPMouseClicked
        try {
            controlSP = new SanPhamController();
            if(maSP > 0) {
                boolean isXoaSP = controlSP.XoaSanPham(maSP);
                if(isXoaSP) {
                    JOptionPane.showMessageDialog(this, "Xóa thông tin sản phẩm thành công");
                    LinkedList<Object[]> lstSP = controlSP.getData();
                    LoadTbSanPham(lstSP);
                    ClearTextSP();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thông tin sản phẩm không thành công");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm");
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXoaSPMouseClicked

    private void txtTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemSPActionPerformed

    private void cbTimKiemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTimKiemSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTimKiemSPActionPerformed

    // Sự kiện click tìm kiếm sản phẩm
    private void btnTimKiemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemSPMouseClicked
        try {
            controlSP = new SanPhamController();
            // Tìm kiếm theo mã sản phẩm
            if(cbTimKiemSP.getSelectedItem() == "Mã sản phẩm") {
                try {
                    int maSP = Integer.parseInt(txtTimKiemSP.getText());
                
                    // Danh sách dũ liệu tìm kiếm được
                    LinkedList<Object[]> lstSP = controlSP.timKiemTheoMaSP(maSP);
                    LoadTbSanPham(lstSP);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            } else {
                // Tìm kiếm theo tên sản phẩm
                // Danh sách dũ liệu tìm kiếm được
                LinkedList<Object[]> lstSP = controlSP.timKiemTheoTenSP(txtTimKiemSP.getText());
                LoadTbSanPham(lstSP);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTimKiemSPMouseClicked

    private void txtSoLuongXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongXuatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongXuatActionPerformed

    // Sự kiện click thêm phiếu xuất
    private void btnThemPXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemPXMouseClicked
        if(txtTenKhXuat.getText().isEmpty() || txtDgXuat.getText().isEmpty() || txtSoLuongXuat.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        else {
            try {
                // Kiểm tra số lượng hàng có đáp ứng đủ hay ko
                controlSP = new SanPhamController();
                int maSpPX = Integer.parseInt(cbMaSpPX.getSelectedItem().toString());
                int soLuongHienTai = controlSP.getSoLuong(maSpPX);
                
                if(soLuongHienTai >= Integer.parseInt(txtSoLuongXuat.getText())) {
                    // Thêm dữ liệu bảng phiếu xuất
                    controlPX = new PhieuXuatController();
                    PhieuXuat px = new PhieuXuat();
                    // Lấy ngày giờ hiện tại
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    px.setNgayXuat(dateFormat.format(date));
                    px.setTenKH(txtTenKhXuat.getText());
                    boolean isThemPX = controlPX.ThemPhieuXuat(px);
                    
                    if(isThemPX) {
                        // Lấy mã phiếu xuất vừa thêm
                        int maPX = controlPX.getMaPxNew();
                        if(maPX > 0) {
                            // Thêm dữ liệu bảng chi tiết phiếu xuất
                            controlCTPX = new ChiTietPhieuXuatController();
                            CTPhieuXuat ctpx = new CTPhieuXuat();
                            ctpx.setMaPX(maPX);
                            ctpx.setMaSP(maSpPX);
                            ctpx.setSlXuat(Integer.parseInt(txtSoLuongXuat.getText()));
                            ctpx.setDgXuat(Float.parseFloat(txtDgXuat.getText()));
                            boolean isThemCTPX = controlCTPX.Them(ctpx);
                            // Nếu thêm thành công
                            if(isThemCTPX) {
                                // Cập nhật số lượng hàng sản phẩm
                                boolean isUpdateSoLuongSP = controlSP.updateSoLuongSP(maSpPX, soLuongHienTai, Integer.parseInt(txtSoLuongXuat.getText()));
                                if(isUpdateSoLuongSP) {
                                    JOptionPane.showMessageDialog(this, "Thêm phiếu xuất thành công");
                                    LinkedList<Object[]> lstPX = controlPX.getData();
                                    LoadTbPhieuXuat(lstPX);
                                    ClearTextPX();
                                } else
                                    JOptionPane.showMessageDialog(this, "Thêm phiếu xuất không thành công");
                            } else {
                                controlPX.XoaPhieuXuat(maPX);
                                JOptionPane.showMessageDialog(this, "Thêm phiếu xuất không thành công");
                            }
                        } else
                            JOptionPane.showMessageDialog(this, "Không tìm thấy mã phiếu xuất");
                    } else
                        JOptionPane.showMessageDialog(this, "Thêm phiếu xuất không thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Số lượng hàng không đủ");
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnThemPXMouseClicked

    // Sự kiện click comboBox mã sản phẩm
    // Gán giá bán vào text input
    private void cbMaSpPXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbMaSpPXMouseClicked
        
    }//GEN-LAST:event_cbMaSpPXMouseClicked

    // Sự kiện click table phiếu xuất gán giá trị vào input
    private void tbPhieuXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhieuXuatMouseClicked
        // get dữ liệu khi click row table
        int index = tbPhieuXuat.getSelectedRow();
        TableModel model = tbPhieuXuat.getModel();
        maPX = Integer.parseInt(model.getValueAt(index, 0).toString());
        String tenKH = model.getValueAt(index, 1).toString();
        int maSP = Integer.parseInt(model.getValueAt(index, 2).toString());
        String tenSP = model.getValueAt(index, 3).toString();
        String ngayXuat = model.getValueAt(index, 4).toString();
        String soLuong = model.getValueAt(index, 5).toString();
        String giaXuat = model.getValueAt(index, 6).toString();
        
        // set dữ liệu ra text input
        txtTenKhXuat.setText(tenKH);
        txtDgXuat.setText(giaXuat);
        txtSoLuongXuat.setText(soLuong);
        
        try {
            controlSP = new SanPhamController();
            LinkedList<Integer> lstMaSP = controlSP.getLstMaSP();
            for(int i = 0; i < lstMaSP.size(); ++i) {
                if(maSP == lstMaSP.get(i)){
                    cbMaSpPX.setSelectedIndex(i);
                    break;
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbPhieuXuatMouseClicked

    // Sự kiện sửa phiếu xuất
    private void btnSuaPXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaPXMouseClicked
        if(txtTenKhXuat.getText().isEmpty() || txtDgXuat.getText().isEmpty() || txtSoLuongXuat.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        else {
            try {
                // Lấy số lượng hàng đã xuất
                controlCTPX = new ChiTietPhieuXuatController();
                int soLuongTruoc = controlCTPX.getSoLuongXuat(maPX);
                if(soLuongTruoc >= 0) {
                    // Kiểm tra số lượng hàng có đáp ứng đủ hay ko
                    controlSP = new SanPhamController();
                    int maSpPX = Integer.parseInt(cbMaSpPX.getSelectedItem().toString());
                    int soLuongHienTai = controlSP.getSoLuong(maSpPX) + soLuongTruoc;
                    
                    if(soLuongHienTai >= Integer.parseInt(txtSoLuongXuat.getText())) {
                        // Sửa dữ liệu bảng phiếu xuất
                        controlPX = new PhieuXuatController();
                        PhieuXuat px = new PhieuXuat();
                        px.setMaPX(maPX);
                        // Lấy ngày giờ hiện tại
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        px.setNgayXuat(dateFormat.format(date));
                        px.setTenKH(txtTenKhXuat.getText());
                        boolean isSuaPX = controlPX.SuaPhieuXuat(px);

                        if(isSuaPX) {
                            // Sửa dữ liệu bảng chi tiết phiếu xuất
                                CTPhieuXuat ctpx = new CTPhieuXuat();
                                ctpx.setMaPX(maPX);
                                ctpx.setMaSP(maSpPX);
                                ctpx.setSlXuat(Integer.parseInt(txtSoLuongXuat.getText()));
                                ctpx.setDgXuat(Float.parseFloat(txtDgXuat.getText()));
                                boolean isSuaCTPX = controlCTPX.Sua(ctpx);
                                // Nếu sửa thành công
                                if(isSuaCTPX) {
                                    // Cập nhật số lượng hàng sản phẩm
                                    boolean isUpdateSoLuongSP = controlSP.updateSoLuongSP(maSpPX, soLuongHienTai, Integer.parseInt(txtSoLuongXuat.getText()));
                                    if(isUpdateSoLuongSP) {
                                        JOptionPane.showMessageDialog(this, "Sửa phiếu xuất thành công");
                                        LinkedList<Object[]> lstPX = controlPX.getData();
                                        LoadTbPhieuXuat(lstPX);
                                        ClearTextPX();
                                    } else
                                        JOptionPane.showMessageDialog(this, "Sửa phiếu xuất không thành công");
                                } else {
                                    controlPX.XoaPhieuXuat(maPX);
                                    JOptionPane.showMessageDialog(this, "Sửa phiếu xuất không thành công");
                                }
                        } else
                            JOptionPane.showMessageDialog(this, "Sửa phiếu xuất không thành công");
                    } else {
                        JOptionPane.showMessageDialog(this, "Số lượng hàng không đủ");
                    }
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSuaPXMouseClicked

    private void btnSuaPXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaPXActionPerformed

    private void btnHuyPXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyPXActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyPXActionPerformed

    // Sự kiện click hủy phiếu xuất
    private void btnHuyPXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyPXMouseClicked
        ClearTextPX();
    }//GEN-LAST:event_btnHuyPXMouseClicked

    // Sự kiện xóa phiếu xuất
    private void btnXoaPXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaPXMouseClicked
        try {
            controlPX = new PhieuXuatController();
            boolean isXoaPX = controlPX.XoaPhieuXuat(maPX);
            
            if(isXoaPX) {
                JOptionPane.showMessageDialog(this, "Xóa phiếu xuất thành công");
                LinkedList<Object[]> lstPX = controlPX.getData();
                LoadTbPhieuXuat(lstPX);
                ClearTextPX();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa phiếu xuất không thành công");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnXoaPXMouseClicked

    // Chức năng thống kê
    private void cbThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbThongKeActionPerformed
        
        // Thống kê tổng doanh thu
        if(cbThongKe.getSelectedItem() == "  Tổng doanh thu") {
            
            panelTKTongDT.setVisible(true);
            panelTKSpBanChay.setVisible(false);
            panelNhapHang.setVisible(false);
            panelTrangChu.setVisible(false);
            panelXuatHang.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            try {
                // Hiển thị dữ liệu lên bảng hóa đơn
                controlTK = new ThongKeController();
                LinkedList<Object[]> lstDT = controlTK.ThongKeTongDT();
                LoadTbThongKeTongDT(lstDT);
                // Hiển thị tổng doanh thu
                double tongDT = controlTK.getTongDT();
                BigDecimal decimal = new BigDecimal(tongDT);
                lbTongDT.setText(decimal.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            
        } else if(cbThongKe.getSelectedItem() == "  Sản phẩm bán chạy") {  
            // Thống kê sản phẩm bán chạy
            panelTKSpBanChay.setVisible(true);
            panelTKTongDT.setVisible(false);
            panelXuatHang.setVisible(false);
            panelNhapHang.setVisible(false);
            panelTrangChu.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            
            try {
                controlTK = new ThongKeController();
                LoadTbThongKeSPBanChay();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } else {
            panelTrangChu.setVisible(true);
            panelNhapHang.setVisible(false);
            panelXuatHang.setVisible(false);
            panelQuanLyNCC.setVisible(false);
            panelQLSP.setVisible(false);
            panelTKSpBanChay.setVisible(false);
            panelTKTongDT.setVisible(false);
        }
    }//GEN-LAST:event_cbThongKeActionPerformed

    // Sự kiện xuất file pdf thống kê doanh thu
    private void btnPrintThongKeDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintThongKeDTMouseClicked
        String thongtin = "Danh sách hóa đơn";
        MessageFormat header = new MessageFormat(thongtin);
        MessageFormat footer = new MessageFormat("Tổng doanh thu: " + lbTongDT.getText());
        
        try {
            tbThongKeDT.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPrintThongKeDTMouseClicked

    // Sự kiện thay đổi item comboBox mã phiếu xuất
    // Gán giá bán vào label
    private void cbMaSpPXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMaSpPXItemStateChanged
        lbTenSpPX.setText("Tên sản phẩm: ");
        lbSluongSpPX.setText("Số lượng hàng: ");
        try {
            controlSP = new SanPhamController();
            int maSpPX = Integer.parseInt(cbMaSpPX.getSelectedItem().toString());
            // Lấy thông tin sản phẩm
            SanPham sp = controlSP.getSanPham(maSpPX);
            double getGiaBan = sp.getGiaBan();
            BigDecimal giaBan = new BigDecimal(getGiaBan);
            txtDgXuat.setText(giaBan.toString());
            lbTenSpPX.setText(lbTenSpPX.getText() + sp.getTenSP());
            lbSluongSpPX.setText(lbSluongSpPX.getText() + sp.getSoLuongHang());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_cbMaSpPXItemStateChanged

    
    private void cbQLNXItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbQLNXItemStateChanged
        
    }//GEN-LAST:event_cbQLNXItemStateChanged

    private void btnSearchThongKeDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchThongKeDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchThongKeDTActionPerformed

    // Sự kiện lọc hóa đơn theo ngày đã chọn
    private void btnSearchThongKeDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchThongKeDTMouseClicked
        if(txtSearchTkStart.getText().isEmpty() || txtSearchTkEnd.getText().isEmpty())
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
        else {
            // Kiểm tra xem có đúng định dạng ngày hay ko
            try {
                String startDate = txtSearchTkStart.getText();
                String endDate = txtSearchTkEnd.getText();
                
                Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(startDate);
                Date date2 = new SimpleDateFormat("yyyy/MM/dd").parse(endDate);
                
                // Thực hiện lọc
                try {
                    // Hiển thị dữ liệu lên table
                    controlTK = new ThongKeController();
                    LinkedList<Object[]> lstDT = controlTK.ThongKeTongDTTheoNgay(txtSearchTkStart.getText(), txtSearchTkEnd.getText());
                    LoadTbThongKeTongDT(lstDT);
                    
                    // Hiển thị tổng doanh thu
                    double tonDT = controlTK.getTongDTTheoNgay(startDate, endDate);
                    BigDecimal decimal = new BigDecimal(tonDT);
                    lbTongDT.setText(decimal.toString());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Nhập đúng định dạng yyyy/mm/dd");
            }
        }
    }//GEN-LAST:event_btnSearchThongKeDTMouseClicked

    // Sự kiện làm mới table thống kê doanh thu
    private void btnRefreshThongKeDTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshThongKeDTMouseClicked
        try {
            controlTK = new ThongKeController();
            LinkedList<Object[]> lstDT = controlTK.ThongKeTongDT();
            LoadTbThongKeTongDT(lstDT);
            
            // Hiển thị tổng doanh thu
            double tonDT = controlTK.getTongDT();
            BigDecimal decimal = new BigDecimal(tonDT);
            lbTongDT.setText(decimal.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRefreshThongKeDTMouseClicked

    // Sự kiện xuất file pdf danh sách sản phẩm bán chạy
    private void btnPrintSpBanChayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintSpBanChayMouseClicked
        String thongtin = "Danh sách sản phẩm bán chạy";
        MessageFormat header = new MessageFormat(thongtin);
        MessageFormat footer = new MessageFormat("Trang {0, number, integer}");
        
        try {
            tbThongKeSpBanChay.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(Trangchu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPrintSpBanChayMouseClicked

    // Phương thức load table thống kê tổng doanh thu
    public void LoadTbThongKeSPBanChay() throws ClassNotFoundException, SQLException {
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng đã bán", "Giá bán"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbThongKeSpBanChay.setModel(model);
        tbThongKeSpBanChay.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        controlTK = new ThongKeController();
        LinkedList<Object[]> lstSpBanChay = controlTK.ThongKeSpBanChay();
        
        // Danh sách thông tin phiếu nhập
        for(var obj : lstSpBanChay) {
            model.addRow(obj);
        }
    }
    
    // Phương thức load table thống kê tổng doanh thu
    public void LoadTbThongKeTongDT(LinkedList<Object[]> lstDT) throws ClassNotFoundException, SQLException {
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã", "Tên khách hàng", "Tên sản phẩm", "Ngày xuất", "Số lượng", "Giá xuất"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbThongKeDT.setModel(model);
        tbThongKeDT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Danh sách thông tin phiếu nhập
        for(var obj : lstDT) {
            model.addRow(obj);
        }
    }
    
    // Phương thức clear text phiếu xuất
    private  void ClearTextPX() {
        txtTenKhXuat.setText("");
        cbMaSpPX.setSelectedIndex(0);
        txtSoLuongXuat.setText("");
        txtDgXuat.setText("");
    }
    
    // Phương thức load dữ liệu table phiếu xuất
    private void LoadTbPhieuXuat(LinkedList<Object[]> lstPX) {
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã", "Tên khách hàng", "Mã sản phẩm", "Tên sản phẩm", "Ngày xuất", "Số lượng", "Giá xuất"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tbPhieuXuat.setModel(model);
        tbPhieuXuat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Danh sách thông tin phiếu nhập
        for(var obj : lstPX) {
            model.addRow(obj);
        }
    }
    
    // Phương thức load dữ liệu table Sản phẩm
    private void LoadTbSanPham(LinkedList<Object[]> lstSP) throws ClassNotFoundException, SQLException {
        // Hiển thị dữ liệu lên table
        String[] columnsName = {"Mã", "Tên", "Đơn vị tính", "Giá bán", "Màu sắc", "Kích thước mh", "RAM", "Bộ nhớ trong", "Pin", "Hệ điều hành", "Số lượng"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnsName);
        tableSanPham.setModel(model);
        tableSanPham.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Danh sách thông tin phiếu nhập
        for(var obj : lstSP) {
            model.addRow(obj);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnHuyPN;
    private javax.swing.JButton btnHuyPX;
    private javax.swing.JButton btnLuuPN;
    private javax.swing.JButton btnPrintSpBanChay;
    private javax.swing.JButton btnPrintThongKeDT;
    private javax.swing.JButton btnQLNCC;
    private javax.swing.JButton btnQLSP;
    private javax.swing.JButton btnRefreshThongKeDT;
    private javax.swing.JButton btnSearchThongKeDT;
    private javax.swing.JButton btnSuaNCC;
    private javax.swing.JButton btnSuaPN;
    private javax.swing.JButton btnSuaPX;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemNCC;
    private javax.swing.JButton btnThemPX;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTimKiemNCC;
    private javax.swing.JButton btnTimKiemSP;
    private javax.swing.JButton btnXoaNCC;
    private javax.swing.JButton btnXoaPN;
    private javax.swing.JButton btnXoaPX;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JComboBox<String> cbMaSpPX;
    private javax.swing.JComboBox<String> cbQLNX;
    private javax.swing.JComboBox<String> cbThongKe;
    private javax.swing.JComboBox<String> cbTimKiemSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel lbSluongSpPX;
    private javax.swing.JLabel lbTenDN;
    private javax.swing.JLabel lbTenSpPX;
    private javax.swing.JLabel lbTongDT;
    private javax.swing.JLabel lbTongPN;
    private javax.swing.JLabel lbTongPX;
    private javax.swing.JLabel lbTongSP;
    private javax.swing.JLabel lbTongTK;
    private javax.swing.JPanel panelBarChart;
    private javax.swing.JPanel panelHistogram;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JPanel panelLineChart1;
    private javax.swing.JPanel panelLineChart2;
    private javax.swing.JPanel panelNhapHang;
    private javax.swing.JPanel panelQLSP;
    private javax.swing.JPanel panelQuanLyNCC;
    private javax.swing.JPanel panelTKSpBanChay;
    private javax.swing.JPanel panelTKTongDT;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel panelXuatHang;
    private javax.swing.JTable tableSanPham;
    private javax.swing.JTable tbPhieuNhap;
    private javax.swing.JTable tbPhieuXuat;
    private javax.swing.JTable tbQuanLyNCC;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tbThongKeDT;
    private javax.swing.JTable tbThongKeSpBanChay;
    private javax.swing.JTextField textBoNhoRAM;
    private javax.swing.JTextField textBoNhoTrong;
    private javax.swing.JTextField textDcNCC;
    private javax.swing.JTextField textDvTinh;
    private javax.swing.JTextField textGiaBan;
    private javax.swing.JTextField textHeDieuHanh;
    private javax.swing.JTextField textKichThuocMH;
    private javax.swing.JTextField textMauSP;
    private javax.swing.JTextField textPINSP;
    private javax.swing.JTextField textSdtNCC;
    private javax.swing.JTextField textSoLuong;
    private javax.swing.JTextField textTenNCC;
    private javax.swing.JTextField textTenSP;
    private javax.swing.JTextField txtDGNhap;
    private javax.swing.JTextField txtDgXuat;
    private javax.swing.JTextField txtDiaChiNccNhap;
    private javax.swing.JTextField txtDvTinhNhap;
    private javax.swing.JTextField txtSdtNccNhap;
    private javax.swing.JTextField txtSearchTkEnd;
    private javax.swing.JTextField txtSearchTkStart;
    private javax.swing.JTextField txtSoLuongSpNhap;
    private javax.swing.JTextField txtSoLuongXuat;
    private javax.swing.JTextField txtTenKhXuat;
    private javax.swing.JTextField txtTenNccNhap;
    private javax.swing.JTextField txtTenSpNhap;
    private javax.swing.JTextField txtTimKiemNCC;
    private javax.swing.JTextField txtTimKiemSP;
    // End of variables declaration//GEN-END:variables
}