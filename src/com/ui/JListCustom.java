package com.ui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Achmad Baihaqi
 */
public class JListCustom extends javax.swing.JList<String>{
    
    private ArrayList<String> list = new ArrayList();
    
    public JListCustom(){}
    
    public JListCustom(String[] values){
        this.list.addAll(Arrays.asList(values));
        this.refreshList();
    }
    
    public Object[] getAllList(){
        return this.list.toArray();
    }
    
    public void setList(String[] values){
        this.list.addAll(Arrays.asList(values));
        this.refreshList();
    }
    
    public final void setList(Object[] values){
        String[] buff = new String[values.length];
        for(int i = 0; i < buff.length; i++){
            buff[i] = values[i].toString();
        }
        this.setList(buff);
        this.refreshList();
    }
    
    public void setList(ArrayList<String> values){
        this.list = values;
        this.refreshList();
    }
    
    public void setList(String oldValue, String newValue){
        this.removeList(oldValue);
        this.addList(newValue);
        this.refreshList();
    }
    
    public void addList(String newList){
        this.list.add(newList);
        this.refreshList();
    }
    
    public void removeList(String valList){
        this.list.remove(valList);
        this.refreshList();
    }
    
    public void removeAllList(){
        Object[] data = list.toArray();
        for(Object d : data){
            this.removeList(d.toString());
        }
        this.refreshList();
    }
    
    public boolean isEmptyList(){
        return this.getAllList().length == 0;
    }
    
    public boolean isExistList(String value){
        for(Object o : this.getAllList()){
            if(o.toString().equalsIgnoreCase(value)) return true;
        }
        return false;
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
