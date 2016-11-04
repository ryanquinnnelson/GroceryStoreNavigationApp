/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import java.util.Iterator;

/**
 * Represents a collection of positions, each of which stores an element.
 * @author Ryan
 * @param <E> generic type to be implemented
 */
public interface PositionalList<E> extends Iterable<E>
{
    /**
     * Returns the number of elements in the list.
     * @return the number of elements in the list
     */
    public abstract int size();
    
    
    /**
     * Tests whether a list is empty.
     * @return true if list is empty, false otherwise
     */
    public abstract boolean isEmpty();
    
    
    /**
     * Returns the first Position in the list.
     * @return first Position in the list, null if empty
     */
    public abstract Position<E> first();
    
    /**
     *  Returns the last Position in the list.
     * @return last Position in the list, null if empty
     */
    public abstract Position<E> last();
    
    
    /**
     * Returns the Position immediately before Position p.
     * @param p
     * @return the Position immediately before Position p or null if p is first
     * @throws IllegalArgumentException if p isn't valid
     */
    public abstract Position<E> before(Position<E> p) throws IllegalArgumentException;
    
    
    /**
     * Returns the Position immediately after Position p.
     * @param p
     * @return the Position immediately after Position p or null if p is last
     * @throws IllegalArgumentException if p isn't valid
     */
    public abstract Position<E> after(Position<E> p) throws IllegalArgumentException;
    
    
    /**
     * Inserts element e at the front of the list and returns its Position.
     * @param e
     * @return 
     */
    public abstract Position<E> addFirst(E e);
    
    
    /**
     * Inserts element e at the end of the list and returns its Position.
     * @param e
     * @return 
     */
    public abstract Position<E> addLast(E e);
    
    
    /**
     * Inserts element e immediately before Position p and returns element e's Position.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    public abstract Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;
    
    
    /**
     * Inserts element e immediately after Position p and returns element e's Position.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    public abstract Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;
    
    
    /**
     * Replaces element stored at Position p and returns the replaced element.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    public abstract E set(Position<E> p, E e) throws IllegalArgumentException;
    
    
    /**
     * Removes element stored at Position p, invalidates p, and returns the element.
     * @param p
     * @return
     * @throws IllegalArgumentException 
     */
    public abstract E remove(Position<E> p) throws IllegalArgumentException;
    
    /**
     * Returns an iterator of the elements stored in the list.
     * @return 
     */
    @Override
    Iterator<E> iterator();
    
    /**
     * Returns an iterable collection of the positions of the list from first to last.
     * @return 
     */
    Iterable<Position<E>> positions();
}
