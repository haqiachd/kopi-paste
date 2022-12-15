/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.koneksi.Koneksi;
import com.manage.Waktu;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateDetailBeli {
    
    public String getNama(String idBahan){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT nama_bahan FROM bahan WHERE id_bahan = '"+idBahan+"'");
            
            if(r.next()){
                String nama = r.getString(1);
                c.close(); s.close(); r.close();
                return nama;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public String getJenis(String idBahan){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT jenis FROM bahan WHERE id_bahan = '"+idBahan+"'");
            
            if(r.next()){
                String jenis = r.getString(1);
                c.close(); s.close(); r.close();
                return jenis;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public String getHarga(String idBahan){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT harga FROM bahan WHERE id_bahan = '"+idBahan+"'");
            
            if(r.next()){
                String harga = r.getString(1);
                c.close(); s.close(); r.close();
                return harga;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public String getSatuan(String idBahan){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT satuan FROM bahan WHERE id_bahan = '"+idBahan+"'");
            
            if(r.next()){
                String harga = r.getString(1);
                c.close(); s.close(); r.close();
                return harga;
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public Object[] getIdTrb(){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM detail_tr_beli");
            ArrayList<String> list = new ArrayList();
            
            while(r.next()){
                list.add(r.getString("id_tr_beli"));
            }
            
            c.close(); s.close(); r.close();
            return list.toArray();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private Object[] getIdBahan(String idTr){
         try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_bahan FROM detail_tr_beli WHERE id_tr_beli = '"+idTr+"'");
            ArrayList<String> a = new ArrayList();
            
            while(r.next()){
                String id = r.getString(1);
                a.add(id);
            }
            c.close(); s.close(); r.close();
            return a.toArray();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private void updateDtrj(String idTr){
        try{
            for(Object idBa : this.getIdBahan(idTr)){
                String nama = this.getNama(idBa.toString()), 
                        jenis = this.getJenis(idBa.toString()), 
                        harga = this.getHarga(idBa.toString()),
                        satuan = this.getSatuan(idBa.toString());
                
                System.out.println(nama);
                System.out.println(jenis);
                System.out.println(harga);
                
                String sql = String.format("UPDATE detail_tr_beli "
                        + "SET nama_bahan = '%s', jenis_bahan = '%s', harga_bahan = '%s', satuan_bahan = '%s'"
                        + "WHERE id_tr_beli = '%s' AND id_bahan = '%s' ", nama, jenis, harga, satuan, idTr, idBa);
                Connection c = (Connection) Koneksi.configDB();
                Statement s = c.createStatement();
                int result = s.executeUpdate(sql);

                if(result > 0){
                    System.out.println("update berhasil");
                }
                
                c.close(); s.close();
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws ParseException {
        
        
        UpdateDetailBeli t = new UpdateDetailBeli();
        
//        for(Object o : t.getIdTrb()){
//            System.out.println(o);
//        }
        
//        for(Object s : t.getIdBahan("TRB0042")){
//            System.out.println(s);
//            System.out.println(t.getNama(s.toString()));
//            System.out.println(t.getJenis(s.toString()));
//            System.out.println(t.getHarga(s.toString()));
//        }
        
        for(Object o : t.getIdTrb()){
            System.out.println(o);
            t.updateDtrj(o.toString());
        }
        
//        for(int i = 0; i <= 50; i++){
//            String id = String.format("BA%03d", i);
//            String nama = t.getNama(id),
//                   jenis = t.getJenis(id),
//                   harga = t.getHarga(id),
//                   satuan = t.getSatuan(id);
//            if(nama != null){
//                System.out.println(id + " : " + jenis);
//            }
//        }

    }
    
}
