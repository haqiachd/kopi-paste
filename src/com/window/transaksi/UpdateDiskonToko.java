package com.window.transaksi;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.manage.Validation;
import com.manage.Waktu;
import com.media.Gambar;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.BorderFactory;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDiskonToko extends javax.swing.JDialog {

    private final Database db = new Database();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    public static final int TAMBAH = 1, EDIT = 2;
    
    private final int kondisi;
    
    private String idSelected;

    public UpdateDiskonToko(java.awt.Frame parent, boolean modal, int kondisi, String idSelected) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        
        this.kondisi = kondisi;
        this.idSelected = idSelected;
    
        // set ui button
        this.btnSimpan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // set margin text field
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpMinHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpTtlDiskon.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        // pilih kondisi tambah atau edit
        switch(kondisi){
            case TAMBAH : 
                this.setTitle("Tambah Diskon");
                this.lblTitle.setText("Tambah Diskon");
                this.inpId.setText(this.createID());
                break;
            case EDIT : 
                this.setTitle("Edit Diskon");
                this.lblTitle.setText("Edit Diskon");
                this.inpId.setText(this.idSelected);
                this.showData();
                break;
        }

    }
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM diskon ORDER BY id_diskon DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_diskon");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }else{
                    nomor = "000";
                }
            }

            // membuat id baru
            return String.format("DS%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showData(){
        try{
            String sql = "SELECT * FROM diskon WHERE id_diskon = '" + this.idSelected + "'";
            this.db.res = this.db.stat.executeQuery(sql);
            
            while(this.db.res.next()){
                this.inpMinHarga.setText(Integer.toString(this.db.res.getInt("min_harga")));
                this.inpTtlDiskon.setText(Integer.toString(this.db.res.getInt("ttl_diskon")));
                this.inpTglMulai.setDate(this.db.res.getDate("tgl_mulai"));
                this.inpTglAKhir.setDate(this.db.res.getDate("tgl_akhir"));
            }
            
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
    }
    
    private boolean tambahData() throws SQLException{
        
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpMinHarga, this.inpTtlDiskon)){
            return false;
        }else if(!Validation.isEmptyJDate(this.inpTglMulai, this.inpTglAKhir)){
            return false;
        }
        
        // mendapatkan data dari diskon yang diinputkan
        String id = this.inpId.getText();
        int minHarga = Integer.parseInt(this.inpMinHarga.getText()),
            ttlDiskon = Integer.parseInt(this.inpTtlDiskon.getText());
        Date tglAwal = this.inpTglMulai.getDate(), 
             tglAkhir = this.inpTglAKhir.getDate();
        LocalDate lclAwal = LocalDate.of(tglAwal.getYear()+1900, tglAwal.getMonth()+1, tglAwal.getDate()),
                  lclAkhir = LocalDate.of(tglAkhir.getYear()+1900, tglAkhir.getMonth()+1, tglAkhir.getDate());
        
        // cek validasi data diskon
        if(ttlDiskon > minHarga){
            Message.showWarning(this, "Total diskon tidak boleh melebihi minimal harga!");
            return false;
        }else if(lclAwal.compareTo(lclAkhir) > 0){
            Message.showWarning(this, "Tanggal mulai tidak boleh lebih besar dari tanggal akhir!");
            return false;
        }
        System.out.println("data is valid");

        // menginputkan data diskon ke sql
        this.db.pst = this.db.conn.prepareStatement("INSERT INTO diskon VALUES(?, ?, ?, ?, ?)");
        this.db.pst.setString(1, id);
        this.db.pst.setInt(2, minHarga);
        this.db.pst.setInt(3, ttlDiskon);
        this.db.pst.setDate(4, new java.sql.Date(tglAwal.getTime()));
        this.db.pst.setDate(5, new java.sql.Date(tglAkhir.getTime()));

        // eksekusi query
        return this.db.pst.executeUpdate() > 0;
    }
    
    private boolean editData() throws SQLException{
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpMinHarga, this.inpTtlDiskon)){
            return false;
        }else if(!Validation.isEmptyJDate(this.inpTglMulai, this.inpTglAKhir)){
            return false;
        }
        
        // mendapatkan data dari diskon yang diinputkan
        int minHarga = Integer.parseInt(this.inpMinHarga.getText()),
            ttlDiskon = Integer.parseInt(this.inpTtlDiskon.getText());
        Date tglAwal = this.inpTglMulai.getDate(), 
             tglAkhir = this.inpTglAKhir.getDate();
        LocalDate lclAwal = LocalDate.of(tglAwal.getYear()+1900, tglAwal.getMonth()+1, tglAwal.getDate()),
                  lclAkhir = LocalDate.of(tglAkhir.getYear()+1900, tglAkhir.getMonth()+1, tglAkhir.getDate());
        
        // validasi data diskon
        if(ttlDiskon > minHarga){
            Message.showWarning(this, "Total diskon tidak boleh melebihi minimal harga!");
            return false;
        }else if(lclAwal.compareTo(lclAkhir) > 0){
            Message.showWarning(this, "Tanggal mulai tidak boleh lebih besar dari tanggal akhir!");
            return false;
        }

        // menginputkan data diskon ke mysql
        String sql = String.format(
                "UPDATE diskon "
              + "SET min_harga = '%d', ttl_diskon = '%d', tgl_mulai = '%s', tgl_akhir = '%s' "
              + "WHERE id_diskon = '%s'", 
                minHarga, ttlDiskon, waktu.dateToString(tglAwal), waktu.dateToString(tglAkhir), this.idSelected
        );
        System.out.println(sql);

        // eksekusi query
        return this.db.stat.executeUpdate(sql) > 0;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        inpId = new com.ui.RoundedTextField(15);
        lblNama = new javax.swing.JLabel();
        inpMinHarga = new com.ui.RoundedTextField(15);
        lblData1 = new javax.swing.JLabel();
        lblData2 = new javax.swing.JLabel();
        lblData4 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lineBottom = new javax.swing.JSeparator();
        inpTtlDiskon = new com.ui.RoundedTextField(15);
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JToggleButton();
        inpTglMulai = new com.toedter.calendar.JDateChooser();
        inpTglAKhir = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-id.png"))); // NOI18N
        lblId.setText("ID Diskon");

        inpId.setEditable(false);
        inpId.setBackground(new java.awt.Color(231, 235, 239));
        inpId.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpId.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblNama.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga.png"))); // NOI18N
        lblNama.setText("Minimal Harga");

        inpMinHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpMinHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpMinHarga.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpMinHarga.setName("Nama Menu"); // NOI18N
        inpMinHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpMinHargaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpMinHargaKeyTyped(evt);
            }
        });

        lblData1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga-bulan.png"))); // NOI18N
        lblData1.setText("Total Diskon");

        lblData2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-bulan.png"))); // NOI18N
        lblData2.setText("Tanggal Mulai");

        lblData4.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblData4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-shift.png"))); // NOI18N
        lblData4.setText("Tanggal Akhir");

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(250, 22, 22));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Update Diskon");

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        inpTtlDiskon.setBackground(new java.awt.Color(248, 249, 250));
        inpTtlDiskon.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpTtlDiskon.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpTtlDiskon.setName("Nama Menu"); // NOI18N
        inpTtlDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpTtlDiskonKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpTtlDiskonKeyTyped(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(41, 180, 50));
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

        inpTglMulai.setBackground(new java.awt.Color(248, 249, 250));
        inpTglMulai.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        inpTglAKhir.setBackground(new java.awt.Color(248, 249, 250));
        inpTglAKhir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(202, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpMinHarga))
                            .addComponent(lineBottom)
                            .addComponent(lineTop)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblData1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpTtlDiskon))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblData2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpTglMulai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblData4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpTglAKhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpMinHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblData1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpTtlDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpTglMulai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblData2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblData4, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpTglAKhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inpMinHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpMinHargaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpTtlDiskon.requestFocus();
        }
    }//GEN-LAST:event_inpMinHargaKeyReleased

    private void inpTtlDiskonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpTtlDiskonKeyReleased
        
    }//GEN-LAST:event_inpTtlDiskonKeyReleased

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        this.btnSimpan.setIcon(Gambar.getIcon("ic-data-simpan-entered.png"));
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        this.btnSimpan.setIcon(Gambar.getIcon("ic-data-simpan.png"));
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed

        try{
            switch(this.kondisi){
                case UpdateDiskonToko.TAMBAH : 
                    if(this.tambahData()){
                      Message.showInformation(this, "Diskon berhasil ditambahkan!");
                      this.dispose();
                    }
                    break;
                case UpdateDiskonToko.EDIT : 
                    if(this.editData()){
                      Message.showInformation(this, "Diskon berhasil diupdate!");
                      this.dispose();
                    }
                    break;
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-batal-entered.png"));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-batal.png"));
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void inpMinHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpMinHargaKeyTyped
        this.text.decimalOnly(evt);
    }//GEN-LAST:event_inpMinHargaKeyTyped

    private void inpTtlDiskonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpTtlDiskonKeyTyped
        this.text.decimalOnly(evt);
    }//GEN-LAST:event_inpTtlDiskonKeyTyped

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
            java.util.logging.Logger.getLogger(UpdateDiskonToko.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UpdateDiskonToko dialog = new UpdateDiskonToko(new javax.swing.JFrame(), true, 1, "DS001");
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
    private javax.swing.JToggleButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField inpId;
    private javax.swing.JTextField inpMinHarga;
    private com.toedter.calendar.JDateChooser inpTglAKhir;
    private com.toedter.calendar.JDateChooser inpTglMulai;
    private javax.swing.JTextField inpTtlDiskon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblData1;
    private javax.swing.JLabel lblData2;
    private javax.swing.JLabel lblData4;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    // End of variables declaration//GEN-END:variables
}
