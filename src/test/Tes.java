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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Achmad Baihaqi
 */
public class Tes {
    
    public String getNama(String idMenu){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT nama_menu FROM menu WHERE id_menu = '"+idMenu+"'");
            
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

    public String getJenis(String idMenu){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT jenis FROM menu WHERE id_menu = '"+idMenu+"'");
            
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
    
    public String getHarga(String idMenu){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT harga FROM menu WHERE id_menu = '"+idMenu+"'");
            
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
    
    public Object[] getIdTrj(){
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM detail_tr_jual");
            ArrayList<String> list = new ArrayList();
            
            while(r.next()){
                list.add(r.getString("id_tr_jual"));
            }
            
            c.close(); s.close(); r.close();
            return list.toArray();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private Object[] getIdMenu(String idTr){
         try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT id_menu FROM detail_tr_jual WHERE id_tr_jual = '"+idTr+"'");
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
            for(Object idMn : this.getIdMenu(idTr)){
                String nama = this.getNama(idMn.toString()), 
                        jenis = this.getJenis(idMn.toString()), 
                        harga = this.getHarga(idMn.toString());
                
                System.out.println(nama);
                System.out.println(jenis);
                System.out.println(harga);
                
                String sql = String.format("UPDATE detail_tr_jual "
                        + "SET nama_menu = '%s', jenis_menu = '%s', harga_menu = '%s' "
                        + "WHERE id_tr_jual = '%s' AND id_menu = '%s' ", nama, jenis, harga, idTr, idMn);
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
        
        
        Tes t = new Tes();
//        System.out.println(t.getIdMenu("TRJ0059"));
        
//        for(Object s : t.getIdMenu("TRJ0059")){
//            System.out.println(s);
//        }
        
        for(Object o : t.getIdTrj()){
            System.out.println(o);
            t.updateDtrj(o.toString());
        }
    }
    
    
//private void cariLaporanHarian(){
//        // reset tabel laporan
////        this.resetTableLpHarian();
////        DefaultTableModel model = (DefaultTableModel) this.tbl_logmasuk.getModel();
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("No");
//        model.addColumn("Tanggal");
//        model.addColumn("Id Log Masuk");
//        model.addColumn("Id Supplier");
//        model.addColumn("Nama Supplier");
//        model.addColumn("Id Produk");
//        model.addColumn("Nama Produk");
//        model.addColumn("Jumlah");
//        model.addColumn("Harga Beli");
//        model.addColumn("Total");
//        tbl_logmasuk.setModel(model);
//        String key = txt_cari.getText(), id ;
//        
//        // membaca semua is tabel laporan
//        for(int i = 0; i < this.carimodel.getRowCount(); i++){
//            // mendapatkan data id, nama dan tanggal
//            id = this.carimodel.getValueAt(i, 2).toString().toLowerCase();
////            nama = this.modelCariLaporan.getValueAt(i, 1).toString().toLowerCase();
////            tanggal = this.modelCariLaporan.getValueAt(i, 5).toString().toLowerCase();
//            
//            // pengecekan id, nama dan tanggal
//            if(id.contains(key)){
//                // jika match maka data ditampilkan kedalam tabel
//                model.addRow(
//                    new Object[]{
//                        id.toUpperCase(), 
//                        this.tbl_logmasuk.getValueAt(i, 1),
//                        this.tbl_logmasuk.getValueAt(i, 2),
//                        this.tbl_logmasuk.getValueAt(i, 3),
//                        this.tbl_logmasuk.getValueAt(i, 4),
//                        this.tbl_logmasuk.getValueAt(i, 5),
//                        this.tbl_logmasuk.getValueAt(i, 6),
//                        this.tbl_logmasuk.getValueAt(i, 7),
//                        this.tbl_logmasuk.getValueAt(i, 8),
//                        this.tbl_logmasuk.getValueAt(i, 9)
//                    }
//                );
//            }
//        }
//        
//        // refresh tabel laporan harian
//        this.tbl_logmasuk.setModel(model);
//        // refresh total data
////        this.showDataLaporanHarian();
//}
}
