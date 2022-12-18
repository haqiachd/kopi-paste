package com.window.dialog;

import com.manage.FileManager;
import com.manage.Message;
import com.manage.Waktu;
import com.media.Audio;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Achmad Baihaqi
 */
public class Pengaturan extends javax.swing.JDialog {

    private final PopUpBackground pop = new PopUpBackground();
    
    private final FileManager fm = new FileManager();
    
    private final Waktu waktu = new Waktu();
    
    /**
     * Creates new form UserProfile
     * @param parent
     * @param modal
     */
    public Pengaturan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.pop.setVisible(true);
        
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
        
        this.btnBackup.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }
    
    public void backupDB() {
        try {
            // membuka file chooser untuk menginputkan nama file
            String savePath = fm.saveFile(null);
            if(savePath == null){
                return;
            }
             savePath += "-" + waktu.getCurrentDate() + ".sql";
            System.out.println("Save Path : " + savePath);
            
            // membuat command untuk membackup database dengan mysqldump
            String cmdCommand = "C:\\xampp\\mysql\\bin\\mysqldump -u root kopi_paste -r \"" + savePath + "\"";
            System.out.println(cmdCommand);
            
            // eksekusi command untuk backup database
            Process runtimeProcess = Runtime.getRuntime().exec(cmdCommand);
            int processComplete = runtimeProcess.waitFor();

            // cek apakah proses backup berhasil
            if (processComplete == 0) {
                Message.showInformation(this, "Proses Backup Berhasil");
                File open = new File(new File(savePath).getAbsolutePath());
                Desktop desktop = Desktop.getDesktop();
                desktop.open(open);
            } else {
                Message.showWarning(this, "Gagal Membackup Database");
            }

        } catch (IOException | InterruptedException ex) {
            Message.showException(null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblClose = new javax.swing.JLabel();
        lblBck = new javax.swing.JLabel();
        btnBackup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));
        pnlMain.setRoundBottomLeft(30);
        pnlMain.setRoundBottomRight(30);
        pnlMain.setRoundTopLeft(30);
        pnlMain.setRoundTopRight(30);

        lblDialogName.setFont(new java.awt.Font("Dialog", 1, 34)); // NOI18N
        lblDialogName.setForeground(new java.awt.Color(26, 105, 243));
        lblDialogName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDialogName.setText("Pengaturan");
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

        lblBck.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblBck.setText("Backup Database     :");

        btnBackup.setBackground(new java.awt.Color(204, 204, 204));
        btnBackup.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        btnBackup.setText("Backup");
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
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
                        .addComponent(lblDialogName, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblBck)
                                .addGap(9, 9, 9)
                                .addComponent(btnBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDialogName, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(lblClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBackup, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap(450, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
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

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
        Audio.play(Audio.SOUND_INFO);
        this.backupDB();
    }//GEN-LAST:event_btnBackupActionPerformed

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
            java.util.logging.Logger.getLogger(Pengaturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pengaturan dialog = new Pengaturan(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBackup;
    private javax.swing.JLabel lblBck;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JSeparator lineTop;
    private com.ui.RoundedPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
