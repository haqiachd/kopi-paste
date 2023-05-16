package com.window;

import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;

/**
 *
 * @author Achmad Baihaqi
 */
public class ChooseLoginType extends javax.swing.JFrame {

    public ChooseLoginType() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setIconImage(Gambar.getWindowIcon());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        iconLoginManual = new javax.swing.JLabel();
        iconLoginRfid = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblLoginManual = new javax.swing.JLabel();
        lblLoginRfid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pilh Tipe Login");

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 26)); // NOI18N
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Pilih Tipe Login");

        iconLoginManual.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        iconLoginManual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLoginManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-logintype-manual.png"))); // NOI18N
        iconLoginManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconLoginManualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconLoginManualMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconLoginManualMouseExited(evt);
            }
        });

        iconLoginRfid.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        iconLoginRfid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLoginRfid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-logintype-rfid.png"))); // NOI18N
        iconLoginRfid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconLoginRfidMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconLoginRfidMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconLoginRfidMouseExited(evt);
            }
        });

        lineTop.setBackground(new java.awt.Color(0, 0, 0));
        lineTop.setForeground(new java.awt.Color(0, 0, 0));

        lblLoginManual.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblLoginManual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoginManual.setText("Manual");
        lblLoginManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginManualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLoginManualMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLoginManualMouseExited(evt);
            }
        });

        lblLoginRfid.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        lblLoginRfid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoginRfid.setText("RFID");
        lblLoginRfid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginRfidMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLoginRfidMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLoginRfidMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lineTop, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblLoginManual, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblLoginRfid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(iconLoginManual, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(iconLoginRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconLoginManual, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(iconLoginRfid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoginManual, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoginRfid, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconLoginManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginManualMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                LoginWindow login = new LoginWindow();
                login.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_iconLoginManualMouseClicked

    private void iconLoginManualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginManualMouseEntered
        this.iconLoginManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconLoginManual.setIcon(Gambar.getIcon("ic-logintype-manual-entered.png"));
        this.lblLoginManual.setForeground(new Color(51,102,255));
    }//GEN-LAST:event_iconLoginManualMouseEntered

    private void iconLoginManualMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginManualMouseExited
        this.iconLoginManual.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconLoginManual.setIcon(Gambar.getIcon("ic-logintype-manual.png"));
        this.lblLoginManual.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconLoginManualMouseExited

    private void lblLoginManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginManualMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                LoginWindow login = new LoginWindow();
                login.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_lblLoginManualMouseClicked

    private void lblLoginManualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginManualMouseEntered
        this.lblLoginManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconLoginManual.setIcon(Gambar.getIcon("ic-logintype-manual-entered.png"));
        this.lblLoginManual.setForeground(new Color(51,102,255));
    }//GEN-LAST:event_lblLoginManualMouseEntered

    private void lblLoginManualMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginManualMouseExited
        this.lblLoginManual.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconLoginManual.setIcon(Gambar.getIcon("ic-logintype-manual.png"));
        this.lblLoginManual.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_lblLoginManualMouseExited

    private void iconLoginRfidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginRfidMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                LoginWindowRFID login = new LoginWindowRFID();
                login.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_iconLoginRfidMouseClicked

    private void iconLoginRfidMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginRfidMouseEntered
        this.iconLoginRfid.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconLoginRfid.setIcon(Gambar.getIcon("ic-logintype-rfid-entered.png"));
        this.lblLoginRfid.setForeground(new Color(255,51,0));
    }//GEN-LAST:event_iconLoginRfidMouseEntered

    private void iconLoginRfidMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLoginRfidMouseExited
        this.iconLoginRfid.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconLoginRfid.setIcon(Gambar.getIcon("ic-logintype-rfid.png"));
        this.lblLoginRfid.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconLoginRfidMouseExited

    private void lblLoginRfidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginRfidMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                LoginWindowRFID login = new LoginWindowRFID();
                login.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_lblLoginRfidMouseClicked

    private void lblLoginRfidMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginRfidMouseEntered
        this.lblLoginRfid.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconLoginRfid.setIcon(Gambar.getIcon("ic-logintype-rfid-entered.png"));
        this.lblLoginRfid.setForeground(new Color(255,51,0));
    }//GEN-LAST:event_lblLoginRfidMouseEntered

    private void lblLoginRfidMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginRfidMouseExited
        this.lblLoginRfid.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconLoginRfid.setIcon(Gambar.getIcon("ic-logintype-rfid.png"));
        this.lblLoginRfid.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_lblLoginRfidMouseExited


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChooseLoginType.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChooseLoginType().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconLoginManual;
    private javax.swing.JLabel iconLoginRfid;
    private javax.swing.JLabel lblLoginManual;
    private javax.swing.JLabel lblLoginRfid;
    private javax.swing.JLabel lblTop;
    private javax.swing.JSeparator lineTop;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
