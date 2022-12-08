package com.manage;


import com.koneksi.Koneksi;
import com.window.LoginWindow;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achmad Baihaqi
 */
public class User {
    
    private static String USERNAME = null, ID_KY, NAMA_USER = null, LEVEL = null;
    
   public static String MD5(String s) throws Exception {
      MessageDigest m = MessageDigest.getInstance("SHA256");
      m.update(s.getBytes(),0,s.length());     
      return new BigInteger(1,m.digest()).toString(512); 
   }
    private Object DigestUtils;
    
    public boolean login(String usernameOrID, String password){
        USERNAME = usernameOrID;
        try{
            // membuat query
            String query = 
                    String.format("SELECT karyawan.id_karyawan, karyawan.nama_karyawan, user.username, user.password, user.level "
                            + "FROM karyawan JOIN user ON karyawan.id_karyawan = user.id_karyawan "
                            + "WHERE user.username = '%s' OR karyawan.id_karyawan = '%s'", USERNAME, usernameOrID),
                    passEncrypt, passDecrypt;
            
            // eksekusi query
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(query);
            
            // cek username ada atau tidak
            if(res.next()){
//                passEncrypt = res.getString("user.password");
//                MessageDigest md = MessageDigest.getInstance("SHA-256");
//                byte[]hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
//                //bytes to hex
//                StringBuilder sb = new StringBuilder();
//                for (byte b : hashInBytes) {
//                        sb.append(String.format("%02x", b));
//                }
//                passDecrypt = sb.toString();
//                System.out.println("Encrypt : " + passEncrypt);
//                System.out.println("Decrypt : " + passDecrypt);
                // cek password cocok atau tidak
                if(password.equals(res.getString("password"))){
                    // mendapatkan nama dari user yang sedang login
                    User.ID_KY = res.getString("karyawan.id_karyawan");
                    User.NAMA_USER = res.getString("karyawan.nama_karyawan");
                    User.LEVEL = res.getString("user.level");
                    conn.close();
                    return true;
                }else{
                    Message.showWarning(this, "Password Anda tidak cocok!");
                }
            }else{
                Message.showWarning(this, String.format("'%s' Username tersebut tidak ditemukan!", usernameOrID));
            }
        }catch(Exception ex){
            Message.showException(this, ex);
        }
        return false;
    }
    
    public static boolean isExistUsername(String username){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT username FROM user WHERE username = '"+username+"'");
            
            if(r.next()){
                c.close(); s.close(); r.close();
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return false;
    }
 
    public static String getUsername(){
        return User.USERNAME;
    }
    
    public static String getIDKaryawan(){
        return User.ID_KY;
    }
    
    public static String getNamaUser(){
        return User.NAMA_USER;
    }
    
    public static String getLevel(){
        return User.LEVEL;
    }
    
    public static boolean isAdmin(){
        return User.getLevel().equalsIgnoreCase("ADMIN");
    }
    
    public void logout(){
        User.NAMA_USER = null;
        User.LEVEL = null;
        // membuka login window
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new LoginWindow().setVisible(true);
            }
        });   
    }
    
    

}
