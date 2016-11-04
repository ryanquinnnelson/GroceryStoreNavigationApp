/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;

/**
 * A representation of a coordinate point in two-dimensions.
 * @author Ryan
 */
public class Point
{
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Constructs a Point of given x and y values.
     * @param xCoordinate x value of point
     * @param yCoordinate y value of point
     */
    public Point(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    //public accessor methods
    
    /**
     * Returns the x value of the point.
     * @return the x value of the point.
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y value of the point.
     * @return the y value of the point.
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    
    
    //public update methods
    /**
     * Sets the x value of the Point.
     * @param xCoordinate the x value of the Point
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Sets the y value of the Point.
     * @param yCoordinate the y value of the Point.
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
    
    
    //public additional methods
    /**
     * Scales the point to match a desired display size.
     * @param scale value to scale point by
     */
    public void scalePoint(double scale)
    {
        xCoordinate = (int) (xCoordinate*scale);
        yCoordinate = (int) (yCoordinate*scale);
    }
    
    /**
     * Shifts the point to match a desired display location.
     * @param xShift amount to shift x value by
     * @param yShift amount to shift y value by
     */
    public void shiftPoint(int xShift, int yShift)
    {
        xCoordinate += xShift;
        yCoordinate += yShift;
    }
    
    /**
     * Returns a String representation of Point object.
     * @return a String representation of Point object.
     */
    @Override
    public String toString()
    {
        return "(" + xCoordinate + "," + yCoordinate + ")";
    }
    
    /**
     * Tests whether this is equal to given object.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if(! (o instanceof Point))
        {
            return false;
        }
        else
        {
            Point other = (Point) o;
            
            return this.xCoordinate == other.xCoordinate && this.yCoordinate == other.yCoordinate;
        }
    }
}
