package com.window;

import com.koneksi.Database;
import com.manage.ChartManager;
import com.manage.Message;
import com.manage.Text;
import com.ui.UIManager;
import com.manage.User;
import com.manage.Waktu;
import com.media.Gambar;
import com.window.dialog.InfoApp;
import com.window.dialog.Pengaturan;
import com.window.dialog.UserProfile;
import java.awt.Cursor;
import java.sql.SQLException;
import javax.swing.JLabel;


/**
 *
 * @author Achmad Baihaqi
 */
public class Dashboard extends javax.swing.JFrame {
    
    private final Database db = new Database();
    
    private final UIManager win = new UIManager();
    
    private final ChartManager chart = new ChartManager();
    
    private final Waktu waktu = new Waktu();
    
    private final Text txt = new Text();
    
    private boolean status;
    
    public Dashboard() {
        initComponents();
        
        this.status = true;
        this.setTitle("Menu Dashboard");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Gambar.getWindowIcon());
        this.chart.showPieChart(this.pnlPieChart, ChartManager.PENDAPATAN, "Presentase Penjualan Bulan Ini", waktu.getBulan(), waktu.getTahun());
        this.chart.showLineChart(this.pnlLineChart, ChartManager.PENDAPATAN, "Penjualan Produk Bulan Ini", waktu.getBulan(), waktu.getTahun());
        this.lblNamaUser.setText(User.getNamaUser());
        
        this.win.btns = new JLabel[]{
            this.btnKaryawan, this.btnSupplier, this.btnPembeli, 
            this.btnTransaksi, this.btnLaporan, this.btnBahan, this.btnLogout, this.btnMenu
        };
        
        this.win.hoverButton();
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    System.out.println("update waktu is " + status);
                    while(status){
                        lblServerTime.setText(waktu.getUpdateWaktu());
                        Thread.sleep(1);
                    }
                    System.out.println("update waktu is false");
                }catch(InterruptedException iex){
                    Message.showException(null, iex);
                }
            }
        }).start();
        
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        
        this.getPenjualan();
        this.getPembelian();
    }
    
    private void getPenjualan(){
        try{
            String sql = "SELECT SUM(tj.total_harga) AS pendapatan, COUNT(tj.id_tr_jual) AS pembeli " +
                         "FROM transaksi_jual AS tj " +
                         "WHERE MONTH(tj.tanggal) = "+waktu.getBulan()+" AND YEAR(tj.tanggal) = " + waktu.getTahun();
            String pendapatan, pembeli;
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // mendapatkan data
                pendapatan = txt.toMoneyCase(this.db.res.getString("pendapatan"));
                pembeli = this.db.res.getString("pembeli") + " Pembeli";
                // menampilkan data
                this.valPendapatan.setText(" "+pendapatan.substring(0, pendapatan.lastIndexOf(".")).replaceAll(",", "."));
                this.valPembeli.setText(pembeli);
            }
            
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
    }
    
    private void getPembelian(){
        try{
            String sql = "SELECT SUM(tb.total_harga) AS pengeluaran " +
                         "FROM transaksi_beli AS tb " +
                         "WHERE MONTH(tb.tanggal) = "+waktu.getBulan()+" AND YEAR(tb.tanggal) = " + waktu.getTahun();
            String pendapatan;
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // mendapatkan dan menampilkan data
                pendapatan = txt.toMoneyCase(this.db.res.getString("pengeluaran"));
                this.valPengeluaran.setText(pendapatan.substring(0, pendapatan.lastIndexOf(".")).replaceAll(",", "."));
            }
            
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        btnMenu = new javax.swing.JLabel();
        btnBahan = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        lineSideMenu1 = new javax.swing.JSeparator();
        lblProfileSidebar = new javax.swing.JLabel();
        btnTransaksi = new javax.swing.JLabel();
        btnDataMaster = new javax.swing.JLabel();
        btnLaporan = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JLabel();
        btnKaryawan = new javax.swing.JLabel();
        btnPembeli = new javax.swing.JLabel();
        btnSupplier = new javax.swing.JLabel();
        pnlTop = new com.ui.RoundedPanel();
        lblIconWindow = new javax.swing.JLabel();
        lblNamaWindow = new javax.swing.JLabel();
        lblTopSetting = new javax.swing.JLabel();
        lblTopInfo = new javax.swing.JLabel();
        lblTopProfile = new javax.swing.JLabel();
        pnlContent = new com.ui.RoundedPanel();
        pnlPendapatan = new com.ui.RoundedPanel();
        lblPendapatan = new javax.swing.JLabel();
        valPendapatan = new javax.swing.JLabel();
        iconPendapatan = new javax.swing.JLabel();
        panelChart = new javax.swing.JPanel();
        pnlCharTop = new javax.swing.JPanel();
        lblNamaChart = new javax.swing.JLabel();
        lblServerTime = new javax.swing.JLabel();
        pnlLineChart = new javax.swing.JPanel();
        pnlPieChart = new javax.swing.JPanel();
        pnlTotalPenjualan1 = new com.ui.RoundedPanel();
        lblPengeluaran = new javax.swing.JLabel();
        valPengeluaran = new javax.swing.JLabel();
        iconPengeluaran = new javax.swing.JLabel();
        pnlTotalPenjualan2 = new com.ui.RoundedPanel();
        lblPembeli = new javax.swing.JLabel();
        valPembeli = new javax.swing.JLabel();
        iconPembeli = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblBottom = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(112, 160, 227));
        pnlMain.setPreferredSize(new java.awt.Dimension(1341, 768));

        pnlSidebar.setBackground(new java.awt.Color(35, 99, 210));
        pnlSidebar.setPreferredSize(new java.awt.Dimension(254, 730));

        btnMenu.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-coffe.png"))); // NOI18N
        btnMenu.setText("Data Menu");
        btnMenu.setIconTextGap(7);
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
        });

        btnBahan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidebar-bahan.png"))); // NOI18N
        btnBahan.setText("Data Bahan");
        btnBahan.setIconTextGap(7);
        btnBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBahanMouseClicked(evt);
            }
        });

        lblNamaUser.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 20)); // NOI18N
        lblNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNamaUser.setText("Achmad Baihaqi");

        lineSideMenu1.setForeground(new java.awt.Color(255, 255, 255));

        lblProfileSidebar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfileSidebar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidebar-userprofile.png"))); // NOI18N
        lblProfileSidebar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        btnTransaksi.setBackground(new java.awt.Color(96, 180, 235));
        btnTransaksi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-trjual.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setIconTextGap(7);
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
        });

        btnDataMaster.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        btnDataMaster.setForeground(new java.awt.Color(255, 255, 255));
        btnDataMaster.setText("Data Master");

        btnLaporan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-lpjual.png"))); // NOI18N
        btnLaporan.setText("Laporan");
        btnLaporan.setIconTextGap(7);
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaporanMouseClicked(evt);
            }
        });

        lblMenu.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        lblMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu.setText("Menu");

        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setIconTextGap(7);
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        btnDashboard.setBackground(new java.awt.Color(166, 203, 227));
        btnDashboard.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-home-dark.png"))); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.setIconTextGap(7);
        btnDashboard.setOpaque(true);
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });

        btnKaryawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        btnKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-karyawan.png"))); // NOI18N
        btnKaryawan.setText("Data Karyawan");
        btnKaryawan.setIconTextGap(7);
        btnKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKaryawanMouseClicked(evt);
            }
        });

        btnPembeli.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnPembeli.setForeground(new java.awt.Color(255, 255, 255));
        btnPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-pembeli.png"))); // NOI18N
        btnPembeli.setText("Data Pembeli");
        btnPembeli.setIconTextGap(7);
        btnPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPembeliMouseClicked(evt);
            }
        });

        btnSupplier.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-supplier.png"))); // NOI18N
        btnSupplier.setText("Data Supplier");
        btnSupplier.setIconTextGap(7);
        btnSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupplierMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProfileSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDataMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(lineSideMenu1))
                        .addGap(10, 10, 10)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                .addGap(0, 33, Short.MAX_VALUE)
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProfileSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineSideMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnDataMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTop.setBackground(new java.awt.Color(248, 249, 250));
        pnlTop.setRoundBottomLeft(20);
        pnlTop.setRoundBottomRight(20);
        pnlTop.setRoundTopLeft(20);
        pnlTop.setRoundTopRight(20);

        lblIconWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-topleft-dashboard.png"))); // NOI18N

        lblNamaWindow.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblNamaWindow.setForeground(new java.awt.Color(0, 21, 39));
        lblNamaWindow.setText("Dashboard");

        lblTopSetting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-setting.png"))); // NOI18N
        lblTopSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseExited(evt);
            }
        });

        lblTopInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-info.png"))); // NOI18N
        lblTopInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseExited(evt);
            }
        });

        lblTopProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-miniuser.png"))); // NOI18N
        lblTopProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIconWindow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNamaWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTopSetting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTopInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblTopProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNamaWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblTopSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlContent.setBackground(new java.awt.Color(248, 249, 250));
        pnlContent.setRoundBottomLeft(20);
        pnlContent.setRoundBottomRight(20);
        pnlContent.setRoundTopLeft(20);
        pnlContent.setRoundTopRight(20);

        pnlPendapatan.setBackground(new java.awt.Color(123, 123, 245));
        pnlPendapatan.setRoundBottomLeft(10);
        pnlPendapatan.setRoundBottomRight(50);
        pnlPendapatan.setRoundTopLeft(60);
        pnlPendapatan.setRoundTopRight(10);

        lblPendapatan.setFont(new java.awt.Font("Ebrima", 1, 22)); // NOI18N
        lblPendapatan.setForeground(new java.awt.Color(255, 255, 255));
        lblPendapatan.setText(" Pendapatan Bulan Ini");
        lblPendapatan.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        valPendapatan.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        valPendapatan.setForeground(new java.awt.Color(255, 255, 255));
        valPendapatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        valPendapatan.setText(" Rp. 1.256.000");
        valPendapatan.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        iconPendapatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPendapatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-dashboard-pendapatan.png"))); // NOI18N
        iconPendapatan.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlPendapatanLayout = new javax.swing.GroupLayout(pnlPendapatan);
        pnlPendapatan.setLayout(pnlPendapatanLayout);
        pnlPendapatanLayout.setHorizontalGroup(
            pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPendapatanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPendapatan, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                    .addGroup(pnlPendapatanLayout.createSequentialGroup()
                        .addComponent(iconPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(valPendapatan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlPendapatanLayout.setVerticalGroup(
            pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPendapatanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconPendapatan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        panelChart.setBackground(new java.awt.Color(255, 255, 255));
        panelChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(61, 115, 239), 3));

        pnlCharTop.setBackground(new java.awt.Color(61, 115, 239));

        lblNamaChart.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNamaChart.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaChart.setText(" Penjualan Dalam Sebulan Terakhir");

        lblServerTime.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblServerTime.setForeground(new java.awt.Color(255, 255, 255));
        lblServerTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServerTime.setText("Minggu, 19 November 2022 08:30:59");

        javax.swing.GroupLayout pnlCharTopLayout = new javax.swing.GroupLayout(pnlCharTop);
        pnlCharTop.setLayout(pnlCharTopLayout);
        pnlCharTopLayout.setHorizontalGroup(
            pnlCharTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCharTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNamaChart, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addGap(185, 185, 185)
                .addComponent(lblServerTime, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCharTopLayout.setVerticalGroup(
            pnlCharTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNamaChart, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lblServerTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlLineChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlLineChart.setLayout(new java.awt.BorderLayout());

        pnlPieChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlPieChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlLineChart, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnlPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlCharTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChartLayout.createSequentialGroup()
                .addComponent(pnlCharTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlTotalPenjualan1.setBackground(new java.awt.Color(186, 123, 247));
        pnlTotalPenjualan1.setRoundBottomLeft(10);
        pnlTotalPenjualan1.setRoundBottomRight(50);
        pnlTotalPenjualan1.setRoundTopLeft(60);
        pnlTotalPenjualan1.setRoundTopRight(10);

        lblPengeluaran.setFont(new java.awt.Font("Ebrima", 1, 22)); // NOI18N
        lblPengeluaran.setForeground(new java.awt.Color(255, 255, 255));
        lblPengeluaran.setText("Pengeluaran Bulan Ini");
        lblPengeluaran.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        valPengeluaran.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        valPengeluaran.setForeground(new java.awt.Color(255, 255, 255));
        valPengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        valPengeluaran.setText(" Rp. 537.000");
        valPengeluaran.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        iconPengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPengeluaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-dashboard-pengeluaran.png"))); // NOI18N
        iconPengeluaran.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlTotalPenjualan1Layout = new javax.swing.GroupLayout(pnlTotalPenjualan1);
        pnlTotalPenjualan1.setLayout(pnlTotalPenjualan1Layout);
        pnlTotalPenjualan1Layout.setHorizontalGroup(
            pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                    .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                        .addComponent(iconPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(valPengeluaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlTotalPenjualan1Layout.setVerticalGroup(
            pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalPenjualan1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTotalPenjualan2.setBackground(new java.awt.Color(219, 120, 49));
        pnlTotalPenjualan2.setRoundBottomLeft(10);
        pnlTotalPenjualan2.setRoundBottomRight(50);
        pnlTotalPenjualan2.setRoundTopLeft(60);
        pnlTotalPenjualan2.setRoundTopRight(10);

        lblPembeli.setFont(new java.awt.Font("Ebrima", 1, 22)); // NOI18N
        lblPembeli.setForeground(new java.awt.Color(255, 255, 255));
        lblPembeli.setText("Pembelian Bulan Ini");
        lblPembeli.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        valPembeli.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        valPembeli.setForeground(new java.awt.Color(255, 255, 255));
        valPembeli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        valPembeli.setText("45 Pembeli");
        valPembeli.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        iconPembeli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-dashboard-pembeli.png"))); // NOI18N
        iconPembeli.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlTotalPenjualan2Layout = new javax.swing.GroupLayout(pnlTotalPenjualan2);
        pnlTotalPenjualan2.setLayout(pnlTotalPenjualan2Layout);
        pnlTotalPenjualan2Layout.setHorizontalGroup(
            pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPembeli, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                        .addComponent(iconPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(valPembeli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlTotalPenjualan2Layout.setVerticalGroup(
            pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalPenjualan2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Can You See Me?");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(pnlPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlTotalPenjualan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlTotalPenjualan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTotalPenjualan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTotalPenjualan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jScrollPane1.setViewportView(pnlMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataMenu window = new DataMenu();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnMenuMouseClicked

    private void btnBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBahanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataBahan window = new DataBahan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnBahanMouseClicked

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuTransaksi window = new MenuTransaksi();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void btnLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuLaporan window = new MenuLaporan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnLaporanMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        new User().logout();
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Dashboard window = new Dashboard();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKaryawanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataKaryawan window = new DataKaryawan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnKaryawanMouseClicked

    private void btnPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPembeliMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataPembeli window = new DataPembeli();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnPembeliMouseClicked

    private void btnSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupplierMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataSupplier window = new DataSupplier();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnSupplierMouseClicked

    private void lblTopProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseClicked
        UserProfile dia = new UserProfile(null, true, this);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopProfileMouseClicked

    private void lblTopProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopProfile.setIcon(Gambar.getIcon("ic-window-top-miniuser-entered.png"));
    }//GEN-LAST:event_lblTopProfileMouseEntered

    private void lblTopProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopProfile.setIcon(Gambar.getIcon("ic-window-top-miniuser.png"));
    }//GEN-LAST:event_lblTopProfileMouseExited

    private void lblTopSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseClicked
        Pengaturan dia = new Pengaturan(null, true);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopSettingMouseClicked

    private void lblTopSettingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopSetting.setIcon(Gambar.getIcon("ic-window-top-setting-entered.png"));
    }//GEN-LAST:event_lblTopSettingMouseEntered

    private void lblTopSettingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopSetting.setIcon(Gambar.getIcon("ic-window-top-setting.png"));
    }//GEN-LAST:event_lblTopSettingMouseExited

    private void lblTopInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseClicked
        InfoApp dia = new InfoApp(null, true);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopInfoMouseClicked

    private void lblTopInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopInfo.setIcon(Gambar.getIcon("ic-window-top-info-entered.png"));
    }//GEN-LAST:event_lblTopInfoMouseEntered

    private void lblTopInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopInfo.setIcon(Gambar.getIcon("ic-window-top-info.png"));
    }//GEN-LAST:event_lblTopInfoMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.status = true;
        System.out.println(this.getClass().getName() + " opened");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.status = true;
        System.out.println(this.getClass().getName() + " activated");
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        System.out.println(this.getClass().getName() + " deactivated");
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.status = false;
        this.chart.closeConnection();
        this.db.closeConnection();
        System.out.println(this.getClass().getName() + " closed");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println(this.getClass().getName() + " closing");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBahan;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnDataMaster;
    private javax.swing.JLabel btnKaryawan;
    private javax.swing.JLabel btnLaporan;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnMenu;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JLabel iconPembeli;
    private javax.swing.JLabel iconPendapatan;
    private javax.swing.JLabel iconPengeluaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNamaChart;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblPembeli;
    private javax.swing.JLabel lblPendapatan;
    private javax.swing.JLabel lblPengeluaran;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblServerTime;
    private javax.swing.JLabel lblTopInfo;
    private javax.swing.JLabel lblTopProfile;
    private javax.swing.JLabel lblTopSetting;
    private javax.swing.JSeparator lineSideMenu1;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel pnlCharTop;
    private com.ui.RoundedPanel pnlContent;
    private javax.swing.JPanel pnlLineChart;
    private javax.swing.JPanel pnlMain;
    private com.ui.RoundedPanel pnlPendapatan;
    private javax.swing.JPanel pnlPieChart;
    private javax.swing.JPanel pnlSidebar;
    private com.ui.RoundedPanel pnlTop;
    private com.ui.RoundedPanel pnlTotalPenjualan1;
    private com.ui.RoundedPanel pnlTotalPenjualan2;
    private javax.swing.JLabel valPembeli;
    private javax.swing.JLabel valPendapatan;
    private javax.swing.JLabel valPengeluaran;
    // End of variables declaration//GEN-END:variables
}
