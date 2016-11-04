/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.Entry;
import _Interfaces.PriorityQueue;
import java.util.Comparator;

/**
 * Abstract base class to assist implementations of the PriorityQueue interface.
 * @author Ryan
 * @param <K>
 * @param <V>
 */
public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K, V>
{
    //--------------- nested PQEntry class ------------------//
    /**
     * Implements the public Entry interface to construct entries for PriorityQueue.
     * @param <K>
     * @param <V> 
     */
    protected static class PQEntry<K,V> implements Entry<K,V>
    {
        private K k;    //key
        private V v;    //value
        
        public PQEntry(K key, V value)
        {
            k = key;
            v = value;
        }

        //accessor methods
        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() 
        {
            return v;
        }
        
        //utility methods
        protected void setK(K key)        //not exposed as part of Entry interface
        {
            k = key;
        }

        protected void setV(V value)        //not exposed as part of Entry interface
        {
            v = value;
        }    
    }//--------------- end of ested PQEntry class ------------------//
    
    
    //instance variables
    private Comparator<K> comp; //comparator defining the ordering of keys in the priority queue
    
    
    /**
     * Constructs an empty priority queue using the given comparator to order keys.
     * @param c 
     */
    protected AbstractPriorityQueue(Comparator<K> c)
    {
        comp = c;
    }
    
    /**
     * Constructs empty priority queue based on the natural ordering of its keys.
     */
    protected AbstractPriorityQueue()
    {
        this(new DefaultComparator<>());
    }
    
    /**
     * Compares two entries according to key.
     * @param a
     * @param b
     * @return 
     */
    protected int compare(Entry<K,V> a, Entry<K,V> b)
    {
        return comp.compare(a.getKey(), b.getKey());
    }
    
    /**
     * Tests whether a key is valid.
     * @param key
     * @return
     * @throws IllegalArgumentException 
     */
    protected boolean checkKey(K key) throws IllegalArgumentException
    {
        try 
        {
            return (comp.compare(key, key) == 0);   //see if key can be compared to itself
        } 
        catch (ClassCastException cce) 
        {
            throw new IllegalArgumentException("Incompatible key.");
        }
    }
    
    /**
     * Test whether priority queue contains entries.
     * @return 
     */
    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }
}
