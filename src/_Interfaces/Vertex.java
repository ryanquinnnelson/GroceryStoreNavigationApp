/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * A vertex of a graph.
 * @author Ryan
 * @param <V>
 */
public interface Vertex<V>
{
    /**
     * Returns the element associated with the vertex.
     * @return 
     */
    V getElement();
    
    /**
     * Returns the unique ID associated with this vertex.
     * @return 
     */
    int getID();
}
