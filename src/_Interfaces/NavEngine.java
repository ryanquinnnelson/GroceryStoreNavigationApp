/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;


import Navigate.Bin;
import java.awt.Graphics;
import java.util.List;

/**
 * Interface represents a system for calculating the shortest (fastest) route in a store, given a particular list of items.
 * @author Ryan
 */
public interface NavEngine 
{
    
    
    /**
     * Draws the underlying graph structure used to calculate routes. 
     * @param g Graphics object to draw this
     */
    void paint(Graphics g);
    
    /**
     * Calculates the shortest (fastest) route in a store from Entrance to Checkout, given an unordered list of Bins to visit.
     * @param list Bins to visit in the store
     * @return ordered list of Bins, representing this fastest route to visit selected bins
     */
    List<Bin> fastestRoute(List<Bin> list);
    
    /**
     * Returns a String representation of a grocery list, describing items to get in the store and their locations.
     * @param route ordered list of Bins, representing the route order to visit Bins
     * @param select list of all Selections which contains the same Bins as the first list (albeit out of order)
     * @return a String representation of a grocery list
     */
    String getDirections(List<Bin> route, List<Selection> select);
}
