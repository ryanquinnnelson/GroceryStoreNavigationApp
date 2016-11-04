/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;

import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Class represents a generic singly linked list data structure.
 * @author ryan.quinn.nelson
 * @param <E>  represents generic type to be implemented
 */
public class SinglyLinkedList<E> implements Iterable<E>
{
    //----------------- Nested Node class ----------------------//
    /**
     * Private inner class contains code for generic Node class. 
     * @param <E> 
     */
    private static class Node<E>
    {
        private E         element;       //reference to the element stored at this node
        private Node<E>   next;           //represents next node in linked list
        
        /**
         * Constructs Node object.
         * 
         * @param contents      the element to store in node
         * @param next          the next node in linked list
         */
        public Node(E contents, Node<E> next)
        {
            this.element   = contents;
            this.next       = next;
        }

        /**
         * Returns element of this node.
         * @return E
         */
        public E getElement() {
            return element;
        }


        /**
         * Returns next node after this.
         * @return Node type E
         */
        public Node<E> getNext() {
            return next;
        }

        /**
         * Sets the next node after this.
         * @param next      the next node in the linked list
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }
        
        /**
         * 
         * @return String representation of Node object
         */
        @Override
        public String toString()
        {
            return element.toString();
        }
        
        /**
         * Tests whether this is equal to given object.
         * @param o object to compare with this
         * @return true if objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o)
        {
            if(!(o instanceof Node))
            {
                return false;
            }
            else
            {
                Node<E> other = (Node) o;
                
                boolean b1 = this.getElement().equals(other.element);
                boolean b2 = this.next == other.next; 
                
                return  b1 && b2;
            }
        }
    }//----------------- end of Nested Node class ----------------------//
    
    
    //----------------- Nested SinglyLinkedListIterator class ----------------------//
    /**
     * Implements Iterator interface in SinglyLinkedList class.
     */
    private class SinglyLinkedListIterator implements Iterator<E>
    {
        private Node<E> cursor = head;
        private Node<E> recent = null;
        
        /**
         * Tests whether the iterator has a next object.
         * @return true if next object exists, false otherwise
         */
        @Override
        public boolean hasNext() 
        {
            return cursor != null;
        }

        /**
         * Returns next position in the iterator.
         * @return next position in the iterator
         * @throws NoSuchElementException if no element exists at this position
         */
        @Override
        public E next() throws NoSuchElementException
        {
            if(!hasNext())
            {
                throw new NoSuchElementException("nothing left");
            }
            
            E answer = cursor.getElement();
            
            recent = cursor;
            
            cursor = cursor.getNext();
            
           return answer;
        }
        
    }//----------------- end of Nested SinglyLinkedListIterator class ----------------------//
    
    
    private Node<E>   head = null;        //represents front of linked list
    private Node<E>   tail = null;        //represents the end of linked list
    private int       size = 0;           //represents number of links in list
    
    /**
     * Constructs SinglyLinkedList object with empty list.
     */
    public SinglyLinkedList()
    {
        //empty on purpose
    }

    //public accessor methods
    /**
     * Returns number of links in linked list.
     * @return number of links in linked list
     */
    public int size() 
    {
        return size;
    }
    
    /**
     * Checks if list has any links.
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * Returns element of first node in linked list.
     * @return element of first node in linked list (null if empty)
     */
    public E first() 
    {
        return (isEmpty() ? null : head.getElement());
    }

    /**
     * Returns element of last node in linked list.
     * @return element of first node in linked list (null if empty)
     */
    public E last() 
    {
        return (isEmpty() ? null : tail.getElement());
    }

    
    //public update methods
    /**
     * Adds given element to the front of linked list.
     * @param element   element to add to list
     */
    public void addFirst(E element)
    {
        head = new Node<>(element, head);
        
        //sets new node as tail if list was empty before adding node
        if( size == 0)
        {
            tail = head;
        }
        size++;
    }
    
    /**
     * Adds given element to the end of the linked list.
     * @param element       element to add to list
     */
    public void addLast(E element)
    {
        Node<E> newest = new Node<>(element, null);
        
        //sets node as head if list is empty
        if(isEmpty())
        {
            head = newest;
        }
        else
        {
            tail.setNext(newest);
        }
        
        tail = newest;
        
        size++;
    }
    
    /**
     * Removes and returns first element in linked list.
     * @return first element in linked list (null if empty)
     */
    public E removeFirst()
    {
        if(isEmpty())
        {
            return null;
        }
        
        //stores element to be removed
        E removed = head.getElement();

        //shifts reference of head to next node
        head = head.getNext();
        
        size--;
        
        //resets reference to tail if list is now empty
        if(isEmpty())
        {
            tail = null;
        }
        
        return removed;
    }
    
    //additional methods
    /**
     * Returns a String representation of SinglyLinkedList.
     * @return String representation of SinglyLinkedList
     */
    @Override
    public String toString()
    {
        if(isEmpty())
        {
            return "Empty list.";
        }
        
        StringBuilder answer = new StringBuilder();

        //gets the Simple name of the class that implements SinglyLinkedList
        String implementedClass = first().getClass().getSimpleName();
        
        //creates standard header for class toString()
        String header = this.getClass().getSimpleName() + "<" + implementedClass + ">: {";
        
        answer.append(header);
        
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
    
 
    
    
    
    /**
     * Tests if this is equal to given object.
     * Objects are the same if they have the same length and if each item in the list is the same in the same order.
     * 
     * @param o object to compare to this
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof SinglyLinkedList))
        {
            return false;
        }
        else
        {
            SinglyLinkedList<E> other = (SinglyLinkedList<E>) o;       //safe cast

            if(this.size != other.size)//lists must have the same size to be equal
            {
                return false;
            }
            
            //variables for traversing linked lists
            Iterator<E> iter        = this.iterator();
            Iterator<E> otherIter   = other.iterator();
            
            while(iter.hasNext() && otherIter.hasNext())
            {
                if(!iter.next().equals(otherIter.next()))
                {
                    return false;
                }
            }
            
            return true;
        }  
    }
    
    
    /**
     * Returns an iterator of the elements stored in the list.
     * @return iterator of the elements stored in the list
     */
    @Override
    public Iterator<E> iterator() 
    {
        return new SinglyLinkedListIterator();
    }
    
}