package com.window.dialog;

import com.koneksi.Dbase;
import com.koneksi.Koneksi;
import com.manage.Message;
import com.manage.Text;
import com.manage.Validation;
import com.media.Gambar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDataMenu extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected;
    
    private Object[] mainList, listID, listNama, listQuantity, listSatuan;
    
    private final Dbase db = new Dbase();
    
    private final PopUpBackground win = new PopUpBackground();
    
    /**
     * Creates new form TambahDataBahan
     * @param parent
     * @param modal
     * @param kondisi
     * @param id
     */
    public UpdateDataMenu(java.awt.Frame parent, boolean modal, int kondisi, String id) {
        super(parent, modal);
        this.setUndecorated(true);
        win.setVisible(true);
        initComponents();
        this.setLocationRelativeTo(null);
        
        // set ui button
        this.inpId.setEditable(false);
        this.btnSimpan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // set margin text field
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        this.idSelected = id;
        this.kondisi = kondisi;
        
        // pilih kondisi tambah atau edit
        switch(kondisi){
            case TAMBAH : 
                this.lblTitle.setText("Tambah Data Menu");
                this.inpId.setText(this.createID());
                break;
            case EDIT : 
                this.lblTitle.setText("Edit Data Menu");
                this.inpId.setText(this.idSelected);
                this.showData();
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
            String query = "SELECT * FROM menu ORDER BY id_menu DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_menu");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }else{
                    nomor = "000";
                }
            }

            // membuat id baru
            return String.format("MN%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }

    private void showData(){
        this.inpBahan.removeAllList();
        try{
            // membuat query untuk menampilkan data menu
            String sql = "SELECT menu.nama_menu, menu.jenis, menu.harga, "
                       + "detail_menu.id_bahan, bahan.nama_bahan, detail_menu.quantity, bahan.satuan "
                       + "FROM menu "
                       + "JOIN detail_menu  "
                       + "ON menu.id_menu = detail_menu.id_menu "
                       + "JOIN bahan "
                       + "ON bahan.id_bahan = detail_menu.id_bahan "
                       + "WHERE menu.id_menu = '"+this.idSelected+"'";
            System.out.println(sql);
            
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            if (r.next()) {
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(r.getString("menu.nama_menu"));
                this.inpJenis.setSelectedItem(r.getString("menu.jenis"));
                this.inpHarga.setText(r.getString("menu.harga"));
                // BA001 | 10.gr, Nama Bahab
                this.inpBahan.addList(
                        String.format(
                                "%s | %s.%s, %s", r.getString("detail_menu.id_bahan"), r.getString("detail_menu.quantity"), r.getString("bahan.satuan"), r.getString("bahan.nama_bahan")
                        ));
                while (r.next()) {
                    this.inpBahan.addList(
                            String.format(
                                    "%s | %s.%s, %s", r.getString("detail_menu.id_bahan"), r.getString("detail_menu.quantity"), r.getString("bahan.satuan"), r.getString("bahan.nama_bahan")
                            ));
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private void updateListData(){
        // copy list id bahan dan nama kedalam object
        this.mainList = this.inpBahan.getAllList();
        
        // BA001 | 10.gr, Nama Barang
        
        // mendapatkan data id bahan dari list
        String buff;
        this.listID = new Object[this.mainList.length];
        for(int i = 0; i < this.listID.length; i++){
            buff = this.mainList[i].toString();
            this.listID[i] = buff.substring(0, buff.indexOf(" "));
        }
        
        // mendapatkan data quantity
        buff = "";
        this.listQuantity = new Object[this.mainList.length];
        for(int i = 0; i < this.listID.length; i++){
            buff = this.mainList[i].toString();
            this.listQuantity[i] = buff.substring(buff.indexOf("|")+1, buff.indexOf("."));
        }
        
        // mendapatkan data quantity
        buff = "";
        this.listSatuan = new Object[this.mainList.length];
        for(int i = 0; i < this.listID.length; i++){
            buff = this.mainList[i].toString();
            this.listSatuan[i] = buff.substring(buff.indexOf(".")+1, buff.indexOf(","));
        }
        
        // mendapatkan data nama bahan
        buff = "";
        this.listNama = new Object[this.mainList.length];
        for(int i = 0; i < this.listID.length; i++){
            buff = this.mainList[i].toString();
            this.listNama[i] = buff.substring(buff.indexOf(",")+2);
        }
        
        System.out.println("==");
        System.out.println("LIST ID");
        for(Object l : listID){
            System.out.println("LIST ID : " + l);
        }
        System.out.println("QUANTITTY");
        for(Object l : listQuantity){
            System.out.println("LIST QTY : " + l);
        }
        System.out.println("SATUAN");
        for(Object l : listSatuan){
            System.out.println("LIST SATUAN : " + l);
        }
        System.out.println("NAMA");
        for(Object l : listNama){
            System.out.println("LIST NAMA : " + l);
        }
        System.out.println("====");
    }
    
    private void resetDetailMenu() throws SQLException{
        Connection c = (Connection) Koneksi.configDB();
        Statement s = c.createStatement();
        int result = s.executeUpdate("DELETE FROM detail_menu WHERE id_menu = '"+this.idSelected+"'");
        if(result > 0){
            System.out.println("Reset detail menu");
        }
        c.close();
    }
    
    private boolean tambahDataMenu() throws SQLException{
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpNama)){
            return false;
        }else if(!Validation.isEmptyComboBox(this.inpJenis)){
            return false;
        }else if(!Validation.isEmptyTextField(this.inpHarga)){
            return false;
        }else if(Integer.parseInt(this.inpHarga.getText()) <= 0){
            Message.showWarning(this, "Harga tidak boleh 0");
            return false;
        }else if(!Validation.isEmptyList(this.inpBahan)){
            return false;
        }
        // mendapatkan data
        String id = this.inpId.getText(),
               nama = this.inpNama.getText(),
               jenis = this.inpJenis.getSelectedItem().toString(),
               harga = this.inpHarga.getText();
        
        // menyiapkan query
        Connection c = (Connection) Koneksi.configDB();
        PreparedStatement p = c.prepareCall("INSERT INTO menu VALUES(?, ?, ?, ?)");
        p.setString(1, id);
        p.setString(2, nama);
        p.setString(3, jenis);
        p.setString(4, harga);
        
        // eksekusi query
        int result = p.executeUpdate();
        c.close();
        return result > 0;
    }
    
    private void updateTableDetailMenu() throws SQLException{
        this.resetDetailMenu();
        this.updateListData();
        
        String idBahan, quantity;
        for(int i = 0; i < this.mainList.length; i++){
            // mendapatkan data
            idBahan = this.listID[i].toString();
            quantity = this.listQuantity[i].toString();
            // membuat koneksi
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            s.executeUpdate(String.format("INSERT INTO detail_menu VALUES ('%s', '%s', '%s')", this.inpId.getText(), idBahan, quantity));
            c.close();
        }
    }
    
    private void tambahData(){
        try {
            boolean s = this.tambahDataMenu();
            if(s){
                this.updateTableDetailMenu();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                this.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private boolean editDataMenu() throws SQLException{
        
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpNama)){
            return false;
        }else if(!Validation.isEmptyComboBox(this.inpJenis)){
            return false;
        }else if(!Validation.isEmptyTextField(this.inpHarga)){
            return false;
        }else if(Integer.parseInt(this.inpHarga.getText()) <= 0){
            Message.showWarning(this, "Harga tidak boleh 0");
            return false;
        }else if(!Validation.isEmptyList(this.inpBahan)){
            return false;
        }
        
        // mendapatkan data
        String id = this.inpId.getText(),
               nama = this.inpNama.getText(),
               jenis = this.inpJenis.getSelectedItem().toString(),
               harga = this.inpHarga.getText(),
               sql = String.format(
                       "UPDATE menu "
                     + "SET nama_menu = '%s', jenis = '%s', harga = '%s' "
                     + "WHERE id_menu = '"+this.idSelected+"'", nama, jenis, harga, id);
        
        Connection c = (Connection) Koneksi.configDB();
        Statement s = c.createStatement();
        int result = s.executeUpdate(sql);
        c.close();
        return result > 0;
    }
    
    private void editData(){
        try {
            boolean s = this.editDataMenu();
            if(s){
                this.updateTableDetailMenu();
                JOptionPane.showMessageDialog(this, "Data berhasil diedit!");
                this.dispose();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
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
        lblData1 = new javax.swing.JLabel();
        lblData2 = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        lblData3 = new javax.swing.JLabel();
        btnTambahBahan = new javax.swing.JButton();
        btnEditBahan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inpBahan = new haqiachd.list.JListCustom();
        inpJenis = new javax.swing.JComboBox();
        btnHapusBahan1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 249, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 10));
        jPanel1.setForeground(new java.awt.Color(59, 63, 74));

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(250, 22, 22));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Data Menu");

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
        lblId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idmenu.png"))); // NOI18N
        lblId.setText("ID Menu");

        inpId.setBackground(new java.awt.Color(231, 235, 239));
        inpId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpId.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblNama.setText("Nama Menu");

        inpNama.setBackground(new java.awt.Color(248, 249, 250));
        inpNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNama.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNama.setName("Nama Menu"); // NOI18N

        lblData1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-jenismenu.png"))); // NOI18N
        lblData1.setText("Jenis Menu");

        lblData2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga.png"))); // NOI18N
        lblData2.setText("Harga");

        inpHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpHarga.setName("Harga"); // NOI18N
        inpHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpHargaKeyTyped(evt);
            }
        });

        lblData3.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idbahan.png"))); // NOI18N
        lblData3.setText("Bahan Menu");
        lblData3.setName("Bahan Menu"); // NOI18N

        btnTambahBahan.setBackground(new java.awt.Color(154, 156, 172));
        btnTambahBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahBahan.setText("Tambah");
        btnTambahBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahBahanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahBahanMouseExited(evt);
            }
        });
        btnTambahBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBahanActionPerformed(evt);
            }
        });

        btnEditBahan.setBackground(new java.awt.Color(154, 156, 172));
        btnEditBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnEditBahan.setText("Edit");
        btnEditBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditBahanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditBahanMouseExited(evt);
            }
        });
        btnEditBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBahanActionPerformed(evt);
            }
        });

        inpBahan.setBackground(new java.awt.Color(248, 249, 250));
        inpBahan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpBahan.setName("Data Bahan"); // NOI18N
        jScrollPane2.setViewportView(inpBahan);

        inpJenis.setBackground(new java.awt.Color(248, 249, 250));
        inpJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Minuman", "Makanan", "Original Coffee", "Falvoured Coffee", "Snack" }));
        inpJenis.setName("Jenis Menu"); // NOI18N

        btnHapusBahan1.setBackground(new java.awt.Color(154, 156, 172));
        btnHapusBahan1.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusBahan1.setText("Hapus");
        btnHapusBahan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHapusBahan1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHapusBahan1MouseExited(evt);
            }
        });
        btnHapusBahan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBahan1ActionPerformed(evt);
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
                        .addComponent(inpNama))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus))
                    .addComponent(lineHorBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                    .addComponent(lineHorTop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblData1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblData3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(lblData2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inpHarga)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnTambahBahan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditBahan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapusBahan1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblData1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpJenis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblData2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblData3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahBahan)
                    .addComponent(btnEditBahan)
                    .addComponent(btnHapusBahan1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lineHorBot, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
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
    
    private void btnTambahBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBahanActionPerformed
        GetDataBahanMenu g = new GetDataBahanMenu(null, true);
        g.setVisible(true);
        
        if(g.isSelected()){
            this.inpBahan.addList(g.getSelectedData());
            this.mainList = this.inpBahan.getAllList();
            System.out.println(g.getBahanCode());
            this.updateListData();
        }
    }//GEN-LAST:event_btnTambahBahanActionPerformed

    private void btnTambahBahanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahBahanMouseEntered
        
    }//GEN-LAST:event_btnTambahBahanMouseEntered

    private void btnTambahBahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahBahanMouseExited
        
    }//GEN-LAST:event_btnTambahBahanMouseExited

    private void btnEditBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBahanActionPerformed
        // mendapatkan data id bahan dan quantity
        String selectedVal = this.inpBahan.getSelectedValue(),
               idBhn = selectedVal.substring(0, selectedVal.indexOf(" ")),
               quantity = selectedVal.substring(selectedVal.indexOf("|")+1, selectedVal.indexOf("."));
        
        // membuka dialog
        GetDataBahanMenu g = new GetDataBahanMenu(null, true, idBhn, quantity);
        g.setVisible(true);
        
        if(g.isSelected()){
            this.inpBahan.removeList(this.inpBahan.getSelectedValue());
            this.inpBahan.addList(g.getSelectedData());
            this.mainList = this.inpBahan.getAllList();
            System.out.println(g.getBahanCode());
            this.updateListData();
        }
    }//GEN-LAST:event_btnEditBahanActionPerformed

    private void btnEditBahanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditBahanMouseEntered
        
    }//GEN-LAST:event_btnEditBahanMouseEntered

    private void btnEditBahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditBahanMouseExited
        
    }//GEN-LAST:event_btnEditBahanMouseExited

    private void btnHapusBahan1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusBahan1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusBahan1MouseEntered

    private void btnHapusBahan1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusBahan1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusBahan1MouseExited

    private void btnHapusBahan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBahan1ActionPerformed
        this.inpBahan.removeList(this.inpBahan.getSelectedValue());
        this.mainList = this.inpBahan.getAllList();
        this.updateListData();
    }//GEN-LAST:event_btnHapusBahan1ActionPerformed

    private Text txt = new Text();
    private void inpHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpHargaKeyTyped
        this.txt.filterAngka(evt);
        this.txt.filterChar(evt);
    }//GEN-LAST:event_inpHargaKeyTyped

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
            java.util.logging.Logger.getLogger(UpdateDataMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            UpdateDataMenu dialog = new UpdateDataMenu(new javax.swing.JFrame(), true, 1, "BA003");
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
    private javax.swing.JButton btnEditBahan;
    private javax.swing.JToggleButton btnHapus;
    private javax.swing.JButton btnHapusBahan1;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahBahan;
    private haqiachd.list.JListCustom inpBahan;
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpId;
    private javax.swing.JComboBox inpJenis;
    private javax.swing.JTextField inpNama;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblData2;
    private javax.swing.JLabel lblData3;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
