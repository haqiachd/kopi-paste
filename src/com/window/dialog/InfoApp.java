package com.window.dialog;

import com.manage.Internet;
import com.manage.Message;
import com.media.Gambar;
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class InfoApp extends javax.swing.JDialog {

    private Internet inet = new Internet();
    
    private PopUpBackground pop = new PopUpBackground();
    
    /**
     * Creates new form UserProfile
     */
    public InfoApp(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.pop.setVisible(true);
        
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        this.setLocationRelativeTo(null);
    }
    
    @Override
    public void dispose(){
        super.dispose();
        this.pop.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new com.ui.RoundedPanel();
        lblDialogName = new javax.swing.JLabel();
        lineTop = new javax.swing.JSeparator();
        lblClose = new javax.swing.JLabel();
        lblNamaApp = new javax.swing.JLabel();
        valNamaApp = new javax.swing.JLabel();
        lblVersi = new javax.swing.JLabel();
        valVersi = new javax.swing.JLabel();
        valDev = new javax.swing.JLabel();
        lblDev = new javax.swing.JLabel();
        pnlKontakDev = new javax.swing.JPanel();
        lblKontakDev = new javax.swing.JLabel();
        iconGmail = new javax.swing.JLabel();
        lblGmail = new javax.swing.JLabel();
        lblWa = new javax.swing.JLabel();
        iconWa = new javax.swing.JLabel();
        lblTele = new javax.swing.JLabel();
        iconTele = new javax.swing.JLabel();
        iconIg = new javax.swing.JLabel();
        lblIg = new javax.swing.JLabel();
        iconTwit = new javax.swing.JLabel();
        lblTwit = new javax.swing.JLabel();
        lblColabolator = new javax.swing.JLabel();
        lblCol1 = new javax.swing.JLabel();
        lblCol2 = new javax.swing.JLabel();
        lblCol3 = new javax.swing.JLabel();
        lblCol4 = new javax.swing.JLabel();

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
        lblDialogName.setText("Informasi Aplikasi");
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

        lblNamaApp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNamaApp.setText("Nama Aplikasi");

        valNamaApp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        valNamaApp.setText(": Kopi Paste");

        lblVersi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblVersi.setText("Versi");

        valVersi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        valVersi.setText(": 1.0");

        valDev.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        valDev.setText(": C2 Team");

        lblDev.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDev.setText("Developer");

        pnlKontakDev.setBackground(new java.awt.Color(248, 249, 250));
        pnlKontakDev.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 101, 9), 3));

        lblKontakDev.setBackground(new java.awt.Color(230, 101, 9));
        lblKontakDev.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        lblKontakDev.setForeground(new java.awt.Color(255, 255, 255));
        lblKontakDev.setText(" Kontak Developer");
        lblKontakDev.setOpaque(true);

        iconGmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconGmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-dialog-infoapp-gmail-entered.png"))); // NOI18N
        iconGmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconGmailMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconGmailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconGmailMouseExited(evt);
            }
        });

        lblGmail.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblGmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGmail.setText("Gmail");

        lblWa.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblWa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWa.setText("WhatsApp");

        iconWa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconWa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-dialog-infoapp-wa-entered.png"))); // NOI18N
        iconWa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconWaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconWaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconWaMouseExited(evt);
            }
        });

        lblTele.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblTele.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTele.setText("Telegram");

        iconTele.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconTele.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-dialog-infoapp-tele-entered.png"))); // NOI18N
        iconTele.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconTeleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconTeleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconTeleMouseExited(evt);
            }
        });

        iconIg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconIg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-dialog-infoapp-ig-entered.png"))); // NOI18N
        iconIg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconIgMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconIgMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconIgMouseExited(evt);
            }
        });

        lblIg.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblIg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIg.setText("Instagram");

        iconTwit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconTwit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-dialog-infoapp-tt-entered.png"))); // NOI18N
        iconTwit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconTwitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconTwitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconTwitMouseExited(evt);
            }
        });

        lblTwit.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        lblTwit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTwit.setText("Twitter");

        javax.swing.GroupLayout pnlKontakDevLayout = new javax.swing.GroupLayout(pnlKontakDev);
        pnlKontakDev.setLayout(pnlKontakDevLayout);
        pnlKontakDevLayout.setHorizontalGroup(
            pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblKontakDev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlKontakDevLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconGmail, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(lblGmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconWa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblWa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconTele, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTele, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconIg, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconTwit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTwit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKontakDevLayout.setVerticalGroup(
            pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKontakDevLayout.createSequentialGroup()
                .addComponent(lblKontakDev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlKontakDevLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlKontakDevLayout.createSequentialGroup()
                        .addComponent(iconGmail, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblGmail))
                    .addGroup(pnlKontakDevLayout.createSequentialGroup()
                        .addComponent(iconWa, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblWa))
                    .addGroup(pnlKontakDevLayout.createSequentialGroup()
                        .addComponent(iconTele, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTele))
                    .addGroup(pnlKontakDevLayout.createSequentialGroup()
                        .addComponent(iconIg, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIg))
                    .addGroup(pnlKontakDevLayout.createSequentialGroup()
                        .addComponent(iconTwit, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTwit)))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        lblColabolator.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblColabolator.setText("Contributors");

        lblCol1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCol1.setText(": Achmad Baihaqi");
        lblCol1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCol1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCol1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCol1MouseExited(evt);
            }
        });

        lblCol2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCol2.setText("  Mohammad Ilham Islamy");
        lblCol2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCol2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCol2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCol2MouseExited(evt);
            }
        });

        lblCol3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCol3.setText("  Septian Yoga Pamungkas");
        lblCol3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCol3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCol3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCol3MouseExited(evt);
            }
        });

        lblCol4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblCol4.setText("  Widyasari Raisya Salsabilla");
        lblCol4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCol4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCol4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCol4MouseExited(evt);
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
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblVersi, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(valVersi, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lineTop, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblNamaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(valNamaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlKontakDev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(lblDev, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(valDev, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlMainLayout.createSequentialGroup()
                                    .addComponent(lblColabolator, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblCol1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblCol3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGap(182, 182, 182)
                                .addComponent(lblCol4, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(lblNamaApp, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(valNamaApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblVersi, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(valVersi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDev, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valDev, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblColabolator, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCol1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCol2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCol3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCol4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(pnlKontakDev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
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
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void iconGmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconGmailMouseClicked
        try {
            this.inet.openLink("mailto:hakiahmad756@gmail.com");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_iconGmailMouseClicked

    private void iconGmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconGmailMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconGmail.setIcon(Gambar.getIcon("ic-dialog-infoapp-gmail.png"));
        this.lblGmail.setForeground(new Color(246,46,46));
    }//GEN-LAST:event_iconGmailMouseEntered

    private void iconGmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconGmailMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconGmail.setIcon(Gambar.getIcon("ic-dialog-infoapp-gmail-entered.png"));
        this.lblGmail.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconGmailMouseExited

    private void iconWaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconWaMouseClicked
        try {
            this.inet.openLink("https://wa.me/6285655864624");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_iconWaMouseClicked

    private void iconWaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconWaMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconWa.setIcon(Gambar.getIcon("ic-dialog-infoapp-wa.png"));
        this.lblWa.setForeground(new Color(246,46,46));
    }//GEN-LAST:event_iconWaMouseEntered

    private void iconWaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconWaMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconWa.setIcon(Gambar.getIcon("ic-dialog-infoapp-wa-entered.png"));
        this.lblWa.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconWaMouseExited

    private void iconTeleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTeleMouseClicked
        try {
            this.inet.openLink("");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_iconTeleMouseClicked

    private void iconTeleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTeleMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconTele.setIcon(Gambar.getIcon("ic-dialog-infoapp-tele.png"));
        this.lblTele.setForeground(new Color(246,46,46));
    }//GEN-LAST:event_iconTeleMouseEntered

    private void iconTeleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTeleMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconTele.setIcon(Gambar.getIcon("ic-dialog-infoapp-tele-entered.png"));
        this.lblTele.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconTeleMouseExited

    private void iconIgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconIgMouseClicked
        try {
            this.inet.openLink("https://www.instagram.com/");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_iconIgMouseClicked

    private void iconIgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconIgMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconIg.setIcon(Gambar.getIcon("ic-dialog-infoapp-ig.png"));
        this.lblIg.setForeground(new Color(246,46,46));
    }//GEN-LAST:event_iconIgMouseEntered

    private void iconIgMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconIgMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconIg.setIcon(Gambar.getIcon("ic-dialog-infoapp-ig-entered.png"));
        this.lblIg.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconIgMouseExited

    private void iconTwitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTwitMouseClicked
        try {
            this.inet.openLink("https://twitter.com/haqiachd");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_iconTwitMouseClicked

    private void iconTwitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTwitMouseEntered
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.iconTwit.setIcon(Gambar.getIcon("ic-dialog-infoapp-tt.png"));
        this.lblTwit.setForeground(new Color(246,46,46));
    }//GEN-LAST:event_iconTwitMouseEntered

    private void iconTwitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconTwitMouseExited
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.iconTwit.setIcon(Gambar.getIcon("ic-dialog-infoapp-tt-entered.png"));
        this.lblTwit.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_iconTwitMouseExited

    private void lblCol1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol1MouseClicked
        try {
            this.inet.openLink("https://wa.me/6285655864624");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_lblCol1MouseClicked

    private void lblCol1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol1MouseEntered
        this.lblCol1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblCol1.setText("<html><p style=\"text-decoration:underline; color:rgb(15,98,230);\">:&nbsp;Achmad Baihaqi</p></html>");
    }//GEN-LAST:event_lblCol1MouseEntered

    private void lblCol1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol1MouseExited
        this.lblCol1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblCol1.setText("<html><p style=\"text-decoration:none; color:rgb(0,0,0);\">:&nbsp;Achmad Baihaqi</p></html>");
    }//GEN-LAST:event_lblCol1MouseExited

    private void lblCol2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol2MouseClicked
        try {
            this.inet.openLink("https://wa.me/6285784626830");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_lblCol2MouseClicked

    private void lblCol2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol2MouseEntered
        this.lblCol2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblCol2.setText("<html><p style=\"text-decoration:underline; color:rgb(15,98,230);\">&nbsp;&nbsp;Mohammad Ilham Islamy</p></html>");
    }//GEN-LAST:event_lblCol2MouseEntered

    private void lblCol2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol2MouseExited
        this.lblCol2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblCol2.setText("<html><p style=\"text-decoration:none; color:rgb(0,0,0);\">&nbsp;&nbsp;Mohammad Ilham Islamy</p></html>");
    }//GEN-LAST:event_lblCol2MouseExited

    private void lblCol3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol3MouseClicked
        try {
            this.inet.openLink("https://wa.me/6285806531609");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_lblCol3MouseClicked

    private void lblCol3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol3MouseEntered
        this.lblCol3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblCol3.setText("<html><p style=\"text-decoration:underline; color:rgb(15,98,230);\">&nbsp;&nbsp;Septian Yoga Pamungkas</p></html>");
    }//GEN-LAST:event_lblCol3MouseEntered

    private void lblCol3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol3MouseExited
        this.lblCol3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblCol3.setText("<html><p style=\"text-decoration:none; color:rgb(0,0,0);\">&nbsp;&nbsp;Septian Yoga Pamungkas</p></html>");
    }//GEN-LAST:event_lblCol3MouseExited

    private void lblCol4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol4MouseClicked
        try {
            this.inet.openLink("https://wa.me/6289637652216");
        } catch (IOException | URISyntaxException ex) {
            Message.showException(this, ex);
        }
    }//GEN-LAST:event_lblCol4MouseClicked

    private void lblCol4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol4MouseEntered
        this.lblCol4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblCol4.setText("<html><p style=\"text-decoration:underline; color:rgb(15,98,230);\">&nbsp;&nbsp;Widyasari Raisya Salsabilla</p></html>");
    }//GEN-LAST:event_lblCol4MouseEntered

    private void lblCol4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCol4MouseExited
        this.lblCol4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblCol4.setText("<html><p style=\"text-decoration:none; color:rgb(0,0,0);\">&nbsp;&nbsp;Widyasari Raisya Salsabilla</p></html>");
    }//GEN-LAST:event_lblCol4MouseExited

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InfoApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfoApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfoApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfoApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InfoApp dialog = new InfoApp(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel iconGmail;
    private javax.swing.JLabel iconIg;
    private javax.swing.JLabel iconTele;
    private javax.swing.JLabel iconTwit;
    private javax.swing.JLabel iconWa;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCol1;
    private javax.swing.JLabel lblCol2;
    private javax.swing.JLabel lblCol3;
    private javax.swing.JLabel lblCol4;
    private javax.swing.JLabel lblColabolator;
    private javax.swing.JLabel lblDev;
    private javax.swing.JLabel lblDialogName;
    private javax.swing.JLabel lblGmail;
    private javax.swing.JLabel lblIg;
    private javax.swing.JLabel lblKontakDev;
    private javax.swing.JLabel lblNamaApp;
    private javax.swing.JLabel lblTele;
    private javax.swing.JLabel lblTwit;
    private javax.swing.JLabel lblVersi;
    private javax.swing.JLabel lblWa;
    private javax.swing.JSeparator lineTop;
    private javax.swing.JPanel pnlKontakDev;
    private com.ui.RoundedPanel pnlMain;
    private javax.swing.JLabel valDev;
    private javax.swing.JLabel valNamaApp;
    private javax.swing.JLabel valVersi;
    // End of variables declaration//GEN-END:variables
}
