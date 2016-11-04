/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;


/**
 * Stores a collection of values based on a uniquely identifying search key for each.
 * @author Ryan
 * @param <K> generic key type to be implemented
 * @param <V> generic value type to be implemented
 */
public interface Map<K,V> 
{
    /**
     * Returns the number of entries in Map.
     * @return the number of entries in Map
     */
    int size();
    
    /**
     * Tests whether Map contains any entries.
     * @return true if map contains entries, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Returns the value associated with given key (null if Entry doesn't exist).
     * @param key to return value of
     * @return the value associated with given key (null if Entry doesn't exist)
     */
    V get(K key);
    
    /**
     * If map does not have an Entry with key equal to given key, adds Entry (k,v) to map and returns null.
     * If map does have an existing Entry with that key, replaces the value stored 
     * at that entry with given value and returns the value that was replaced.
     * @param key identifying key for Entry
     * @param value value stored at Entry
     * @return null if key was new; returns the value replaced if key was existing.
     */
    V put(K key, V value);
    
    /**
     * Removes from map the Entry with key equal to given key and returns its value.
     * If map has no such key, returns null.
     * @param key key identifying entry to remove
     * @return value of Entry removed from map, or null if map contains no such key.
     */
    V remove(K key);
    
    /**
     * Returns an iterable collection containing all keys stored in map.
     * @return an iterable collection containing all keys stored in map
     */
    Iterable<K> keySet();
    
    /**
     * Returns an iterable collection containing all the values of entries stored in map.
     * Includes repetition if multiple keys map to the same value.
     * @return an iterable collection containing all the values of entries stored in map
     */
    Iterable<V> values();
    
    /**
     * Returns an iterable collection containing all key-value entries in map.
     * @return an iterable collection containing all key-value entries in map
     */
    Iterable<Entry<K,V>> entrySet();
    
  
}
