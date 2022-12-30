package com.koneksi;

import com.manage.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Digunakan untuk menghubungkan aplikasi ke Database MySQL
 *
 * @author Achmad Baihaqi
 * @since 2020-11-14
 */
public class Database {
    
    /**
     * Object ini digunakan untuk membangun koneksi dengan Databaseee
     */
    public Connection conn;
    /**
     * Digunakan untuk mengeksekusi query MySQL
     */
    public Statement stat;
    /**
     * Digunakan untuk mengambil output dari eksekusi query
     */
    public ResultSet res;
    /**
     * Digunakan untuk menyiapkan query sebelum dieksekusqi
     */
    public PreparedStatement pst;
    
    /**
     * Attribute yang digunakan untuk menhubungkan Aplikasi ke <B>Database MySQL</B>
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver",
                                DB_NAME = "kopi_paste",
                                URL = "jdbc:mysql://localhost/" + DB_NAME,
                                USER = "root",
                                PASS = "";
    
    /**
     * Digunakan untuk menghitung jumlah koneksi yang aktif pada database
     */
    public static int CONN_COUNT = 0;
    
    /**
     * Saat class pertama kali diinisialisasi maka akan otomatis membuat koneksi baru
     */
    public Database(){
        this.startConnection();
    }
    
    /**
     * Digunakan untuk membuat koneksi baru ke database
     */
    public final void startConnection() {
        try {
            // meregristrasi driver
            Class.forName(DRIVER);
            // membuat koneksi
            conn = DriverManager.getConnection(URL, USER, PASS);
            // membuat object statement
            stat = conn.createStatement();

            // jumlah koneksi bertambah
            CONN_COUNT++;
            System.out.printf("Berhasil terhubung ke Database '%s'.\nJumlah Koneksi : %d\n", DB_NAME, CONN_COUNT);
        } catch (ClassNotFoundException | SQLException ex) {
            // Menanggani exception yang terjadi dengan cara mendapatkan pesan error dari exception tersebut.
            if (ex.getMessage().contains("com.mysql.jdbc.Driver")) {
                Message.showException(null, "MySQL Connector tidak dapat ditemukan", ex);
            } else if (ex.getMessage().contains("Communications link failure")) {
                Message.showException(null, "Sepertinya MySQL Anda belum diaktifkan!! \nSilahkan aktifkan MySQL Anda dan buka kembali Aplikasi!!", ex);
            } else if (ex.getMessage().contains("Unknown database")) {
                Message.showException(null, "Tidak dapat menemukan database '" + DB_NAME + "'\nSilahkan melakukan import Database secara manual dan buka kembali Aplikasi!", ex);
            } else {
                Message.showException(null, "Terjadi Kesalahan!\nError message : " + ex.getMessage(), ex);
            }
            System.exit(1);
        }
    }

    /**
     * Digunakan untuk menutup koneksi pada Database
     * 
     */
    public final void closeConnection() {
        try {
            // Mengecek apakah conn kosong atau tidak, jika tidak maka akan diclose
            if (conn != null) {
                conn.close();
            }
            // Mengecek apakah stat kosong atau tidak, jika tidak maka akan diclose
            if (stat != null) {
                stat.close();
            }
            // Mengecek apakah res kosong atau tidak, jika tidak maka akan diclose
            if (res != null) {
                res.close();
            }
            
            // jumlah koneksi berkurang
            CONN_COUNT--;
            System.out.printf("Berhasil menutup koneksi dari Database '%s'.\nJumlah Koneksi : %d\n", DB_NAME, CONN_COUNT);
        } catch (SQLException ex) {
            Message.showException(null, "Terjadi Kesalahan!\nError message : " + ex.getMessage(), ex);
        }
    }
    
}
