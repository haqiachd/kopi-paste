package test;

import com.manage.Waktu;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achmad Baihaqi
 */
public class TestWaktu {
    
    private static Waktu waktu = new Waktu();
    
    public static void main(String[] args) {
        
        new Thread(new Runnable(){
            @Override
            public void run() {
                Waktu.updateWaktu();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TestWaktu.class.getName()).log(Level.SEVERE, null, ex);
                }

                StringTokenizer token = new StringTokenizer(waktu.getLastMonthDate(), "-");
                int tahun = Integer.parseInt(token.nextToken()),
                        bulan = Integer.parseInt(token.nextToken()),
                        tanggal = Integer.parseInt(token.nextToken()),
                        totalHari = waktu.getTotalHari(bulan);

                System.out.println(tahun);
                System.out.println(bulan);
                System.out.println(tanggal);

                System.out.println("hitung hari");
                for (int i = tanggal; i <= totalHari; i+=2) {
                    System.out.println(i);
                }
                
                if(tanggal != totalHari){
                    for(int i = 1; i < tanggal; i+=2){
                        System.out.println(i);
                    }
                }
            }
        }).start();

        

        
//        if(tanggal != totalHari){
//            for(int i = 1; i < tanggal; i++){
//                System.out.println(i);
//            }
//        }

    }
}
