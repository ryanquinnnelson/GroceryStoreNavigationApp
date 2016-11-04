/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;

import _Interfaces.Pair;
import _Interfaces.Vertex;



/**
 * Represents a Bin and the corresponding Vertex in front of it, for comparison purposes.
 * Used for comparison purposes in GraphNavEngine. 
 * @author Ryan
 */
public class DestinationPair implements Pair<Bin, Vertex<Point>>
{
    
    private Bin bin;                //represents first object in pair
    private Vertex<Point> vertex;   //represents element pair is storing
    private Bin second = null;      //unneeded second object in pair

    /**
     * Constructs a DestinationPair object with given values.
     * @param b Bin in pair
     * @param v Vertex in pair
     */
    public DestinationPair(Bin b, Vertex<Point> v)
    {
        bin = b;
        vertex = v;
    }
    
    //public accessor methods
    /**
     * Returns the Vertex of the DestinationPair.
     * @return the Vertex of the DestinationPair
     */
    @Override
    public Vertex<Point> getElement() 
    {
        return vertex;
    }
    
    /**
     * Returns the Bin of the DestinationPair.
     * @return the Bin of the DestinationPair.
     */
    @Override
    public Bin getFirst() 
    {
        return bin;
    }
    
    /**
     * Returns null.
     * @return null
     */
    @Override
    public Bin getSecond() 
    {
        return null;
    }
    
    //public update methods
    /**
     * Sets the vertex of the pair.
     * @param element the vertex of the pair
     */
    @Override
    public void setElement(Vertex<Point> element) 
    {
        vertex = element;
    }

    /**
     * Sets the Bin of the pair.
     * @param start the Bin of the pair
     */
    @Override
    public void setFirst(Bin start) 
    {
        bin = start;
    }

    
    /**
     * Sets second Bin of pair as null.
     * @param second second Bin of pair
     */
    @Override
    public void setSecond(Bin second) 
    {
        this.second = null;
    }
    
    /**
     * Compares whether this is equal to given object.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof DestinationPair))
        {
            return false;
        }
        else
        {
            DestinationPair other = (DestinationPair) o;
            boolean b1 = this.bin.equals(other.bin);
            boolean b2 = this.vertex.equals(other.vertex);
            return b1 && b2;
        }   
    }   
}
