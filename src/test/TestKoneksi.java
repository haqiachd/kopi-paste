/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.koneksi.Dbase;
import java.sql.SQLException;

/**
 *
 * @author Achmad Baihaqi
 */
public class TestKoneksi {
    
    private final Dbase db = new Dbase();
    
    public void showMenu(){
        try{
            db.res = db.stat.executeQuery("SELECT * FROM menu");
            while(db.res.next()){
                System.out.println(db.res.getString("id_menu") + " | " + db.res.getString("nama_menu"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public void showBahan(){
        try{
            db.res = db.stat.executeQuery("SELECT * FROM bahan");
            while(db.res.next()){
                System.out.println(db.res.getString("id_bahan") + " | " + db.res.getString("nama_bahan"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void closeConn(){
        db.closeConnection();
    }
    
    public static void main(String[] args) {
        
        TestKoneksi t = new TestKoneksi();
        t.showMenu();
        t.showBahan();
        t.closeConn();
        
    }
    
}
