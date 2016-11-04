/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;

import _Interfaces.Pair;

/**
 * Represents a pair of Point objects, which is useful for constructing a graph.
 * @author Ryan
 */
public class PointPair implements Pair<Point, Double> 
{
    private Point first; //first Point in the pair
    private Point second; //second Point in the pair
    private Double element = null; //unused value stored in the pair

    /**
     * Constructs a PointPair of given Points.
     * @param first first Point in the pair
     * @param second second Point in the pair
     */
    public PointPair(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    //public accessor methods
    /**
     * Returns the first point in the pair.
     * @return the first point in the pair.
     */
    @Override
    public Point getFirst() {
        return first;
    }
      
    /**
     * Returns the second point in the pair.
     * @return the second point in the pair. 
     */
    @Override
    public Point getSecond() {
        return second;
    }
    
    /**
     * Returns the value stored in the pair.
     * @return the value stored in the pair.
     */
    @Override
    public Double getElement() {
        return element;
    }

    
    //public update methods
    /**
     * Sets the first point in the pair.
     * @param first the first point in the pair.
     */
    @Override
    public void setFirst(Point first) {
        this.first = first;
    }

    
    /**
     * Sets the second point in the pair. 
     * @param second the second point in the pair
     */
    @Override
    public void setSecond(Point second) {
        this.second = second;
    }

    

    /**
     * Sets the value stored in the pair.
     * @param element the value stored in the pair. 
     */
    @Override
    public void setElement(Double element) {
        this.element = element;
    }
    

    
    /**
     * Returns a String representation of a PointPair.
     * @return a String representation of a PointPair.
     */
    @Override
    public String toString()
    {
        return "First: " + first + "," + "Second: " + second;
    }
}
