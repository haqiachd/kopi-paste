/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChartExample extends ApplicationFrame {

   public BarChartExample(String title) {
      super(title);

      // membuat dataset
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      dataset.addValue(25.0, "Produk A", "Januari");
      dataset.addValue(34.0, "Produk A", "Februari");
      dataset.addValue(19.0, "Produk A", "Maret");
      dataset.addValue(29.0, "Produk B", "Januari");
      dataset.addValue(42.0, "Produk B", "Februari");
      dataset.addValue(53.0, "Produk B", "Maret");

      // membuat chart dari dataset
      JFreeChart chart = ChartFactory.createBarChart("Penjualan Produk", "Bulan", "Jumlah Penjualan", dataset);

      // menambahkan chart ke panel
      ChartPanel panel = new ChartPanel(chart);
      setContentPane(panel);
   }

   public static void main(String[] args) {
      BarChartExample example = new BarChartExample("Contoh Chart Batang");
      example.pack();
      example.setVisible(true);
   }
}
