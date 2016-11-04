/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * Collection of generic objects that are inserted and removed according to the first-in, first-out principle.
 * @author ryan.quinn.nelson
 * @param <E> represents generic type to be implemented
 */
public interface Queue<E> extends Iterable<E>
{
    /**
     * Returns the number of objects in the queue.
     * @return number of objects in the queue
     */
    public abstract int size();
    
    /**
     * Checks whether queue has any objects in it.
     * @return true if queue has zero objects, false otherwise
     */
    public abstract boolean isEmpty();
    
    /**
     * Adds an object to the end of the queue.
     * @param e generic object to add to the queue
     */
    public abstract void enqueue(E e);
    
    /**
     * Removes and returns an object from the front of the queue.
     * @return generic object that was removed from queue (null if queue is empty)
     */
    public abstract E dequeue();
    
    /**
     * Returns but does not remove an object from the front of the queue.
     * @return generic object that was removed from queue (null if queue is empty)
     */
    public abstract E first();
    
    
    
    
}
