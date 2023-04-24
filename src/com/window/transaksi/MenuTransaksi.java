package com.window.transaksi;

import com.ui.UIManager;
import com.manage.User;
import com.media.Gambar;
import com.window.Dashboard;
import com.window.DataBahan;
import com.window.DataAkun;
import com.window.DataMenu;
import com.window.DataPembeli;
import com.window.DataSupplier;
import com.window.laporan.MenuLaporan;
import com.window.dialog.InfoApp;
import com.window.dialog.Pengaturan;
import com.window.dialog.UserProfile;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class MenuTransaksi extends javax.swing.JFrame {
    
    private final UIManager win = new UIManager();
    
    public MenuTransaksi() {
        initComponents();
        
        this.setTitle("Menu Transaksi");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Gambar.getWindowIcon());
        this.lblNamaUser.setText(User.getNamaUser());
        this.win.btns = new JLabel[]{
            this.btnKaryawan, this.btnSupplier, this.btnPembeli, 
            this.btnDashboard, this.btnLaporan, this.btnBahan, this.btnLogout, this.btnMenu
        };
        
        this.win.hoverButton();
        
        // set ui button
        this.btnTrBeli.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnTrJual.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // set hidden button
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        this.btnSupplier.setVisible(false);
        
         if(!User.isAdmin()){
            this.btnKaryawan.setVisible(false);
            this.btnLaporan.setVisible(false);
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
        pnlTotalPenjualan1 = new com.ui.RoundedPanel();
        jLabel2 = new javax.swing.JLabel();
        btnTrBeli = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlTotalPenjualan2 = new com.ui.RoundedPanel();
        jLabel3 = new javax.swing.JLabel();
        btnTrJual = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
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

        btnTransaksi.setBackground(new java.awt.Color(166, 203, 227));
        btnTransaksi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-trjual-dark.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setIconTextGap(7);
        btnTransaksi.setOpaque(true);
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
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-home.png"))); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.setIconTextGap(7);
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });

        btnKaryawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        btnKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-karyawan.png"))); // NOI18N
        btnKaryawan.setText("Data Akun");
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
                            .addComponent(lblNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProfileSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDataMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(lineSideMenu1))
                        .addGap(10, 10, 10)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
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
                .addContainerGap(88, Short.MAX_VALUE))
        );

        pnlTop.setBackground(new java.awt.Color(248, 249, 250));
        pnlTop.setRoundBottomLeft(20);
        pnlTop.setRoundBottomRight(20);
        pnlTop.setRoundTopLeft(20);
        pnlTop.setRoundTopRight(20);

        lblIconWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-topleft-transaksi.png"))); // NOI18N

        lblNamaWindow.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblNamaWindow.setForeground(new java.awt.Color(0, 21, 39));
        lblNamaWindow.setText("Menu Transaksi");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                .addComponent(lblTopSetting)
                .addGap(5, 5, 5)
                .addComponent(lblTopInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTopProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
            .addComponent(lblNamaWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlContent.setBackground(new java.awt.Color(248, 249, 250));
        pnlContent.setRoundBottomLeft(20);
        pnlContent.setRoundBottomRight(20);
        pnlContent.setRoundTopLeft(20);
        pnlContent.setRoundTopRight(20);

        pnlTotalPenjualan1.setBackground(new java.awt.Color(217, 221, 234));
        pnlTotalPenjualan1.setRoundBottomLeft(10);
        pnlTotalPenjualan1.setRoundBottomRight(100);
        pnlTotalPenjualan1.setRoundTopLeft(100);
        pnlTotalPenjualan1.setRoundTopRight(10);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 90, 225));
        jLabel2.setText("  Pembelian Suplai Bahan");

        btnTrBeli.setBackground(new java.awt.Color(84, 173, 247));
        btnTrBeli.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTrBeli.setForeground(new java.awt.Color(255, 255, 255));
        btnTrBeli.setText("Buka");
        btnTrBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrBeliActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/icons8_bar_chart_50px_1.png"))); // NOI18N
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlTotalPenjualan1Layout = new javax.swing.GroupLayout(pnlTotalPenjualan1);
        pnlTotalPenjualan1.setLayout(pnlTotalPenjualan1Layout);
        pnlTotalPenjualan1Layout.setHorizontalGroup(
            pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalPenjualan1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                .addGroup(pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btnTrBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTotalPenjualan1Layout.setVerticalGroup(
            pnlTotalPenjualan1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalPenjualan1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTrBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnlTotalPenjualan2.setBackground(new java.awt.Color(217, 221, 234));
        pnlTotalPenjualan2.setRoundBottomLeft(10);
        pnlTotalPenjualan2.setRoundBottomRight(100);
        pnlTotalPenjualan2.setRoundTopLeft(100);
        pnlTotalPenjualan2.setRoundTopRight(10);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 90, 225));
        jLabel3.setText("  Transaksi Penjualan");

        btnTrJual.setBackground(new java.awt.Color(84, 173, 247));
        btnTrJual.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTrJual.setForeground(new java.awt.Color(255, 255, 255));
        btnTrJual.setText("Buka");
        btnTrJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrJualActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/icons8_line_chart_50px.png"))); // NOI18N
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlTotalPenjualan2Layout = new javax.swing.GroupLayout(pnlTotalPenjualan2);
        pnlTotalPenjualan2.setLayout(pnlTotalPenjualan2Layout);
        pnlTotalPenjualan2Layout.setHorizontalGroup(
            pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotalPenjualan2Layout.createSequentialGroup()
                .addGap(0, 40, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                .addGroup(pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(btnTrJual, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTotalPenjualan2Layout.setVerticalGroup(
            pnlTotalPenjualan2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotalPenjualan2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTrJual, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(pnlTotalPenjualan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(pnlTotalPenjualan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTotalPenjualan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlTotalPenjualan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(lblBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataMenu window = new DataMenu();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnLaporanMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        JOptionPane.showMessageDialog(null, "Coming Soon!");
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Dashboard window = new Dashboard();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKaryawanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataAkun window = new DataAkun();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
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
        System.out.println(this.getClass().getName() + " opened");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        System.out.println(this.getClass().getName() + " activated");
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        System.out.println(this.getClass().getName() + " deactivated");
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.out.println(this.getClass().getName() + " closed");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println(this.getClass().getName() + " closing");
    }//GEN-LAST:event_formWindowClosing

    private void btnTrBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrBeliActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuTransaksiBeli window = new MenuTransaksiBeli();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnTrBeliActionPerformed

    private void btnTrJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrJualActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuTransaksiJual window = new MenuTransaksiJual();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnTrJualActionPerformed

    
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
            java.util.logging.Logger.getLogger(MenuTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuTransaksi().setVisible(true);
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
    private javax.swing.JButton btnTrBeli;
    private javax.swing.JButton btnTrJual;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblTopInfo;
    private javax.swing.JLabel lblTopProfile;
    private javax.swing.JLabel lblTopSetting;
    private javax.swing.JSeparator lineSideMenu1;
    private com.ui.RoundedPanel pnlContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSidebar;
    private com.ui.RoundedPanel pnlTop;
    private com.ui.RoundedPanel pnlTotalPenjualan1;
    private com.ui.RoundedPanel pnlTotalPenjualan2;
    // End of variables declaration//GEN-END:variables
}
