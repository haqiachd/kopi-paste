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
    
    public void showPieChart(JPanel panel, String title, int bulan, int tahun){
        // mendapatkan data dari database
        double makanan = this.getPieData("Makanan", bulan, tahun)
              ,minuman = this.getPieData("Minuman", bulan, tahun)
              ,original = this.getPieData("Original Coffee", bulan, tahun)
              ,flavoured = this.getPieData("Falvoured Coffee", bulan, tahun)
              ,snack = this.getPieData("Snack", bulan, tahun);
        
        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();
        
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

        //create chart
        JFreeChart piechart = ChartFactory.createPieChart("Penjualan Produk",barDataset, false,true,false);//explain
        piechart.setTitle(new TextTitle(title, this.F_MENU));

        //changing pie chart blocks colors
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
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panel.removeAll();
        panel.add(barChartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
    private int getPieData(String jenis, int bulan, int tahun){
        // membuat query
        String sql = "SELECT SUM(dtrj.jumlah) AS pesanan " +
                     "FROM detail_tr_jual AS dtrj " +
                     "JOIN transaksi_jual AS trj " +
                     "ON trj.id_tr_jual = dtrj.id_tr_jual " +
                     "JOIN menu AS mn " +
                     "ON mn.id_menu = dtrj.id_menu\n" +
                     "WHERE mn.jenis = '"+jenis+"' AND MONTH(trj.tanggal) = "+bulan+" AND YEAR(trj.tanggal) = " + tahun;
//        System.out.println(sql);
        
        try{
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            if(r.next()){
                int total = r.getInt("pesanan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
                c.close(); s.close(); r.close();
                return total;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public void showLineChart(JPanel panel, String title, int bulan, int tahun){
//        bulan = bulan + 1;
        //create dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 1; i <= 31; i+=2){
            dataset.setValue(this.getLineData(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
        }
        
        //create chart
        JFreeChart linechart = ChartFactory.createLineChart(title,"Tanggal","Pendapatan", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        linechart.setTitle(new TextTitle(title, this.F_MENU));
        
        //create plot object
         CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(Color.WHITE);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        Color lineChartColor = new Color(255,2,9);
        lineRenderer.setSeriesPaint(0, lineChartColor);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        panel.removeAll();
        panel.add(lineChartPanel, BorderLayout.CENTER);
        panel.validate();
    }
    
    private int getLineData(int bulan, int tahun, int hari1, int hari2){
        try{
            // query untuk mendapatkan total pendapatan selama dua hari
            String sql = String.format(
                    "SELECT SUM(dt.harga) AS total " +
                    "FROM transaksi_jual AS t " +
                    "JOIN detail_tr_jual AS dt " +
                    "ON t.id_tr_jual = dt.id_tr_jual " +
                    "WHERE MONTH(t.tanggal) = %d AND YEAR(t.tanggal) = %d AND DAY(tanggal) " + 
                    "BETWEEN %d AND %d;", bulan, tahun, hari1, hari2
            );
            System.out.println(sql);
            
            // membuat koneksi
            Connection c = (Connection) Koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
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
    
    public void showBarChart(JPanel panel, String title, int bulan, int tahun){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(int i = 1; i <= 4; i++){
            dataset.setValue(new java.util.Random().nextInt(150), "Amount", "Minggu ke-"+Integer.toString(i));
        }
        
        JFreeChart barchart = ChartFactory.createBarChart(title,"Minggu","Jumlah Pendapatan", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        barchart.setTitle(new TextTitle(title, this.F_MENU));
        
        CategoryPlot categoryPlot = barchart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(237,40,40);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(barchart);
        panel.removeAll();
        panel.add(barpChartPanel, BorderLayout.CENTER);
        panel.validate();
    }

    public void showHistogram(JPanel panel){
        
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
