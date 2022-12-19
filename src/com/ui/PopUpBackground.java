package com.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Achmad Baihaqi
 */
public class PopUpBackground extends JFrame{
 
    private final JLabel label = new JLabel();
    
    public PopUpBackground(){
        this.initComponents();
    }
    
    private void initComponents(){
        setUndecorated(true);
        setBackground(new Color(0,0,20,200));setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
        
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
        new PopUpBackground().setVisible(true);
    }
    
}
