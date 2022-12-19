package com.window.dialog;

import com.koneksi.Dbase;
import com.manage.Message;
import com.manage.Text;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class TransaksiSuccess extends javax.swing.JDialog {

    private final Dbase db = new Dbase();
    
    private final PopUpBackground pop = new PopUpBackground();
    
    private final Text txt = new Text();
    
    private String idTr;
    
    /**
     * Creates new form TransaksiSuccess
     * @param parent
     * @param modal
     * @param idTr
     */
    public TransaksiSuccess(java.awt.Frame parent, boolean modal, String idTr) {
        super(parent, modal);
        this.pop.setVisible(true);
        this.idTr = idTr;
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        this.btnCetak.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnClose.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.showDetailTransaksi();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.setVisible(false);
        this.db.closeConnection();
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
                    "No", "Nama Bahan", "Harga", "Jumlah", "Total Harga"
                }
        ){
            boolean[] canEdit = new boolean[]{
                true, true, true, true, true
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
        columnModel.getColumn(1).setPreferredWidth(190);
        columnModel.getColumn(1).setMaxWidth(190);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(100);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(3).setMaxWidth(60);
    }
    
    private void showDetailTransaksi(){
        this.resetTransaksi();
        DefaultTableModel model = (DefaultTableModel) this.tabelData.getModel();
        
        // query untuk mengambil data transaksi
        String sql = "SELECT ky.nama_karyawan, sp.nama_supplier, tr.total_harga, tr.total_bahan, dtr.nama_bahan, dtr.harga_bahan, dtr.jumlah, dtr.total_harga "
                   + "FROM transaksi_beli AS tr "
                   + "JOIN detail_tr_beli AS dtr "
                   + "ON tr.id_tr_beli = dtr.id_tr_beli "
                   + "JOIN karyawan AS ky "
                   + "ON ky.id_karyawan = tr.id_karyawan "
                   + "JOIN supplier AS sp "
                   + "ON sp.id_supplier = tr.id_supplier "
                   + "WHERE tr.id_tr_beli = '"+this.idTr+"';",
                   totalHarga, totalBahan, namaSupplier, namaKaryawan;
        
        try {
            // eksekusi query
            db.res = db.stat.executeQuery(sql);
            
            int row = 0;
            while(db.res.next()){
                // menambahkan data transaksi ke dalam tabel
                model.addRow(new Object[]{
                    row,
                    db.res.getString("dtr.nama_bahan"),
                    txt.toMoneyCase(db.res.getString("dtr.harga_bahan")),
                    db.res.getString("dtr.jumlah"),
                    txt.toMoneyCase(db.res.getString("dtr.total_harga"))
                });
                row++;
                
                // jika query pada baris terakhir maka akan 
                if(db.res.isLast()){
                    // menambahkan total harga
                    model.addRow(new Object[]{
                        "", "Total", "", db.res.getString("tr.total_bahan"), txt.toMoneyCase(db.res.getString("tr.total_harga"))
                    });
                    
                    // menampilkan data transaksi
                    this.lblNamaKy.setText(this.lblNamaKy.getText() + db.res.getString("ky.nama_karyawan"));
                    this.lblNamaSp.setText(this.lblNamaSp.getText() + db.res.getString("sp.nama_supplier"));
                }
            }
            
            
            this.tabelData.setModel(model);
            this.tabelData.setRowHeight(this.tabelData.getRowCount()-1, 40);
            
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
        btnCetak = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        icTop = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelData = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        lblNamaKy = new javax.swing.JLabel();
        lblNamaSp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 7));
        pnlMain.setForeground(new java.awt.Color(0, 90, 215));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Transaksi Berhasil!");

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        btnCetak.setBackground(new java.awt.Color(255, 102, 0));
        btnCetak.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setText("Cetak Struk");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

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

        tabelData.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
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
        jScrollPane1.setViewportView(tabelData);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        lblNamaKy.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNamaKy.setText("Nama Karyawan : ");

        lblNamaSp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNamaSp.setText("Nama Supplier   : ");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(icTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNamaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamaKy, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(btnCetak)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(icTop, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNamaKy, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNamaSp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCetak)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCetakActionPerformed

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
            java.util.logging.Logger.getLogger(TransaksiSuccess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TransaksiSuccess dialog = new TransaksiSuccess(new javax.swing.JFrame(), true, "TRB0048");
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
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel icTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblNamaKy;
    private javax.swing.JLabel lblNamaSp;
    private javax.swing.JLabel lblTop;
    private javax.swing.JSeparator lineTop;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTable tabelData;
    // End of variables declaration//GEN-END:variables
}
