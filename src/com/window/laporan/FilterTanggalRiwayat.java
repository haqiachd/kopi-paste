package com.window.laporan;

import com.manage.Message;
import java.awt.Cursor;
import java.text.SimpleDateFormat;

/**
 *
 * @author Achmad Baihaqi
 */
public class FilterTanggalRiwayat extends javax.swing.JDialog {

    private boolean isAntara = false, isPerhari = false;
    
    private String perhari = "", antara = "";
    
    /**
     * Creates new form FilterTanggalRiwayat
     * @param parent
     * @param modal
     */
    public FilterTanggalRiwayat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public boolean isPerhari(){
        return this.isPerhari;
    }
    
    public boolean isAntara(){
        return this.isAntara;
    }
    
    public String getPerhari(){
        return this.perhari;
    }
    
    public String getAntara(){
        return this.antara;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        line = new javax.swing.JSeparator();
        lblDataPerhari = new javax.swing.JLabel();
        inpDataPerhari = new com.toedter.calendar.JDateChooser();
        cariPerhari = new javax.swing.JLabel();
        inpAntara1 = new com.toedter.calendar.JDateChooser();
        inpSampai = new javax.swing.JLabel();
        inpAntara2 = new com.toedter.calendar.JDateChooser();
        lblAntara = new javax.swing.JLabel();
        cariAntara = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Filter Tanggal Riwayat");

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblTop.setForeground(new java.awt.Color(250, 22, 22));
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Filter Tanggal Riwayat Transaksi ");

        line.setBackground(new java.awt.Color(0, 0, 0));
        line.setForeground(new java.awt.Color(0, 0, 0));

        lblDataPerhari.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblDataPerhari.setForeground(new java.awt.Color(27, 109, 235));
        lblDataPerhari.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDataPerhari.setText("Data Perhari");

        inpDataPerhari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        cariPerhari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        cariPerhari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariPerhariMouseClicked(evt);
            }
        });

        inpAntara1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        inpSampai.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        inpSampai.setText("Sampai");

        inpAntara2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblAntara.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblAntara.setForeground(new java.awt.Color(27, 109, 235));
        lblAntara.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAntara.setText("Urutkan Berdaskarkan Tanggal");

        cariAntara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-window searchdata.png"))); // NOI18N
        cariAntara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariAntaraMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(line)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inpDataPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDataPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariPerhari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(inpAntara1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpSampai)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inpAntara2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAntara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cariAntara)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataPerhari, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAntara, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpSampai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cariPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpDataPerhari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpAntara1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpAntara2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cariAntara, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(0, 20, Short.MAX_VALUE))
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

    private void cariPerhariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariPerhariMouseClicked
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
 
        // mendapatkan input dari jdatechooser
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d");
        if(this.inpDataPerhari.getDate() == null){
            Message.showWarning(this, "Silahkan pilih tanggal terlebih dahulu!");
        }else{
            String tanggal = format.format(this.inpDataPerhari.getDate());

            // mendapatkan data tanggal
            this.perhari = "AND DATE(t.tanggal) = '"+tanggal+"'";
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            Message.showInformation(this, "Menampilkan data riwayat tanggal " + tanggal);
        }
        this.isPerhari = true;
        this.dispose();
    }//GEN-LAST:event_cariPerhariMouseClicked

    private void cariAntaraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariAntaraMouseClicked
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // mereset input tanggal perhari
        this.inpDataPerhari.setDate(null);
        
        // mendapatkan input dari jdatechooser
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-d"), 
                           format2 = format1;
        if(this.inpAntara1.getDate() == null || this.inpAntara2.getDate() == null){
            Message.showWarning(this, "Silahkan pilih tanggal terlebih dahulu!");
            return;
        }else{
              String tanggal1 = format1.format(this.inpAntara1.getDate()),
                     tanggal2 = format2.format(this.inpAntara2.getDate());

            // mendapatkan data
            this.antara = "AND DATE(t.tanggal) BETWEEN '"+tanggal1+"' AND '"+tanggal2+"'";
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));        
            Message.showInformation(this, "Menampilkan data riwayat tanggal " + tanggal1 + " sampai " + tanggal2);
        }
        this.isAntara = true;
        this.dispose();
    }//GEN-LAST:event_cariAntaraMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FilterTanggalRiwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FilterTanggalRiwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FilterTanggalRiwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FilterTanggalRiwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FilterTanggalRiwayat dialog = new FilterTanggalRiwayat(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel cariAntara;
    private javax.swing.JLabel cariPerhari;
    private com.toedter.calendar.JDateChooser inpAntara1;
    private com.toedter.calendar.JDateChooser inpAntara2;
    private com.toedter.calendar.JDateChooser inpDataPerhari;
    private javax.swing.JLabel inpSampai;
    private javax.swing.JLabel lblAntara;
    private javax.swing.JLabel lblDataPerhari;
    private javax.swing.JLabel lblTop;
    private javax.swing.JSeparator line;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
