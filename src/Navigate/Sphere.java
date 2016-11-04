/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;


import _Interfaces.Vertex;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * A visual representation of a single vertex in the graph.
 * @author Ryan
 */
public class Sphere 
{
    private int     xPosition;      //x origin for paint method
    private int     yPosition;      //y origin for paint method
    private String  coordinates;    //String representation of a Point object's coordinates
    private String  vertexID;       //String representation of a Vertex's ID
    private int     diameter = 15;  //default diameter of sphere
    private Color   color;          //default color of sphere

    /**
     * Constructs a sphere of given vertex and given color.
     * @param v vertex for sphere to represent
     * @param color color to paint the sphere
     */
    public Sphere(Vertex<Point> v, Color color)
    {
        xPosition = v.getElement().getXCoordinate();
        yPosition = v.getElement().getYCoordinate();
        coordinates = v.getElement().toString();
        vertexID = v.getID() + "";
        this.color = color;
    }
    
   
    
    
    //public update methods
    /**
     * Sets the color of the sphere.
     * @param c color for the sphere
     */
    public void setColor(Color c)
    {
        color = c;
    }
    
    //public additional method
    /**
     * Draws a visual representation of the sphere object as well as a String of its coordinate points and an ID.
     * @param g Graphics objects to draw the sphere in
     */
    public void paint(Graphics g)
    {
        g.setColor(color); //color sphere and coordinate string
        g.fillOval(xPosition - diameter/2,yPosition-diameter/2,diameter, diameter);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 8));
        g.drawString(coordinates, xPosition-diameter, (yPosition+(int) (diameter*1.25)));
        
        g.setColor(Color.BLACK); //color vertex ID
        g.drawString(vertexID, xPosition-diameter/4, yPosition + diameter/3);
    }
    
}
