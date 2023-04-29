package com.manage;

import java.util.Random;

/**
 *
 * @author Achmad Baihaqi
 */
public class VerifikasiEmail {
    
    private final Random rand = new Random();
    
    private final Waktu waktu = new Waktu();
    
    private static int L_RANDOM = 0, L_KODE = 0, KODE = 0;
    
    public int randomKode(){
      String kode = "";
      // cek apakah bisa merandom kode
      if(this.isCanRandom()){
          // merandom kode
          while (kode.length() < 10) {
              kode += rand.nextInt(10);
          }
          
          L_RANDOM = 30; // reset limit waktu
          L_KODE = 60; // reset limit aktif kode
          KODE = Integer.parseInt(kode.substring(0, 8)); // menyimpan reandom kode ke field
          this.hitungMundurRandom(); // menghitung mundur limit waktu
          this.hitungMundurKode(); // menghitung mundur limit kode
          return KODE;
      }else{
          // jika masih dalam limit waktu untuk random
          return this.isActiveKode() ? KODE : -1;
      }
    }
    
    public int getKode(){
        return KODE;
    }
    
    public int getLRandom(){
        return L_RANDOM;
    }
    
    public int getLKode(){
        return L_KODE;
    }
    
    public void hitungMundurRandom(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                // hitung mundur limit waktu sampai detik ke 0
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
                // hitung mundur limit waktu sampai detik ke 0
                while(L_KODE >= 0){
                    if(L_KODE == 0){
                        KODE = 0;
                    }
                    L_KODE--;
                    waktu.delay(1000);
                }
            }
        }).start();
    }
    
    public boolean isCanRandom(){
        return L_RANDOM == 0;
    }
    
    public boolean isActiveKode(){
        return L_KODE >= 1;
    }
    
    public void killKode(){
        KODE = 0;
        L_RANDOM = 0;
        L_KODE = 0;
    }
    
    public static void main(String[] args) {
        
        VerifikasiEmail ve = new VerifikasiEmail();
        System.out.println(ve.randomKode());
        while(true){
            System.out.println(ve.getKode());
            ve.waktu.delay(2500);
        }
    }
    
}
