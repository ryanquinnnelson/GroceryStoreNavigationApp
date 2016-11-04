/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;



/**
 * Extends the priority queue ADT with additional functionality.
 * @author Ryan
 * @param <K> key type to be implemented
 * @param <V> value type to be implemented
 */
public interface AdaptablePriorityQueue<K,V> extends PriorityQueue<K, V>
{
    /**
     * Removes given Entry from the priority queue.
     * @param e Entry to be removed
     * @throws IllegalArgumentException if given Entry is invalid
     */
    void remove(Entry<K,V> e) throws IllegalArgumentException;
    
    /**
     * Replaces the key of given Entry with given key.
     * @param e Entry to replace key at
     * @param key key value to add to Entry
     * @throws IllegalArgumentException if given Entry is invalid
     */
    void replaceKey(Entry<K,V> e, K key) throws IllegalArgumentException;
    
    /**
     * Replaces the value of given Entry with given value.
     * @param e Entry to replace value at
     * @param value value to add to Entry
     * @throws IllegalArgumentException if given Entry is invalid
     */
    void replaceValue(Entry<K,V> e, V value) throws IllegalArgumentException;
            
}
