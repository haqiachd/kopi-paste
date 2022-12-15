/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.manage.Waktu;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Achmad Baihaqi
 */
public class Tes {
    

    
    public static void main(String[] args) throws ParseException {
        
        Waktu t = new Waktu();
        Date[] d = t.weekk(24, 11, 2022);
//        System.out.println("d[0] "+ d[0]);
//        System.out.println("d[1] "+ d[1]);
        
        
        Date dd;
        Calendar c;
        int hari = t.getTotalHari(12);
        for(int i = 0; i <= hari; i++){
            dd = new Date(i, 11, 2022);
            c = Calendar.getInstance();
            c.setTime(dd);
            int p = c.get(Calendar.DAY_OF_WEEK);
            if(p == 1){
                Date[] v = t.weekk(i+1, 11, 2022);
                System.out.format("\nSENIN  : %d-%d-%d", v[0].getDate(), v[0].getMonth(), v[0].getYear());
                System.out.format("\nMINGGU : %d-%d-%d\n\n", v[1].getDate(), v[1].getMonth(), v[1].getYear());
            }
        }
        
        
    }
    
}
