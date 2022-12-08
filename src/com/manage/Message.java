package com.manage;

import com.media.Audio;

import java.awt.Component;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * @author Achmad Baihaqi
 * @since 2021-06-12
 */
public class Message {
    

    public static void showException(Component parent, String msg, Throwable ex){
        Audio.play(Audio.SOUND_ERROR);
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showException(Object obj, Throwable ex){
        Audio.play(Audio.SOUND_ERROR);
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    
 
    public static void showException(Component parent, Throwable ex){
        Message.showException(parent, ex.getMessage(), ex);
    }
    
    public static void showWarning(Object obj, String msg){
        // menampilkan dialog pesan ke layar
        Audio.play(Audio.SOUND_WARNING);
        JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void showWarning(Component parent, String msg){
        // menampilkan dialog pesan ke layar
        Audio.play(Audio.SOUND_WARNING);
        JOptionPane.showMessageDialog(parent, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public static void showInformation(Object obj, String msg){
        // menampilkan dialog pesan ke layar
        Audio.play(Audio.SOUND_INFO);
        JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showInformation(Component parent, String msg){
        // menampilkan dialog pesan ke layar
        Audio.play(Audio.SOUND_INFO);
        JOptionPane.showMessageDialog(parent, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        Message.showWarning(null, "");
    }
}
