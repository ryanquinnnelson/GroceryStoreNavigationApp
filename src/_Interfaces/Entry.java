/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * Interface for a key-value pair.
 * @author Ryan
 * @param <K>
 * @param <V>
 */
public interface Entry<K,V> 
{
    /**
     * Returns the key stored in this entry
     * @return 
     */
    K getKey();
    
    /**
     * Returns the value stored in this entry.
     * @return 
     */
    V getValue();
}
