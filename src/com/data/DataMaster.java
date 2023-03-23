package com.data;

/**
 *
 * @author Achmad Baihaqi
 */
public interface DataMaster {
    
    void createId();
    
    default void tambahData(){};
    
    default void hapusData(){}
    
}
