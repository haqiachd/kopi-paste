package com.ui;

import com.media.Gambar;
import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Achmad Baihaqi
 */
public class UIManager {

    static void put(String comboBoxbackground, Color BACKGROUND) {
        
    }
    
    public JLabel[] btns;
    
    public void hoverButton(){
        
        for(JLabel btn : btns){
            btn.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
//                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
//                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
//                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setOpaque(true);
                    btn.setForeground(new Color(0,0,0));
                    btn.setBackground(new Color(96,180,235));
                    btn.setIcon(Gambar.getDarkIcon(btn.getIcon().toString()));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setOpaque(false);
                    btn.setForeground(new Color(255,255,255));
                    btn.setBackground(new Color(0,0,0,0));
                    btn.setIcon(Gambar.getWhiteIcon(btn.getIcon().toString()));
                }
            });
        }
    }
    
    public void updateData(JTextField[] fields){
        for(JTextField field : fields){
            field.addMouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "Information!\n\nTekan tombol tambah atau edit data\nUntuk menambahkan atau mengedit sebuah data!");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
        }
    }
    
}
