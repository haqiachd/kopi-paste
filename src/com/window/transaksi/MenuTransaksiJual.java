package com.window.transaksi;

import com.koneksi.Database;
import com.manage.Laporan;
import com.manage.Message;
import com.manage.Text;
import com.ui.UIManager;
import com.manage.User;
import com.manage.Waktu;
import com.media.Gambar;
import com.window.Dashboard;
import com.window.DataBahan;
import com.window.DataAkun;
import com.window.DataMenu;
import com.window.DataPembeli;
import com.window.DataSupplier;
import com.window.laporan.MenuLaporan;
import com.window.get.GetDataMenu;
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
public class MenuTransaksiJual extends javax.swing.JFrame {
    
    private String idTransaksi, idMenu = "", idDiskon, namaMenu, jenisMenu, idKaryawan, namaKaryawan;
    
    private int hargaMenu // harga dari menu
               ,diskonToko // diskon toko 
               ,jumlah // jumlah menu yg dipilih
               ,ttlHrgMenu // total harga dari menu (harga menu * jumlah menu)
               ,ttlSeluruahHarga = 0 // total keseluruhan harga dari menu
               ,ttlBayar = 0 // total uang yang dibayarkan pembeli
               ,ttlKembalian = 0; // total kembalian
    
    private DefaultTableModel tblModel;
    
    private final Database db = new Database();
    
    private final UIManager win = new UIManager();
    
    private final Text txt = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private final Laporan report = new Laporan();
    
    private boolean status, isEdit = false, isUpdateTr = false, isUangCukup = false;
    
    public MenuTransaksiJual() {
        initComponents();
        this.setTitle("Menu Transaksi Penjualan");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Gambar.getWindowIcon());
        
        this.status = true;
        
        this.inpIdTransaksi.setText(this.createID());
        this.lblNamaUser.setText(User.getNamaUser());
        
        // set hover button
        this.win.btns = new JLabel[]{
            this.btnKaryawan, this.btnSupplier, this.btnPembeli, 
            this.btnDashboard, this.btnLaporan, this.btnBahan, this.btnLogout, this.btnMenu
        };
        this.win.hoverButton();
        
        // set ui button
        this.btnBayar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnUpdateMenu.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // update server time
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("update waktu is " + status);
                while(status){
                    lblServerTime.setText("Server Time : " + waktu.getUpdateWaktu());
                    waktu.delay(1);
                }
                System.out.println("update waktu is false");
            }
        }).start();
        
        // set model tabel ke default
        this.resetTabel();
        // setting desain tabel
        this.tabelTr.setRowHeight(29);
        this.tabelTr.getTableHeader().setBackground(new java.awt.Color(248,249,250));
        this.tabelTr.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        // set margin textfield
        this.inpIdTransaksi.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpIdMenu.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNamaMenu.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpJumlah.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); 
        
        // set hidden button
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        this.btnSupplier.setVisible(false);
        
        if(!User.isAdmin()){
            this.btnKaryawan.setVisible(false);
            this.btnLaporan.setVisible(false);
        }
        
        this.inpJumlah.requestFocus();
    }
    
    public MenuTransaksiJual(String idTransaksi){
        this();
        // menampilkan data yang akan diupdate
        this.isUpdateTr = true;
        this.idTransaksi = idTransaksi;
        this.inpIdTransaksi.setText(this.idTransaksi);
        this.showUpdateTransaksi();
    }
    
    /**
     * Auto increment id transaksi
     * 
     * @return 
     */
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1", lastID, nomor = "00000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_tr_jual");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(4);
                }
            }
            // membuat id baru
            return String.format("TRJ%05d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private void changeButton(){
        if(this.isEdit){
            this.btnUpdateMenu.setText("Edit");
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-edit.png"));
            this.btnUpdateMenu.setBackground(new Color(204,0,153));
        }else{
            this.btnUpdateMenu.setText("Tambah");
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-tambah.png"));
            this.btnUpdateMenu.setBackground(new Color(34,119,237));
        }
    }
    
    /**
     * Menampilkan data menu yang diplih
     */
    private void showMenuSelected(){

        try {
            // membuat query
            String sql = "SELECT * FROM menu WHERE id_menu = '"+this.idMenu+"'";
            
            // mengeksekusqi query
            this.db.res = this.db.stat.executeQuery(sql);

            if(this.db.res.next()){
                // mendapatkan data menu
                this.namaMenu = this.db.res.getString("nama_menu");
                this.jenisMenu = this.db.res.getString("jenis");
                this.hargaMenu = this.db.res.getInt("harga");
                // menampilkan data menu ke window
                this.inpIdMenu.setText(idMenu);
                this.inpNamaMenu.setText(namaMenu);
                this.inpHarga.setText(txt.toMoneyCase(""+hargaMenu));
//                this.inpJenis.setText(this.jenisMenu);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    /**
     * Menampilkan menu yang akan diedit dari tabel
     */
    private void showMenuFromTabel(){
        int row = this.tabelTr.getSelectedRow();
        // mendapatkan data menu
        this.idMenu = this.tabelTr.getValueAt(row, 1).toString();
        this.namaMenu = this.tabelTr.getValueAt(row, 2).toString();
        this.hargaMenu = Integer.parseInt(this.txt.removeMoneyCase(this.tabelTr.getValueAt(row, 4).toString()));
        this.jumlah = Integer.parseInt(this.tabelTr.getValueAt(row, 5).toString());
        
        // menampilkan data menu
        this.inpIdMenu.setText(this.idMenu);
        this.inpNamaMenu.setText(this.namaMenu);
        this.inpHarga.setText(this.txt.toMoneyCase(this.hargaMenu));
        this.inpJumlah.setText(Integer.toString(this.jumlah));
        
        this.inpJumlah.requestFocus();
    }
    
    private void updateTabel(){
        // set model tabel dengan model sebelumnya
        DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
        boolean dataBaru = true;
        
        // membaca baris pada tabel
        for (int i = 0; i < this.tblModel.getRowCount(); i++) {
            // cek apakah data menu sudah ada didalam tabel atau tidak
            if (this.inpIdMenu.getText().equals(String.valueOf(this.tabelTr.getValueAt(i, 1)))) {
                // jika sudah ada maka data jumlah pada menu akan diupdate 
                // dengan cara mendapatkan data jumlah baru dan ditambahkan dengan data jumlah yang lama
                this.jumlah += Integer.parseInt(String.valueOf(this.tabelTr.getValueAt(i, 5)));
            }
        }

        /**
         * Mengecek apakah data nama menu yang dipilih sudah ada atau belum didalam tabel transaksi yang ada di window
         * Jika sudah ada maka data jumlah pada tabel transaksi akan diupdate dan tidak membuat baris baru pada tabel
         * Jika belum maka data nama menu akan ditambahkan pada baris baru pada tabel
         */
        if (tabelTr.getRowCount() >= 1) {
            // membaca isi tabel transaksi
            for (int i = 0; i < model.getRowCount(); i++) {
                // mendapatkan data id menu dari tabel
                String id = model.getValueAt(i, 1).toString();

                // jika id menu sudah ada didalam tabel, maka akan mengupdate data jumlah yang ada didalam tabel transaksi
                if (id.equalsIgnoreCase(this.idMenu)) {
                    dataBaru = false;
                    
                    // update data jumlah yang ada didalam tabel
                    model.setValueAt(Integer.toString(this.jumlah), i, 5);
                    String total = Integer.toString(this.jumlah * hargaMenu);
                    model.setValueAt(txt.toMoneyCase(total), i, 6);
                }
            }
        }
        
        // jika id menu belum ada didalam tabel maka data menu akan ditambahkan pada baris baru
        if (dataBaru) {
            System.out.println("DATA BARU");
            model.addRow(new Object[]{
                this.inpIdTransaksi.getText(),
                this.idMenu,
                this.namaMenu,
                this.jenisMenu,
                this.txt.toMoneyCase(""+this.hargaMenu),
                this.inpJumlah.getText(),
                this.txt.toMoneyCase(Integer.toString(this.ttlHrgMenu))
            });
        }
        
        // menyimpan update tabel
        tabelTr.setModel(model);
    }
    
    private void resetTabel(){
        // setting tabel model
        tblModel = new DefaultTableModel(
                new String[][]{}, // default valuenya kosong
                new String [] {
                    "ID Transaksi", "ID Menu", "Nama Menu", "Jenis Menu", "Harga Menu", "Jumlah", "Total Harga"
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
        
        this.tabelTr.setModel(tblModel);
        
        // setting panjang kolom
        TableColumnModel columnModel = tabelTr.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(110);
        columnModel.getColumn(0).setMaxWidth(110);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(1).setMaxWidth(110);
        columnModel.getColumn(2).setPreferredWidth(220);
        columnModel.getColumn(2).setMaxWidth(300);
        columnModel.getColumn(3).setPreferredWidth(220);
        columnModel.getColumn(3).setMaxWidth(220);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(4).setMaxWidth(160);
        columnModel.getColumn(5).setPreferredWidth(60);
        columnModel.getColumn(5).setMaxWidth(60);
//        columnModel.getColumn(6).setPreferredWidth(160);
//        columnModel.getColumn(6).setMaxWidth(160);
    }
    
    // resset saat menamkan tombol tambah menu
    private void resetTambah(){
        this.inpIdMenu.setText("");
        this.inpNamaMenu.setText("");
        this.inpHarga.setText("");
        this.inpJumlah.setText("");
        if(this.tabelTr.getSelectedRow() > -1){
            this.tabelTr.removeRowSelectionInterval(this.tabelTr.getSelectedRow(), this.tabelTr.getSelectedRow());
        }
    }
    
    // reset saat menekan tombol transaksi
    private void resetTransaksi(){
        // reset textfield dan data
        this.resetTambah();
//        this.inpJenis.setText("");
        this.inpDiskonToko.setText("");
        this.inpTotalHarga.setText("");
        this.idMenu = null;
        this.namaMenu = null;
        this.jumlah = 0;
        this.hargaMenu = 0;
        this.ttlSeluruahHarga = 0;
        this.ttlBayar = 0;
        this.ttlKembalian = 0;
        this.ttlHrgMenu = 0;
        
        // reset tabel
        this.resetTabel();
        
        // reset button
        this.changeButton();
        
        // update id transaksi
        this.inpIdTransaksi.setText(this.createID());
        
        // reset input total bayar
        this.inpTotalBayar.setText("");
        
        // reset input kembalian
        this.inpTotalKembalian.setBackground(new Color(248,249,250));
        this.inpTotalKembalian.setText("");
        
        // set fokus ke jumlah
        this.inpJumlah.requestFocus();
    }
    
    private void eventUpdateMenu(){
        if(this.isEdit){
            // cek apakah data jumlah sudah dimasukan
            if (this.inpJumlah.getText().isEmpty()) {
                Message.showWarning(this, "Jumlah menu belum dimasukan!");
            } else{
                this.editDataMenu();
            }
        }else{
            // cek apakah data menu sudah dipilih
            if(this.inpIdMenu.getText().isEmpty()){
                Message.showWarning(this, "Tidak ada data menu yang dipilih!");
                return;          
            }
            // cek apakah data jumlah sudah dimasukan
            else if (this.inpJumlah.getText().isEmpty()){
                Message.showWarning(this, "Jumlah menu belum dimasukan!");
                return;
            }
            // cek apakah jumlah menu kurang dari 1
            else if(Integer.parseInt(this.inpJumlah.getText()) <= 0){
                Message.showWarning(this, "Jumlah menu harus lebih dari 0!");
                return;
            }
            // menambahkan data menu
            this.tambahDataMenu();
        }
    }
    
    // untuk menambahkan data menu
    private void tambahDataMenu(){
        // reset total harga dan diskon
        this.ttlSeluruahHarga = this.getTotalHarga();
        
        // update data transaksi
        this.jumlah = Integer.parseInt(this.inpJumlah.getText());
        // mendapatkan total harga sebelum diskon
        this.ttlHrgMenu = this.hargaMenu * this.jumlah;
        this.ttlSeluruahHarga += this.ttlHrgMenu;
        
        // menghitung diskon
        this.diskonToko = this.getDiskonToko(this.ttlSeluruahHarga);
        // mengurangi total harga dengan diskon
        this.ttlSeluruahHarga = this.ttlSeluruahHarga - this.diskonToko;
        
        // menampilkan data ke field
        this.inpTotalHarga.setText(this.txt.toMoneyCase(""+this.ttlSeluruahHarga).substring(4));
//        this.inpDiskonToko.setText(this.txt.toMoneyCase(this.diskonToko).substring(4) + this.getPresentaseDiskon());
        this.showDiskon();
        
        // reset tabel dan textfield
        this.updateTabel();
        this.resetTambah();
        
        // alihkan fokus ke input total bayar
        this.inpTotalBayar.requestFocus();
    }
    
    private void editDataMenu(){
        try{
            int row = this.tabelTr.getSelectedRow();
            
                // mendapatkan jumlah menu yang baru
            int newJml = Integer.parseInt(this.inpJumlah.getText()),
                // mendapatkan data harga dari menu    
                harga = Integer.parseInt(txt.removeMoneyCase(this.inpHarga.getText())),
                // mendapatkan data total harga menu yang lama
                oldTotalHarga = Integer.parseInt(txt.removeMoneyCase(this.tabelTr.getValueAt(row, 6).toString())),
                newTotalHarga = newJml * harga; // menghitung total harga menu yang baru (jumlah menu * harga menu)

            if(newJml <= 0){
                Message.showWarning(this, "Jumlah stok harus minimal 1");
                return;
            }
            
            // mengupdate total harga bayar
            this.ttlSeluruahHarga = (this.getTotalHarga() - oldTotalHarga);
            this.ttlSeluruahHarga += newTotalHarga;
            // mendapatkan diskon
            this.diskonToko = this.getDiskonToko(this.ttlSeluruahHarga);
            // mengurangi total harga dengan diskon
            this.ttlSeluruahHarga = this.ttlSeluruahHarga - this.diskonToko;
            
            // mengupdate data yang ada di textfield dan di tabel
            this.tabelTr.setValueAt(Integer.toString(newJml), row, 5);
            this.tabelTr.setValueAt(txt.toMoneyCase(newTotalHarga), row, 6);
            this.inpTotalHarga.setText(this.txt.toMoneyCase(this.ttlSeluruahHarga).substring(4));
            this.showDiskon();
            
            // reset data menu
            this.resetTambah();
            
            // reset button
            this.isEdit = false;
            this.changeButton();
            
            // alihkan fokus ke input total bayar
            this.inpTotalBayar.requestFocus();
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Jumlah menu harus berupa Angka!");
        }
    }
    
    // untuk menghapus data menu pada tabel
    private void hapusDataMenu() {
        if(this.tabelTr.getSelectedRow() > -1){
           // menghapus baris pada tabel
            DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
            model.removeRow(tabelTr.getSelectedRow());
            
            // menghitung ulang total harga
            this.ttlSeluruahHarga = this.getTotalHarga();
            // menhitung ulang diskon
            this.diskonToko = this.getDiskonToko(this.ttlSeluruahHarga);
            this.ttlSeluruahHarga = this.ttlSeluruahHarga -= this.diskonToko;
            
            // menampilkan ulang total harga bayar pada textfield
            this.inpTotalHarga.setText(txt.toMoneyCase(this.ttlSeluruahHarga).substring(4));
            this.showDiskon();
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }
    
    private int getTotalJumlahMenu(){
        int ttlJumlah = 0;
        for(int i = 0; i < this.tabelTr.getRowCount(); i++){
            ttlJumlah += Integer.parseInt(this.tabelTr.getValueAt(i, 5).toString());
        }
        return ttlJumlah;
    }
    
    private int getTotalHarga(){
        int ttlHarga = 0;
        for(int i = 0; i < this.tabelTr.getRowCount(); i++){
            ttlHarga += Integer.parseInt(this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString()));
        }
        return ttlHarga;
    }
    
    private int getDiskonToko(int ttlHarga){
        try{
            String sql = "SELECT id_diskon, ttl_diskon "
                    + "FROM diskon "
                    + "WHERE tgl_akhir >= CURRENT_DATE() "
                    + "AND min_harga <= " + ttlHarga
                    + " ORDER BY min_harga DESC LIMIT 0,1";
            
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            if(this.db.res.next()){
                this.idDiskon = this.db.res.getString("id_diskon");
                return this.db.res.getInt("ttl_diskon");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return 0;
    }
    
    private void showDiskon(){
        double presentase = ((double)this.diskonToko / (double)(this.ttlSeluruahHarga+this.diskonToko)) * 100f;
        if(presentase == 0){
            this.lblDiskonTokoRp.setText("");
            this.inpDiskonToko.setText("Tidak ada diskon");
        }else{
            this.lblDiskonTokoRp.setText("Rp");
            this.inpDiskonToko.setText(String.format("%,d.00 / %.1f", this.diskonToko, presentase)+"%");
        }
    }
    
    private boolean transaksi(){
        try{
            // membuat query
            String sql = "INSERT INTO transaksi_jual VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            this.idTransaksi = this.createID();
            
            // membuat preparedstatement
            this.db.pst = this.db.conn.prepareStatement(sql);
            
            // menambahkan data transaksi ke query
            this.db.pst.setString(1, this.idTransaksi);
            this.db.pst.setString(2, User.getIdAkun());
            this.db.pst.setString(3, User.getNamaUser());
            this.db.pst.setInt(4, this.getTotalJumlahMenu());
            this.db.pst.setInt(5, this.ttlSeluruahHarga);
            this.db.pst.setString(6, this.idDiskon);
            this.db.pst.setInt(7, this.diskonToko);
            this.db.pst.setInt(8, this.ttlBayar);
            this.db.pst.setInt(9, this.ttlKembalian);
            this.db.pst.setString(10, waktu.getCurrentDateTime());
            
            // eksekusi query
            boolean isSuccess = this.db.pst.executeUpdate() > 0;
            
            // cek apakah transaksi berhasil atau tidak
            if(isSuccess){
                // jika transaksi berhasil maka akan memanggil method detail transaksi
                return this.detailTransaksi(this.idTransaksi);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private boolean detailTransaksi(String idTransaksi){
        try{
            // membuat query
            String sql = "INSERT INTO detail_tr_jual VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            // membaca semua data yang ada didalam tabel
            for(int i = 0; i < this.tabelTr.getRowCount(); i++){
                // menyiapkan query
                this.db.pst = this.db.conn.prepareStatement(sql);
                // menambahkan data transaksi ke query
                this.db.pst.setString(1, idTransaksi);
                this.db.pst.setString(2, this.tabelTr.getValueAt(i, 1).toString());
                this.db.pst.setString(3, this.tabelTr.getValueAt(i, 2).toString());
                this.db.pst.setString(4, this.tabelTr.getValueAt(i, 3).toString());
                this.db.pst.setString(5, this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 4).toString()));
                this.db.pst.setString(6, this.tabelTr.getValueAt(i, 5).toString());
                this.db.pst.setString(7, this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString()));
                
                // eksekusi query
                this.db.pst.executeUpdate();
            }
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private void showUpdateTransaksi(){
        try{
            this.resetTabel();
            DefaultTableModel model = (DefaultTableModel) this.tabelTr.getModel();
            // membuat query untuk mendapatkan data dari dua tabel yaitu transaksi utama dan detail
            String sql = String.format(
                    "SELECT t.id_akun, t.nama_karyawan, t.total_menu, t.total_harga, t.ttl_diskon, t.total_bayar, t.total_kembalian, t.tanggal, "
                  + "d.id_menu, d.nama_menu, d.jenis_menu, d.harga_menu, d.jumlah, d.total_harga "
                  + "FROM transaksi_jual AS t "
                  + "JOIN detail_tr_jual AS d "
                  + "ON t.id_tr_jual = d.id_tr_jual "
                  + "WHERE d.id_tr_jual = '%s'",
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
                    this.idKaryawan = this.db.res.getString("t.id_akun");
                    this.namaKaryawan = this.db.res.getString("t.nama_karyawan");
                    this.ttlSeluruahHarga = this.db.res.getInt("t.total_harga");
                    this.diskonToko = this.db.res.getInt("t.ttl_diskon");
                    this.ttlBayar = this.db.res.getInt("t.total_bayar");
                    
                    // menampilkan data 
//                    this.inpKaryawan.setSelectedItem(String.format("%s | %s", this.idKaryawan, this.namaKaryawan));
                    this.showDiskon();
                    this.inpTotalHarga.setText(txt.toMoneyCase(this.ttlSeluruahHarga).substring(4));
                    this.inpTotalBayar.setText(Integer.toString(this.ttlBayar));
                }
                
                // menambahkan data detail transaksi ke tabel
                model.addRow(
                    new Object[]{
                        this.inpIdTransaksi.getText(),
                        this.db.res.getString("d.id_menu"),
                        this.db.res.getString("d.nama_menu"),
                        this.db.res.getString("d.jenis_menu"),
                        this.txt.toMoneyCase(this.db.res.getString("d.harga_menu")),
                        this.db.res.getInt("d.jumlah"),
                        this.txt.toMoneyCase(this.db.res.getString("d.total_harga"))
                    }
                );
            }
            
            // set fokus ke total bayar
            this.inpTotalBayar.requestFocus();
            this.hitungKembalian();
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

                // update id akun, nama karyawan, total menu, total harga dan tanggal
                String sql = String.format(
                        "UPDATE transaksi_jual SET id_akun = '%s', nama_karyawan = '%s', total_menu = %d, total_harga = %d, ttl_diskon = %d, "
                      + "total_bayar = %d, total_kembalian = %d, tanggal = '%s' "
                      + "WHERE id_tr_jual = '%s' ", 
                        User.getIdAkun(), User.getNamaUser(), this.getTotalJumlahMenu(), this.ttlSeluruahHarga, this.diskonToko, this.ttlBayar, this.ttlKembalian,  this.waktu.getCurrentDateTime(), this.inpIdTransaksi.getText()
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
                String.format("DELETE FROM detail_tr_jual WHERE id_tr_jual = '%s'", this.inpIdTransaksi.getText())
        ) > 0;
    }
    
    private void hitungKembalian(){
        // mendapatkan data
        this.ttlSeluruahHarga = this.getTotalHarga();
        this.ttlSeluruahHarga -= this.diskonToko;
        this.ttlBayar = this.inpTotalBayar.getText().isEmpty() ? -1 : Integer.parseInt(this.inpTotalBayar.getText());
        this.ttlKembalian = this.ttlBayar - this.ttlSeluruahHarga;
        
        // jika input total bayar kosong
        if(this.ttlBayar <= 0){
            this.inpTotalKembalian.setBackground(new Color(244,109,41));
            this.inpTotalKembalian.setText("Masukan Uang");
            this.isUangCukup = false;
        }
        // jika uang pas
        else if(this.ttlBayar == this.ttlSeluruahHarga){
            this.inpTotalKembalian.setText("Uang Pas");
            this.lblTotalKembalianRp.setText("");
            this.inpTotalKembalian.setBackground(new Color(15,172,49));
            this.isUangCukup = true;
        }
        // jika uang kurang
        else if(this.ttlBayar < this.ttlSeluruahHarga){
            this.inpTotalKembalian.setText("Uang Tidak Cukup");
            this.lblTotalKembalianRp.setText("");
            this.inpTotalKembalian.setBackground(new Color(242,47,47));
            this.isUangCukup = false;
        }
        // jika terdapat kembalian
        else if(this.ttlBayar > this.ttlSeluruahHarga){
            this.inpTotalKembalian.setText(this.txt.toMoneyCase(""+ttlKembalian).substring(4));
            this.lblTotalKembalianRp.setText("Rp");
            this.inpTotalKembalian.setBackground(new Color(48,119,242));
            this.isUangCukup = true;
        }
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
        lblIdMenu = new javax.swing.JLabel();
        inpIdMenu = new com.ui.RoundedTextField(50);
        inpNamaMenu = new com.ui.RoundedTextField(15);
        btnBayar = new javax.swing.JButton();
        btnUpdateMenu = new javax.swing.JToggleButton();
        btnHapus = new javax.swing.JToggleButton();
        lblHarga = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        inpCariMenu = new javax.swing.JLabel();
        lblServerTime = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
        lblTotalHargaRp = new javax.swing.JLabel();
        inpTotalHarga = new javax.swing.JTextField();
        lblDiskonToko = new javax.swing.JLabel();
        inpDiskonToko = new javax.swing.JTextField();
        lblJumlah = new javax.swing.JLabel();
        inpJumlah = new com.ui.RoundedTextField(15);
        butNutupinBugAja = new javax.swing.JLabel();
        lblIdTransaksi = new javax.swing.JLabel();
        lblNamaMenu = new javax.swing.JLabel();
        btnPengaturan = new javax.swing.JLabel();
        lblTotalBayar = new javax.swing.JLabel();
        lblTotalBayarRp = new javax.swing.JLabel();
        inpTotalBayar = new javax.swing.JTextField();
        lblTotalKembalian = new javax.swing.JLabel();
        lblTotalKembalianRp = new javax.swing.JLabel();
        inpTotalKembalian = new javax.swing.JTextField();
        btnHistori = new javax.swing.JLabel();
        inpIdTransaksi = new com.ui.RoundedTextField(15);
        lblDiskonTokoRp = new javax.swing.JLabel();
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
        btnKaryawan.setText("Data Akun");
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

        lblIconWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-topleft-trjual.png"))); // NOI18N

        lblNamaWindow.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblNamaWindow.setForeground(new java.awt.Color(0, 21, 39));
        lblNamaWindow.setText("Transaksi Penjualan");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTopInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
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
                "ID Transaksi", "ID Menu", "Nama Menu", "Jenis Menu", "Harga Menu", "Jumlah", "Total Harga"
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tabelTrKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tabelTr);

        lineCenter.setBackground(new java.awt.Color(0, 0, 0));
        lineCenter.setForeground(new java.awt.Color(0, 0, 0));

        lblIdMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblIdMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idmenu.png"))); // NOI18N
        lblIdMenu.setText("ID Menu");

        inpIdMenu.setBackground(new java.awt.Color(248, 249, 250));
        inpIdMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpIdMenu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpIdMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        inpIdMenu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpIdMenu.setEnabled(false);
        inpIdMenu.setSelectionStart(5);
        inpIdMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpIdMenuMouseClicked(evt);
            }
        });

        inpNamaMenu.setBackground(new java.awt.Color(248, 249, 250));
        inpNamaMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaMenu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpNamaMenu.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpNamaMenu.setEnabled(false);
        inpNamaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpNamaMenuMouseClicked(evt);
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

        btnUpdateMenu.setBackground(new java.awt.Color(34, 119, 237));
        btnUpdateMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-tambah.png"))); // NOI18N
        btnUpdateMenu.setText("Tambah");
        btnUpdateMenu.setMaximumSize(new java.awt.Dimension(109, 25));
        btnUpdateMenu.setMinimumSize(new java.awt.Dimension(109, 25));
        btnUpdateMenu.setPreferredSize(new java.awt.Dimension(109, 25));
        btnUpdateMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateMenuMouseExited(evt);
            }
        });
        btnUpdateMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateMenuActionPerformed(evt);
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
        lblHarga.setText("Harga Menu");

        inpHarga.setBackground(new java.awt.Color(248, 249, 250));
        inpHarga.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpHarga.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpHarga.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpHarga.setEnabled(false);
        inpHarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpHargaMouseClicked(evt);
            }
        });

        inpCariMenu.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        inpCariMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        inpCariMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpCariMenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                inpCariMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                inpCariMenuMouseExited(evt);
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
        inpTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTotalHargaActionPerformed(evt);
            }
        });

        lblDiskonToko.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDiskonToko.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idtransaksii.png"))); // NOI18N
        lblDiskonToko.setText("Diskon Toko");

        inpDiskonToko.setBackground(new java.awt.Color(237, 239, 242));
        inpDiskonToko.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpDiskonToko.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpDiskonToko.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpDiskonToko.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpDiskonToko.setEnabled(false);
        inpDiskonToko.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpDiskonTokoMouseClicked(evt);
            }
        });

        lblJumlah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblJumlah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-jumlah.png"))); // NOI18N
        lblJumlah.setText("Jumlah");

        inpJumlah.setBackground(new java.awt.Color(248, 249, 250));
        inpJumlah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJumlah.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpJumlahActionPerformed(evt);
            }
        });
        inpJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpJumlahKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpJumlahKeyTyped(evt);
            }
        });

        butNutupinBugAja.setForeground(new java.awt.Color(255, 255, 255));
        butNutupinBugAja.setText("Can You See Me?");

        lblIdTransaksi.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblIdTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-id.png"))); // NOI18N
        lblIdTransaksi.setText("ID Transaksi");

        lblNamaMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNamaMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblNamaMenu.setText("Nama Menu");

        btnPengaturan.setForeground(new java.awt.Color(0, 102, 255));
        btnPengaturan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnPengaturan.setText("Diskon Toko");
        btnPengaturan.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnPengaturan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPengaturanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPengaturanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPengaturanMouseExited(evt);
            }
        });

        lblTotalBayar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotalBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-harga-tanggal.png"))); // NOI18N
        lblTotalBayar.setText("Total Bayar");

        lblTotalBayarRp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalBayarRp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalBayarRp.setText("Rp");

        inpTotalBayar.setBackground(new java.awt.Color(248, 249, 250));
        inpTotalBayar.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpTotalBayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpTotalBayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpTotalBayar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpTotalBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpTotalBayarMouseClicked(evt);
            }
        });
        inpTotalBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTotalBayarActionPerformed(evt);
            }
        });
        inpTotalBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inpTotalBayarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpTotalBayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpTotalBayarKeyTyped(evt);
            }
        });

        lblTotalKembalian.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTotalKembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-kembalian.png"))); // NOI18N
        lblTotalKembalian.setText("Kembalian");

        lblTotalKembalianRp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotalKembalianRp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalKembalianRp.setText("Rp");

        inpTotalKembalian.setBackground(new java.awt.Color(248, 249, 250));
        inpTotalKembalian.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpTotalKembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpTotalKembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpTotalKembalian.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        inpTotalKembalian.setEnabled(false);
        inpTotalKembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpTotalKembalianMouseClicked(evt);
            }
        });
        inpTotalKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpTotalKembalianActionPerformed(evt);
            }
        });

        btnHistori.setForeground(new java.awt.Color(255, 0, 0));
        btnHistori.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnHistori.setText("Histori Transaksi Hari Ini");
        btnHistori.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnHistori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHistoriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHistoriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHistoriMouseExited(evt);
            }
        });

        inpIdTransaksi.setBackground(new java.awt.Color(248, 249, 250));
        inpIdTransaksi.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpIdTransaksi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpIdTransaksi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpIdTransaksi.setEnabled(false);
        inpIdTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpIdTransaksiMouseClicked(evt);
            }
        });

        lblDiskonTokoRp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDiskonTokoRp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiskonTokoRp.setText("Rp");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lineCenter)
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUpdateMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblServerTime, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE))))))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(butNutupinBugAja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlContentLayout.createSequentialGroup()
                                    .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlContentLayout.createSequentialGroup()
                                    .addComponent(lblIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(inpIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlContentLayout.createSequentialGroup()
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblNamaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                        .addComponent(lblIdMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(inpNamaMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnlContentLayout.createSequentialGroup()
                                            .addComponent(inpIdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inpCariMenu))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addComponent(lblTotalKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalKembalianRp, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                                        .addComponent(lblTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTotalBayarRp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblDiskonToko, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTotalHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblDiskonTokoRp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(inpTotalHarga)
                                        .addComponent(inpDiskonToko)
                                        .addComponent(inpTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(inpTotalKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addComponent(btnHistori, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPengaturan)))
                        .addGap(12, 12, 12)))
                .addGap(22, 22, 22))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblDiskonToko, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inpDiskonToko, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDiskonTokoRp, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalBayarRp, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(lblTotalBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalKembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTotalKembalianRp, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inpTotalKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(inpCariMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inpIdMenu)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpNamaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(lblNamaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inpJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(3, 3, 3)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butNutupinBugAja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPengaturan)
                            .addComponent(btnHistori))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdateMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblServerTime, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
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
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
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
        DataAkun window = new DataAkun();
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
        System.out.println(this.getClass().getName() + " closed");
        this.db.closeConnection();
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
        if(this.tabelTr.getRowCount() <= 0){
            Message.showWarning(this, "Tidak ada menu yang dipilih!");
            return;
        }else if(!this.isUangCukup){
            Message.showWarning(this, "Uang yang anda masukan kurang dari Rp. " + this.inpTotalHarga.getText());
            return;
        }

        this.setCursor(new java.awt.Cursor(Cursor.WAIT_CURSOR));
        // pengecekan mode transaksi
        // jika mode update
        if (this.isUpdateTr) {
            // melakukan update transaksi
            if (this.updateTransaksi()) {
                this.report.cetakStrukPenjualan(this.db.conn, this.idTransaksi);
                this.isUpdateTr = false;
                this.isEdit = false;
                this.changeButton();
                this.resetTransaksi();
            }
        } // jika mode tambah
        else {
            // melakukan transaksi baru
            if (this.transaksi()) {
                this.report.cetakStrukPenjualan(this.db.conn, this.idTransaksi);
                this.isEdit = false;
                this.changeButton();
                this.resetTransaksi();
            }
        }
        this.setCursor(new java.awt.Cursor(Cursor.DEFAULT_CURSOR));
        
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnUpdateMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMenuMouseEntered
        if(this.isEdit){
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-edit-entered.png"));
        }else{
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-tambah-entered.png"));
        }
    }//GEN-LAST:event_btnUpdateMenuMouseEntered

    private void btnUpdateMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMenuMouseExited
        if(this.isEdit){
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-edit.png"));
        }else{
            this.btnUpdateMenu.setIcon(Gambar.getIcon("ic-data-tambah.png"));
        }
    }//GEN-LAST:event_btnUpdateMenuMouseExited

    private void btnUpdateMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateMenuActionPerformed
        this.eventUpdateMenu();
    }//GEN-LAST:event_btnUpdateMenuActionPerformed

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
        
        String nmMenu = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 2).toString()+"?";
        int konf = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus " + nmMenu, "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        // cek konfirmasi delete
        if(konf == JOptionPane.YES_OPTION){
            this.hapusDataMenu();
            this.resetTambah();
            this.isEdit = false;
            this.changeButton();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void inpIdMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdMenuMouseClicked
        this.inpCariMenuMouseClicked(evt);
    }//GEN-LAST:event_inpIdMenuMouseClicked

    private void inpCariMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseClicked
        
        // membuka window untuk memilih menu
        GetDataMenu g = new GetDataMenu(null, true);
        g.setVisible(true);
        
        if(g.isSelected()){
            this.idMenu = g.getIdSelected();
            this.inpIdMenu.setText(idMenu);
            this.showMenuSelected();
            this.inpJumlah.setText("");
            this.inpJumlah.requestFocus();
            this.isEdit = false;
            this.changeButton();
        }
    }//GEN-LAST:event_inpCariMenuMouseClicked

    private void inpCariMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseEntered
        this.inpCariMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_inpCariMenuMouseEntered

    private void inpCariMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseExited
        this.inpCariMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpCariMenuMouseExited

    private void tabelTrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTrMouseClicked
        this.isEdit = true;
        this.showMenuFromTabel();
        this.changeButton();
    }//GEN-LAST:event_tabelTrMouseClicked

    private void tabelTrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyPressed

    }//GEN-LAST:event_tabelTrKeyPressed

    private void inpNamaMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpNamaMenuMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpNamaMenuMouseClicked

    private void inpHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpHargaMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpHargaMouseClicked

    private void inpJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyTyped
        this.txt.decimalOnly(evt);
    }//GEN-LAST:event_inpJumlahKeyTyped

    private void inpJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.eventUpdateMenu();
        }
    }//GEN-LAST:event_inpJumlahKeyPressed

    private void tabelTrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyTyped

    }//GEN-LAST:event_tabelTrKeyTyped

    private void tabelTrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyReleased
        // untuk editing data
        if(this.tabelTr.getSelectedColumn() == 5){
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                this.editDataMenu();
            }            
        }
        
        // untuk hapus data
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            String nmMenu = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 2).toString()+"?";
            int konf = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus " + nmMenu, "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            // cek konfirmasi delete
            if(konf == JOptionPane.YES_OPTION){
                this.hapusDataMenu();
            }
        }
    }//GEN-LAST:event_tabelTrKeyReleased

    private void inpJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJumlahActionPerformed
        
    }//GEN-LAST:event_inpJumlahActionPerformed

    private void btnPengaturanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPengaturanMouseClicked
        DiskonToko setting = new DiskonToko(null, true);
        setting.setVisible(true);
    }//GEN-LAST:event_btnPengaturanMouseClicked

    private void btnPengaturanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPengaturanMouseEntered
        this.btnPengaturan.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btnPengaturanMouseEntered

    private void btnPengaturanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPengaturanMouseExited
        this.btnPengaturan.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }//GEN-LAST:event_btnPengaturanMouseExited

    private void inpTotalBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpTotalBayarMouseClicked
        
    }//GEN-LAST:event_inpTotalBayarMouseClicked

    private void inpTotalBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTotalBayarActionPerformed
        
    }//GEN-LAST:event_inpTotalBayarActionPerformed

    private void inpTotalKembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpTotalKembalianMouseClicked
        
    }//GEN-LAST:event_inpTotalKembalianMouseClicked

    private void inpTotalKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTotalKembalianActionPerformed
        
    }//GEN-LAST:event_inpTotalKembalianActionPerformed
       
    private void inpTotalBayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpTotalBayarKeyTyped
        if(!this.inpTotalHarga.getText().isEmpty()){
            this.txt.decimalOnly(evt);
            this.hitungKembalian();
        }else{
            this.txt.decimalOnly(evt);
        }
    }//GEN-LAST:event_inpTotalBayarKeyTyped

    private void inpTotalBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpTotalBayarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.btnBayarActionPerformed(null);
        }
    }//GEN-LAST:event_inpTotalBayarKeyPressed

    private void inpTotalBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpTotalBayarKeyReleased
        if(!this.inpTotalHarga.getText().isEmpty()){
            this.txt.decimalOnly(evt);
            this.hitungKembalian();
        }
    }//GEN-LAST:event_inpTotalBayarKeyReleased

    private void btnHistoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoriMouseClicked
        HistoriTransaksi his = new HistoriTransaksi(null, true, HistoriTransaksi.STATUS_JUAL);
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
    }//GEN-LAST:event_btnHistoriMouseClicked

    private void btnHistoriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoriMouseEntered
        this.btnHistori.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_btnHistoriMouseEntered

    private void btnHistoriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoriMouseExited
        this.btnHistori.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnHistoriMouseExited

    private void inpIdTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdTransaksiMouseClicked
        JOptionPane.showMessageDialog(this, "ID Transaksi tidak bisa diedit!!");
    }//GEN-LAST:event_inpIdTransaksiMouseClicked

    private void inpTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTotalHargaActionPerformed

    }//GEN-LAST:event_inpTotalHargaActionPerformed

    private void inpTotalHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpTotalHargaMouseClicked
        //        JOptionPane.showMessageDialog(this, "Total harga akan terupdate otomatis saat menambahkan menu!!");
    }//GEN-LAST:event_inpTotalHargaMouseClicked

    private void inpDiskonTokoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpDiskonTokoMouseClicked
        JOptionPane.showMessageDialog(this, "Total harga akan terupdate otomatis saat menambahkan menu!!");
    }//GEN-LAST:event_inpDiskonTokoMouseClicked

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
            java.util.logging.Logger.getLogger(MenuTransaksiJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuTransaksiJual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBahan;
    private javax.swing.JButton btnBayar;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnDataMaster;
    private javax.swing.JToggleButton btnHapus;
    private javax.swing.JLabel btnHistori;
    private javax.swing.JLabel btnKaryawan;
    private javax.swing.JLabel btnLaporan;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnMenu;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JLabel btnPengaturan;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JToggleButton btnUpdateMenu;
    private javax.swing.JLabel butNutupinBugAja;
    private javax.swing.JLabel inpCariMenu;
    private javax.swing.JTextField inpDiskonToko;
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpIdMenu;
    private javax.swing.JTextField inpIdTransaksi;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JTextField inpNamaMenu;
    private javax.swing.JTextField inpTotalBayar;
    private javax.swing.JTextField inpTotalHarga;
    private javax.swing.JTextField inpTotalKembalian;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblDiskonToko;
    private javax.swing.JLabel lblDiskonTokoRp;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblIdMenu;
    private javax.swing.JLabel lblIdTransaksi;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNamaMenu;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblServerTime;
    private javax.swing.JLabel lblTopInfo;
    private javax.swing.JLabel lblTopProfile;
    private javax.swing.JLabel lblTopSetting;
    private javax.swing.JLabel lblTotalBayar;
    private javax.swing.JLabel lblTotalBayarRp;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JLabel lblTotalHargaRp;
    private javax.swing.JLabel lblTotalKembalian;
    private javax.swing.JLabel lblTotalKembalianRp;
    private javax.swing.JSeparator lineCenter;
    private javax.swing.JSeparator lineSideMenu1;
    private com.ui.RoundedPanel pnlContent;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlSidebar;
    private com.ui.RoundedPanel pnlTop;
    private javax.swing.JTable tabelTr;
    // End of variables declaration//GEN-END:variables
}
