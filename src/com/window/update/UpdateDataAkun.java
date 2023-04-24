package com.window.update;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.manage.Triggers;
import com.manage.User;
import com.manage.Validation;
import com.media.Gambar;
import com.window.dialog.PopUpBackground;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDataAkun extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected, oldUsername;
    
    private final Database db = new Database();
    
    private final User us = new User();
    
    private final PopUpBackground win = new PopUpBackground();
    
    /**
     * Creates new form TambahDataBahan
     * @param parent
     * @param modal
     * @param kondisi
     * @param id
     */
    public UpdateDataAkun(java.awt.Frame parent, boolean modal, int kondisi, String id) {
        super(parent, modal);
        this.setUndecorated(true);
        win.setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.inpId.setEditable(false);
        
        // set ui button
        this.btnSimpan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // set margin button
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNoTelp.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));   
        this.inpAlamat.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpPassword.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpUsername.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        this.idSelected = id;
        this.kondisi = kondisi;
        
        switch(kondisi){
            case TAMBAH : 
                this.lblTitle.setText("Tambah Data Akun");
                this.inpId.setText(this.createID());
                break;
            case EDIT : 
                this.lblTitle.setText("Edit Data Akun");
                this.inpId.setText(this.idSelected);
                this.showData();
                // menghilangkan username dan password saat mengedit data
                this.inpPassword.setVisible(false);
                this.lblEye.setVisible(false);
                this.lblPassword.setVisible(false);
                this.inpUsername.setEditable(false);
                this.inpUsername.setBackground(new Color(231,235,239));
                break;
        }
        
        this.oldUsername = this.inpUsername.getText();
    }

    @Override
    public void dispose(){
        super.dispose();
        this.win.dispose();
    }
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM karyawan ORDER BY id_karyawan DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_karyawan");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }
            }
            
            // membuat id baru
            return String.format("KY%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showData(){
        try{
            // menyiapkan query
            String sql = "SELECT karyawan.nama_karyawan, karyawan.alamat, karyawan.no_telp, karyawan.shif, user.username, user.password "
                    + "FROM karyawan JOIN user ON karyawan.id_karyawan = user.id_karyawan "
                    + "WHERE karyawan.id_karyawan = '"+this.idSelected+"'";
            
            // membuat koneksi
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // menampilkan data ke window
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(this.db.res.getString("karyawan.nama_karyawan"));
                this.inpAlamat.setText(this.db.res.getString("karyawan.alamat"));
                this.inpNoTelp.setText(this.db.res.getString("karyawan.no_telp"));
                this.inpShif.setSelectedItem(this.db.res.getString("karyawan.shif"));
                this.inpUsername.setText(this.db.res.getString("user.username"));
                this.inpPassword.setText(this.db.res.getString("user.password"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private boolean addDataKaryawan() throws SQLException{
        String sql = "INSERT INTO karyawan VALUES(?, ?, ?, ?, ?)",
               idKaryawan = this.inpId.getText(),
               nama = this.inpNama.getText(),
               noTelp = this.inpNoTelp.getText(),
               alamat = this.inpAlamat.getText(),
               shif = this.inpShif.getSelectedItem().toString();
        
        this.db.pst = this.db.conn.prepareStatement(sql);
        this.db.pst.setString(1, idKaryawan);
        this.db.pst.setString(2, nama);
        this.db.pst.setString(3, noTelp);
        this.db.pst.setString(4, alamat);
        this.db.pst.setString(5, shif);
        
        boolean o = this.db.pst.executeUpdate() > 0;
        return o;
    }
    
    private boolean addDataUser() throws SQLException{
        String sql = "INSERT INTO user VALUES(?, ?, ?, ?)",
               idKaryawan = this.inpId.getText(),
               username = this.inpUsername.getText(),
               password = this.inpPassword.getText();
        
        this.us.pst = this.us.conn.prepareStatement(sql);
        this.us.pst.setString(1, username);
        this.us.pst.setString(2, BCrypt.hashpw(password, BCrypt.gensalt(12)));
        this.us.pst.setString(3, "KARYAWAN");
        this.us.pst.setString(4, idKaryawan);
        
        return this.us.pst.executeUpdate() > 0;
    }
    
    
    private void tambahData(){
        // cek validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpUsername)){
            return;
        }else if(this.us.isExistUsername(this.inpUsername.getText())){
            Message.showWarning(this, "Username tersebut sudah terpakai");
            return;
        }else if(!Validation.isEmptyTextField(this.inpNama, this.inpNoTelp, this.inpAlamat)){
            return;
        }else if(!Validation.isNoHp(this.inpNoTelp.getText())){
            return;
        }else if(!Validation.isEmptyComboBox(this.inpShif)){
            return;
        }else if(!Validation.isEmptyPasswordField(this.inpPassword)){
            return;
        }else if(!Validation.isPassword(this.inpPassword.getText())){
            return;
        }
        
        try{
            boolean karyawan = this.addDataKaryawan(),
                    user = this.addDataUser();
            
            if(karyawan && user){
                JOptionPane.showMessageDialog(this, "Data karyawan berhasil ditambahkan!");
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Data karyawan gagal ditambahkan!");
            }
        }catch(SQLException ex){
            if(ex.getMessage().contains("Duplicate entry")){
                Message.showInformation(this, "Username tersebut sudah ada!");
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private boolean editDataKaryawan() throws SQLException{
        if(!this.isValidPass()){
            JOptionPane.showMessageDialog(this, "Password minimal harus 8 karakter!");
            return false;
        }
        // mendapatkan data
        String idKaryawan = this.inpId.getText(),
               nama = this.inpNama.getText(),
               noTelp = this.inpNoTelp.getText(),
               alamat = this.inpAlamat.getText(),
               shif = this.inpShif.getSelectedItem().toString(), sql;
                // set developer shif
                if(this.inpId.getText().equalsIgnoreCase("KY001") || this.inpId.getText().equalsIgnoreCase("KY002") || this.inpId.getText().equalsIgnoreCase("KY003") || this.inpId.getText().equalsIgnoreCase("KY004")){
                    shif = "No Shif";
                }
               // membuat query
               sql = String.format("UPDATE karyawan "
                        + "SET nama_karyawan = '%s', no_telp = '%s', alamat = '%s', shif = '%s' "
                        + "WHERE id_karyawan = '%s'", nama, noTelp, alamat, shif, idKaryawan);
        
        return  this.db.stat.executeUpdate(sql) > 0;
    }
    
    private void editData(){
        // cek validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpUsername)){
            return;
        }else if(!this.oldUsername.equalsIgnoreCase(this.inpUsername.getText())){
            if(this.us.isExistUsername(this.inpUsername.getText())){
                Message.showWarning(this, "Username tersebut sudah terpakai");
                return;
            }
        }else if(!Validation.isEmptyTextField(this.inpNama, this.inpNoTelp, this.inpAlamat)){
            return;
        }else if(!Validation.isNoHp(this.inpNoTelp.getText())){
            return;
        }else if(!Validation.isEmptyComboBox(this.inpShif)){
            return;
        }else if(!Validation.isEmptyPasswordField(this.inpPassword)){
            return;
        }else if(!Validation.isPassword(this.inpPassword.getText())){
            return;
        }
        
        try{
            boolean karyawan = this.editDataKaryawan();
            
            if(karyawan){
                JOptionPane.showMessageDialog(this, "Data karyawan berhasil diedit!");
                this.dispose();
                new Triggers().updateKaryawan();
            }else{
                JOptionPane.showMessageDialog(this, "Data karyawan gagal diedit!");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    public boolean isValidPass(){
        return this.inpPassword.getText().length() >= 8;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lineHorTop = new javax.swing.JSeparator();
        lineHorBot = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JToggleButton();
        lblId = new javax.swing.JLabel();
        inpId = new com.ui.RoundedTextField(15);
        lblNama = new javax.swing.JLabel();
        inpNama = new com.ui.RoundedTextField(15);
        lblNoTelp = new javax.swing.JLabel();
        inpNoTelp = new com.ui.RoundedTextField(15);
        lblAlamat = new javax.swing.JLabel();
        inpAlamat = new com.ui.RoundedTextField(15);
        lblUsername = new javax.swing.JLabel();
        inpUsername = new com.ui.RoundedTextField(15);
        lblShif = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        inpPassword = new com.ui.RoundedPasswordField(15);
        lblEye = new javax.swing.JLabel();
        inpShif = new javax.swing.JComboBox();
        lblRfid = new javax.swing.JLabel();
        inpRfid = new com.ui.RoundedTextField(15);
        lblEmail = new javax.swing.JLabel();
        inpEmail = new com.ui.RoundedTextField(15);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(248, 249, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 10));
        jPanel1.setForeground(new java.awt.Color(59, 63, 74));

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(250, 22, 22));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Data Karyawan");

        lineHorTop.setBackground(new java.awt.Color(8, 8, 9));
        lineHorTop.setForeground(new java.awt.Color(8, 8, 9));

        lineHorBot.setBackground(new java.awt.Color(0, 0, 0));
        lineHorBot.setForeground(new java.awt.Color(0, 0, 0));

        btnSimpan.setBackground(new java.awt.Color(34, 119, 237));
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-simpan.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimpanMouseExited(evt);
            }
        });
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(220, 41, 41));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-batal.png"))); // NOI18N
        btnHapus.setText("Batalkan");
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHapusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHapusMouseExited(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        lblId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-id.png"))); // NOI18N
        lblId.setText("ID Karyawan");

        inpId.setBackground(new java.awt.Color(231, 235, 239));
        inpId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpId.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-nama.png"))); // NOI18N
        lblNama.setText("Nama ");

        inpNama.setBackground(new java.awt.Color(248, 249, 250));
        inpNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNama.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNama.setName("Nama Karyawan"); // NOI18N

        lblNoTelp.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNoTelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-telephone.png"))); // NOI18N
        lblNoTelp.setText("No Telephone");

        inpNoTelp.setBackground(new java.awt.Color(248, 249, 250));
        inpNoTelp.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNoTelp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNoTelp.setName("No Telephone"); // NOI18N
        inpNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpNoTelpKeyTyped(evt);
            }
        });

        lblAlamat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblAlamat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-alamat.png"))); // NOI18N
        lblAlamat.setText("Alamat");

        inpAlamat.setBackground(new java.awt.Color(248, 249, 250));
        inpAlamat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpAlamat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpAlamat.setName("Alamat"); // NOI18N

        lblUsername.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-username.png"))); // NOI18N
        lblUsername.setText("Username");

        inpUsername.setBackground(new java.awt.Color(248, 249, 250));
        inpUsername.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpUsername.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpUsername.setName("Username"); // NOI18N

        lblShif.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblShif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-shift.png"))); // NOI18N
        lblShif.setText("Shif");

        lblPassword.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-password.png"))); // NOI18N
        lblPassword.setText("Password");

        inpPassword.setBackground(new java.awt.Color(248, 249, 250));
        inpPassword.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpPassword.setName("Password"); // NOI18N

        lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-eye-close.png"))); // NOI18N
        lblEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEyeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEyeMouseExited(evt);
            }
        });

        inpShif.setBackground(new java.awt.Color(248, 249, 250));
        inpShif.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpShif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Shif", "Siang", "Malam" }));
        inpShif.setName("Shif"); // NOI18N

        lblRfid.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblRfid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-rfid.png"))); // NOI18N
        lblRfid.setText("Kode RFID");

        inpRfid.setBackground(new java.awt.Color(248, 249, 250));
        inpRfid.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpRfid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpRfid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpRfid.setName("Username"); // NOI18N

        lblEmail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-email.png"))); // NOI18N
        lblEmail.setText("Email");

        inpEmail.setBackground(new java.awt.Color(248, 249, 250));
        inpEmail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpEmail.setName("No Telephone"); // NOI18N
        inpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpEmailKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus))
                    .addComponent(lineHorBot, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineHorTop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEye))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblShif, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(lblAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inpShif, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inpAlamat)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineHorTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRfid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblShif, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpShif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEye, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpPassword))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lineHorBot, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
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

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        this.btnSimpan.setIcon(Gambar.getIcon("ic-data-simpan-entered.png"));
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        this.btnSimpan.setIcon(Gambar.getIcon("ic-data-simpan.png"));
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        switch(this.kondisi){
            case TAMBAH : 
                    this.tambahData();
                break;
            case EDIT : 
                    this.editData();
                break;
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-batal-entered.png"));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-batal.png"));
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        this.win.dispose();
        this.dispose();
    }//GEN-LAST:event_btnHapusActionPerformed

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

    private Text txt = new Text();
    private void inpNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNoTelpKeyTyped
            this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpNoTelpKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void inpEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_inpEmailKeyTyped

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
            java.util.logging.Logger.getLogger(UpdateDataAkun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            UpdateDataAkun dialog = new UpdateDataAkun(new javax.swing.JFrame(), true, 1, "BA003");
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField inpAlamat;
    private javax.swing.JTextField inpEmail;
    private javax.swing.JTextField inpId;
    private javax.swing.JTextField inpNama;
    private javax.swing.JTextField inpNoTelp;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpRfid;
    private javax.swing.JComboBox inpShif;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRfid;
    private javax.swing.JLabel lblShif;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
