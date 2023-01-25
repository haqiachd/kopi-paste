package com.window.transaksi;

import com.koneksi.Database;
import com.manage.Bahan;
import com.manage.Message;
import com.manage.Text;
import com.ui.UIManager;
import com.manage.User;
import com.manage.Waktu;
import com.media.Gambar;
import com.window.Dashboard;
import com.window.DataBahan;
import com.window.DataKaryawan;
import com.window.DataMenu;
import com.window.DataPembeli;
import com.window.DataSupplier;
import com.window.laporan.MenuLaporan;
import com.window.get.GetDataBahan;
import com.window.dialog.InfoApp;
import com.window.dialog.Pengaturan;
import com.window.dialog.UserProfile;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 * @since 03 December 2022
 */
public class MenuTransaksiBeli extends javax.swing.JFrame {
    
    private DefaultTableModel tblModel;
    
    private final Database db = new Database();
    
    private final Bahan ba = new Bahan();
    
    private final UIManager win = new UIManager();
    
    private final Text txt = new Text();
    
    private final Waktu waktu = new Waktu();

    private String idTransaksi, idBahan, namaBahan, jenisBahan, satuanBahan, idKaryawan, namaKaryawan;
    
    private int hargaBahan, ttlHargaBayar;
    
    private float jumlah;
    
    private boolean status, isEdit = false, isUpdateTr;
    
    public MenuTransaksiBeli() {
        initComponents();
        
        this.status = true;
        this.setTitle("Menu Transaksi Pembelian");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Gambar.getWindowIcon());
        
        // set hover
        this.lblNamaUser.setText(User.getNamaUser());
        this.win.btns = new JLabel[]{
            this.btnKaryawan, this.btnSupplier, this.btnPembeli, 
            this.btnDashboard, this.btnLaporan, this.btnBahan, this.btnLogout, this.btnMenu
        };
        this.win.hoverButton();
        
        // reset satuan
        this.inpStokSatuan.setText(" ");
        
        // set ui button
        this.btnBayar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnUpdateBahan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // id otomatis
        this.inpIdTransaksi.setText(this.createID());
        
        // update server time
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    System.out.println("update waktu is " + status);
                    while(status){
                        lblServerTime.setText("Server Time : " + waktu.getUpdateWaktu());
                        Thread.sleep(1);
                    }
                    System.out.println("update waktu is false");
                }catch(InterruptedException iex){
                    Message.showException(null, iex);
                }
            }
        }).start();
        
        // set model tabel ke default
        this.resetTabel();
        
        // setting desain tabel
        this.tabelTr.setRowHeight(29);
        this.tabelTr.getTableHeader().setBackground(new java.awt.Color(248,249,250));
        this.tabelTr.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        // set margin textfield
        this.inpIdBahan.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNamaBahan.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpJumlah.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        // hidden button
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        this.btnSupplier.setVisible(false);
        
        if(!User.isAdmin()){
            this.btnKaryawan.setVisible(false);
            this.btnLaporan.setVisible(false);
        }
        
        this.showListKaryawan();
        this.inpJumlah.requestFocus();
    }
    
    public MenuTransaksiBeli(String idTransaksi){
        this();
        
        // menampilkan data yang akan diupdate
        this.isUpdateTr = true;
        this.idTransaksi = idTransaksi;
        this.inpIdTransaksi.setText(this.idTransaksi);
        this.showUpdateTransaksi();
    }
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM transaksi_beli ORDER BY id_tr_beli DESC LIMIT 0,1", lastID, nomor = "00000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_tr_beli");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(4);
                }            
            }
            
            // membuat id baru
            return String.format("TRB%05d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showListKaryawan(){
        try{
            String sql = "SELECT ky.id_karyawan, ky.nama_karyawan, us.level "
                       + "FROM karyawan AS ky "
                       + "JOIN user AS us "
                       + "ON ky.id_karyawan = us.id_karyawan ";
            
            if(!User.isDeveloper()){
                sql += "WHERE us.level != 'DEVELOPER'";
            }
            
            this.db.res = this.db.stat.executeQuery(sql);
            
            while(this.db.res.next()){
                this.inpKaryawan.addItem(this.db.res.getString("ky.id_karyawan") + " | " + this.db.res.getString("ky.nama_karyawan"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    /**
     * Digunakan untuk menampilkan data bahan yang dipilih user
     */
    public void showBahanSelected(){
        
        try{
            // membuat query
            String sql = "SELECT * FROM bahan WHERE id_bahan = '"+this.idBahan+"'";
            
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // mendapatkan data bahan
                this.namaBahan = this.db.res.getString("nama_bahan");
                this.jenisBahan = this.db.res.getString("jenis");
                this.satuanBahan = this.db.res.getString("satuan");
                
                // menampilkan data bahan
                this.inpNamaBahan.setText(namaBahan);
                this.inpStokSatuan.setText(this.ba.getNamaSatuan(this.satuanBahan));
                // reset data 
                this.inpHarga.setText("");
                this.inpJumlah.setText("");
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private void showBahanFromTabel(){
        int row = this.tabelTr.getSelectedRow();
        
        // mendapatkan data bahan
        this.idBahan = this.tabelTr.getValueAt(row, 1).toString();
        this.namaBahan = this.tabelTr.getValueAt(row, 2).toString();
        this.hargaBahan = Integer.parseInt(this.txt.removeMoneyCase(this.tabelTr.getValueAt(row, 6).toString()));
        this.jumlah = Float.parseFloat(this.tabelTr.getValueAt(row, 4).toString());
        
        // menampilkan data bahan
        this.inpIdBahan.setText(this.idBahan);
        this.inpNamaBahan.setText(this.namaBahan);
        this.inpHarga.setText(Integer.toString(this.hargaBahan));
        this.inpJumlah.setText(Float.toString(this.jumlah));
        this.inpStokSatuan.setText(this.tabelTr.getValueAt(row, 5).toString());
    }
    
    private void updateTabel(){
        // set model tabel dengan model sebelumnya
        DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
        boolean dataBaru = true;
        
        // membaca baris pada tabel
        for (int i = 0; i < this.tblModel.getRowCount(); i++) {
            // cek apakah data bahan sudah ada didalam tabel atau tidak
            if (this.inpIdBahan.getText().equals(String.valueOf(this.tabelTr.getValueAt(i, 1))) && this.inpHarga.getText().equals(this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString()))) {
                // jika sudah ada maka data jumlah pada bahan akan diupdate 
                // dengan cara mendapatkan data jumlah baru dan ditambahkan dengan data jumlah yang lama
                this.jumlah += Float.parseFloat(String.valueOf(this.tabelTr.getValueAt(i, 4)));
            }
        }

        /**
         * Mengecek apakah data bahan yang dipilih sudah ada atau belum didalam tabel transaksi yang ada di window
         * Jika sudah ada maka data jumlah pada tabel transaksi akan diupdate dan tidak membuat baris baru pada tabel
         * Jika belum maka data nama bahan akan ditambahkan pada baris baru pada tabel
         */
        if (tabelTr.getRowCount() >= 1) {
            // membaca isi tabel transaksi
            for (int i = 0; i < model.getRowCount(); i++) {
                // mendapatkan data id bahan dari tabel
                String id = model.getValueAt(i, 1).toString(),
                       harga = this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString());

                // jika id bahan sudah ada didalam tabel, maka akan mengupdate data jumlah yang ada didalam tabel transaksi
                if (id.equalsIgnoreCase(this.idBahan) && harga.equalsIgnoreCase(Integer.toString(this.hargaBahan))) {
                    dataBaru = false;
                    
                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Float.toString(this.jumlah), i, 4);
                    model.setValueAt(txt.toMoneyCase(this.hargaBahan), i, 6);
//                    this.inpTotalHarga.setText(this.txt.toMoneyCase(""+ttlHargaBayar).substring(4));
                }
            }
        }
        
        // jika id bahan belum ada didalam tabel maka data bahan akan ditambahkan pada baris baru
        if (dataBaru) {
            // update total harga
            this.ttlHargaBayar += this.hargaBahan;
            System.out.println("plus");
            this.inpTotalHarga.setText(this.txt.toMoneyCase(""+ttlHargaBayar).substring(4));
            // menambahkan data ke tabel
            model.addRow(new Object[]{
                this.inpIdTransaksi.getText(),
                this.idBahan,
                this.namaBahan,
                this.jenisBahan,
                new Float(this.inpJumlah.getText()),
                this.ba.getNamaSatuan(this.satuanBahan),
                this.txt.toMoneyCase(this.hargaBahan),
            });
        }
        
        // merefresh update tabel
        tabelTr.setModel(model);
    }
    
    private void resetTabel(){
        // setting tabel model
        tblModel = new DefaultTableModel(
                new String[][]{}, // default valuenya kosong
                new String [] {
                    "ID Transaksi", "ID Bahan", "Nama Bahan", "Jenis Bahan", "Jumlah", "Satuan", "Harga Bahan"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        // set model tabel dengan model yang diatas
        this.tabelTr.setModel(tblModel);
        
        // set ukuran kolom
        TableColumnModel columnModel = tabelTr.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(110);
        columnModel.getColumn(0).setMaxWidth(110);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(1).setMaxWidth(110);
        columnModel.getColumn(2).setPreferredWidth(240);
        columnModel.getColumn(2).setMaxWidth(300);
        columnModel.getColumn(3).setPreferredWidth(220);
        columnModel.getColumn(3).setMaxWidth(220);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(4).setMaxWidth(80);
        columnModel.getColumn(5).setPreferredWidth(140);
        columnModel.getColumn(5).setMaxWidth(140);
//        columnModel.getColumn(6).setPreferredWidth(160);
//        columnModel.getColumn(6).setMaxWidth(160);
    }
    
    private void changeButton(){
        if(this.isEdit){
            this.btnUpdateBahan.setText("Edit");
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-edit.png"));
            this.btnUpdateBahan.setBackground(new Color(204,0,153));
        }else{
            this.btnUpdateBahan.setText("Tambah");
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-tambah.png"));
            this.btnUpdateBahan.setBackground(new Color(34,119,237));
        }
    }
    
    // reset saat menekan tombol transaksi
    private void resetTransaksi(){
        // reset textfield dan data
        this.resetTambah();
        this.inpTotalHarga.setText("");
        this.idBahan = null;
        this.namaBahan = null;
        this.jumlah = 0;
        this.hargaBahan = 0;
        this.ttlHargaBayar = 0;
        
        // reset tabel
        this.resetTabel();
        
        // reset button
        this.changeButton();
        
        // update id transaksi
        this.inpIdTransaksi.setText(this.createID());
    }
    
    // reset saat menambahkan 
    private void resetTambah(){
        this.inpStokSatuan.setText(" ");
        this.inpIdBahan.setText("");
        this.inpNamaBahan.setText("");
        this.inpHarga.setText("");
        this.inpJumlah.setText("");
        
        if(this.tabelTr.getSelectedRow() > -1){
            this.tabelTr.removeRowSelectionInterval(this.tabelTr.getSelectedRow(), this.tabelTr.getSelectedRow());
        }  
    }
    
    // untuk menambahkan data bahan ke dalam tabel
    private void tambahDataBahan(){
        this.jumlah = Float.parseFloat(this.inpJumlah.getText());
        // reset tabel dan textfield
        this.updateTabel();
        this.resetTambah();
    }
    
    private void eventUpdateBahan(){
        if (this.isEdit) {
            if (this.inpHarga.getText().isEmpty() || Integer.parseInt(this.inpHarga.getText()) < 0) {
                Message.showWarning(this, "Harga bahan harus lebih dari 0");
            } 
            // cek apakah jumlah kosong atau tidak
            else if (this.inpJumlah.getText().isEmpty() || Float.parseFloat(this.inpJumlah.getText()) <= 0.01) {
                Message.showWarning(this, "Jumlah bahan harus lebih dari 0.1 " + this.ba.getNamaSatuan(this.satuanBahan));
            }else{
                // mendapatkan harga bahan
                this.hargaBahan = Integer.parseInt(this.inpHarga.getText());
                // mengedit data bahan
                this.editDataBahan();                
            }
            
        } else {
            // cek apakah data bahan sudah dipilih
            if (this.inpIdBahan.getText().isEmpty()) {
                Message.showWarning(this, "Data bahan tidak boleh kosong!");
                return;
            }
            // cek apakah harga kosong atau tidak
            else if (this.inpHarga.getText().isEmpty() || Integer.parseInt(this.inpHarga.getText()) < 0) {
                Message.showWarning(this, "Harga bahan harus lebih dari 0");
                return;
            } 
            // cek apakah jumlah kosong atau tidak
            else if (this.inpJumlah.getText().isEmpty() || Float.parseFloat(this.inpJumlah.getText()) <= 0.01) {
                Message.showWarning(this, "Jumlah bahan harus lebih dari 0.1 " + this.ba.getNamaSatuan(this.satuanBahan));
                return;
            }
            
            // mendapatkan harga bahan
            this.hargaBahan = Integer.parseInt(this.inpHarga.getText());
            // menambahkan data bahan
            this.tambahDataBahan();
        }
        
    }
    
    // untuk mengedit data bahan yang ada didalam tabel
    private void editDataBahan(){
        try{
            int row = this.tabelTr.getSelectedRow();
                // mendapatkan jumlah menu yang baru
            float newJml = Float.parseFloat(this.inpJumlah.getText());
                // mendapatkan data harga dari menu    
            int harga = Integer.parseInt(this.inpHarga.getText()),
                // mendapatkan data total harga menu yang lama
                oldTotalHarga = Integer.parseInt(txt.removeMoneyCase(this.tabelTr.getValueAt(row, 6).toString()));
            
            if(newJml <= 0){
                Message.showInformation(this, "Jumlah stok harus lebih dari 0");
                return;
            }   
                // mengupdate total harga bayar
                this.ttlHargaBayar = this.ttlHargaBayar - oldTotalHarga;
                this.ttlHargaBayar = this.ttlHargaBayar + harga;

                // mengupdate data yang ada di textfield dan di tabel
                this.tabelTr.setValueAt(Float.toString(newJml), row, 4);
                this.tabelTr.setValueAt(this.txt.toMoneyCase(harga), row, 6);
                this.inpTotalHarga.setText(this.txt.toMoneyCase(""+ttlHargaBayar).substring(4));
            
                // reset data bahan
                this.resetTambah();
                
                this.isEdit = false;
                this.changeButton();
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Jumlah menu harus berupa Angka!");
        }
    }
    
    // untuk menghapus data bahan pada tabel
    private void hapusDataBahan() {
        if(this.tabelTr.getSelectedRow() > -1){
            // mendapatkan total harga bahan
            String hrg = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 6).toString().substring(4).replaceAll(",", "").replaceAll("\\.", "");
            // mengurangi total harga bayar dengan total harga bahan
            this.ttlHargaBayar = this.ttlHargaBayar - Integer.parseInt(hrg.substring(0, hrg.length()-2));
            // menampilkan ulang total harga bayar pada textfield
            this.inpTotalHarga.setText(txt.toMoneyCase(""+this.ttlHargaBayar).substring(4));
            
            // menghapus baris pada tabel
            DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
            int row = tabelTr.getSelectedRow();
            model.removeRow(row);            
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }
    
    private int getTotalJumlahBahan(){
        return this.tabelTr.getRowCount();
    }
    
    private int getTotalHarga(){
        int ttlHarga = 0;
        for(int i = 0; i < this.tabelTr.getRowCount(); i++){
            ttlHarga += Integer.parseInt(this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString()));
        }
        
        return ttlHarga;
    }
    
    private String getSatuanBahan(String idBahan){
        try{
            // membuat query
            String sql = "SELECT satuan FROM bahan WHERE id_bahan = '"+idBahan+"'";
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // mendapatkan data satuan
            if(this.db.res.next()){
                return this.db.res.getString("satuan");
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
    }
    
    private boolean transaksi(){
        try{
            // menyiapkan query
            String sql = "INSERT INTO transaksi_beli VALUES(?, ?, ?, ?, ?, ?)";
            this.idTransaksi = this.createID();
            
            // membuat preparestatemnt
            this.db.pst = this.db.conn.prepareCall(sql);
            
            // menambahkan data transaksi ke query
            this.db.pst.setString(1, this.idTransaksi);
            this.db.pst.setString(2, this.idKaryawan);
            this.db.pst.setString(3, this.namaKaryawan);
            this.db.pst.setInt(4, this.getTotalJumlahBahan());
            this.db.pst.setInt(5, this.getTotalHarga());
            this.db.pst.setString(6, this.waktu.getCurrentDateTime());
            
            // eksekusi query
            boolean isSuccess = this.db.pst.executeUpdate() > 0;
            
            // cek apakah transaksi berhasil atau tidak
            if(isSuccess){
                // jika transaksi berhasil maka akan memanggil method detail transaksi dan triger transaksi
                return this.detailTransaksi(this.idTransaksi);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Saat Transaksi : " + ex.getMessage());
        }
        return false;
    }
    
    private boolean detailTransaksi(String idTransaksi){
        try{
            // membuat query
            String sql = "INSERT INTO detail_tr_beli VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            // membaca semua data yang ada didalam tabel
            for(int i = 0; i < tabelTr.getRowCount(); i++){
            // mendapatkan id bahan dan satuan bahan
            String idBhn = this.tabelTr.getValueAt(i, 1).toString(),
                   satuan = this.ba.getkodeSatuan(this.tabelTr.getValueAt(i, 5).toString());
            
                // menyiapkan query
                this.db.pst = this.db.conn.prepareCall(sql);
                this.db.pst.setString(1, idTransaksi);
                this.db.pst.setString(2, idBhn); // get data id bahan
                this.db.pst.setString(3, this.tabelTr.getValueAt(i, 2).toString()); // get data nama bahan
                this.db.pst.setString(4, this.tabelTr.getValueAt(i, 3).toString()); // get data jenis bahan
                this.db.pst.setFloat(5, Float.parseFloat(this.tabelTr.getValueAt(i, 4).toString())); // get data jumlah
                this.db.pst.setString(6, satuan); // get data satuan bahan
                this.db.pst.setString(7, this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString())); // get data harga total
                
                // eksekusi query
                this.db.pst.executeUpdate();
            }
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saat detail transaksi : " + ex.getMessage());
        }
        return false;
    }
    
    private void keyEventInputJumlah(KeyEvent evt){
        try {
            // filter karakter selain angka dan titik
            this.txt.rillOnly(evt);
            System.out.println(this.inpJumlah.getText());
            // cek input jumlah kosong atau tidak
            if (!this.inpJumlah.getText().isEmpty()) {
                // cek apakah input jumlah diawali dengan titik
                if (!this.inpJumlah.getText().startsWith(".")) {
                    // konversi input jumlah ke float (untuk mengecek apakah terjadi exception atau tidak)s
                    float d = Float.parseFloat(inpJumlah.getText());
                    System.out.println(d);
                } else {
                    this.inpJumlah.setText("0");
                }
            } else {
                this.inpJumlah.setText("");
            }
        } catch (NumberFormatException ex) {
            // jika terjadi exception
            ex.printStackTrace();
            if (ex.getMessage().contains("multiple points")) {
                Message.showWarning(this, "Jumlah titik boleh lebih dari 1!");
                this.inpJumlah.setText("0");
            }
        }
    }
    
    private void showUpdateTransaksi(){
        try{
            this.resetTabel();
            DefaultTableModel model = (DefaultTableModel) this.tabelTr.getModel();
            
            // membuat query untuk mendapatkan data dari dua tabel yaitu transaksi utama dan detail
            String sql = String.format(
                    "SELECT t.id_karyawan, t.nama_karyawan, t.total_harga,  "
                  + "d.id_bahan, d.nama_bahan, d.jenis_bahan, d.jumlah, d.satuan_bahan, d.total_harga "
                            + "FROM transaksi_beli AS t "
                            + "JOIN detail_tr_beli AS d "
                            + "ON t.id_tr_beli = d.id_tr_beli "
                            + "WHERE d.id_tr_beli = '%s'",
                    this.inpIdTransaksi.getText()
            );
            
            System.out.println(sql);
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // membaca data pada query
            while(this.db.res.next()){
                
                // mendapatkan data transaksi
                if(this.db.res.isFirst()){
                    // mendapatkan data
                    this.idKaryawan = this.db.res.getString("t.id_karyawan");
                    this.namaKaryawan = this.db.res.getString("t.nama_karyawan");
                    this.ttlHargaBayar = this.db.res.getInt("t.total_harga");
                    
                    // menampilkan data 
                    this.inpKaryawan.setSelectedItem(String.format("%s | %s", this.idKaryawan, this.namaKaryawan));
                    this.inpTotalHarga.setText(txt.toMoneyCase(""+this.ttlHargaBayar).substring(4));
                }
                
                // menambahkan data detail transaksi ke tabel
                model.addRow(
                    new Object[]{
                        this.inpIdTransaksi.getText(),
                        this.db.res.getString("d.id_bahan"),
                        this.db.res.getString("d.nama_bahan"),
                        this.db.res.getString("d.jenis_bahan"),
                        this.db.res.getFloat("d.jumlah"),
                        this.ba.getNamaSatuan(this.db.res.getString("d.satuan_bahan")),
                        this.txt.toMoneyCase(this.db.res.getString("d.total_harga"))
                    }
                );
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private boolean updateTransaksi(){
        try{
            // reset detail transaksi
            boolean reset = this.resetDetailtr();
            
            // cek reset berhasil atau tidak
            if(reset){

                // update id karyawan, nama karyawan, total menu, total harga dan tanggal
                String sql = String.format(
                        "UPDATE transaksi_beli SET id_karyawan = '%s', nama_karyawan = '%s', total_bahan = %d, total_harga = %d, tanggal = '%s' "
                      + "WHERE id_tr_beli = '%s' ", 
                        this.idKaryawan, this.namaKaryawan, this.getTotalJumlahBahan(), this.getTotalHarga(), this.waktu.getCurrentDateTime(), this.inpIdTransaksi.getText()
                );
                
                System.out.println(sql);
                
                // mengeksekusi query
                boolean isSukses = this.db.stat.executeUpdate(sql) > 0;

                if(isSukses){
                    // mengudate data detail transaksi
                    return this.detailTransaksi(this.inpIdTransaksi.getText());
                }else{
                    Message.showWarning(this, "Terjadi kegagalan saat mengupdate transaksi");
                }
            }else{
                Message.showWarning(this, "Terjadi kegagalan saat mengupdate transaksi");
            }

        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private boolean resetDetailtr() throws SQLException{
        return this.db.stat.executeUpdate(
                String.format("DELETE FROM detail_tr_beli WHERE id_tr_beli = '%s'", this.inpIdTransaksi.getText())
        ) > 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        btnMenu = new javax.swing.JLabel();
        btnBahan = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        lineSideMenu1 = new javax.swing.JSeparator();
        lblProfileSidebar = new javax.swing.JLabel();
        btnTransaksi = new javax.swing.JLabel();
        btnDataMaster = new javax.swing.JLabel();
        btnLaporan = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JLabel();
        btnKaryawan = new javax.swing.JLabel();
        btnPembeli = new javax.swing.JLabel();
        btnSupplier = new javax.swing.JLabel();
        pnlTop = new com.ui.RoundedPanel();
        lblIconWindow = new javax.swing.JLabel();
        lblNamaWindow = new javax.swing.JLabel();
        lblTopSetting = new javax.swing.JLabel();
        lblTopInfo = new javax.swing.JLabel();
        lblTopProfile = new javax.swing.JLabel();
        pnlContent = new com.ui.RoundedPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTr = new javax.swing.JTable();
        lineCenter = new javax.swing.JSeparator();
        lblIdBahan = new javax.swing.JLabel();
        inpIdBahan = new com.ui.RoundedTextField(50);
        lblNamaBahan = new javax.swing.JLabel();
        inpNamaBahan = new com.ui.RoundedTextField(15);
        btnBayar = new javax.swing.JButton();
        btnUpdateBahan = new javax.swing.JToggleButton();
        btnHapus = new javax.swing.JToggleButton();
        lblHarga = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        inpCariBahan = new javax.swing.JLabel();
        lblServerTime = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
        lblTotalHargaRp = new javax.swing.JLabel();
        inpTotalHarga = new javax.swing.JTextField();
        lblIDTransaksi = new javax.swing.JLabel();
        inpIdTransaksi = new javax.swing.JTextField();
        lblStok = new javax.swing.JLabel();
        inpJumlah = new com.ui.RoundedTextField(15);
        inpStokSatuan = new javax.swing.JLabel();
        lblKaryawan = new javax.swing.JLabel();
        inpKaryawan = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lblHistori = new javax.swing.JLabel();
        lblBottom = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(112, 160, 227));

        pnlSidebar.setBackground(new java.awt.Color(35, 99, 210));
        pnlSidebar.setPreferredSize(new java.awt.Dimension(254, 730));

        btnMenu.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-coffe.png"))); // NOI18N
        btnMenu.setText("Data Menu");
        btnMenu.setIconTextGap(7);
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
        });

        btnBahan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidebar-bahan.png"))); // NOI18N
        btnBahan.setText("Data Bahan");
        btnBahan.setIconTextGap(7);
        btnBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBahanMouseClicked(evt);
            }
        });

        lblNamaUser.setFont(new java.awt.Font("Swis721 LtEx BT", 1, 20)); // NOI18N
        lblNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNamaUser.setText("Achmad Baihaqi");

        lineSideMenu1.setForeground(new java.awt.Color(255, 255, 255));

        lblProfileSidebar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfileSidebar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidebar-userprofile.png"))); // NOI18N
        lblProfileSidebar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        btnTransaksi.setBackground(new java.awt.Color(166, 203, 227));
        btnTransaksi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-trjual-dark.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setIconTextGap(7);
        btnTransaksi.setOpaque(true);
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
        });

        btnDataMaster.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        btnDataMaster.setForeground(new java.awt.Color(255, 255, 255));
        btnDataMaster.setText("Data Master");

        btnLaporan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-lpjual.png"))); // NOI18N
        btnLaporan.setText("Laporan");
        btnLaporan.setIconTextGap(7);
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLaporanMouseClicked(evt);
            }
        });

        lblMenu.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        lblMenu.setForeground(new java.awt.Color(255, 255, 255));
        lblMenu.setText("Menu");

        btnLogout.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setIconTextGap(7);
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        btnDashboard.setBackground(new java.awt.Color(166, 203, 227));
        btnDashboard.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-home.png"))); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.setIconTextGap(7);
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
        });

        btnKaryawan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        btnKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-karyawan.png"))); // NOI18N
        btnKaryawan.setText("Data Karyawan");
        btnKaryawan.setIconTextGap(7);
        btnKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKaryawanMouseClicked(evt);
            }
        });

        btnPembeli.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnPembeli.setForeground(new java.awt.Color(255, 255, 255));
        btnPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-pembeli.png"))); // NOI18N
        btnPembeli.setText("Data Pembeli");
        btnPembeli.setIconTextGap(7);
        btnPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPembeliMouseClicked(evt);
            }
        });

        btnSupplier.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-supplier.png"))); // NOI18N
        btnSupplier.setText("Data Supplier");
        btnSupplier.setIconTextGap(7);
        btnSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupplierMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlSidebarLayout = new javax.swing.GroupLayout(pnlSidebar);
        pnlSidebar.setLayout(pnlSidebarLayout);
        pnlSidebarLayout.setHorizontalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProfileSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDataMaster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(lineSideMenu1))
                        .addGap(10, 10, 10)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        pnlSidebarLayout.setVerticalGroup(
            pnlSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProfileSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineSideMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(lblMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnDataMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTop.setBackground(new java.awt.Color(248, 249, 250));
        pnlTop.setRoundBottomLeft(20);
        pnlTop.setRoundBottomRight(20);
        pnlTop.setRoundTopLeft(20);
        pnlTop.setRoundTopRight(20);

        lblIconWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-topleft-trbeli.png"))); // NOI18N

        lblNamaWindow.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblNamaWindow.setForeground(new java.awt.Color(0, 21, 39));
        lblNamaWindow.setText("Pembelian Suplai Bahan");

        lblTopSetting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-setting.png"))); // NOI18N
        lblTopSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopSettingMouseExited(evt);
            }
        });

        lblTopInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-info.png"))); // NOI18N
        lblTopInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopInfoMouseExited(evt);
            }
        });

        lblTopProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTopProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-top-miniuser.png"))); // NOI18N
        lblTopProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTopProfileMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIconWindow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNamaWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTopSetting)
                .addGap(5, 5, 5)
                .addComponent(lblTopInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTopProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIconWindow, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
            .addComponent(lblNamaWindow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTopProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlContent.setBackground(new java.awt.Color(248, 249, 250));
        pnlContent.setRoundBottomLeft(20);
        pnlContent.setRoundBottomRight(20);
        pnlContent.setRoundTopLeft(20);
        pnlContent.setRoundTopRight(20);

        tabelTr.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelTr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"TRJ00001", "MN002", "Original Coffee", "Coffee", "Rp. 5000", "2", "Rp. 10.000"},
                {"TRJ00001", "MN006", "Orange Juice", "Minuman", "Rp. 7000", "1", "Rp. 7.000"},
                {"TRJ00001", "MN001", "Coklat Dingin", "Coffee", "Rp. 3000", "2", "Rp. 6.000"},
                {"TRJ00001", "MN009", "Nasi Goreng", "Makanan", "Rp. 11.000", "1", "Rp. 11.000"}
            },
            new String [] {
                "ID Transaksi", "ID Bahan", "Nama Bahan", "Jenis Bahan", "Harga Bahan", "Jumlah", "Total Harga"
            }
        ));
        tabelTr.setGridColor(new java.awt.Color(0, 0, 0));
        tabelTr.setSelectionBackground(new java.awt.Color(26, 164, 250));
        tabelTr.setSelectionForeground(new java.awt.Color(250, 246, 246));
        tabelTr.getTableHeader().setReorderingAllowed(false);
        tabelTr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTrMouseClicked(evt);
            }
        });
        tabelTr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelTrKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelTrKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabelTr);

        lineCenter.setBackground(new java.awt.Color(0, 0, 0));
        lineCenter.setForeground(new java.awt.Color(0, 0, 0));

        lblIdBahan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblIdBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idbahan.png"))); // NOI18N
        lblIdBahan.setText("ID Bahan");

        inpIdBahan.setBackground(new java.awt.Color(248, 249, 250));
        inpIdBahan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpIdBahan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpIdBahan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        inpIdBahan.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpIdBahan.setEnabled(false);
        inpIdBahan.setSelectionStart(5);
        inpIdBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpIdBahanMouseClicked(evt);
            }
        });

        lblNamaBahan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNamaBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblNamaBahan.setText("Nama Bahan");

        inpNamaBahan.setBackground(new java.awt.Color(248, 249, 250));
        inpNamaBahan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaBahan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNamaBahan.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpNamaBahan.setEnabled(false);
        inpNamaBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpNamaBahanMouseClicked(evt);
            }
        });

        btnBayar.setBackground(new java.awt.Color(41, 180, 50));
        btnBayar.setForeground(new java.awt.Color(255, 255, 255));
        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-pembayaran-pay.png"))); // NOI18N
        btnBayar.setText("Transaksi");
        btnBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBayarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBayarMouseExited(evt);
            }
        });
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        btnUpdateBahan.setBackground(new java.awt.Color(34, 119, 237));
        btnUpdateBahan.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-tambah.png"))); // NOI18N
        btnUpdateBahan.setText("Tambah");
        btnUpdateBahan.setMaximumSize(new java.awt.Dimension(109, 25));
        btnUpdateBahan.setMinimumSize(new java.awt.Dimension(109, 25));
        btnUpdateBahan.setPreferredSize(new java.awt.Dimension(109, 25));
        btnUpdateBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateBahanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateBahanMouseExited(evt);
            }
        });
        btnUpdateBahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBahanActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(220, 41, 41));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
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

        lblHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga.png"))); // NOI18N
        lblHarga.setText("Harga Bahan");

        inpHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpHarga.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpHarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpHargaMouseClicked(evt);
            }
        });
        inpHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpHargaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpHargaKeyTyped(evt);
            }
        });

        inpCariBahan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        inpCariBahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        inpCariBahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpCariBahanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                inpCariBahanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                inpCariBahanMouseExited(evt);
            }
        });

        lblServerTime.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblServerTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServerTime.setText("Server Time : Jumat, 18 November 2022 / 09:23:29");

        lblTotalHarga.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotalHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-totalharga.png"))); // NOI18N
        lblTotalHarga.setText("Total Harga");

        lblTotalHargaRp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalHargaRp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalHargaRp.setText("Rp");

        inpTotalHarga.setBackground(new java.awt.Color(237, 239, 242));
        inpTotalHarga.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpTotalHarga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpTotalHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpTotalHarga.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpTotalHarga.setEnabled(false);
        inpTotalHarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpTotalHargaMouseClicked(evt);
            }
        });

        lblIDTransaksi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblIDTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idtransaksii.png"))); // NOI18N
        lblIDTransaksi.setText("ID Transaksi");

        inpIdTransaksi.setBackground(new java.awt.Color(237, 239, 242));
        inpIdTransaksi.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpIdTransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpIdTransaksi.setText("TRB00001");
        inpIdTransaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpIdTransaksi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpIdTransaksi.setEnabled(false);
        inpIdTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpIdTransaksiMouseClicked(evt);
            }
        });

        lblStok.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-stock.png"))); // NOI18N
        lblStok.setText("Jumlah");

        inpJumlah.setBackground(new java.awt.Color(248, 249, 250));
        inpJumlah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJumlah.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpJumlah.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpJumlah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpJumlahMouseClicked(evt);
            }
        });
        inpJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpJumlahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpJumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpJumlahKeyTyped(evt);
            }
        });

        inpStokSatuan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpStokSatuan.setText("Kilogram");

        lblKaryawan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-nama.png"))); // NOI18N
        lblKaryawan.setText("Karyawan");

        inpKaryawan.setBackground(new java.awt.Color(248, 249, 250));
        inpKaryawan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpKaryawan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Karyawan" }));
        inpKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpKaryawanActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Can You See Me?");

        lblHistori.setForeground(new java.awt.Color(255, 0, 51));
        lblHistori.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHistori.setText("Histori Transaksi Hari Ini");
        lblHistori.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblHistori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHistoriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHistoriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHistoriMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lineCenter)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdateBahan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblServerTime, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                                .addGap(13, 13, 13))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(pnlContentLayout.createSequentialGroup()
                                                .addComponent(lblNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inpNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pnlContentLayout.createSequentialGroup()
                                                .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inpHarga))
                                            .addGroup(pnlContentLayout.createSequentialGroup()
                                                .addComponent(lblIdBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inpIdBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inpCariBahan)))
                                        .addGroup(pnlContentLayout.createSequentialGroup()
                                            .addComponent(lblKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inpKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlContentLayout.createSequentialGroup()
                                            .addComponent(lblStok, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inpJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(inpStokSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblIDTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(inpTotalHarga, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(inpIdTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                                        .addGap(11, 11, 11))
                                    .addComponent(lblHistori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(16, 16, 16))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIDTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpIdBahan)
                            .addComponent(inpCariBahan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblIdBahan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpNamaBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblStok, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpStokSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpJumlah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblHistori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateBahan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblServerTime, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlSidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );

        jScrollPane1.setViewportView(pnlMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataMenu window = new DataMenu();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnMenuMouseClicked

    private void btnBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBahanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataBahan window = new DataBahan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnBahanMouseClicked

    private void btnTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTransaksiMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuTransaksi window = new MenuTransaksi();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnTransaksiMouseClicked

    private void btnLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MenuLaporan window = new MenuLaporan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnLaporanMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        new User().logout();
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        Dashboard window = new Dashboard();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void btnKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKaryawanMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataKaryawan window = new DataKaryawan();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnKaryawanMouseClicked

    private void btnPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPembeliMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataPembeli window = new DataPembeli();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnPembeliMouseClicked

    private void btnSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupplierMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataSupplier window = new DataSupplier();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setVisible(true);
                window.setLocation(getX(), getY());
                window.setSize(getWidth(), getHeight());
            }
        });
        this.dispose();
    }//GEN-LAST:event_btnSupplierMouseClicked

    private void lblTopProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseClicked
        UserProfile dia = new UserProfile(null, true, this);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopProfileMouseClicked

    private void lblTopProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopProfile.setIcon(Gambar.getIcon("ic-window-top-miniuser-entered.png"));
    }//GEN-LAST:event_lblTopProfileMouseEntered

    private void lblTopProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopProfileMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopProfile.setIcon(Gambar.getIcon("ic-window-top-miniuser.png"));
    }//GEN-LAST:event_lblTopProfileMouseExited

    private void lblTopSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseClicked
        Pengaturan dia = new Pengaturan(null, true);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopSettingMouseClicked

    private void lblTopSettingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopSetting.setIcon(Gambar.getIcon("ic-window-top-setting-entered.png"));
    }//GEN-LAST:event_lblTopSettingMouseEntered

    private void lblTopSettingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopSettingMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopSetting.setIcon(Gambar.getIcon("ic-window-top-setting.png"));
    }//GEN-LAST:event_lblTopSettingMouseExited

    private void lblTopInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseClicked
        InfoApp dia = new InfoApp(null, true);
        dia.setVisible(true);
    }//GEN-LAST:event_lblTopInfoMouseClicked

    private void lblTopInfoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTopInfo.setIcon(Gambar.getIcon("ic-window-top-info-entered.png"));
    }//GEN-LAST:event_lblTopInfoMouseEntered

    private void lblTopInfoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopInfoMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTopInfo.setIcon(Gambar.getIcon("ic-window-top-info.png"));
    }//GEN-LAST:event_lblTopInfoMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        System.out.println(this.getClass().getName() + " opened");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        this.status = true;
        System.out.println(this.getClass().getName() + " activated");
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        System.out.println(this.getClass().getName() + " deactivated");
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.status = false;
        this.db.closeConnection();
        System.out.println(this.getClass().getName() + " closed");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println(this.getClass().getName() + " closing");
    }//GEN-LAST:event_formWindowClosing

    private void btnBayarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseEntered
        this.btnBayar.setIcon(Gambar.getIcon("ic-pembayaran-pay-entered.png"));
    }//GEN-LAST:event_btnBayarMouseEntered

    private void btnBayarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBayarMouseExited
        this.btnBayar.setIcon(Gambar.getIcon("ic-pembayaran-pay.png"));
    }//GEN-LAST:event_btnBayarMouseExited

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        if(this.inpKaryawan.getSelectedIndex() <= 0){
            Message.showWarning(this, "Tidak ada karyawan yang dipilih!");
            return;
        }else if(this.tabelTr.getRowCount() <= 0){
            Message.showWarning(this, "Tidak ada bahan yang dibeli!");
            return;
        }
        
        // menyiapkan window transaksi sukses
        TransaksiBeliSuccess dia;
        
        // pengecekan mode transaksi
        // jika mode update
        if(this.isUpdateTr){
            // melakukan update transaksi
            if(this.updateTransaksi()){
                dia = new TransaksiBeliSuccess(null, true, this.idTransaksi);
                dia.setVisible(true);
                this.isUpdateTr = false;
                this.isEdit = false;
                this.changeButton();
                this.resetTransaksi();            
            }
        }
        // jika mode tambah
        else{
            // melakukan transaksi baru
            if(this.transaksi()){
                dia = new TransaksiBeliSuccess(null, true, this.idTransaksi);
                dia.setVisible(true);
                this.isEdit = false;
                this.changeButton();
                this.resetTransaksi();            
            }
        }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnUpdateBahanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateBahanMouseEntered
        if(this.isEdit){
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-edit-entered.png"));
        }else{
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-tambah-entered.png"));
        }
    }//GEN-LAST:event_btnUpdateBahanMouseEntered

    private void btnUpdateBahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateBahanMouseExited
        if(this.isEdit){
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-edit.png"));
        }else{
            this.btnUpdateBahan.setIcon(Gambar.getIcon("ic-data-tambah.png"));
        }
    }//GEN-LAST:event_btnUpdateBahanMouseExited

    private void btnUpdateBahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBahanActionPerformed
        this.eventUpdateBahan();
    }//GEN-LAST:event_btnUpdateBahanActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(this.tabelTr.getSelectedRow() < 0){
            Message.showWarning(this, "Tidak ada data yang dipilih");
            return;
        }
        
        String nmBahan = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 2).toString()+"?";
        int konf = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus "+nmBahan , "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        // cek konfirmasi hapus
        if(konf == JOptionPane.YES_OPTION){
            this.hapusDataBahan();
            this.isEdit = false;
            this.changeButton();
            this.resetTambah();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void inpIdBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdBahanMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpIdBahanMouseClicked

    private void inpCariBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariBahanMouseClicked
        
        GetDataBahan dia = new GetDataBahan(null, true);
        dia.setVisible(true);

        if(dia.isSelected()){
            this.idBahan = dia.getIdSelected();
            this.inpIdBahan.setText(this.idBahan);
            this.showBahanSelected();
            this.inpHarga.requestFocus();
            this.isEdit = false;
            this.changeButton();
        }

    }//GEN-LAST:event_inpCariBahanMouseClicked

    private void inpCariBahanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariBahanMouseEntered
        this.inpCariBahan.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_inpCariBahanMouseEntered

    private void inpCariBahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariBahanMouseExited
        this.inpCariBahan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpCariBahanMouseExited

    private void tabelTrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTrMouseClicked
        this.isEdit = true;
        this.showBahanFromTabel();
        this.changeButton();
        this.inpHarga.requestFocus();
    }//GEN-LAST:event_tabelTrMouseClicked

    private void tabelTrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyPressed
        
    }//GEN-LAST:event_tabelTrKeyPressed

    private void inpNamaBahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpNamaBahanMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpNamaBahanMouseClicked

    private void inpHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpHargaMouseClicked

    }//GEN-LAST:event_inpHargaMouseClicked

    private void inpIdTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdTransaksiMouseClicked
        JOptionPane.showMessageDialog(this, "ID Transaksi tidak bisa diedit!!");
    }//GEN-LAST:event_inpIdTransaksiMouseClicked

    private void inpTotalHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpTotalHargaMouseClicked
        JOptionPane.showMessageDialog(this, "Total harga akan terupdate otomatis saat menambahkan menu!!");
    }//GEN-LAST:event_inpTotalHargaMouseClicked

    private void inpJumlahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpJumlahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_inpJumlahMouseClicked

    private void inpJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyTyped
        this.keyEventInputJumlah(evt);
    }//GEN-LAST:event_inpJumlahKeyTyped

    private void inpJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.eventUpdateBahan();
        }else if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.inpHarga.requestFocus();
        }
    }//GEN-LAST:event_inpJumlahKeyPressed

    private void tabelTrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyReleased
        // untuk editing data
        if(this.tabelTr.getSelectedColumn() == 5){
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                this.editDataBahan();
            }            
        }
        
        // untuk hapus data
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            String nmBahan = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 2).toString()+"?";
            int konf = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus "+nmBahan , "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            // cek konfirmasi hapus
            if(konf == JOptionPane.YES_OPTION){
                this.hapusDataBahan();
            }
        }
    }//GEN-LAST:event_tabelTrKeyReleased

    private void inpHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpHargaKeyTyped
        this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpHargaKeyTyped

    private void inpHargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpHargaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.inpJumlah.requestFocus();
        }
    }//GEN-LAST:event_inpHargaKeyPressed

    private void inpKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpKaryawanActionPerformed
        String data = this.inpKaryawan.getSelectedItem().toString();
        // mendapatkan data karyawan
        this.idKaryawan = data.substring(0, data.indexOf("|")-1);
        this.namaKaryawan = data.substring(data.indexOf("|")+2);
        System.out.println("ID : " + idKaryawan);
        System.out.println("Nama : " + namaKaryawan);
    }//GEN-LAST:event_inpKaryawanActionPerformed

    private void inpJumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyReleased
        this.keyEventInputJumlah(evt);
    }//GEN-LAST:event_inpJumlahKeyReleased

    private void lblHistoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoriMouseClicked
        HistoriTransaksi his = new HistoriTransaksi(null, true, HistoriTransaksi.STATUS_BELI);
        his.setVisible(true);
        
        if(his.isIdSelected()){
            // update status transaksi menjadi mode update
            this.isUpdateTr = true;
            
            // mengkosongkan data transaksi
            this.resetTransaksi();
            
            // mengupdate id transaksi
            this.idTransaksi = his.getIdSelected();
            this.inpIdTransaksi.setText(this.idTransaksi);

            // panggil method showUPdateTarnsaksi
            this.showUpdateTransaksi();
        }
    }//GEN-LAST:event_lblHistoriMouseClicked

    private void lblHistoriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoriMouseEntered
        this.lblHistori.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblHistoriMouseEntered

    private void lblHistoriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoriMouseExited
        this.lblHistori.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }//GEN-LAST:event_lblHistoriMouseExited

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
            java.util.logging.Logger.getLogger(MenuTransaksiBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuTransaksiBeli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBahan;
    private javax.swing.JButton btnBayar;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnDataMaster;
    private javax.swing.JToggleButton btnHapus;
    private javax.swing.JLabel btnKaryawan;
    private javax.swing.JLabel btnLaporan;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnMenu;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JToggleButton btnUpdateBahan;
    private javax.swing.JLabel inpCariBahan;
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpIdBahan;
    private javax.swing.JTextField inpIdTransaksi;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JComboBox inpKaryawan;
    private javax.swing.JTextField inpNamaBahan;
    private javax.swing.JLabel inpStokSatuan;
    private javax.swing.JTextField inpTotalHarga;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblHistori;
    private javax.swing.JLabel lblIDTransaksi;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblIdBahan;
    private javax.swing.JLabel lblKaryawan;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNamaBahan;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblServerTime;
    private javax.swing.JLabel lblStok;
    private javax.swing.JLabel lblTopInfo;
    private javax.swing.JLabel lblTopProfile;
    private javax.swing.JLabel lblTopSetting;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JLabel lblTotalHargaRp;
    private javax.swing.JSeparator lineCenter;
    private javax.swing.JSeparator lineSideMenu1;
    private com.ui.RoundedPanel pnlContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSidebar;
    private com.ui.RoundedPanel pnlTop;
    private javax.swing.JTable tabelTr;
    // End of variables declaration//GEN-END:variables
}
