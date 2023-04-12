package com.window.update;

import com.barcodelib.barcode.Linear;
import com.onbarcode.barcode.EAN13;
import com.onbarcode.barcode.IBarcode;
import com.koneksi.Database;
import com.manage.Bahan;
import com.manage.Message;
import com.manage.Text;
import com.manage.Triggers;
import com.manage.Validation;
import com.media.Audio;
import com.media.Gambar;
import com.onbarcode.barcode.EAN13;
import com.onbarcode.barcode.IBarcode;
import com.window.dialog.PopUpBackground;
import java.awt.Font;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.event.KeyEvent;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDataBahan extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected;
    
    private final Database db = new Database();
    
    private final Bahan ba = new Bahan();
    
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
        
        // set margin
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
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
        this.cetak2();
//        
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
            // membuat query
            String sql = "SELECT * FROM bahan WHERE id_bahan = '" + this.idSelected + "'";
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // mendapatkan data
            if(this.db.res.next()){
                String namaBahan = this.db.res.getString("nama_bahan"),
                       jenisBahan = this.db.res.getString("jenis"),
                       satuan = this.db.res.getString("satuan");
                
                // menampilkan data
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(namaBahan);
                this.inpJenis.setSelectedItem(jenisBahan);
                this.inpSatuan.setSelectedItem(this.ba.getNamaSatuan(satuan));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.win.dispose();
    } 
    
    private void tambahData(){
        // validasi data kosong atau tidak
        if(!Validation.isEmptyTextField(this.inpNama)){
            return;
        }else if(!Validation.isEmptyComboBox(this.inpJenis, this.inpSatuan)){
            return;
        }
        
        // mendapatkan data dari textfield
        String idBahan = this.inpId.getText(),
        namaBahan = this.inpNama.getText(),
        jenisBahan = this.inpJenis.getSelectedItem().toString(),
        satuan = this.inpSatuan.getSelectedItem().toString();

        try{
            // membuat query
            String sql = "INSERT INTO bahan VALUES (?, ?, ?, ?)";
            
            // menyiapkan query
            this.db.pst = this.db.conn.prepareStatement(sql);
            
            // menambahkan data bahan ke query
            this.db.pst.setString(1, idBahan);
            this.db.pst.setString(2, namaBahan);
            this.db.pst.setString(3, jenisBahan);
            this.db.pst.setString(4, this.ba.getkodeSatuan(satuan));

            System.out.println(this.db.pst.toString());
            // eksekusi query
            int result = this.db.pst.executeUpdate();
            if(result > 0){
                this.generate(idBahan);
                //
//                this.cetak2();
                Message.showInformation(this, "Data berhasil ditambahkan!");
                dispose();
            }
        }catch(Exception ex){
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
            }
        
            // mendapatkan input dari textfield
            String idBahan = this.inpId.getText(),
                   namaBahan = this.inpNama.getText(),
                   jenisBahan = this.inpJenis.getSelectedItem().toString(),
                   satuan = this.ba.getkodeSatuan(this.inpSatuan.getSelectedItem().toString()),
                    
                   // membuat query sql
                   sql = String.format("UPDATE bahan "
                           + "SET nama_bahan = '%s', jenis = '%s', satuan = '%s'"
                           + "WHERE id_bahan = '%s'", namaBahan, jenisBahan, satuan, idBahan
                   );
            
            System.out.println(sql);
            
                // eksekusi query
                int result = this.db.stat.executeUpdate(sql);
                
                // jika query berhasil dieksekusi
                if(result > 0){
                    Audio.play(Audio.SOUND_INFO);
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                    // refresh data
                    this.showData();
                    this.dispose();
                    new Triggers().updateBahan();
                }else{
                    Audio.play(Audio.SOUND_WARNING);
                    JOptionPane.showMessageDialog(this, "Data gagal diupdate!");
                }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void generate(String kode) throws Exception{
        EAN13 barcode = new EAN13();
        barcode.setData(kode);
        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(75f);
        
        barcode.setLeftMargin(0f);
        barcode.setRightMargin(0f);
        barcode.setTopMargin(0f);
        barcode.setBottomMargin(0f);
        
        barcode.setResolution(72);
        
        barcode.setShowText(true);
        barcode.setTextFont(new Font("Arial", 0, 12));
        
        barcode.setTextMargin(6);
        barcode.setRotate(IBarcode.ROTATE_0);
        barcode.drawBarcode("src\\resources\\image\\barcode\\" + kode + ".gif");
        
                
    }

    public void encodeBarcode() {

        EAN13 objEan = new EAN13();
        objEan.setData(this.lblBarcode.getText().toString());
        objEan.setUom(IBarcode.UOM_PIXEL);
        objEan.setX(3f);
        objEan.setY(175f);

        objEan.setLeftMargin(0f);
        objEan.setRightMargin(0f);
        objEan.setTopMargin(0f);
        objEan.setBottomMargin(0f);
        objEan.setResolution(72);
        objEan.setShowText(true);
        objEan.setTextMargin(6);
        objEan.setRotate(IBarcode.ROTATE_0);
        try {
            objEan.drawBarcode("C://drivers//" + this.inpId.getText().toString() + ".png");
            JOptionPane.showMessageDialog(this, "BARCODE BERHASIL.....");

        } catch (Exception ex) {
        }
    }

    private void setUkuranBarCode(EAN13 mBarcode, float panjangBarcode, float tinggiBarcode) {
        mBarcode.setX(panjangBarcode);
        mBarcode.setY(tinggiBarcode);

    }

    private void cetak2() {
        try {
            Linear barcode = new Linear();
            barcode.setType(Linear.CODE128B);
            barcode.setData(this.inpId.getText());
            barcode.setI(12.0f);
            barcode.renderBarcode("src\\resources\\image\\barcode\\" + this.inpId.getText() + ".png");
            this.lblBarcode.setIcon(Gambar.getBarcode(this.inpId.getText() + ".png"));
//            this.lblBarcode.setIcon(new ImageIcon(new File("D:").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getImage() {
        File fileBarcode = new File("src\\resources\\image\\barcode\\");
        File[] listFileBarCode = fileBarcode.listFiles();
        List<File> listBarcode = new ArrayList();

        for (File fileBarc : listFileBarCode) {
            if (fileBarc.getName().contains(".png")) {
                listBarcode.add(new File(fileBarc.getAbsolutePath()));

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
        inpId = new com.ui.RoundedTextField(15);
        lblId = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        inpNama = new com.ui.RoundedTextField(15);
        lblJenis = new javax.swing.JLabel();
        lblSatuan = new javax.swing.JLabel();
        inpJenis = new javax.swing.JComboBox();
        inpSatuan = new javax.swing.JComboBox();
        lblSatuan1 = new javax.swing.JLabel();
        lblBarcode = new javax.swing.JLabel();

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
        inpNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpNamaKeyReleased(evt);
            }
        });

        lblJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-jenismenu.png"))); // NOI18N
        lblJenis.setText("Jenis Bahan");

        lblSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblSatuan.setText("Satuan Bahan");

        inpJenis.setBackground(new java.awt.Color(248, 249, 250));
        inpJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Hewani", "Nabati", "Coffee", "Perasa", "Cairan", "Bahan Jadi" }));
        inpJenis.setName("Jenis Bahan"); // NOI18N

        inpSatuan.setBackground(new java.awt.Color(248, 249, 250));
        inpSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpSatuan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Kilogram", "Liter", "Dus", "Renceng", "Lusin", "Botol", "Galon" }));
        inpSatuan.setName("Satuan Bahan"); // NOI18N
        inpSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpSatuanActionPerformed(evt);
            }
        });

        lblSatuan1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblSatuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblSatuan1.setText("Barcode");

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
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpSatuan, 0, 293, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSatuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSatuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 43, Short.MAX_VALUE))
                    .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    }//GEN-LAST:event_inpSatuanActionPerformed

    private Text txt = new Text();
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void inpNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNamaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            //            this.lblBarcode.setIcon((Icon) new File("src\\resources\\image\\barcode\\" + this.inpId + ".png"));
                //            lblSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
                lblBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/barcode/" + this.inpId + ".png")));
                //            this.lblBarcode.setIcon();
            }
    }//GEN-LAST:event_inpNamaKeyReleased

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
            UpdateDataBahan dialog = new UpdateDataBahan(new javax.swing.JFrame(), true, 1, "BA003");
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
    private javax.swing.JTextField inpId;
    private javax.swing.JComboBox inpJenis;
    private javax.swing.JTextField inpNama;
    private javax.swing.JComboBox inpSatuan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblJenis;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblSatuan;
    private javax.swing.JLabel lblSatuan1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
