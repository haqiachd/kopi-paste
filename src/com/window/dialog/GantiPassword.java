package com.window.dialog;

import com.manage.Message;
import com.manage.Text;
import com.manage.User;
import com.manage.Validation;
import com.media.Gambar;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Achmad Baihaqi
 */
public class GantiPassword extends javax.swing.JDialog {
    
    private final User us = new User();
    
    private final Text txt = new Text();
    
    private final String usernameOrId;
    
    private boolean isSuccess = false;

    /**
     * Creates new form GantiPassword
     * @param parent
     * @param modal
     * @param username
     */
    public GantiPassword(java.awt.Frame parent, boolean modal, String username) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        // mendapatkan dan menampilkan username 
        this.usernameOrId = username;
        this.inpUsername.setText(username);
        
        // set ui button
        this.btnGanti.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    private boolean isValidTelephone(){
        try{
            // membuat query sql
            String sql = "SELECT k.no_telp "
                    + "FROM karyawan AS k "
                    + "JOIN user AS u "
                    + "ON k.id_karyawan = u.id_karyawan "
                    + "WHERE u.username = '"+this.usernameOrId+"' OR u.id_karyawan = '"+this.usernameOrId+"'";
            
            // eksekusi query sql
            this.us.res = this.us.stat.executeQuery(sql);
            
            if(this.us.res.next()){
                // mengecek apakah no telepone valid atau tidak
                return this.inpNoTelp.getText().equals(this.us.res.getString(1));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private boolean gantiPassword(){
        try{
            // enkripsi password baru
            String hashPw = BCrypt.hashpw(this.inpPassword.getText(), BCrypt.gensalt(12)),
                    // membuat query untuk ganti password
                    sql = "UPDATE user SET password = '"+hashPw+"' "
                        + "WHERE username = '"+this.usernameOrId+"' OR id_karyawan = '"+this.usernameOrId+"'";
            
            // eksekusi query
            return this.us.stat.executeUpdate(sql) > 0;
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return false;
    }

    public boolean isSuccess(){
        return this.isSuccess;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        inpUsername = new com.ui.RoundedTextField(15);
        lblUsername = new javax.swing.JLabel();
        inpNoTelp = new com.ui.RoundedTextField(15);
        lblNoTelp = new javax.swing.JLabel();
        inpPassword = new com.ui.RoundedPasswordField(15);
        lblPassword = new javax.swing.JLabel();
        lblEye = new javax.swing.JLabel();
        lblKonfPass = new javax.swing.JLabel();
        inpKonfPass = new com.ui.RoundedPasswordField(15);
        lblEyeKonf = new javax.swing.JLabel();
        lineBottom = new javax.swing.JSeparator();
        btnGanti = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ganti Password");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setForeground(new java.awt.Color(250, 56, 56));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Ganti Password");

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        inpUsername.setBackground(new java.awt.Color(231, 235, 239));
        inpUsername.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpUsername.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpUsername.setEnabled(false);
        inpUsername.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpUsername.setMinimumSize(new java.awt.Dimension(6, 35));
        inpUsername.setName("Username"); // NOI18N
        inpUsername.setPreferredSize(new java.awt.Dimension(6, 35));
        inpUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpUsernameKeyReleased(evt);
            }
        });

        lblUsername.setBackground(new java.awt.Color(0, 0, 0));
        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(44, 119, 238));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("Username / ID Karyawan");

        inpNoTelp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpNoTelp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpNoTelp.setMaximumSize(new java.awt.Dimension(2147483647, 35));
        inpNoTelp.setMinimumSize(new java.awt.Dimension(6, 35));
        inpNoTelp.setName("Nomor Telephone"); // NOI18N
        inpNoTelp.setPreferredSize(new java.awt.Dimension(6, 35));
        inpNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpNoTelpKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpNoTelpKeyTyped(evt);
            }
        });

        lblNoTelp.setBackground(new java.awt.Color(0, 0, 0));
        lblNoTelp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNoTelp.setForeground(new java.awt.Color(44, 119, 238));
        lblNoTelp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNoTelp.setText("Nomor Telephone");

        inpPassword.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpPassword.setName("Password Baru"); // NOI18N
        inpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpPasswordKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpPasswordKeyTyped(evt);
            }
        });

        lblPassword.setBackground(new java.awt.Color(0, 0, 0));
        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(44, 119, 238));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password Baru");

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

        lblKonfPass.setBackground(new java.awt.Color(0, 0, 0));
        lblKonfPass.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblKonfPass.setForeground(new java.awt.Color(44, 119, 238));
        lblKonfPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKonfPass.setText("Konfirmasi Password");

        inpKonfPass.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpKonfPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpKonfPass.setName("Konfirmasi Password"); // NOI18N
        inpKonfPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpKonfPassKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpKonfPassKeyTyped(evt);
            }
        });

        lblEyeKonf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEyeKonf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-eye-close.png"))); // NOI18N
        lblEyeKonf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEyeKonfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEyeKonfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEyeKonfMouseExited(evt);
            }
        });

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        btnGanti.setBackground(new java.awt.Color(44, 119, 238));
        btnGanti.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnGanti.setForeground(new java.awt.Color(255, 255, 255));
        btnGanti.setText("Ganti");
        btnGanti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGantiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGantiMouseExited(evt);
            }
        });
        btnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 51, 51));
        btnBatal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBatalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBatalMouseExited(evt);
            }
        });
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lineTop)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(inpNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(8, 8, 8)
                                            .addComponent(lblEye, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(inpKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(8, 8, 8)
                                            .addComponent(lblEyeKonf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(lblKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lineBottom))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(inpNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEye, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEyeKonf, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnGanti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inpUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpNoTelp.requestFocus();
        }
    }//GEN-LAST:event_inpUsernameKeyReleased

    private void inpNoTelpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNoTelpKeyReleased
          if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpPassword.requestFocus();
        }
    }//GEN-LAST:event_inpNoTelpKeyReleased

    private void inpPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpKonfPass.requestFocus();
        }
    }//GEN-LAST:event_inpPasswordKeyPressed

    private void inpPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyTyped

    }//GEN-LAST:event_inpPasswordKeyTyped

    private void lblEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseClicked

    }//GEN-LAST:event_lblEyeMouseClicked

    private void lblEyeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
        this.inpPassword.setEchoChar((char)0);
    }//GEN-LAST:event_lblEyeMouseEntered

    private void lblEyeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
        this.inpPassword.setEchoChar('•');
    }//GEN-LAST:event_lblEyeMouseExited

    private void inpKonfPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKonfPassKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGantiActionPerformed(null);
        }
    }//GEN-LAST:event_inpKonfPassKeyPressed

    private void inpKonfPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKonfPassKeyTyped
        
    }//GEN-LAST:event_inpKonfPassKeyTyped

    private void lblEyeKonfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseClicked
        
    }//GEN-LAST:event_lblEyeKonfMouseClicked

    private void lblEyeKonfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseEntered
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.lblEyeKonf.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
        this.inpKonfPass.setEchoChar((char)0);
    }//GEN-LAST:event_lblEyeKonfMouseEntered

    private void lblEyeKonfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        this.lblEyeKonf.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
        this.inpKonfPass.setEchoChar('•');
    }//GEN-LAST:event_lblEyeKonfMouseExited

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        // validasi data ganti password
        if(!Validation.isEmptyTextField(this.inpUsername, this.inpNoTelp, this.inpPassword, this.inpKonfPass)){
            return;
        }else if(!this.isValidTelephone()){
            Message.showWarning(this, "Nomor Telephone tidak cocok!");
            return;
        }else if(!Validation.isPassword(this.inpPassword.getText())){
            return;
        }else if(!this.inpPassword.getText().equals(this.inpKonfPass.getText())){
            JOptionPane.showMessageDialog(this, "Konfirmasi Password tidak cocok!");
            return;
        }
        
        // mengubah password
        if(this.gantiPassword()){
            // jika password berhasil diubah
            isSuccess = true;
            Message.showInformation(this, "Password berhasil diganti!");
            this.setVisible(false);
        }else{
            Message.showWarning(this, "Password gagal diganti!!");
        }
    }//GEN-LAST:event_btnGantiActionPerformed

    private void btnGantiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGantiMouseEntered
        
    }//GEN-LAST:event_btnGantiMouseEntered

    private void btnGantiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGantiMouseExited
        
    }//GEN-LAST:event_btnGantiMouseExited

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
        
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
        
    }//GEN-LAST:event_btnBatalMouseExited

    private void inpNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNoTelpKeyTyped
        txt.filterAngka(evt);
        txt.filterChar(evt);
    }//GEN-LAST:event_inpNoTelpKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(GantiPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GantiPassword dialog = new GantiPassword(new javax.swing.JFrame(), true, "admin");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnGanti;
    private javax.swing.JPasswordField inpKonfPass;
    private javax.swing.JTextField inpNoTelp;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblEyeKonf;
    private javax.swing.JLabel lblKonfPass;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    // End of variables declaration//GEN-END:variables
}
