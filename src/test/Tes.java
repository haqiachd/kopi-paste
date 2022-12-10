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
public class Tes {
    
    public static void main(String[] args) {
        
        int a = 0;
        for (int i = 0; i < 10; i++) {
            a = a += i;
            System.out.println(a);
        }
        
        System.out.println(String.valueOf(a));
        
    }
    
}
