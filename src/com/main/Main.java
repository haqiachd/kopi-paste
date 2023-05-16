package com.main;

import com.manage.Waktu;

/**
 *
 * @author Achmad Baihaqi
 */
public class Main {
    
    public static void main(String[] args) {
        Waktu.updateWaktu();
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                // membuka window pilih tipe login
                new com.window.ChooseLoginType().setVisible(true);
            }
        });
    }
}
