/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.koneksi.Database;
import com.koneksi.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Achmad Baihaqi
 */
public class TesId {
    
    private final Database db = new Database();
    
    private String createID(){
        try{
            // menyiapkan query untuk mendapatkan id terakhir
            String query = "SELECT * FROM supplier ORDER BY id_supplier DESC LIMIT 0,1", lastID, nomor = "000";
            db.res = db.stat.executeQuery(query);
            
            // cek apakah query berhasil dieksekusi
            if(db.res.next()){
                // mendapatkan id terakhir
                lastID =  db.res.getString("id_supplier");
                if(lastID != null){
                    // mendapatkan nomor id
                    nomor = lastID.substring(2);
                }
            }
            // membuat id baru
            return String.format("SP%03d", Integer.parseInt(nomor)+1);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        
        TesId t = new TesId();
        System.out.println("\n\n");
        System.out.println(t.createID());
        
    }
    
}
