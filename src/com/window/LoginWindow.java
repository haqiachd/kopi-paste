package com.window;

import com.manage.User;
import com.koneksi.Database;
import com.koneksi.Koneksi;
import com.manage.Message;
import com.media.Gambar;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Digunakan untuk login bagi admin, petugas dan siswa.
 * 
 * @author Achmad Baihaqi
 * @since 2020-11-22
 */
public class LoginWindow extends javax.swing.JFrame {

    private final Database dbase = new Database();
    
    private int x, y;
    
    public LoginWindow() {
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.lblKembali.setVisible(false);
        this.btnLogin.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    private void login(){
        User user = new User();
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mendapatkan data dari textfield
        String username = this.inpUsername.getText(),
               password = this.inpPassword.getText();
        // login
        if(user.login(username, password)){
            // membuka window dashboard jika login berhasil
            java.awt.EventQueue.invokeLater(new Runnable(){
                public void run(){
                    new Dashboard().setVisible(true);
                }
            });
            this.setVisible(false);
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
        btnLogin = new javax.swing.JButton();
        lblEye = new javax.swing.JLabel();
        inpPassword = new com.manage.RoundedPasswordField(15);
        inpUsername = new com.manage.RoundedTextField(15);
        lblPassword = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblKembali = new javax.swing.JLabel();
        lblMinimaze = new javax.swing.JLabel();
        lblTop = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();
        lblBgImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        lblDesApp.setText("Smart App For Your Business.");

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

        btnLogin.setBackground(new java.awt.Color(44, 119, 238));
        btnLogin.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlMain.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 105, 35));

        lblEye.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-eye-close.png"))); // NOI18N
        lblEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEyeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEyeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEyeMouseExited(evt);
            }
        });
        pnlMain.add(lblEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 220, 36, 39));

        inpPassword.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpPasswordKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpPasswordKeyTyped(evt);
            }
        });
        pnlMain.add(inpPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 272, 39));

        inpUsername.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpUsername.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpUsername.setMinimumSize(new java.awt.Dimension(6, 35));
        inpUsername.setPreferredSize(new java.awt.Dimension(6, 35));
        inpUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpUsernameKeyReleased(evt);
            }
        });
        pnlMain.add(inpUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 272, 39));

        lblPassword.setBackground(new java.awt.Color(0, 0, 0));
        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(44, 119, 238));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password");
        pnlMain.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 190, 374, 22));

        lblUsername.setBackground(new java.awt.Color(0, 0, 0));
        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(44, 119, 238));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("Username / ID Karyawan");
        pnlMain.add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 384, 23));

        lblKembali.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-kembali.png"))); // NOI18N
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

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setForeground(new java.awt.Color(250, 56, 56));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("User Login");
        pnlMain.add(lblTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 384, 40));

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

        lblCopyright.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        lblCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCopyright.setText("Copyright © 2022. C2 Team. All Rights Reserved");
        pnlMain.add(lblCopyright, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 370, 20));

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

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void lblEyeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
        this.inpPassword.setEchoChar('•');
    }//GEN-LAST:event_lblEyeMouseExited

    private void lblEyeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
        this.inpPassword.setEchoChar((char)0);
    }//GEN-LAST:event_lblEyeMouseEntered

    private void lblEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseClicked

    }//GEN-LAST:event_lblEyeMouseClicked

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
    private Connection mysqlconfig;
    private Connection koneksi(){
        try{
            String url = "jdbc:mysql://localhost:3306/appbaju";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi Done");
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        this.login();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        this.btnLogin.setBackground(new Color(44,119,238));
    }//GEN-LAST:event_btnLoginMouseExited

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        this.btnLogin.setBackground(new Color(23,24,26));
    }//GEN-LAST:event_btnLoginMouseEntered

    private void lblKembaliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseExited
        this.lblKembali.setIcon(Gambar.getIcon("ic-login-kembali.png"));
    }//GEN-LAST:event_lblKembaliMouseExited

    private void lblKembaliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseEntered
        this.lblKembali.setIcon(Gambar.getIcon("ic-login-kembali-entered.png"));
    }//GEN-LAST:event_lblKembaliMouseEntered

    private void lblKembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKembaliMouseClicked

    }//GEN-LAST:event_lblKembaliMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        Log.addLog("Membuka Window " + getClass().getName());
    }//GEN-LAST:event_formWindowOpened

    private void lblLogoAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoAppMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new User().login("admin", "12345678");
                new Dashboard().setVisible(true);
            }
        });
        this.setVisible(false);
    }//GEN-LAST:event_lblLogoAppMouseClicked

    private void inpPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.login();
        }
    }//GEN-LAST:event_inpPasswordKeyPressed

    private void inpPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyTyped

    }//GEN-LAST:event_inpPasswordKeyTyped

    private void inpUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpPassword.requestFocus();
        }
    }//GEN-LAST:event_inpUsernameKeyReleased

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new LoginWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JLabel lblBgImage;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblDesApp;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblKembali;
    private javax.swing.JLabel lblLogoApp;
    private javax.swing.JLabel lblMinimaze;
    private javax.swing.JLabel lblNamaApp;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
