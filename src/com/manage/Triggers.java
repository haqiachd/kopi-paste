package com.manage;

import com.koneksi.Database;
import java.sql.SQLException;

/**
 *
 * @author Achmad Baihaqi
 */
public class Triggers extends Database{
    
    public Triggers(){
        super();
    }
    
    public void updateBahan(){
        try{
            // membuat query untuk mengupdate nama bahan, jenis dan satuan
            String sql = "UPDATE " +
                         "detail_tr_beli dtr, " +
                         "bahan ba " +
                         "SET " +
                         "dtr.nama_bahan = ba.nama_bahan, " +
                         "dtr.jenis_bahan = ba.jenis, " +
                         "dtr.satuan_bahan = ba.satuan " +
                         "WHERE dtr.id_bahan = ba.id_bahan";
                                
            // eksekusi query
            super.stat.executeUpdate(sql);
            super.closeConnection();
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
    }
    
    public void updateMenu(){
        try{
            // membuat query untuk mengupdate nama bahan, jenis dan satuan
            String sql = "UPDATE "
                    + "detail_tr_jual dtr, "
                    + "menu mn "
                    + "SET "
                    + "dtr.nama_menu = mn.nama_menu, "
                    + "dtr.jenis_menu = mn.jenis "
                    + "WHERE mn.id_menu = dtr.id_menu";
                                
            // eksekusi query
            super.stat.executeUpdate(sql);
            super.closeConnection();
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
    }
    
    public void updateKaryawan(){
        try{
            // membuat query untuk mengupdate nama bahan, jenis dan satuan
            String sql = "UPDATE "
                        + "transaksi_jual trj, "
                        + "transaksi_beli trb, "
                        + "karyawan ky "
                        + "SET "
                        + "trb.nama_karyawan = ky.nama_karyawan, "
                        + "trj.nama_karyawan = ky.nama_karyawan "
                        + "WHERE "
                        + "trj.id_karyawan= ky.id_karyawan "
                        + "OR "
                        + "trb.id_tr_beli = ky.id_karyawan";
                                
            // eksekusi query
            super.stat.executeUpdate(sql);
            super.closeConnection();
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
    }
    
}
