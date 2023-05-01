package com.manage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Achmad Baihaqi
 */
public class Internet {
    
    // gmail dan password
    private final String GMAIL = "kopipaste.app@gmail.com",
                         APP_PASS = "qgkypjxzdqhepips";
    
    /**
     * Digunakan untuk mengecek apakah user tersambung ke inernet atau tidak
     * 
     * @return Jika user tersambung te Internet maka akan mengembalikan nilai <b>True</b>. 
     *         Tapi jika user tidak tersambung ke Internet maka akan mengembalikan nilai <b>False</b>
     */
    public boolean isConnectInternet(){
        Socket s = new Socket();
        InetSocketAddress inet = new InetSocketAddress("www.google.com", 80);
        
            try{
                s.connect(inet, 1000);
                return true;
            }catch(Exception ex){
                return false;
            }finally{
                try{
                    s.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
    }
    
    /**
     * digunakan untuk membuka sebuah link
     * 
     * @param link 
     * @throws java.io.IOException 
     * @throws java.net.URISyntaxException 
     */
    public void openLink(final String link) throws IOException, URISyntaxException {
        if(this.isConnectInternet()){
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(link));
            JOptionPane.showMessageDialog(null, "Sedang membuka link yang dituju!", "Pesan", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Tidak terhubung ke internet!!");
        }
    }
    
    public boolean sendGmail(String penerima, String subjek, String pesan){
        
        // jika tidak ada koneksi internet
        if(!this.isConnectInternet()){
            com.manage.Message.showWarning(this, "Tidak terhubung ke internet!");
            return false;
        }
            
        System.out.println("Mengirim email ke " + penerima);

        // konfigurasi smtp
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        // membuat session gmail
        Session session = Session.getInstance(props, new Authenticator(){

            @Override 
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(GMAIL, APP_PASS);
            }
        });
        
        // mendebug session
        session.setDebug(true);

        try{
            // membuat pesan email yang akan dikirim
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GMAIL)); // mengatur pengirim email
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(penerima)); // mengatur tipe pesan dan penerima email
            message.setSubject(subjek); // mengatur subject dari email
            message.setContent(pesan, "text/html"); // mengatur isi dari email

            // mengirimkan email
            Transport.send(message);
            System.out.println("Email sukses terkirim ke " + penerima);
            return true;

        }catch (MessagingException ex) {
            ex.printStackTrace();
            com.manage.Message.showException(this, ex);
        }
        return false;
    }
}
