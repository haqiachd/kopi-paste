package com.koneksi;

import com.manage.Message;
import com.manage.User;
import com.manage.Waktu;
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
     * Attribute yang digunakan untuk menhubungkan ke local server
     */
//    private static final String DRIVER = "com.mysql.jdbc.Driver",
//                                DB_NAME = "kopi_paste_business",
//                                URL = "jdbc:mysql://localhost/" + DB_NAME,
//                                USER = "root",
//                                PASS = "";
    /**
     * Attribute yang digunakan untuk menghubungkan ke server
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver",
                                DB_NAME = "sql6590038",
                                URL = "jdbc:mysql://sql6.freemysqlhosting.net/" + DB_NAME,
                                USER = "sql6590038",
                                PASS = "bjxsxD2B5d";    
    /**
     * Digunakan untuk menghitung jumlah koneksi yang aktif pada database
     */
    public static int CONN_COUNT = 0;
    
    /**
     * limit saat error, jumlah waktu koneksi ke server, dan no urut koneksi dalam server
     */
    public int err_limit = 0, minutes = 0, local_count;
    
    /**
     * Saat class pertama kali diinisialisasi maka akan otomatis membuat koneksi baru
     */
    public Database(){
        this.startConnection();
    }
    
    /**
     * Digunakan untuk membuat koneksi baru ke database server
     */
    public final void startConnection() {
        try {            
            // meregristrasi driver
            Class.forName(DRIVER);
            // membuat koneksi
            conn = DriverManager.getConnection(URL, USER, PASS);
            // membuat object statement
            stat = conn.createStatement();

            // jumlah koneksi bertambah saat ada koneksi baru
            CONN_COUNT++;
            System.out.printf("Berhasil terhubung ke Database '%s'.\nJumlah Koneksi : %d\n", "kopi paste", CONN_COUNT);
            
            // restart limit error, jumlah menit koneksi dan mendapatkan no urut koneksi
            this.err_limit = 0;
            this.minutes = 0;
            this.local_count = CONN_COUNT;
            
            // digunakan untuk merefresh koneksi ke server setiap 15 menit sekali
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        // akan merefersh koneksi selama masih ada koneksi ke sever
                        while (!conn.isClosed()) {
                            System.out.println(String.format("jumlah menit dalam koneksi (%d) : %d", local_count, minutes));
                            // jika menit koneksi >= 15 maka koneksi akan direfresh
                            if(minutes >= 15){
                                // refresh koneksi ke server
                                System.out.println("SEND TEST");
                                sendTestConn();
                                System.out.println("KONEKSI BERHASIL DI REFRESH");
                            }
                            // pertambahan menit setiap 60.000 milisecond / 1 menit
                            Thread.sleep(60_000);
                            minutes++;
                        }
                        System.out.println("Koneksi ke server dari koneksi no ("+local_count+") telah berakhir..");
                    }catch(InterruptedException | SQLException ex){
                        ex.printStackTrace();
                        System.out.println("ERROR IN SERVER THREAD");
                    }
                }
            }).start();
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            // Menanggani exception yang terjadi dengan cara mendapatkan pesan error dari exception tersebut.
            if (ex.getMessage().contains("com.mysql.jdbc.Driver")) {
                Message.showException(null, "MySQL Connector tidak dapat ditemukan", ex);
            } else if (ex.getMessage().contains("Communications link failure")) { // error yg terjadi karena tidak bisa terhubung ke server
                // membatasi limit koneksi saat terjadi error
                // jika kegagalan koneksi ke server lebih dari 20 kali maka aplikasi akan force close
                if ((err_limit++) >= 20) {
                    Message.showWarning(this, "Terjadi kegagalan pada koneksi ke server!\nCoba tutup dan buka kembali aplikasi!");
                    System.exit(0);
                }
                System.out.println("error limit ("+this.local_count+") : " + (20 - err_limit));

                // mendapatkan pesan error dari exception
                String dtlMsg = ex.getLocalizedMessage();
//                contoh output error
//                System.out.println("The last packet successfully received from the server was 4,239,368 milliseconds ago".indexOf("4"));
                
                // jika detail message mengandung kata received maka koneksi yang terputus dari server dan akan diaktifkan kembali
                if (dtlMsg.contains("successfully received from the server")) {
                    System.out.println(dtlMsg);
                    // mengaktifkan kembali koneksi yang terputus
                    System.out.println("Please wait!\nConnecting back to the database");
                    this.startConnection();
                    return;
                } else {
                    // mencoba menghubungkan kembali ke server jika tidak ada internet
                    Message.showException(null, "Tidak dapat terhubung dengan server!\nSilahkan cek koneksi Internet Anda!!", ex);
                    this.startConnection();
                    return;
                }
            } else if (ex.getMessage().contains("Unknown database")) {
                Message.showException(null, "Tidak dapat menemukan database 'kopi paste'\nSilahkan melakukan import Database secara manual dan buka kembali Aplikasi!", ex);
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
                System.out.println("EXECUTION OFF CONN CLOSE");
                conn.close();
            }
            // Mengecek apakah stat kosong atau tidak, jika tidak maka akan diclose
            if (stat != null) {
                System.out.println("EXECUTION OFF STAT CLOSE");
                stat.close();
            }
            // Mengecek apakah res kosong atau tidak, jika tidak maka akan diclose
            if (res != null) {
                System.out.println("EXECUTION OFF RESS CLOSE");
                res.close();
            }
            
            // reset limit error, menit koneksi dan local count
            this.err_limit = 0;
            this.minutes = 0;
            this.local_count = 0;
            
            // jumlah koneksi berkurang
            CONN_COUNT--;
            System.out.printf("Berhasil menutup koneksi dari Database (%d) '%s'.\nJumlah Koneksi : %d\n", this.local_count, "kopi paste", CONN_COUNT);
        } catch (SQLException ex) {
            Message.showException(null, "Terjadi Kesalahan!\nError message : " + ex.getMessage(), ex);
        }
    }
  
    /**
     * Digunakan untuk merefresh koneksi ke server selama 15 menit sekali agar koneksi ke server tidak otomatis terputus
     * 
     * @throws SQLException error pada sql 
     */
    private void sendTestConn() throws SQLException{
        // menyiapkan query
        this.pst = this.conn.prepareStatement("INSERT INTO test_conn VALUES (?, ?, ?, ?)");
        // mendapatkan data data test_conn (untuk pencatatan koneksi)
        this.pst.setString(1, null);
        this.pst.setString(2, User.getUsername());
        this.pst.setInt(3, this.local_count);
        this.pst.setString(4, new Waktu().getCurrentDateTime());
        // eksekusi query dan refresh menit koneksi
        this.pst.executeUpdate();
        this.minutes = 0;
    }
    
}
