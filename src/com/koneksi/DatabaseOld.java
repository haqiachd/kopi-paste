package com.koneksi;

import com.manage.Message;
import com.media.Audio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Class ini digunakan untuk menghubungkan dan memutuskan koneksi ke <strong>Database</strong>, membackup <strong>Database</strong> dan menangani masalah yang terjadi pada <strong>Database.</strong>
 * Kondisi seperti <strong>CRUD</strong> tidak tersedia di class ini. Kondisi <strong>CRUD</strong> tersedia di class <code>Account</code> dan <code>CovidCases</code>.
 * <BR><BR>
 * <strong>Database</strong> pada aplikasi ini sebagian besar menggunakan <strong>MySQL Database</strong> dan sisanya menggunakan <strong>Database Text</strong>.
 * 
 * 
 * @author Achmad Baihaqi
 * @since 2020-11-14
 */
public class DatabasefgvOld {
    
    public DatabaseOld(){
        this.startConnections();
    }
    
    /**
     * Object ini digunakan untuk membangun koneksi dengan <B>Database</B>
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
     * Digunakan untuk menyimpan query sql
     */
    private String sql;
    /**
     * Attribute yang digunakan untuk menhubungkan Aplikasi ke <B>Database MySQL</B>
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver",
                                DB_NAME = "kopi_paste",
                                URL = "jdbc:mysql://localhost/" + DB_NAME,
                                USER = "root",
                                PASS = "";
    
    public final void startConnection(){
        try{
            // meregristrasi driver
            Class.forName(DRIVER); 
            // membuat koneksi
            conn = DriverManager.getConnection(URL, USER, PASS);
            // membuat object statement
            stat = conn.createStatement(); 
            
            System.out.printf("Berhasil terhubung ke Database '%s'.", DB_NAME);
        }catch(ClassNotFoundException | SQLException ex){
            // Menanggani exception yang terjadi dengan cara mendapatkan pesan error dari exception tersebut.
            if(ex.getMessage().contains("com.mysql.jdbc.Driver")){
                Message.showException(null, "MySQL Connector tidak dapat ditemukan", ex);
            }else if(ex.getMessage().contains("Communications link failure")){
                Message.showException(null, "Sepertinya MySQL Anda belum diaktifkan!! \nSilahkan aktifkan MySQL Anda dan buka kembali Aplikasi!!", ex);
            }else if(ex.getMessage().contains("Access denied for user")){
                Message.showException(null, "Maaf untuk membuka aplikasi ini \nUsername dan password dari MySQL anda harus diatur ke default!\nMohon maaf atas ketidaknyamanan ini!", ex);
            }else if(ex.getMessage().contains("Unknown database")){
                Message.showException(null, "Tidak dapat menemukan database '"+DB_NAME+"'\nSilahkan melakukan import Database secara manual dan buka kembali Aplikasi!", ex);
            }else{
                Message.showException(null, "Terjadi Kesalahan!\nError message : " + ex.getMessage(), ex);
            }
            System.exit(1);
        }
    }
    
    public void closeConnections(){
        try{
            // Mengecek apakah conn kosong atau tidak, jika tidak maka akan diclose
            if(conn != null){
                conn.close();
            }
            // Mengecek apakah stat kosong atau tidak, jika tidak maka akan diclose
            if(stat != null){
                stat.close();
            }
            // Mengecek apakah res koson atau tidak, jika tidak maka akan diclose
            if(res != null){
                res.close();
            }
            
        System.out.printf("Berhasil memutus koneksi dari Database '%s'.", DB_NAME);
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public int getJumlahData(String tabel, String kondisi){
        try{
            String query = "SELECT COUNT(*) AS total FROM " + tabel + " " + kondisi;
            res = stat.executeQuery(query);
            if(res.next()){
                return res.getInt("total");
            }
        }catch(SQLException ex){
            Message.showException(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), ex);
        }
        return -1;
    }
    
    public int getJumlahData(String tabel){
        return this.getJumlahData(tabel, "");
    }
    
    public int sumData(String tabel, String field, String kondisi){
        try{
            String query = "SELECT SUM("+field+") AS total FROM " + tabel + " " + kondisi;
            System.out.println(query);
            res = stat.executeQuery(query);
            if(res.next()){
                System.out.println("query di eksekusi");
                return res.getInt("total");
            }
        }catch(SQLException ex){
            Message.showException(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), ex);
        }catch(NullPointerException n){
            n.printStackTrace();
            return 0;
        }
        return -1;
    }
    
    public boolean isExistData(String tabel, String field, String data){
        try{
            String query = "SELECT * FROM " + tabel + " WHERE " + field + " = '" + data + "'";
            res = stat.executeQuery(query);
            return res.next();
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public boolean addData(String query){
        try{
            int result = stat.executeUpdate(query);
            return result > 0;
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public String getData(String table, String field, String kondisi){
        try{
            sql = "SELECT "+field+" FROM "+table + " " + kondisi;
            res = stat.executeQuery(sql);
            if(res.next()){
                return res.getString(field);
            }
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        return "null";
    }
    
    public String getData(String table, String field){
        return getData(table, field, "");
    }
    
    /**
     * digunakan untuk mendapatkan fields yang berasal dari param field pada method getData
     * 
     * @param fields
     * @return 
     */
    private String getMultipleFields(String fields[]){
        String field = "";
        for (String buff : fields) {
            field += buff + ", ";
        }
        // membuang tanda koma diakhir String
        return field.substring(0, field.length()-2);
    }
    
    public Object[][] getData(final String tabel, final String[] fields, final String kondisi){
        try{
            Object[][] obj;
            int rows = 0;
            sql = "SELECT " + getMultipleFields(fields) + " FROM " + tabel + " " + kondisi;
            // mendefinisikan object berdasarkan total rows dan cols yang ada didalam tabel
            obj = new Object[getJumlahData(tabel, kondisi)][fields.length];
            // mengeksekusi query
            res = stat.executeQuery(sql);
            // mendapatkan semua data yang ada didalam tabel
            while(res.next()){
                // menyimpan data dari tabel ke object
                for (int i = 0; i < fields.length; i++) {
                    obj[rows][i] = res.getString(i+1);
                }
                rows++; // rows akan bertambah 1 setiap selesai membaca 1 row pada tabel
            }
            return obj;
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengambil data dari database\n" + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }
    
    public Object[][] getData(final String tabel, final String[] fields){
        return this.getData(tabel, fields, "");
    }
    
    public boolean setData(String tabel, String field, String oldValue, String newValue){
        return this.setData(tabel, field, field, oldValue, newValue);
    }
    
    public boolean setData(String tabel, String field, String spesifikasi, String oldValue, String newValue){
        try{
            sql = "UPDATE "+tabel+" SET " + field + " = '" + newValue + "' WHERE " + spesifikasi + " = '" + oldValue + "'";
            int result = stat.executeUpdate(sql);
            return result > 0;
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public boolean deleteData(String tabel, String field, String value){
        try{
            sql = "DELETE FROM " + tabel + " WHERE " + field + " = '" + value + "'";
            int result = stat.executeUpdate(sql);
            return result > 0;
        }catch(SQLException ex){
            Audio.play(Audio.SOUND_ERROR);
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan!\n\nError message : "+ex.getMessage(), "Peringatan!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }    
    
    public static void main(String[] args) {
        
        DatabaseOld db = new DatabaseOld();
        System.out.println("");
        System.out.println(db.sumData("transaksi_jual", "total_harga", ""));
        
    }
    
//    public static void main(String[] args) {
//        koneksi dbase = new koneksi();
//        dbase.startConnection();
//    }
}
