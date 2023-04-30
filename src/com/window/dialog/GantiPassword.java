package com.window.dialog;

import com.manage.Message;
import com.manage.Text;
import com.manage.User;
import com.manage.Validation;
import com.manage.VerifikasiEmail;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JTextField;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Achmad Baihaqi
 */
public class GantiPassword extends javax.swing.JDialog {
    
    private final User us = new User();
    
    private final VerifikasiEmail ve = new VerifikasiEmail("");
    
    private final Text txt = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private final String email;
    
    private boolean isVerif = false;

    /**
     * Creates new form GantiPassword
     * @param parent
     * @param modal
     * @param email
     */
    public GantiPassword(java.awt.Frame parent, boolean modal, String email) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null); // password gmail
        
        // mendapatkan dan menampilkan email 
        this.email = email;
        this.ve.setEmail(this.email);
        this.inpEmail.setText(this.email);
        
        // set ui 
        this.btnGanti.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.lblIconVerifikasi.setIcon(null);
        this.inpVerifikasi.requestFocus();
        
        // mengaktifkan input verifikasi
        this.statusPassword();
        
        // set aksi saat textfied ditekan jika verifikasi masih salah
        for(JTextField field : new JTextField[]{this.inpPassword, this.inpKonfPass}){
            field.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!isVerif){
                        Message.showInformation(this, "Masukan kode verifikasi email terlebih dahulu!");
                        waktu.delay(100);
                    }
                }
            });
        }
        
        // cek apakah ada kode yang aktif atau tidak
        if(this.ve.isActiveKode()){
            // menampilkan limit waktu ke layar jika masih ada kode yang aktif
            this.showLimitWaktu();            
        }else{
            // mengirim kode ke email
            kirimKode();

        }
    }
    
    private void kirimKode(){
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // merandom kode dan mengirimkannya ke email
        this.ve.randomKode();
        System.out.println("KODE VERIFIKASI : "+this.ve.getKode());
        this.showLimitWaktu();
        // jika kode gagal dikirim lewat email
        if (!ve.isActiveKode()) {
            waktu.delay(1500);
            lblStatusEmail.setText("Kode gagal dikirim, Mohon cek koneksi internet Anda!");
        } else {
            Message.showInformation(this, "Kode verifikasi telah dikirim ke email Anda '" + email + "'");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private void showLimitWaktu(){
        // cek apakah verifikasi sudah berhasil atau belum
        if(this.isVerif){
            // jika verifikasi sudah berhasil maka akan langsung keluar dari method
            return;
        }
        
        // thread untuk menampilkan limit random kode ke status
         new Thread(new Runnable(){
             @Override
             public void run(){
                // mengatur warna teks status menjadi unggu
                lblStatusEmail.setForeground(new Color(204,0,204));
                // menampilkan limit random kode ke status
                 while(ve.getLRandom() > 0){
                     lblStatusEmail.setText("Kirim ulang kode verifikasi dalam "+ve.getLRandom()+" detik");
                     waktu.delay(10);
                     // berhenti jika user menginputkan kode verifikasi yang valid
                     if(isVerif){
                         break;
                     }
                 }
                 
                 // jika while sudah berhenti dan kode verifikasi tidak valid
                 if(!isVerif){
                     // atur pesan untuk mengirimkan ulang kode verifikasi
                     lblStatusEmail.setForeground(new Color(0,0,0));
                     lblStatusEmail.setText("Tekan tombol pesawat untuk mengirimkan ulang kode");                     
                 }else{
                     // atur pesan menjadi kosong jika verifikasi valid
                     lblStatusEmail.setText("");                     
                 }
             }
         }).start();
         
         // thread untuk menghitung apakah kode verifikasi masih aktif atau tidak
         new Thread(new Runnable(){
             @Override
             public void run(){
                 // melakukan perulangan sampai kode tidak aktif
                 while(ve.getLKode() > 0){
                     waktu.delay(10);
                     // berhenti jika user menginputkan kode verifikasi yang valid
                     if(isVerif){
                         break;
                     }
                 }
                 
                 // jika while sudah berhenti dan kode verifikasi tidak valid
                 if(!isVerif){
                     // atur pesan bahwa kode verifikasi telah kadaluarsa
                    lblStatusEmail.setForeground(new Color(255,51,0));
                    lblStatusEmail.setText("Kode kadaluarsa, Tekan pesawat untuk mengirimkan ulang!");
                 }else{
                     // atur pesan menjadi kosong jika verifikasi valid
                     lblStatusEmail.setText("");
                 }
             }
         }).start();
    }
    
    private void statusPassword(){
        if(this.isVerif){
            // set editable dan enable menjadi true pada password
            this.inpPassword.setEditable(true);
            this.inpKonfPass.setEditable(true);
            this.lblEye.setEnabled(true);
            this.lblEyeKonf.setEnabled(true);
            this.inpPassword.requestFocus();
            this.lblStatusPassword.setText("Password harus minimal 8 karakter");
            this.lblStatusKonfPass.setText("Ketikan ulang password anda");
            
            // set editable false pada verifikasi
            this.inpVerifikasi.setEditable(false);
            this.lblSendEmail.setEnabled(false);
            this.lblStatusEmail.setText("");
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));
        }else{
            // set editable dan enable menjadi false pada password
            this.inpPassword.setEditable(false);
            this.inpKonfPass.setEditable(false);
            this.lblEye.setEnabled(false);
            this.lblEyeKonf.setEnabled(false);
            this.lblStatusPassword.setText("");
            this.lblStatusKonfPass.setText("");
            
            // set editable true pada verifikasi
            this.inpVerifikasi.requestFocus(true);
            this.inpVerifikasi.setEditable(true);
            this.lblSendEmail.setEnabled(true);
            this.lblStatusEmail.setText("Kirim ulang kode verifikasi dalam 1 menit");
            // set icon verifikasi gagal
            if(this.inpVerifikasi.getText().length() >= 8){
                this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));
            }else{
                this.lblIconVerifikasi.setIcon(null);
            }
        }
    }
    
    private void validasiKode(KeyEvent evt, String inputKode){
        // set hanya angka pada textfield
        this.txt.decimalOnly(evt);
        // mendapatkan panjang dari kode
        int indexKode = inputKode.length(); 
        
        // jika panjang dari kode > 0 dan < 8
        if(indexKode > 0 && indexKode < 8){
            // setting verifikasi tidak cocok
            this.lblStatusVerifikasi.setForeground(new Color(255,51,0));
            this.lblStatusVerifikasi.setText("Kode verifikasi tidak cocok!");
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));
        }
        // jika panjang dari kode sama dengan 8
        else if(indexKode == 8){
            // jika kode tidak cocok
            if(!inputKode.equals(ve.getKode())){
                // setting verifikasi tidak cocoko
                this.lblStatusVerifikasi.setForeground(new Color(255,51,0));
                this.lblStatusVerifikasi.setText("Kode verifikasi tidak cocok!");
                this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));
            }
            // jika verifikasi cocok
            else{
                // reset kode
                this.ve.killKode();
                // setting verifikasi berhasil
                this.isVerif = true;
                this.lblStatusVerifikasi.setForeground(new Color(0,153,0));
                this.lblStatusVerifikasi.setText("Verifikasi berhasil!");
                this.statusPassword();
                Audio.play(Audio.SOUND_INFO);
            }
        }
        // jika panjang dari kode 0 atau lebih dari 8
        else {
            // reset verifikasi ke null
            this.inpVerifikasi.setText("");
            this.lblStatusVerifikasi.setForeground(new Color(0,0,0));
            this.lblStatusVerifikasi.setText("Masukan kode verifikasi");
            this.lblIconVerifikasi.setIcon(null);
        }
    }
    
    private void validasiPassword(String pass){
        // mendapatkan panjang dari password
        int indexPass = pass.length();
        
        if(indexPass <= 0){
            this.lblStatusPassword.setForeground(new Color(0,0,0));
            this.lblStatusPassword.setText("Masukan password baru");
        }else if(indexPass < 8){
            this.lblStatusPassword.setForeground(new Color(255,51,0));
            this.lblStatusPassword.setText("Password harus minimal 8 karakter");
        }else{
            this.lblStatusPassword.setForeground(new Color(0,153,0));
            this.lblStatusPassword.setText("Password valid");
        }
    }

    private void validasiKonfPassword(String pass){
        // mendapatkan panjang dari password
        int indexPass = pass.length();
        
        if(indexPass <= 0){
            this.lblStatusKonfPass.setForeground(new Color(0,0,0));
            this.lblStatusKonfPass.setText("Ketikan ulang password Anda");
        }else if(indexPass > 0){
            if(pass.equals(this.inpPassword.getText())){
                this.lblStatusKonfPass.setForeground(new Color(0,153,0));
                this.lblStatusKonfPass.setText("Konfirmasi password valid");
            }else{
                this.lblStatusKonfPass.setForeground(new Color(255,51,0));
                this.lblStatusKonfPass.setText("Konfimasi password tidak cocok");
            }
        }
    }
    
    private boolean gantiPassword(){
        try{
            // enkripsi password baru
            String hashPw = BCrypt.hashpw(this.inpPassword.getText(), BCrypt.gensalt(12)),
                    // membuat query untuk ganti password
                    sql = "UPDATE akun SET password = '"+hashPw+"' "
                        + "WHERE username = '"+User.getUsername()+"'";
            
            // eksekusi query
            return this.us.stat.executeUpdate(sql) > 0;
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        inpPassword = new com.ui.RoundedPasswordField(15);
        lblStatusVerifikasi = new javax.swing.JLabel();
        lblEye = new javax.swing.JLabel();
        lblStatusPassword = new javax.swing.JLabel();
        inpKonfPass = new com.ui.RoundedPasswordField(15);
        lblEyeKonf = new javax.swing.JLabel();
        lineBottom = new javax.swing.JSeparator();
        btnGanti = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        lblSendEmail = new javax.swing.JLabel();
        lblStatusEmail = new javax.swing.JLabel();
        lblIconVerifikasi = new javax.swing.JLabel();
        lblVerifikasi = new javax.swing.JLabel();
        inpEmail = new com.ui.RoundedTextField(15);
        lblPassword = new javax.swing.JLabel();
        inpVerifikasi = new com.ui.RoundedTextField(15);
        lineCenter = new javax.swing.JSeparator();
        lblKonfPass = new javax.swing.JLabel();
        lblStatusKonfPass = new javax.swing.JLabel();

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

        inpPassword.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpPassword.setName("Password Baru"); // NOI18N
        inpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpPasswordKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpPasswordKeyTyped(evt);
            }
        });

        lblStatusVerifikasi.setBackground(new java.awt.Color(0, 0, 0));
        lblStatusVerifikasi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusVerifikasi.setText("Masukan kode verifikasi");

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

        lblStatusPassword.setBackground(new java.awt.Color(0, 0, 0));
        lblStatusPassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusPassword.setText("Password harus minimal 8 karakter");

        inpKonfPass.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpKonfPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpKonfPass.setName("Konfirmasi Password"); // NOI18N
        inpKonfPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpKonfPassKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpKonfPassKeyReleased(evt);
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

        lblEmail.setBackground(new java.awt.Color(0, 0, 0));
        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(44, 119, 238));
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setText("Alamat Email");

        lblSendEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSendEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-verifikasi-send.png"))); // NOI18N
        lblSendEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSendEmailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSendEmailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSendEmailMouseExited(evt);
            }
        });

        lblStatusEmail.setBackground(new java.awt.Color(0, 0, 0));
        lblStatusEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusEmail.setForeground(new java.awt.Color(204, 0, 204));
        lblStatusEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusEmail.setText("Kirim ulang kode verifikasi dalam 1 menit");

        lblIconVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconVerifikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-verifikasi-success.png"))); // NOI18N
        lblIconVerifikasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIconVerifikasiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblIconVerifikasiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblIconVerifikasiMouseExited(evt);
            }
        });

        lblVerifikasi.setBackground(new java.awt.Color(0, 0, 0));
        lblVerifikasi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVerifikasi.setForeground(new java.awt.Color(44, 119, 238));
        lblVerifikasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVerifikasi.setText("Kode Verifikasi");

        inpEmail.setEditable(false);
        inpEmail.setBackground(new java.awt.Color(231, 235, 239));
        inpEmail.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpEmail.setText("hakiahmad756@gmail.com");

        lblPassword.setBackground(new java.awt.Color(0, 0, 0));
        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(44, 119, 238));
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassword.setText("Password Baru");

        inpVerifikasi.setFont(new java.awt.Font("Dialog", 1, 19)); // NOI18N
        inpVerifikasi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpVerifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpVerifikasiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpVerifikasiKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpVerifikasiKeyTyped(evt);
            }
        });

        lineCenter.setBackground(new java.awt.Color(0, 0, 0));
        lineCenter.setForeground(new java.awt.Color(0, 0, 0));

        lblKonfPass.setBackground(new java.awt.Color(0, 0, 0));
        lblKonfPass.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblKonfPass.setForeground(new java.awt.Color(44, 119, 238));
        lblKonfPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKonfPass.setText("Konfirmasi Password");

        lblStatusKonfPass.setBackground(new java.awt.Color(0, 0, 0));
        lblStatusKonfPass.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatusKonfPass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusKonfPass.setText("Ketikan ulang password anda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineTop)
                    .addComponent(lineCenter, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStatusKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(lblEye, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(inpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSendEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(inpVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblIconVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(inpKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8)
                                        .addComponent(lblEyeKonf, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10))
                    .addComponent(lineBottom, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGanti, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSendEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(inpEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIconVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusVerifikasi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(lineCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEye, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEyeKonf, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusKonfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGanti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
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

    private void btnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiActionPerformed
        if(!this.isVerif){
            Message.showWarning(this, "Masukan kode verifikasi email terlebih dahulu!");
            return;
        }
        
//         validasi data ganti password
        if(!Validation.isEmptyTextField(this.inpPassword, this.inpKonfPass)){
            return;
        }else if(!Validation.isPassword(this.inpPassword.getText())){
            return;
        }else if(!this.inpPassword.getText().equals(this.inpKonfPass.getText())){
            Message.showWarning(this, "Konfirmasi Password tidak cocok!");
            return;
        }
        
        // mengubah password
        if(this.gantiPassword()){
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

    private void inpVerifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpVerifikasiKeyPressed

    }//GEN-LAST:event_inpVerifikasiKeyPressed

    private void inpVerifikasiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpVerifikasiKeyReleased
        this.validasiKode(evt, this.inpVerifikasi.getText());
    }//GEN-LAST:event_inpVerifikasiKeyReleased

    private void inpVerifikasiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpVerifikasiKeyTyped
        this.validasiKode(evt, this.inpVerifikasi.getText());
    }//GEN-LAST:event_inpVerifikasiKeyTyped

    private void inpPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyPressed
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            this.inpKonfPass.requestFocus();
//        }
//        this.validasiPassword(this.inpPassword.getText());
    }//GEN-LAST:event_inpPasswordKeyPressed

    private void inpPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyReleased
        this.validasiPassword(this.inpPassword.getText());
    }//GEN-LAST:event_inpPasswordKeyReleased

    private void inpPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyTyped
        this.validasiPassword(this.inpPassword.getText());
    }//GEN-LAST:event_inpPasswordKeyTyped

    private void inpKonfPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKonfPassKeyPressed
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            btnGantiActionPerformed(null);
//        }
//        this.validasiKonfPassword(this.inpKonfPass.getText());
    }//GEN-LAST:event_inpKonfPassKeyPressed

    private void inpKonfPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKonfPassKeyReleased
        this.validasiKonfPassword(this.inpKonfPass.getText());
    }//GEN-LAST:event_inpKonfPassKeyReleased

    private void inpKonfPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKonfPassKeyTyped
        this.validasiKonfPassword(this.inpKonfPass.getText());
    }//GEN-LAST:event_inpKonfPassKeyTyped

    private void lblSendEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSendEmailMouseClicked
        if(this.isVerif){
            return;
        }
        
        if(this.ve.isCanRandom()){
            this.kirimKode();
        }else{
            Message.showWarning(this, "Tunggu beberapa saat lagi!");
        }
    }//GEN-LAST:event_lblSendEmailMouseClicked

    private void lblSendEmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSendEmailMouseEntered
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblSendEmail.setIcon(Gambar.getIcon("ic-verifikasi-send-entered.png"));            
        }
    }//GEN-LAST:event_lblSendEmailMouseEntered

    private void lblSendEmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSendEmailMouseExited
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblSendEmail.setIcon(Gambar.getIcon("ic-verifikasi-send.png"));            
        }
    }//GEN-LAST:event_lblSendEmailMouseExited

    private void lblIconVerifikasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconVerifikasiMouseClicked
        
    }//GEN-LAST:event_lblIconVerifikasiMouseClicked

    private void lblIconVerifikasiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconVerifikasiMouseEntered
        if(this.inpVerifikasi.getText().length() <= 0){
            this.lblIconVerifikasi.setIcon(null);
            return;
        }
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-gagal-entered.png"));    
        }else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));    
        }
    }//GEN-LAST:event_lblIconVerifikasiMouseEntered

    private void lblIconVerifikasiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIconVerifikasiMouseExited
        if(this.inpVerifikasi.getText().length() <= 0){
            this.lblIconVerifikasi.setIcon(null);
            return;
        }
        if(!this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-gagal.png"));    
        }else{
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblIconVerifikasi.setIcon(Gambar.getIcon("ic-verifikasi-success.png"));    
        }
    }//GEN-LAST:event_lblIconVerifikasiMouseExited

    private void lblEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseClicked

    }//GEN-LAST:event_lblEyeMouseClicked

    private void lblEyeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseEntered
        if(this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
            this.inpPassword.setEchoChar((char)0);
        }
    }//GEN-LAST:event_lblEyeMouseEntered

    private void lblEyeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseExited
        if(this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
            this.inpPassword.setEchoChar('•');
        }
    }//GEN-LAST:event_lblEyeMouseExited

    private void lblEyeKonfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseClicked
        
    }//GEN-LAST:event_lblEyeKonfMouseClicked

    private void lblEyeKonfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseEntered
        if(this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblEyeKonf.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
            this.inpKonfPass.setEchoChar((char)0); 
        }
    }//GEN-LAST:event_lblEyeKonfMouseEntered

    private void lblEyeKonfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeKonfMouseExited
        if(this.isVerif){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblEyeKonf.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
            this.inpKonfPass.setEchoChar('•');
        }
    }//GEN-LAST:event_lblEyeKonfMouseExited

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
    private javax.swing.JTextField inpEmail;
    private javax.swing.JPasswordField inpKonfPass;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpVerifikasi;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblEyeKonf;
    private javax.swing.JLabel lblIconVerifikasi;
    private javax.swing.JLabel lblKonfPass;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSendEmail;
    private javax.swing.JLabel lblStatusEmail;
    private javax.swing.JLabel lblStatusKonfPass;
    private javax.swing.JLabel lblStatusPassword;
    private javax.swing.JLabel lblStatusVerifikasi;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblVerifikasi;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineCenter;
    private javax.swing.JSeparator lineTop;
    // End of variables declaration//GEN-END:variables
}
