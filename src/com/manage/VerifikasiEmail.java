package com.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Achmad Baihaqi
 */
public class VerifikasiEmail {
    
    private final Random rand = new Random();
    
    private final Internet inet = new Internet();
    
    private final Waktu waktu = new Waktu();
    
    private static int L_RANDOM = 0, L_KODE = 0;
    
    private static String EMAIL;
    
    private static String KODE = null;
    
    public VerifikasiEmail(String email){
        EMAIL = email;
    }
    
    public VerifikasiEmail(){
        this(null);
    }
    
    public String getKode(){
        return KODE;
    }
    
    public int getLRandom(){
        return L_RANDOM;
    }
    
    public int getLKode(){
        return L_KODE;
    }
    
    public String getEmail(){
        return EMAIL;
    }
    
    public void setEmail(String email){
        EMAIL = email;
    }
    
    public String randomKode(){
      String kode = "";
      
      // cek apakah bisa merandom kode
      if(this.isCanRandom()){
          // merandom 8 digit kode verifikasi
          while (kode.length() < 8) {
              kode += rand.nextInt(10);
          }
          
          // mereset limit random dan limit kode
          L_RANDOM = 60; 
          L_KODE = 600;
          // menyimpan kode verifikasi ke field
          KODE = kode;
          // menghitung mundur limit random dan limit kode
          this.hitungMundurRandom();
          this.hitungMundurKode(); 
          // kirim kode ke email
          boolean send = this.sendEmail();
          // jika kode gagal dikirim maka kode akan direset
          if(!send){
              this.killKode();
          }
          
          return KODE;
      }else{
          // jika masih dalam limit random maka akan mereturn kode yang sudah ada
          return this.isActiveKode() ? KODE : null;
      }
    }
    
    private boolean sendEmail(){
        try {
            String subject = KODE + " adalah kode verifikasi Anda";
            return this.inet.sendGmail(EMAIL, subject, this.generateHtml());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    private String generateHtml() throws IOException{
        File file = new File("src\\resources\\file\\verifikasi-email.html");
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        String buffer, html = "";
        
        while((buffer = bf.readLine()) != null){
            if(buffer.contains("00000000")){
                buffer = buffer.replace("00000000", KODE);
            }
            html += buffer;
        }
        return html;
    }
    
    public void hitungMundurRandom(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                // hitung mundur limit random sampai detik ke 0
                while(L_RANDOM > 0){
                    L_RANDOM--;
                    waktu.delay(1000);
                }
            }
        }).start();
    }
    
    public void hitungMundurKode(){
         new Thread(new Runnable(){
            @Override
            public void run(){
                // hitung mundur limit kode sampai detik ke 0
                while(L_KODE >= 0){
                    // cek apakah limit kode sudah berakhir
                    if(L_KODE == 0){
                        // reset kode menjadi null jika melewati batas limit kode
                        KODE = null;
                    }
                    L_KODE--;
                    waktu.delay(1000);
                }
            }
        }).start();
    }
    
    /**
     * Digunakan untuk mengecek apakah user dapat merandom kode atau tidak
     * Jika user dapat merandom kode maka user tidak dalam waktu limit random,
     * tetapi jika user tidak dapat merandom maka user dalam limit waktu random
     * 
     * @return 
     */
    public boolean isCanRandom(){
        return L_RANDOM == 0;
    }
    
    /**
     * Digunakan untuk mengecek apakah kode yang dirandom sebelumnya masih aktif atau tidak,
     * jika kode melebihi waktu limit kode maka kode akan tidak aktif lagi
     * 
     * @return 
     */
    public boolean isActiveKode(){
        return L_KODE >= 1;
    }
    
    /**
     * Digunakan untuk mereset kode yang sebelumnya dirandom
     * 
     */
    public void killKode(){
        KODE = null;
        L_RANDOM = 0;
        L_KODE = 0;
    }
    
    public static void main(String[] args) throws IOException {
        
        VerifikasiEmail ve = new VerifikasiEmail("hakiahmad756@gmail.com");
        ve.randomKode();
        System.out.println(ve.randomKode());
//        ve.generateHtml();
//        System.out.println(ve.randomKode());
//        while(true){
//            ve.randomKode();
//            System.out.println(ve.getKode());
//            ve.waktu.delay(1000);
//        }
    }
    
}
