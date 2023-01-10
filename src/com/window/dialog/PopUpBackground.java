package com.window.dialog;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Achmad Baihaqi
 */
public class PopUpBackground extends JFrame{
 
    public PopUpBackground(){
        this.initComponents();
    }
    
    JLabel label = new JLabel();
    
    private void initComponents(){
        setUndecorated(true);
        setBackground(new Color(0,0,20,200));
        setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
        label.addMouseListener(new java.awt.event.MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
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
        
        add(label);
    }
    
    public static void main(String[] args) {
//        TambahDataBahan d = new TambahDataBahan(null, true);
//        d.setVisible(true);
        new PopUpBackground().setVisible(true);
    }
    
}
