package com.manage;

import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
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
    
    private void showJasperReport(String jasperFile, Connection conn){
        try{
            // menyiapkan jasper report
            JasperPrint jprint = JasperFillManager.fillReport(jasperFile, null, conn);
            JasperViewer jview = new JasperViewer(jprint, false);
            
            // menampilkan jasper report
            jview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jview.setVisible(true);
        }catch(Exception ex){
            ex.printStackTrace();
            Message.showException(this, ex);
        }
    }
    
    public void cetakLaporanJualHarian(Connection conn){
        this.showJasperReport("src\\report\\LaporanJualHarian.jasper", conn);
    }
    
    public void cetakLaporanBeliHarian(Connection conn){
        this.showJasperReport("src\\report\\LaporanBeliHarian.jasper", conn);
    }
    
    
    public void cetakStrukPenjualan(Connection conn, boolean diskon, String idTransaksi) {
        try {
            // menyiapkan id transaksi
            HashMap parameter = new HashMap();
            parameter.put("id_tr_jual", idTransaksi);

            // meyiapkan jasper report
            InputStream file;
            if(diskon){
                file = getClass().getResourceAsStream("/report/CetakStrukJualDiskon.jrxml");
            }else{
                file = getClass().getResourceAsStream("/report/CetakStrukJual.jrxml");
            }
            JasperDesign desain = JRXmlLoader.load(file);
            JasperReport report = JasperCompileManager.compileReport(desain);
            JasperPrint print = JasperFillManager.fillReport(report, parameter, conn);

            // membuka jasper report
            JasperViewer jview = new JasperViewer(print, false);
            jview.setTitle("Cetak Struk " + idTransaksi);
            jview.setVisible(true);
            jview.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jview.setLocationRelativeTo(null);
            jview.setFitPageZoomRatio();

            // solved bug jasper report tiba-tiba minimaze
            jview.addWindowListener(new java.awt.event.WindowAdapter() {

                // menutup jasper report saat user menekan tombol close
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("JASPER CLOSING");
                    jview.dispose();
                }

                // menutup jasper report saat user menekan tombol close
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("JASPER CLOSED");
                    jview.dispose();
                }

                // memaksa jasper report untuk tetap aktif (tidak minimaze)
                @Override
                public void windowDeactivated(WindowEvent e) {
                    System.out.println("JASPER ACTIVATED");
                    jview.setVisible(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Message.showException(null, e);
        }
    }
}
