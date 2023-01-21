package com.window.dialog;

import com.manage.ChartManager;
import com.manage.Message;
import com.manage.Text;
import com.manage.Waktu;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.SwingConstants;

/**
 *
 * @author Achmad Baihaqi
 */
public class MenuBarChart extends javax.swing.JDialog {

    private final ChartManager chart = new ChartManager();
    
    private final Text text = new Text();
    
    private final Waktu waktu = new Waktu();
    
    private final String idMenu, namaMenu;
    
    private int bulan, tahun;
    
    private boolean statusTotal = true;
    
    /**
     * Creates new form MenuLneChart
     * @param parent
     * @param modal
     * @param idMenu
     */
    public MenuBarChart(java.awt.Frame parent, boolean modal, String idMenu) {
        super(parent, modal);
        initComponents();
        this.setSize(this.getWidth(), 389);
        this.setLocationRelativeTo(null);
        this.btnShow.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        this.btnShow.requestFocus();
        
        // mendapatkan data menu
        this.idMenu = idMenu;
        this.namaMenu = this.getNamaMenu();
        
        // mendapatkan data bulan dan tahun
        this.bulan = this.waktu.getBulan();
        this.tahun = this.waktu.getTahun();
        
        this.showChartMenu();
    }
    
    private String getNamaMenu(){
        try{
            this.chart.res = this.chart.stat.executeQuery("SELECT nama_menu FROM menu WHERE id_menu = '" + this.idMenu + "'");
            
            if(this.chart.res.next()){
                return this.chart.res.getString("nama_menu");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }
    
    private boolean isExistPenjualan(){
        try{
            // membuat query
            String sql = String.format(
                    "SELECT COUNT(d.id_menu) AS total "
                  + "FROM detail_tr_jual AS d "
                  + "JOIN transaksi_jual AS t "
                  + "ON t.id_tr_jual = d.id_tr_jual " 
                  + "WHERE d.id_menu = '%s' AND MONTH(t.tanggal) = '%d' AND YEAR(t.tanggal) = '%d'", 
                    this.idMenu, this.bulan, this.tahun
            );
            System.out.println(sql);
            
            // eksekusi query
            this.chart.res = this.chart.stat.executeQuery(sql);
            
            if(this.chart.res.next()){
                // jika penjualan lebih dari 0 maka dinyatakan valid
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
            String sql = String.format(
                    "SELECT SUM(d.jumlah) AS penjualan, SUM(d.total_harga) AS pendapatan "
                  + "FROM detail_tr_jual AS d "
                  + "JOIN transaksi_jual AS t "
                  + "ON t.id_tr_jual = d.id_tr_jual " 
                  + "WHERE d.id_menu = '%s' AND MONTH(t.tanggal) = '%d' AND YEAR(t.tanggal) = '%d'", 
                    this.idMenu, this.bulan, this.tahun
            );
            System.out.println(sql);
            
            // eksekusi query
            this.chart.res = this.chart.stat.executeQuery(sql);
            
            if(this.chart.res.next()){
                // jika penjualan lebih dari 0 maka dinyatakan valid
                int penjualan =  this.chart.res.getInt("penjualan"),
                    pendapatan = this.chart.res.getInt("pendapatan");
                
                // menampilkan data total penjualan / pendapatan
                if(this.statusTotal){
                    this.lblTotal.setText(String.format("Total Penjualan : %d ", penjualan));
                }else{
                    this.lblTotal.setText(String.format("Pendapatan : %s ", this.text.toMoneyCase(pendapatan)));
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    private void showChartMenu(){
        // cek apakah ada penjualan
        if(this.isExistPenjualan()){
            // menampilkan chart jika ada penjualan
            if(this.statusTotal){
                this.chart.showBarChart(this.pnlChart, ChartManager.MENU, ChartManager.PENJUALAN, idMenu,  "Penjualan Menu " + namaMenu, bulan, tahun);
            }else{
                this.chart.showBarChart(this.pnlChart, ChartManager.MENU, ChartManager.PENDAPATAN, idMenu, "Penjualan Menu " + namaMenu, bulan, tahun);
            }
        }else{
            // menampilkan pesan jika tidak ada penjualan
            String pesan = String.format("Tidak ada penjualan %s pada bulan %s %d", namaMenu, waktu.getNamaBulan(bulan), tahun);
            this.chart.setEmptyChart(pesan, pnlChart, lblBulan);
            
            // set style label pesan
            this.lblBulan.setFont(new Font("Verdana", 1, 16));
            this.lblBulan.setHorizontalAlignment(SwingConstants.CENTER);
            this.lblBulan.setForeground(new Color(255,0,0));
        }
        
        // menampilkan total penjualan dan pendapatan
        this.showTotal();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();
        lblBulan = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblPilihBulan = new javax.swing.JLabel();
        inpBulan = new com.toedter.calendar.JMonthChooser();
        inpTahun = new com.toedter.calendar.JYearChooser();
        btnShow = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Penjualan Menu Bulan Ini");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(248, 249, 250));

        pnlChart.setBackground(new java.awt.Color(248, 249, 250));
        pnlChart.setLayout(new java.awt.CardLayout());

        lblBulan.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblBulan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBulan.setText("Bar Chart Penjualan Menu : )");
        pnlChart.add(lblBulan, "card2");

        jSeparator1.setBackground(new java.awt.Color(0, 51, 204));
        jSeparator1.setForeground(new java.awt.Color(0, 51, 204));

        lblPilihBulan.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lblPilihBulan.setText("Pilih Bulan");

        inpBulan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        inpTahun.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

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
        lblTotal.setText("Total Penjualan : 32 ");
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
            .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(lblPilihBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inpBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inpTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnShow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inpBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inpTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPilihBulan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // mendapatkan data bulan dan tahun
        this.bulan = this.inpBulan.getMonth()+1;
        this.tahun = this.inpTahun.getYear();
        
        // menampilkan chart
        this.showChartMenu();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnShowActionPerformed

    private void lblTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalMouseClicked
        this.lblTotal.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.statusTotal = !this.statusTotal;
        this.showChartMenu();
        this.lblTotal.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
            java.util.logging.Logger.getLogger(MenuBarChart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuBarChart dialog = new MenuBarChart(new javax.swing.JFrame(), true, "MN001");
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBulan;
    private javax.swing.JLabel lblPilihBulan;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}
