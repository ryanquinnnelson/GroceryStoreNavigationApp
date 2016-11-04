/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * An edge of a graph.
 * @author Ryan
 * @param <E>
 */
public interface Edge<E>
{
    /**
     * Returns the element associated with the edge.
     * @return 
     */
    E getElement();
}
