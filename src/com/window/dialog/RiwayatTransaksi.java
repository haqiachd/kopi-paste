package com.window.dialog;

import com.koneksi.Koneksi;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class RiwayatTransaksi extends javax.swing.JDialog {
    
    private DefaultTableModel cariData;

    private final PopUpBackground pop = new PopUpBackground();
    
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
    public RiwayatTransaksi(java.awt.Frame parent, boolean modal, int bulan, int tahun) {
        super(parent, modal);
        this.pop.setVisible(true);
        this.bulan = bulan;
        this.tahun = tahun;

        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
        this.lblDialogName.setText(this.lblDialogName.getText() + waktu.getNamaBulan(bulan) + " " + tahun);
        
        this.resetTableLpBulanan();
        this.showRiwayatTransaksi();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private void resetTableLpBulanan(){
        // set desain tabel
        this.tabelLpBulanan.setRowHeight(29);
        this.tabelLpBulanan.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelLpBulanan.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelLpBulanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
                new String[]{
                    "ID Transaksi", "Nama Karyawan", "Nama Pembeli", "Total Pesanan", "Total Harga", "Tanggal"
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
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(1).setMaxWidth(250);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(2).setMaxWidth(250);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(3).setMaxWidth(110);
        columnModel.getColumn(4).setPreferredWidth(135);
        columnModel.getColumn(4).setMaxWidth(135);
//        columnModel.getColumn(5).setPreferredWidth(210);
//        columnModel.getColumn(5).setMaxWidth(210);
    }
    
    private void showRiwayatTransaksi(){
        // r
        this.resetTableLpBulanan();
        DefaultTableModel model = (DefaultTableModel) this.tabelLpBulanan.getModel();
        
        try{
            String sql = "SELECT trj.id_tr_jual, ky.nama_karyawan, trj.nama_pembeli, trj.total_menu, trj.total_harga, DATE(trj.tanggal), DAYNAME(trj.tanggal) " +
                        "FROM transaksi_jual AS trj " +
                        "JOIN karyawan AS ky " +
                        "ON ky.id_karyawan = trj.id_karyawan " +
                        "WHERE MONTH(tanggal) = "+(this.bulan+1)+" AND YEAR(tanggal) = " + this.tahun + 
                        " ORDER BY trj.tanggal DESC";
            System.out.println(sql);
            System.out.println("NILAI BULAN : " + this.bulan);
            System.out.println("MENAMPILKAN DATA BULAN " + this.waktu.getNamaBulan(this.bulan));
            
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            while(r.next()){
                model.addRow(
                    new Object[]{
                        r.getString(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4) + " Pesanan",
                        this.text.toMoneyCase(r.getString(5)),
                        this.waktu.getNamaHariInIndonesian(r.getString(7)) + ", " + this.text.toDateCase(r.getString(6))
                    }
                );
            }
            
            this.tabelLpBulanan.setModel(model);
            this.cariData = model;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.manage.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblClose = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelLpBulanan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

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

        tabelLpBulanan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabelLpBulanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Nama Karyawan", "Nama Pembeli", "Jumlah", "Harga", "Tanggal"
            }
        ));
        tabelLpBulanan.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tabelLpBulanan.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabelLpBulanan);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(250, 22, 22));
        jLabel1.setText("Cari Transaksi");

        jTextField1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 105, 233));
        jLabel2.setText(" Total Transaksi : 4,232");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 105, 233));
        jLabel4.setText(" Total Pesanan : 5,901");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 17)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 105, 233));
        jLabel5.setText(" Total Pendapatan : Rp. 9.454.464.00");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Detail");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 102, 0));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cetak");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

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
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                            .addComponent(lineTop, javax.swing.GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE)
                            .addComponent(jSeparator1))
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DetailTransaksi dtr = new DetailTransaksi(null, true, this.tabelLpBulanan.getValueAt(this.tabelLpBulanan.getSelectedRow(), 0).toString(), "");
        dtr.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(RiwayatTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RiwayatTransaksi dialog = new RiwayatTransaksi(new javax.swing.JFrame(), true, 11, 2022);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineTop;
    private com.manage.RoundedPanel pnlMain;
    private javax.swing.JTable tabelLpBulanan;
    // End of variables declaration//GEN-END:variables
}
