package test;

import com.koneksi.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class TesTempStokMenu {
    
    /**
     * Meyimpan data stok bahan dan stok menu sementara saat proses transaksi sedang berlangsung
     */
    private HashMap<String, Integer> tempStokBahan, tempStokMenu;
    
    /**
     * Digunakan untuk mendapatkan semua stok bahan dari tabel database dan outputnya disimpan dalam hashmap
     */
    private void getAllStokBahan(){
        try{
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_bahan, stok FROM bahan");
            // inisialisasi hashmap
            tempStokBahan = new HashMap<>();
            
            // membaca semua data yang ada didalam tabel bahan
            while(r.next()){
                // menyimpan id bahan dan stok kedalam hashmap
                tempStokBahan.put(r.getString("id_bahan"), r.getInt("stok"));
            }
            
            // menutup koneksi
            s.close();
            r.close();
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
    }
    
    /**
     * Digunakan untuk menampilkan semua data stok bahan
     */
    private void showStokBahan(){
        // konversi hashmap ke object
        Object obj[] = this.tempStokBahan.entrySet().toArray();
        // membaca semua data
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk mendapatkan data stok bahan sementara yang ada di hashmap
     * 
     * @param idBahan id bahan yang akan diambil stoknya
     * @return stok sementara dari bahan
     */
    private int getTempStokBahan(String idBahan){
        return this.tempStokBahan.get(idBahan);
    }
    
    /**
     * Digunakan untuk mengubah data stok bahan sementara yang ada di hashmap
     * 
     * @param idBahan id bahan yang akan ubah data stoknya
     * @param newStok stok baru dari bahan
     */
    private void setTempStokBahan(String idBahan, int newStok){
        this.tempStokBahan.replace(idBahan, newStok);
    }
    
    /**
     * Digunakan untuk mendapatkan bahan-bahan dari menu yang akan dipesan
     * 
     * @param idMenu id dari menu yang akan diambil bahan-bahanya
     * @return id bahan dan jumlah bahan dari menu
     */
    private HashMap<String, Integer> getBahanMenu(String idMenu){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_bahan, quantity FROM detail_menu WHERE id_menu = '"+idMenu+"'");
            // menyimpan data sementara dari bahan
            HashMap<String, Integer> data = new HashMap();
            
            // mendapatkan data bahan-bahan
            while(r.next()){
                data.put(r.getString("id_bahan"), r.getInt("quantity"));
            }
            
            c.close();
            s.close();
            r.close();
            
            return data;
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return null;
    }

    /**
     * Digunakan untuk menampilkan bahan-bahan dari menu
     * 
     * @param idMenu id menu yang akan ditampilkan bahan-bahannya
     */
    private void showBahanMenu(String idMenu){
        Object[] obj = this.getBahanMenu(idMenu).entrySet().toArray();
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk menghitung ketersediaan stok dari menu
     * 
     * @param idMenu id menu yang akan dicek
     * @return stok dari menu
     */
    private int hitungStokMenu(String idMenu){
        String idBahan;
        StringTokenizer token; // meyimpan token dari id bahan
        Object[] bahanMenu =  this.getBahanMenu(idMenu).entrySet().toArray(); // mendapatkan data bahan-bahan dari menu dalam bentuk token
        int cekStok[] = new int[bahanMenu.length]; // menyimpan stok sementara dari menu
        int stokBahan, quantity;

        for(int i = 0; i < bahanMenu.length; i++){
            // mendapatkan token dari data bahan menu (contoh token : BA001=10) 
            token = new StringTokenizer(bahanMenu[i].toString(), "=");
            // mendapatkan data id bahan dari token
            idBahan = token.nextToken();
            // mendapatkan jumlah bahan dari token
            quantity = Integer.parseInt(token.nextToken());
            // mendapatkan stok dari bahan
            stokBahan = this.getTempStokBahan(idBahan);
            
            // menghindari error pembagian degan 0
            if(quantity == 0){
                return 0;
            }
            
            // menghitung stok dengan cara membagi stok dari bahan dengan quantity bahan
            cekStok[i] = (int)stokBahan / quantity; 
            System.out.printf("\nId Bahan : %s \nStok Bahan : %d \nQuantity : %d \nStok : %d\n", idBahan, stokBahan, quantity, cekStok[i]);
        }
        
        // mendapatkan stok terendah dari bahan bahan menu
        Arrays.sort(cekStok);
        return cekStok[0];
    }
    
    /**
     * Digunakan untuk mendapatkan semua stok menu
     */
    private void getAllStokMenu(){
        try{
            // eksekusi query
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_menu FROM menu");
            String id;
            // inisialisasi hashmap
            tempStokMenu = new HashMap();
            
            // membaca semua data menu
            while(r.next()){
                id = r.getString("id_menu");
                // menghitung stok dari menu
                tempStokMenu.put(id, this.hitungStokMenu(id));
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
    }
    
    /**
     * Menampilkan semua stok dari menu
     */
    private void showStokMenu(){
        // konversi hashmap ke array objct
        Object[] obj = this.tempStokMenu.entrySet().toArray();
        // menampilkan semua data
        for(Object o : obj){
            System.out.println(o);
        }
    }
    
    /**
     * Digunakan untuk mengupdate stok dari menu saat terjadi proses transaksi penjualan
     * 
     * @param idMenu id menu yang dipesan
     * @param jumlah jumlah menu yang dipesan
     * @param status <ul> <li>add : menambahkan menu </li>
     *                    <li>min : mengurangi menu </li>
     *               </ul>
     */
    /**
     * Digunakan untuk mengupdate stok dari menu saat terjadi proses transaksi penjualan
     * 
     * @param idMenu id menu yang dipesan
     * @param jumlah jumlah menu yang dipesan
     * @param status <ul> <li>add : menambahkan menu </li>
     *                    <li>min : mengurangi menu </li>
     *               </ul>
     */
    private void updateStokMenu(String idMenu, int jumlah, String status){
        int quantity, stokBahan = 0;
        String idBahan;
        StringTokenizer token; // menyimpan token dari data-data bahan
        Object[] dataBahan = this.getBahanMenu(idMenu).entrySet().toArray(); // mendapatkan data bahan-bahan dari menu
        
        for (Object bahan : dataBahan) {
            // mendapatkan token dari data bahan menu (contoh token : BA001=10) 
            token = new StringTokenizer(bahan.toString(), "=");
            // mendapatkan data id bahan dari token
            idBahan = token.nextToken();
            // mendapatkan jumlah bahan dari token
            quantity = Integer.parseInt(token.nextToken());
            
            // cek status tambah atau mengurangi menu
            switch(status){
                case "add":
                    // mendapatkan menghitung stok bahan baru
                    stokBahan = this.getTempStokBahan(idBahan) + (quantity * jumlah);
                    break;
                case "min":
                    // mendapatkan menghitung stok bahan baru
                    stokBahan = this.getTempStokBahan(idBahan) - (quantity * jumlah);
                    break;
            }
            
            // meyimpan stok bahan baru pada hashmap
            this.setTempStokBahan(idBahan, stokBahan);
        }
        // hitung ulang stok menu
        this.getAllStokMenu();
    }
    
    public static void main(String[] args) {
        
        TesTempStokMenu t = new TesTempStokMenu();
        t.getAllStokBahan();
//        t.showStokBahan();
//        t.setTempStokBahan("BA019", 1200);
//        System.out.println(t.getTempStokBahan("BA019"));
//        t.showBahanMenu("MN014");
//        System.out.println(t.hitungStokMenu("MN018"));
        t.getAllStokMenu();
//        t.showStokMenu();
        t.updateStokMenu("MN018", 2, "min");
        System.out.println(t.hitungStokMenu("MN018"));
        
    }
    
}
