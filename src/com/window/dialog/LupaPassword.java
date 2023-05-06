package com.window.dialog;

import com.manage.Message;
import com.manage.User;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Achmad Baihaqi
 */
public class LupaPassword extends javax.swing.JDialog {
    
    private final User us = new User();
    
    private final JFrame frame;
    
    private final String usernameOrId, email;
    
    private boolean isVerif = false, isSuccess = false;

    /**
     * Creates new form GantiPassword
     * @param parent
     * @param frame
     * @param modal
     * @param usernameOrId
     */
    public LupaPassword(java.awt.Frame parent, JFrame frame, boolean modal, String usernameOrId) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.frame = frame;
        // mendapatkan dan menampilkan username dan email
        this.usernameOrId = usernameOrId;
        this.inpUsername.setText(this.usernameOrId);
        this.email = this.getEmail(this.usernameOrId);
        
        // set ui 
        this.btnKirim.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.lblIconEmail.setIcon(null);
        this.inpEmail.requestFocus();
    }
    
    /**
     * Digunakan untuk mengecek apakah email valid atau tidak
     * @param email
     * @return 
     */
    private boolean isValidEmail(String email) {
        return email.length() >= 10 && email.length() <= 60
                && email.contains("@") && email.contains(".")
                && email.charAt(email.length() - 1) != '.';
    }
    
    private void validasi(String email){
        if(email.equals("")){
            // reset verifikasi ke null
            this.inpEmail.setText("");
            this.lblStatusEmail.setForeground(new Color(0,0,0));
            this.lblStatusEmail.setText("Masukan email sesuai dengan username");
            this.lblIconEmail.setIcon(null);
        }else if(!this.isValidEmail(email)){
            // setting verifikasi tidak cocok
            this.lblStatusEmail.setForeground(new Color(204,0,204));
            this.lblStatusEmail.setText("Email yang dimasukan tidak valid");
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));
        }else if(!email.equalsIgnoreCase(this.email)){
            // setting verifikasi tidak cocoko
            this.lblStatusEmail.setForeground(new Color(255,51,0));
            this.lblStatusEmail.setText("Email yang dimasukan tidak cocok");
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));
        }else{
            this.isVerif = true;
            this.lblStatusEmail.setForeground(new Color(0,153,0));
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));
            this.lblStatusEmail.setText("Email valid, Sekarang Anda dapat mengirimkan kode verifikasi");
            
            this.inpEmail.setEditable(false);
            this.btnKirim.requestFocus();
        }
    }
    
    private String getEmail(String usernameOrId){
        try{
            String sql = "SELECT da.email " +
                         "FROM akun AS a " +
                         "INNER JOIN detail_akun AS da " +
                         "ON a.id_akun = da.id_akun  " +
                         "WHERE a.username = '"+usernameOrId+"' OR da.id_akun = '"+usernameOrId+"'";
            
            this.us.res = this.us.stat.executeQuery(sql);
            
            if(this.us.res.next()){
                return this.us.res.getString("da.email");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
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
        lblStatusEmail = new javax.swing.JLabel();
        lineBottom = new javax.swing.JSeparator();
        btnKirim = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblIconEmail = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        inpUsername = new com.ui.RoundedTextField(15);
        inpEmail = new com.ui.RoundedTextField(15);

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
        lblTop.setText("Lupa Password");

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        lblStatusEmail.setBackground(new java.awt.Color(0, 0, 0));
        lblStatusEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusEmail.setText("Masukan email sesuai dengan username");

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        btnKirim.setBackground(new java.awt.Color(44, 119, 238));
        btnKirim.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnKirim.setForeground(new java.awt.Color(255, 255, 255));
        btnKirim.setText("Kirim Kode");
        btnKirim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKirimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKirimMouseExited(evt);
            }
        });
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
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

        lblUsername.setBackground(new java.awt.Color(0, 0, 0));
        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(44, 119, 238));
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setText("Username / ID Karyawan");

        lblIconEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-verifikasi-success.png"))); // NOI18N
        lblIconEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconEmailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconEmailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconEmailMouseExited(evt);
            }
        });

        lblEmail.setBackground(new java.awt.Color(0, 0, 0));
        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(44, 119, 238));
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setText("Alamat Email");

        inpUsername.setEditable(false);
        inpUsername.setBackground(new java.awt.Color(231, 235, 239));
        inpUsername.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpUsername.setText("haqiachd");

        inpEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpEmailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpEmailKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineTop)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(inpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblIconEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10))
                    .addComponent(lineBottom, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnKirim)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIconEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnKirim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
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

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        if(!this.isVerif){
            Message.showWarning(this, "Masukan email yang valid terlebih dahulu!");
        }else{
            this.dispose();
            this.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            // membuka window ganti password
            GantiPassword g = new GantiPassword(null, true, this.usernameOrId, this.email);
            g.setVisible(true);
            if(g.isSuccess()){
                this.isSuccess = true;
                g.setVisible(false);
            }
            this.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
    }//GEN-LAST:event_btnKirimActionPerformed

    private void btnKirimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKirimMouseEntered
        
    }//GEN-LAST:event_btnKirimMouseEntered

    private void btnKirimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKirimMouseExited
        
    }//GEN-LAST:event_btnKirimMouseExited

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
        
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
        
    }//GEN-LAST:event_btnBatalMouseExited

    private void inpEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyPressed

    }//GEN-LAST:event_inpEmailKeyPressed

    private void inpEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyReleased
        this.validasi(this.inpEmail.getText());
    }//GEN-LAST:event_inpEmailKeyReleased

    private void inpEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyTyped
        this.validasi(this.inpEmail.getText());
    }//GEN-LAST:event_inpEmailKeyTyped

    private void lblIconEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconEmailMouseClicked
        
    }//GEN-LAST:event_lblIconEmailMouseClicked

    private void lblIconEmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconEmailMouseEntered
        if(this.inpEmail.getText().length() <= 0){
            this.lblIconEmail.setIcon(null);
            return;
        }
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-gagal-entered.png"));    
        }else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));    
        }
    }//GEN-LAST:event_lblIconEmailMouseEntered

    private void lblIconEmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconEmailMouseExited
        if(this.inpEmail.getText().length() <= 0){
            this.lblIconEmail.setIcon(null);
            return;
        }
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));    
        }else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconEmail.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));    
        }
    }//GEN-LAST:event_lblIconEmailMouseExited

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
            java.util.logging.Logger.getLogger(LupaPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LupaPassword dialog = new LupaPassword(new javax.swing.JFrame(), null, true, "dev2003");
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
    private javax.swing.JButton btnKirim;
    private javax.swing.JTextField inpEmail;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblIconEmail;
    private javax.swing.JLabel lblStatusEmail;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    // End of variables declaration//GEN-END:variables
}
