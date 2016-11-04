/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.Entry;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implementation of priority queue using array-based heap.
 * @author Ryan
 * @param <K> Generic key type to be implemented
 * @param <V> Generic value type to be implemented
 */
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K, V>
{
    protected ArrayList<Entry<K,V>> heap = new ArrayList<>();   //primary collection of priority queue entries
    
    /**
     * Constructs empty priority queue based on natural ordering of its keys.
     */
    public HeapPriorityQueue()
    {
        super();
    }
    
    /**
     * Constructs empty priority queue using given comparator to order keys.
     * @param comp 
     */
    public HeapPriorityQueue(Comparator<K> comp)
    {
        super(comp);
    }
    
    /**
     * Constructs filled priority queue of given parallel arrays using bottom-up construction.
     * @param keys array storing Entry keys
     * @param values array storing corresponding Entry values
     */
    public HeapPriorityQueue(K[] keys, V[] values)
    {
        super();    //Constructs empty priority queue based on natural ordering of its keys
        
        //determines the maximum number of paired  key, value pairs and adds each pair to the heap
        for(int j = 0; j < Math.min(keys.length, values.length); j++) //start at root, and add entries to fill up each level of heap
        {
            heap.add(new PQEntry<>(keys[j], values[j]));    //adds Entry to end of array list
        }
        heapify();  //maintains Heap-Order.
    }
    
    //protected utilities
    /**
     * Returns the index position of the parent of given index.
     *                       __
     *                      |  |0
     *                       /\
     *                      /  \
     *                     /    \
     *                    /      \
     *                   /        \
     *                  /          \
     *              __ /            \ __
     *             |  | 1            |  | 2
    *               /\                /\
    *              /  \              /  \
    *             /    \            /    \
    *            /      \          /      \
    *          _/_      _\_      _/_      _\_
     *        |  |3     |  |4   |  |5     |  |6
     * 
     * If j is 2, returns 0. If j is 1 returns 0. 
     * If j is 3, returns 1. If j is 4, returns 1. 
     * 
     * @param j index to determine parent of
     * @return the index position of the parent
     */
    protected int parent(int j)
    {
        return (j-1)/2;
    }
    
    /**
     * Returns the index position of the left child of given index.
     *                       __
     *                      |  |0
     *                       /\
     *                      /  \
     *                     /    \
     *                    /      \
     *                   /        \
     *                  /          \
     *              __ /            \ __
     *             |  | 1            |  | 2
    *               /\                /\
    *              /  \              /  \
    *             /    \            /    \
    *            /      \          /      \
    *          _/_      _\_      _/_      _\_
     *        |  |3     |  |4   |  |5     |  |6
     * 
     * If j is 2, returns 5. If j is 1 returns 3. 
     * If j is 0, returns 1. 
     * 
     * @param j index to determine left child of
     * @return the index position of the left child
     */
    protected int left(int j)
    {
        return 2*j+1;
    }
    
    /**
     * Returns the index position of the right child of given index.
     *                       __
     *                      |  |0
     *                       /\
     *                      /  \
     *                     /    \
     *                    /      \
     *                   /        \
     *                  /          \
     *              __ /            \ __
     *             |  | 1            |  | 2
    *               /\                /\
    *              /  \              /  \
    *             /    \            /    \
    *            /      \          /      \
    *          _/_      _\_      _/_      _\_
     *        |  |3     |  |4   |  |5     |  |6
     * 
     * If j is 2, returns 6. If j is 1 returns 4. 
     * If j is 0, returns 2. 
     * 
     * @param j index to determine left child of
     * @return the index position of the left child
     */
    protected int right(int j)
    {
        return 2*j+2;
    }
    
    /**
     * Tests whether index has a left child.
     * @param j index to check for left child
     * @return true if index has left child, false otherwise
     */
    protected boolean hasLeft(int j)
    {
        //if left(j) calculation is larger than the current number of elements in array, left child doesn't exist
        return left(j) < heap.size();   
    }
    
    /**
     * Tests whether index has a right child.
     * @param j index to check for right child
     * @return true if index has right child, false otherwise
     */
    protected boolean hasRight(int j)
    {
        //if right(j) calculation is larger than the current number of elements in array, right child doesn't exist
        return right(j) < heap.size();
    }
    
    /**
     * Switches the values of given indices in array list.
     * @param i first position
     * @param j second position
     */
    protected void swap(int i, int j)
    {
        Entry<K,V> temp = heap.get(i);  //store Entry temporarily
        heap.set(i, heap.get(j));   //replace Entry at i with Entry at j
        heap.set(j, temp);          //replace Entry at j with Entry originally at i
    }
    
    /**
     * Moves entry at given index higher, if necessary, to restore Heap-Order property.
     * Heap-Order property states that in a heap T, for every position p other than the
     * root, the key stored at p is greater than or equal to the key stored at p's parent.
     * 
     * @param j index that might need up-heap bubbling to restore Heap-Order property
     */
    protected void upheap(int j)
    {
        while(j > 0)    //while the index position isn't the root of the heap
        {
            int p = parent(j);  //get the index of the parent of j
            
            if(compare(heap.get(j), heap.get(p)) >= 0)  //if key of j is greater than or equal to key of parent, j is correctly positioned
            {
                break;
            }
            swap(j, p); //if key of j is smaller than key of parent, need to swap Entries to maintain Heap-Order property
            
            j = p;  // assign index position of parent to j to continue up-heap bubbling from j's new position
            
        }   //if index position is the root, can't bubble up-heap any further
    }
    
    /**
     * Moves entry at given index lower, if necessary, to restore Heap-Order property.
     * Heap-Order property states that in a heap T, for every position p other than the
     * root, the key stored at p is greater than or equal to the key stored at p's parent.
     * 
     * Complete Binary Tree property states that a heap T with height h is a complete
     * binary tree if levels 0, 1, 2, ..., h-1 of T have the maximum number of nodes
     * possible, and the remaining nodes at level h reside in the leftmost possible
     * positions at that level.
     * 
     * @param j index that might need down-heap bubbling to restore Heap-Order property
     */
    protected void downheap(int j)
    {
        while(hasLeft(j))   //j has a left child
        {
            int leftIndex = left(j);    //get index position of j's left child
            
            //determine which of j's children has the smaller key, so that the child with smaller key can be compared with j
            int smallChildIndex = leftIndex;    //if j has any children, because of Complete Binary Tree Property, j will have a left child
            
            
            if(hasRight(j)) //j has a right child, so need to compare siblings to see which has smaller key
            {
                int rightIndex = right(j);  //get index position of j's right child
                
                if(compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)  //if key value of left child is larger than key value of right
                {
                    smallChildIndex = rightIndex;   //right child is the child with smaller key
                }
            }
            
            //once the smaller child has been determined, compare child's key with j's key
            if(compare(heap.get(smallChildIndex), heap.get(j))>=0) //j's key is smaller than child's, j is correctly positioned
            {
                break;
            }
            swap(j, smallChildIndex);   //if key of j is smaller than key of child, need to swap Entries to maintain Heap-Order property
            
            j = smallChildIndex; // assign index position of child to j to continue down-heap bubbling from j's new position
        }//if index position is a leaf, can't bubble down-heap any further
    }
    
    /**
     * Checks whether Entries are in correct position to maintain Heap-Order, starting with parent of last Entry in subtree.
     * Used with bottom-up heap construction.
     */
    protected void heapify()
    {
        int startIndex = parent(size()-1); //gets the index of the parent of the last occupied position in array list
        
        for(int j = startIndex; j >= 0; j--) //iterates through every index from parent of last occupied position until root of subtree
        {
            downheap(j);    //checks whether that index needs to down-heap bubble to maintain Heap-Order.
        }
    }
    
    
    //public accessor methods
    /**
     * Returns the number of entries in the priority queue.
     * @return the number of entries in the priority queue
     */
    @Override
    public int size() 
    {
        return heap.size();
    }

    /**
     * Returns the Entry that has the minimal key in the priority queue (null if queue is empty).
     * @return Entry that has the minimal key in the priority queue (null if queue is empty)
     */
    @Override
    public Entry<K, V> min() 
    {
        if(heap.isEmpty())
        {
            return null;
        }
        return heap.get(0); //minimal key is always stored at root
    }
    
    
    //update methods
    /**
     * Constructs an entry with key k and value v in the priority queue and returns the new Entry.
     * @param key key of new entry
     * @param value value of new entry
     * @return Entry object created by method
     * @throws IllegalArgumentException if key is invalid
     */
    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException 
    {
        checkKey(key);
        Entry<K,V> newest = new PQEntry<>(key, value);
        
        heap.add(newest);   //adds newest Entry to first open position in array list
        upheap(heap.size()-1);  //checks whether newest Entry needs to be moved forward (upward) to maintain Heap-Order.
        return newest;
    }

    

    /**
     * Removes and returns an entry with minimal key.
     * @return an entry with minimal key (null if queue is empty)
     */
    @Override
    public Entry<K, V> removeMin() 
    {
        if(heap.isEmpty())
        {
            return null;
        }
        Entry<K,V> answer = heap.get(0);    //minimal key is always stored at root
        
        swap(0, heap.size()-1); //switches Entry objects of root and last occupied position in array list to facilitate removal
        
        heap.remove(heap.size()-1); //removes last occupied position in array list (which contains the root Entry now)
        
        downheap(0); //checks whether Entry that was moved from last occupied position 
                     //needs to be moved backward (downward) to maintain Heap-Order.
        return answer;
    }
    
}
