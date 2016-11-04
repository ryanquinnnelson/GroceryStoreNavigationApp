/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;

import _Interfaces.Plan;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the floor plan of the grocery store. 
 * Designed on 320 x 500 with each unit representing the integer 1.
 * Scaled according to constructor parameter.
 * @author Ryan
 */
public class StorePlan implements Plan
{
    private int width;  //overall width of the StorePlan
    private int height; //overall height of the StorePlan
    private int unit;   //unit the StorePlan is scaled by
    private Color color;    //color of the Bins in the store
    
    private int xStart  = 0; //default starting x coordinate for StorePlan object
    private int yStart  = 0; //default starting y coordinate for StorePlan object
    
    private List<Bin> bins; //list of all Bin objects in StorePlan
    /*
    Represents a pair of Point objects where first Point is the store entrance 
    on the plan and second is the Point immediately above it, a member of the the PointPair paths.
    
    This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
    */
    private PointPair entryPair;  
    
    /*
    Represents a pair of Point objects where first Point a member of the the 
    PointPair paths immediately above the checkout Point, and the second is the 
    checkpoint Point on the StorePlan.
    
    This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
    */
    private PointPair exitPair;
    
    /*
    Represents all major intersections and nodes in plan, as pairs of Points.
    Does not include Entrance Point or Checkout Point.
    
    This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
    */
    private PointPair[] paths;
    
    /**
     * Constructs a StorePlan object with given x and y values as upper left corner and given width.
     * @param xStart x value of upper left corner to draw the plan
     * @param yStart y value of upper left corner to draw the plan
     * @param width desired width of plan on the screen in pixels
     * @param c Color to make the Bins in the store
     */
    public StorePlan(int xStart, int yStart, int width, Color c)
    { 
        this.xStart = xStart;
        this.yStart = yStart;
        this.width = width;
        unit = width / 20;  //calculates unit based on width (i.e. width = 500, unit = 25)
        height = 13*unit;   //calculates height based on unit
        color= c;
        
        
        bins        = constructBins(); //constructs the Bin objects in the plan
        paths       = constructPaths(); //constructs the PointPair array for the paths of the plan
        entryPair   = constructEntryPair(); //constructs the PointPair representing the entrance
        exitPair    = constructExitPair(); //constructs the PointPair representing the checkout   
    }
   
    //private utilities
    /**
     * Constructs all Bin objects in the plan, given xStart, yStart, and unit.
     * @return list of all Bin objects in plan
     */
     private List<Bin> constructBins()
    {
        List<Bin> list = new ArrayList<>();
        int count = 0;
        
        
        //construct Bin object and adds it to ArrayList
        /*
        Some notes:
        -  aisles are labeled 1 through 11, representing a single row of Bins
           the aisle designates the row of Bins, not the open aisle to walk through
        -  sections represent the horizontal row the Bin is in, as counted from
           the top of a single aisle, downward
        */
       
        
        
        //Aisle 11
        for(int i = 1; i <= 17; i++) //constructs 17 Bins in a horizontal row, starting at the left
        { 
            list.add(new Bin(11,i,xStart+unit/2,yStart,unit,color, count++));
        }
        
        //Aisle 1
        for(int i = 1; i <= 8; i++) //constructs 8 Bins in a vertical row, starting at the top
        { 
            list.add(new Bin(1,i,xStart,yStart,unit,color, count++));
        }
        
        //for Aisles 2 - 9
        for(int j = 0; j <= 3; j++) //constructs 2 vertical rows of 6 Bins each, starting at the top left corner of the leftmost row
        {
            //Aisle L - represents a left facing Bin
            for(int i = 1; i <= 6; i++) //constructs vertical row of 6 Bins, starting at the top
            {
                list.add(new Bin(2 + j*2, i, xStart+unit*(3+j*4), yStart+unit*2, unit,color, count++));
            }
            //Aisle R - represents a right facing Bin
            for(int i = 1; i <= 6; i++) //constructs vertical row of 6 Bins, starting at the top
            {
                list.add(new Bin(3 + j*2, i, xStart+unit*(4+j*4), yStart+unit*2, unit,color, count++));
            }
        }
        
        //Aisle 10
        for(int i = 1; i <= 8; i++) //constructs 8 Bins in a vertical row, starting at the top
        { 
            list.add(new Bin(10,i,xStart+unit*19,yStart,unit,color, count++));
        }

        return list;
        
    }
    
    /**
     * Constructs all Point objects representing intersections in store and returns a list.
     * @return 
     */
    private List<Point> constructIntersections()
    {
        List<Point> list = new ArrayList<>();
        
        //add top intersections, L to R
        for(int i = 0; i < 5; i++)
        { 
            Point newest = new Point(xStart+unit*(2+4*i),yStart+(int) (unit * 1.5));            
            list.add(newest);
//            System.out.println(newest);
        }
        
        //add middle intersections, L to R
        for(int i = 0; i < 5; i++)
        { 
            Point newest = new Point(xStart+unit*(2+4*i),yStart+(int) (unit*5.5));            
            list.add(newest);
//            System.out.println(newest);
        }
        
        //add bottom intersections, L to R
        for(int j = 0; j < 5; j++)
        {
            Point newest = new Point(xStart+unit*(2+4*j),yStart+(int) (unit*9.5));            
            list.add(newest);
//            System.out.println(newest);
        }
        
        return list;
    }
      
    /**
     * Constructs a representation of all major intersections and nodes in plan, as pairs of Points.
     * Does not include Entrance Point or Checkout Point.
     * @return array of PointPair objects representing all major intersections and nodes in plan
     */
    private PointPair[] constructPaths()
    {
        List<Point> intersections = constructIntersections();
        PointPair [] pairArr = new PointPair [18]; 
        int i = 0;
        
        //get Points to set up pairs
        //top row, L to R
        Point t1 = intersections.get(i++);
        Point t2 = intersections.get(i++);
        Point t3 = intersections.get(i++);
        Point t4 = intersections.get(i++);
        Point t5 = intersections.get(i++);

        //middle row, L to R
        Point m1 = intersections.get(i++);
        Point m2 = intersections.get(i++);
        Point m3 = intersections.get(i++);
        Point m4 = intersections.get(i++);
        Point m5 = intersections.get(i++);

        //bottom row, L to R
        Point b1 = intersections.get(i++);
        Point b2 = intersections.get(i++);
        Point b3 = intersections.get(i++);
        Point b4 = intersections.get(i++);
        Point b5 = intersections.get(i++);
        
        //combines Points into PointPair objects
        //top row, L to R
        pairArr[0] = new PointPair(t1,t2);
        pairArr[1] = new PointPair(t2,t3);
        pairArr[2] = new PointPair(t3,t4);
        pairArr[3] = new PointPair(t4,t5);
        
        //bottom row, L to R
        pairArr[4] = new PointPair(b1,b2);
        pairArr[5] = new PointPair(b2,b3);
        pairArr[6] = new PointPair(b3,b4);
        pairArr[7] = new PointPair(b4,b5);
        
        //top verticals, L to R
        pairArr[8] = new PointPair(t1,m1);
        pairArr[9] = new PointPair(t2,m2);
        pairArr[10] = new PointPair(t3,m3);
        pairArr[11] = new PointPair(t4,m4);
        pairArr[12] = new PointPair(t5,m5);
        
        //bottom verticals, L to R
        pairArr[13] = new PointPair(m1,b1);
        pairArr[14] = new PointPair(m2,b2);
        pairArr[15] = new PointPair(m3,b3);
        pairArr[16] = new PointPair(m4,b4);
        pairArr[17] = new PointPair(m5,b5);
        
        return pairArr;
    }
    
    /**
     * Constructs a representation of a pair of Point objects where first Point is the store entrance 
       on the plan and second is the Point immediately above it, a member of the the PointPair paths.
     * @return PointPair object representing entrance
     */
    private PointPair constructEntryPair()
    {
        List<Point> intersections = constructIntersections();
        Point b5 = intersections.get(14);
        Point entry = new Point(xStart + unit*18, yStart+ (int) (unit*10.5));
        
        return new PointPair(b5, entry);
    }
    
    /**
     * Constructs a representation of a pair of Point objects where first Point is a member of the 
       PointPair paths immediately above the checkout Point, and the second is the 
       checkpoint Point on the StorePlan.
     * @return PointPair object representing the checkout
     */
    private PointPair constructExitPair()
    {
        List<Point> intersections = constructIntersections();
        Point b1 = intersections.get(10);
        Point entry = new Point(xStart + unit*2, yStart+ (int) (unit*10.5));
        
        return new PointPair(b1, entry);
    }
    
    
    
    
    //public accessor methods
    /**
     * Returns the PointPair array representing the paths in the plan.
     * @return the PointPair array representing the paths in the plan.
     */
    @Override
    public PointPair[] getPaths()
    {
        return paths;
    }
    
    /**
     * Returns the PointPair representing the entrance path in the plan.
     * @return the PointPair representing the entrance path in the plan.
     */
    @Override
    public PointPair getEntryPair()
    {
        return entryPair;
    }
    
    /**
     * Returns the PointPair representing the exit path in the plan.
     * @return the PointPair representing the exit path in the plan.
     */
    @Override
    public PointPair getExitPair()
    {
       return exitPair;
    }
    
    /**
     * Returns the nth Bin created during the Plan's construction.
     * @param n represents the 'index' of Bin in plan (first Bin created is 0)
     * @return nth Bin created in the Plan
     * @throws IndexOutOfBoundsException if no Bin exists at that 'index'
     */
    @Override
    public Bin getBin(int n) throws IndexOutOfBoundsException
    {
        if(n < 0 || n >= bins.size())
        {
            throw new IndexOutOfBoundsException("Index out of bounds: " + n);
        }
        return bins.get(n);
    }
    
    /**
     * Returns a list of all Bin objects in the Plan.
     * @return a list of all Bin objects in the Plan
     */
    @Override
    public List<Bin> getBins()
    {
        return bins;
    }
    
    /**
     * Returns a count of the number of Bins in the Plan.
     * @return a count of the number of Bins in the Plan
     */
    @Override
    public int getBinCount()
    {
        return bins.size();
    }
    
    
    

    //public additional methods
    /**
     * Draws a visual representation of StorePlan object.
     * @param g Graphics object to draw representation on
     */
    @Override
    public void paint(Graphics g)
    {
        //fill in unreachable upper corners and other items
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xStart, yStart, unit+unit/2, unit);  //upper left
        g.fillRect(xStart + unit*18+unit/2, yStart, unit+unit/2, unit); //upper right
        g.fillRect(xStart + unit*7, yStart + unit*11, unit*9, unit*2); //help desk
        g.fillRect(xStart+unit, yStart+unit*11, unit/2, unit);  //register 1
        g.fillRect(xStart+unit*2+unit/2, yStart+unit*11, unit/2, unit);  //register 2
        g.fillRect(xStart+unit*3+unit, yStart+unit*11, unit/2, unit);  //register 3
        g.fillRect(xStart+unit*5+unit/2, yStart+unit*11, unit/2, unit);  //register 4
        

        //draw upper corners
        g.setColor(Color.GRAY);
        g.drawRect(xStart + unit*18+unit/2, yStart, unit+unit/2, unit); //upper right
        g.drawRect(xStart, yStart, unit+unit/2, unit);  //upper left
        
        
        //draws the bins
        for(Bin b : bins)
        {
            b.paint(g);
        }
        
        //draw outlines
        g.setColor(Color.BLACK);
        g.drawRect(xStart, yStart, width, height);   //overall box 
        g.drawRect(xStart + unit*7, yStart + unit*11, unit*9, unit*2); //help desk
        g.drawRect(xStart+unit, yStart+unit*11, unit/2, unit);  //register 1
        g.drawRect(xStart+unit*2+unit/2, yStart+unit*11, unit/2, unit);  //register 2
        g.drawRect(xStart+unit*3+unit, yStart+unit*11, unit/2, unit);  //register 3
        g.drawRect(xStart+unit*5+unit/2, yStart+unit*11, unit/2, unit);  //register 4
        
        //write text
        g.setFont(new Font("Arial", Font.BOLD,15));
        g.drawString("Checkout", xStart + (int) (unit*2.7), yStart + (int) (unit*12.5));
        g.drawString("Entrance", xStart + (int) (unit*17.2), yStart + (int) (unit*12.5));
       
        //write aisle numbers
        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.PLAIN,20));
        g.drawString("1", xStart+ (int) (unit*1.75), yStart + (int) (unit*8.5));
        g.drawString("2", xStart+ (int) (unit*5.75), yStart + (int) (unit*8.5));
        g.drawString("3", xStart+ (int) (unit*9.75), yStart + (int) (unit*8.5));
        g.drawString("4", xStart+ (int) (unit*13.75), yStart + (int) (unit*8.5));
        g.drawString("5", xStart+ (int) (unit*17.75), yStart + (int) (unit*8.5));
        g.drawString("6", xStart+ (int) (unit*9.75), yStart + (int) (unit*2.25));
    }
    
    /**
     * Draws the given Bin objects in given color on the Plan, to 'highlight' specific Bins.
     * @param g Graphics objects on which Bin is drawn
     * @param selected Bin objects selected by customer
     * @param c the color the objects should be displayed as
     */
    @Override
    public void highlightSelected(Graphics g, List<Bin> selected, Color c)
    {
        for(Bin b : selected)
        {
            b.setColor(c);
            b.paint(g);
        }
    }
   
}
