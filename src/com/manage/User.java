package com.manage;

import com.koneksi.Database;
import com.window.ChooseLoginType;
import java.sql.SQLException;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Achmad Baihaqi
 */
public class User extends Database{
    
    private static String USERNAME = null, ID_KY, NAMA_USER = null, LEVEL = null;
    
    public boolean login(String usernameOrID, String password){
        try{
            // membuat query
            String query = 
                    String.format("SELECT karyawan.id_karyawan, karyawan.nama_karyawan, user.username, user.password, user.level "
                            + "FROM karyawan JOIN user ON karyawan.id_karyawan = user.id_karyawan "
                            + "WHERE user.username = '%s' OR karyawan.id_karyawan = '%s'", usernameOrID, usernameOrID);
            
            // eksekusi query
            super.res = super.stat.executeQuery(query);
            
            // cek username ada atau tidak
            if(super.res.next()){
                // mengecek password 
                if(BCrypt.checkpw(password, super.res.getString("password"))){
                    // mendapatkan nama dari user yang sedang login
                    User.USERNAME = super.res.getString("user.username");
                    User.ID_KY = super.res.getString("karyawan.id_karyawan");
                    User.NAMA_USER = super.res.getString("karyawan.nama_karyawan");
                    User.LEVEL = super.res.getString("user.level");
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
    
    public boolean loginRFID(String rfid){
        try{
            // membuat query
            String query = 
                    String.format("SELECT karyawan.id_karyawan, karyawan.nama_karyawan, user.username, user.password, user.level "
                            + "FROM karyawan JOIN user ON karyawan.id_karyawan = user.id_karyawan "
                            + "WHERE user.rfid = '%s' ", rfid);
            
            // eksekusi qeury
            super.res = super.stat.executeQuery(query);
            
            // jika rfid ditemukan
            if(super.res.next()){
                // mendapatkan nama dari user yang sedang login
                User.USERNAME = super.res.getString("user.username");
                User.ID_KY = super.res.getString("karyawan.id_karyawan");
                User.NAMA_USER = super.res.getString("karyawan.nama_karyawan");
                User.LEVEL = super.res.getString("user.level");
                return true;
            }
            
        }catch(Exception ex){
            Message.showException(this, ex);
        }
        return false;
    }
    
    public boolean isExistUsername(String username){
        try{
            // mengeksekusi query
            super.res = super.stat.executeQuery("SELECT username FROM user WHERE username = '"+username+"'");
            
            if(super.res.next()){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return false;
    }
    
    public boolean isIdKaryawan(String id){
        try{
            // megeksekusi query
            super.res = super.stat.executeQuery("SELECT id_karyawan FROM user WHERE id_karyawan = '"+id+"'");
            
            if(super.res.next()){
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
        return User.getLevel().equalsIgnoreCase("ADMIN") || User.getLevel().equalsIgnoreCase("DEVELOPER");
    }
    
    public static boolean isDeveloper(){
        return User.getLevel().equalsIgnoreCase("DEVELOPER");
    }
    
    public void logout(){
        User.NAMA_USER = null;
        User.LEVEL = null;
        User.USERNAME = null;
        User.ID_KY = null;
        // membuka login window
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new ChooseLoginType().setVisible(true);
            }
        });   
    }
}
