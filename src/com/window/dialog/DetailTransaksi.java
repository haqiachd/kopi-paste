package com.window.dialog;

import com.manage.User;
import com.media.Audio;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class DetailTransaksi extends javax.swing.JDialog {

    private final PopUpBackground pop = new PopUpBackground();
    
    private final String id;
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     * @param id
     */
    public DetailTransaksi(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        this.pop.setVisible(true);
        
        this.id = id;
        
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
//        this.btnBackup.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        this.tabelDetail.setRowHeight(29);
        this.tabelDetail.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelDetail.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        this.resetTableLpBulanan();
        
        this.lblDialogName.setText(this.lblDialogName.getText() + id);
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    private void resetTableLpBulanan(){
        // set desain tabel
        this.tabelDetail.setRowHeight(29);
        this.tabelDetail.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tabelDetail.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tabelDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {id, "Kentang Goreng", "4", "Rp. 56.000,00"},
                {null, "Es Jeruk", "1", "Rp. 10.000,00"},
                {null, "Chocholate", "2", "Rp. 24.000,00"},
                {null, "Ayam Goreng", "1", "Rp. 13.300,00"}
            },
            new String [] {
                "ID Transaksi", "Nama Menu", "Jumlah", "Harga"
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
        TableColumnModel columnModel = this.tabelDetail.getColumnModel();
//        columnModel.getColumn(0).setPreferredWidth(90);
//        columnModel.getColumn(0).setMaxWidth(90);
//        columnModel.getColumn(1).setPreferredWidth(235);
//        columnModel.getColumn(1).setMaxWidth(235);
//        columnModel.getColumn(2).setPreferredWidth(235);
//        columnModel.getColumn(2).setMaxWidth(235);
//        columnModel.getColumn(3).setPreferredWidth(100);
//        columnModel.getColumn(3).setMaxWidth(100);
//        columnModel.getColumn(4).setPreferredWidth(125);
//        columnModel.getColumn(4).setMaxWidth(125);
//        columnModel.getColumn(5).setPreferredWidth(210);
//        columnModel.getColumn(5).setMaxWidth(210);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.manage.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDetail = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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
                {"TRJ0030", "Kentang Goreng", "4", "Rp. 56.000,00"},
                {null, "Es Jeruk", "1", "Rp. 10.000,00"},
                {null, "Chocholate", "2", "Rp. 24.000,00"},
                {null, "Ayam Goreng", "1", "Rp. 13.300,00"}
            },
            new String [] {
                "ID Transaksi", "Nama Menu", "Jumlah", "Harga"
            }
        ));
        jScrollPane1.setViewportView(tabelDetail);

        jButton2.setBackground(new java.awt.Color(255, 102, 0));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cetak");

        jButton1.setBackground(new java.awt.Color(246, 70, 70));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tutup");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGap(10, 10, 10)
                        .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
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
            java.util.logging.Logger.getLogger(DetailTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DetailTransaksi dialog = new DetailTransaksi(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineTop;
    private com.manage.RoundedPanel pnlMain;
    private javax.swing.JTable tabelDetail;
    // End of variables declaration//GEN-END:variables
}
