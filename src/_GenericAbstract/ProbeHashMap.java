/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.Entry;
import java.util.ArrayList;

/**
 * Concrete implementation of Map ADT that relies on storing key-value pairs using a hash table with open addressing (linear probing).
 * @author Ryan
 * @param <K>
 * @param <V>
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K, V>
{
    private MapEntry<K,V>[] table;  //fixed array of Entry objects
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null); //sentinel
    
    /**
     * Constructs ProbeHashMap object with default capacity and default prime factor.
     */
    public ProbeHashMap()
    {
        super();
    }
    
    /**
     * Constructs ProbeHashMap object with given capacity and default prime factor.
     * @param capacity 
     */
    public ProbeHashMap(int capacity)
    {
        super(capacity);
    }
    
    /**
     * Constructs ProbeHashMap object with given capacity and given prime factor.
     * @param capacity
     * @param prime 
     */
    public ProbeHashMap(int capacity, int prime)
    {
        super(capacity, prime);
    }
    
    
    //private utilities
    /**
     * Checks if array index position is able to be used to store new Entry.
     * @param j given position in array to check
     * @return true if table[j] is empty or a defunct sentinel
     */
    private boolean isAvailable(int j)
    {
        return ((table[j] ==null) || (table[j] == DEFUNCT));
    }
    
    
    /**
     * Returns index with given key, or -(a+1) indicating given key could be added at index a.
     * @param hashValue
     * @param key
     * @return 
     */
    private int findSlot(int hashValue, K key)
    {
        int available = -1; //no slot available so far
        int j = hashValue;  //index to start searching at
        
        do
        {
            if(isAvailable(j))  //position is empty or defunct
            {
                if(available == -1) //this is the first available position
                {
                    available = j;  //sets a to j
                }
                if(table[j] == null) //index position is empty 
                {
                    break;  //given key can be added at index a
                }    
            }
            else if(table[j].getKey().equals(key))  //key at that index position is equal to key to be added
            {
                return j;   //key exists in map already, so returns index with given key
            }
            
            //keep looking
            j = (j+1) % capacity;   //uses modular arithmetic to cycle to next index position to check
            
        }while(j != hashValue); //stop searching if we return to the start
               
        return -(available + 1);        //given key can be added at index a
    }
    
    
    //protected utilities
    /**
     * Returns value associated with given key in bucket with given hashValue (null if key doesn't exist)
     * @param hashValue
     * @param key
     * @return 
     */
    @Override
    protected V bucketGet(int hashValue, K key)
    {
        int j = findSlot(hashValue, key);   //finds index with given key or -(a+1) indicating key can be added at index a
        
        if(j < 0) //key can be added
        {
            return null;        //no match found
        }
        return table[j].getValue();
    }
    
    /**
     * Associates given key with given value in bucket with given hashValue, returns null if entry is new or old value if entry exists.
     * @param hashValue
     * @param key
     * @param value
     * @return 
     */
    @Override
    protected V bucketPut(int hashValue, K key, V value)
    {
        int j = findSlot(hashValue, key);
        
        if(j >= 0)  //key exists
        {
            return table[j].setValue(value);
        }
        
        int properIndex = -(j + 1); //converts -(a+1) to a
        table[properIndex] = new MapEntry<>(key, value);
        n++;
        return null;
    }
    
    /**
     * Removes entry having given key from bucket with given hashValue (null if key doesn't exist).
     * @param hashValue
     * @param key
     * @return 
     */
    @Override
    protected V bucketRemove(int hashValue, K key)
    {
        int j = findSlot(hashValue, key);
        
        if(j < 0) //key doesn't exist
        {
            return null;    //nothing to remove
        }
        
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }
    
    /**
     * Constructs empty table having length equal to current capacity.
     */
    @Override
    protected void createTable()
    {
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }
    
    //public additional methods
    /**
     * Returns an iterable collection containing all key-value entries in map.
     * @return an iterable collection containing all key-value entries in map
     */
    @Override
    public Iterable<Entry<K,V>> entrySet()
    {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        
        for(int h = 0; h < capacity; h++)
        {
            if(!isAvailable(h))
            {
                buffer.add(table[h]);
            }
        }
        return buffer;
    }

}
