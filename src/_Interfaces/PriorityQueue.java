/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * Interface for a collection of prioritized elements that allows arbitrary insertion and removal of element that has first priority.
 * For a general and reusable form of a priority queue, we allow a user to choose
 * any key type and send an appropriate comparator instance as a parameter to the 
 * priority queue constructor. Priority queue will use that comparator any time it
 * needs to compare two keys to each other.
 * 
 * For convenience, we also allow a default priority queue to rely on natural ordering
 * for the given keys (assuming the keys come from a comparable class).
 * @author Ryan
 * @param <K> generic key type to be implemented
 * @param <V> generic value type to be implemented
 */
public interface PriorityQueue<K,V>
{
    /**
     * Returns the number of entries in the priority queue.
     * @return number of entries in the priority queue.
     */
    int size();
    
    /**
     * Indicates whether the priority queue contains any entries.
     * @return true if priority queue contains entries, false otherwise.
     */
    boolean isEmpty();
    
    /**
     * Constructs an entry with key k and value v in the priority queue and returns the new Entry.
     * @param key key of new entry
     * @param value value of new entry
     * @return Entry object created by method
     * @throws IllegalArgumentException if key is invalid
     */
    Entry<K,V> insert(K key, V value) throws IllegalArgumentException;
    
    /**
     * Returns but does not remove a priority queue entry having the minimal key.
     * Returns null if priority queue is empty.
     * 
     * @return entry having the minimal key (null if queue is empty)
     */
    Entry<K,V> min();
    
    /**
     * Removes and returns an entry having minimal key from the priority queue.
     * Returns null if priority queue is empty.
     * 
     * @return entry having minimal key (null if queue is empty)
     */
    Entry<K,V> removeMin();
}
