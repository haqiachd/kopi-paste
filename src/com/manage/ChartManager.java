package com.manage;

import com.koneksi.Koneksi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Achmad Baihaqi
 * @since 2022-10-12
 */
public class ChartManager {
    
    private final Color C_MAKANAN = new Color(250,138,16), C_MINUMAN = new Color(64,123,250), 
                        C_ORIGINAL = new Color(32,245,15), C_FLAVOURED = new Color(204,34,245), C_SNACK = new Color(245,38,41),
                        BG_CHART = Color.WHITE;
    
    private final Font F_MENU = new Font("Ebrima", 1, 20);
    
    public static final int PENDAPATAN = 0, PENGELUARAN = 1;
    
    public void showPieChart(JPanel panel, int type, String title, int bulan, int tahun){
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN : 
                this.showPieChartPenjualan(panel, title, bulan, tahun);
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                this.showPieChartPembelian(panel, title, bulan, tahun);
                break;
        }
    }
    
    private void showPieChartPenjualan(JPanel panel, String title, int bulan, int tahun){
        // mendapatkan data dari database
        double makanan = this.getPieDataPenjualan("Makanan", bulan, tahun)
              ,minuman = this.getPieDataPenjualan("Minuman", bulan, tahun)
              ,original = this.getPieDataPenjualan("Original Coffee", bulan, tahun)
              ,flavoured = this.getPieDataPenjualan("Falvoured Coffee", bulan, tahun)
              ,snack = this.getPieDataPenjualan("Snack", bulan, tahun);
        
        // membuat data set untuk menampung data        
        DefaultPieDataset barDataset = new DefaultPieDataset();
        
        // jika datanya kosong maka data tidak akan ditampilkan didalam chart
        if(makanan > 0){
            barDataset.setValue( "Makanan", new Double(makanan));
        }
        if(minuman > 0){
            barDataset.setValue( "Minuman", new Double(minuman));
        }
        if(original > 0){
            barDataset.setValue("Original Coffee", new Double(original));
        }
        if(flavoured > 0){
            barDataset.setValue("Flavoured Coffee", new Double(flavoured));
        }
        if(snack > 0){
            barDataset.setValue("Snack", new Double(snack));
        }

        // membuat chart
        JFreeChart piechart = ChartFactory.createPieChart("Penjualan Produk",barDataset, false,true,false);//explain
        piechart.setTitle(new TextTitle(title, this.F_MENU));

        // merubah warna dari setiap data pada chart
        PiePlot piePlot =(PiePlot) piechart.getPlot();
        if(makanan > 0){
            piePlot.setSectionPaint("Makanan", this.C_MAKANAN);
        }
        if(minuman > 0){
            piePlot.setSectionPaint("Minuman", this.C_MINUMAN);
        }
        if(original > 0){
            piePlot.setSectionPaint("Original Coffee", this.C_ORIGINAL);
        }
        if(flavoured > 0){
            piePlot.setSectionPaint("Flavoured Coffee", this.C_FLAVOURED);
        }
        if(snack > 0){
            piePlot.setSectionPaint("Snack", this.C_SNACK);
        }
        piePlot.setBackgroundPaint(this.BG_CHART);
        
        // menampilkan chart ke panel
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panel.removeAll();
        panel.add(barChartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
    private void showPieChartPembelian(JPanel panel, String title, int bulan, int tahun){
        // mendapatkan data dari database
        double makanan = this.getPieDataPembelian("Hewani", bulan, tahun)
              ,minuman = this.getPieDataPembelian("Nabati", bulan, tahun)
              ,original = this.getPieDataPembelian("Coffee", bulan, tahun)
              ,flavoured = this.getPieDataPembelian("Perasa", bulan, tahun)
              ,snack = this.getPieDataPembelian("Cairan", bulan, tahun);
        
        // membuat data set untuk menampung data      
        DefaultPieDataset barDataset = new DefaultPieDataset();
        
        // jika datanya kosong maka data tidak akan ditampilkan didalam chart
        if(makanan > 0){
            barDataset.setValue( "Hewani", new Double(makanan));
        }
        if(minuman > 0){
            barDataset.setValue( "Nabati", new Double(minuman));
        }
        if(original > 0){
            barDataset.setValue("Coffee", new Double(original));
        }
        if(flavoured > 0){
            barDataset.setValue("Perasa", new Double(flavoured));
        }
        if(snack > 0){
            barDataset.setValue("Cairan", new Double(snack));
        }

        // membuat chart
        JFreeChart piechart = ChartFactory.createPieChart("Pembelian Produk",barDataset, false,true,false);//explain
        piechart.setTitle(new TextTitle(title, this.F_MENU));

        // merubah warna dari setiap data pada chart
        PiePlot piePlot =(PiePlot) piechart.getPlot();
        if(makanan > 0){
            piePlot.setSectionPaint("Hewani", this.C_MAKANAN);
        }
        if(minuman > 0){
            piePlot.setSectionPaint("Nabati", this.C_MINUMAN);
        }
        if(original > 0){
            piePlot.setSectionPaint("Coffee", this.C_ORIGINAL);
        }
        if(flavoured > 0){
            piePlot.setSectionPaint("Perasa", this.C_FLAVOURED);
        }
        if(snack > 0){
            piePlot.setSectionPaint("Cairan", this.C_SNACK);
        }
        piePlot.setBackgroundPaint(this.BG_CHART);
        
        // menampilkan chart ke panel
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panel.removeAll();
        panel.add(barChartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
    private int getPieDataPenjualan(String jenis, int bulan, int tahun){
        // membuat query
        String sql = "SELECT SUM(dtrj.jumlah) AS pesanan " +
                     "FROM detail_tr_jual AS dtrj " +
                     "JOIN transaksi_jual AS trj " +
                     "ON trj.id_tr_jual = dtrj.id_tr_jual " +
                     "WHERE dtrj.jenis_menu = '"+jenis+"' AND MONTH(trj.tanggal) = "+bulan+" AND YEAR(trj.tanggal) = " + tahun;
        System.out.println(sql);
        
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // mengembalikan data total pesanan berdasarkan jenis menu
            if(r.next()){
                int total = r.getInt("pesanan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
                c.close(); s.close(); r.close();
                return total;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private int getPieDataPembelian(String jenis, int bulan, int tahun){
        // membuat query
        String sql = "SELECT SUM(dtrb.jumlah) AS dipesan " +
                     "FROM detail_tr_beli AS dtrb " +
                     "JOIN transaksi_beli AS trb " +
                     "ON trb.id_tr_beli = dtrb.id_tr_beli " +
                     "WHERE dtrb.jenis_bahan = '"+jenis+"' AND MONTH(trb.tanggal) = "+bulan+" AND YEAR(trb.tanggal) = " + tahun;
        System.out.println(sql);
        
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // mengembalikan data total pesanan berdasarkan jenis bahan
            if(r.next()){
                int total = r.getInt("dipesan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
                c.close(); s.close(); r.close();
                return total;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public void showLineChart(JPanel panel, int type, String title, int bulan, int tahun){
        String valTitle = "";
        // mendapatkan data chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN :
                valTitle = "Jumlah Pendapatan";
                for(int i = 1; i <= 31; i+=2){
                    dataset.setValue(this.getLineDataPenjualan(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
                }                                
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                valTitle = "Jumlah Pengeluaran";
                for(int i = 1; i <= 31; i+=2){
                    dataset.setValue(this.getLineDataPembelian(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
                }                
                break;
        }
        
        // membuat line chart
        JFreeChart linechart = ChartFactory.createLineChart(title, "Tanggal", valTitle, 
                dataset, PlotOrientation.VERTICAL, false, true, false);
        linechart.setTitle(new TextTitle(title, this.F_MENU));
        
        // mengatur warna background dan grid pada chart
        CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        // mengatur warna line pada chart
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(255,2,9);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
        // menampilkan chart ke panel
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        panel.removeAll();
        panel.add(lineChartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
    private int getLineDataPenjualan(int bulan, int tahun, int hari1, int hari2){
        try{
            // query untuk mendapatkan total pendapatan selama dua hari
            String sql = String.format(
                    "SELECT SUM(total_harga) AS total " +
                    "FROM transaksi_jual " +
                    "WHERE MONTH(tanggal) = %d AND YEAR(tanggal) = %d \n" +
                    "AND DAY(tanggal) BETWEEN %d AND %d;", bulan, tahun, hari1, hari2
            );
            System.out.println(sql);
            
            // membuat koneksi
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // mendapatkan data total berdasaarkan bulan
            if(r.next()){
                int total = r.getInt("total");
                c.close(); r.close(); s.close();
                return total;
            }
            
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private int getLineDataPembelian(int bulan, int tahun, int hari1, int hari2){
        try{
            // query untuk mendapatkan total pendapatan selama dua hari
            String sql = String.format(
                    "SELECT SUM(total_harga) AS total " +
                    "FROM transaksi_beli " +
                    "WHERE MONTH(tanggal) = %d AND YEAR(tanggal) = %d \n" +
                    "AND DAY(tanggal) BETWEEN %d AND %d;", bulan, tahun, hari1, hari2
            );
            System.out.println(sql);
            
            // membuat koneksi
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // mendapatkan data total pengeluaran berdasarkan bulan
            if(r.next()){
                int total = r.getInt("total");
                c.close(); r.close(); s.close();
                return total;
            }
            
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    public void showBarChart(JPanel panel, int type, String title, int bulan, int tahun){
        String valTitle = "";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN : 
                valTitle = "Jumlah Pendapatan";
                for(int i = 1; i <= 4; i++){
                    dataset.setValue(new java.util.Random().nextInt(150), "Amount", "Minggu ke-"+Integer.toString(i));
                }         
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                valTitle = "Jumlah Pengeluaran";
                for(int i = 1; i <= 4; i++){
                    dataset.setValue(new java.util.Random().nextInt(150), "Amount", "Minggu ke-"+Integer.toString(i));
                }             
                break;
        }
        
        // membuat bar 
        JFreeChart barchart = ChartFactory.createBarChart(title,"Minggu",valTitle, 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        barchart.setTitle(new TextTitle(title, this.F_MENU));
        
        // mengatur warna pada bar dan background chart
        CategoryPlot categoryPlot = barchart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(237,40,40);
        renderer.setSeriesPaint(0, clr3);
        
        // menampilkan chart ke panel
        ChartPanel barpChartPanel = new ChartPanel(barchart);
        panel.removeAll();
        panel.add(barpChartPanel, BorderLayout.CENTER);
        panel.validate();
    }

    @Deprecated
    public void showHistogram(JPanel panel, int type, String title){
        
         double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
                          };
 
 
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("key", values, 20);
        
         JFreeChart chart = ChartFactory.createHistogram("JFreeChart Histogram","Data", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
            XYPlot plot= chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);

        
        
        ChartPanel barpChartPanel2 = new ChartPanel(chart);
        panel.removeAll();
        panel.add(barpChartPanel2, BorderLayout.CENTER);
        panel.validate();
    }
    
}
