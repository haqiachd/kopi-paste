package com.window;

import com.manage.User;
import com.media.Gambar;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this.inpCode.requestFocus();
    }
    
    private void login(){
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        // mendapatkan data dari textfield
        String rfid = this.inpCode.getText();
        
        if(rfid.length() >= 10){
            // mendapatkan 10 digit code rfid
            rfid = rfid.substring(rfid.length()-10);
            this.inpCode.setText("");
        }else{
            // jika panjang dari rfid < 10 maka kode tidak valid
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            return;
        }
        
        // login
        if(this.user.loginRFID(rfid)){
            // set textfield status rfid
            this.inpStatusRfid.setText(User.getNamaUser());
            this.inpStatusRfid.setForeground(new Color(0, 153, 0));
            this.lblIconKartu.setIcon(Gambar.getIcon("gif-login-rfid-success.gif"));

            // membuka window dashboard
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // slepp untuk animasi
                        Thread.sleep(5100);
                        // membuka window dashboard
                        new Dashboard().setVisible(true);
                        user.closeConnection();
                        dispose();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LoginWindowRFID.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }else{
            // aksi jika kartu tidak terdaftar
            this.inpStatusRfid.setForeground(new Color(255,0,0));
            this.inpStatusRfid.setText("Kartu Anda Tidak Terdaftar!");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
        inpStatusRfid = new com.ui.RoundedTextField(15);
        lblIconKartu = new javax.swing.JLabel();
        lblTop = new javax.swing.JLabel();
        lblAnimStatus = new javax.swing.JLabel();
        lblBenar = new javax.swing.JLabel();
        lblSalah = new javax.swing.JLabel();
        lblBgImage = new javax.swing.JLabel();
        inpCode = new com.ui.RoundedTextField(15);

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

        inpStatusRfid.setEditable(false);
        inpStatusRfid.setBackground(new java.awt.Color(255, 255, 255));
        inpStatusRfid.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpStatusRfid.setForeground(new java.awt.Color(44, 119, 238));
        inpStatusRfid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpStatusRfid.setText("Tempelkan Kartu Anda ke RFID.");
        inpStatusRfid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpStatusRfid.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpStatusRfid.setMinimumSize(new java.awt.Dimension(6, 35));
        inpStatusRfid.setPreferredSize(new java.awt.Dimension(6, 35));
        inpStatusRfid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpStatusRfidActionPerformed(evt);
            }
        });
        inpStatusRfid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpStatusRfidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpStatusRfidKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpStatusRfidKeyTyped(evt);
            }
        });
        pnlMain.add(inpStatusRfid, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 272, 39));

        lblIconKartu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconKartu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/gif-login-rfid.gif"))); // NOI18N
        pnlMain.add(lblIconKartu, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 80, 390, 190));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setForeground(new java.awt.Color(250, 56, 56));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Login RFID");
        pnlMain.add(lblTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 390, 40));
        pnlMain.add(lblAnimStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 50, 40));

        lblBenar.setForeground(new java.awt.Color(255, 255, 255));
        lblBenar.setText("b");
        lblBenar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBenarMouseClicked(evt);
            }
        });
        pnlMain.add(lblBenar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 20, 40));

        lblSalah.setForeground(new java.awt.Color(255, 255, 255));
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

        inpCode.setBackground(new java.awt.Color(255, 255, 255));
        inpCode.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpCode.setForeground(new java.awt.Color(44, 119, 238));
        inpCode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpCode.setText("Tempelkan Kartu Anda ke RFID.");
        inpCode.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpCode.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpCode.setMinimumSize(new java.awt.Dimension(6, 35));
        inpCode.setPreferredSize(new java.awt.Dimension(6, 35));
        inpCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpCodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCodeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCodeKeyTyped(evt);
            }
        });
        pnlMain.add(inpCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 272, 39));

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
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                user.loginRFID("0008277089");
                new Dashboard().setVisible(true);
                user.closeConnection();
            }
        });
        this.setVisible(false);
    }//GEN-LAST:event_lblLogoAppMouseClicked

    private void lblCopyrightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseClicked

    }//GEN-LAST:event_lblCopyrightMouseClicked

    private void lblCopyrightMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseEntered

    }//GEN-LAST:event_lblCopyrightMouseEntered

    private void lblCopyrightMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCopyrightMouseExited

    }//GEN-LAST:event_lblCopyrightMouseExited

    private void inpStatusRfidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusRfidKeyReleased

    }//GEN-LAST:event_inpStatusRfidKeyReleased

    private void lblSalahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalahMouseClicked
        this.inpCode.setText(this.inpCode.getText()+"0008277089");
    }//GEN-LAST:event_lblSalahMouseClicked

    private void lblBenarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBenarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblBenarMouseClicked

    private void inpStatusRfidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusRfidKeyPressed

    }//GEN-LAST:event_inpStatusRfidKeyPressed

    private void inpStatusRfidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStatusRfidKeyTyped

    }//GEN-LAST:event_inpStatusRfidKeyTyped

    private void inpCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCodeKeyPressed

    }//GEN-LAST:event_inpCodeKeyPressed

    private void inpCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCodeKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.login();
        }
    }//GEN-LAST:event_inpCodeKeyReleased

    private void inpCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCodeKeyTyped

    }//GEN-LAST:event_inpCodeKeyTyped

    private void inpStatusRfidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpStatusRfidActionPerformed
        this.inpCode.requestFocus();
    }//GEN-LAST:event_inpStatusRfidActionPerformed
 
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
    private javax.swing.JTextField inpCode;
    private javax.swing.JTextField inpStatusRfid;
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
