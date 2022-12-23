package com.window.dialog;

import com.koneksi.Dbase;
import com.koneksi.Koneksi;
import com.manage.Message;
import com.manage.Text;
import com.manage.Validation;
import com.media.Audio;
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
public class UpdateDataBahan extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected;
    
    private final Dbase db = new Dbase();
    
    private final PopUpBackground win = new PopUpBackground();
    
    /**
     * Creates new form TambahDataBahan
     * @param parent
     * @param modal
     * @param kondisi
     * @param id
     */
    public UpdateDataBahan(java.awt.Frame parent, boolean modal, int kondisi, String id) {
        super(parent, modal);
        this.setUndecorated(true);
        win.setVisible(true);
        initComponents();
        
        this.setLocationRelativeTo(null);
        this.inpId.setEditable(false);
        this.btnSimpan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpStok.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));   
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        this.idSelected = id;
        this.kondisi = kondisi;
        
        switch(kondisi){
            case TAMBAH : 
                this.lblTitle.setText("Tambah Data Bahan");
                this.inpId.setText(this.createID());
                break;
            case EDIT : 
                this.lblTitle.setText("Edit Data Bahan");
                this.inpId.setText(this.idSelected);
                this.showData();
                break;
        }
    }

    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM bahan ORDER BY id_bahan DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_bahan");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }
            }
            // membuat id baru
            return String.format("BA%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showData(){
//        this.idSelected = this.jTabel1.getValueAt(this.jTabel1.getSelectedRow(), 0).toString();
        try {
            String sql = "SELECT * FROM bahan WHERE id_bahan = '" + this.idSelected + "'";
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // mendapatkan data
            if(r.next()){
                String namaBahan = r.getString("nama_bahan"),
                       jenisBahan = r.getString("jenis"),
                       stok = r.getString("stok"),
                       harga = r.getString("harga"),
                       satuan = r.getString("satuan");
                
                // menampilkan data
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(namaBahan);
                this.inpJenis.setSelectedItem(jenisBahan);
                this.inpStok.setText(stok);
                this.inpHarga.setText(harga);
                this.inpSatuan.setSelectedItem(this.getNamaSatuan(satuan));
                
                if(this.inpSatuan.getSelectedIndex() == 1){
                    this.lblStokSatuan.setText("Gram");
                    this.lblHargaPerSatuan.setText("/ Kilogram");
                }else if(this.inpSatuan.getSelectedIndex() == 2){
                    this.lblStokSatuan.setText("Mililiter");
                    this.lblHargaPerSatuan.setText("/ Liter");            
                }
            }
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.win.dispose();
    }
    
    private String getNamaSatuan(String satuan){
       switch(satuan){
            case "gr" : 
                return "Gram";
            case "ml" : 
                return "MiliLiter";
            default : return "null satuan";
        }
    }
    
    private String getkodeSatuan(String satuan){
        switch(satuan){
            case "Gram" : return "gr";
            case "MiliLiter" : return "ml";
            default : return "null statuan";
        }
    }
    
    private void tambahData(){
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpNama)){
            return;
        }else if(!Validation.isEmptyComboBox(this.inpJenis, this.inpSatuan)){
            return;
        }else if(!Validation.isEmptyTextField(this.inpStok, this.inpHarga)){
            return;
        }else if(Integer.parseInt(this.inpHarga.getText()) <= 0){
            Message.showWarning(this, "Harga tidak boleh 0");
            return;
        }
        
        // mendapatkan data dari textfield
        String idBahan = this.inpId.getText(),
        namaBahan = this.inpNama.getText(),
        jenisBahan = this.inpJenis.getSelectedItem().toString(),
        stok = this.inpStok.getText(),
        satuan = this.inpSatuan.getSelectedItem().toString(),
        harga = this.inpHarga.getText();

        try{
            String sql = "INSERT INTO bahan VALUES (?, ?, ?, ?, ?, ?)";
            // membuat koneksi ke database
            Connection conn = (Connection) Koneksi.configDB();
            // menyiapkan query
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idBahan);
            pst.setString(2, namaBahan);
            pst.setString(3, jenisBahan);
            pst.setInt(4, Integer.parseInt(stok));
            pst.setString(5, this.getkodeSatuan(satuan));
            pst.setInt(6, Integer.parseInt(harga));

            // eksekusi query
            int result = pst.executeUpdate();
            if(result > 0){
                Audio.play(Audio.SOUND_INFO);
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                conn.close();
                dispose();
            }
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private void editData(){
        try{
            // validasi data kosong atau tidak
            if(!Validation.isEmptyTextField(this.inpNama)){
                return;
            }else if(!Validation.isEmptyComboBox(this.inpJenis, this.inpSatuan)){
                return;
            }else if(!Validation.isEmptyTextField(this.inpStok, this.inpHarga)){
                return;
            }else if(Integer.parseInt(this.inpHarga.getText()) <= 0){
                Message.showWarning(this, "Harga tidak boleh 0");
                return;
            }
        
            // mendapatkan input dari textfield
            String idBahan = this.inpId.getText(),
                   namaBahan = this.inpNama.getText(),
                   jenisBahan = this.inpJenis.getSelectedItem().toString(),
                   stok = this.inpStok.getText(),
                   harga = this.inpHarga.getText(),
                   satuan = this.getkodeSatuan(this.inpSatuan.getSelectedItem().toString()),
                    
                   // membuat query sql
                   sql = String.format("UPDATE bahan "
                           + "SET nama_bahan = '%s', jenis = '%s', harga = '%s', stok = '%s', satuan = '%s'"
                           + "WHERE id_bahan = '%s'", namaBahan, jenisBahan, harga, stok, satuan, idBahan
                   );
            
            System.out.println(sql);
            
                // membuat koneksi ke database
                Connection koneksi = (Connection) Koneksi.configDB();
                Statement stat = koneksi.createStatement();
                // eksekusi query
                int result = stat.executeUpdate(sql);
                
                // jika query berhasil dieksekusi
                if(result > 0){
                    Audio.play(Audio.SOUND_INFO);
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                    // refresh data
                    this.showData();
                    stat.close();
                    koneksi.close();
                    this.dispose();
                }else{
                    Audio.play(Audio.SOUND_WARNING);
                    JOptionPane.showMessageDialog(this, "Data gagal diupdate!");
                }
                koneksi.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
            ex.printStackTrace();
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
        inpId = new com.ui.RoundedTextField(15);
        lblId = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        inpNama = new com.ui.RoundedTextField(15);
        lblJenis = new javax.swing.JLabel();
        lblSatuan = new javax.swing.JLabel();
        lblStok = new javax.swing.JLabel();
        inpStok = new com.ui.RoundedTextField(15);
        lblHarga = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        inpJenis = new javax.swing.JComboBox();
        inpSatuan = new javax.swing.JComboBox();
        lblStokSatuan = new javax.swing.JLabel();
        lblHargaPerSatuan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(248, 249, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 10));
        jPanel1.setForeground(new java.awt.Color(59, 63, 74));

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(250, 22, 22));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Data Bahan");

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

        inpId.setBackground(new java.awt.Color(231, 235, 239));
        inpId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpId.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lblId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idbahan.png"))); // NOI18N
        lblId.setText("ID Bahan");

        lblNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idmenu.png"))); // NOI18N
        lblNama.setText("Nama Bahan");

        inpNama.setBackground(new java.awt.Color(248, 249, 250));
        inpNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpNama.setName("Nama Bahan"); // NOI18N

        lblJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-jenismenu.png"))); // NOI18N
        lblJenis.setText("Jenis Bahan");

        lblSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblSatuan.setText("Satuan Bahan");

        lblStok.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-stock.png"))); // NOI18N
        lblStok.setText("Stok");

        inpStok.setBackground(new java.awt.Color(248, 249, 250));
        inpStok.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpStok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpStok.setName("Stok"); // NOI18N
        inpStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpStokKeyTyped(evt);
            }
        });

        lblHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga.png"))); // NOI18N
        lblHarga.setText("Harga");

        inpHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        inpHarga.setName("Harga"); // NOI18N
        inpHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpHargaKeyTyped(evt);
            }
        });

        inpJenis.setBackground(new java.awt.Color(248, 249, 250));
        inpJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Hewani", "Nabati", "Coffee", "Perasa", "Cairan" }));
        inpJenis.setName("Jenis Bahan"); // NOI18N

        inpSatuan.setBackground(new java.awt.Color(248, 249, 250));
        inpSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Gram", "MiliLiter" }));
        inpSatuan.setName("Satuan Bahan"); // NOI18N
        inpSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpSatuanActionPerformed(evt);
            }
        });

        lblStokSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblStokSatuan.setText(" ");

        lblHargaPerSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblHargaPerSatuan.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus))
                            .addComponent(lineHorTop, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                            .addComponent(lineHorBot)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpNama, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHargaPerSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblStok, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpStok, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStokSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpSatuan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineHorTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpId)
                    .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpNama)
                    .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblJenis, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpJenis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpSatuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpStok)
                    .addComponent(lblStok, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblStokSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpHarga)
                    .addComponent(lblHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(lblHargaPerSatuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
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

    private void inpSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpSatuanActionPerformed
        if(this.inpSatuan.getSelectedIndex() == 1){
            this.lblStokSatuan.setText("Gram");
            this.lblHargaPerSatuan.setText("/ Kilogram");
        }else if(this.inpSatuan.getSelectedIndex() == 2){
            this.lblStokSatuan.setText("Mililiter");
            this.lblHargaPerSatuan.setText("/ Liter");            
        }else{
            this.lblStokSatuan.setText("");
            this.lblHargaPerSatuan.setText("");      
        }
    }//GEN-LAST:event_inpSatuanActionPerformed

    private Text txt = new Text();
    private void inpStokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpStokKeyTyped
        this.txt.filterAngka(evt);
        this.txt.filterChar(evt);
    }//GEN-LAST:event_inpStokKeyTyped

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
            java.util.logging.Logger.getLogger(UpdateDataBahan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            UpdateDataBahan dialog = new UpdateDataBahan(new javax.swing.JFrame(), true, 2, "BA003");
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
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpId;
    private javax.swing.JComboBox inpJenis;
    private javax.swing.JTextField inpNama;
    private javax.swing.JComboBox inpSatuan;
    private javax.swing.JTextField inpStok;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblHargaPerSatuan;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblJenis;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblSatuan;
    private javax.swing.JLabel lblStok;
    private javax.swing.JLabel lblStokSatuan;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
