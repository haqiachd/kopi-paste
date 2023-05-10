package com.window.dialog;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.User;
import com.manage.Validation;
import com.media.Audio;
import com.media.Gambar;
import com.manage.VerifikasiEmail;
import com.window.ChooseLoginType;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class EditAkun extends javax.swing.JDialog {

    private final Database db = new Database();
    
    private final User us = new User();
    
    private final PopUpBackground pop = new PopUpBackground();
    
    private final VerifikasiEmail ve = new VerifikasiEmail();
    
    private final JFrame frame;
    
    private final String email;
    
    private boolean isVerif;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param frame
     */
    public EditAkun(java.awt.Frame parent, boolean modal, JFrame frame) {
        super(parent, modal);
        this.pop.setVisible(true);

        initComponents();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        this.frame = frame;
        
        this.inpIdAkun.setText(User.getIdAkun());
        
        this.btnLogoutAccount.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnGantiPass.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.inpIdAkun.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpUsername.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpEmail.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNoHp.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpAlamat.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        this.showDataAkun();
        this.email = this.inpEmail.getText();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private void showDataAkun(){
        try {
            // membuat query
            String sql = "SELECT * FROM detail_akun WHERE id_akun = '"+User.getIdAkun()+"'";
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                this.inpUsername.setText(User.getUsername());
                this.inpNama.setText(User.getNamaUser());
                this.inpNoHp.setText(this.db.res.getString("no_telp"));
                this.inpEmail.setText(this.db.res.getString("email"));
                this.inpAlamat.setText(this.db.res.getString("alamat"));
            }
        } catch (SQLException ex) {
            Message.showException(this, ex);
        }
    }
    
    /**
     * validasi username untuk key event 
     * 
     * @param evt 
     */
    private void validasiUsername(KeyEvent evt) {
        // mendapatkan data dari textfield
        String username = this.inpUsername.getText();
        
        if(username.equalsIgnoreCase(User.getUsername())){
            return;
        }
        
        // jika menekan enter
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.lblStatusUsername.setText("Username valid");
            this.lblStatusUsername.setForeground(new Color(0, 0, 255));
            this.inpNama.requestFocus();
        }else {
            // username minimal 5 karakter
            if(username.length() <= 4) {
                this.lblStatusUsername.setText("Min 5 Huruf");
                this.lblStatusUsername.setForeground(new Color(255, 0, 0));
            }
            // username maximal 20 karakter
            else if (username.length() > 20) {
                this.lblStatusUsername.setText("Max 20 Huruf");
                this.lblStatusUsername.setForeground(new Color(255, 0, 0));
            }
            // cek apakah usrename sudah digunakan atau belum
            else if (this.us.isExistUsername(this.inpUsername.getText())) {
                this.lblStatusUsername.setText("Sudah terpakai!");
                this.lblStatusUsername.setForeground(new Color(255, 0, 0));
            }
            // jika username valid
            else {
                this.lblStatusUsername.setText("Username valid");
                this.lblStatusUsername.setForeground(new Color(0,153,0));
            }
        }
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
    
    private void validasi(String emailInp){
        
        if(emailInp.equalsIgnoreCase(this.email)){
            this.isVerif = true;
            this.lblStatusEmail.setForeground(new Color(0,153,0));
            this.lblStatusEmail.setText("Email valid");
            return;
        }
        
        if(emailInp.equals("")){
            // reset verifikasi ke null
            this.inpEmail.setText("");
            this.lblStatusEmail.setForeground(new Color(0,0,0));
            this.lblStatusEmail.setText("Masukan email Anda");
        }else if(!this.isValidEmail(emailInp)){
            // setting verifikasi tidak cocok
            this.lblStatusEmail.setForeground(new Color(255, 0, 0));
            this.lblStatusEmail.setText("Email yang dimasukan tidak valid");
        }else{
            this.isVerif = true;
            this.lblStatusEmail.setForeground(new Color(0,153,0));
            this.lblStatusEmail.setText("Email valid");
        }
    }
    
    private void editData(){
        // cek validasi username
        if(!Validation.isEmptyTextField(this.inpUsername)){
            return;
        }else if(!User.getUsername().equalsIgnoreCase(this.inpUsername.getText())){
            if(this.us.isExistUsername(this.inpUsername.getText())){
                Message.showWarning(this, "Username tersebut sudah digunakan");
                return;
            }
        }
        
        // cek validasi nama, no hp, email dan alamat
        else if(!Validation.isEmptyTextField(this.inpNama, this.inpNoHp, this.inpEmail)){
            return;
        }else if(!Validation.isNamaOrang(this.inpNama.getText())){
            return;
        }else if(!Validation.isNoHp(this.inpNoHp.getText())){
            return;
        }
        else if(!Validation.isEmail(this.inpEmail.getText())){
            return;
        }else if(!this.email.equalsIgnoreCase(this.inpEmail.getText())){
            if(this.us.isExisEmail(this.inpEmail.getText())){
                Message.showWarning(this, "Email tersebut sudah terpakai!");
                return;
            }
        }
        else if(this.inpAlamat.getText().isEmpty()){
            Message.showWarning(this, "Alamat tidak boleh kosong");
            return;
        }else if(this.inpAlamat.getText().length() < 4 || this.inpAlamat.getTabSize() > 50){
            Message.showWarning(this, "Panjang dari Nama Tempat harus diantara 4-50 karakter!");
            return;
        }
        
        try{
            // mengedit data akun dan detail akun ke database
            boolean editAkun = this.editDataAkun(),
                    editDetail = this.editDataDetailAkun();
            System.out.println("EDIT AKUN : " + editAkun);
            System.out.println("DETAL AKUN : " + editDetail);
            
            if(editAkun && editDetail){
                if(User.getUsername().equalsIgnoreCase(this.inpUsername.getText()) && User.getNamaUser().equalsIgnoreCase(this.inpNama.getText())){
                    Message.showInformation(this, "Data karyawan berhasil diedit!");
                    this.dispose();
                }else{
                    Message.showInformation(this, "Anda telah mengedit Username / Nama Lengkap,\nSilahkan login lagi!");
                    this.dispose();
                    this.frame.dispose();
                    java.awt.EventQueue.invokeLater(new Runnable(){
                        @Override
                        public void run(){
                            ChooseLoginType login = new ChooseLoginType();
                            login.setVisible(true);
                        }
                    });
                }
            }else{
                Message.showWarning(this, "Data karyawan gagal diedit!");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private boolean editDataAkun() throws SQLException{
        // mendapatkan data-data yang dapat diedit
        String username = this.inpUsername.getText(),
               // membuat query
               sql = String.format("UPDATE akun "
                       + "SET username = '%s' "
                       + "WHERE id_akun = '%s'", username, User.getIdAkun());
        
        // eksekusi query
        return this.db.stat.executeUpdate(sql) > 0;
    }
    
    private boolean editDataDetailAkun() throws SQLException{
        // mendapatkan data-data yang dapat diedit
        String nama = this.inpNama.getText(), noHp = this.inpNoHp.getText(),
               inpEml = this.inpEmail.getText(), alamat = this.inpAlamat.getText().replaceAll("\n", ""),
               // membuat query
               sql = String.format("UPDATE detail_akun "
                        + "SET nama_lengkap = '%s', no_telp = '%s', email = '%s', alamat = '%s' "
                        + "WHERE id_akun = '%s'", nama, noHp, inpEml, alamat, User.getIdAkun());
        
        System.out.println(sql);
        
        // eksekusi query
        return this.db.stat.executeUpdate(sql) > 0;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnLogoutAccount = new javax.swing.JButton();
        lblClose = new javax.swing.JLabel();
        btnGantiPass = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        inpIdAkun = new com.ui.RoundedTextField(15);
        inpUsername = new com.ui.RoundedTextField(15);
        inpNama = new com.ui.RoundedTextField(15);
        inpNoHp = new com.ui.RoundedTextField(15);
        jScrollPane1 = new javax.swing.JScrollPane();
        inpAlamat = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        inpEmail = new com.ui.RoundedTextField(15);
        lblStatusUsername = new javax.swing.JLabel();
        lblStatusEmail = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));
        pnlMain.setRoundBottomLeft(30);
        pnlMain.setRoundBottomRight(30);
        pnlMain.setRoundTopLeft(30);
        pnlMain.setRoundTopRight(30);

        lblDialogName.setFont(new java.awt.Font("Dialog", 1, 34)); // NOI18N
        lblDialogName.setForeground(new java.awt.Color(26, 105, 243));
        lblDialogName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDialogName.setText("Edit Akun");
        lblDialogName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("ID Akun");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Username");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Nomor Hp");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("Alamat");

        btnLogoutAccount.setBackground(new java.awt.Color(255, 0, 51));
        btnLogoutAccount.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnLogoutAccount.setForeground(new java.awt.Color(255, 255, 255));
        btnLogoutAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-logout.png"))); // NOI18N
        btnLogoutAccount.setText("Batal");
        btnLogoutAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutAccountMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutAccountMouseExited(evt);
            }
        });
        btnLogoutAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutAccountActionPerformed(evt);
            }
        });

        lblClose.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-mpopup-close.png"))); // NOI18N
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

        btnGantiPass.setBackground(new java.awt.Color(51, 204, 0));
        btnGantiPass.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnGantiPass.setForeground(new java.awt.Color(255, 255, 255));
        btnGantiPass.setText("Simpan");
        btnGantiPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGantiPassActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Nama Lengkap");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        inpIdAkun.setEditable(false);
        inpIdAkun.setBackground(new java.awt.Color(231, 235, 239));
        inpIdAkun.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        inpIdAkun.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        inpIdAkun.setName("ID Akun"); // NOI18N

        inpUsername.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        inpUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        inpUsername.setName("Username"); // NOI18N
        inpUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpUsernameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpUsernameKeyTyped(evt);
            }
        });

        inpNama.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        inpNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        inpNama.setName("Nama Lengkap"); // NOI18N

        inpNoHp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        inpNoHp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        inpNoHp.setName("Nomor Hp"); // NOI18N

        inpAlamat.setColumns(20);
        inpAlamat.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        inpAlamat.setRows(5);
        inpAlamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpAlamat.setName("Alamat"); // NOI18N
        jScrollPane1.setViewportView(inpAlamat);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Email");

        inpEmail.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        inpEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        inpEmail.setName("Email"); // NOI18N
        inpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpEmailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpEmailKeyTyped(evt);
            }
        });

        lblStatusUsername.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblStatusUsername.setText(" ");

        lblStatusEmail.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblStatusEmail.setText(" ");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnGantiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLogoutAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblDialogName, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlMainLayout.createSequentialGroup()
                                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(inpIdAkun)
                                            .addComponent(inpUsername)
                                            .addComponent(inpNama)
                                            .addComponent(inpNoHp)
                                            .addComponent(inpEmail)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))))
                                .addGap(10, 10, 10)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblStatusUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblStatusEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDialogName, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(lblClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(inpIdAkun))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblStatusUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpUsername)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpNama)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpNoHp)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblStatusEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(inpEmail)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogoutAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGantiPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutAccountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutAccountMouseEntered
        this.btnLogoutAccount.setBackground(new Color(0,0,0));
    }//GEN-LAST:event_btnLogoutAccountMouseEntered

    private void btnLogoutAccountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutAccountMouseExited
        this.btnLogoutAccount.setBackground(new Color(252,51,51));
    }//GEN-LAST:event_btnLogoutAccountMouseExited

    private void btnLogoutAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutAccountActionPerformed
        Audio.play(Audio.SOUND_INFO);
        this.dispose();
    }//GEN-LAST:event_btnLogoutAccountActionPerformed

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        this.lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblClose.setIcon(Gambar.getIcon("ic-mpopup-close-entered.png"));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        this.lblClose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblClose.setIcon(Gambar.getIcon("ic-mpopup-close.png"));
    }//GEN-LAST:event_lblCloseMouseExited

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void btnGantiPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGantiPassActionPerformed
        String pesan = "Apakah Anda yakin ingin mengubah data akun Anda?";
        int status = JOptionPane.showConfirmDialog(this, pesan, "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if(status == JOptionPane.YES_OPTION){
            this.editData();
        }
    }//GEN-LAST:event_btnGantiPassActionPerformed

    private void inpUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyTyped
        this.validasiUsername(evt);
    }//GEN-LAST:event_inpUsernameKeyTyped

    private void inpUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyReleased
        this.validasiUsername(evt);
    }//GEN-LAST:event_inpUsernameKeyReleased

    private void inpEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyReleased
        this.validasi(this.inpEmail.getText());
    }//GEN-LAST:event_inpEmailKeyReleased

    private void inpEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyTyped
        this.validasi(this.inpEmail.getText());
    }//GEN-LAST:event_inpEmailKeyTyped

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                EditAkun dialog = new EditAkun(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnGantiPass;
    private javax.swing.JButton btnLogoutAccount;
    private javax.swing.JTextArea inpAlamat;
    private javax.swing.JTextField inpEmail;
    private javax.swing.JTextField inpIdAkun;
    private javax.swing.JTextField inpNama;
    private javax.swing.JTextField inpNoHp;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JLabel lblStatusEmail;
    private javax.swing.JLabel lblStatusUsername;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
