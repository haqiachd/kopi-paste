package com.window.update;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.manage.Triggers;
import com.manage.User;
import com.manage.Validation;
import com.media.Gambar;
import java.awt.event.KeyEvent;
import com.window.dialog.PopUpBackground;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDataAkun extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected, oldRfid, oldUsername;
    
    private final Database db = new Database();
    
    private final User us = new User();
    
    private final Text txt = new Text();
    
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
        this.inpRfid.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpUsername.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNoTelp.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));   
        this.inpEmail.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpAlamat.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpPassword.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        this.idSelected = id;
        this.kondisi = kondisi;
        
        switch(kondisi){
            case TAMBAH : 
                this.lblTitle.setText("Tambah Data Akun");
                this.inpId.setText(this.createID());
                this.inpUsername.requestFocus();
                break;
            case EDIT : 
                this.lblTitle.setText("Edit Data Akun");
                this.inpId.setText(this.idSelected);
                this.showData();
                // 
                this.lblEye.setEnabled(false);
                this.inpPassword.setText("12345678");
                this.inpPassword.setBackground(new Color(231,235,239));
                this.inpPassword.setEditable(false);
                this.inpUsername.setEditable(false);
                this.inpUsername.setBackground(new Color(231,235,239));
                this.inpNama.setEditable(false);
                this.inpNama.setBackground(new Color(231,235,239));
                this.inpNoTelp.setEditable(false);
                this.inpNoTelp.setBackground(new Color(231,235,239));
                this.inpEmail.setEditable(false);
                this.inpEmail.setBackground(new Color(231,235,239));
                this.inpAlamat.setEditable(false);
                this.inpAlamat.setBackground(new Color(231,235,239));
                this.inpRfid.requestFocus();
                // set data value
                this.oldUsername = this.inpUsername.getText();
                this.oldRfid = this.inpRfid.getText();
                // 
                JTextField fields[] = {this.inpUsername, this.inpNama, this.inpNoTelp, this.inpEmail, this.inpAlamat, this.inpPassword};
                for(JTextField f : fields){
                    f.addMouseListener(new java.awt.event.MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            Message.showInformation(this, "Hanya pemilik akun yang dapat mengedit data ini");
                        }
                    });
                }
                break;
        }        
        
    }

    @Override
    public void dispose(){
        super.dispose();
        this.win.dispose();
    }
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM akun ORDER BY id_akun DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_akun");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }
            }
            
            // membuat id baru
            return String.format("KY%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showData(){
        try{
            // menyiapkan query
            String sql = "SELECT da.nama_lengkap, da.alamat, da.no_telp, da.email, da.shif, a.id_akun, a.username, a.rfid, a.password, a.level "
                    + "FROM akun AS a "
                    + "JOIN detail_akun AS da "
                    + "ON a.id_akun = da.id_akun "
                    + "WHERE a.id_akun = '"+this.idSelected+"'";
            
            System.out.println(sql);
            // membuat koneksi
            this.db.res = this.db.stat.executeQuery(sql);
            
            
            if(this.db.res.next()){
                // menampilkan data ke window
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(this.db.res.getString("da.nama_lengkap"));
                this.inpAlamat.setText(this.db.res.getString("da.alamat"));
                this.inpNoTelp.setText(this.db.res.getString("da.no_telp"));
                this.inpEmail.setText(this.db.res.getString("da.email"));
                this.inpLevel.setSelectedItem(this.txt.toCapitalize(this.db.res.getString("a.level")));
                this.inpShif.setSelectedItem(this.db.res.getString("da.shif"));
                this.inpUsername.setText(this.db.res.getString("a.username"));
                this.inpRfid.setText(this.db.res.getString("a.rfid"));
                this.inpPassword.setText(this.db.res.getString("a.password"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    } 
    
    private void tambahData(){
        // cek validasi username
        if(!Validation.isEmptyTextField(this.inpUsername)){
            return;
        }else if(!Validation.isUsername(this.inpUsername.getText())){
            return;
        }else if(this.us.isExistUsername(this.inpUsername.getText())){
            Message.showWarning(this, String.format("'%s' Username tersebut sudah terpakai", this.inpUsername.getText()));
            return;
        }
        // cek validasi rfid
        else if(!Validation.isEmptyTextField(this.inpRfid)){
            return;
        }else if(!Validation.isRfid(this.inpRfid.getText())){
            return;
        }else if(this.us.isExistRfid(this.inpRfid.getText())){
            Message.showWarning(this, String.format("'%s' Kode RFID tersebut sudah terpakai", this.inpRfid.getText()));
            return;
        }
        // cek validasi nama, no hp, email dan alamat
        else if(!Validation.isEmptyTextField(this.inpNama, this.inpNoTelp, this.inpEmail, this.inpAlamat)){
            return;
        }else if(!Validation.isNamaOrang(this.inpNama.getText())){
            return;
        }else if(!Validation.isNoHp(this.inpNoTelp.getText())){
            return;
        }else if(!Validation.isEmail(this.inpEmail.getText())){
            return;
        }else if(!Validation.isNamaTempat(this.inpAlamat.getText())){
            return;
        }
        // cek validasi level dan shif
        else if(!Validation.isEmptyComboBox(this.inpLevel, this.inpShif)){
            return;
        }else if(!Validation.isShif(this.inpLevel.getSelectedItem().toString(), this.inpShif.getSelectedItem().toString())){
            return;   
        }
        // cek validasi password
        else if(!Validation.isEmptyPasswordField(this.inpPassword)){
            return;
        }else if(!Validation.isPassword(this.inpPassword.getText())){
            return;
        }
        
        try{
            // menambahkan data akun dan detail akun ke database
            boolean akun = this.addDataAkun(), 
                    detailAkun = this.addDataDetailAkun();
            
            // cek apakah data berhasil ditambahkan
            if(akun && detailAkun){
                Message.showInformation(this, "Akun berhasil ditambahkan!");
                this.dispose();
            }else{
                Message.showInformation(this, "Akun gagal ditambahkan!");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private boolean addDataAkun() throws SQLException{
        // menyiapkan query
        String sql = "INSERT INTO akun VALUES(?, ?, ?, ?, ?)",
               // mendapatkan data akun
               idAKun = this.inpId.getText(),
               username = this.inpUsername.getText(),
               password = this.inpPassword.getText(),
               rfid = this.inpRfid.getText(),
               level = this.inpLevel.getSelectedItem().toString().toUpperCase();
        
        // menambahkan ke query
        this.us.pst = this.us.conn.prepareStatement(sql);
        this.us.pst.setString(1, idAKun);
        this.us.pst.setString(2, username);
        this.us.pst.setString(3, BCrypt.hashpw(password, BCrypt.gensalt(12))); // enkripsi password
        this.us.pst.setString(4, rfid);
        this.us.pst.setString(5, level);
        
        // eksekusi query
        return this.us.pst.executeUpdate() > 0;
    }
    
    private boolean addDataDetailAkun() throws SQLException{
        // menyiapkan query
        String sql = "INSERT INTO detail_akun VALUES(?, ?, ?, ?, ?, ?)",
               // mendapatkan data detail akun
               idAkun = this.inpId.getText(),
               nama = this.inpNama.getText(),
               noTelp = this.inpNoTelp.getText(),
               email = this.inpEmail.getText(),
               alamat = this.inpAlamat.getText(),
               shif = this.inpShif.getSelectedItem().toString();
        
        // menambahkan data ke query
        this.db.pst = this.db.conn.prepareStatement(sql);
        this.db.pst.setString(1, idAkun);
        this.db.pst.setString(2, nama);
        this.db.pst.setString(3, noTelp);
        this.db.pst.setString(4, email);
        this.db.pst.setString(5, alamat);
        this.db.pst.setString(6, shif);
        
        // eksekusi query
        return this.db.pst.executeUpdate() > 0;
    }
    
    private void editData(){

        // cek validasi rfid
        if(!Validation.isEmptyTextField(this.inpRfid)){
            return;
        }else if(!Validation.isRfid(this.inpRfid.getText())){
            return;
        }else if(!this.oldRfid.equalsIgnoreCase(this.inpRfid.getText())){
            if (this.us.isExistRfid(this.inpRfid.getText())) {
                Message.showWarning(this, String.format("'%s' Kode RFID tersebut sudah terpakai", this.inpRfid.getText()));
                return;
            }
        }
        // cek validasi nama, no hp, email dan alamat
        else if(!Validation.isEmptyTextField(this.inpNama, this.inpNoTelp, this.inpEmail, this.inpAlamat)){
            return;
        }else if(!Validation.isNamaOrang(this.inpNama.getText())){
            return;
        }else if(!Validation.isNoHp(this.inpNoTelp.getText())){
            return;
        }else if(!Validation.isEmail(this.inpEmail.getText())){
            return;
        }else if(!Validation.isNamaTempat(this.inpAlamat.getText())){
            return;
        }
        // cek validasi level dan shif
        else if(!Validation.isEmptyComboBox(this.inpLevel, this.inpShif)){
            return;
        }else if(!Validation.isShif(this.inpLevel.getSelectedItem().toString(), this.inpShif.getSelectedItem().toString())){
            return;   
        }
        
        try{
            // mengedit data akun dan detail akun ke database
            boolean editAkun = this.editDataAkun() && this.editDataDetailAkun();
            
            if(editAkun){
                Message.showInformation(this, "Data karyawan berhasil diedit!");
                this.dispose();
//                new Triggers().updateKaryawan();
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
        String idAkun = this.inpId.getText(),
               rfid = this.inpRfid.getText(),
               level = this.inpLevel.getSelectedItem().toString(),
               // membuat query
               sql = String.format("UPDATE akun "
                       + "SET rfid = '%s', level = '%s' "
                       + "WHERE id_akun = '%s'", rfid, level, idAkun);
        
        // eksekusi query
        return this.db.stat.executeUpdate(sql) > 0;
    }
    
    private boolean editDataDetailAkun() throws SQLException{
        // mendapatkan data-data yang dapat diedit
        String idAkun = this.inpId.getText(),
               shif = this.inpShif.getSelectedItem().toString(), 
               // membuat query
               sql = String.format("UPDATE detail_akun "
                        + "SET shif = '%s' "
                        + "WHERE id_akun = '%s'", shif, idAkun);
        
        // eksekusi query
        return this.db.stat.executeUpdate(sql) > 0;
    }
    
    /**
     * validasi username untuk key event 
     * 
     * @param evt 
     */
    private void validasiUsername(KeyEvent evt) {
        // mendapatkan data dari textfield
        String username = this.inpUsername.getText();
        
        // jika menekan enter
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.inpRfid.requestFocus();
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
                this.lblStatusUsername.setForeground(new Color(0, 0, 255));
            }
        }
    }
    
    /**
     * validasi rfid untuk keyevent
     * 
     * @param evt 
     */
    private void validasiRfid(KeyEvent evt){
        // mendapatkan data dari textfield
        String rfid = this.inpRfid.getText();
        
        // jika menekan enter
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpNama.requestFocus();
        }else{
            // rfid harus 10 karakter
            if(rfid.length() != 10){
                this.lblStatusRfid.setText("Harus 10 angka");
                this.lblStatusRfid.setForeground(new Color(255,0,0));
            }else{
                // cek kondisi
                switch(this.kondisi){
                    // kondisi tambah data
                    case UpdateDataAkun.TAMBAH : 
                        // cek apakah rfid sudah digunakan atau belum
                        if(this.us.isExistRfid(rfid)){
                            this.lblStatusRfid.setText("Sudah terpakai");
                            this.lblStatusRfid.setForeground(new Color(255,0,0));
                        }else{
                            this.lblStatusRfid.setText("RFID valid");
                            this.lblStatusRfid.setForeground(new Color(0,0,255));                            
                        }
                        break;
                    // kondisi edit data
                    case UpdateDataAkun.EDIT : 
                        // jika rfid tidak diubah
                        if(rfid.equalsIgnoreCase(this.oldRfid)){
                            this.lblStatusRfid.setText("RFID valid");
                            this.lblStatusRfid.setForeground(new Color(0,0,255));    
                        }
                        // cek apakah rfid sudah digunakan atau belum
                        else if(this.us.isExistRfid(rfid)){
                            this.lblStatusRfid.setText("Sudah terpakai");
                            this.lblStatusRfid.setForeground(new Color(255,0,0));
                        }else{
                            this.lblStatusRfid.setText("RFID valid");
                            this.lblStatusRfid.setForeground(new Color(0,0,255));  
                        }
                        break;
                }
            }
        }
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
        lblLevel = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        inpPassword = new com.ui.RoundedPasswordField(15);
        lblEye = new javax.swing.JLabel();
        inpLevel = new javax.swing.JComboBox();
        lblRfid = new javax.swing.JLabel();
        inpRfid = new com.ui.RoundedTextField(15);
        lblEmail = new javax.swing.JLabel();
        inpEmail = new com.ui.RoundedTextField(15);
        lblShif = new javax.swing.JLabel();
        inpShif = new javax.swing.JComboBox();
        lblStatusUsername = new javax.swing.JLabel();
        lblStatusRfid = new javax.swing.JLabel();

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
        lblId.setText("ID Akun");
        lblId.setName(""); // NOI18N

        inpId.setBackground(new java.awt.Color(231, 235, 239));
        inpId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpId.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpId.setName("ID Akun"); // NOI18N

        lblNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-nama.png"))); // NOI18N
        lblNama.setText("Nama ");

        inpNama.setBackground(new java.awt.Color(248, 249, 250));
        inpNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNama.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNama.setName("Nama Karyawan"); // NOI18N
        inpNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpNamaKeyReleased(evt);
            }
        });

        lblNoTelp.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNoTelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-telephone.png"))); // NOI18N
        lblNoTelp.setText("Nomor HP");

        inpNoTelp.setBackground(new java.awt.Color(248, 249, 250));
        inpNoTelp.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNoTelp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNoTelp.setName("Nomor HP"); // NOI18N
        inpNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpNoTelpKeyReleased(evt);
            }
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
        inpAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpAlamatKeyReleased(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-username.png"))); // NOI18N
        lblUsername.setText("Username");

        inpUsername.setBackground(new java.awt.Color(248, 249, 250));
        inpUsername.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpUsername.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpUsername.setName("Username"); // NOI18N
        inpUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpUsernameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpUsernameKeyTyped(evt);
            }
        });

        lblLevel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-levelakun.png"))); // NOI18N
        lblLevel.setText("Level Akun");

        lblPassword.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-password.png"))); // NOI18N
        lblPassword.setText("Password");

        inpPassword.setBackground(new java.awt.Color(248, 249, 250));
        inpPassword.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpPassword.setName("Password"); // NOI18N
        inpPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpPasswordKeyReleased(evt);
            }
        });

        lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-login-eye-close.png"))); // NOI18N
        lblEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEyeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEyeMouseExited(evt);
            }
        });

        inpLevel.setBackground(new java.awt.Color(248, 249, 250));
        inpLevel.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Level", "Admin", "Karyawan" }));
        inpLevel.setName("Level"); // NOI18N
        inpLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpLevelActionPerformed(evt);
            }
        });
        inpLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpLevelKeyReleased(evt);
            }
        });

        lblRfid.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblRfid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-rfid.png"))); // NOI18N
        lblRfid.setText("Kode RFID");

        inpRfid.setBackground(new java.awt.Color(248, 249, 250));
        inpRfid.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpRfid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpRfid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpRfid.setName("RFID"); // NOI18N
        inpRfid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpRfidKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpRfidKeyTyped(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-email.png"))); // NOI18N
        lblEmail.setText("Email");

        inpEmail.setBackground(new java.awt.Color(248, 249, 250));
        inpEmail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpEmail.setName("Email"); // NOI18N
        inpEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpEmailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpEmailKeyTyped(evt);
            }
        });

        lblShif.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblShif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-shift.png"))); // NOI18N
        lblShif.setText("Shif");

        inpShif.setBackground(new java.awt.Color(248, 249, 250));
        inpShif.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpShif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Shif", "No Shif", "Siang", "Malam" }));
        inpShif.setName("Shif"); // NOI18N
        inpShif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpShifKeyReleased(evt);
            }
        });

        lblStatusUsername.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblStatusUsername.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lblStatusRfid.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblStatusRfid.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineHorBot, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineHorTop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(lblAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblShif, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inpLevel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inpAlamat)
                                    .addComponent(inpShif, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEye))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStatusUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStatusRfid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(21, 21, 21))
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
                    .addComponent(lblStatusUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRfid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatusRfid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(lblLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpLevel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblShif, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpShif))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEye, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lineHorBot, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if(this.kondisi == UpdateDataAkun.TAMBAH){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-open.png"));
            this.inpPassword.setEchoChar((char)0);            
        }
    }//GEN-LAST:event_lblEyeMouseEntered

    private void lblEyeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseExited
        if(this.kondisi == UpdateDataAkun.TAMBAH){
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            this.lblEye.setIcon(Gambar.getIcon("ic-login-eye-close.png"));
            this.inpPassword.setEchoChar('•');
        }
    }//GEN-LAST:event_lblEyeMouseExited

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
        this.us.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosing


    private void inpUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyReleased
        this.validasiUsername(evt);
    }//GEN-LAST:event_inpUsernameKeyReleased

    private void inpUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpUsernameKeyTyped
        this.validasiUsername(evt);
    }//GEN-LAST:event_inpUsernameKeyTyped

    private void inpRfidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpRfidKeyReleased
        this.validasiRfid(evt);
    }//GEN-LAST:event_inpRfidKeyReleased

    private void inpRfidKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpRfidKeyTyped
        this.validasiRfid(evt);
    }//GEN-LAST:event_inpRfidKeyTyped

    private void inpNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNamaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpNoTelp.requestFocus();
        }
    }//GEN-LAST:event_inpNamaKeyReleased

    private void inpNoTelpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNoTelpKeyTyped
        this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpNoTelpKeyTyped

    private void inpEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyTyped
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpAlamat.requestFocus();
        }
    }//GEN-LAST:event_inpEmailKeyTyped

    private void inpAlamatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpAlamatKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpLevel.requestFocus();
        }
    }//GEN-LAST:event_inpAlamatKeyReleased

    private void inpLevelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpLevelKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpShif.requestFocus();
        }
    }//GEN-LAST:event_inpLevelKeyReleased

    private void inpShifKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpShifKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpPassword.requestFocus();
        }
    }//GEN-LAST:event_inpShifKeyReleased

    private void inpPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpPasswordKeyReleased
        // nanti dulu
    }//GEN-LAST:event_inpPasswordKeyReleased

    private void inpNoTelpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNoTelpKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpEmail.requestFocus();
        }
    }//GEN-LAST:event_inpNoTelpKeyReleased

    private void inpEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpEmailKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpAlamat.requestFocus();
        }
    }//GEN-LAST:event_inpEmailKeyReleased

    private void inpLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpLevelActionPerformed
        if(this.inpLevel.getSelectedItem().toString().equalsIgnoreCase("admin")){
            inpShif.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Pilih Shif", "No Shif"}));
        }else if(this.inpLevel.getSelectedItem().toString().equalsIgnoreCase("karyawan")){
            inpShif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Shif", "Siang", "Malam" }));
        }
    }//GEN-LAST:event_inpLevelActionPerformed

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
    private javax.swing.JComboBox inpLevel;
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
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblNoTelp;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRfid;
    private javax.swing.JLabel lblShif;
    private javax.swing.JLabel lblStatusRfid;
    private javax.swing.JLabel lblStatusUsername;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
