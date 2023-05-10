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
    
    private static String USERNAME = null, ID_AKUN = null, RFID = null, NAMA_USER = null, LEVEL = null;
    
    public boolean login(String usernameOrID, String password){
        try{
            // membuat query
            String query = 
                    String.format("SELECT da.nama_lengkap, a.id_akun, a.username, a.rfid, a.password, a.level "
                            + "FROM akun AS a "
                            + "JOIN detail_akun AS da "
                            + "ON a.id_akun = da.id_akun "
                            + "WHERE a.username = '%s' OR a.id_akun = '%s'", usernameOrID, usernameOrID);
            
            System.out.println(query);
            // eksekusi query
            super.res = super.stat.executeQuery(query);
            
            // cek username ada atau tidak
            if(super.res.next()){
                // mengecek password 
                if(BCrypt.checkpw(password, super.res.getString("a.password"))){
                    // mendapatkan nama dari user yang sedang login
                    User.USERNAME = super.res.getString("a.username");
                    User.ID_AKUN = super.res.getString("a.id_akun");
                    User.RFID = super.res.getString("a.rfid");
                    User.NAMA_USER = super.res.getString("da.nama_lengkap");
                    User.LEVEL = super.res.getString("a.level");
                    return true;
                }else{
                    Message.showWarning(this, "Password Anda tidak cocok!");
                }
            }else{
                Message.showWarning(this, String.format("'%s' Username tersebut tidak ditemukan!", usernameOrID));
            }
        }catch(Exception ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    public boolean loginRFID(String rfid){
        try{
            // membuat query
            String query = 
                    String.format("SELECT da.nama_lengkap, a.id_akun, a.username, a.rfid, a.password, a.level "
                            + "FROM akun AS a "
                            + "JOIN detail_akun AS da "
                            + "ON a.id_akun = da.id_akun "
                            + "WHERE a.rfid = '%s' ", rfid);
            System.out.println(query);
            
            // eksekusi qeury
            super.res = super.stat.executeQuery(query);
            
            // jika rfid ditemukan
            if(super.res.next()){
                // mendapatkan nama dari user yang sedang login
                User.USERNAME = super.res.getString("a.username");
                User.ID_AKUN = super.res.getString("a.id_akun");
                User.RFID = rfid;
                User.NAMA_USER = super.res.getString("da.nama_lengkap");
                User.LEVEL = super.res.getString("a.level");
                return true;
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    public static boolean isLogin(){
        return User.USERNAME != null;
    }
    
    public boolean isExistUsername(String username){
        try{
            // mengeksekusi query
            super.res = super.stat.executeQuery("SELECT username FROM akun WHERE username = '"+username+"'");
            
            if(super.res.next()){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return false;
    }

    public boolean isExistRfid(String username){
        try{
            // mengeksekusi query
            super.res = super.stat.executeQuery("SELECT rfid FROM akun WHERE rfid = '"+username+"'");
            
            if(super.res.next()){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return false;
    }
    
    public boolean isExistIdAkun(String id){
        try{
            // megeksekusi query
            super.res = super.stat.executeQuery("SELECT id_akun FROM akun WHERE id_akun = '"+id+"'");
            
            if(super.res.next()){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return false;
    }
    
    public boolean isExisEmail(String id){
        try{
            // megeksekusi query
            super.res = super.stat.executeQuery("SELECT id_akun FROM akun WHERE id_akun = '"+id+"'");
            
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
    
    public static String getIdAkun(){
        return User.ID_AKUN;
    }
    
    public static String getRfid(){
        return User.RFID;
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
        User.ID_AKUN = null;
        User.RFID = null;
        // membuka login tipe window
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new ChooseLoginType().setVisible(true);
            }
        });   
    }
}
