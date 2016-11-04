/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;


import _Interfaces.Pair;
import _Interfaces.Vertex;
import java.util.Objects;

/**
 * Represents two vertices and the distance between them, for comparison purposes.
 * Used in GraphNavEngine.
 * @author Ryan
 */
public class EdgePair implements Pair<Vertex<Point>, Double> 
{
    private Vertex<Point> u; //first vertex in pair
    private Vertex<Point> v; //second vertex in pair
    private Double distance; //distance between them
    
    /**
     * Constructs an EdgePair object with given values.
     * @param first first vertex in pair
     * @param second second vertex in pair
     * @param distance distance between them
     */
    public EdgePair(Vertex<Point> first, Vertex<Point> second, double distance)
    {
        u = first;
        v = second;
        this.distance = distance;
    }
    
    //public accessor methods
    /**
     * Returns the distance between the vertices.
     * @return the distance between the vertices.
     */
    @Override
    public Double getElement() 
    {
        return distance;
    }
    
    /**
     * Return the first vertex in pair.
     * @return the first vertex in pair.
     */
    @Override
    public Vertex<Point> getFirst() 
    {
        return u;
    }
    
    /**
     * Return the second vertex in pair.
     * @return the second vertex in pair.
     */
    @Override
    public Vertex<Point> getSecond() 
    {
        return v;
    }
    
    
    //public update methods
    /**
     * Sets the distance between vertices to given value.
     * @param element the distance between vertices
     */
    @Override
    public void setElement(Double element) 
    {
        distance = element;
    }

    
    /**
     * Sets the first vertex of the pair.
     * @param start the first vertex of the pair.
     */
    @Override
    public void setFirst(Vertex<Point> start) 
    {
        u = start;
    }

    

    /**
     * Sets the second vertex of the pair.
     * @param second the second vertex of the pair.
     */
    @Override
    public void setSecond(Vertex<Point> second) 
    {
        v = second;
    }
    
    
    //public additional methods
    
    /**
     * Compares whether this is equal to given object.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise 
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof EdgePair))
        {
            return false;
        }
        else
        {
            EdgePair other = (EdgePair) o;
            
            boolean b1 = Objects.equals(other.distance, this.distance); //because Double is wrapper class
            boolean b2 = this.u.equals(other.u) && this.v.equals(other.v);  //same
            boolean b3 = this.u.equals(other.v) && this.v.equals(other.u);  //switched
            
            return b1 && b2 || b3;
        }
    }    
}
