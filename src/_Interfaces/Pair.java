/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

/**
 * Represents a generic pair of objects of the same type to be compared against other pairs of objects.
 * Used in GraphNavEngine for constructing the underlying graph, clustering, and for grouping vertices and bins.
 * Used in StorePlan for grouping Point objects.
 * @author Ryan
 * @param <K> generic type of each object in the pair
 * @param <V> generic type of an additional value to store for the pair
 */
public interface Pair<K,V>
{
    /**
     * Returns the additional value stored with the pair.
     * @return the additional value stored with the pair
     */
    public V getElement();

    /**
     * Sets the additional value to be stored with the pair.
     * @param element the additional value to be stored with the pair.
     */
    public void setElement(V element);

    /**
     * Returns the first object in the pair.
     * @return the first object in the pair.
     */
    public K getFirst();

    /**
     * Sets the first object in the pair.
     * @param first the first object in the pair
     */
    public void setFirst(K first);

    /**
     * Returns the second object in the pair.
     * @return the second object in the pair
     */
    public K getSecond();

    /**
     * Sets the second object in the pair.
     * @param second the second object in the pair
     */
    public void setSecond(K second);
}
