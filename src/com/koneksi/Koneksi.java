package com.koneksi;

import com.manage.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class Koneksi {
    
    private static Connection mysqlconfig;
    
    public static Connection configDB() throws SQLException{
        try{
            String url = "jdbc:mysql://localhost:3306/kopi_paste";
            
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            System.out.println("Koneksi berhasil;");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return mysqlconfig;
    }
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM transaksi ORDER BY id_transaksi DESC LIMIT 0,1", lastID, nomor;
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(res.next()){
                // mendapatkan id terakhir
                lastID =  res.getString("id_transaksi");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(3);
                }else{
                    nomor = "000";
                }
                conn.close();
            
                // jika id barang belum exist maka id akan dibuat
                return String.format("KPR%03d", Integer.parseInt(nomor)+1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public void test(){
        
//        String Username = txt_username.getText();
//        String Password = txt_password.getText();
//        this.setVisible(false);
//        
//        try{
//            String sql = "SELECT * FROM karyawan WHERE username="+"'"+Username+"'"+" AND password='"+Password+"'";
//            Connection conn = (Connection) Koneksi.configDB();
//            Statement s = conn.createStatement();
//            ResultSet r = s.executeQuery(sql);
//            System.out.println("Koneksi Berhasil!!");
//            
//            if(r.next()){
//                JOptionPane.showMessageDialog(null, "Login Berhasil","Informasi",JOptionPane.INFORMATION_MESSAGE);
//                if(r.getString("level_user").equalsIgnoreCase("owner")){
//                    // buka window dashboard owner
//                    this.dispose();    
//                }else{
//                    // buka window dashboard user
//                    
//                    this.dispose();
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Maaf Username atau Password salah","Informasi",JOptionPane.INFORMATION_MESSAGE);
//                this.setVisible(true);
//            }
//        } catch(Exception e) {
//            JOptionPane.showMessageDialog(null, "Gagal, silahkan ulangi","Informasi",JOptionPane.INFORMATION_MESSAGE);
//        }
        

        
    }
    
    public static void main(String[] args) {
        
        Koneksi k = new Koneksi();
        System.out.println(k.createID());
        
    }
    
}

