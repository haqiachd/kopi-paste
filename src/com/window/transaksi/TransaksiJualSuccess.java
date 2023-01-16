package com.window.transaksi;

import com.koneksi.Database;
import com.manage.Laporan;
import com.manage.Message;
import com.manage.Text;
import com.window.dialog.PopUpBackground;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class TransaksiJualSuccess extends javax.swing.JDialog {

    private final Database db = new Database();
    
    private final PopUpBackground pop = new PopUpBackground();
    
    private final Text txt = new Text();
    
    private final Laporan report = new Laporan();
    
    private final String idTr;
    
    /**
     * Creates new form TransaksiSuccess
     * @param parent
     * @param modal
     * @param idTr
     */
    public TransaksiJualSuccess(java.awt.Frame parent, boolean modal, String idTr) {
        super(parent, modal);
        this.pop.setVisible(true);
        this.idTr = idTr;
        initComponents();
        
        this.setLocationRelativeTo(null);
        
//        this.btnCetak.setUI(new javax.swing.plaf.basic.BasicButtonUI());
//        this.btnClose.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.showDetailTransaksi();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.setVisible(false);
    }
    
    private void resetTransaksi(){
        // setting desain tabel
        this.tabelData.setRowHeight(29);
        this.tabelData.getTableHeader().setBackground(new java.awt.Color(248,249,250));
        this.tabelData.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        
        // set model tabel
        this.tabelData.setModel(new DefaultTableModel(
                new String[][]{},
                new String[]{
                    "No", "Nama Menu", "Harga", "Jumlah", "Total Harga"
                }
        ){
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return canEdit[columnIndex];
            }
        });
        
        // set size column tabel
        TableColumnModel columnModel = tabelData.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(170);
        columnModel.getColumn(1).setMaxWidth(170);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(2).setMaxWidth(110);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(3).setMaxWidth(60);
    }
    
    private void showDetailTransaksi(){
        this.resetTransaksi();
        DefaultTableModel model = (DefaultTableModel) this.tabelData.getModel();
        
        // query untuk mengambil data transaksi
        String sql = "SELECT tr.nama_karyawan, tr.total_harga, tr.total_menu, tr.total_bayar, tr.total_kembalian, dtr.nama_menu, dtr.harga_menu, dtr.jumlah, dtr.total_harga "
                   + "FROM transaksi_jual AS tr "
                   + "JOIN detail_tr_jual AS dtr "
                   + "ON tr.id_tr_jual = dtr.id_tr_jual "
                   + "WHERE tr.id_tr_jual = '"+this.idTr+"';";
        
        try {
            // eksekusi query
            db.res = db.stat.executeQuery(sql);
            
            int row = 1;
            while(db.res.next()){
                // menambahkan data transaksi ke dalam tabel
                model.addRow(new Object[]{
                    row,
                    db.res.getString("dtr.nama_menu"),
                    txt.toMoneyCase(db.res.getString("dtr.harga_menu")),
                    db.res.getString("dtr.jumlah"),
                    txt.toMoneyCase(db.res.getString("dtr.total_harga"))
                });
                row++;
                
                // jika query pada baris terakhir maka akan 
                if(db.res.isLast()){
                    // menambahkan total harga
                    model.addRow(new Object[]{
                        "", "Total", "", db.res.getString("tr.total_menu"), txt.toMoneyCase(db.res.getString("tr.total_harga"))
                    });
                    
                    // menampilkan data transaksi
                    this.lblNamaKy.setText(this.lblNamaKy.getText() + db.res.getString("tr.nama_karyawan"));
                    this.lblTotalBayar.setText(this.lblTotalBayar.getText() + txt.toMoneyCase(db.res.getInt("tr.total_bayar")));
                    this.lblTotalKembalian.setText(this.lblTotalKembalian.getText() + txt.toMoneyCase(this.db.res.getInt("tr.total_kembalian")));
                }
            }
            
            // menampilkan data transaksi ke tabel
            this.tabelData.setModel(model);
            this.tabelData.setRowHeight(this.tabelData.getRowCount()-1, 40);
            this.tabelData.setRowSelectionInterval(this.tabelData.getRowCount()-1, this.tabelData.getRowCount()-1);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        btnClose = new javax.swing.JButton();
        icTop = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        lblNamaKy = new javax.swing.JLabel();
        lblTotalBayar = new javax.swing.JLabel();
        lblTotalKembalian = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 7));
        pnlMain.setForeground(new java.awt.Color(0, 90, 215));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Penjualan Berhasil!");

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        btnClose.setBackground(new java.awt.Color(255, 0, 0));
        btnClose.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        icTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/aaaa.gif"))); // NOI18N
        icTop.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        tabelData.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        tabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Bahan", "Harga", "Jumlah", "Total Harga"
            }
        ));
        tabelData.getTableHeader().setReorderingAllowed(false);
        tabelData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDataKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabelData);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        lblNamaKy.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblNamaKy.setText("Nama Karyawan    : ");

        lblTotalBayar.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalBayar.setText("Total Bayar             : ");

        lblTotalKembalian.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotalKembalian.setText("Total Kembalian    : ");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(icTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNamaKy, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(icTop, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNamaKy, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDataKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.dispose();
        }
    }//GEN-LAST:event_tabelDataKeyPressed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane.showMessageDialog(this, this.idTr);
        this.dispose();
        this.report.cetakStrukPenjualan(this.db.conn, this.idTr);
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
            java.util.logging.Logger.getLogger(TransaksiJualSuccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TransaksiJualSuccess dialog = new TransaksiJualSuccess(new javax.swing.JFrame(), true, "TRJ0150");
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
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel icTop;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblNamaKy;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblTotalBayar;
    private javax.swing.JLabel lblTotalKembalian;
    private javax.swing.JSeparator lineTop;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTable tabelData;
    // End of variables declaration//GEN-END:variables
}
