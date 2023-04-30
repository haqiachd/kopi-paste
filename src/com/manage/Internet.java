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
    
    private final String GMAIL = "kopipaste.app@gmail.com",
                         APP_PASS = "dbobgyhspodcwysz",
                         RECIPIENT = "hakiahmad756@gmail.com";
    
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
    
    public boolean sendGmail(String email, String subject, String body){
        
        if(!this.isConnectInternet()){
            com.manage.Message.showWarning(this, "Tidak terhubung ke internet!");
            return false;
        }
            
        System.out.println("Mengirim email ke " + RECIPIENT);

        // membuat properti object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // membuat session
        Session session = Session.getInstance(props, new Authenticator(){

            @Override 
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(GMAIL, APP_PASS);
            }
        });
        
        // mendebug session
        session.setDebug(true);

        try{
            // membuat email yang akan dikirim
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GMAIL)); // mengatur pengirim email
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // mengatur tipe pesan dan penerima email
            message.setSubject(subject); // mengatur subject dari email
            message.setContent(body, "text/html"); // mengatur isi dari email

            Transport.send(message); // mengirimkan email 
            System.out.println("Email sukses terkirim ke " + RECIPIENT);
            return true;

        }catch (MessagingException ex) {
            com.manage.Message.showException(this, ex);
        }
        return false;
    }
    
    public static void main(String[] args) throws MessagingException {
        
        String html = "<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"	<title>Verifikasi Email</title>\n" +
"	<style>\n" +
"		body {\n" +
"			font-family: Arial, sans-serif;\n" +
"			background-color: #f5f5f5;\n" +
"		}\n" +
"		h1 {\n" +
"			color: lightseagreen;\n" +
"			font-weight: bold;\n" +
"			margin-top: 40px;\n" +
"			margin-bottom: 20px;\n" +
"			text-align: center;\n" +
"		}\n" +
"		.info{\n" +
"			text-align: center;\n" +
"			font-size: 16px;\n" +
"			color: slategrey;\n" +
"			font-weight: bold;\n" +
"		}\n" +
"		.info-email{\n" +
"			text-align: center;\n" +
"			font-size: 16px;\n" +
"			color: black;\n" +
"			font-weight: bold;\n" +
"		}\n" +
"		.warning{\n" +
"			font-size: 16px;\n" +
"			font-weight: bold;\n" +
"			color : orangered;\n" +
"			text-align: center;\n" +
"			margin-bottom: 20px;\n" +
"		}\n" +
"		p {\n" +
"			color: #777;\n" +
"			font-size: 16px;\n" +
"			margin-bottom: 20px;\n" +
"			text-align: center;\n" +
"		}\n" +
"		.code-container {\n" +
"			background-color: #fff;\n" +
"			border-radius: 10px;\n" +
"			box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);\n" +
"			display: inline-block;\n" +
"			margin: 0 auto;\n" +
"			padding: 20px;\n" +
"			text-align: center;\n" +
"		}\n" +
"		.code {\n" +
"			background-color: #f1f1f1;\n" +
"			border: none;\n" +
"			color: dodgerblue; \n" +
"			font-family: monospace;\n" +
"			font-size: 32px;\n" +
"			font-weight: bold;\n" +
"			padding: 15px;\n" +
"			text-align: center;\n" +
"			text-shadow: 1px 1px 1px rgba(255, 255, 255, 0.5);\n" +
"			width: 200px;\n" +
"		}\n" +
"        .footer {\n" +
"        	background-color: black;\n" +
"        	color: #fff;\n" +
"        	padding: 20px;\n" +
"        	display: flex;\n" +
"        	justify-content: space-between;\n" +
"        }\n" +
"        .footer-left {\n" +
"        	display: flex;\n" +
"        	align-items: center;\n" +
"        }\n" +
"        .footer-left img {\n" +
"        	width: 60px;\n" +
"        	margin-right: 20px;\n" +
"        }\n" +
"        .footer-left p {\n" +
"        	margin: 0;\n" +
"        	font-size: 14px;\n" +
"        	color: white;\n" +
"        }\n" +
"	</style>\n" +
"</head>\n" +
"<body>\n" +
"	<div class=\"container\">\n" +
//"		<hr>\n" +
"		<h1>Kode Verifikasi Email</h1>\n" +
"		<hr>\n" +
"		<p class=\"info\">Masukan kode verifikasi dibawah ini pada form Aplikasi</p>\n" +
"        <center>\n" +
"            <div class=\"code-container\">\n" +
"                <div class=\"code\">78392006</div>\n" +
"            </div>\n" +
"        </center>\n" +
"	</div>\n" +
"	<p class=\"warning\">Kode verifikasi akan kadaluarsa dalam waktu 5 menit.</p>\n" +
"	\n" +
"	<p class=\"info-email\">Email ini dikirim secara otomatis mohon jangan balas email ini</p>\n" +
"\n" +
"      <div class=\"footer\">\n" +
"        <div class=\"footer-left\">\n" +
"          <img src=\"https://polije.ac.id/wp-content/uploads/elementor/thumbs/LOGO-POLITEKNIK-NEGERI-JEMBER-200x200-p501e8qsx93hro564g7wmlj5f1d6bn1idluqt46f2o.png\" alt=\"Logo Kopi Paste\" >\n" +
"          <p>Copyright © 2022-2023. Cito Team TIF 22 Polije. All rights reserved</p>\n" +
"        </div>\n" +
"      </div>\n" +
"</body>\n" +
"</html>";
        Internet inet = new Internet();
        inet.sendGmail("e41222905@student.polije.ac.id", "test 3", html);
        
    }
    
}
