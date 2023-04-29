package com.manage;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import java.awt.Desktop;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.swing.JOptionPane;

//import javax.mail.Session;
//import javax.mail.Authenticator;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Transport;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

/**
 *
 * @author Achmad Baihaqi
 */
public class Internet {
    
    private final String GMAIL = "kopipaste.app@gmail.com",
                         RECIPIENT = "hakiahmad756@gmail.com",
                         PASSWORD = "void main () { print('Halo Dunia') }";
    
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
//                    Apps.showException("Terjadi kesalahan yang tidak diketahui", Apps.class.getName(), ex.toString());
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
    
//    public void sendGmail(String subject, String body){
//            
//        System.out.println("Mengirim email ke " + RECIPIENT);
//
//        // membuat properti object
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        // membuat session
//        Session session = Session.getInstance(props, new Authenticator(){
//
//            @Override 
//            protected PasswordAuthentication getPasswordAuthentication(){
//                return new PasswordAuthentication(GMAIL, PASSWORD);
//            }
//
//        });
//        
//        // mendebug session
//        session.setDebug(true);
//
//        try{
//            // membuat email yang akan dikirim
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(GMAIL)); // mengatur pengirim email
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT)); // mengatur tipe pesan dan penerima email
//            message.setSubject(subject); // mengatur subject dari email
//            message.setContent(body, "text/html"); // mengatur isi dari email
//
//            Transport.send(message); // mengirimkan email 
//            System.out.println("Email sukses terkirim ke " + RECIPIENT);
//
//        }catch (MessagingException ex) {
//            com.manage.Message.showException(this, ex);
//        }
//
//    }
    
    public void sendGmail(String subject, String body) throws MessagingException{
        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.mailtrap.io");
//        prop.put("mail.smtp.port", "587");
//        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL, PASSWORD);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));
        message.setRecipients(
        Message.RecipientType.TO, InternetAddress.parse(RECIPIENT));
        message.setSubject("Mail Subject");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }
    
    public static void main(String[] args) throws MessagingException {
        
        Internet inet = new Internet();
        inet.sendGmail("asu", "anjing anda ini");
        
    }
    
}
