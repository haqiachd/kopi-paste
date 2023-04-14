package com.window.update;

import com.koneksi.Database;
import com.manage.Barcode;
import com.manage.Message;
import com.manage.Text;
import com.manage.Triggers;
import com.manage.Validation;
import com.manage.Waktu;
import com.media.Gambar;
import java.awt.event.KeyEvent;
import com.window.dialog.PopUpBackground;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDataMenu extends javax.swing.JDialog {

    public final static int TAMBAH = 1, EDIT = 2;
    
    private int kondisi;
    
    private String idSelected, idBarcode = "";
    
    private boolean isValidBarcode = false;
    
    private final Database db = new Database();
    
    private final PopUpBackground win = new PopUpBackground();
    
    private final Barcode barcode = new Barcode();
    
    private final Text txt = new Text();
    
    private final Waktu waktu = new Waktu();
    
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
        this.btnBatal.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // set margin text field
        this.inpId.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNama.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpKodeBarcode.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
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
        try{
            // menyiapkan query untuk mendapatkan data dari menu
            String sql = "SELECT nama_menu, jenis, jenis, harga, id_barcode FROM menu "
                       + "WHERE id_menu = '"+this.idSelected+"'";
            System.out.println(sql);
            
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // menampilkan data ke window
            if(this.db.res.next()){
                this.inpId.setText(this.idSelected);
                this.inpNama.setText(this.db.res.getString("nama_menu"));
                this.inpJenis.setSelectedItem(this.db.res.getString("jenis"));
                this.inpHarga.setText(this.db.res.getString("harga"));
                
                // jika pada menu terdapat barcode
                if(!this.barcode.isNullBarcode(this.idSelected)){
                    // medapatkan kode barcode
                    this.idBarcode = this.db.res.getString("id_barcode");
                    this.inpKodeBarcode.setText(this.idBarcode);
                    // menampilkan barcode image
                    this.lblShowBarcode.setText("");
                    this.lblShowBarcode.setIcon(this.barcode.getBarcodeImage(this.inpId.getText(), this.lblShowBarcode.getWidth() - 2, this.lblShowBarcode.getHeight() - 2));
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
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
        }
        
        // mendapatkan data
        String id = this.inpId.getText(),
               nama = this.inpNama.getText(),
               jenis = this.inpJenis.getSelectedItem().toString(),
               harga = this.inpHarga.getText();
        
        // menyiapkan query
        String sql = "INSERT INTO menu VALUES(?, ?, ?, ?, ?, ?)";
        this.db.pst = this.db.conn.prepareStatement(sql);
        this.db.pst.setString(1, id);
        this.db.pst.setString(2, nama);
        this.db.pst.setString(3, jenis);
        this.db.pst.setString(4, harga);
        
        // jika barcode dimasukan / valid
        if(this.isValidBarcode){
            this.db.pst.setString(5, this.inpKodeBarcode.getText());
            this.db.pst.setBlob(6, this.barcode.barcodeToBlob(id));            
        }else{
            // jika barcode tidak dimasukan
            this.db.pst.setString(5, null);
            this.db.pst.setString(6, null);
            this.barcode.deleteBarcode(id);
        }
        
        // eksekusi query
        return this.db.pst.executeUpdate() > 0;
    }
    
    private boolean editDataMenuOld() throws SQLException{
        
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
        }
        
        // mendapatkan data
        String id = this.inpId.getText(),
               nama = this.inpNama.getText(),
               jenis = this.inpJenis.getSelectedItem().toString(),
               harga = this.inpHarga.getText(),
               kodeBarcode = this.inpKodeBarcode.getText(),
               sql = String.format(
                       "UPDATE menu "
                     + "SET nama_menu = '%s', jenis = '%s', harga = '%s', id_barcode = '%s', img_barcode = '%s'"
                     + "WHERE id_menu = '"+this.idSelected+"'", nama, jenis, harga, kodeBarcode, this.barcode.barcodeToBlob(id), id);
        
        int result = this.db.stat.executeUpdate(sql);
        return result > 0;
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
        }
        
        // mendapatkan data
        String id = this.inpId.getText(),
               nama = this.inpNama.getText(),
               jenis = this.inpJenis.getSelectedItem().toString(),
               harga = this.inpHarga.getText(),
               kodeBarcode = this.inpKodeBarcode.getText(),
               sql = "UPDATE menu "
                     + "SET nama_menu = ?, jenis = ?, harga = ?, id_barcode = ?, img_barcode = ?"
                     + "WHERE id_menu = ?";
        
        this.db.pst = this.db.conn.prepareStatement(sql);
        this.db.pst.setString(1, nama);
        this.db.pst.setString(2, jenis);
        this.db.pst.setString(3, harga);
        
        // jika barcode dimasukan / valid
        if(this.isValidBarcode){
            this.db.pst.setString(4, kodeBarcode);
            this.db.pst.setBlob(5, this.barcode.barcodeToBlob(id));            
        }else{
            // jika barcode tidak dimasukan
            this.db.pst.setString(4, null);
            this.db.pst.setString(5, null);
            this.barcode.deleteBarcode(id);
        }
        this.db.pst.setString(6, id);
        
        return this.db.pst.executeUpdate() > 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lineHorTop = new javax.swing.JSeparator();
        lineHorBot = new javax.swing.JSeparator();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JToggleButton();
        lblId = new javax.swing.JLabel();
        inpId = new com.ui.RoundedTextField(15);
        lblNama = new javax.swing.JLabel();
        inpNama = new com.ui.RoundedTextField(15);
        lblJenis = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        inpJenis = new javax.swing.JComboBox();
        lblBarcode = new javax.swing.JLabel();
        lblShowBarcode = new javax.swing.JLabel();
        lblKodeBarcode = new javax.swing.JLabel();
        inpKodeBarcode = new com.ui.RoundedTextField(15);

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

        btnBatal.setBackground(new java.awt.Color(220, 41, 41));
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-batal.png"))); // NOI18N
        btnBatal.setText("Batalkan");
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
        inpNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpNamaKeyReleased(evt);
            }
        });

        lblJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-jenismenu.png"))); // NOI18N
        lblJenis.setText("Jenis Menu");

        lblHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga.png"))); // NOI18N
        lblHarga.setText("Harga");

        inpHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpHarga.setName("Harga"); // NOI18N
        inpHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpHargaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpHargaKeyTyped(evt);
            }
        });

        inpJenis.setBackground(new java.awt.Color(248, 249, 250));
        inpJenis.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Minuman", "Makanan", "Original Coffee", "Falvoured Coffee", "Snack" }));
        inpJenis.setName("Jenis Menu"); // NOI18N
        inpJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpJenisKeyReleased(evt);
            }
        });

        lblBarcode.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-barcode.png"))); // NOI18N
        lblBarcode.setText("Barcode");
        lblBarcode.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblShowBarcode.setFont(new java.awt.Font("Ebrima", 1, 20)); // NOI18N
        lblShowBarcode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShowBarcode.setText("Tidak ada barcode");
        lblShowBarcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblKodeBarcode.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblKodeBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-barcode-kode.png"))); // NOI18N
        lblKodeBarcode.setText("Kode Barcode");

        inpKodeBarcode.setBackground(new java.awt.Color(248, 249, 250));
        inpKodeBarcode.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpKodeBarcode.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpKodeBarcode.setName("Harga"); // NOI18N
        inpKodeBarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpKodeBarcodeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpKodeBarcodeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpKodeBarcodeKeyTyped(evt);
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
                        .addComponent(btnBatal))
                    .addComponent(lineHorBot, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(lineHorTop, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpJenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblKodeBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblShowBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpHarga)
                            .addComponent(inpKodeBarcode))))
                .addContainerGap(19, Short.MAX_VALUE))
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
                    .addComponent(lblJenis, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(inpJenis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblKodeBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpKodeBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblShowBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lineHorBot, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        try{
            switch(this.kondisi){
                case TAMBAH : 
                    if(this.tambahDataMenu()){
                        Message.showInformation(this, "Data berhasil ditambahkan!");
                        this.dispose();                        
                    }
                    break;
                case EDIT : 
                    if(this.editDataMenu()){
                        Message.showInformation(this, "Data berhasil diedit!");
                        this.dispose();
                        new Triggers().updateMenu();                        
                    }
                    break;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseEntered
        this.btnBatal.setIcon(Gambar.getIcon("ic-data-batal-entered.png"));
    }//GEN-LAST:event_btnBatalMouseEntered

    private void btnBatalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBatalMouseExited
        this.btnBatal.setIcon(Gambar.getIcon("ic-data-batal.png"));
    }//GEN-LAST:event_btnBatalMouseExited

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.win.dispose();
        this.dispose();
        
        switch(this.kondisi){
            case UpdateDataMenu.TAMBAH : 
                System.out.println("HAPUS TAMBAH");
                this.barcode.deleteBarcode(this.inpId.getText());
                break;
            case UpdateDataMenu.EDIT : 
                if(!this.idBarcode.equals(this.inpKodeBarcode.getText())){
                    System.out.println("HAPUS EDIT");
                    this.barcode.deleteBarcode(this.inpId.getText());
                }
                break;
        }
    }//GEN-LAST:event_btnBatalActionPerformed
    
    
    private void inpHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpHargaKeyTyped
        this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpHargaKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
        this.barcode.close();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
        this.barcode.close();
    }//GEN-LAST:event_formWindowClosing

    private void inpHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpHargaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpKodeBarcode.requestFocus();
        }
    }//GEN-LAST:event_inpHargaKeyReleased

    private void inpNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpNamaKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpJenis.requestFocus();
        }
    }//GEN-LAST:event_inpNamaKeyReleased

    private void inpJenisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJenisKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.inpHarga.requestFocus();
        }
    }//GEN-LAST:event_inpJenisKeyReleased

    private void inpKodeBarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKodeBarcodeKeyReleased
        // cek barcode kosong atau panjangnya kurang dari 5
        if(this.inpKodeBarcode.getText().equals("")){
            this.isValidBarcode = false;
            this.lblShowBarcode.setIcon(null);
            this.lblShowBarcode.setText("Tidak ada barcode");
            return;
        }else if(this.inpKodeBarcode.getText().length() < 5){
            this.isValidBarcode = false;
            this.lblShowBarcode.setIcon(null);
            this.lblShowBarcode.setText("Barcode tidak valid");
            return;
        }
        
        // cek barcode sudah terpakai atau belum
        if (!this.barcode.isExistMysql(this.inpKodeBarcode.getText())) {
            // mengenerate barcode dan menampilkanya
            this.isValidBarcode = true;
            this.barcode.generate(this.inpKodeBarcode.getText(), this.inpId.getText());
            this.lblShowBarcode.setText("");
            this.lblShowBarcode.setIcon(this.barcode.getBarcodeImage(this.inpId.getText(), this.lblShowBarcode.getWidth() - 2, this.lblShowBarcode.getHeight() - 2));
            new Waktu().delay(100);
        }else {
            // jika barcode sudah ada
            Message.showWarning(this, "Kode barcode tersebut sudah terpakai!");
            this.isValidBarcode = false;
            this.lblShowBarcode.setIcon(null);
            this.lblShowBarcode.setText("Tidak ada barcode");
            this.inpKodeBarcode.setText("");
        }
    }//GEN-LAST:event_inpKodeBarcodeKeyReleased

    private void inpKodeBarcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKodeBarcodeKeyTyped
        this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpKodeBarcodeKeyTyped

    private void inpKodeBarcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpKodeBarcodeKeyPressed
        if(this.inpKodeBarcode.getText().length() > 12){
            // set barcode kosong
            this.isValidBarcode = false;
            this.inpKodeBarcode.setText("");
            this.lblShowBarcode.setIcon(null);
            this.lblShowBarcode.setText("Tidak ada barcode");
        }
    }//GEN-LAST:event_inpKodeBarcodeKeyPressed

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
    private javax.swing.JToggleButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpId;
    private javax.swing.JComboBox inpJenis;
    private javax.swing.JTextField inpKodeBarcode;
    private javax.swing.JTextField inpNama;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBarcode;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblJenis;
    private javax.swing.JLabel lblKodeBarcode;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblShowBarcode;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JSeparator lineHorBot;
    private javax.swing.JSeparator lineHorTop;
    // End of variables declaration//GEN-END:variables
}
