package com.window.transaksi;

import com.window.laporan.*;
import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import com.window.dialog.PopUpBackground;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class HistoriTransaksi extends javax.swing.JDialog {
    
    private DefaultTableModel cariData;

    private final PopUpBackground pop = new PopUpBackground();
    
    private final Database db = new Database();
    
    private final Waktu waktu = new Waktu();
    
    private final Text text = new Text();
    
    private String idSelected = "";
    
    private final String tanggal = this.waktu.getCurrentDate(), status;
    
    public static final String STATUS_JUAL = "jual", STATUS_BELI = "beli";
    
    private boolean isSelected = false;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param status
     */
    public HistoriTransaksi(java.awt.Frame parent, boolean modal, String status) {
        super(parent, modal);
        this.pop.setVisible(true);
        this.status = status;
        
        initComponents();
        this.btnUpdate.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDetail.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
        this.lblDialogName.setText(this.lblDialogName.getText() + text.toDateCase(this.tanggal));
        
        switch(this.status){
            case STATUS_JUAL : this.showRiwayatJual(); break;
            case STATUS_BELI : this.showRiwayatBeli(); break;
        }
        
    }
    
    public boolean isIdSelected(){
        return this.isSelected;
    }
    
    public String getIdSelected(){
        return this.idSelected;
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private boolean isExistDetailBeli() {
        try {
            this.db.res = this.db.stat.executeQuery("SELECT COUNT(id_tr_beli) AS ttl FROM detail_tr_beli WHERE id_tr_beli = '" + this.idSelected + "'");
            if (this.db.res.next()) {
                return this.db.res.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }

    private boolean isExistDetailJual() {
        try {
            this.db.res = this.db.stat.executeQuery("SELECT COUNT(id_tr_jual) AS ttl FROM detail_tr_jual WHERE id_tr_jual = '" + this.idSelected + "'");
            System.out.println();
            if (this.db.res.next()) {
                return this.db.res.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private void resetTableJual(){
        // set desain tabel
        this.tabelRiwayat.setRowHeight(29);
        this.tabelRiwayat.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelRiwayat.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
                new String[]{
                    "ID Transaksi", "ID Karyawan", "Nama Karyawan", "Total Pesanan", "Total Harga", "Total Bayar", "Total Kembalian", "Waktu"
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
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(1).setMaxWidth(90);
        columnModel.getColumn(2).setPreferredWidth(235);
        columnModel.getColumn(2).setMaxWidth(235);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);
        columnModel.getColumn(4).setPreferredWidth(140);
        columnModel.getColumn(4).setMaxWidth(140);
        columnModel.getColumn(5).setPreferredWidth(140);
        columnModel.getColumn(5).setMaxWidth(140);
        columnModel.getColumn(6).setPreferredWidth(140);
        columnModel.getColumn(6).setMaxWidth(140);
//        columnModel.getColumn(5).setPreferredWidth(210);
//        columnModel.getColumn(5).setMaxWidth(210);
    }
    
    private void showRiwayatJual(){
        
        this.resetTableJual();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        
        try{
            // membuat query
            String sql = "SELECT trj.id_tr_jual, trj.id_karyawan, trj.nama_karyawan,  trj.total_menu, trj.total_harga, trj.total_bayar, trj.total_kembalian, TIME(trj.tanggal) AS waktu " +
                        "FROM transaksi_jual AS trj " +
                        "WHERE DATE(trj.tanggal) = '" + this.tanggal + "' " + 
                        " ORDER BY trj.tanggal DESC";
            System.out.println(sql);

            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            while(this.db.res.next()){
                model.addRow(
                    new Object[]{
                        this.db.res.getString(1),
                        this.db.res.getString(2),
                        this.db.res.getString(3),
                        this.db.res.getString(4) + " Pesanan",
                        this.text.toMoneyCase(this.db.res.getInt(5)),
                        this.text.toMoneyCase(this.db.res.getInt(6)),
                        this.text.toMoneyCase(this.db.res.getInt(7)),
                        this.db.res.getString(8)
                    }
                );
            }
            
            this.tabelRiwayat.setModel(model);
            this.cariData = model;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private void cariRiwayatJual(){
        // reset tabel riwayat
        this.resetTableJual();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        String key = inpCari.getText().toLowerCase(), id, nama, idKy, tanggal;
        
        // membaca semua is tabel riwayat
        for(int i = 0; i < this.cariData.getRowCount(); i++){
            // mendapatkan data id, nama dan tanggal
            id = this.cariData.getValueAt(i, 0).toString().toLowerCase();
            nama = this.cariData.getValueAt(i, 1).toString().toLowerCase();
            idKy = this.cariData.getValueAt(i, 2).toString().toLowerCase();
            tanggal = this.cariData.getValueAt(i, 5).toString().toLowerCase();
            
            // pengecekan id, nama dan tanggal
            if(id.contains(key) || idKy.contains(key) || nama.contains(key) || tanggal.contains(key)){
                // jika match maka data ditampilkan kedalam tabel
                model.addRow(
                    new Object[]{
                        id.toUpperCase(), 
                        this.cariData.getValueAt(i, 1),
                        this.cariData.getValueAt(i, 2),
                        this.cariData.getValueAt(i, 3),
                        this.cariData.getValueAt(i, 4),
                        this.cariData.getValueAt(i, 5),
                        this.cariData.getValueAt(i, 6),
                        this.cariData.getValueAt(i, 7)
                    }
                );
            }
        }
        
        // refresh tabel riwayat harian
        this.tabelRiwayat.setModel(model);
    }
    
    private void resetTableBeli(){
        // set desain tabel
        this.tabelRiwayat.setRowHeight(29);
        this.tabelRiwayat.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelRiwayat.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
                new String[][]{},
                new String[]{
                    "ID Transaksi", "ID Karyawan", "Nama Karyawan", "Total Bahan", "Total Harga", "Tanggal", "Waktu"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
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
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(1).setMaxWidth(90);
        columnModel.getColumn(2).setPreferredWidth(235);
        columnModel.getColumn(2).setMaxWidth(235);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);
        columnModel.getColumn(4).setPreferredWidth(140);
        columnModel.getColumn(4).setMaxWidth(140);
        columnModel.getColumn(5).setPreferredWidth(210);
        columnModel.getColumn(5).setMaxWidth(210);
//        columnModel.getColumn(5).setPreferredWidth(210);
//        columnModel.getColumn(5).setMaxWidth(210);
    }
    
    private void showRiwayatBeli(){
        
        this.resetTableBeli();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        
        try{
            
            // membuat query
            String sql = "SELECT trb.id_tr_beli, trb.id_karyawan, trb.nama_karyawan, trb.total_bahan, trb.total_harga, DATE(trb.tanggal), DAYNAME(trb.tanggal), TIME(trb.tanggal) AS waktu  " +
                        "FROM transaksi_beli AS trb " +
                        "WHERE DATE(trb.tanggal) = '" + this.tanggal + "' " + 
                        " ORDER BY trb.tanggal DESC";
            System.out.println(sql);
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            while(this.db.res.next()){
                model.addRow(
                    new Object[]{
                        this.db.res.getString(1),
                        this.db.res.getString(2),
                        this.db.res.getString(3),
                        this.db.res.getString(4) + " Bahan",
                        this.text.toMoneyCase(this.db.res.getInt(5)),
                        this.waktu.getNamaHariInIndonesian(this.db.res.getString(7)) + ", " + this.text.toDateCase(this.db.res.getString(6)),
                        this.db.res.getString(8)
                    }
                );
            }
            
            this.tabelRiwayat.setModel(model);
            this.cariData = model;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private void cariRiwayatBeli(){
        // reset tabel riwayat
        this.resetTableBeli();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        String key = inpCari.getText().toLowerCase(), id, idKy, nama, tgl;
        
        // membaca semua is tabel riwayat
        for(int i = 0; i < this.cariData.getRowCount(); i++){
            // mendapatkan data id, nama dan tanggal
            id = this.cariData.getValueAt(i, 0).toString().toLowerCase();
            idKy = this.cariData.getValueAt(i, 1).toString().toLowerCase();
            nama = this.cariData.getValueAt(i, 2).toString().toLowerCase();
            tgl = this.cariData.getValueAt(i, 5).toString().toLowerCase();
            
            // pengecekan id, nama dan tanggal
            if(id.contains(key) || idKy.contains(key) || nama.contains(key) || tgl.contains(key)){
                // jika match maka data ditampilkan kedalam tabel
                model.addRow(
                    new Object[]{
                        id.toUpperCase(), 
                        this.cariData.getValueAt(i, 1),
                        this.cariData.getValueAt(i, 2),
                        this.cariData.getValueAt(i, 3),
                        this.cariData.getValueAt(i, 4),
                        this.cariData.getValueAt(i, 5),
                        this.cariData.getValueAt(i, 6)
                    }
                );
            }
        }
        
        // refresh tabel riwayat harian
        this.tabelRiwayat.setModel(model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblClose = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelRiwayat = new javax.swing.JTable();
        lblCari = new javax.swing.JLabel();
        inpCari = new javax.swing.JTextField();
        btnDetail = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        lineBottom = new javax.swing.JSeparator();
        btnHapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));
        pnlMain.setRoundBottomLeft(30);
        pnlMain.setRoundBottomRight(30);
        pnlMain.setRoundTopLeft(30);
        pnlMain.setRoundTopRight(30);

        lblDialogName.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        lblDialogName.setForeground(new java.awt.Color(26, 105, 243));
        lblDialogName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialogName.setText("Histori Transaksi Tanggal ");
        lblDialogName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        lblClose.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-mpopup-close.png"))); // NOI18N
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
        });

        tabelRiwayat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Nama Karyawan", "Nama Pembeli", "Total Pesanan", "Total Harga", "Tanggal"
            }
        ));
        tabelRiwayat.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tabelRiwayat.getTableHeader().setReorderingAllowed(false);
        tabelRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatMouseClicked(evt);
            }
        });
        tabelRiwayat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelRiwayatKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabelRiwayat);

        lblCari.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCari.setForeground(new java.awt.Color(250, 22, 22));
        lblCari.setText("Cari Transaksi");

        inpCari.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        inpCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inpCariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inpCariKeyTyped(evt);
            }
        });

        btnDetail.setBackground(new java.awt.Color(0, 153, 255));
        btnDetail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnDetail.setText("Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 102, 0));
        btnUpdate.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        btnHapus.setBackground(new java.awt.Color(220, 41, 41));
        btnHapus.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addComponent(lblDialogName, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                                    .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(inpCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                                .addComponent(lineTop, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                                .addComponent(lineBottom)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDialogName, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inpCari, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetail)
                    .addComponent(btnUpdate)
                    .addComponent(btnHapus))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        this.lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblClose.setIcon(Gambar.getIcon("ic-mpopup-close-entered.png"));
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        this.lblClose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblClose.setIcon(Gambar.getIcon("ic-mpopup-close.png"));
    }//GEN-LAST:event_lblCloseMouseExited

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        String id;
        if(this.tabelRiwayat.getSelectedRow() >= 0){
            // mendapatkan id transakasi
            id= this.tabelRiwayat.getValueAt(this.tabelRiwayat.getSelectedRow(), 0).toString();
            // membuka windowo pop up detail transaksi
            switch (this.status) {
                case STATUS_JUAL:
                    DetailTransaksiJual dtrj = new DetailTransaksiJual(null, true, id);
                    dtrj.setVisible(true);
                    break;
                case STATUS_BELI:
                    DetailTransaksiBeli dtrb = new DetailTransaksiBeli(null, true, id);
                    dtrb.setVisible(true);
                    break;
            }       
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        switch(this.status){
            case STATUS_JUAL : this.cariRiwayatJual(); break;
            case STATUS_BELI : this.cariRiwayatBeli(); break;
        }
    }//GEN-LAST:event_inpCariKeyReleased

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        switch(this.status){
            case STATUS_JUAL : this.cariRiwayatJual(); break;
            case STATUS_BELI : this.cariRiwayatBeli(); break;
        }
    }//GEN-LAST:event_inpCariKeyTyped

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(!this.isExistDetailBeli() && !this.isExistDetailJual()){
            Message.showWarning(this, "Gagal mengupdate transaksi!");
            return;
        }
        
        if(this.tabelRiwayat.getSelectedRow() > -1){
            // membuka confirm dialog untuk mengupdate data
            Audio.play(Audio.SOUND_INFO);
            int s = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin mengupdate transaksi " + this.idSelected + "?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            // cek status konfirmasi
            switch(s){
                case JOptionPane.YES_OPTION : 
                    // mendapatkan id transaksi yang dipilih
                    this.isSelected = !this.isSelected;
                    this.dispose();                    
                    break;
            }
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tabelRiwayatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelRiwayatKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if(evt.getKeyCode() == KeyEvent.VK_UP){
            this.idSelected = this.tabelRiwayat.getValueAt(tabelRiwayat.getSelectedRow() - 1, 0).toString();
        }else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.tabelRiwayat.getValueAt(tabelRiwayat.getSelectedRow() + 1, 0).toString();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_tabelRiwayatKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed

        try {
            // mengecek apakah ada data yang dipilih atau tidak
            if (tabelRiwayat.getSelectedRow() > -1) {
                
                // membuka confirm dialog untuk menghapus data
                Audio.play(Audio.SOUND_INFO);
                int s = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi " + this.idSelected + "?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

                // membuat query untuk menghapus data
                String sql = "";
                switch(this.status){
                    case HistoriTransaksi.STATUS_JUAL : sql = "DELETE FROM transaksi_jual WHERE id_tr_jual = '" + this.idSelected + "'"; break;
                    case HistoriTransaksi.STATUS_BELI : sql = "DELETE FROM transaksi_beli WHERE id_tr_beli = '" + this.idSelected + "'"; break;
                }
                
                // mengecek pilihan dari barang
                switch (s) {
                    // jika yes maka data akan dihapus
                    case JOptionPane.YES_OPTION:
                        // menghapus transaksi
                        boolean sukses = this.db.stat.executeUpdate(sql) > 0;
                        // cek apakah transaksi berhasil dihapus
                        if(sukses){
                            Message.showInformation(this, "Transaksi berhasil dihapus!");
                        }else{
                            Message.showInformation(this, "Transaksi gagal dihapus!");
                        }
                        break;
                }
            } else {
                Message.showWarning(this, "Tidak ada data yang dipilih!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        
        switch(this.status){
            case STATUS_JUAL : this.showRiwayatJual(); break;
            case STATUS_BELI : this.showRiwayatBeli(); break;
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tabelRiwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatMouseClicked
        this.idSelected = this.tabelRiwayat.getValueAt(this.tabelRiwayat.getSelectedRow(), 0).toString();
    }//GEN-LAST:event_tabelRiwayatMouseClicked

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
            java.util.logging.Logger.getLogger(HistoriTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HistoriTransaksi dialog = new HistoriTransaksi(new javax.swing.JFrame(), true, HistoriTransaksi.STATUS_JUAL);
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
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField inpCari;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCari;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    private javax.swing.JTable tabelRiwayat;
    // End of variables declaration//GEN-END:variables
}
