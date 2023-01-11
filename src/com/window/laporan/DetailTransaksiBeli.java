package com.window.laporan;

import com.koneksi.Database;
import com.manage.Bahan;
import com.manage.Message;
import com.manage.Text;
import com.window.dialog.PopUpBackground;
import java.awt.event.KeyEvent;

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class DetailTransaksiBeli extends javax.swing.JDialog {

    private final Database db = new Database();
    
    private final Bahan ba = new Bahan();
    
    private final PopUpBackground pop = new PopUpBackground();
    
    private final Text txt = new Text();
    
    private final String id;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param id
     */
    public DetailTransaksiBeli(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        this.pop.setVisible(true);
        
        this.id = id;
        
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        this.lblDialogName.setText(this.lblDialogName.getText() + id);
        
        if(!this.isExistDetail()){
            Message.showWarning(null, "Gagal menampilkan detail transaksi beli!");
            this.btnCloseActionPerformed(null);
        }
        
        System.out.println("esfessef");
        
        this.resetTableDetail();
        this.showDetailTransaksi();
        
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private boolean isExistDetail(){
        try{
            this.db.res = this.db.stat.executeQuery("SELECT COUNT(id_tr_beli) AS ttl FROM detail_tr_beli WHERE id_tr_beli = '"+this.id+"'");
            if(this.db.res.next()){
                return this.db.res.getInt(1) > 0;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private void resetTableDetail(){
        // set desain tabel
        this.tabelDetail.setRowHeight(29);
        this.tabelDetail.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelDetail.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID Transaksi", "Nama Bahan", "Jumlah", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // set size kolom tabel
        TableColumnModel columnModel = this.tabelDetail.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(0).setMaxWidth(90);
        columnModel.getColumn(1).setPreferredWidth(220);
        columnModel.getColumn(1).setMaxWidth(220);
        columnModel.getColumn(2).setPreferredWidth(130);
        columnModel.getColumn(2).setMaxWidth(130);
//        columnModel.getColumn(3).setPreferredWidth(100);
//        columnModel.getColumn(3).setMaxWidth(100);
    }
//    
    private void showDetailTransaksi(){
        // reset tabel
        this.resetTableDetail();
        DefaultTableModel model = (DefaultTableModel) this.tabelDetail.getModel();
        
        try{
        // query untuk mengambil data transaksi
        String sql = "SELECT tr.id_tr_beli, tr.nama_karyawan, tr.total_harga, tr.total_bahan, dtr.nama_bahan, dtr.satuan_bahan, dtr.jumlah, dtr.total_harga "
                   + "FROM transaksi_beli AS tr "
                   + "JOIN detail_tr_beli AS dtr "
                   + "ON tr.id_tr_beli = dtr.id_tr_beli "
                   + "WHERE tr.id_tr_beli = '"+this.id+"';";
            
            // eksekusi query
            this.db.res = this.db.stat.executeQuery(sql);
            
            int row = 0;
            while(this.db.res.next()){
                String idTr; 
                
                // id transaksi hanya ditampilkan pada baris ke satu saja
                if(row == 0){
                    idTr = this.db.res.getString("tr.id_tr_beli");
                }else{
                    idTr = "";
                }
                
                // menambahkan data detail ke tabel
                model.addRow(new Object[]{
                        idTr,
                        this.db.res.getString("dtr.nama_bahan"),
                        this.ba.getNilaiSatuan(Float.parseFloat(String.format("%.1f", this.db.res.getFloat("dtr.jumlah"))), this.db.res.getString("dtr.satuan_bahan")),
                        this.txt.toMoneyCase(this.db.res.getString("dtr.total_harga"))
                    }
                );
                row++;
                
                // jika berada di index terakhir
                if(this.db.res.isLast()){
                    // menghitung total jumlah bahan
                    String ttlJml = Integer.toString(model.getRowCount()) + " Bahan";

                    // menampilkan total jumlah pesanan dan total harga
                    model.addRow(
                        new Object[]{
                            "", "Total", ttlJml, txt.toMoneyCase(this.db.res.getString("tr.total_harga"))
                        }
                    );                    
                }
            }
            
            
            // menampilkan data kedalam tabel
            this.tabelDetail.setModel(model);
            this.tabelDetail.setRowHeight(this.tabelDetail.getRowCount()-1, 50);
            this.tabelDetail.setRowSelectionInterval(this.tabelDetail.getRowCount()-1, this.tabelDetail.getRowCount()-1);
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDetail = new javax.swing.JTable();
        btnClose = new javax.swing.JButton();

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
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(35, 136, 211), 10));
        pnlMain.setRoundBottomLeft(30);
        pnlMain.setRoundBottomRight(30);
        pnlMain.setRoundTopLeft(30);
        pnlMain.setRoundTopRight(30);

        lblDialogName.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        lblDialogName.setForeground(new java.awt.Color(26, 105, 243));
        lblDialogName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialogName.setText("Detail Transaksi ");
        lblDialogName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        tabelDetail.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        tabelDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"TRJ0030", "Kentang Goreng", null, "4", "Rp. 56.000,00"},
                {null, "Es Jeruk", null, "1", "Rp. 10.000,00"},
                {null, "Chocholate", null, "2", "Rp. 24.000,00"},
                {null, "Ayam Goreng", null, "1", "Rp. 13.300,00"}
            },
            new String [] {
                "ID Transaksi", "Nama Bahan", "Harga", "Jumlah", "Total Harga"
            }
        ));
        tabelDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelDetailKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabelDetail);

        btnClose.setBackground(new java.awt.Color(246, 70, 70));
        btnClose.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Tutup");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDialogName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lineTop))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(lblDialogName, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tabelDetailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelDetailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.dispose();
        }
    }//GEN-LAST:event_tabelDetailKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
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
            java.util.logging.Logger.getLogger(DetailTransaksiBeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DetailTransaksiBeli dialog = new DetailTransaksiBeli(new javax.swing.JFrame(), true, "TRB0130");
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    private javax.swing.JTable tabelDetail;
    // End of variables declaration//GEN-END:variables
}
