package com.manage;

import com.koneksi.Database;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.StringTokenizer;
import javax.swing.JLabel;
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
public class ChartManager extends Database{
    
    private final Waktu waktu = new Waktu();
    
    private final Color C_MAKANAN = new Color(250,138,16), C_MINUMAN = new Color(64,123,250), 
                        C_ORIGINAL = new Color(32,245,15), C_FLAVOURED = new Color(204,34,245), 
                        C_SNACK = new Color(245,38,41), C_BAHAN_JADI = new Color(1, 143, 104),
                        BG_CHART = Color.WHITE;
    
    private final Font F_MENU = new Font("Ebrima", 1, 20);
    
    public static final int PENDAPATAN = 0, PENGELUARAN = 1, PENJUALAN = 2, PEMBELIAN = 3,MENU = 2, BAHAN = 3,
                            LAST_MONTH = 4;
    
    public void showPieChart(JPanel panel, int type, String title, int bulan, int tahun){
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN : 
                this.showPieChartPenjualan(panel, PENDAPATAN, title, bulan, tahun);
                break;
            case LAST_MONTH : 
                this.showPieChartPenjualan(panel, LAST_MONTH, title, bulan, tahun);
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                this.showPieChartPembelian(panel, title, bulan, tahun);
                break;
        }
    }
    
    private void showPieChartPenjualan(JPanel panel, int type, String title, int bulan, int tahun){
        // mendapatkan data dari database
        double makanan = type == PENDAPATAN ? this.getPieDataPenjualan("Makanan", bulan, tahun) : this.getPieDataPenjualanLastMonth("Makanan");
        double minuman = type == PENDAPATAN ? this.getPieDataPenjualan("Minuman", bulan, tahun) : this.getPieDataPenjualanLastMonth("Minuman");
        double original = type == PENDAPATAN ? this.getPieDataPenjualan("Original Coffee", bulan, tahun) : this.getPieDataPenjualanLastMonth("Original Coffee");
        double flavoured = type == PENDAPATAN ? this.getPieDataPenjualan("Falvoured Coffee", bulan, tahun) : this.getPieDataPenjualanLastMonth("Falvoured Coffee");
        double snack = type == PENDAPATAN ? this.getPieDataPenjualan("Snack", bulan, tahun) : this.getPieDataPenjualanLastMonth("Snack");
        
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
        
        System.out.println("JUMLAH ITEM -> : " + barDataset.getItemCount());
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
        double hewani = this.getPieDataPembelian("Hewani", bulan, tahun)
              ,nabati = this.getPieDataPembelian("Nabati", bulan, tahun)
              ,coffee = this.getPieDataPembelian("Coffee", bulan, tahun)
              ,perasa = this.getPieDataPembelian("Perasa", bulan, tahun)
              ,cairan = this.getPieDataPembelian("Cairan", bulan, tahun),
               bahanjadi = this.getPieDataPembelian("Bahan Jadi", bulan, tahun);
        
        // membuat data set untuk menampung data      
        DefaultPieDataset barDataset = new DefaultPieDataset();
        
        // jika datanya kosong maka data tidak akan ditampilkan didalam chart
        if(hewani > 0){
            barDataset.setValue("Hewani", new Double(hewani));
        }
        if(nabati > 0){
            barDataset.setValue("Nabati", new Double(nabati));
        }
        if(coffee > 0){
            barDataset.setValue("Coffee", new Double(coffee));
        }
        if(perasa > 0){
            barDataset.setValue("Perasa", new Double(perasa));
        }
        if(cairan > 0){
            barDataset.setValue("Cairan", new Double(cairan));
        }
        if(bahanjadi > 0){
            barDataset.setValue("Bahan Jadi", new Double(bahanjadi));
        }

        // membuat chart
        JFreeChart piechart = ChartFactory.createPieChart("Pembelian Produk",barDataset, false,true,false);//explain
        piechart.setTitle(new TextTitle(title, this.F_MENU));

        // merubah warna dari setiap data pada chart
        PiePlot piePlot =(PiePlot) piechart.getPlot();
        if(hewani > 0){
            piePlot.setSectionPaint("Hewani", this.C_MAKANAN);
        }
        if(nabati > 0){
            piePlot.setSectionPaint("Nabati", this.C_MINUMAN);
        }
        if(coffee > 0){
            piePlot.setSectionPaint("Coffee", this.C_ORIGINAL);
        }
        if(perasa > 0){
            piePlot.setSectionPaint("Perasa", this.C_FLAVOURED);
        }
        if(cairan > 0){
            piePlot.setSectionPaint("Cairan", this.C_SNACK);
        }
        if(bahanjadi > 0){
            piePlot.setSectionPaint("Bahan Jadi", this.C_BAHAN_JADI);
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
            super.res = super.stat.executeQuery(sql);
            
            // mengembalikan data total pesanan berdasarkan jenis menu
            if(super.res.next()){
                int total = super.res.getInt("pesanan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
                return total;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private int getPieDataPenjualanLastMonth(String jenis){
        // membuat query
        String sql = "SELECT SUM(dtrj.jumlah) AS pesanan " +
                     "FROM detail_tr_jual AS dtrj " +
                     "JOIN transaksi_jual AS trj " +
                     "ON trj.id_tr_jual = dtrj.id_tr_jual " +
                     "WHERE dtrj.jenis_menu = '"+jenis+"' AND DATE(trj.tanggal) BETWEEN '" + this.waktu.getLastMonthDate() + "' AND '" + this.waktu.getCurrentDate() + "'";
        System.out.println(sql);
        
        try{            
            super.res = super.stat.executeQuery(sql);
            
            // mengembalikan data total pesanan berdasarkan jenis menu
            if(super.res.next()){
                int total = super.res.getInt("pesanan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
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
            super.res = super.stat.executeQuery(sql);
            
            // mengembalikan data total pesanan berdasarkan jenis bahan
            if(super.res.next()){
                int total = super.res.getInt("dipesan");
                System.out.println("TOTAL " + jenis.toUpperCase() + " : " + total);
                return total;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public void showLineChart(JPanel panel, int type, int type2, String id, String title, int bulan, int tahun){
        String valTitle = "";
        // mendapatkan data chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN :
                valTitle = "Jumlah Pendapatan";
                for(int i = 1; i <= waktu.getTotalHari(bulan); i+=2){
                    dataset.setValue(this.getLineDataPenjualan(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
                }                                
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                valTitle = "Jumlah Pengeluaran";
                for(int i = 1; i <= waktu.getTotalHari(bulan); i+=2){
                    dataset.setValue(this.getLineDataPembelian(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
                }                
                break;
            case MENU : 
                valTitle = "Penjualan Menu";
                for(int i = 1; i <= waktu.getTotalHari(bulan); i++){
                    dataset.setValue(this.getLinePenjualanMenu(id, type2, bulan, tahun, i), "Amount", Integer.toString(i));
                }                
                break;
            case BAHAN : 
                valTitle = "Pembelian Bahan";
                for(int i = 1; i <= waktu.getTotalHari(bulan); i++){
                    dataset.setValue(this.getLinePembelianBahan(id, type2, bulan, tahun, i), "Amount", Integer.toString(i));
                }                
                break;
            case LAST_MONTH : 
                valTitle = "Jumlah Pendapatan";
                // mendapatkan tanggal sebulan yang lalu
                StringTokenizer token = new StringTokenizer(waktu.getLastMonthDate(), "-");
                int thn = Integer.parseInt(token.nextToken()),
                    bln = Integer.parseInt(token.nextToken()),
                    tgl = Integer.parseInt(token.nextToken()),
                    ttl = waktu.getTotalHari(bulan);

                // forloop dari tanggal kemarin ke tanggal 1 bulan ini
                System.out.println("hitung hari");
                for (int i = tgl; i <= ttl; i += 2) {
                    dataset.setValue(this.getLineDataPenjualan(bln, thn, i, (i+1)), "Amount", Integer.toString(i-1));
                }
                
                // forloop dari tanggal 1 sampai tanggal saat ini
                if (tgl != ttl) {
                    for (int i = 1; i < tgl; i += 2) {
                        dataset.setValue(this.getLineDataPenjualan(bulan, tahun, i, (i+1)), "Amount", Integer.toString(i));
                    }
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
    
    public void showLineChart(JPanel panel, int type, String title, int bulan, int tahun){
        this.showLineChart(panel, type, 0, "kosoning aja wkwkwk", title, bulan, tahun);
    }
    
    private int getLineDataPenjualan(int bulan, int tahun, int hari1, int hari2){
        try{
            // query untuk mendapatkan total pendapatan selama dua hari
            String sql = String.format(
                    "SELECT SUM(total_harga) AS total " +
                    "FROM transaksi_jual " +
                    "WHERE MONTH(tanggal) = %d AND YEAR(tanggal) = %d " +
                    "AND DAY(tanggal) BETWEEN %d AND %d;", bulan, tahun, hari1, hari2
            );
            System.out.println(sql);
            
            // membuat koneksi
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total berdasaarkan bulan
            if(super.res.next()){
                int total = super.res.getInt("total");
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
                    "WHERE MONTH(tanggal) = %d AND YEAR(tanggal) = %d " +
                    "AND DAY(tanggal) BETWEEN %d AND %d;", bulan, tahun, hari1, hari2
            );
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pengeluaran berdasarkan bulan
            if(super.res.next()){
                int total = super.res.getInt("total");
                return total;
            }
            
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private int getLinePenjualanMenu(String idMenu, int by, int bulan, int tahun, int hari){
        try{
            // query untuk mendapatkan total pendapatan dari menu
            String sql = String.format("SELECT SUM(d.jumlah) AS penjualan, SUM(d.total_harga) AS pendapatan " +
                    "FROM transaksi_jual AS t " +
                    "JOIN detail_tr_jual AS d " +
                    "ON t.id_tr_jual = d.id_tr_jual " +
                    "WHERE d.id_menu = '%s' AND MONTH(tanggal) = %d AND YEAR(tanggal) = %d AND "+ 
                    "DAY(tanggal) = '%d';", idMenu, bulan, tahun, hari
            );
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan
            if(super.res.next()){
                switch(by){
                    case PENJUALAN : return super.res.getInt("penjualan");
                    case PENDAPATAN : return super.res.getInt("pendapatan");
                }
            }
            
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private float getLinePembelianBahan(String idBahan, int by, int bulan, int tahun, int hari){
        try{
            // query untuk mendapatkan total pendapatan dari menu
            String sql = String.format("SELECT SUM(d.jumlah) AS pembelian, SUM(d.total_harga) AS pengeluaran " +
                    "FROM transaksi_beli AS t " +
                    "JOIN detail_tr_beli AS d " +
                    "ON t.id_tr_beli = d.id_tr_beli " +
                    "WHERE d.id_bahan = '%s' AND MONTH(tanggal) = %d AND YEAR(tanggal) = %d AND "+ 
                    "DAY(tanggal) = '%d';", idBahan, bulan, tahun, hari
            );
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan
            if(super.res.next()){
                switch(by){
                    case PEMBELIAN : return super.res.getFloat("pembelian");
                    case PENGELUARAN : return super.res.getInt("pengeluaran");
                }
            }
            
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    public void showBarChart(JPanel panel, int type, int type2, String id, String title, int bulan, int tahun){
        String valTitle = "";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // menambahkan value ke dalam chart
        switch(type){
            // mendapatkan data pendapatan
            case PENDAPATAN :
                valTitle = "Jumlah Pendapatan";
                // mendapatkan data mingguan
                Object[] mingguJual = new Waktu().getMinggu(bulan, tahun);
                String minggu1Jual, minggu2Jual;
                int weekJual = 1;
                // menambahkan data pendapatan ke dataset
                for(Object m : mingguJual){
                    minggu1Jual = m.toString().substring(0, m.toString().indexOf("="));
                    minggu2Jual = m.toString().substring(m.toString().indexOf("=")+1);
                    dataset.setValue(this.getBarDataPenjualan(minggu1Jual, minggu2Jual), "Amount", "Minggu " + weekJual);
                    weekJual++;
                }
                break;
            // mendapatkan data pengeluaran
            case PENGELUARAN :
                valTitle = "Jumlah Pengeluaran";
                // mendapatkan data mingguan
                Object[] mingguBeli = new Waktu().getMinggu(bulan, tahun);
                String minggu1Beli, minggu2Beli;
                int weekBeli = 1;
                // menambahkan data pendapatan ke dataset
                for(Object m : mingguBeli){
                    minggu1Beli = m.toString().substring(0, m.toString().indexOf("="));
                    minggu2Beli = m.toString().substring(m.toString().indexOf("=")+1);
                    dataset.setValue(this.getBarDataPembelian(minggu1Beli, minggu2Beli), "Amount", "Minggu " + weekBeli);
                    weekBeli++;
                }      
                break;
            case MENU :
                valTitle = "Jumlah Penjualan";
                // mendapatkan data mingguan
                Object[] mingguMenu = new Waktu().getMinggu(bulan, tahun);
                String minggu1Menu, minggu2Menu;
                int weekMenu = 1;
                // menambahkan data pendapatan ke dataset
                for(Object m : mingguMenu){
                    minggu1Menu = m.toString().substring(0, m.toString().indexOf("="));
                    minggu2Menu = m.toString().substring(m.toString().indexOf("=")+1);
                    dataset.setValue(this.getBarPenjualanMenu(id, type2, minggu1Menu, minggu2Menu), "Amount", "Minggu " + weekMenu);
                    weekMenu++;
                } 
                break;
            case BAHAN :
                valTitle = "Jumlah Pembelian";
                // mendapatkan data mingguan
                Object[] mingguBahan = new Waktu().getMinggu(bulan, tahun);
                String minggu1Bahan, minggu2Bahan;
                int weekBahan = 1;
                // menambahkan data pendapatan ke dataset
                for(Object m : mingguBahan){
                    minggu1Bahan = m.toString().substring(0, m.toString().indexOf("="));
                    minggu2Bahan = m.toString().substring(m.toString().indexOf("=")+1);
                    dataset.setValue(this.getBarPembelianBahan(id, type2, minggu1Bahan, minggu2Bahan), "Amount", "Minggu " + weekBahan);
                    weekBahan++;
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
    
    public void showBarChart(JPanel panel, int type, String title, int bulan, int tahun){
        this.showBarChart(panel, type, 0, "kosogin aja wkwkwk", title, bulan, tahun);
    }

    private int getBarDataPenjualan(String minggu1, String minggu2){
        try{
            // mendapatkan query untuk mendapatkan total pendapatan mingguan
            String sql = "SELECT SUM(total_harga) AS total "
                       + "FROM transaksi_jual "
                       + "WHERE DATE(tanggal) BETWEEN '"+minggu1+"' AND '"+minggu2+"'";
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan mingguan
            if(super.res.next()){
                int total = super.res.getInt("total");
                return total;
            }
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
   
    private int getBarDataPembelian(String minggu1, String minggu2){
        try{
            // mendapatkan query untuk mendapatkan total pendapatan mingguan
            String sql = "SELECT SUM(total_harga) AS total "
                       + "FROM transaksi_beli "
                       + "WHERE DATE(tanggal) BETWEEN '"+minggu1+"' AND '"+minggu2+"'";
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan mingguan
            if(super.res.next()){
                int total = super.res.getInt("total");
                return total;
            }
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private int getBarPenjualanMenu(String idMenu, int by, String minggu1, String minggu2){
        try{
            // mendapatkan query untuk mendapatkan total pendapatan mingguan
            String sql = String.format("SELECT SUM(d.jumlah) AS penjualan, SUM(d.total_harga) AS pendapatan " +
                         "FROM transaksi_jual AS t " +
                         "JOIN detail_tr_jual AS d " +
                         "ON t.id_tr_jual = d.id_tr_jual " +
                         "WHERE d.id_menu = '%s' AND  DATE(tanggal) BETWEEN '%s'  AND '%s';", idMenu, minggu1, minggu2);
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan mingguan
            if(super.res.next()){
                switch(by){
                    case PENJUALAN : return super.res.getInt("penjualan");
                    case PENDAPATAN : return super.res.getInt("pendapatan");
                }
            }
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
    }
    
    private float getBarPembelianBahan(String idBahan, int by, String minggu1, String minggu2){
        try{
            // mendapatkan query untuk mendapatkan total pendapatan mingguan
            String sql = String.format("SELECT SUM(d.jumlah) AS pembelian, SUM(d.total_harga) AS pengeluaran " +
                         "FROM transaksi_beli AS t " +
                         "JOIN detail_tr_beli AS d " +
                         "ON t.id_tr_beli = d.id_tr_beli " +
                         "WHERE d.id_bahan = '%s' AND  DATE(tanggal) BETWEEN '%s'  AND '%s';", idBahan, minggu1, minggu2);
            System.out.println(sql);
            
            // eksekusi query
            super.res = super.stat.executeQuery(sql);
            
            // mendapatkan data total pendapatan mingguan
            if(super.res.next()){
                switch(by){
                    case PEMBELIAN : return super.res.getFloat("pembelian");
                    case PENGELUARAN : return super.res.getInt("pengeluaran");
                }
            }
        }catch(SQLException ex){
            Message.showException(null, ex);
        }
        return 0;
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
    
    public void setEmptyChart(String text, JPanel pnlChart, JLabel label){
        label.setText(text);
        
        pnlChart.removeAll();
        pnlChart.repaint();
        pnlChart.revalidate();
        
        pnlChart.add(new JPanel().add(label), BorderLayout.CENTER);
        pnlChart.repaint();
        pnlChart.revalidate();
    }
  
}
