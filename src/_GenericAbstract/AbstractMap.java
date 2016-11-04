/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.Entry;
import _Interfaces.Map;
import java.util.Iterator;

/**
 * Abstract class that implements Map interface and provides functionality shared by concrete classes.
 * @author Ryan
 * @param <K> generic key type to be implemented
 * @param <V> generic value type to be implemented
 */
public abstract class AbstractMap<K,V> implements Map<K,V>
{
    //---------------- Nested MapEntry class --------------------//
    /**
     * Concrete implementation of Entry interface to be within a Map implementation.
     * @param <K> generic key type to be implemented
     * @param <V> generic value type to be implemented
     */
    protected static class MapEntry<K,V> implements Entry<K,V>
    {
        private K key;  //key stored at this Entry
        private V value; //value stored at this Entry
       
        /**
         * Constructs Entry object with given key and value.
         * @param key
         * @param value 
         */
        public MapEntry(K key, V value)
        {
           this.key = key;
           this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
        
        //protected utilities
        protected void setKey(K k) {
            this.key = k;
        }

        /**
         * Returns value that was replaced.
         * @param value
         * @return 
         */
        protected V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
       
       
    }//---------------- end of Nested MapEntry class --------------------//
    
    //---------------- Nested KeyIterator class --------------------//
    /**
     * Class defines iterator for all keys stored in map.
     */
    private class KeyIterator implements Iterator<K>
    {
        private Iterator<Entry<K,V>> entries = entrySet().iterator();   //iterator instance containing all key-value entries in map
        
        @Override
        public boolean hasNext()
        {
            return entries.hasNext();
        }
        
        @Override
        public K next()
        {
            return entries.next().getKey();
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    } //---------------- end of Nested KeyIterator class --------------------//
    
    
    //---------------- Nested KeyIterable class --------------------//
    /**
     * Class provides support for keySet() and constructs iterator instance for all keys stored in map.
     */
    private class KeyIterable implements Iterable<K>
    {
        @Override
        public Iterator<K> iterator()
        {
            return new KeyIterator();
        }
    }//---------------- end of Nested KeyIterable class --------------------//
    
    
    
    //---------------- Nested ValueIterator class --------------------//
    /**
     * Class defines iterator for all values stored in map.
     */
    private class ValueIterator implements Iterator<V>
    {
        private Iterator<Entry<K,V>> entries = entrySet().iterator(); //iterator instance containing all key-value entries in map
        
        @Override
        public boolean hasNext()
        {
            return entries.hasNext();
        }
        
        @Override
        public V next()
        {
            return entries.next().getValue();
        }
        
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    } //---------------- end of Nested ValueIterator class --------------------//
    
    
    //---------------- Nested ValueIterable class --------------------//
    /**
     * Class provides support for values() and constructs iterator instance for all values stored in map.
     */
    private class ValueIterable implements Iterable<V>
    {
        @Override
        public Iterator<V> iterator()
        {
            return new ValueIterator();
        }
    } //---------------- end of Nested ValueIterable class --------------------//
    
    
    
    
    //public accessor methods
    /**
     * Tests whether Map contains any entries.
     * @return true if map contains entries, false otherwise
     */
    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    
    //public additional methods
    /**
     * Returns an iterable collection containing all keys stored in map.
     * @return an iterable collection containing all keys stored in map
     */
    @Override
    public Iterable<K> keySet()
    {
        return new KeyIterable();
    }
    
    /**
     * Returns an iterable collection containing all the values of entries stored in map.
     * Includes repetition if multiple keys map to the same value.
     * @return an iterable collection containing all the values of entries stored in map 
     */
    @Override
    public Iterable<V> values()
    {
        return new ValueIterable();
    }
    
    
}
