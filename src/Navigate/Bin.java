/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Represents a row of an column for storing items.
 * @author Ryan
 */
public class Bin implements Serializable
{
    private int column;     //column of bins in store
    private int row;        //row of bin in column
    private int xStart;     //origin for paint method
    private int yStart;     //origin for paint method
    private int unit;       //unit of measure for plan
    private Color color;    //color of Bin
    private int name;       //unique identification for Bin
    
    private int xFront; //coordinate matches xCoordinate of closest vertex in graph of pathways
    private int yFront; //coordinate matches yCoordinate of closest vertex in graph of pathways

    /**
     * Constructs a Bin object.
     * @param aisle column in store 
     * @param section row of an column
     * @param xStart origin for paint method
     * @param yStart origin for paint method
     * @param unit unit of measure for plan
     * @param color color of Bin
     * @param name unique identification for Bin
     */
    public Bin(int aisle, int section, int xStart, int yStart, int unit, Color color, int name) {
        this.column = aisle;
        this.row = section;
        this.unit = unit;
        this.color = color;
        this.name = name;
        
        if(aisle != 11)  //vertical row of bins
        {
            this.xStart = xStart;
            this.yStart = yStart+unit*section;  //yStart changes depending on row
        }
        else //horizontal row of bins
        {
            this.xStart = xStart+unit*section;  //xStart changes depending on row
            this.yStart = yStart;
        }
        
        //calculates xFront and yFront
        if(aisle % 2 == 0) //for all even numbered rows, xCoordinate is to the left
        {
            xFront = this.xStart - unit;
            yFront = this.yStart + unit/2;
        }
        else if(aisle % 2 != 0 && aisle != 11) //odd numbered row not equal to 11
        {
            xFront = this.xStart + unit*2;  //for odd numbered rows, xCoordinate is to the right
            yFront = this.yStart + unit/2;
        }
        else //aisle 11
        {
            xFront = this.xStart + unit/2; //coordinate point is below the bin on the plan
            yFront = this.yStart + (int) (unit*1.5);
        }
    }

    //public accessor methods
    /**
     * Returns the column number of the bin.
     * @return the column number of the bin.
     */
    public int getColumn()
    {
        return column;
    }
    
    /**
     * Returns the xCoordinate representing vertex coordination.
     * @return the xCoordinate representing vertex coordination.
     */
    public int getXFront()
    {
        return xFront;
    }
    
    /**
     * Returns the yCoordinate representing vertex coordination.
     * @return the yCoordinate representing vertex coordination.
     */
    public int getYFront()
    {
        return yFront;
    }
    
    /**
     * Returns the unit measurement of the Bin.
     * @return the unit measurement of the Bin.
     */
    public int getUnit()
    {
        return unit;
    }
    
    /**
     * Returns the name of the bin as an integer.
     * @return the name of the bin as an integer.
     */
    public int getName()
    {
        return name;
    }
    
    /**
     * Returns the color of the Bin.
     * @return the color of the Bin.
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Returns the xCoordinate origin of Bin.
     * @return the xCoordinate origin of Bin.
     */
    public int getXStart()
    {
        return xStart;
    }
    
    /**
     * Returns the yCoordinate origin of Bin.
     * @return the yCoordinate origin of Bin.
     * 
     */
    public int getYStart()
    {
        return yStart;
    }
    
    
    //public update methods
    /**
     * Sets the color of the Bin to given.
     * @param c the new color of the Bin
     */
    public void setColor(Color c)
    {
        color = c;
    }
    
    
    
    //public additional methods
    /**
     * Draws visual representation of Bin object.
     * @param g Graphics objects representing this Bin
     */
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillRect(xStart, yStart, unit, unit); 
        
        //draws outline of row
        g.setColor(Color.GRAY);
        g.drawRect(xStart, yStart, unit, unit);
        
        //writes the name of the bin
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD,unit/5));
        g.drawString(name + "", xStart + unit/3, yStart + unit - unit/4);
        
    }
    
    /**
     * Compares whether this is equal to given object.
     * @param o object to compare with this
     * @return true if objects are equal, false otherwise
     */
    public boolean equals(Object o)
    {
        if(!(o instanceof Bin))
        {
            return false;
        }
        else
        {
            Bin other = (Bin) o;
            
            return this.name == other.name; //bins are equal if they have the same name
        }
    }
    
    /**
     * Returns a String representation of the Bin object.
     * @return a String representation of the Bin object.
     */
    public String toString()
    {
        return "Bin " + name;
    }
    

}