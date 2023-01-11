package com.manage;

/**
 *
 * @author Achmad Baihaqi
 */
public class Bahan {
    
    public String getkodeSatuan(String satuan){
        switch(satuan){
            case "Kilogram" : return "kg";
            case "Liter" : return "lt";
            case "Dus" : return "ds";
            case "Renceng" : return "rc";
            case "Lusin" : return "ls";
            case "Botol" : return "bt";
            default : return "null statuan";
        }
    }
    
    public String getNamaSatuan(String satuan){
       switch(satuan){
            case "kg" : 
                return "Kilogram";
            case "lt" : 
                return "Liter";
            case "ds" : 
                return "Dus";
            case "rc" : 
                return "Renceng";
            case "ls" : 
                return "Lusin";
            case "bt" : 
                return "Botol";
            default : return "null satuan";
        }
    }
    
    public String getSatuanBesaran(String satuan){
        switch(satuan){
            case "Kilogram" : return "kg";
            case "Liter" : return "lt";
            case "Dus" : return "ds";
            case "Renceng" : return "rc";
            case "Lusin" : return "ls";
            case "Botol" : return "bt";
            default : return "null statuan";
        }
    }
    
    public String getNilaiSatuan(float value, String satuan){
        // mendapatkan satuan bahan
        switch(satuan){
            case "kg" : return Float.toString(value) + " Kilogram";
            case "lt" : return Float.toString(value) + " Liter";
            case "ds" : return Float.toString(value) + " Dus";
            case "rc" : return Float.toString(value) + " Renceng";
            case "ls" : return Float.toString(value) + " Lusin";
            case "bt" : return Float.toString(value) + " Botol";
            default : return Float.toString(value) + " ??";
        }
    }
    
    public String removeSatuan(String text){
        return text.replace(" Botol", "").replace(" Lusin", "").replace(" Renceng", "").replace(" Dus", "").replace(" Liter", "").replace(" Kilogram", "");
    }
}
