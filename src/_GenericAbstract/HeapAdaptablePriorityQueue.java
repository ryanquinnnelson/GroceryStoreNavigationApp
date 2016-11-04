/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.AdaptablePriorityQueue;
import _Interfaces.Entry;
import java.util.Comparator;

/**
 * Implementation of an adaptable priority queue using an array-based heap.
 * @author Ryan
 * @param <K>
 * @param <V>
 */
public class HeapAdaptablePriorityQueue<K,V> extends HeapPriorityQueue<K,V> implements AdaptablePriorityQueue<K,V>
{
    //--------------- nested AdaptablePQEntry class ------------------//
    /**
     * Extension of PQEntry class to include location information.
     * @param <K> generic key type to be implemented
     * @param <V> generic value type to be implemented
     */
    protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V>
    {
        private int index;      //entry's current index within the heap
        
        /**
         * Constructs Entry object of given key, value, and current index within the heap.
         * @param key key stored at Entry
         * @param value value stored at Entry
         * @param j current index within the heap
         */
        public AdaptablePQEntry(K key, V value, int j)
        {
            super(key, value);
            index = j;
        }

        //accessor methods
        /**
         * Returns the current index position of the Entry object within the heap.
         * @return the current index position of the Entry object within the heap
         */
        public int getIndex()
        {
            return index;
        }
        
        //update methods
        /**
         * Sets the current index position of the Entry object within the heap
         * @param j represents index value of Entry object within the heap
         */
        public void setIndex(int j)
        {
            index = j;
        }
    }//--------------- end of nested AdaptablePQEntry class ------------------//

    /**
     * Constructs empty adaptable priority queue using natural ordering of keys.
     */
    public HeapAdaptablePriorityQueue()
    {
        super();
    }
    
    /**
     * Constructs empty adaptable priority queue using given comparator to order keys.
     * @param comp 
     */
    public HeapAdaptablePriorityQueue(Comparator<K> comp)
    {
        super(comp);
    }
    
    //protected utilties
    /**
     * Checks an entry to ensure that it is location-aware.
     * @param entry Entry to validate
     * @return location-aware entry
     * @throws IllegalArgumentException if Entry is invalid or not location-aware
     */
    protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException
    {
        if(!(entry instanceof AdaptablePQEntry))
        {
            throw new IllegalArgumentException("Invalid entry.");
        }
        
        AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry;     //safe type cast
        
        int j = locator.getIndex();     //stores current index position of Entry in heap
        
        if(j >= heap.size() || heap.get(j) != locator)  //if j exceeds size, j is not in heap
                                                        //if Entry at j is not the same as locator, location-aware is not working
        {
            throw new IllegalArgumentException("Invalid entry.");
        }
        return locator;
    }
    
    /**
     * Exchanges entries at given indices of the array list.
     * @param i first index position
     * @param j second index position
     */
    @Override
    protected void swap(int i, int j)
    {
        super.swap(i, j);   //perform the swap, so entries are in a different index than their third field
        ((AdaptablePQEntry<K,V>) heap.get(i)).setIndex(i);  //reset's entry's index to its current position
        ((AdaptablePQEntry<K,V>) heap.get(j)).setIndex(j); //reset's entry's index to its current position
    }
    
    /**
     * Restores Heap-Order property by moving the entry at index j upward or downward depending on criteria.
     * @param j index to check
     */
    protected void bubble(int j)
    {
        if(j > 0 && compare(heap.get(j), heap.get(parent(j))   ) < 0)   //j isn't the root, and key of j is less than key of parent
        {
            upheap(j);
        }
        else
        {
            downheap(j);
        }
    }
    
    /**
     * Inserts a key-value pair and returns the entry created.
     * @param key key of value to insert
     * @param value value to insert into heap
     * @return Entry added to heap
     * @throws IllegalArgumentException if key isn't valid
     */
    @Override
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException
    {
        checkKey(key);
        Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());    //adds Entry to end of array list
        
        heap.add(newest);
        upheap(heap.size()-1); //checks if heap needs to up-heap bubble to maintain Heap-Order
        return newest;           
        
    }
    
    /**
     * Removes the given entry from the priority queue. 
     * @param entry Entry to be removed from priority queue
     * @throws IllegalArgumentException if Entry isn't valid
     */
    @Override
    public void remove(Entry<K, V> entry) throws IllegalArgumentException 
    {
        AdaptablePQEntry<K,V> locator = validate(entry);
        
        int j = locator.getIndex();
        
        if(j == heap.size()-1)  //entry is at last position 
        {
            heap.remove(heap.size()-1); //removes entry
        }
        else //entry is not at last position
        {
            swap(j, heap.size()-1); //swaps last position and entry's position
            heap.remove(heap.size()-1); //removes entry
            bubble(j);  //fix location of Entry displaced by swap
        }
    }

    /**
     * Replaces the key of given Entry with given key.
     * @param e Entry to replace key at
     * @param key key value to add to Entry
     * @throws IllegalArgumentException if given Entry is invalid
     * @throws IllegalArgumentException if given key is invalid
     */
    @Override
    public void replaceKey(Entry<K, V> e, K key) throws IllegalArgumentException 
    {
        AdaptablePQEntry<K,V> locator = validate(e);
        
        checkKey(key);
        
        locator.setK(key);
        bubble(locator.getIndex()); //with new key, may need to move entry
    }

    /**
     * Replaces the value of given Entry with given value.
     * @param e Entry to replace value at
     * @param value value to add to Entry
     * @throws IllegalArgumentException if given Entry is invalid
     */
    @Override
    public void replaceValue(Entry<K, V> e, V value) throws IllegalArgumentException
    {
        AdaptablePQEntry<K,V> locator = validate(e);
        locator.setV(value);
    }
    
}
