package test;

import com.koneksi.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class TestStokMenu {
    
    private int getStokBahan(String idBahan){
        try{
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT stok FROM bahan WHERE id_bahan = '"+idBahan+"'");
            
            if(res.next()){
                int val = res.getInt("stok");
                conn.close();
                stat.close();
                res.close();
                return val;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return -1;
    }
    
    private int getQuantity(String idMenu, String idBahan){
        try{
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT quantity FROM detail_menu WHERE id_menu = '"+idMenu+"' AND id_bahan = '"+idBahan+"'");
            
            if(res.next()){
                int val = res.getInt("quantity");
                conn.close();
                stat.close();
                res.close();
                return val;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return -1;
    }
    
    private int getJumlahBahan(String idMenu){
        try{
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT COUNT(id_bahan) AS total FROM detail_menu WHERE id_menu = '"+idMenu+"'");
            
            if(res.next()){
                int val = res.getInt("total");
                conn.close();
                stat.close();
                res.close();
                return val;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return -1;
    }
    
    private int hitungStokMenu(String idMenu){
        try{
            if(this.getJumlahBahan(idMenu) < 1){
                return 0;
            }
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT id_bahan FROM detail_menu WHERE id_menu = '"+idMenu+"'");
            int[] qCek = new int[this.getJumlahBahan(idMenu)];
            int stokBahan, quantity, index = 0;
            String idBahan;
            
            while(res.next()){
                idBahan = res.getString("id_bahan");
                stokBahan = this.getStokBahan(idBahan);
                quantity = this.getQuantity(idMenu, idBahan);
                
                if(quantity == 0){
                    return 0;
                }
                
                qCek[index] = (int)stokBahan / quantity;
                System.out.println(idBahan + " : " + (int)stokBahan / quantity);
                index++;
            }
            
            conn.close();
            stat.close();
            res.close();
            
            Arrays.sort(qCek);
            return qCek[0];
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
        }
        return -1;
    }
    
    public static void main(String[] args) {
        TestStokMenu bh = new TestStokMenu();
//        System.out.println(bh.getStokBahan("BA001"));
//        System.out.println(bh.getQuantity("MN001", "BA025"));
//        System.out.println(bh.getJumlahBahan("MN001"));
        System.out.println(bh.hitungStokMenu("MN018"));
    }
    
}
