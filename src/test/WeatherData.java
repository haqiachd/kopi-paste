/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Achmad Baihaqi
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherData {

   public static void main(String[] args) {
      // URL untuk permintaan API cuaca
      String url = "http://api.openweathermap.org/data/2.5/weather?q=Jakarta,ID&appid=apikey.json";

      try {
         // mengirim permintaan HTTP ke API
         URL obj = new URL(url);
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
         con.setRequestMethod("GET");

         // membaca response dari API
         BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
         String inputLine;
         StringBuffer response = new StringBuffer();
         while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
         }
         in.close();

         // menampilkan response dari API
         System.out.println(response.toString());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
