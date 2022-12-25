/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import com.koneksi.Database;
import java.sql.SQLException;

/**
 *
 * @author Achmad Baihaqi
 */
public class TestConnWindow extends javax.swing.JFrame {

    private final Database db = new Database();
    
    private int jmlConn = 0;
    
    /**
     * Creates new form TestConnWindow
     */
    public TestConnWindow() {
        initComponents();
    }

    
    private void showKoneksi(){
        this.lblDibuka.setText("   Koneksi Dibuka :  " + Database.CONN_COUNT);
        this.lblDitutp.setText("   Koneksi Ditutup :  " + Database.CONN_COUNT);
        this.lblJml.setText("    Total Koneksi : " + Database.CONN_COUNT);
    }
    
    private void showMenu(){
        try{
            db.res = db.stat.executeQuery("SELECT * FROM menu");
            while(db.res.next()){
                System.out.println(db.res.getString("id_menu") + " | " + db.res.getString("nama_menu"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void testInsert(){
        try{
            db.pst = db.conn.prepareStatement("INSERT INTO test_table VALUES (?, ?, ?)");
            db.pst.setString(1, "Values " + Database.CONN_COUNT);
            db.pst.setString(2, "Values " + Database.CONN_COUNT);
            db.pst.setString(3, "Values " + Database.CONN_COUNT);
            System.out.println("IS INSERT : " + db.pst.executeUpdate());
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void testDelete(){
        try{
            int res = db.stat.executeUpdate("DELETE FROM test_table WHERE col1 = 'Values " + Database.CONN_COUNT +"'");
            System.out.println("IS DELETE : " + (res > 0));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void testUpdate(){
        try{
            int res = db.stat.executeUpdate("UPDATE test_table SET col2 = '', col3 = 'Val "+jmlConn+"' WHERE col1 = 'Values " + Database.CONN_COUNT +"'");
            System.out.println("IS UPDATE : " + (res > 0));
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnOpen = new javax.swing.JButton();
        lblDibuka = new javax.swing.JLabel();
        lblDitutp = new javax.swing.JLabel();
        lblJml = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnOpen.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        btnOpen.setText("Open Koneksi");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        lblDibuka.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDibuka.setText("   Koneksi Dibuka : ");

        lblDitutp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDitutp.setText("   Koneksi Ditutup : ");

        lblJml.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblJml.setText("   Total Koneksi : ");

        btnClose.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        btnClose.setText("Tutup Koneksi");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDibuka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDitutp, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
            .addComponent(lblJml, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(btnOpen)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblDibuka, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDitutp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJml, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        this.jmlConn++;
        this.showKoneksi();
        this.db.startConnection();
        this.showMenu();
        this.testInsert();
        this.testUpdate();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.showKoneksi();
        this.db.closeConnection();
        this.testDelete();
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(TestConnWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestConnWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestConnWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestConnWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestConnWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnOpen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDibuka;
    private javax.swing.JLabel lblDitutp;
    private javax.swing.JLabel lblJml;
    // End of variables declaration//GEN-END:variables
}
