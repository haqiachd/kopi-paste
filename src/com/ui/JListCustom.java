package com.ui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Achmad Baihaqi
 */
public class JListCustom extends javax.swing.JList<String>{
    
    private ArrayList<String> list = new ArrayList();
    
    public JListCustom(){
        
    }
    
    public JListCustom(String values[]){
        this.list.addAll(Arrays.asList(values));
    }
    
    public void setList(String[] values){
        this.list.addAll(Arrays.asList(values));
    }
    
    public void addList(String newList){
        this.list.add(newList);
        this.refreshList();
    }
    
    public void removeList(String valList){
        this.list.remove(valList);
        this.refreshList();
    }
    
    private void refreshList(){
        this.setModel(new javax.swing.AbstractListModel() {
            @Override
            public int getSize() { return list.toArray().length; }
            @Override
            public Object getElementAt(int i) { return list.toArray()[i]; }
        });
    }
    
    public void printList(){
        Object[] data = list.toArray();
        for(Object d : data){
            System.out.println(d);
        }
    }
    
}
