package com.manage;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Achmad Baihaqi
 */
public class Laporan {
    
    public void cetakPdf(JTable table, String judul){
        try {
            // set header dan footer
            MessageFormat header = new MessageFormat(judul);
            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");
            // cek tabel kosong atau tidak
            if (table.getRowCount() > 0) {
                // print tabel
                table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            } else {
                Message.showWarning(this, "Tidak ada data didalam tabel yang akan diprint!");
            }
        } catch (PrinterException ex) {
            Message.showException(null, "Tabel gagal diprint", ex);
        }
    }
    
    public void cetakExcel(JTable tabel) {

        // membuka file chooser
        JFileChooser path = new JFileChooser();
        path.showOpenDialog(null);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        try {
            // mendapatkan lokasi dan nama file 
            File f = path.getSelectedFile();
            String location = f.getAbsolutePath();
            String filename = location + "_" + date + ".xls";
            JOptionPane.showMessageDialog(null, filename);
            
            // menyimpan file
            File fp = new File(filename);
            konversiExcel(tabel, fp);

            Message.showInformation(this, "Data Berhasil Di export!");
        } catch (Exception e) {
            Message.showException(this, e);
        }
    }
    public void konversiExcel(javax.swing.JTable table, File file) {
        try {
            // membaca data pada tabel 
            DefaultTableModel Model_Data = (DefaultTableModel) table.getModel();
            try (FileWriter ObjWriter = new FileWriter(file)) {
                for (int i = 0; i < Model_Data.getColumnCount(); i++) {
                    ObjWriter.write(Model_Data.getColumnName(i) + "\t");
                }
                
                ObjWriter.write("\n");
                
                for (int i = 0; i < Model_Data.getRowCount(); i++) {
                    for (int j = 0; j < Model_Data.getColumnCount(); j++) {
                        ObjWriter.write(Model_Data.getValueAt(i, j).toString() + "\t");
                    }
                    ObjWriter.write("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showJasperReport(String jasperFile, String idTransaksi, Connection conn){
        try{
            JasperPrint jprint;
            if(idTransaksi == null){
                jprint = JasperFillManager.fillReport(jasperFile, null, conn);
            }else{
                HashMap map = new HashMap();
                map.put("id_tr_jual", idTransaksi);
                jprint = JasperFillManager.fillReport(jasperFile, map, conn);
            }
//            JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperViewer jview = new JasperViewer(jprint, false);
            jview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jview.setVisible(true);
        }catch(Exception ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    public void cetakLaporanJualHarian(Connection conn){
        this.showJasperReport("src\\report\\LaporanJualHarian.jasper", null, conn);
    }
    
    public void cetakLaporanBeliHarian(Connection conn){
        this.showJasperReport("src\\report\\LaporanBeliHarian.jasper", null, conn);
    }
    
    public void cetakStrukPenjualan(Connection conn, String idTransaksi){
            
        try {
            HashMap parameter = new HashMap();
            parameter.put("id_tr_jual", idTransaksi);

            InputStream file = getClass().getResourceAsStream("/report/CetakStrukJual.jrxml");
            JasperDesign dsn = JRXmlLoader.load(file);
            JasperReport Jupe = JasperCompileManager.compileReport(dsn);
            JasperPrint jp = JasperFillManager.fillReport(Jupe, parameter, conn);

            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(Laporan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
