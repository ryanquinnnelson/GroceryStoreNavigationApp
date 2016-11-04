/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import Navigate.Bin;
import Navigate.PointPair;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a floor plan that contains Bin objects, PointPairs, and can be drawn.
 * @author Ryan
 */
public interface Plan 
{
    /**
     * Returns a list of all Bin objects in the Plan.
     * @return a list of all Bin objects in the Plan
     */
    List<Bin> getBins();
    
    /**
     * Returns the nth Bin created during the Plan's construction.
     * @param n represents the 'index' of Bin in plan
     * @return nth Bin created in the Plan
     */
    Bin getBin(int n);
    
    /**
     * Returns a count of the number of Bins in the Plan.
     * @return a count of the number of Bins in the Plan
     */
    int getBinCount();
    
    /**
     * Returns the PointPair representing the Entry of the store and the intersection it attaches to.
     * This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
     * @return the PointPair representing the Entry of the store and the intersection it attaches to.
     */
    PointPair getEntryPair();
    
    /**
     * Returns the PointPair representing the Checkout of the store and the intersection it attaches to.
     * This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
     * @return the PointPair representing the Checkout of the store and the intersection it attaches to.
     */
    PointPair getExitPair();
    
    /**
     * Returns an array of PointPairs representing all intersections and major nodes in the Plan.
     * This is so that the navigation graph can coordinate with the scaled and shifted version of the plan.
     * @return an array of PointPairs representing all intersections and major nodes in the Plan.
     */
    PointPair[] getPaths();
    
    /**
     * Draws the given Bin objects in given color on the Plan, to 'highlight' specific Bins.
     * @param g Graphics objects on which Bin is drawn
     * @param selected Bin objects selected by customer
     * @param c the color the objects should be displayed as
     */
    void highlightSelected(Graphics g, List<Bin> selected, Color c);
    
    /**
     * Draws a visual representation of Plan object.
     * @param g Graphics object to draw representation on
     */
    void paint(Graphics g);
    
}
