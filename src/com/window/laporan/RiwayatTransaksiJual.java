package com.window.laporan;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Gambar;
import com.window.dialog.PopUpBackground;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.MessageFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class RiwayatTransaksiJual extends javax.swing.JDialog {
    
    private DefaultTableModel cariData;

    private final PopUpBackground pop = new PopUpBackground();
    
    private final Database db = new Database();
    
    private final Waktu waktu = new Waktu();
    
    private final Text text = new Text();
    
    private final int bulan, tahun;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param bulan
     * @param tahun
     */
    public RiwayatTransaksiJual(java.awt.Frame parent, boolean modal, int bulan, int tahun) {
        super(parent, modal);
        this.pop.setVisible(true);
        this.bulan = bulan;
        this.tahun = tahun;
        
        initComponents();
        this.btnCetak.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDetail.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
        this.lblDialogName.setText(this.lblDialogName.getText() + waktu.getNamaBulan(bulan) + " " + tahun);
        
        this.showRiwayatTransaksi();
        this.showDataRiwayat();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private void resetTableLpRiwayat(){
        // set desain tabel
        this.tabelRiwayat.setRowHeight(29);
        this.tabelRiwayat.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelRiwayat.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
                new String[]{
                    "ID Transaksi", "ID Karyawan", "Nama Karyawan", "Total Pesanan", "Total Harga", "Tanggal", "Waktu"
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
    
    private void showRiwayatTransaksi(){
        
        this.resetTableLpRiwayat();
        DefaultTableModel model = (DefaultTableModel) this.tabelRiwayat.getModel();
        
        try{
            // membuat query
            String sql = "SELECT trj.id_tr_jual, trj.id_karyawan, trj.nama_karyawan,  trj.total_menu, trj.total_harga, DATE(trj.tanggal), DAYNAME(trj.tanggal), TIME(trj.tanggal) AS waktu " +
                        "FROM transaksi_jual AS trj " +
                        "WHERE MONTH(tanggal) = "+this.bulan+" AND YEAR(tanggal) = " + this.tahun + 
                        " ORDER BY trj.id_tr_jual DESC";
            System.out.println(sql);
            System.out.println("NILAI BULAN : " + this.bulan);
            System.out.println("MENAMPILKAN DATA BULAN " + this.waktu.getNamaBulan(this.bulan));
            
            // mengeksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            while(this.db.res.next()){
                model.addRow(
                    new Object[]{
                        this.db.res.getString(1),
                        this.db.res.getString(2),
                        this.db.res.getString(3),
                        this.db.res.getString(4) + " Pesanan",
                        this.text.toMoneyCase(this.db.res.getString(5)),
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

    private void showDataRiwayat(){
        // jika data riwayat kosong
        if(this.tabelRiwayat.getRowCount() <= 0){
            this.lblTotalTrRiwayat.setText(" Transaksi : 0");
            this.lblTotalPsRiwayat.setText(" Pesanan : 0");
            this.lblTotalPdRiwayat.setText(" Pendapatan : Rp. 00");
        }
     
        // inisialisasi awal
        int transaksi = this.tabelRiwayat.getRowCount(),
            pesanan = 0, pendapatan = 0;
        
        // menghitung total data
        for(int i = 0; i < this.tabelRiwayat.getRowCount(); i++){
            pesanan += Integer.parseInt(this.tabelRiwayat.getValueAt(i, 3).toString().replace(" Pesanan", ""));
            pendapatan += Integer.parseInt(text.removeMoneyCase(this.tabelRiwayat.getValueAt(i, 4).toString()));
        }
        
        // menampilkan data
        this.lblTotalTrRiwayat.setText(String.format(" Total Transaksi : %,d" , transaksi));
        this.lblTotalPsRiwayat.setText(String.format(" Total Pesanan : %,d" , pesanan));
        this.lblTotalPdRiwayat.setText(String.format(" Total Pendapatan : %s " , text.toMoneyCase(""+pendapatan)));
    }
    
    private void cariRiwayatTransaksi(){
        // reset tabel riwayat
        this.resetTableLpRiwayat();
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
                        this.cariData.getValueAt(i, 6)
                    }
                );
            }
        }
        
        // refresh tabel riwayat harian
        this.tabelRiwayat.setModel(model);
        // refresh total data
        this.showDataRiwayat();
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
        lblTotalTrRiwayat = new javax.swing.JLabel();
        lblTotalPsRiwayat = new javax.swing.JLabel();
        lblTotalPdRiwayat = new javax.swing.JLabel();
        btnDetail = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        lineBottom = new javax.swing.JSeparator();

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
        lblDialogName.setText("Riwayat Transaksi Bulan ");
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

        lblTotalTrRiwayat.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        lblTotalTrRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalTrRiwayat.setText(" Total Transaksi : 4,232");
        lblTotalTrRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        lblTotalPsRiwayat.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        lblTotalPsRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPsRiwayat.setText(" Total Pesanan : 5,901");
        lblTotalPsRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        lblTotalPdRiwayat.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        lblTotalPdRiwayat.setForeground(new java.awt.Color(0, 105, 233));
        lblTotalPdRiwayat.setText(" Total Pendapatan : Rp. 9.454.464.00");
        lblTotalPdRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        btnDetail.setBackground(new java.awt.Color(0, 153, 255));
        btnDetail.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnDetail.setText("Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(255, 102, 0));
        btnCetak.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

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
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                                .addComponent(lblTotalTrRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTotalPsRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTotalPdRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblCari, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpCari, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                            .addComponent(lineTop, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                            .addComponent(lineBottom))
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
                    .addComponent(inpCari, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnDetail)
                    .addComponent(btnCetak))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalTrRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPsRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPdRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            // membuka pop up detail transaksi
            id= this.tabelRiwayat.getValueAt(this.tabelRiwayat.getSelectedRow(), 0).toString();
            DetailTransaksiJual dtr = new DetailTransaksiJual(null, true, id);
            dtr.setVisible(true);            
        }else{
            Message.showWarning(this, "Tidak ada data yang dipilih!");
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void inpCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyReleased
        this.cariRiwayatTransaksi();
    }//GEN-LAST:event_inpCariKeyReleased

    private void inpCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inpCariKeyTyped
        this.cariRiwayatTransaksi();
    }//GEN-LAST:event_inpCariKeyTyped

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        try {
            // set header dan footer
            MessageFormat header = new MessageFormat("Riwayat Penjualan Bulan " + this.waktu.getNamaBulan(this.bulan) + " " + this.tahun);
            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");
            // cek tabel kosong atau tidak
            if (this.tabelRiwayat.getRowCount() > 0) {
                // print tabel
                this.tabelRiwayat.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            } else {
                Message.showWarning(this, "Tidak ada data didalam tabel yang akan diprint!");
            }
        } catch (PrinterException ex) {
            Message.showException(this, "Tabel gagal diprint", ex);
        }   
    }//GEN-LAST:event_btnCetakActionPerformed

    private void tabelRiwayatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelRiwayatKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_D){
            this.btnDetailActionPerformed(null);
        }
    }//GEN-LAST:event_tabelRiwayatKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(RiwayatTransaksiJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RiwayatTransaksiJual dialog = new RiwayatTransaksiJual(new javax.swing.JFrame(), true, 11, 2022);
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
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnDetail;
    private javax.swing.JTextField inpCari;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCari;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JLabel lblTotalPdRiwayat;
    private javax.swing.JLabel lblTotalPsRiwayat;
    private javax.swing.JLabel lblTotalTrRiwayat;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    private javax.swing.JTable tabelRiwayat;
    // End of variables declaration//GEN-END:variables
}
