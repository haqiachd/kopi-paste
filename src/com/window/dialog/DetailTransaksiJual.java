package com.window.dialog;


import com.koneksi.Koneksi;
import com.manage.Message;
import com.manage.Text;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class DetailTransaksiJual extends javax.swing.JDialog {

    private final PopUpBackground pop = new PopUpBackground();
    
    private final Text txt = new Text();
    
    private final String id;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param id
     */
    public DetailTransaksiJual(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        this.pop.setVisible(true);
        
        this.id = id;
        
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        this.lblDialogName.setText(this.lblDialogName.getText() + id);
        
        this.resetTableDetail();
        this.showDetailTransaksi();
        
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
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
                "ID Transaksi", "Nama Menu", "Harga", "Jumlah", "Total Harga"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
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
        columnModel.getColumn(1).setPreferredWidth(190);
        columnModel.getColumn(1).setMaxWidth(190);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(2).setMaxWidth(120);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(3).setMaxWidth(60);
    }
    
    private void showDetailTransaksi(){
        // reset tabel
        this.resetTableDetail();
        DefaultTableModel model = (DefaultTableModel) this.tabelDetail.getModel();
        
        try{
            String sql = "SELECT id_tr_jual, nama_menu, harga_menu, jumlah, total_harga "
                       + "FROM detail_tr_jual  "
                       + "WHERE id_tr_jual = '"+this.id+"'";
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            int row = 0;
            while(r.next()){
                String idTr; 
                
                // id transaksi hanya ditampilkan pada baris ke satu saja
                if(row == 0){
                    idTr = r.getString("id_tr_jual");
                }else{
                    idTr = "";
                }
                
                // menambahkan data detail ke tabel
                model.addRow(new Object[]{
                        idTr,
                        r.getString("nama_menu"),
                        txt.toMoneyCase(r.getString("harga_menu")),
                        r.getString("jumlah"),
                        txt.toMoneyCase(r.getString("total_harga"))
                    }
                );
                row++;
            }
            
            c.close();
            r.close();
            s.close();
            
            int jumlah = 0, harga = 0;
            // menghitung total menu dan harga
            for(int i = 0; i < model.getRowCount(); i++){
                jumlah += Integer.parseInt(model.getValueAt(i, 3).toString());
                harga += Integer.parseInt(txt.removeMoneyCase(model.getValueAt(i, 4).toString()));
            }
            
            // menampilkan total jumlah pesanan dan total harga
            model.addRow(
                new Object[]{
                    "Total", "", "", jumlah, txt.toMoneyCase(""+harga)
                }
            );
            
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
                "ID Transaksi", "Nama Menu", "Harga", "Jumlah", "Total Harga"
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
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            java.util.logging.Logger.getLogger(DetailTransaksiJual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DetailTransaksiJual dialog = new DetailTransaksiJual(new javax.swing.JFrame(), true, "");
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
