package com.manage;

import com.media.Audio;
import com.toedter.calendar.JDateChooser;
import haqiachd.list.JListCustom;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Achmad Baihaqi
 * @since 2021-03-15
 */
public class Validation {
    
    private static final Text txt = new Text();
    
    public static boolean containsNumber(String text){
        String number = "1234567890";
        char a, b;
        for(int i = 0; i < number.length(); i++){
            for(int k = 0; k < text.length(); k++){
                a = number.charAt(i); b = text.charAt(k);
                if(a == b){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isNumber(String text){
        if(text == null){
            return false;
        }
        text = text.toLowerCase();
        // karakter yang bukan merupakan number
        String notNums = "abcdefghijklmnopqrstuvwxyz`~!@#$%^&*()_+=\\|{[]}:;'\"<>?/";
        for(int i = 0; i < text.length(); i++){
            for(int k = 0; k < notNums.length(); k++){
                // jika kerakter yang ada didalam text mengandung karakter yang ada didalam notNums maka akan mereturn false
                if(text.charAt(i) == notNums.charAt(k)){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isNamaOrang(String nama){
        if(nama.length() >= 4 && nama.length() <= 50){
            if(!containsNumber(nama)){
                return true;
            }else{
                Audio.play(Audio.SOUND_WARNING);
                JOptionPane.showMessageDialog(null, "Nama tidak boleh mengandung angka!", "Pesan", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            Audio.play(Audio.SOUND_WARNING);
            JOptionPane.showMessageDialog(null, "Nama harus terdiri dari 4-50 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isUsername(String username){
        if(username.length() >= 5 && username.length() <= 20){
            if(!username.contains(" ")){
                return true;
            }else{
                Audio.play(Audio.SOUND_WARNING);
                JOptionPane.showMessageDialog(null, "Username tidak boleh mengandung spasi!", "Pesan", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            Audio.play(Audio.SOUND_WARNING);
            JOptionPane.showMessageDialog(null, "Panjang dari Username harus diantara 5-20 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isRfid(String rfid){
        if(rfid.length() == 10){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Panjang dari RFID harus 10 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isPassword(String password){
        if(password.length() >= 8){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Panjang dari Password harus lebih dari 8 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isNamaTempat(String namaTempat){
        if(namaTempat.length() >= 4 && namaTempat.length() <= 50){
            return true;
        }else{
            Audio.play(Audio.SOUND_WARNING);
            JOptionPane.showMessageDialog(null, "Panjang dari Nama Tempat harus diantara 4-50 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isEmail(String email){
        String gmail = "@gmail.com", ymail = "@yahoo.com", sekolah = "@smkn1kts.sch.id", alamat;
        if(email.length() >= 10 && email.length() <= 60){
            if(email.contains("@") && email.contains(".")){
                alamat = email.substring(email.lastIndexOf("@"));
                if(alamat.equalsIgnoreCase(gmail) || alamat.equalsIgnoreCase(ymail) || alamat.equalsIgnoreCase(sekolah)){
                    return true;
                }else{
                    Audio.play(Audio.SOUND_WARNING);
                    JOptionPane.showMessageDialog(null, "Alamat email yang didukung hanyalah : @gmail.com, @yahoo.com dan @smkn1kts.sch.id", "Pesan", JOptionPane.WARNING_MESSAGE);
                }                
            }else{
                Audio.play(Audio.SOUND_WARNING);
                JOptionPane.showMessageDialog(null, "Email tersebut tidak valid!", "Pesan", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            Audio.play(Audio.SOUND_WARNING);
            JOptionPane.showMessageDialog(null, "Panjang dari Email harus diantara 10-60 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isNoHp(String noHp){
        if(!Validation.isNumber(noHp)){
            JOptionPane.showMessageDialog(null, "Nomor HP harus berupa angka");
            return false;
        }
        
        if(noHp.length() >= 10 && noHp.length() <= 15){
            if(noHp.startsWith("08")){
                return true;
            }else{
                Audio.play(Audio.SOUND_WARNING);
                JOptionPane.showMessageDialog(null, "Nomor HP tidak valid!\nContoh Nomor HP yang valid : 085655864624", "Pesan", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            Audio.play(Audio.SOUND_WARNING);
            JOptionPane.showMessageDialog(null, "Panjang dari Nomor Hp harus diantara 10-15 karakter!", "Pesan", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    public static boolean isShif(String level, String shif){
        switch(level.toLowerCase()){
            case "admin" : 
                if(shif.equalsIgnoreCase("no shif") == true){
                    return true;
                }else{
                    Message.showWarning(null, "Akun dengan level admin hanya dapat memiliki shif 'No Shif'");
                    return false;
                }
            case "karyawan" : 
                if(shif.equalsIgnoreCase("Siang") || shif.equalsIgnoreCase("Malam")){
                    return true;
                }else{
                    Message.showWarning(null, "Akun dengan level karyawan hanya dapat memiliki shif 'Siang' dan 'Malam'");
                    return false;
                }
        }
        return false;
    }
    
    public static boolean isEmptyTextField(JTextField... fields){
        // mengecek semua field apakah kosong atau tidak
        for(JTextField field : fields){
            // jika field kosong
            if(field.getText().isEmpty() || txt.isBlank(field.getText())){
                Message.showWarning(null, "Data " + field.getName() + " tidak boleh kosong!");
                return false;
            }
        }
        return true;
    }
    
    public static boolean isEmptyPasswordField(JPasswordField... fields){
        // mengecek semua field apakah kosong atau tidak
        for(JPasswordField field : fields){
            // jika field kosong
            if(field.getText().isEmpty()){
                Message.showWarning(null, "Data " + field.getName() + " tidak boleh kosong!");
                return false;
            }
        }
        return true;
    }
    
    public static boolean isEmptyComboBox(JComboBox... combos){
        // mengecek semua combo box apakah kosong atau tidak
        for(JComboBox combo : combos){
            // jika combo box
            if(combo.getSelectedIndex() == 0){
                Message.showWarning(null, "Data " + combo.getName() + " tidak boleh kosong!");
                return false;
            }
        }
        return true;
    }
    
    public static boolean isEmptyList(JListCustom... lists){
        // mengecek semua list apakah kosong atau tidak
        for(JListCustom list : lists){
            // jika list kosong
            if(list.getAllList().length == 0){
                Message.showWarning(null, "Data " + list.getName() + " tidak boleh kosong!");
                return false;
            }
        }
        return true;
    }
    
    public static boolean isEmptyJDate(JDateChooser... dates){
        // mengecek semua list apakah kosong atau tidak
        for(JDateChooser date : dates){
            // jika list kosong
            if(date.getDate() == null){
                Message.showWarning(null, "Data " + date.getName() + " tidak boleh kosong!");
                return false;
            }
        }
        return true;
    }
     
}
