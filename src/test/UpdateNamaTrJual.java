package test;

import com.koneksi.Dbase;
import java.sql.SQLException;

/**
 *
 * @author Achmad Baihaqi
 */
public class UpdateNamaTrJual {
    
    private final Dbase db = new Dbase();
    
    private String[] getIdKaryawan(){
        try{
            db.stat = db.conn.createStatement();
            db.res = db.stat.executeQuery("SELECT id_karyawan FROM transaksi_jual");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
}
