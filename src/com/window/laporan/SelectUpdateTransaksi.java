package com.window.laporan;

import com.koneksi.Database;
import com.manage.Message;
import com.media.Audio;
import com.window.transaksi.MenuTransaksiBeli;
import com.window.transaksi.MenuTransaksiJual;
import java.awt.Frame;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class SelectUpdateTransaksi extends javax.swing.JDialog {

    private final Database db = new Database();
    
    private final Frame parent;
    
    public static final String STATUS_JUAL = "jual", STATUS_BELI = "beli";
    
    private final String status, idSelected;
    
    private boolean isHapus = false;
    
    /**
     * Creates new form SelectUpdateTransaksi
     * @param parent
     * @param modal
     * @param status
     * @param idTransaksi
     */
    public SelectUpdateTransaksi(java.awt.Frame parent, boolean modal, String status, String idTransaksi) {
        super(parent, modal);
        initComponents();
        
        this.parent = parent;
        this.status = status;
        this.idSelected = idTransaksi;
        
        this.lblTop.setText(this.lblTop.getText() + this.idSelected);
        
        this.setLocationRelativeTo(null);
        
        this.btnUpdate.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnDelete.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }
    
    public boolean isHapus(){
        return this.isHapus;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlButton = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edi Transaksi ");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));

        lblTop.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        lblTop.setForeground(new java.awt.Color(204, 0, 204));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Pilih Tipe Update Transaksi ");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        pnlButton.setBackground(new java.awt.Color(248, 249, 250));

        btnUpdate.setBackground(new java.awt.Color(59, 111, 231));
        btnUpdate.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Edit Transaksi");
        btnUpdate.setIconTextGap(8);
        btnUpdate.setPreferredSize(new java.awt.Dimension(190, 35));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlButton.add(btnUpdate);
        pnlButton.add(jLabel2);

        btnDelete.setBackground(new java.awt.Color(245, 50, 50));
        btnDelete.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus Transaksi");
        btnDelete.setIconTextGap(8);
        btnDelete.setPreferredSize(new java.awt.Dimension(190, 35));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addComponent(pnlButton, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        try {
            // membuka confirm dialog untuk menghapus data
            Audio.play(Audio.SOUND_INFO);
            int s = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi " + this.idSelected + "?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

            // membuat query untuk menghapus data
            String sql = "";
            switch (this.status) {
                case STATUS_JUAL:
                    sql = "DELETE FROM transaksi_jual WHERE id_tr_jual = '" + this.idSelected + "'";
                    break;
                case STATUS_BELI:
                    sql = "DELETE FROM transaksi_beli WHERE id_tr_beli = '" + this.idSelected + "'";
                    break;
            }

            // mengecek pilihan dari barang
            switch (s) {
                // jika yes maka data akan dihapus
                case JOptionPane.YES_OPTION:
                    // menghapus transaksi
                    boolean sukses = this.db.stat.executeUpdate(sql) > 0;
                    // cek apakah transaksi berhasil dihapus
                    if (sukses) {
                        Message.showInformation(this, "Transaksi berhasil dihapus!");
                        this.isHapus = true;
                    } else {
                        Message.showInformation(this, "Transaksi gagal dihapus!");
                    }
                    break;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }

        this.dispose();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // membuka confirm dialog untuk menghapus data
        Audio.play(Audio.SOUND_INFO);
        int s = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus transaksi " + this.idSelected + "?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(s == JOptionPane.YES_OPTION){
            switch(this.status){
                case STATUS_JUAL : 
                    // membuka window transaksi jual
                    MenuTransaksiJual menuj = new MenuTransaksiJual(this.idSelected);
                    menuj.setVisible(true);
                    // menutup pop up dan window laporan jual
                    this.dispose();
                    this.db.closeConnection();
                    this.parent.dispose();
                    break;
                case STATUS_BELI : 
                    // membuka window transaksi beli
                    MenuTransaksiBeli menub = new MenuTransaksiBeli(this.idSelected);
                    menub.setVisible(true);
                    // menutup pop up dan window laporan beli
                    this.dispose();
                    this.db.closeConnection();
                    this.parent.dispose();
                    break;
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.db.closeConnection();
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(SelectUpdateTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SelectUpdateTransaksi dialog = new SelectUpdateTransaksi(new javax.swing.JFrame(), true, "", "");
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTop;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
