package com.window.transaksi;

import com.koneksi.Database;
import com.manage.Message;
import com.manage.Text;
import com.media.Audio;
import com.media.Gambar;
import com.window.dialog.PopUpBackground;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class DiskonToko extends javax.swing.JDialog {

    private final Database dbase = new Database();
    
    private final PopUpBackground popUp = new PopUpBackground();
    
    private final Text text = new Text();

    public DiskonToko(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        popUp.setVisible(true);
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
        // set ui button
        this.btnTambah.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnEdit.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnHapus.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        this.showDiskon();
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.popUp.dispose();
        this.dbase.closeConnection();
    }
    
    private void resetTabelDiskon(){
        // set desain tabel
        this.tblDiskon.setRowHeight(29);
        this.tblDiskon.getTableHeader().setBackground(new java.awt.Color(0,105,233));
        this.tblDiskon.getTableHeader().setForeground(new java.awt.Color(255, 255, 255)); 
        
        // set model tabel
        this.tblDiskon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
                new String[]{
                    "ID Diskon", "Minimal Harga", "Total Diskon", "Presentase", "Tanggal Mulai", "Tanggal Akhir"
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
        TableColumnModel columnModel = this.tblDiskon.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(0).setMaxWidth(90);
        columnModel.getColumn(1).setPreferredWidth(160);
        columnModel.getColumn(1).setMaxWidth(160);
        columnModel.getColumn(2).setPreferredWidth(160);
        columnModel.getColumn(2).setMaxWidth(160);
        columnModel.getColumn(3).setPreferredWidth(90);
        columnModel.getColumn(3).setMaxWidth(90);
        columnModel.getColumn(4).setPreferredWidth(160);
        columnModel.getColumn(4).setMaxWidth(160);
        columnModel.getColumn(5).setPreferredWidth(160);
        columnModel.getColumn(5).setMaxWidth(160);
    }
    
    private void showDiskon(){
        this.resetTabelDiskon();
        DefaultTableModel model = (DefaultTableModel) this.tblDiskon.getModel();
        
        try{
            String sql = "SELECT * FROM diskon WHERE tgl_akhir >= CURRENT_DATE() ORDER BY min_harga ASC";
            this.dbase.res = this.dbase.stat.executeQuery(sql);
            
            double minHarga, ttlDiskon;
            while(dbase.res.next()){
                minHarga = this.dbase.res.getDouble("min_harga");
                ttlDiskon = this.dbase.res.getDouble("ttl_diskon");
                model.addRow(
                        new String[]{
                            this.dbase.res.getString("id_diskon"),
                            this.text.toMoneyCase((int)minHarga),
                            this.text.toMoneyCase((int)ttlDiskon),
                            String.format("%.1f ", (double)(ttlDiskon / minHarga) * 100f)+"%",
                            this.text.toDateCase(this.dbase.res.getString("tgl_mulai")),
                            this.text.toDateCase(this.dbase.res.getString("tgl_akhir")),

                        }
                );
                
                this.tblDiskon.setModel(model);
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblClose = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDiskon = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        lineBottom = new javax.swing.JSeparator();
        btnHapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));
        pnlMain.setRoundBottomLeft(30);
        pnlMain.setRoundBottomRight(30);
        pnlMain.setRoundTopLeft(30);
        pnlMain.setRoundTopRight(30);

        lblDialogName.setFont(new java.awt.Font("Dialog", 1, 28)); // NOI18N
        lblDialogName.setForeground(new java.awt.Color(250, 22, 22));
        lblDialogName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDialogName.setText("Diskon Toko");
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

        tblDiskon.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblDiskon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id Diskon", "Minimal Bayar", "Jumlah Diskon", "Tanggal Mulai", "Tanggal Akhir"
            }
        ));
        tblDiskon.setSelectionBackground(new java.awt.Color(71, 230, 143));
        tblDiskon.getTableHeader().setReorderingAllowed(false);
        tblDiskon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDiskonMouseClicked(evt);
            }
        });
        tblDiskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDiskonKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDiskon);

        btnEdit.setBackground(new java.awt.Color(34, 119, 237));
        btnEdit.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-edit.png"))); // NOI18N
        btnEdit.setText("Update");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnTambah.setBackground(new java.awt.Color(41, 180, 50));
        btnTambah.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-data-tambah.png"))); // NOI18N
        btnTambah.setText("Tambah");
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

        lineBottom.setBackground(new java.awt.Color(0, 0, 0));
        lineBottom.setForeground(new java.awt.Color(0, 0, 0));

        btnHapus.setBackground(new java.awt.Color(220, 41, 41));
        btnHapus.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
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

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDialogName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 819, Short.MAX_VALUE)
                            .addComponent(lineTop)
                            .addComponent(lineBottom))
                        .addGap(0, 29, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDialogName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lineBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnTambah)
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

    private void tblDiskonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDiskonMouseClicked

    }//GEN-LAST:event_tblDiskonMouseClicked

    private void tblDiskonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDiskonKeyPressed

    }//GEN-LAST:event_tblDiskonKeyPressed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        UpdateDiskonToko pop = new UpdateDiskonToko(null, true, UpdateDiskonToko.EDIT, this.tblDiskon.getValueAt(this.tblDiskon.getSelectedRow(), 0).toString());
        pop.setLocation(pop.getX(), this.getY());
        pop.setVisible(true);
        
        // reset tabel diskon
        this.showDiskon();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        UpdateDiskonToko pop = new UpdateDiskonToko(null, true, UpdateDiskonToko.TAMBAH, "");
        pop.setLocation(pop.getX(), this.getY());
        pop.setVisible(true);
        
        // reset tabel diskon
        this.showDiskon();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int status;
        try {
            // mengecek apakah ada data yang dipilih atau tidak
            if (this.tblDiskon.getSelectedRow() > -1) {
                // membuka confirm dialog untuk menghapus data
                Audio.play(Audio.SOUND_INFO);
                status = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus diskon?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
                // mengecek pilihan dari jdialog
                switch (status) {
                    // jika yes maka diskon akan dihapus
                    case JOptionPane.YES_OPTION:
                        // menghapus diskon
                        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        String idDiskon = this.tblDiskon.getValueAt(this.tblDiskon.getSelectedRow(), 0).toString(),
                         sql = "DELETE FROM diskon WHERE id_diskon = '" + idDiskon + "'";
                        
                        // mengecek kesamaan id
                        if(!idDiskon.equalsIgnoreCase(this.tblDiskon.getValueAt(this.tblDiskon.getSelectedRow(), 0).toString())){
                            Message.showWarning(this, "Diskon gagal dihapus!");
                            this.tblDiskon.removeRowSelectionInterval(this.tblDiskon.getSelectedRow(), this.tblDiskon.getSelectedRow());
                            return;
                        }
                        // mengecek apakah data supplier berhasil terhapus atau tidak
                        if (this.dbase.stat.executeUpdate(sql) > 0) {
                            Message.showInformation(this, "Diskon berhasil dihapus!");
                            // reset data menu
                            this.showDiskon();
                        } else {
                            Message.showWarning(this, "Diskon gagal dihapus!");
                        }
                        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        break;
                }
            } else {
                Message.showWarning(this, "Tidak ada diskon yang dipilih!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseEntered
        this.btnTambah.setIcon(Gambar.getIcon("ic-data-tambah-entered.png"));
    }//GEN-LAST:event_btnTambahMouseEntered

    private void btnTambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseExited
        this.btnTambah.setIcon(Gambar.getIcon("ic-data-tambah.png"));
    }//GEN-LAST:event_btnTambahMouseExited

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit.png"));
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        this.btnEdit.setIcon(Gambar.getIcon("ic-data-edit.png"));
    }//GEN-LAST:event_btnEditMouseExited

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus-entered.png"));
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
        this.btnHapus.setIcon(Gambar.getIcon("ic-data-hapus.png"));
    }//GEN-LAST:event_btnHapusMouseExited


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiskonToko.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiskonToko dialog = new DiskonToko(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineBottom;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    private javax.swing.JTable tblDiskon;
    // End of variables declaration//GEN-END:variables
}
