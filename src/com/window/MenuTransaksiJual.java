package com.window;

import com.koneksi.Koneksi;
import com.manage.Message;
import com.manage.Text;
import com.ui.UIManager;
import com.manage.User;
import com.manage.Waktu;
import com.media.Gambar;
import com.window.dialog.GetDataMenu;
import com.window.dialog.InfoApp;
import com.window.dialog.Pengaturan;
import com.window.dialog.TransaksiSuccess;
import com.window.dialog.UserProfile;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
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
    
    private String idMenu = "", namaMenu, jenisMenu;
    
    private int hargaMenu // harga dari menu
               ,jumlah // jumlah menu yg dipilih
               ,ttlHrgMenu // total harga dari menu (harga menu * jumlah menu)
               ,ttlHargaBayar = 0 // total keseluruhan harga dari menu
               ,oldJumlah;
    
    private final UIManager win = new UIManager();
    
    private DefaultTableModel tblModel;
    
    private final Text txt = new Text();
    
    private final Waktu waktu = new Waktu();
    
    /**
     * Meyimpan data stok bahan dan stok menu sementara saat proses transaksi sedang berlangsung
     */
    private HashMap<String, Integer> tempStokBahan, tempStokMenu;
    
    private boolean status;
    
    public MenuTransaksiJual() {
        initComponents();
        this.setTitle("Menu Transaksi Penjualan");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
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
        this.btnTambah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
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
        this.inpIdMenu.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpPembeli.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpNamaMenu.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpHarga.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        this.inpJumlah.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0)); 
        
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        
        this.getAllStokBahan();
        this.getAllStokMenu();
    }
    
    /**
     * Auto increment id transaksi
     * 
     * @return 
     */
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM transaksi_jual ORDER BY id_tr_jual DESC LIMIT 0,1", lastID, nomor;
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("id_tr_jual");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(3);
                }else{
                    nomor = "0000";
                }
                conn.close();
                
                // jika id barang belum exist maka id akan dibuat
                return String.format("TRJ%04d", Integer.parseInt(nomor)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Menampilkan data menu yang diplih
     */
    private void showMenuSelected(){

        try (Connection c = (Connection) Koneksi.configDB()) {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM menu WHERE id_menu = '"+this.idMenu+"'");

            if(r.next()){
                this.inpIdMenu.setText(idMenu);
                // mendapatkan data menu
                this.namaMenu = r.getString("nama_menu");
                this.jenisMenu = r.getString("jenis");
                this.hargaMenu = Integer.parseInt(r.getString("harga"));
                // menampilkan data menu ke window
                this.inpNamaMenu.setText(namaMenu);
                this.inpHarga.setText(txt.toMoneyCase(""+hargaMenu));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
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
        
        // meyimpan update tabel
        tabelTr.setModel(model);
    }
    
    private void setColumnSize(){
        // setting panjang kolom
        TableColumnModel columnModel = tabelTr.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(110);
        columnModel.getColumn(0).setMaxWidth(110);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(1).setMaxWidth(110);
        columnModel.getColumn(2).setPreferredWidth(220);
        columnModel.getColumn(2).setMaxWidth(220);
        columnModel.getColumn(3).setPreferredWidth(220);
        columnModel.getColumn(3).setMaxWidth(220);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(4).setMaxWidth(160);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(5).setMaxWidth(80);
        columnModel.getColumn(6).setPreferredWidth(160);
        columnModel.getColumn(6).setMaxWidth(160);
    }
    
    private void resetTabel(){
        // setting tabel model
        tblModel = new DefaultTableModel(
                new String[][]{}, // default valuenya kosong
                new String [] {
                    "ID Transaksi", "ID Menu", "Nama Menu", "Jenis Menu", "Harga Menu", "Jumlah", "Total Harga"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                if(col == 5){
                    // col == 5 (data jumlah) hanya data jumlah yg bisa diedit di tabel secara langsung
                    return true;
                }else{
                    return false;
                }
            }
        };
        
        this.tabelTr.setModel(tblModel);
        
        // set ukuran kolom
        this.setColumnSize();
    }
    
    // resset saat menamkan tombol tambah menu
    private void resetTambah(){
        this.inpIdMenu.setText("");
        this.inpNamaMenu.setText("");
        this.inpHarga.setText("");
        this.inpJumlah.setText("");
        this.lblStokMenu.setText("");
    }
    
    // reset saat menekan tombol transaksi
    private void resetTransaksi(){
        // reset textfield dan data
        this.resetTambah();
        this.inpPembeli.setText("");
        this.inpTotalHarga.setText("");
        this.idMenu = null;
        this.namaMenu = null;
        this.hargaMenu = 0;
        this.ttlHargaBayar = 0;
        this.ttlHrgMenu = 0;
        this.jumlah = 0;
        
        // reset tabel
        this.resetTabel();
        
        // reset total menu
        this.getAllStokBahan();
        this.getAllStokMenu();
        
        // update id transaksi
        this.inpIdTransaksi.setText(this.createID());
    }
    
    private void tambahMenu(){
        // cek apakah data menu sudah dipilih
        if(!this.inpIdMenu.getText().isEmpty()){
            // cek apakah data jumlah sudah dimasukan
            if (this.inpJumlah.getText().isEmpty()) {
                Message.showWarning(this, "Jumlah menu belum dimasukan!");
            } else {
                this.tambahDataMenu();
            }                
        }else{
            Message.showWarning(this, "Tidak ada data menu yang dipilih!");
        }
    }
    
    // untuk menambahkan data menu
    private void tambahDataMenu(){
        // update data total harga
        this.jumlah = Integer.parseInt(this.inpJumlah.getText());
        this.ttlHrgMenu = hargaMenu * jumlah;
        this.ttlHargaBayar += this.ttlHrgMenu;
        this.inpTotalHarga.setText(this.txt.toMoneyCase(""+ttlHargaBayar).substring(4));
        // mengurangi stok menu
        this.updateStokMenu(this.idMenu, this.jumlah, "min");
        // reset tabel dan textfield
        this.updateTabel();
        this.resetTambah();
    }
    
    private void editDataMenu(){
        try{
            int row = this.tabelTr.getSelectedRow(), 
                // mendapatkan jumlah menu yang baru
                newJml = Integer.parseInt(this.tabelTr.getValueAt(row, 5).toString()),
                // mendapatkan data harga dari menu    
                harga = Integer.parseInt(txt.removeMoneyCase(this.tabelTr.getValueAt(row, 4).toString())),
                // mendapatkan data total harga menu yang lama
                oldTotalHarga = Integer.parseInt(txt.removeMoneyCase(this.tabelTr.getValueAt(row, 6).toString())),
                newTotalHarga;
            
            if(newJml >= 1){
                // menghitung total harga menu yang baru (jumlah menu * harga menu)
                newTotalHarga = newJml * harga;
                
                // mengupdate total harga bayar
                this.ttlHargaBayar = this.ttlHargaBayar - oldTotalHarga;
                this.ttlHargaBayar = this.ttlHargaBayar + newTotalHarga;

                // mengupdate data yang ada di textfield dan di tabel
                this.tabelTr.setValueAt(txt.toMoneyCase(""+newTotalHarga), row, 6);
                this.inpTotalHarga.setText(txt.toMoneyCase(""+this.ttlHargaBayar));
                
                // mengupdate stok menu
                if(newJml > this.oldJumlah){
                    newJml = newJml - this.oldJumlah;
                    System.out.println("TAMBAH PESANAN");
                    System.out.println("NEW JUMLAH : " + newJml);
                    System.out.println("OLD JUMLAH : " + oldJumlah);
                    this.updateStokMenu(this.tabelTr.getValueAt(row, 1).toString(), newJml, "min");
                }else if(newJml < this.oldJumlah){
                    newJml = oldJumlah - newJml;
                    System.out.println("KURANGI PESANAN");
                    System.out.println("NEW JUMLAH : " + newJml);
                    System.out.println("OLD JUMLAH : " + oldJumlah);
                    this.updateStokMenu(this.tabelTr.getValueAt(row, 1).toString(), newJml, "add");
                }
            }
            
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Jumlah menu harus berupa Angka!");
        }
    }
    
    // untuk menghapus data menu pada tabel
    private void hapusDataMenu() {
        if(this.tabelTr.getSelectedRow() > -1){
            // mendapatkan total harga menu
            String hrg = this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 6).toString().substring(4).replaceAll(",", "").replaceAll("\\.", "");
            // mengurangi total harga bayar dengan total harga menu
            this.ttlHargaBayar = this.ttlHargaBayar - Integer.parseInt(hrg.substring(0, hrg.length()-2));
            // menampilkan ulang total harga bayar pada textfield
            this.inpTotalHarga.setText(txt.toMoneyCase(""+this.ttlHargaBayar).substring(4));
            
            int row = tabelTr.getSelectedRow();
            
            // update stok menu
            this.updateStokMenu(
                    this.tabelTr.getValueAt(row, 1).toString(), 
                    Integer.parseInt(this.tabelTr.getValueAt(row, 5).toString()),
                    "add"
            );
            
            // menghapus baris pada tabel
            DefaultTableModel model = (DefaultTableModel) tabelTr.getModel();
            model.removeRow(row);            
            
            // update stok menu
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
    
    /**
     * Digunakan untuk mendapatkan semua stok bahan dari tabel database dan outputnya disimpan dalam hashmap
     */
    private void getAllStokBahan(){
        try{
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_bahan, stok FROM bahan");
            // inisialisasi hashmap
            tempStokBahan = new HashMap<>();
            
            // membaca semua data yang ada didalam tabel bahan
            while(r.next()){
                // menyimpan id bahan dan stok kedalam hashmap
                tempStokBahan.put(r.getString("id_bahan"), r.getInt("stok"));
            }
            
            // menutup koneksi
            s.close();
            r.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
    }
    
    /**
     * Digunakan untuk menampilkan semua data stok bahan
     */
    private void showStokBahan(){
        // konversi hashmap ke object
        Object obj[] = this.tempStokBahan.entrySet().toArray();
        // membaca semua data
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk mendapatkan data stok bahan sementara yang ada di hashmap
     * 
     * @param idBahan id bahan yang akan diambil stoknya
     * @return stok sementara dari bahan
     */
    private int getTempStokBahan(String idBahan){
        return this.tempStokBahan.get(idBahan);
    }
    
    /**
     * Digunakan untuk mengubah data stok bahan sementara yang ada di hashmap
     * 
     * @param idBahan id bahan yang akan ubah data stoknya
     * @param newStok stok baru dari bahan
     */
    private void setTempStokBahan(String idBahan, int newStok){
        this.tempStokBahan.replace(idBahan, newStok);
    }
    
    /**
     * Digunakan untuk mendapatkan bahan-bahan dari menu yang akan dipesan
     * 
     * @param idMenu id dari menu yang akan diambil bahan-bahanya
     * @return id bahan dan jumlah bahan dari menu
     */
    private HashMap<String, Integer> getBahanMenu(String idMenu){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_bahan, quantity FROM detail_menu WHERE id_menu = '"+idMenu+"'");
            // menyimpan data sementara dari bahan
            HashMap<String, Integer> data = new HashMap();
            
            // mendapatkan data bahan-bahan
            while(r.next()){
                data.put(r.getString("id_bahan"), r.getInt("quantity"));
            }
            
            c.close();
            s.close();
            r.close();
            
            return data;
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return null;
    }

    /**
     * Digunakan untuk menampilkan bahan-bahan dari menu
     * 
     * @param idMenu id menu yang akan ditampilkan bahan-bahannya
     */
    private void showBahanMenu(String idMenu){
        Object[] obj = this.getBahanMenu(idMenu).entrySet().toArray();
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk menghitung ketersediaan stok dari menu
     * 
     * @param idMenu id menu yang akan dicek
     * @return stok dari menu
     */
    private int hitungStokMenu(String idMenu){
        String idBahan;
        StringTokenizer token; // meyimpan token dari id bahan
        Object[] bahanMenu =  this.getBahanMenu(idMenu).entrySet().toArray(); // mendapatkan data bahan-bahan dari menu dalam bentuk token
        int cekStok[] = new int[bahanMenu.length]; // menyimpan stok sementara dari menu
        int stokBahan, quantity;

        for(int i = 0; i < bahanMenu.length; i++){
            // mendapatkan token dari data bahan menu (contoh token : BA001=10) 
            token = new StringTokenizer(bahanMenu[i].toString(), "=");
            // mendapatkan data id bahan dari token
            idBahan = token.nextToken();
            // mendapatkan jumlah bahan dari token
            quantity = Integer.parseInt(token.nextToken());
            // mendapatkan stok dari bahan
            stokBahan = this.getTempStokBahan(idBahan);
            
            // menghindari error pembagian degan 0
            if(quantity == 0){
                return 0;
            }
            
            // menghitung stok dengan cara membagi stok dari bahan dengan quantity bahan
            cekStok[i] = (int)stokBahan / quantity; 
//            System.out.printf("\nId Bahan : %s \nStok Bahan : %d \nQuantity : %d \nStok : %d\n", idBahan, stokBahan, quantity, cekStok[i]);
        }
        
        // mendapatkan stok terendah dari bahan bahan menu
        Arrays.sort(cekStok);
        return cekStok[0];
    }
    
    /**
     * Digunakan untuk mendapatkan semua stok menu
     */
    private void getAllStokMenu(){
        try{
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_menu FROM menu");
            String id;
            // inisialisasi hashmap
            tempStokMenu = new HashMap();
            
            // membaca semua data menu
            while(r.next()){
                id = r.getString("id_menu");
                // menghitung stok dari menu
                tempStokMenu.put(id, this.hitungStokMenu(id));
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
    }
    
    /**
     * Menampilkan semua stok dari menu
     */
    private void showStokMenu(){
        // konversi hashmap ke array objct
        Object[] obj = this.tempStokMenu.entrySet().toArray();
        // menampilkan semua data
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk mengupdate stok dari menu saat terjadi proses transaksi penjualan
     * 
     * @param idMenu id menu yang dipesan
     * @param jumlah jumlah menu yang dipesan
     * @param status <ul> <li>add : menambahkan menu </li>
     *                    <li>min : mengurangi menu </li>
     *               </ul>
     */
    private void updateStokMenu(String idMenu, int jumlah, String status){
        int quantity, stokBahan = 0;
        String idBahan;
        StringTokenizer token; // menyimpan token dari data-data bahan
        Object[] dataBahan = this.getBahanMenu(idMenu).entrySet().toArray(); // mendapatkan data bahan-bahan dari menu
        
        for (Object bahan : dataBahan) {
            // mendapatkan token dari data bahan menu (contoh token : BA001=10) 
            token = new StringTokenizer(bahan.toString(), "=");
            // mendapatkan data id bahan dari token
            idBahan = token.nextToken();
            // mendapatkan jumlah bahan dari token
            quantity = Integer.parseInt(token.nextToken());
            
            // cek status tambah atau mengurangi menu
            switch(status){
                case "add":
                    // mendapatkan menghitung stok bahan baru
                    stokBahan = this.getTempStokBahan(idBahan) + (quantity * jumlah);
                    break;
                case "min":
                    // mendapatkan menghitung stok bahan baru
                    stokBahan = this.getTempStokBahan(idBahan) - (quantity * jumlah);
                    break;
            }
            
            // meyimpan stok bahan baru pada hashmap
            this.setTempStokBahan(idBahan, stokBahan);
        }
        // hitung ulang stok menu
        this.getAllStokMenu();
    }
    
    private boolean trJual(){
        try{
            Connection c = (Connection) Koneksi.configDB();
            PreparedStatement p = c.prepareStatement("INSERT INTO transaksi_jual VALUES (?, ?, ?, ?, ?, ?)");
            p.setString(1, this.inpIdTransaksi.getText());
            p.setString(2, User.getIDKaryawan());
            p.setString(3, this.inpPembeli.getText()+"");
            p.setInt(4, this.getTotalJumlahMenu());
            p.setInt(5, this.ttlHargaBayar);
            p.setString(6, waktu.getCurrentDateTime());
            
            boolean isSuccess = p.executeUpdate() > 0;
            c.close();
            p.close();
            
            if(isSuccess){
                boolean detail = this.detailTrJual(),
                        log = this.triggerTrJual();
                
                return detail && log;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private boolean detailTrJual(){
        try{
            Connection c = (Connection) Koneksi.configDB();
            PreparedStatement p;
            
            for(int i = 0; i < this.tabelTr.getRowCount(); i++){
                p = c.prepareStatement("INSERT INTO detail_tr_jual VALUES (?, ?, ?, ?, ?, ?, ?)");
                p.setString(1, this.inpIdTransaksi.getText());
                p.setString(2, this.tabelTr.getValueAt(i, 1).toString());
                p.setString(3, this.tabelTr.getValueAt(i, 2).toString());
                p.setString(4, this.tabelTr.getValueAt(i, 3).toString());
                p.setString(5, this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 4).toString()));
                p.setString(6, this.tabelTr.getValueAt(i, 5).toString());
                p.setString(7, this.txt.removeMoneyCase(this.tabelTr.getValueAt(i, 6).toString()));
                
                p.executeUpdate();
                p.close();
            }
            c.close();
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private boolean triggerTrJual(){
        try{
            Connection c = (Connection) Koneksi.configDB();
            PreparedStatement p;
            HashMap data;
            
            for(int i = 0; i < this.tabelTr.getRowCount(); i++){
                String id = this.tabelTr.getValueAt(i, 1).toString();
                data = this.getBahanMenu(idMenu);
                Object[] idBahan = data.keySet().toArray(),
                         quantity = data.values().toArray();
                for(int j = 0; j < idBahan.length; j++){
                    System.out.println("id bahan : "+idBahan[j].toString());
                    System.out.println("quantity : "+quantity[j].toString());
                    p = c.prepareStatement("INSERT INTO log_tr_jual VALUES (?, ?, ?, ?)");
                    p.setString(1, this.inpIdTransaksi.getText());
                    p.setString(2, id);
                    p.setString(3, idBahan[j].toString());
                    p.setInt(4, (Integer.parseInt(quantity[j].toString()) * Integer.parseInt(this.tabelTr.getValueAt(i, 5).toString())));
                    
                    p.executeUpdate();
                    p.close();
                }                
            }
            c.close();
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
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
        lblPembeli = new javax.swing.JLabel();
        inpPembeli = new com.ui.RoundedTextField(15);
        lblIdMenu = new javax.swing.JLabel();
        inpIdMenu = new com.ui.RoundedTextField(50);
        lblNamaMenu = new javax.swing.JLabel();
        inpNamaMenu = new com.ui.RoundedTextField(15);
        btnBayar = new javax.swing.JButton();
        btnTambah = new javax.swing.JToggleButton();
        btnHapus = new javax.swing.JToggleButton();
        lblHarga = new javax.swing.JLabel();
        inpHarga = new com.ui.RoundedTextField(15);
        inpCariMenu = new javax.swing.JLabel();
        lblServerTime = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
        lblTotalHargaRp = new javax.swing.JLabel();
        inpTotalHarga = new javax.swing.JTextField();
        lblIDTransaksi = new javax.swing.JLabel();
        inpIdTransaksi = new javax.swing.JTextField();
        lblJumlah = new javax.swing.JLabel();
        inpJumlah = new com.ui.RoundedTextField(15);
        lblStokMenu = new javax.swing.JLabel();
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
                            .addComponent(lblNamaUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProfileSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSidebarLayout.createSequentialGroup()
                        .addGap(0, 29, Short.MAX_VALUE)
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
                .addContainerGap(131, Short.MAX_VALUE))
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

        lblPembeli.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPembeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-id.png"))); // NOI18N
        lblPembeli.setText("Nama Pembeli");

        inpPembeli.setBackground(new java.awt.Color(248, 249, 250));
        inpPembeli.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpPembeli.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        inpPembeli.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpPembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpPembeliMouseClicked(evt);
            }
        });

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

        lblNamaMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNamaMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-menu.png"))); // NOI18N
        lblNamaMenu.setText("Nama Menu");

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

        btnTambah.setBackground(new java.awt.Color(34, 119, 237));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-tambah.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.setMaximumSize(new java.awt.Dimension(109, 25));
        btnTambah.setMinimumSize(new java.awt.Dimension(109, 25));
        btnTambah.setPreferredSize(new java.awt.Dimension(109, 25));
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahMouseExited(evt);
            }
        });
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
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

        lblIDTransaksi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblIDTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-data-idtransaksii.png"))); // NOI18N
        lblIDTransaksi.setText("ID Transaksi");

        inpIdTransaksi.setBackground(new java.awt.Color(237, 239, 242));
        inpIdTransaksi.setFont(new java.awt.Font("Dialog", 1, 22)); // NOI18N
        inpIdTransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpIdTransaksi.setText("TRJ00001");
        inpIdTransaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 99, 210), 2));
        inpIdTransaksi.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        inpIdTransaksi.setEnabled(false);
        inpIdTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpIdTransaksiMouseClicked(evt);
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

        lblStokMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblStokMenu.setText(" ");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblIdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpIdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpCariMenu))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpPembeli))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblStokMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblIDTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(inpTotalHarga)
                            .addComponent(inpIdTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lineCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(164, 164, 164)
                                    .addComponent(lblServerTime, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(13, 13, 13))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPembeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpPembeli, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIdMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpIdMenu)
                            .addComponent(inpCariMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inpJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblStokMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIDTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalHargaRp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inpTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(lineCenter, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(pnlMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataMenu window = new DataMenu();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
                window.setLocation(getX(), getY());
                window.setVisible(true);
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
//        Message.showInformation(this, "Transaksi berhasil!");
        if(this.trJual()){
            TransaksiSuccess dia = new TransaksiSuccess(null, true, this.inpIdTransaksi.getText());
            dia.setVisible(true);
            this.resetTransaksi();            
        }
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnTambahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseEntered
        this.btnTambah.setIcon(Gambar.getIcon("ic-data-tambah-entered.png"));
    }//GEN-LAST:event_btnTambahMouseEntered

    private void btnTambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseExited
        this.btnTambah.setIcon(Gambar.getIcon("ic-data-tambah.png"));
    }//GEN-LAST:event_btnTambahMouseExited

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        this.tambahMenu();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        this.hapusDataMenu();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void inpIdMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdMenuMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpIdMenuMouseClicked

    private void inpCariMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseClicked
        GetDataMenu g = new GetDataMenu(null, true, this.tempStokMenu);
        g.setVisible(true);
        
        if(g.isSelected()){
            this.idMenu = g.getIdSelected();
            this.inpIdMenu.setText(idMenu);
            this.showMenuSelected();
            this.inpJumlah.requestFocus();
            this.lblStokMenu.setText("/ "+tempStokMenu.get(this.idMenu) + " Pesanaan");
        }
    }//GEN-LAST:event_inpCariMenuMouseClicked

    private void inpCariMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseEntered
        this.inpCariMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_inpCariMenuMouseEntered

    private void inpCariMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpCariMenuMouseExited
        this.inpCariMenu.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpCariMenuMouseExited

    private void tabelTrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTrMouseClicked
        this.oldJumlah = Integer.parseInt(this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 5).toString());
        System.out.println("old jumlah : " + oldJumlah);
    }//GEN-LAST:event_tabelTrMouseClicked

    private void tabelTrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelTrKeyPressed
//        this.oldJumlah = Integer.parseInt(this.tabelTr.getValueAt(this.tabelTr.getSelectedRow(), 5).toString());
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_UP){
            this.oldJumlah = Integer.parseInt(this.tabelTr.getValueAt(tabelTr.getSelectedRow() - 1, 5).toString());
        }else if(evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_DOWN){
            this.oldJumlah = Integer.parseInt(this.tabelTr.getValueAt(tabelTr.getSelectedRow() + 1, 5).toString());
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        System.out.println("old jumlah : " + this.oldJumlah);
    }//GEN-LAST:event_tabelTrKeyPressed

    private void inpPembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpPembeliMouseClicked
//        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data pembeli!!");
    }//GEN-LAST:event_inpPembeliMouseClicked

    private void inpNamaMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpNamaMenuMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpNamaMenuMouseClicked

    private void inpHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpHargaMouseClicked
        JOptionPane.showMessageDialog(this, "Tekan tombol cari untuk mengedit data menu!!");
    }//GEN-LAST:event_inpHargaMouseClicked

    private void inpIdTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpIdTransaksiMouseClicked
        JOptionPane.showMessageDialog(this, "ID Transaksi tidak bisa diedit!!");
    }//GEN-LAST:event_inpIdTransaksiMouseClicked

    private void inpTotalHargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpTotalHargaMouseClicked
        JOptionPane.showMessageDialog(this, "Total harga akan terupdate otomatis saat menambahkan menu!!");
    }//GEN-LAST:event_inpTotalHargaMouseClicked

    private void inpJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyTyped
        this.txt.filterAngka(evt);
        this.txt.filterChar(evt);
    }//GEN-LAST:event_inpJumlahKeyTyped

    private void inpJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpJumlahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(Integer.parseInt(this.inpJumlah.getText()) > this.tempStokMenu.get(this.inpIdMenu.getText())){
                Message.showWarning(this, "Jumlah stok tidak cukup!");
            }else if(Integer.parseInt(this.inpJumlah.getText()) == 0){
                Message.showWarning(this, "Jumlah stok tidak boleh 0");
            }else{
                this.tambahMenu();
            }
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
            this.hapusDataMenu();
        }
    }//GEN-LAST:event_tabelTrKeyReleased

    private void inpTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpTotalHargaActionPerformed

    private void inpJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inpJumlahActionPerformed

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
    private javax.swing.JLabel btnKaryawan;
    private javax.swing.JLabel btnLaporan;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnMenu;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JToggleButton btnTambah;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JLabel inpCariMenu;
    private javax.swing.JTextField inpHarga;
    private javax.swing.JTextField inpIdMenu;
    private javax.swing.JTextField inpIdTransaksi;
    private javax.swing.JTextField inpJumlah;
    private javax.swing.JTextField inpNamaMenu;
    private javax.swing.JTextField inpPembeli;
    private javax.swing.JTextField inpTotalHarga;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblIDTransaksi;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblIdMenu;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNamaMenu;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblPembeli;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblServerTime;
    private javax.swing.JLabel lblStokMenu;
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
