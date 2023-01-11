package com.window.dialog;

import com.manage.Bahan;
import com.manage.ChartManager;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.sql.SQLException;
import javax.swing.SwingConstants;

/**
 *
 * @author Achmad Baihaqi
 */
public class BahanLineChart extends javax.swing.JDialog {

    private final ChartManager chart = new ChartManager();
    
    private final Bahan ba = new Bahan();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private final String idBahan, namaBahan, title, satuan;
    
    private int bulan, tahun;
    
    private boolean statusTotal = true;
    
    /**
     * Creates new form BahanLneChart
     * @param parent
     * @param modal
     * @param idMenu
     */
    public BahanLineChart(java.awt.Frame parent, boolean modal, String idMenu) {
        super(parent, modal);
        initComponents();
        this.setSize(this.getWidth(), 375);
        this.setLocationRelativeTo(null);
        this.btnShow.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnShow.requestFocus();
        
        // mendapatkan data bahan
        System.out.println("ID MENU : " + idMenu);
        this.idBahan = idMenu;
        this.namaBahan = this.getNamaBahan();
        this.satuan = this.getSatuanBahan();
        this.title = "Pembelian Bahan " + namaBahan + " / " + satuan;
        
        // mendapatkan bulan dan tahun
        this.bulan = this.waktu.getBulan();
        this.tahun = this.waktu.getTahun();
        
        this.showChartBahan();
    }
    
    private String getNamaBahan(){
        try{
            this.chart.res = this.chart.stat.executeQuery("SELECT nama_bahan FROM bahan WHERE id_bahan = '" + this.idBahan + "'");
            
            if(this.chart.res.next()){
                return this.chart.res.getString("nama_bahan");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }

    private boolean isExistPembelian(){
        try{
            // membuat query
            String sql = String.format("SELECT COUNT(d.id_bahan) AS total "
                  + "FROM detail_tr_beli AS d "
                  + "JOIN transaksi_beli AS t "
                  + "ON t.id_tr_beli = d.id_tr_beli " 
                  + "WHERE d.id_bahan = '%s' AND MONTH(t.tanggal) = '%d' AND YEAR(t.tanggal) = '%d'", 
                    this.idBahan, this.bulan, this.tahun
            );
            System.out.println(sql);
            
            // eksekusi query
            this.chart.res = this.chart.stat.executeQuery(sql);
            
            if(this.chart.res.next()){
                // jika pembelian lebih dari 0 maka dinyatakan valid
                return this.chart.res.getInt("total") > 0;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    private void showTotal(){
        try{
            // membuat query
            String sql = String.format("SELECT SUM(d.jumlah) AS pembelian, SUM(d.total_harga) AS pengeluaran "
                  + "FROM detail_tr_beli AS d "
                  + "JOIN transaksi_beli AS t "
                  + "ON t.id_tr_beli = d.id_tr_beli " 
                  + "WHERE d.id_bahan = '%s' AND MONTH(t.tanggal) = '%d' AND YEAR(t.tanggal) = '%d'", 
                    this.idBahan, this.bulan, this.tahun
            );
            System.out.println(sql);
            
            // eksekusi query
            this.chart.res = this.chart.stat.executeQuery(sql);
            
            if(this.chart.res.next()){
                // jika pembelian lebih dari 0 maka dinyatakan valid
                float pembelian =  this.chart.res.getFloat("pembelian");
                int pengeluaran = this.chart.res.getInt("pengeluaran");
                
                // menampilkan data total pembelian / pengeluaran
                if(this.statusTotal){
                    this.lblTotal.setText(String.format("Total Pembelian : %.1f %s", pembelian, satuan));
                }else{
                    this.lblTotal.setText(String.format("Pengeluran : %s ", this.text.toMoneyCase(pengeluaran)));
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private void showChartBahan(){
        // cek apakah ada pembelian
        if(this.isExistPembelian()){
            // menampilkan chart jika ada pembelian
            if(this.statusTotal){
                this.chart.showLineChart(this.pnlChart, ChartManager.BAHAN, ChartManager.PEMBELIAN, idBahan,  title, bulan, tahun);
            }else{
                this.chart.showLineChart(this.pnlChart, ChartManager.BAHAN, ChartManager.PENGELUARAN, idBahan, title, bulan, tahun);
            }
        }else{
            // menampilkan pesan jika tidak ada pembelian
            String pesan = String.format("Tidak ada pembelian %s pada bulan %s %d", namaBahan, waktu.getNamaBulan(bulan), tahun);
            this.chart.setEmptyChart(pesan, pnlChart, lblChart);
            
            // set style label pesan
            this.lblChart.setFont(new Font("Verdana", 1, 16));
            this.lblChart.setHorizontalAlignment(SwingConstants.CENTER);
            this.lblChart.setForeground(new Color(255,0,0));
        }
        
        // menampilkan total pembelian dan pegeluaran
        this.showTotal();
    }
    
    private String getSatuanBahan(){
        try{
            this.chart.res = this.chart.stat.executeQuery("SELECT satuan FROM bahan WHERE id_bahan = '" + this.idBahan + "'");
            
            if(this.chart.res.next()){
                String satuan = this.chart.res.getString("satuan");
                return this.ba.getNamaSatuan(satuan);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();
        lblChart = new javax.swing.JLabel();
        line = new javax.swing.JSeparator();
        inpBulan = new com.toedter.calendar.JMonthChooser();
        inpTahun = new com.toedter.calendar.JYearChooser();
        lblBulan = new javax.swing.JLabel();
        btnShow = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pembelian Bahan Bulan Ini");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));

        pnlChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlChart.setLayout(new java.awt.CardLayout());

        lblChart.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChart.setText("Line Chart Penjualan Bahan :)");
        pnlChart.add(lblChart, "card2");

        line.setBackground(new java.awt.Color(0, 51, 204));
        line.setForeground(new java.awt.Color(0, 51, 204));

        inpBulan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        inpBulan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                inpBulanAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        inpBulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inpBulanMouseClicked(evt);
            }
        });

        inpTahun.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblBulan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblBulan.setText("Pilih Bulan");

        btnShow.setBackground(new java.awt.Color(255, 102, 0));
        btnShow.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnShow.setForeground(new java.awt.Color(255, 255, 255));
        btnShow.setText("Show");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("Total Penjualan : 23 ");
        lblTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTotalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblTotalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblTotalMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(line)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inpBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inpTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnShow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnShow, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.chart.closeConnection();
    }//GEN-LAST:event_formWindowClosing

    private void inpBulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inpBulanMouseClicked

    }//GEN-LAST:event_inpBulanMouseClicked

    private void inpBulanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_inpBulanAncestorAdded

    }//GEN-LAST:event_inpBulanAncestorAdded

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // mendapatkan data bulan dan tahun
        this.bulan = this.inpBulan.getMonth()+1;
        this.tahun = this.inpTahun.getYear();
        
        // menampilkan chart bahan
        this.showChartBahan();
    }//GEN-LAST:event_btnShowActionPerformed

    private void lblTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalMouseClicked
        this.statusTotal = !this.statusTotal;
        this.showChartBahan();
    }//GEN-LAST:event_lblTotalMouseClicked

    private void lblTotalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalMouseEntered
        this.lblTotal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.lblTotal.setForeground(new Color(0,0,255));
    }//GEN-LAST:event_lblTotalMouseEntered

    private void lblTotalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalMouseExited
        this.lblTotal.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblTotal.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_lblTotalMouseExited

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
            java.util.logging.Logger.getLogger(BahanLineChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BahanLineChart dialog = new BahanLineChart(new javax.swing.JFrame(), true, "MN001");
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
    private javax.swing.JButton btnShow;
    private com.toedter.calendar.JMonthChooser inpBulan;
    private com.toedter.calendar.JYearChooser inpTahun;
    private javax.swing.JLabel lblBulan;
    private javax.swing.JLabel lblChart;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSeparator line;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
