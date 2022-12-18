/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.manage.FileManager;
import com.manage.Waktu;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Achmad Baihaqi
 */
public class BackupDb {

    public static void Backupdbtosql() {
        try {

            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = BackupDb.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            /*NOTE: Creating Database Constraints*/
            String dbName = "kopi_paste";
            String dbUser = "root";
            String dbPass = "";

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "\\backup";

            /*NOTE: Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();
            
            // membuat penyimpanan backup
            String savePath = "\"" + jarDir + "\\backup\\" + "fasd.sql\"";

            // membuat command untuk membackup database dengan mysqldump
            String cmdCommand = "C:\\xampp\\mysql\\bin\\mysqldump -u "+dbUser+ " " + dbName +" -r" + savePath;
            
            System.out.println(cmdCommand);
            // eksekusi command untuk backup database
            Process runtimeProcess = Runtime.getRuntime().exec(cmdCommand);
            int processComplete = runtimeProcess.waitFor();

            // cek apakah proses backup berhasil
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
    }
    
    private final FileManager fm = new FileManager();
    
    private final Waktu waktu = new Waktu();
    
    public void backupDB() {
        try {
            // membuka file chooser untuk menginputkan nama file
            String savePath = fm.saveFile(null) + "-" + waktu.getCurrentDate() + ".sql";
            System.out.println("Save Path : " + savePath);
            
            // membuat command untuk membackup database dengan mysqldump
            String cmdCommand = "C:\\xampp\\mysql\\bin\\mysqldump -u root kopi_paste -r \"" + savePath + "\"";
            System.out.println(cmdCommand);
            
            // eksekusi command untuk backup database
            Process runtimeProcess = Runtime.getRuntime().exec(cmdCommand);
            int processComplete = runtimeProcess.waitFor();

            // cek apakah proses backup berhasil
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        Waktu.updateWaktu();
        new BackupDb().backupDB();
    }
}

// mysqldump -u root appbaju -r "C:\Users\Achmad Baihaqi\Documents\NetBeansProjects\Kopi Paste\build\backup\backup1.sql"