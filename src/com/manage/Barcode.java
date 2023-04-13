package com.manage;

import com.barcodelib.barcode.Linear;
import com.koneksi.Database;
import com.media.Gambar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Manajemen barcode pada data menu
 * 
 * @author Achmad Baihaqi
 */
public class Barcode {
    
    private final Database db = new Database();
    
    private final FileManager fl = new FileManager();
    
    private static final String DIRECTORY = "src\\resources\\image\\barcode\\";
    
    public void generate(String kodeBarcode, String idMenu) {
        try {
            Linear barcode = new Linear();
            barcode.setType(Linear.CODE128B);
            barcode.setData(kodeBarcode);
            barcode.setI(12.0f);
            barcode.renderBarcode(Barcode.DIRECTORY + idMenu + ".png");
        } catch (Exception e) {
            e.printStackTrace();
            Message.showException(this, e);
        }
    }
    
    public String getKodeBarcode(String idMenu){
        try{
            String sql = "SELECT id_barcode FROM menu WHERE id_menu = '"+idMenu+"'";
            this.db.res = this.db.stat.executeQuery(sql);
            if(this.db.res.next()){
                return this.db.res.getString("id_barcode");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }
    
    public boolean isNullBarcode(String idMenu){
        return this.getKodeBarcode(idMenu) == null;
    }
    
    public boolean isExistMysql(String kodeBarcode){
        try{
            String sql = "SELECT id_barcode FROM menu WHERE id_barcode = '" + kodeBarcode + "'";
            this.db.res = this.db.stat.executeQuery(sql);
            if(this.db.res.next()){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    public boolean isExistImage(String filename){
        return new File(DIRECTORY + filename + ".png").exists();
    }
    
    public boolean downloadBarcode(String idMenu){
        Blob blob;
        // membuat filename dan query download
        String filename = DIRECTORY + idMenu + ".png",
               sql = "SELECT img_barcode FROM menu WHERE id_menu = '" + idMenu + "'";
        try{
            // cek apakah kode barcode ada atau tidak
            if(!this.isNullBarcode(idMenu)){
                // eksekusi query untuk download barcode
                this.db.res = this.db.stat.executeQuery(sql);
                
                if(this.db.res.next()){
                    // mendapatkan blob dari barcode image
                    blob = this.db.res.getBlob("img_barcode");
                    
                    // konversi blob ke file (png)
                    fl.blobToFile(blob, filename);
                    System.out.println("Download barcode success");
                    return true;
                }
            }else{
                System.out.println("Tidak ada barcode");
            }       
        }catch(SQLException | IOException ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return false;
    }
    
    public InputStream barcodeToBlob(String namaFile){
        try {
            // konversi file image barcode ke blob mysql
            return fl.fileToBlob(new File(DIRECTORY + namaFile + ".png"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            Message.showException(this, ex);
        }
        return null;
    }
    
    public ImageIcon getBarcodeImage(String idMenu) {
        // mengecek apakah file gambar barcode ada atau tidak
        if (this.isExistImage(idMenu)) {
            // mengembalikan gambar barcode
            return new ImageIcon(new File(Barcode.DIRECTORY + idMenu + ".png").toString());
        } else {
            // download barcode
            return this.downloadBarcode(idMenu) ? this.getBarcodeImage(idMenu) : null;
        }
    }
    
    public ImageIcon getBarcodeImage(String idMenu, int width, int height){
        // mengecek apakah file gambar barcode ada atau tidak
        if(this.isExistImage(idMenu)){
            // mengembalikan gambar dengan scale
            return Gambar.scaleImage(new File(Barcode.DIRECTORY + idMenu + ".png"), width, height);            
        } else {
            // download barcode
            return this.downloadBarcode(idMenu) ? this.getBarcodeImage(idMenu, width, height) : null;
        }
    }
    
    public boolean deleteBarcode(String filename){
        return new File(Barcode.DIRECTORY + filename + ".png").delete();
    }
    
    public void close(){
        this.db.closeConnection();
    }
    
    
    public static void main(String[] args) {
        
        Barcode barcode = new Barcode();
//        System.out.println(barcode.isExistImage("MN074"));
        System.out.println(barcode.deleteBarcode("MN076"));
        
    }
    
}
