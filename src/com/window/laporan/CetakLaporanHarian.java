package com.window.laporan;

import com.manage.Laporan;
import com.manage.Message;
import com.media.Gambar;
import java.awt.Cursor;
import java.sql.Connection;
import javax.swing.JTable;

/**
 *
 * @author Achmad Baihaqi
 */
public class CetakLaporanHarian extends javax.swing.JDialog {
    
    private final Connection conn;
    
    private final JTable tabel;
    
    private final String title, statue;
    
    public static final String STATUS_JUAL = "jual", STATUS_BELI = "beli";
    
    private final Laporan report = new Laporan();
    
    public CetakLaporanHarian(java.awt.Frame parent, boolean modal, JTable table, Connection conn, String status, String title) {
        super(parent, modal);
        initComponents();
        
        this.tabel = table;
        this.conn = conn;
        this.title = title;
        this.statue = status;
        
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTop = new javax.swing.JLabel();
        line = new javax.swing.JSeparator();
        pnlButton = new javax.swing.JPanel();
        btnPrint = new javax.swing.JLabel();
        btnExcel = new javax.swing.JLabel();
        btnPdf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cetak Laporan");
        setBackground(new java.awt.Color(255, 255, 255));

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));

        lblTop.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lblTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTop.setText("Cetak Laporan");

        line.setBackground(new java.awt.Color(0, 0, 0));
        line.setForeground(new java.awt.Color(0, 0, 0));

        pnlButton.setBackground(new java.awt.Color(255, 255, 255));

        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-cetaklaporan-print.png"))); // NOI18N
        btnPrint.setPreferredSize(new java.awt.Dimension(70, 70));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrintMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrintMouseExited(evt);
            }
        });
        pnlButton.add(btnPrint);

        btnExcel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-cetaklaporan-excel.png"))); // NOI18N
        btnExcel.setPreferredSize(new java.awt.Dimension(70, 70));
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExcelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcelMouseExited(evt);
            }
        });
        pnlButton.add(btnExcel);

        btnPdf.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image/icons/ic-cetaklaporan-pdf.png"))); // NOI18N
        btnPdf.setPreferredSize(new java.awt.Dimension(70, 70));
        btnPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPdfMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPdfMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPdfMouseExited(evt);
            }
        });
        pnlButton.add(btnPdf);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(line)
                .addContainerGap())
            .addComponent(pnlButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(lblTop, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.dispose();
        switch(this.statue){
            case STATUS_JUAL : this.report.cetakLaporanJualHarian(this.conn); break;
            case STATUS_BELI : this.report.cetakLaporanBeliHarian(this.conn); break;
            default : Message.showWarning(this, "Print saat ini belum tersedia untuk semua laporan!");
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnPrintMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseEntered
        this.btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnPrint.setIcon(Gambar.getIcon("ic-cetaklaporan-print.png"));
    }//GEN-LAST:event_btnPrintMouseEntered

    private void btnPrintMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseExited
        this.btnPrint.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.btnPrint.setIcon(Gambar.getIcon("ic-cetaklaporan-print-entered.png"));
    }//GEN-LAST:event_btnPrintMouseExited

    private void btnExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.report.cetakExcel(this.tabel);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dispose();
    }//GEN-LAST:event_btnExcelMouseClicked

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        this.btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnExcel.setIcon(Gambar.getIcon("ic-cetaklaporan-excel.png"));
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        this.btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnExcel.setIcon(Gambar.getIcon("ic-cetaklaporan-excel-entered.png"));
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPdfMouseClicked
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.report.cetakPdf(this.tabel, this.title);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.dispose();
    }//GEN-LAST:event_btnPdfMouseClicked

    private void btnPdfMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPdfMouseEntered
        this.btnPdf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnPdf.setIcon(Gambar.getIcon("ic-cetaklaporan-pdf.png"));
    }//GEN-LAST:event_btnPdfMouseEntered

    private void btnPdfMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPdfMouseExited
        this.btnPdf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.btnPdf.setIcon(Gambar.getIcon("ic-cetaklaporan-pdf-entered.png"));
    }//GEN-LAST:event_btnPdfMouseExited

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
            java.util.logging.Logger.getLogger(CetakLaporanHarian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CetakLaporanHarian dialog = new CetakLaporanHarian(new javax.swing.JFrame(), true, null, null, "", "");
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
    private javax.swing.JLabel btnExcel;
    private javax.swing.JLabel btnPdf;
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel lblTop;
    private javax.swing.JSeparator line;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
