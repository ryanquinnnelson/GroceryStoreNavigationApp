/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.Queue;
import java.util.Iterator;


/**
 * Implements Queue interface using SinglyLinkedList and adapter pattern.
 * @author ryan.quinn.nelson
 * @param <E> represents generic data type
 */
public class LinkedQueue<E> implements Queue<E>
{

    
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();        //constructs empty linked list
    
    /**
     * Constructs generic LinkedQueue object with empty linked list.
     */
    public LinkedQueue()
    {
        //empty on purpose
    }
    
    
    //public accessor methods
    /**
     * Returns the number of objects in the queue.
     * @return number of objects in the queue 
     */
    @Override
    public int size() 
    {
        return list.size();
    }

    /**
     * Checks whether queue has any objects in it.
     * @return true if queue has zero objects, false otherwise
     */
    @Override
    public boolean isEmpty() 
    {
        return list.isEmpty();
    }

    /**
     * Returns but does not remove an object from the front of the queue.
     * @return generic object that was removed from queue (null if queue is empty)
     */
    @Override
    public E first() 
    {
        return list.first();
    }
    
    
    //public update methods
    /**
     * Adds an object to the end of the queue.
     * @param e generic object to add to the queue
     * 
     */
    @Override
    public void enqueue(E e) 
    {
        list.addLast(e);
    }

    /**
     * Removes and returns an object from the front of the queue.
     * @return generic object that was removed from queue (null if queue is empty)
     */
    @Override
    public E dequeue() 
    {
        return list.removeFirst();
    }

    //additional methods
    /**
     * 
     * @return String representation of LinkedQueue object
     */
    @Override
    public String toString()
    {
        if(isEmpty())
        {
            return "Empty Queue.";
        }
        
        StringBuilder answer = new StringBuilder();

        //gets the Simple name of the class that implements FixedArrayList
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
     * Tests whether two LinkedQueue objects are equal.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise 
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof LinkedQueue))
        {
            return false;
        }
        else
        {
            LinkedQueue<E> other = (LinkedQueue<E>) o;
            
            //check whether stacks have same number of objects in them
            if(this.size() != other.size())
            {
                return false;
            }
            
            Iterator<E> iter = iterator();
            Iterator<E> otherIter = other.iterator();
            
            while(iter.hasNext())
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
     * Returns an Iterator of the elements stored in the queue.
     * @return an Iterator of the elements stored in the queue 
     */
    @Override
    public Iterator<E> iterator()
    {
        return list.iterator();
    }
}
