package com.window.laporan;

import com.window.transaksi.MenuTransaksi;
import com.koneksi.Database;
import com.manage.ChartManager;
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
import com.window.dialog.InfoApp;
import com.window.dialog.Pengaturan;
import com.window.dialog.UserProfile;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class MenuLaporanJual extends javax.swing.JFrame {
    
    
    private final Database db = new Database(); 
    
    private DefaultTableModel modelCariLaporan;
    
    private final UIManager win = new UIManager();
    
    private final ChartManager chart = new ChartManager();
    
    private final Waktu waktu = new Waktu();
    
    private final Text txt = new Text();
    
    private String namaBulan = "???", filterTglRiwayat = "";
    
    private int trBulanan = 0, psBulanan = 0, pdBulanan = 0;

    public MenuLaporanJual() {
        initComponents();
        
        this.setTitle("Menu Laporan Penjualan");
        this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setIconImage(Gambar.getWindowIcon());
        this.lblNamaUser.setText(User.getNamaUser());
        this.lblNamaWindow.setText("Laporan Penjualan Harian");
        
        // set hover button
        this.win.btns = new JLabel[]{
            this.btnKaryawan, this.btnSupplier, this.btnPembeli, 
            this.btnDashboard, this.btnTransaksi, this.btnBahan, this.btnLogout, this.btnMenu
        };
        this.win.hoverButton();
        
        // set ui button
        this.btnCetakBulanan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnRiwayatBulanan.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDetailHarian.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnCetakHarian.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDetailHarian.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnSemuaHarian.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnUpdate.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnTgl.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // hidden button
        this.btnPembeli.setVisible(false);
        this.btnLogout.setVisible(false);
        this.btnSupplier.setVisible(false);
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                // menampilkan data 
                showLaporanHarian("");
                showDataLaporanHarian();
                showLaporanBulanan();
                showDataLaporanBulanan();
                showRiwayatTransaksi();
                showDataRiwayat();                
            }
        }).start();
    }
        
    private void resetTableLpHarian(){
        // set desain tabel
        this.tabelLpHarian.setRowHeight(29);
        this.tabelLpHarian.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelLpHarian.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelLpHarian.setModel(new javax.swing.table.DefaultTableModel(
                new String[][]{},
                new String[]{
                    "ID Transaksi", "ID Akun", "Nama Karyawan", "Total Pesanan", "Sub Total", "Total Diskon",  "Tanggal", "Waktu"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // set size kolom tabel
        TableColumnModel columnModel = this.tabelLpHarian.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(0).setMaxWidth(90);
        columnModel.getColumn(1).setPreferredWidth(65);
        columnModel.getColumn(1).setMaxWidth(65);
        columnModel.getColumn(2).setPreferredWidth(210);
        columnModel.getColumn(2).setMaxWidth(210);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(4).setMaxWidth(120);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(5).setMaxWidth(120);
        columnModel.getColumn(6).setPreferredWidth(200);
        columnModel.getColumn(6).setMaxWidth(200);
//        columnModel.getColumn(5).setPreferredWidth(210);
//        columnModel.getColumn(5).setMaxWidth(210);
    }
    
    private void showLaporanHarian(String kondisi){
        
        // mereset tabel laporan harian
        this.resetTableLpHarian();
        DefaultTableModel model = (DefaultTableModel) this.tabelLpHarian.getModel();
        
        try{
            // query untuk mengambil data laporan
            String sql = String.format(
                    "SELECT trj.id_tr_jual, trj.id_akun, trj.nama_karyawan, trj.total_menu, trj.sub_total, trj.tanggal, trj.ttl_diskon, DAYNAME(trj.tanggal) AS hari, TIME(trj.tanggal) AS waktu "
                  + "FROM transaksi_jual AS trj "
                  + kondisi 
                  + " ORDER BY trj.id_tr_jual DESC "
                  + "LIMIT 0,1000"
            );
            System.out.println(sql);

            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // membaca semua data yang ada didalam tabel
            while(this.db.res.next()){
                // menambahkan data kedalam tabel
                model.addRow(
                    new String[]{
                        this.db.res.getString("trj.id_tr_jual"), 
                        this.db.res.getString("trj.id_akun"),
                        this.db.res.getString("trj.nama_karyawan"),
                        this.db.res.getString("trj.total_menu") + " Pesanan", 
                        txt.toMoneyCase(this.db.res.getString("trj.sub_total")), 
                        txt.toMoneyCase(this.db.res.getString("trj.ttl_diskon")),
                        waktu.getNamaHariInIndonesian(this.db.res.getString("hari")) + ", " + txt.toDateCase(this.db.res.getString("tanggal")),
                        this.db.res.getString("waktu")
                    }
                );
            }
            
            // menampilkan data tabel
            this.tabelLpHarian.setModel(model);
            this.modelCariLaporan = model;
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
    }
    
    private void showDataLaporanHarian(){
        // jika tidak ada data pada tabel
        if(this.tabelLpHarian.getRowCount() < 1){
            this.lblTotalTrHarian.setText(" Transaksi : 0");
            this.lblTotalPsHarian.setText(" Pesananan : 0");
            this.lblTotalPdtHarian.setText(" Pendapatan : Rp. 00");
            return;
        }
        
        // mendapatkan total transaksi
        int transaksi = this.tabelLpHarian.getRowCount(), 
            pesanan = 0, pendapatan = 0;
        
        // menghitung data pesanan dan pendapatan
        for(int i = 0; i < this.tabelLpHarian.getRowCount(); i++){
            pesanan += Integer.parseInt(this.tabelLpHarian.getValueAt(i, 3).toString().replace(" Pesanan", ""));
            pendapatan += Integer.parseInt(txt.removeMoneyCase(this.tabelLpHarian.getValueAt(i, 4).toString()));
        }
        
        // menampilkan data
        this.lblTotalTrHarian.setText(String.format(" Transaksi : %,d", transaksi));
        this.lblTotalPsHarian.setText(String.format(" Pesanan : %,d", pesanan));
        this.lblTotalPdtHarian.setText(String.format(" Pendapatan : %s", txt.toMoneyCase(Integer.toString(pendapatan))));
    }
    
    private void cariLaporanHarian(){
        // reset tabel laporan
        this.resetTableLpHarian();
        DefaultTableModel model = (DefaultTableModel) this.tabelLpHarian.getModel();
        String key = inpCariHarian.getText().toLowerCase(), id, nama, idKy, tanggal;
        
        // membaca semua is tabel laporan
        for(int i = 0; i < this.modelCariLaporan.getRowCount(); i++){
            // mendapatkan data id, nama dan tanggal
            id = this.modelCariLaporan.getValueAt(i, 0).toString().toLowerCase();
            idKy = this.modelCariLaporan.getValueAt(i, 1).toString().toLowerCase();
            nama = this.modelCariLaporan.getValueAt(i, 2).toString().toLowerCase();
            tanggal = this.modelCariLaporan.getValueAt(i, 6).toString().toLowerCase();
            
            // pengecekan id, nama dan tanggal
            if(id.contains(key) || idKy.contains(key) || nama.contains(key) || tanggal.contains(key)){
                // jika match maka data ditampilkan kedalam tabel
                model.addRow(
                    new Object[]{
                        id.toUpperCase(), 
                        this.modelCariLaporan.getValueAt(i, 1),
                        this.modelCariLaporan.getValueAt(i, 2),
                        this.modelCariLaporan.getValueAt(i, 3),
                        this.modelCariLaporan.getValueAt(i, 4),
                        this.modelCariLaporan.getValueAt(i, 5),
                        this.modelCariLaporan.getValueAt(i, 6),
                        this.modelCariLaporan.getValueAt(i, 7)
                    }
                );
            }
        }
        
        // refresh tabel laporan harian
        this.tabelLpHarian.setModel(model);
        // refresh total data
        this.showDataLaporanHarian();
    }
    
    private void resetTableLpBulanan(){
        // set desain tabel
        this.tabelLpBulanan.setRowHeight(29);
        this.tabelLpBulanan.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelLpBulanan.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
        
        // set model tabel
        this.tabelLpBulanan.setModel(new javax.swing.table.DefaultTableModel(
            new String[][]{},
            new String [] {
                "Bulan", "Tahun", "Pembeli", "Pesanan", "Total Pendapatan"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // set size kolom tabel
        TableColumnModel columnModel = this.tabelLpBulanan.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(0).setMaxWidth(90);
        columnModel.getColumn(1).setPreferredWidth(60);
        columnModel.getColumn(1).setMaxWidth(60);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(2).setMaxWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(3).setMaxWidth(80);
    }
    
    private Object[] getDataLaporan(int bulan, int tahun){
        try{
            int pendapatan, pesanan, transaksi;
            // membuat query untuk menampilkan data laporan
            String sql = "SELECT MONTH(tanggal), COUNT(id_tr_jual), SUM(total_menu), SUM(total_harga) "
                         + "FROM transaksi_jual "
                         + "WHERE MONTH(tanggal) = "+bulan+" AND YEAR(tanggal) = " + tahun;
            System.out.println(sql);
            
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // mendapatakan data transaksi, pesanan dan pendapatan
                transaksi = this.db.res.getInt(2);
                pesanan = this.db.res.getInt(3);
                pendapatan = this.db.res.getInt(4);
                
                // jika pendapatan tidak kosong maka data akan diambil dari mysql
                if(pendapatan >= 1){
                    // mengupdate total data
                    this.trBulanan += transaksi;
                    this.psBulanan += pesanan;
                    this.pdBulanan += pendapatan;
                    
                    // mendapatkan data dan mengembalikan data
                    return new Object[]{
                        waktu.getNamaBulan(this.db.res.getInt(1)),
                        this.inpPilihTahun.getYear(),
                        String.format("%,d", transaksi),
                        String.format("%,d", pesanan),
                        String.format("%s", txt.toMoneyCase(Integer.toString(pendapatan)))
                    };                    
                // jika pendapatan kurang dari 1 maka data dianggap kosong dan data yang akan ditampilkan akan diset default
                }else{
                    return new Object[]{
                        waktu.getNamaBulan(bulan),
                        this.inpPilihTahun.getYear(),
                        0,
                        0,
                        "Rp. 00"
                    };    
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }
    
    private void showLaporanBulanan(){
        // reset tabel laporan bulanan
        this.resetTableLpBulanan();
        DefaultTableModel model = (DefaultTableModel) this.tabelLpBulanan.getModel();
        
        // mendapatkan data waktu
        int bulanSaatIni = this.waktu.getBulan(),
            tahunSaatIni = this.waktu.getTahun(),
            tahunDipilih = this.inpPilihTahun.getYear();
        
        // jika tahun yang dipilih adalah tahun saat ini maka yang ditampilkan ada bulan paling akhir smp januari
        if(tahunSaatIni == this.inpPilihTahun.getYear()){
            // menambahkan data ke tabel
            for(int i = bulanSaatIni; i >= 1; i--){
                model.addRow(this.getDataLaporan(i, tahunDipilih));
            }
        // jika tahun yang dipilih adalah lebih kecil dari tahun saat ini maka data yang ditampilkan adalah desember smp januari
        }else if(tahunSaatIni > this.inpPilihTahun.getYear()){
            // menambahkan data ke tabel
            for(int i = 12; i >= 1; i--){
                model.addRow(this.getDataLaporan(i, tahunDipilih));
            }
        }
        
        // refresh tabel
        this.tabelLpBulanan.setModel(model);
        this.showDataLaporanBulanan();
    }

    private void showDataLaporanBulanan(){
        this.lblTotalTrBulanan.setText(String.format(" Transaksi : %,d", this.trBulanan));
        this.lblTotalPsBulanan.setText(String.format(" Pesanan : %,d", this.psBulanan));
        this.lblTotalPdBulanan.setText(String.format(" Pendapatan : %s", this.txt.toMoneyCase(Integer.toString(this.pdBulanan))));
    } 

    private void setEmptyChart(String text){
        this.lblEmptyChart.setText(text);
        
        this.pnlShowChart.removeAll();
        this.pnlShowChart.repaint();
        this.pnlShowChart.revalidate();
        
        this.pnlShowChart.add(new JPanel().add(this.lblEmptyChart), BorderLayout.CENTER);
        this.pnlShowChart.repaint();
        this.pnlShowChart.revalidate();
    }
    
    private void resetTabelRiwayat(){
        // set desain tabel
        this.tabelRiwayat.setRowHeight(29);
        this.tabelRiwayat.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelRiwayat.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
                new String[][]{},
                new String [] {
                    "ID Transaksi", "ID Menu", "Nama Menu", "Jenis Menu", "Harga Menu", "Jumlah", "Total Harga", "Tanggal"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // set size kolom tabel
        TableColumnModel columnModel = this.tabelRiwayat.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(0).setMaxWidth(90);
        columnModel.getColumn(1).setPreferredWidth(70);
        columnModel.getColumn(1).setMaxWidth(70);
        columnModel.getColumn(2).setPreferredWidth(170);
//        columnModel.getColumn(2).setMaxWidth(170);
        columnModel.getColumn(3).setPreferredWidth(160);
        columnModel.getColumn(3).setMaxWidth(160);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(4).setMaxWidth(120);
        columnModel.getColumn(5).setPreferredWidth(70);
        columnModel.getColumn(5).setMaxWidth(70);
        columnModel.getColumn(6).setPreferredWidth(130);
        columnModel.getColumn(6).setMaxWidth(130);
    }
    
    private String getUrutkanRiwayat(){
        int tipe = this.inpUrutkan.getSelectedIndex();
        
        // mendapatkan sort by
        switch(tipe){
            case 0 : return "d.id_tr_jual ASC";
            case 1 : return "d.id_tr_jual DESC";
            case 2 : return "d.nama_menu ASC";
            case 3 : return "d.nama_menu DESC";
            case 4 : return "d.jumlah ASC";
            case 5 : return "d.jumlah DESC";
            case 6 : return "d.total_harga ASC";
            case 7 : return "d.total_harga DESC";
            default : return "d.id_tr_jual DESC";
        }
    }
    
    private String getJenisMenu(){
        int tipe = this.inpJenisMenu.getSelectedIndex();
        
        switch(tipe){
            case 1 : return "WHERE d.jenis_menu = 'Makanan' ";
            case 2 : return "WHERE d.jenis_menu = 'Minuman' ";
            case 3 : return "WHERE d.jenis_menu = 'Original Coffee' ";
            case 4 : return "WHERE d.jenis_menu = 'Falvoured Coffee' ";
            case 5 : return "WHERE d.jenis_menu = 'Snack' ";
            default : return "";
        }
    }
    
    private void showListNamaMenu(){
        try{
            // mendapatkan jenis menu yang dipilih user
            String jenis = this.inpJenisMenu.getSelectedItem().toString();
            
            // jika user tidak meilih jenis scr spesifik
            if(jenis.equalsIgnoreCase("Semua Jenis")){
                inpNamaMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Semua Menu"}));
                return;
            }

            // untuk menyimpan nama2 bahan berdasarkan jenis
            ArrayList<String> list = new ArrayList();
            list.add("Semua Menu");
            // membuat query untuk mendapatkan nama menu
            String sql = "SELECT nama_menu FROM menu WHERE jenis = '"+jenis+"'";
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // mendapatkan semua nama dari berdasrkan jenis menu
            while(this.db.res.next()){
                list.add(this.db.res.getString("nama_menu"));
            }
            
            // menampilkan nama menu pada combo box
            inpNamaMenu.setModel(new javax.swing.DefaultComboBoxModel(list.toArray()));
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
    }
    
    private String getNamaMenu(){
        // mendapatkan nama menu yang dipilih user
        String item = this.inpNamaMenu.getSelectedItem().toString();
        
        // jika user tidak memilih nama menu scr spesifik
        if(item.equalsIgnoreCase("Semua Menu")){
            return "";
        }else{
            // mengembalikan nama menu yang dipilih user
            return "AND nama_menu = '" + item + "'";
        }
    }
    
    private void showRiwayatTransaksi(){
        // reset tabel riwayat
        this.resetTabelRiwayat();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        
        try{
            // membuat query
            String kondisi = this.getJenisMenu() + this.getNamaMenu() + " " + this.filterTglRiwayat,
                   sql = String.format(
                     "SELECT d.id_tr_jual, d.id_menu, d.nama_menu, d.jenis_menu, d.harga_menu, d.jumlah, d.total_harga, t.tanggal "
                   + "FROM detail_tr_jual AS d "
                   + "JOIN transaksi_jual AS t "
                   + "ON t.id_tr_jual = d.id_tr_jual "
                   + "%s "
                   + "ORDER BY %s "
                   + "LIMIT 0,2000", kondisi, this.getUrutkanRiwayat()
            );
            System.out.println(sql);
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            // membaca semua data yang ada didalam tabel
            while(this.db.res.next()){
                String id = this.db.res.getString("d.id_tr_jual"),
                       idMenu = this.db.res.getString("d.id_menu");
                
                model.addRow(
                    new Object[]{
                        // menambahkan data ke dalam tabel
                        id,
                        idMenu,
                        this.db.res.getString("d.nama_menu"),
                        this.db.res.getString("d.jenis_menu"),
                        this.txt.toMoneyCase(this.db.res.getString("d.harga_menu")),
                        this.db.res.getString("d.jumlah"),
                        this.txt.toMoneyCase(this.db.res.getString("d.total_harga")),
                        this.txt.toDateCase(this.db.res.getString("t.tanggal"))
                    }
                );
            }
            
            // refresh tabel
            this.tabelRiwayat.setModel(model);
            
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private void showDataRiwayat(){
        // mendapatkan data total transaksi
        int transaksi = this.tabelRiwayat.getRowCount(),
            pesanan = 0,
            pendapatan = 0;
        
        // mendapatkan data total pesanan dan total pendapatan
        for(int i = 0; i < transaksi; i++){
            pesanan += Integer.parseInt(this.tabelRiwayat.getValueAt(i, 5).toString());
            pendapatan += Integer.parseInt(txt.removeMoneyCase(this.tabelRiwayat.getValueAt(i, 6).toString()));
        }
        
        // menampilkan data riwayat transaksi
        this.lblTotalTrRiwayat.setText(String.format(" Transaksi : %,d", transaksi));
        this.lblTotalPsRiwayat.setText(String.format(" Pesanan : %,d", pesanan));
        this.lblTotalPdRiwayat.setText(String.format(" Pendapatan : %s", txt.toMoneyCase(""+pendapatan)));
        this.lblMenuFavRiwayat.setText(String.format(" Menu Favorite : %s", this.getFavoriteMenu()));
    }
    
    private String getFavoriteMenu(){
        try{
            // membuat query untuk mnedapatkan menu favorite
            // membuat query
            String sql = String.format(
                    "SELECT d.nama_menu, COUNT(d.id_menu) AS total "
                  + "FROM detail_tr_jual AS d "
                  + "JOIN transaksi_jual AS t "
                  + "ON t.id_tr_jual = d.id_tr_jual "
                  + "%s %s %s "
                  + "GROUP BY nama_menu ORDER BY total DESC, d.jumlah DESC "
                  + "LIMIT 0,1;", 
                    this.getJenisMenu(), this.getNamaMenu(), this.filterTglRiwayat
            );
            System.out.println(sql);
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            if(this.db.res.next()){
                // mengembalikan menu favorite
                return this.db.res.getString(1);
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return null;
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
        tabPane = new javax.swing.JTabbedPane();
        pnlLaporanHarian = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelLpHarian = new javax.swing.JTable();
        inpDataPerhari = new com.toedter.calendar.JDateChooser();
        cariDataHarian = new javax.swing.JLabel();
        lblDataPerhari = new javax.swing.JLabel();
        lblDataAntara = new javax.swing.JLabel();
        inpDataHarianBetween1 = new com.toedter.calendar.JDateChooser();
        lblAntara1 = new javax.swing.JLabel();
        cariDataAntara = new javax.swing.JLabel();
        inpDataHarianBetween2 = new com.toedter.calendar.JDateChooser();
        lblTotalTrHarian = new javax.swing.JLabel();
        lblTotalPsHarian = new javax.swing.JLabel();
        btnDetailHarian = new javax.swing.JButton();
        btnCetakHarian = new javax.swing.JButton();
        lblCari = new javax.swing.JLabel();
        inpCariHarian = new javax.swing.JTextField();
        btnSemuaHarian = new javax.swing.JButton();
        lineBottom = new javax.swing.JSeparator();
        lblTotalPdtHarian = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        pnlLaporanBulanan = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelLpBulanan = new javax.swing.JTable();
        lblTahun = new javax.swing.JLabel();
        pnlChart = new javax.swing.JPanel();
        pnlShowChart = new javax.swing.JPanel();
        lblEmptyChart = new javax.swing.JLabel();
        inpChartBulanan = new javax.swing.JComboBox();
        lblChartBulanan = new javax.swing.JLabel();
        lblTotalPsBulanan = new javax.swing.JLabel();
        btnCetakBulanan = new javax.swing.JButton();
        btnRiwayatBulanan = new javax.swing.JButton();
        lblTotalPdBulanan = new javax.swing.JLabel();
        inpPilihTahun = new com.toedter.calendar.JYearChooser();
        cariTahun = new javax.swing.JLabel();
        lblTotalTrBulanan = new javax.swing.JLabel();
        pnlRiwayatPenjualan = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelRiwayat = new javax.swing.JTable();
        lblUrutkan = new javax.swing.JLabel();
        inpUrutkan = new javax.swing.JComboBox();
        lblJenisMenu = new javax.swing.JLabel();
        inpJenisMenu = new javax.swing.JComboBox();
        lblTotalTrRiwayat = new javax.swing.JLabel();
        lblTotalPsRiwayat = new javax.swing.JLabel();
        lblTotalPdRiwayat = new javax.swing.JLabel();
        lblMenuFavRiwayat = new javax.swing.JLabel();
        lblNamaMenu = new javax.swing.JLabel();
        inpNamaMenu = new javax.swing.JComboBox();
        btnTgl = new javax.swing.JButton();
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
        btnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-trjual.png"))); // NOI18N
        btnTransaksi.setText("Transaksi");
        btnTransaksi.setIconTextGap(7);
        btnTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTransaksiMouseClicked(evt);
            }
        });

        btnDataMaster.setFont(new java.awt.Font("Dialog", 1, 21)); // NOI18N
        btnDataMaster.setForeground(new java.awt.Color(255, 255, 255));
        btnDataMaster.setText("Data Master");

        btnLaporan.setBackground(new java.awt.Color(166, 203, 227));
        btnLaporan.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-sidemenu-lpjual-dark.png"))); // NOI18N
        btnLaporan.setText("Laporan");
        btnLaporan.setIconTextGap(7);
        btnLaporan.setOpaque(true);
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

        lblIconWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window-topleft-lpjual.png"))); // NOI18N

        lblNamaWindow.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblNamaWindow.setForeground(new java.awt.Color(0, 21, 39));
        lblNamaWindow.setText("Laporan Penjualan Menu");

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

        tabPane.setBackground(new java.awt.Color(248, 249, 250));
        tabPane.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabPaneMouseClicked(evt);
            }
        });

        pnlLaporanHarian.setBackground(new java.awt.Color(248, 249, 250));

        tabelLpHarian.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelLpHarian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"TRJ001", "Mohammad Ilham", null, "4", "Rp. 25.000,00", "22 November 2022"},
                {"TRJ002", "Mohammad Ilham", null, "7", "Rp. 89.000,00", "22 November 2022"},
                {"TRJ003", "Widyasari Raisya", null, "2", "Rp. 10.000,00", "22 November 2022"},
                {"TRJ004", "Septian Yoga", null, "3", "Rp. 17.500,00", "22 November 2022"},
                {"TRJ005", "Widyasari Raisya", null, "1", "Rp. 8.000,00", "22 November 2022"},
                {"TRJ006", "Mohammad Ilham", null, "3", "Rp. 18.000,00", "22 November 2022"},
                {"TRJ007", "Mohammad Ilham", null, "4", "Rp. 23.000,00", "22 November 2022"},
                {"TRJ008", "Septian Yoga", null, "3", "Rp. 11.000,00", "22 November 2022"}
            },
            new String [] {
                "ID Transaksi", "Nama Karyawan", "Nama Pembeli", "Total Pesanan", "Total Harga", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelLpHarian.setGridColor(new java.awt.Color(0, 0, 0));
        tabelLpHarian.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tabelLpHarian.getTableHeader().setReorderingAllowed(false);
        tabelLpHarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelLpHarianKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tabelLpHarian);

        inpDataPerhari.setForeground(new java.awt.Color(102, 204, 0));
        inpDataPerhari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        cariDataHarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        cariDataHarian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariDataHarianMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cariDataHarianMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cariDataHarianMouseExited(evt);
            }
        });

        lblDataPerhari.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDataPerhari.setForeground(new java.awt.Color(27, 109, 235));
        lblDataPerhari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDataPerhari.setText("Data Perhari");

        lblDataAntara.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDataAntara.setForeground(new java.awt.Color(27, 109, 235));
        lblDataAntara.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDataAntara.setText("Urutkan Berdasarkan Hari");

        inpDataHarianBetween1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblAntara1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAntara1.setText("Sampai");

        cariDataAntara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        cariDataAntara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariDataAntaraMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cariDataAntaraMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cariDataAntaraMouseExited(evt);
            }
        });

        inpDataHarianBetween2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblTotalTrHarian.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalTrHarian.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalTrHarian.setText(" Transaksi : 1.239 ");
        lblTotalTrHarian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblTotalPsHarian.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPsHarian.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPsHarian.setText(" Pesanan : 2.133");
        lblTotalPsHarian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        btnDetailHarian.setBackground(new java.awt.Color(204, 0, 204));
        btnDetailHarian.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDetailHarian.setForeground(new java.awt.Color(255, 255, 255));
        btnDetailHarian.setText("Detail");
        btnDetailHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailHarianActionPerformed(evt);
            }
        });

        btnCetakHarian.setBackground(new java.awt.Color(255, 102, 0));
        btnCetakHarian.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCetakHarian.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakHarian.setText("Cetak");
        btnCetakHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakHarianActionPerformed(evt);
            }
        });

        lblCari.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCari.setForeground(new java.awt.Color(250, 22, 22));
        lblCari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCari.setText("Cari Transaksi");

        inpCariHarian.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpCariHarian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inpCariHarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariHarianKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariHarianKeyTyped(evt);
            }
        });

        btnSemuaHarian.setBackground(new java.awt.Color(0, 153, 255));
        btnSemuaHarian.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSemuaHarian.setForeground(new java.awt.Color(255, 255, 255));
        btnSemuaHarian.setText("Semua");
        btnSemuaHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemuaHarianActionPerformed(evt);
            }
        });

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        lblTotalPdtHarian.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPdtHarian.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPdtHarian.setText(" Pendapatan : Rp. 3.901.000.00");
        lblTotalPdtHarian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        btnUpdate.setBackground(new java.awt.Color(50, 180, 82));
        btnUpdate.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLaporanHarianLayout = new javax.swing.GroupLayout(pnlLaporanHarian);
        pnlLaporanHarian.setLayout(pnlLaporanHarianLayout);
        pnlLaporanHarianLayout.setHorizontalGroup(
            pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblDataPerhari, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                                .addComponent(inpDataPerhari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cariDataHarian)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                                .addComponent(inpDataHarianBetween1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAntara1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpDataHarianBetween2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cariDataAntara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblDataAntara, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inpCariHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLaporanHarianLayout.createSequentialGroup()
                        .addComponent(lblTotalTrHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalPsHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalPdtHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSemuaHarian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakHarian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetailHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(lineBottom))
                .addGap(33, 33, 33))
        );
        pnlLaporanHarianLayout.setVerticalGroup(
            pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLaporanHarianLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                        .addComponent(lblDataPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpDataPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cariDataHarian, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                    .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                        .addComponent(lblDataAntara, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpDataHarianBetween1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAntara1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(cariDataAntara, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(inpDataHarianBetween2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                        .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inpCariHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLaporanHarianLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(pnlLaporanHarianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDetailHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCetakHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSemuaHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTotalPsHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalTrHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPdtHarian, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabPane.addTab("Laporan Harian", pnlLaporanHarian);

        pnlLaporanBulanan.setBackground(new java.awt.Color(248, 249, 250));

        tabelLpBulanan.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelLpBulanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Januari", "2022", "123", "674", "Rp. 1.000,00"},
                {"Februari", "2022", "543", "989", "Rp. 1.000,00"},
                {"Maret", "2022", "123", "901", "Rp. 1.000,00"},
                {"April", "2022", "232", "790", "Rp. 1.000,00"},
                {"Mei", "2022", "145", "1,902", "Rp. 1.000,00"},
                {"Juni", "2022", "412", "1,031", "Rp. 1.000,00"},
                {"Juli", "2022", "242", "958", "Rp. 1.000,00"},
                {"Agustus", "2022", "232", "690", "Rp. 1.000,00"},
                {"September", "2022", "232", "618", "Rp. 1.000,00"},
                {"Oktober", "2022", "424", "956", "Rp. 1.000,00"},
                {"November", "2022", "253", "890", "Rp. 1.000,00"},
                {"Desember", "2022", "", null, ""}
            },
            new String [] {
                "Bulan", "Tahun", "Pembeli", "Pesanan", "Total Pendapatan"
            }
        ));
        tabelLpBulanan.setGridColor(new java.awt.Color(0, 0, 0));
        tabelLpBulanan.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tabelLpBulanan.getTableHeader().setReorderingAllowed(false);
        tabelLpBulanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelLpBulananMouseClicked(evt);
            }
        });
        tabelLpBulanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelLpBulananKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tabelLpBulanan);

        lblTahun.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTahun.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTahun.setText("Pilih Tahun ");

        pnlChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlChart.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        pnlShowChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlShowChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlShowChart.setLayout(new java.awt.BorderLayout());

        lblEmptyChart.setBackground(new java.awt.Color(237, 40, 40));
        lblEmptyChart.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblEmptyChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmptyChart.setText("Silahkan Pilih Tipe Chart");
        pnlShowChart.add(lblEmptyChart, java.awt.BorderLayout.CENTER);

        inpChartBulanan.setBackground(new java.awt.Color(248, 249, 250));
        inpChartBulanan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpChartBulanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih Tipe", "Pie Chart", "Line Chart", "Bar Chart" }));
        inpChartBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpChartBulananActionPerformed(evt);
            }
        });

        lblChartBulanan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblChartBulanan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChartBulanan.setText("Pilih Tipe Chart");

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartLayout.createSequentialGroup()
                .addGroup(pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlChartLayout.createSequentialGroup()
                        .addComponent(lblChartBulanan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inpChartBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlShowChart, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChartLayout.createSequentialGroup()
                .addGroup(pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpChartBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChartBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlShowChart, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
        );

        lblTotalPsBulanan.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalPsBulanan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPsBulanan.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPsBulanan.setText(" Pesanan : 5.650");
        lblTotalPsBulanan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        btnCetakBulanan.setBackground(new java.awt.Color(255, 102, 0));
        btnCetakBulanan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCetakBulanan.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakBulanan.setText("Cetak");
        btnCetakBulanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCetakBulananMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCetakBulananMouseExited(evt);
            }
        });
        btnCetakBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakBulananActionPerformed(evt);
            }
        });

        btnRiwayatBulanan.setBackground(new java.awt.Color(0, 153, 255));
        btnRiwayatBulanan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRiwayatBulanan.setForeground(new java.awt.Color(255, 255, 255));
        btnRiwayatBulanan.setText("Riwayat");
        btnRiwayatBulanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRiwayatBulananMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRiwayatBulananMouseExited(evt);
            }
        });
        btnRiwayatBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiwayatBulananActionPerformed(evt);
            }
        });

        lblTotalPdBulanan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPdBulanan.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPdBulanan.setText(" Pendapatan : Rp. 12.903.902,00");
        lblTotalPdBulanan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        cariTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        cariTahun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariTahunMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cariTahunMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cariTahunMouseExited(evt);
            }
        });

        lblTotalTrBulanan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalTrBulanan.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalTrBulanan.setText(" Transaksi : 2,345");
        lblTotalTrBulanan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        javax.swing.GroupLayout pnlLaporanBulananLayout = new javax.swing.GroupLayout(pnlLaporanBulanan);
        pnlLaporanBulanan.setLayout(pnlLaporanBulananLayout);
        pnlLaporanBulananLayout.setHorizontalGroup(
            pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanBulananLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLaporanBulananLayout.createSequentialGroup()
                        .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addGroup(pnlLaporanBulananLayout.createSequentialGroup()
                                .addComponent(lblTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpPilihTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(cariTahun)))
                        .addGap(18, 18, 18)
                        .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlLaporanBulananLayout.createSequentialGroup()
                        .addComponent(btnCetakBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRiwayatBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalTrBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalPsBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotalPdBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
        );
        pnlLaporanBulananLayout.setVerticalGroup(
            pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLaporanBulananLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLaporanBulananLayout.createSequentialGroup()
                        .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTahun, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(inpPilihTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cariTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlLaporanBulananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalPsBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRiwayatBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetakBulanan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTotalPdBulanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalTrBulanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabPane.addTab("Lapoan Bulanan", pnlLaporanBulanan);

        pnlRiwayatPenjualan.setBackground(new java.awt.Color(248, 249, 250));

        tabelRiwayat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Menu", "Nama Menu", "Jenis Menu", "Harga", "Jumlah", "Total Harga", "Tanggal"
            }
        ));
        tabelRiwayat.setGridColor(new java.awt.Color(0, 0, 0));
        tabelRiwayat.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tabelRiwayat.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(tabelRiwayat);

        lblUrutkan.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblUrutkan.setForeground(new java.awt.Color(250, 22, 22));
        lblUrutkan.setText("Urutkan ");

        inpUrutkan.setBackground(new java.awt.Color(248, 249, 250));
        inpUrutkan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpUrutkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tanggal ASC", "Tanggal DESC", "Nama Menu ASC", "Nama Menu DESC", "Jumlah Menu ASC", "Jumlah Menu DESC", "Total Harga ASC", "Total Harga DESC" }));
        inpUrutkan.setSelectedIndex(1);
        inpUrutkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpUrutkanActionPerformed(evt);
            }
        });

        lblJenisMenu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblJenisMenu.setForeground(new java.awt.Color(250, 22, 22));
        lblJenisMenu.setText("Jenis Menu");

        inpJenisMenu.setBackground(new java.awt.Color(248, 249, 250));
        inpJenisMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpJenisMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Jenis", "Makanan", "Minuman", "Original Coffee", "Falvoured Coffee", "Snack" }));
        inpJenisMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpJenisMenuActionPerformed(evt);
            }
        });

        lblTotalTrRiwayat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalTrRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalTrRiwayat.setText(" Transaksi : 2,343");
        lblTotalTrRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblTotalPsRiwayat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPsRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPsRiwayat.setText(" Pesanan : 2,343");
        lblTotalPsRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblTotalPdRiwayat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalPdRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPdRiwayat.setText(" Pendapatan : Rp. 3.430.123.00");
        lblTotalPdRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblMenuFavRiwayat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblMenuFavRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblMenuFavRiwayat.setText(" Menu Favorite : Kentang Goreng");
        lblMenuFavRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        lblNamaMenu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNamaMenu.setForeground(new java.awt.Color(250, 22, 22));
        lblNamaMenu.setText("Nama Menu");

        inpNamaMenu.setBackground(new java.awt.Color(248, 249, 250));
        inpNamaMenu.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpNamaMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua Menu" }));
        inpNamaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inpNamaMenuActionPerformed(evt);
            }
        });

        btnTgl.setBackground(new java.awt.Color(255, 51, 0));
        btnTgl.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnTgl.setForeground(new java.awt.Color(255, 255, 255));
        btnTgl.setText("TGL");
        btnTgl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTglMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTglMouseExited(evt);
            }
        });
        btnTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTglActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRiwayatPenjualanLayout = new javax.swing.GroupLayout(pnlRiwayatPenjualan);
        pnlRiwayatPenjualan.setLayout(pnlRiwayatPenjualanLayout);
        pnlRiwayatPenjualanLayout.setHorizontalGroup(
            pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRiwayatPenjualanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
                    .addGroup(pnlRiwayatPenjualanLayout.createSequentialGroup()
                        .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRiwayatPenjualanLayout.createSequentialGroup()
                                .addComponent(lblTotalTrRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotalPsRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotalPdRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMenuFavRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRiwayatPenjualanLayout.createSequentialGroup()
                                .addComponent(lblUrutkan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpUrutkan, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblJenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpJenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(32, 32, 32))
        );
        pnlRiwayatPenjualanLayout.setVerticalGroup(
            pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRiwayatPenjualanLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblUrutkan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inpUrutkan, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inpNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblJenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inpJenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlRiwayatPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalTrRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPsRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPdRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMenuFavRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tabPane.addTab("Riwayat Penjualan", pnlRiwayatPenjualan);

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPane)
                .addContainerGap())
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(tabPane)
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
                    .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSidebar, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
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
        System.out.println(this.getClass().getName() + " activated");
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        System.out.println(this.getClass().getName() + " deactivated");
    }//GEN-LAST:event_formWindowDeactivated

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
        this.chart.closeConnection();
        System.out.println(this.getClass().getName() + " closed");
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println(this.getClass().getName() + " closing");
    }//GEN-LAST:event_formWindowClosing

    
    private void inpChartBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpChartBulananActionPerformed
        // mendapatkan row tabel dan tipe chart
        int row = this.tabelLpBulanan.getSelectedRow(),
            tipe = this.inpChartBulanan.getSelectedIndex();
        
        // jika user belum memilih data pada tabel
        if(row < 0){
            this.setEmptyChart("Silahkan pilih bulan pada tabel disamping");
            return;
        }

        // mendapatkan nama bulan
        this.namaBulan = this.tabelLpBulanan.getValueAt(row, 0).toString();
        // mendapatkan bulan tahun yg dipilih
        int bulanDipilih = this.waktu.getNilaiBulan(this.namaBulan),
            tahunDipilih = this.inpPilihTahun.getYear();
        
        System.out.println("BULAN DIPILIH : " + bulanDipilih);
        
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String title = String.format("Penjualan Pada Bulan %s %d", namaBulan, tahunDipilih);
        switch(tipe){
            // jika yang dipilih adalah pie chart
            case 1 : 
                this.chart.showPieChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);
                break;
            // jika yang dipilih adalah line chart
            case 2 : 
                this.chart.showLineChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);
                break;
            case 3 :
                // jika yang dipilih adalah bar chart
                this.chart.showBarChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);    
                break;
            default : this.setEmptyChart("Silahkan Pilih Tipe Chart");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpChartBulananActionPerformed
    
    private void tabelLpBulananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelLpBulananMouseClicked
        // mendapatkan nama bulan
        this.namaBulan = this.tabelLpBulanan.getValueAt(this.tabelLpBulanan.getSelectedRow(), 0).toString();
        
        // mendapatkan bulan tahun yg dipilih
        int bulanDipilih = this.waktu.getNilaiBulan(this.namaBulan),
            tahunDipilih = this.inpPilihTahun.getYear(),
            // mendapatkan jumlah pembeli dan tipe chart
            jmlPembeli = Integer.parseInt(this.tabelLpBulanan.getValueAt(this.tabelLpBulanan.getSelectedRow(), 2).toString()),
            tipeChart = this.inpChartBulanan.getSelectedIndex();
        
        System.out.println("JUMLAH PEMBELI : " + jmlPembeli);
        
        if(jmlPembeli > 0){
            
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            String title = String.format("Penjualan Pada Bulan %s %d", namaBulan, tahunDipilih);
            switch(tipeChart){
                // jika yang dipilih adalah pie chart
                case 1 : 
                    this.chart.showPieChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);
                    break;
                // jika yang dipilih adalah line chart
                case 2 : 
                    this.chart.showLineChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);
                    break;
                case 3 :
                    // jika yang dipilih adalah bar chart
                    this.chart.showBarChart(this.pnlShowChart, ChartManager.PENDAPATAN, title, bulanDipilih, tahunDipilih);    
                    break;
                default : this.setEmptyChart("Silahkan Pilih Tipe Chart");
            }
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }else{
            System.out.println("EXECUTED");
            this.setEmptyChart("Tidak ada penjualan pada bulan " + namaBulan);
        }
    }//GEN-LAST:event_tabelLpBulananMouseClicked

    private void cariDataHarianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataHarianMouseClicked
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.inpDataHarianBetween1.setDate(null);
        this.inpDataHarianBetween2.setDate(null);
 
        // mendapatkan input dari jdatechooser
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d");
        if(this.inpDataPerhari.getDate() == null){
            Message.showWarning(this, "Silahkan pilih tanggal terlebih dahulu!");
        }else{
            String tanggal = format.format(this.inpDataPerhari.getDate());

            // menampilkan data
            this.showLaporanHarian("WHERE DATE(trj.tanggal) = '"+tanggal+"'");
            this.showDataLaporanHarian();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_cariDataHarianMouseClicked

    private void cariDataAntaraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataAntaraMouseClicked
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.inpDataPerhari.setDate(null);
        
        // mendapatkan input dari jdatechooser
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-d"), 
                           format2 = format1;
        if(this.inpDataHarianBetween1.getDate() == null || this.inpDataHarianBetween2.getDate() == null){
            Message.showWarning(this, "Silahkan pilih tanggal terlebih dahulu!");
        }else{
              String tanggal1 = format1.format(this.inpDataHarianBetween1.getDate()),
                     tanggal2 = format2.format(this.inpDataHarianBetween2.getDate());

            // menampilkan data
            this.showLaporanHarian("WHERE DATE(trj.tanggal) BETWEEN '"+tanggal1+"' AND '"+tanggal2+"'");
            this.showDataLaporanHarian();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));            
        }
    }//GEN-LAST:event_cariDataAntaraMouseClicked

    private void tabPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabPaneMouseClicked
        if(this.tabPane.getSelectedIndex() == 0){
            this.lblNamaWindow.setText("Laporan Penjualan Harian");
        }else if(this.tabPane.getSelectedIndex() == 1){
            this.lblNamaWindow.setText("Laporan Penjualan Bulanan");
        }else if(this.tabPane.getSelectedIndex() == 2){
            this.lblNamaWindow.setText("Riwayat Penjualan");
            this.tabelRiwayat.requestFocus();
        }
    }//GEN-LAST:event_tabPaneMouseClicked

    private void btnDetailHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailHarianActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String id;
        if(this.tabelLpHarian.getSelectedRow() >= 0){
            // membuka pop up detail transaksi
            id= this.tabelLpHarian.getValueAt(this.tabelLpHarian.getSelectedRow(), 0).toString();
            DetailTransaksiJual dtr = new DetailTransaksiJual(null, true, id);
            dtr.setVisible(true);            
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnDetailHarianActionPerformed

    private void cariDataHarianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataHarianMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_cariDataHarianMouseEntered

    private void cariDataHarianMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataHarianMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_cariDataHarianMouseExited

    private void cariDataAntaraMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataAntaraMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_cariDataAntaraMouseEntered

    private void cariDataAntaraMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDataAntaraMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_cariDataAntaraMouseExited

    private void inpCariHarianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariHarianKeyTyped
        this.cariLaporanHarian();
    }//GEN-LAST:event_inpCariHarianKeyTyped

    private void inpCariHarianKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariHarianKeyReleased
        this.cariLaporanHarian();
    }//GEN-LAST:event_inpCariHarianKeyReleased

    private void btnSemuaHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemuaHarianActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.showLaporanHarian("");
        this.showDataLaporanHarian();
        this.inpDataPerhari.setDate(null);
        this.inpDataHarianBetween1.setDate(null);
        this.inpDataHarianBetween2.setDate(null);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnSemuaHarianActionPerformed

    private void btnCetakHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakHarianActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        CetakLaporanHarian report = new CetakLaporanHarian(this, true, this.tabelLpHarian, this.db.conn, CetakLaporanHarian.STATUS_JUAL, "Laporan Penjualan Harian");
        report.setVisible(true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnCetakHarianActionPerformed

    private void cariTahunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariTahunMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.trBulanan = 0;
        this.psBulanan = 0;
        this.pdBulanan = 0;
        // menampilkan data laporan
        this.showLaporanBulanan();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_cariTahunMouseClicked

    private void cariTahunMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariTahunMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_cariTahunMouseEntered

    private void cariTahunMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariTahunMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_cariTahunMouseExited

    private void btnRiwayatBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiwayatBulananActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        int row = this.tabelLpBulanan.getSelectedRow(),
            jmlData;
        if(row >= 0){
            jmlData = Integer.parseInt(this.tabelLpBulanan.getValueAt(row, 3).toString());
            if(jmlData > 0){
                int bulan = waktu.getNilaiBulan(this.tabelLpBulanan.getValueAt(row, 0).toString()),
                    tahun = Integer.parseInt(this.tabelLpBulanan.getValueAt(row, 1).toString());

                RiwayatTransaksiJual dtl = new RiwayatTransaksiJual(null, true, bulan, tahun);
                dtl.setVisible(true);                
            }else{
                Message.showWarning(this, "Gagal menampilkan data!\nTidak ada penjualan yang dilakukan pada bulan " + this.tabelLpBulanan.getValueAt(row, 0).toString());
            }
        }else{
            Message.showWarning(this, "Tidak ada bulan yang dipilih!");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnRiwayatBulananActionPerformed

    private void btnCetakBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakBulananActionPerformed
        CetakLaporanHarian report = new CetakLaporanHarian(this, true, this.tabelLpBulanan, this.db.conn, "unsupported-yet :|", "Laporan Penjualan Bulanan");
        report.setVisible(true);      
    }//GEN-LAST:event_btnCetakBulananActionPerformed

    private void btnCetakBulananMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakBulananMouseEntered
        
    }//GEN-LAST:event_btnCetakBulananMouseEntered

    private void btnCetakBulananMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakBulananMouseExited
        
    }//GEN-LAST:event_btnCetakBulananMouseExited

    private void btnRiwayatBulananMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatBulananMouseEntered
        
    }//GEN-LAST:event_btnRiwayatBulananMouseEntered

    private void btnRiwayatBulananMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatBulananMouseExited
        
    }//GEN-LAST:event_btnRiwayatBulananMouseExited

    private void inpUrutkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpUrutkanActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.showRiwayatTransaksi();
        this.showDataRiwayat();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpUrutkanActionPerformed

    private void inpJenisMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpJenisMenuActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.showListNamaMenu();
        this.showRiwayatTransaksi();
        this.showDataRiwayat();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpJenisMenuActionPerformed

    private void inpNamaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inpNamaMenuActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.showRiwayatTransaksi();
        this.showDataRiwayat();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_inpNamaMenuActionPerformed

    private void tabelLpHarianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelLpHarianKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_D){
            this.btnDetailHarianActionPerformed(null);
        }
    }//GEN-LAST:event_tabelLpHarianKeyPressed

    private void tabelLpBulananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelLpBulananKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_R){
            this.btnRiwayatBulananActionPerformed(null);
        }
    }//GEN-LAST:event_tabelLpBulananKeyPressed

    private void btnTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTglActionPerformed
        // membuka filter angka
        FilterTanggalRiwayat filter = new FilterTanggalRiwayat(null, true);
        filter.setVisible(true);
        
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // jika yang dipilih data perhari
        if (filter.isPerhari()) {
            this.filterTglRiwayat = filter.getPerhari();
            this.showRiwayatTransaksi();
            this.showDataRiwayat();
        } 
        // jika yang dipilih data antara
        else if (filter.isAntara()) {
            this.filterTglRiwayat = filter.getAntara();
            this.showRiwayatTransaksi();
            this.showDataRiwayat();
        } 
        // jika tidak ada data yang dipilh maka mereset tanggal
        else {
            this.filterTglRiwayat = "";
            this.showRiwayatTransaksi();
            this.showDataRiwayat();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnTglActionPerformed

    private void btnTglMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTglMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTglMouseEntered

    private void btnTglMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTglMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTglMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(this.tabelLpHarian.getSelectedRow() > -1){
            // mendapatkan id
            String id = this.tabelLpHarian.getValueAt(this.tabelLpHarian.getSelectedRow(), 0).toString();
            
            // membuka pop up update
            SelectUpdateTransaksi sel = new SelectUpdateTransaksi(this, true, SelectUpdateTransaksi.STATUS_JUAL, id);
            sel.setVisible(true);            
            
            if(sel.isHapus()){
                // menampilkan ulang data transaksi
                this.showLaporanHarian("");
                this.showDataLaporanHarian();
                this.inpDataPerhari.setDate(null);
                this.inpDataHarianBetween1.setDate(null);
                this.inpDataHarianBetween2.setDate(null);
            }
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(MenuLaporanJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuLaporanJual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBahan;
    private javax.swing.JButton btnCetakBulanan;
    private javax.swing.JButton btnCetakHarian;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnDataMaster;
    private javax.swing.JButton btnDetailHarian;
    private javax.swing.JLabel btnKaryawan;
    private javax.swing.JLabel btnLaporan;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnMenu;
    private javax.swing.JLabel btnPembeli;
    private javax.swing.JButton btnRiwayatBulanan;
    private javax.swing.JButton btnSemuaHarian;
    private javax.swing.JLabel btnSupplier;
    private javax.swing.JButton btnTgl;
    private javax.swing.JLabel btnTransaksi;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel cariDataAntara;
    private javax.swing.JLabel cariDataHarian;
    private javax.swing.JLabel cariTahun;
    private javax.swing.JTextField inpCariHarian;
    private javax.swing.JComboBox inpChartBulanan;
    private com.toedter.calendar.JDateChooser inpDataHarianBetween1;
    private com.toedter.calendar.JDateChooser inpDataHarianBetween2;
    private com.toedter.calendar.JDateChooser inpDataPerhari;
    private javax.swing.JComboBox inpJenisMenu;
    private javax.swing.JComboBox inpNamaMenu;
    private com.toedter.calendar.JYearChooser inpPilihTahun;
    private javax.swing.JComboBox inpUrutkan;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAntara1;
    private javax.swing.JLabel lblBottom;
    private javax.swing.JLabel lblCari;
    private javax.swing.JLabel lblChartBulanan;
    private javax.swing.JLabel lblDataAntara;
    private javax.swing.JLabel lblDataPerhari;
    private javax.swing.JLabel lblEmptyChart;
    private javax.swing.JLabel lblIconWindow;
    private javax.swing.JLabel lblJenisMenu;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMenuFavRiwayat;
    private javax.swing.JLabel lblNamaMenu;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblNamaWindow;
    private javax.swing.JLabel lblProfileSidebar;
    private javax.swing.JLabel lblTahun;
    private javax.swing.JLabel lblTopInfo;
    private javax.swing.JLabel lblTopProfile;
    private javax.swing.JLabel lblTopSetting;
    private javax.swing.JLabel lblTotalPdBulanan;
    private javax.swing.JLabel lblTotalPdRiwayat;
    private javax.swing.JLabel lblTotalPdtHarian;
    private javax.swing.JLabel lblTotalPsBulanan;
    private javax.swing.JLabel lblTotalPsHarian;
    private javax.swing.JLabel lblTotalPsRiwayat;
    private javax.swing.JLabel lblTotalTrBulanan;
    private javax.swing.JLabel lblTotalTrHarian;
    private javax.swing.JLabel lblTotalTrRiwayat;
    private javax.swing.JLabel lblUrutkan;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineSideMenu1;
    private javax.swing.JPanel pnlChart;
    private com.ui.RoundedPanel pnlContent;
    private javax.swing.JPanel pnlLaporanBulanan;
    private javax.swing.JPanel pnlLaporanHarian;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlRiwayatPenjualan;
    private javax.swing.JPanel pnlShowChart;
    private javax.swing.JPanel pnlSidebar;
    private com.ui.RoundedPanel pnlTop;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JTable tabelLpBulanan;
    private javax.swing.JTable tabelLpHarian;
    private javax.swing.JTable tabelRiwayat;
    // End of variables declaration//GEN-END:variables
}
