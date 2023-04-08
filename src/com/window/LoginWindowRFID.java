package com.window;

import com.manage.Message;
import com.manage.User;
import com.media.Gambar;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;

/**
 * Digunakan untuk login bagi admin, petugas dan siswa.
 * 
 * @author Achmad Baihaqi
 */
public class LoginWindowRFID extends javax.swing.JFrame {

    private final User user = new User();
    
    private int x, y;
    
    public LoginWindowRFID() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.setIconImage(Gambar.getWindowIcon());
        this.inpStatus.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlLeft = new javax.swing.JPanel();
        lblLogoApp = new javax.swing.JLabel();
        lblNamaApp = new javax.swing.JLabel();
        lblDesApp = new javax.swing.JLabel();
        lblKembali = new javax.swing.JLabel();
        lblMinimaze = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();
        inpStatus = new com.ui.RoundedTextField(15);
        lblIconKartu = new javax.swing.JLabel();
        lblTop = new javax.swing.JLabel();
        lblAnimStatus = new javax.swing.JLabel();
        lblBenar = new javax.swing.JLabel();
        lblSalah = new javax.swing.JLabel();
        lblBgImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Window");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlMainMouseDragged(evt);
            }
        });
        pnlMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlMainMousePressed(evt);
            }
        });
        pnlMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeft.setBackground(new java.awt.Color(238, 240, 244));

        lblLogoApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/icons8_coffee_pot_145px.png"))); // NOI18N
        lblLogoApp.setToolTipText("");
        lblLogoApp.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblLogoApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoAppMouseClicked(evt);
            }
        });

        lblNamaApp.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        lblNamaApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNamaApp.setText("Kopi Paste");
        lblNamaApp.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblDesApp.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblDesApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDesApp.setText("KOPI Pelayanan ASPIRASI Terpadu");

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogoApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblNamaApp, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
            .addComponent(lblDesApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addComponent(lblLogoApp, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNamaApp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDesApp)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pnlMain.add(pnlLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 380));

        lblKembali.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-loginwindow-kembali.png"))); // NOI18N
        lblKembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKembaliMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblKembaliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblKembaliMouseExited(evt);
            }
        });
        pnlMain.add(lblKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 24, 28));

        lblMinimaze.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblMinimaze.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimaze.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-loginwindow-min.png"))); // NOI18N
        lblMinimaze.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMinimazeMouseExited(evt);
            }
        });
        pnlMain.add(lblMinimaze, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, -1, 28));

        lblClose.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-loginwindow-close.png"))); // NOI18N
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
        });
        pnlMain.add(lblClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 0, 31, 28));

        lblCopyright.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyright.setText("Copyright © 2023. Cito Team. TIF Kampus 3 Polije ");
        lblCopyright.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCopyrightMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCopyrightMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCopyrightMouseExited(evt);
            }
        });
        pnlMain.add(lblCopyright, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 370, 30));

        inpStatus.setEditable(false);
        inpStatus.setBackground(new java.awt.Color(255, 255, 255));
        inpStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpStatus.setForeground(new java.awt.Color(44, 119, 238));
        inpStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpStatus.setText("Tempelkan Kartu Anda ke RFID");
        inpStatus.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpStatus.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpStatus.setMinimumSize(new java.awt.Dimension(6, 35));
        inpStatus.setPreferredSize(new java.awt.Dimension(6, 35));
        inpStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpStatusKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpStatusKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpStatusKeyTyped(evt);
            }
        });
        pnlMain.add(inpStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 272, 39));

        lblIconKartu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ezgif-1-9b17a45a17.gif"))); // NOI18N
        pnlMain.add(lblIconKartu, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 80, 390, 190));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setForeground(new java.awt.Color(250, 56, 56));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Login RFID");
        pnlMain.add(lblTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 390, 40));
        pnlMain.add(lblAnimStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 50, 40));

        lblBenar.setText("b");
        lblBenar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBenarMouseClicked(evt);
            }
        });
        pnlMain.add(lblBenar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 20, 40));

        lblSalah.setText("s");
        lblSalah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalahMouseClicked(evt);
            }
        });
        pnlMain.add(lblSalah, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 280, 20, 40));

        lblBgImage.setBackground(new java.awt.Color(255, 255, 255));
        lblBgImage.setForeground(new java.awt.Color(255, 255, 255));
        pnlMain.add(lblBgImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 0, 386, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlMainMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMainMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_pnlMainMousePressed

    private void pnlMainMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMainMouseDragged
        int xx = evt.getXOnScreen(),
            yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
    }//GEN-LAST:event_pnlMainMouseDragged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.user.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.user.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void lblMinimazeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseExited
        this.lblMinimaze.setIcon(Gambar.getIcon("ic-loginwindow-min.png"));
    }//GEN-LAST:event_lblMinimazeMouseExited

    private void lblMinimazeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseEntered
        this.lblMinimaze.setIcon(Gambar.getIcon("ic-loginwindow-min-entered.png"));
    }//GEN-LAST:event_lblMinimazeMouseEntered

    private void lblMinimazeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimazeMouseClicked
        this.setExtendedState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinimazeMouseClicked

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        this.lblClose.setIcon(Gambar.getIcon("ic-loginwindow-close.png"));
    }//GEN-LAST:event_lblCloseMouseExited

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        this.lblClose.setIcon(Gambar.getIcon("ic-loginwindow-close-entered.png"));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
//        Application.closeApplication();
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked
 
    private void lblKembaliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseExited
        this.lblKembali.setIcon(Gambar.getIcon("ic-loginwindow-kembali.png"));
    }//GEN-LAST:event_lblKembaliMouseExited

    private void lblKembaliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseEntered
        this.lblKembali.setIcon(Gambar.getIcon("ic-loginwindow-kembali-entered.png"));
    }//GEN-LAST:event_lblKembaliMouseEntered

    private void lblKembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                ChooseLoginType tipe = new ChooseLoginType();
                tipe.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_lblKembaliMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        Log.addLog("Membuka Window " + getClass().getName());
    }//GEN-LAST:event_formWindowOpened

    private void lblLogoAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoAppMouseClicked
//        java.awt.EventQueue.invokeLater(new Runnable(){
//            @Override
//            public void run(){
//                user = new User();
//                user.login("dev2003", "lastidea");
//                new Dashboard().setVisible(true);
//                user.closeConnection();
//            }
//        });
//        this.setVisible(false);
    }//GEN-LAST:event_lblLogoAppMouseClicked

    private void lblCopyrightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseClicked
//        if(this.isLaliPass){
//            LupaPassword g = new LupaPassword(this, true, this.inpUsername.getText());
//            
//            // cek apakah username exist
//            if(this.user.isExistUsername(this.inpUsername.getText()) || this.user.isIdKaryawan(this.inpUsername.getText())){
//                // membuka window
//                g.setVisible(true);                
//            }else{
//                g.dispose();
//                Message.showWarning(this, "Username/Id Karyawan tersebut tidak ditemukan!");
//            }
//            
//            // jika password berhasil diganti
//            if(g.isSuccess()){
//                Message.showInformation(this, "Silahkan masukan username dan password baru Anda!");
//                // reset textfield
//                this.inpUsername.requestFocus();
//                this.inpPassword.setText("");
//                this.inpUsername.setText("");
//            }
//        }
    }//GEN-LAST:event_lblCopyrightMouseClicked

    private void lblCopyrightMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseEntered
        if(isLaliPass){
            this.lblCopyright.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.lblCopyright.setText("<html><p style=\"text-decoration:underline; color:rgb(255,0,0);\">Lupa Password?</p></html>");
        }
    }//GEN-LAST:event_lblCopyrightMouseEntered

    private void lblCopyrightMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseExited
        if(isLaliPass){
            this.lblCopyright.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            this.lblCopyright.setText("<html><p style=\"text-decoration:none; color:rgb(255,0,0);\">Lupa Password?</p></html>");
        }
    }//GEN-LAST:event_lblCopyrightMouseExited

    private void inpStatusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.cekId();
        }
    }//GEN-LAST:event_inpStatusKeyReleased

    private void lblSalahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalahMouseClicked
        this.inpStatus.setText("KY001\n");
        
//        if(this.user.isIdKaryawan(this.inpUsername.getText())){
//            Message.showInformation(this, "ID exist");
//        }
    }//GEN-LAST:event_lblSalahMouseClicked

    private void lblBenarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBenarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblBenarMouseClicked

    private void inpStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.cekId();
        }
    }//GEN-LAST:event_inpStatusKeyPressed

    private void inpStatusKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusKeyTyped
//        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
//            this.cekId();
//        }
    }//GEN-LAST:event_inpStatusKeyTyped

    public void cekId(){
        if(this.user.isIdKaryawan(this.inpStatus.getText())){
            Message.showInformation(this, "ID is exist");
        }else{
            Message.showInformation(this, "ID is not exist");
        }
    }
    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindowRFID.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new LoginWindowRFID().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inpStatus;
    private javax.swing.JLabel lblAnimStatus;
    private javax.swing.JLabel lblBenar;
    private javax.swing.JLabel lblBgImage;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblDesApp;
    private javax.swing.JLabel lblIconKartu;
    private javax.swing.JLabel lblKembali;
    private javax.swing.JLabel lblLogoApp;
    private javax.swing.JLabel lblMinimaze;
    private javax.swing.JLabel lblNamaApp;
    private javax.swing.JLabel lblSalah;
    private javax.swing.JLabel lblTop;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
