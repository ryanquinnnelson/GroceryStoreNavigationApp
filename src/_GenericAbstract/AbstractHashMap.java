/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;

import _Interfaces.Entry;
import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract class that implements Map interface and provides functionality common to hash table implementations.
 * @author Ryan
 * @param <K> generic key type to be implemented
 * @param <V> generic value type to be implemented
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V>
{
    protected int n = 0;        //number of entries in dictionary
    protected int capacity;     //length of the table
    private int prime;          //prime factor - length of bucket array
    private long scale;         //scale factor for hash function
    private long shift;         //shift factor for hash function
    
    /**
     * Constructs AbstractHashMap object with given capacity and given prime factor.
     * @param capacity
     * @param prime 
     */
    public AbstractHashMap(int capacity, int prime)
    {
        this.prime = prime;
        this.capacity = capacity;
        
        Random rand = new Random();
        scale = rand.nextInt( prime - 1) + 1;   //scale assigned pseudo-random value between [1, prime factor)
        shift = rand.nextInt(prime);    //shift assigned pseudo-random value between [0, prime factor)
        
        createTable();  
    }
    
    /**
     * Constructs AbstractHashMap object with given capacity and default prime factor.
     * @param capacity 
     */
    public AbstractHashMap(int capacity)
    {
        this(capacity, 109345121);   //default prime
    }
    
    /**
     * Constructs AbstractHashMap object with default capacity and default prime factor.
     */
    public AbstractHashMap()
    {
        this(17);
    }
    
    
    
    //public accessor methods
    /**
     * Returns the number of entries in Map.
     * @return the number of entries in Map
     */
    @Override
    public int size()
    {
        return n;
    }
    
    /**
     * Returns the value associated with given key (null if Entry doesn't exist).
     * @param key to return value of
     * @return the value associated with given key (null if Entry doesn't exist)
     */
    @Override
    public V get(K key)
    {
        return bucketGet(hashValue(key), key);
    }
    
    
    
    //public update methods
    /**
     * Removes from map the Entry with key equal to given key and returns its value.
     * If map has no such key, returns null.
     * @param key key identifying entry to remove
     * @return value of Entry removed from map, or null if map contains no such key.
     */
    @Override
    public V remove(K key)
    {
        return bucketRemove(hashValue(key), key);
    }
    
    /**
     * If map does not have an Entry with key equal to given key, adds Entry (k,v) to map and returns null.
     * If map does have an existing Entry with that key, replaces the value stored 
     * at that entry with given value and returns the value that was replaced.
     * @param key identifying key for Entry
     * @param value value stored at Entry
     * @return null if key was new; returns the value replaced if key was existing.
     */
    @Override
    public V put(K key, V value)
    {
        V answer = bucketPut(hashValue(key), key, value);
        
        if(n > capacity / 2)    //keep load factor less than or equal to 0.5
        {
            resize(2 * capacity - 1); //approximately doubles size of map
        }
        
        return answer;
    }
    
    
    //private utilties
    /**
     * Computes and returns the hash value for given key using default hash code and MAD compression function.
     * Hash value is the index position Entry will be stored at in Map, given its key.
     * Hash value is computed by multiplying a hash code by a compression function.
     * @param key key to determine hash value for
     * @return integer value representing the appropriate index to store given key in map
     */
    private int hashValue(K key)
    {
        // MAD (Multiply-Add-and-Divide) compression function
        // integer hash value = [(ai + b) mod p] mod N
        // takes absolute value of this result
        return (int) ((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
    }
    
    /**
     * Resizes the map to increase capacity and decrease load factor.
     * @param newCapacity capacity of new map
     */
    private void resize(int newCapacity)
    {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);  //constructs ArrayList with length equal to number of entries in map
        
        for(Entry<K,V> e : entrySet())  //iterates through every Entry in Map
        {
            buffer.add(e);  //adds to ArrayList
        }
        capacity = newCapacity;
        createTable();
        n = 0;      //resets number of entries in map
        
        for(Entry<K,V> e : buffer)  //iterates through every Entry in buffer
        {
            put(e.getKey(), e.getValue());  //adds Entry to map
        }
    }    
    
    /**
     * Constructs an initially empty table having size equal to designated capacity field.
     */
    protected abstract void createTable();
    
    /**
     * Returns the value associated with given AbstractHashMap (null if Entry doesn't exist).
     * @param hashCode designates which position in bucket array to check
     * @param key to return value of
     * @return the value associated with given AbstractHashMap (null if Entry doesn't exist)
     */
    protected abstract V bucketGet(int hashCode, K key);
    
    /**
     * If map does not have an Entry with AbstractHashMap equal to given, adds Entry (k,v) to map and returns null.
     * If map does have an existing Entry with that AbstractHashMap, replaces the value stored 
 at that entry with given value and returns the value that was replaced.
     * @param hashCode designates which position in bucket array to check
     * @param key key to store in map
     * @param value value to store in map
     * @return null if AbstractHashMap was new; returns the value replaced if AbstractHashMap was existing
     */
    protected abstract V bucketPut(int hashCode, K key , V value);
    
    /**
     * Removes from map the Entry with AbstractHashMap equal to given and returns its value.
     * If map has no such AbstractHashMap, returns null.
     * @param hashCode designates which position in bucket array to check
     * @param key key identifying entry to remove
     * @return  value of Entry removed from map, or null if map contains no such AbstractHashMap
     */
    protected abstract V bucketRemove(int hashCode, K key);
}
