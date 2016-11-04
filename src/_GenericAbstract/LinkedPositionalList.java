/*
 * To change this license title, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;

import _Interfaces.Position;
import _Interfaces.PositionalList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of positional list stored as a doubly linked list structure.
 * @author Ryan
 * @param <E>
 */
public class LinkedPositionalList<E> implements PositionalList<E>
{
    
    //------------ nested Node class -----------------//
    /**
     * Represents a single link in the list using Position interface. 
     * Private to maintain encapsulation. 
     * Node is also a Position because it implements Position interface.
     * @param <E> generic type to be implemented
     */
    private static class Node<E> implements Position<E>
    {
        private E element;              //reference to element stored at this node
        private Node<E> prev;           //previous node in the list
        private Node<E> next;           //subsequent node in the list
        
        public Node(E e, Node<E> prev, Node<E> next)
        {
            element = e;
            this.prev = prev;
            this.next = next;
        }

        //public accessor methods
        @Override
        public E getElement() throws IllegalStateException 
        {
            if(next == null)        //this is the convention for a defunct node
            {
                throw new IllegalStateException("Position no longer valid.");
            }
            
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }
        
        //public update methods
        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
        
        public void setElement(E e)
        {
            element = e;
        }    
    }//------------ end of nested Node class -----------------//

    
    //------------ nested PositionIterator class --------------//
    /**
     * Provides core functionality of the list iterations for Position.
     */
    private class PositionIterator implements Iterator<Position<E>>
    {
        private Position<E> cursor = first();       //position of the next element to report
        private Position<E> recent = null;          //position of the last reported element
        

        /**
         * Tests whether the iterator has a next object.
         * @return true if there are further objects, false otherwise
         */
        @Override
        public boolean hasNext() 
        {
            return cursor != null;
        }

        /**
         * Returns next position in the iterator.
         * @return next Position object.
         */
        @Override
        public Position<E> next() throws NoSuchElementException
        {
            if(cursor == null)
            {
                throw new NoSuchElementException("nothing left");
            }
            recent = cursor;
            cursor = after(cursor);
            
            return recent;
        }
        
        /**
         * Removes the Position returned by the most recent call to next().
         * @throws IllegalStateException if remove() was already called since recent next()
         * @throws IllegalStateException if next() hasn't been called
         */
        @Override
        public void remove() throws IllegalStateException
        {
            if(recent == null)
            {
                throw new IllegalStateException("nothing to remove");
            }
            
            LinkedPositionalList.this.remove(recent);       //removes from outer list
            
            recent = null;  //do not allow remove() again until next() is called
            
        }
        
    }
    
    //-------------nested PositionIterable class -------------//
    /**
     * Provides functionality to make this Iterable.
     */
    private class PositionIterable implements Iterable<Position<E>>
    {

        /**
         * Constructs and returns a new PositionIterator object.
         * @return PositionIterator object
         */
        @Override
        public Iterator<Position<E>> iterator() 
        {
            return new PositionIterator();
        }
        
    }
    
    
    //------------ nested ElementIterator class --------------//
    /**
     * Adapts the iteration produced by positions() to return elements.
     */
    private class ElementIterator implements Iterator<E>
    {
        Iterator<Position<E>> positionIterator = new PositionIterator();    //adapter pattern to reuse methods
        

        /**
         * Tests whether the iterator has a next object.
         * @return true if there are further objects, false otherwise 
         */
        @Override
        public boolean hasNext() 
        {
            return positionIterator.hasNext();
        }

        @Override
        public E next() 
        {
            return positionIterator.next().getElement();    //return element instead of Position
        }
        
        @Override
        public void remove()
        {
            positionIterator.remove();
        }
        
    }
    
    private Node<E> header;     //sentinel at beginning of list
    private Node<E> trailer;    //sentinel at end of list
    private int size = 0;
    
    /**
     * Constructs a new empty list.
     */
    public LinkedPositionalList()
    {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        
        header.setNext(trailer);
    }
    
     //private utilities
    /**
     * Validates position and returns it as a node if it meets criteria.
     * @param p
     * @return
     * @throws IllegalArgumentException 
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException
    {
        if(!(p instanceof Node))
        {
            throw new IllegalArgumentException("Invalid p.");
        }
        
        Node<E> node = (Node<E>) p;     //safe cast
        
        if(node.getNext() == null)
        {
            throw new IllegalArgumentException("p is no longer in the list.");
        }
        
        return node;
    }
    
    /**
     * Returns the given node as a Position.
     * @param node
     * @return the given node as a Position or null if it is a sentinel
     */
    private Position<E> position(Node<E> node)
    {
        if(node == header || node == trailer)
        {
            return null;       //hides sentinels from user
        }
        return node;
    }
    
    
    /**
     * Adds element e to the linked list between the given nodes and returns Position.
     * @param e
     * @param predecessor
     * @param successor
     * @return 
     */
    private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor)
    {
        Node<E> newest = new Node(e, predecessor, successor);       //instantiate new node
        
        predecessor.setNext(newest);
        successor.setPrev(newest);
        size++;
        
        return newest;
    }
    
    
    //public accessor methods
    /**
     * Returns number of elements in the linked list.
     * @return 
     */
    @Override
    public int size() 
    {
        return size;
    }

    /**
     * Tests whether list contains elements.
     * @return true if list contains elements, false otherwise.
     */
    @Override
    public boolean isEmpty() 
    {
        return size == 0;
    }

    /**
     * Returns first Position in the linked list.
     * @return first Position in the linked list or null if empty.
     */
    @Override
    public Position<E> first()
    {        
        return position(header.getNext());
    }

    /**
     * Returns last Position in the linked list.
     * @return last Position in the linked list or null if empty.
     */
    @Override
    public Position<E> last()
    {
        return position(trailer.getPrev());
    }

    /**
     * Returns Position before argument or null if p is first.
     * @param p
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return position(node.getPrev());
    }

    /**
     * Returns Position after argument or null if p is last.
     * @param p
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return position(node.getNext());
    }

    
    
    
    //public update methods
    /**
     * Inserts element e at the front of the linked list and returns its new Position.
     * @param e
     * @return 
     */
    @Override
    public Position<E> addFirst(E e) 
    {
        return addBetween(e, header, header.getNext());
    }

    /**
     * Inserts element e at the end of the linked list and returns its new Position.
     * @param e
     * @return 
     */
    @Override
    public Position<E> addLast(E e) 
    {
         return addBetween(e, trailer.getPrev(), trailer);
    }

    /**
     * Inserts element e immediately before Position p and returns inserted element's new Position.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return addBetween(e, node.getPrev(), node);
    }

    
    /**
     * Inserts element e immediately after Position p and returns inserted element's new Position.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        
        return addBetween(e, node, node.getNext());
    }

    /**
     * Replaces element stored at Position p and returns the replaced element.
     * @param p
     * @param e
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public E set(Position<E> p, E e) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        E replaced = node.getElement();
        
        node.setElement(e);
        
        return replaced;
        
    }

    /**
     * Removes Position p and returns element at the removed position.
     * @param p
     * @return
     * @throws IllegalArgumentException 
     */
    @Override
    public E remove(Position<E> p) throws IllegalArgumentException 
    {
        Node<E> node = validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        
        //removes Position / Node by linking around it
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        
        size--;
        
                
        E removed = node.getElement();
        
        //assist garbage collection, also convention for a defunct node
        node.setElement(null);
        node.setNext(null);
        node.setPrev(null);
        
        return removed;
    }


   
    
    //additional methods
    /**
     * Returns an iterable representation of the list's positions.
     * @return iterable Position object of generic type
     */
    @Override
    public Iterable<Position<E>> positions()
    {
        return new PositionIterable();
    }
    
    /**
     * Returns an iterator of the elements stored in the list.
     * @return 
     */
    @Override
    public Iterator<E> iterator()
    {
        return new ElementIterator();
    }
    
    @Override
    public String toString()
    {
        if(isEmpty())
        {
            return "Empty List.";
        }
        
        StringBuilder answer = new StringBuilder();

        //gets the Simple name of the class that implements FixedArrayList
        String implementedClass = first().getElement().getClass().getSimpleName();
        
        //creates standard title for class toString()
        String title = this.getClass().getSimpleName() + "<" + implementedClass + ">: {";
        
        answer.append(title);
        
        
        Iterator<E> iter = iterator();
        
        while(iter.hasNext())
        {
            answer.append(iter.next().toString());
            
            if(iter.hasNext() == true)
            {
                answer.append(", ");
            }
        }
        
        answer.append("}");
        return answer.toString();
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof LinkedPositionalList))
        {
            return false;
        }
        else
        {
            LinkedPositionalList<E> other = (LinkedPositionalList<E>) o;
            
            
            //lists must contain the same number of elements
            if(this.size != other.size)
            {
                return false;
            }
            
            //the frequency of all elements in the bag must be the same
            Iterator<E> iter = iterator();
            Iterator<E> otherIter = other.iterator();
            
            while(iter.hasNext())
            {                              
                if(iter.next() != otherIter.next())
                {
                    return false;
                }
            }
            return true;
        }
    }
}
