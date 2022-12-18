package com.manage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Achmad Baihaqi
 * @since 2021-03-16
 */
public class Waktu {
    
    private static Calendar kalender = Calendar.getInstance();
    
    private static int tahun, bulan, tanggal, jam, menit, detik, milidetik;
    
    public static final String JANUARI = "Januari", FEBRUARI = "Februari", MARET = "Maret", APRIL = "April", 
                               MEI = "Mei", JUNI = "Juni", JULI = "Juli", AGUSTUS = "Agustus", SEPTEMBER = "September", 
                               OKTOBER = "Oktober", NOVEMBER = "November", DESEMBER = "Desember";
    
    public int getTahun(){
        return tahun;
    }
    
    public int getBulan(){
        return bulan;
    }
    
    public int getTanggal(){
        return tanggal;
    }
    
    public int getJam(){
        return jam;
    }
    
    public int getMenit(){
        return menit;
    }
    
    public int getDetik(){
        return detik;
    }
    
    public String getDateFromInput(Calendar c){
        // mendapatkatkan data tanggal
        return String.format(
                "%d-%d-%d", 
                c.get(Calendar.YEAR), // mendapatkan tahun
                c.get(Calendar.MONTH)+1, // mendapatkan bulan
                c.get(Calendar.DAY_OF_MONTH) // mendapatkan tanggal
        );
    }
    
    
    public String getNamaHari(int dayOfWeek){
        switch(dayOfWeek){
            case Calendar.SUNDAY: return "Minggu";
            case Calendar.MONDAY: return "Senin";
            case Calendar.TUESDAY: return "Selasa";
            case Calendar.WEDNESDAY: return "Rabu";
            case Calendar.THURSDAY: return "Kamis";
            case Calendar.FRIDAY: return "Jumat";
            case Calendar.SATURDAY: return "Sabtu";
            default: return "null";
        }
    }
    
    public String getNamaHari(){
        return this.getNamaHari(kalender.get(Calendar.DAY_OF_WEEK));
    }
    
//Senin = Monday.
//Selasa = Tuesday.
//Rabu = Wednesday.
//Kamis = Thursday.
//Jumat = Friday.
//Sabtu = Saturday.
//Minggu = Sunday.
    public String getNamaHariInIndonesian(String english){
        switch(english.toLowerCase()){
            case "monday" : return "Senin";
            case "tuesday" : return "Selasa";
            case "wednesday" : return "Rabu";
            case "thursday" : return "Kamis";
            case "friday": return "Jumat";
            case "saturday" : return "Sabtu";
            case "sunday" : return "Minggu";
            default : return "null";
        }
    }
    
    public String getNamaBulan(int bulan){
        switch(bulan-1){
            case Calendar.JANUARY: return "Januari";
            case Calendar.FEBRUARY: return "Februari";
            case Calendar.MARCH: return "Maret";
            case Calendar.APRIL: return "April";
            case Calendar.MAY: return "Mei";
            case Calendar.JUNE: return "Juni";
            case Calendar.JULY: return "Juli";
            case Calendar.AUGUST: return "Agustus";
            case Calendar.SEPTEMBER: return "September";
            case Calendar.OCTOBER: return "Oktober";
            case Calendar.NOVEMBER: return "November";
            case Calendar.DECEMBER: return "Desember";
            default: return "null";
        }
    }
    
    public String getNamaBulan(){
        return this.getNamaBulan(Waktu.bulan);
    }
    
    public int getNilaiBulan(String bulan){
        switch(bulan){
            case "Januari": return 1;
            case "Februari": return 2;
            case "Maret": return 3;
            case "April": return 4;
            case "Mei": return 5;
            case "Juni": return 6;
            case "Juli": return 7;
            case "Agustus": return 8;
            case "September": return 9;
            case "Oktober": return 10;
            case "November": return 11;
            case "Desember": return 12;
            default: return -11;
        }
    }
    
    public int getTotalHari(int bulan){
        switch(bulan){
            case 1: return 31;
            case 2: return (tahun%4==0) || (tahun%400==0) ? 29 : 28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return -1;
        }
    }
    
    @Deprecated
    public static void updateWaktuOld(){
        // mendapatkan waktu saat ini
        Waktu.tahun = Waktu.kalender.get(Calendar.YEAR);
        Waktu.bulan = Waktu.kalender.get(Calendar.MONTH);
        Waktu.tanggal = Waktu.kalender.get(Calendar.DAY_OF_MONTH);
        Waktu.jam = Waktu.kalender.get(Calendar.HOUR_OF_DAY);
        Waktu.menit = Waktu.kalender.get(Calendar.MINUTE);
        Waktu.detik = Waktu.kalender.get(Calendar.SECOND);
        Waktu.milidetik = Waktu.kalender.get(Calendar.MILLISECOND);
        
        // mengupdate waktu
        new Thread(new Runnable(){
            
            @Override
            public void run(){
                try{
                    while(true){
                        milidetik+=1;
                        if(milidetik > 999){
                            detik++;
                            milidetik = 0;
                        }else if(detik > 59){
                            menit++;
                            detik = 0;
                        }else if(menit > 59){
                            jam++;
                            menit = 0;
                        }else if(jam > 23){
                            jam = 0;
                            tanggal++;
                        }
                        Thread.sleep(1);
                    }
                }catch(InterruptedException ex){
                    Message.showException(null, "Terjadi Kesalahan : " + ex.getMessage(), ex);
                }
            }
        }).start();
    }
    
    public static void updateWaktu() {

        new Thread(() -> {
            try {
                while (true) {
                    kalender = Calendar.getInstance();
                    tahun = kalender.get(Calendar.YEAR);
                    bulan = kalender.get(Calendar.MONTH)+1;
                    tanggal = kalender.get(Calendar.DAY_OF_MONTH);
                    jam = kalender.get(Calendar.HOUR_OF_DAY);
                    menit = kalender.get(Calendar.MINUTE);
                    detik = kalender.get(Calendar.SECOND);
                    milidetik = kalender.get(Calendar.MILLISECOND);
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }).start();

    }
    
    public String getCurrentDate(){
        return String.format("%d-%02d-%02d", getTahun(), getBulan(), getTanggal());
    }
    
    public String getCurrentDateTime(){
        return String.format("%s %02d:%02d:%02d", this.getCurrentDate(), getJam(), getMenit(), getDetik());
    }
    
    public String getUpdateWaktu(){
        return String.format("%s, %02d %s %d %02d:%02d:%02d", 
                this.getNamaHari(), tanggal, this.getNamaBulan(bulan), tahun, jam, menit, detik
        );
    }
    
    public String getUpdateTime(){
        return String.format("%02d %s %d %02d:%02d:%02d", 
                tanggal, this.getNamaBulan(bulan), tahun, jam, menit, detik
        );
    }
    
    @Deprecated
     public Date[] weekk(int tgl, int bln, int thn) throws ParseException{
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date(thn, bln, tgl);
        Calendar calendar = Calendar.getInstance(); //tanggal sekarang
        calendar.setTime(d);
        Calendar c1 = Calendar.getInstance();
        Calendar random = Calendar.getInstance();
        Date tanggal[] = new Date[2];
        Date awal = new Date();
        Date akhir = new Date();
        Date senin = new Date();
        if(bln <=-1){
            c1 .set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),tgl);
        }else{
            c1.set(calendar.get(Calendar.YEAR), bln,tgl);
        }
        System.out.println("tanggal hari ini "+ c1.getTime());
        int max = c1.getActualMaximum(Calendar.DATE);
        int sisa = max-tgl;
        int harike = c1.get(Calendar.DAY_OF_WEEK)-1;
        if(max == 28||max == 29){
            if(tgl <= 6){
                    System.out.println("hari ini kurang dari 6");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }else{
                    //done 
                    System.out.println(" logic false");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,-harike+7);
                    akhir = c1.getTime();
                }
            }else if(tgl > 6 && sisa >= 6){
                    System.out.println("hari ini lebih dari 6");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }else{
                    //done 
                    System.out.println(" logic false");
                    c1.add(Calendar.DATE,-harike+1);
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }
            }else{
                    System.out.println("hari tua");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    System.out.println("sisa hari "+sisa);
                    if(bln <= -1){
                        c1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),max);
                    }else{
                        c1.set(calendar.get(Calendar.YEAR),bln,max);
                    }
                    akhir = c1.getTime();
                }else{
                    System.out.println(" logic false");
                    c1.add(Calendar.DATE,-harike+1);
                    awal = c1.getTime();
                    System.out.println("sisa hari "+sisa);
                    if(bln <= -1){
                        c1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),max);
                    }else{
                        c1.set(calendar.get(Calendar.YEAR),bln,max);
                    }
                    akhir = c1.getTime();
                }
            }
        }else{
            if(tgl <= 6){
                    System.out.println("hari ini kurang dari 6");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }else{
                    //done 
                    System.out.println(" logic false");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,-harike+7);
                    akhir = c1.getTime();
                }
            }else if(tgl > 6 && sisa >= 6){
                    System.out.println("hari ini lebih dari 6");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }else{
                    //done 
                    System.out.println(" logic false");
                    c1.add(Calendar.DATE,-harike+1);
                    awal = c1.getTime();
                    c1.add(Calendar.DATE,6);
                    akhir = c1.getTime();
                }
            }else{
                    System.out.println("hari tua");
                if(harike == 1){
                    //done 
                    System.out.println(" logic true");
                    awal = c1.getTime();
                    System.out.println("sisa hari "+sisa);
                    if(bln <= -1){
                        c1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),max);
                    }else{
                        c1.set(calendar.get(Calendar.YEAR),bln,max);
                    }
                    akhir = c1.getTime();
                }else{
                    System.out.println(" logic false");
                    c1.add(Calendar.DATE,-harike+1);
                    awal = c1.getTime();
                    System.out.println("sisa hari "+sisa);
                    if(bln <= -1){
                        c1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),max);
                    }else{
                        c1.set(calendar.get(Calendar.YEAR),bln,max);
                    }
                    akhir = c1.getTime();
                }
            }
        }
        System.out.println("tanggal senin adalah "+awal);
        System.out.println("tanggal terakhir adalah "+akhir);
        tanggal[0] = awal;
        tanggal[1] = akhir;
        return tanggal;
    }
     
     public Object[] getMinggu(int bulan, int tahun){
         // menampung data minggu
         SortedMap<String, String> data = new TreeMap<>();
         
         // inisialisasi waktu awal
         bulan--;
         int hari = 1, week = 1;
         String minggu, sabtu;
         Date d = new Date(tahun-1900, bulan, hari);
         Calendar c = Calendar.getInstance();
         c.setTime(d);
         System.out.printf("Menampilkan data minggu pada bulan %s %d\n", this.getNamaBulan(bulan+1), tahun);
         
         // mendapatkan sisa hari pada awal bulan
         int tgl1 = c.get(Calendar.DAY_OF_WEEK),
             sisaHari = 7, totalHari = this.getTotalHari(bulan+1);
         System.out.printf("Hari pertama pada %s adalah hari %s\n", this.getNamaBulan(bulan+1), this.getNamaHari(tgl1));
         
         // jika bulan tidak diawali dengan hari minggu
         if(tgl1 > 1){
             // mengurangi sisahari
             sisaHari = sisaHari - (7 - tgl1+1);
         }
         else{
             sisaHari = 0;
         }
         
         // mendapatkan hari minggu pertama
         hari = hari - sisaHari;
         d = new Date(tahun-1900, bulan, hari);
         c.setTime(d);
         minggu = String.format("%d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
         
         // mendapatkan hari sabtu pertama
         hari = 7 - sisaHari;
         d = new Date(tahun-1900, bulan, hari);
         c.setTime(d);
         sabtu = String.format("%d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
         
         // menyimpan data minggu
         data.put(minggu, sabtu);
         System.out.println(String.format("Minggu ke-%d : %s ---> %s", week, minggu, sabtu));
         
         // mendapatkan data minggu ke 2 sampai minggu terakhir
         while(hari <= totalHari){
//             System.out.println("HARI KE -> " + hari);
             // inisialisasi ulang waktu
             d = new Date(tahun-1900, bulan, hari);
             c.setTime(d);
             
             // mendapatkan data minggu pada bulan
             int fWeek = c.get(Calendar.DAY_OF_WEEK);
             if(fWeek == 1){
                 // mendapatkan awal minggu
                 minggu = String.format("%d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
                 hari+=6;
                 // mendapatkan akhir minggu
                 d = new Date(tahun-1900, bulan, hari);
                 c.setTime(d);
                 sabtu = String.format("%d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
                 
                 // menyimpan data minggu
                 week++;
                 data.put(minggu, sabtu);
                 System.out.println(String.format("Minggu ke-%d : %s ---> %s", week, minggu, sabtu));
             }
             hari++;
         }
         
         System.out.println("\nAKHIR METHOD\n\n");
         return data.entrySet().toArray();
     }
     
    public static void main(String[] args) {
        
        Waktu w = new Waktu();
        
        int week = 1;
        Object[] minggu = w.getMinggu(12, 2022);
        System.out.println(minggu[0]);
        System.out.println("\n\n\n");
        for(Object m : minggu){
//            System.out.println(String.format("Minggu %d : %s ", week, m.toString().replace("=",  " --> ")));
            System.out.println(String.format("Minggu %d : %s ", week, m));
            week++;
        }
        

    }
    

     
     
}
