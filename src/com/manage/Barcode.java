package com.manage;

import com.barcodelib.barcode.Linear;
import com.media.Gambar;
import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Achmad Baihaqi
 */
public class Barcode {
    
    private static final String DIRECTORY = "src\\resources\\image\\barcode\\";
    
    public void generate(String kode) {
        try {
            Linear barcode = new Linear();
            barcode.setType(Linear.CODE128B);
            barcode.setData(kode);
            barcode.setI(12.0f);
            barcode.renderBarcode("src\\resources\\image\\barcode\\" + kode + ".png");
        } catch (Exception e) {
            Message.showException(this, e);
        }
    }
    
    public boolean isExistBarcode(String kode){
        return false;
    }
    
    public ImageIcon getBarcodeImage(String kode) {
        File file = new File(Barcode.DIRECTORY + kode + ".png");

        // mengecek apakah file gambar barcode ada atau tidak
        if (file.exists()) {
            return new ImageIcon(file.toString());
        } else {
            Message.showWarning(this, "Tidak dapat menemukan file '" + kode + "'");
            return null;
        }
    }
    
    private String randomBarcode(){
        return Long.toString((long) (new Random().nextDouble() * 9_000_000_000L) + 1_000_000_000_00L);
    }
    
    public ImageIcon getBarcodeImage(String kode, int width, int height){
        File file = new File(Barcode.DIRECTORY + kode + ".png");
        return Gambar.scaleImage(file, width, height);
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
        
    }
    
}
