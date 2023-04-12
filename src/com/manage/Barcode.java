package com.manage;

import com.barcodelib.barcode.Linear;
import com.koneksi.Database;
import com.media.Gambar;
import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Achmad Baihaqi
 */
public class Barcode {
    
    private final Database db = new Database();
    
    private final FileManager fl = new FileManager();
    
    private static final String DIRECTORY = "src\\resources\\image\\barcode\\";
    
    public void generate(String kode) {
        try {
            Linear barcode = new Linear();
            barcode.setType(Linear.CODE128B);
            barcode.setData(kode);
            barcode.setI(12.0f);
            barcode.renderBarcode(Barcode.DIRECTORY + kode + ".png");
        } catch (Exception e) {
            Message.showException(this, e);
        }
    }
    
    public boolean isExistMysql(String kode){
        try{
            String sql = "SELECT id_barcode FROM menu WHERE id_barcode = " + kode;
            this.db.res = this.db.stat.executeQuery(sql);
            if(this.db.res.next()){
                return true;
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return false;
    }
    
    public boolean isExistImage(String kode){
        return new File(DIRECTORY + kode + ".png").exists();
    }
    
    public boolean isNullBarcode(String idMenu){
        try{
            String sql = "SELECT id_barcode FROM menu WHERE id_menu = '" + idMenu + "'";
            this.db.res = this.db.stat.executeQuery(sql);
            if(this.db.res.next()){
                return this.db.res.getString("id_barcode") == null;
            }
        }catch(SQLException ex){
            Message.showException(this, ex);
        }
        return false;
    }
    
    public void downloadBarcode(String kode){
        Blob blob;
        String dir = DIRECTORY + kode + ".png";
        try{
            if(this.isExistMysql(kode)){
                String sql = "SELECT img_barcode FROM menu WHERE id_barcode = " + kode;
                this.db.res = this.db.stat.executeQuery(sql);
                if(this.db.res.next()){
                    blob = this.db.res.getBlob("img_barcode");
                    fl.blobToFile(blob, dir);
                    
                }
            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String randomBarcode(){
        long random = (long) (new Random().nextDouble() * 9_000_000_000L) + 1_000_000_000_00L;
        return this.isExistMysql(Long.toString(random)) ? this.randomBarcode() : Long.toString(random);
    }
    
    public ImageIcon getBarcodeImage(String kode) {
        // mengecek apakah file gambar barcode ada atau tidak
        if (this.isExistImage(kode)) {
            // mengembalikan gambar barcode
            return new ImageIcon(new File(Barcode.DIRECTORY + kode + ".png").toString());
        } else {
            // download barcode
            Message.showWarning(this, "Tidak dapat menemukan file '" + kode + "'");
            return null;
        }
    }
    
    public ImageIcon getBarcodeImage(String kode, int width, int height){
        if(this.isExistImage(kode)){
            return Gambar.scaleImage(new File(Barcode.DIRECTORY + kode + ".png"), width, height);            
        } else {
            Message.showWarning(this, "Tidak dapat menemukan file '" + kode + "'");
            return null;
        }
    }
    
    public void close(){
        this.db.closeConnection();
    }
    
    
    public static void main(String[] args) {
        
        Barcode barcode = new Barcode();
        barcode.generate("240588155633");
        System.out.println(barcode.randomBarcode());
        
        Random random = new Random();
        long randomNumber = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000_00L;
        System.out.println(Long.toString(randomNumber).length());
        System.out.println(randomNumber);
        
//        barcode.generate("0");
//        barcode.generate("01");
//        barcode.generate("012");
//        barcode.generate("0123");
//        barcode.generate("01234");
//        barcode.generate("012345");
//        barcode.generate("0123456");
//        barcode.generate("01234567");
//        barcode.generate("012345678");
//        barcode.generate("01234567890");
//        barcode.generate("012345678901");
//        barcode.generate("0123456789012");
        
        for(int i = 0; i <= 10; i++){
            System.out.println(barcode.randomBarcode());
        }
        
        System.out.println(barcode.isExistMysql("0123456789012"));
//        System.out.println(barcode.isExistBarcode(Barcode.DATA_BAHAN, "0123456789012"));
        System.out.println(barcode.isExistImage("0123456789012"));
        System.out.println(barcode.isNullBarcode("MN069"));
        barcode.downloadBarcode("0123456789012");
        
    }
    
}
