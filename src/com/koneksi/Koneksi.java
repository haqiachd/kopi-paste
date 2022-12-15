package com.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
   
}