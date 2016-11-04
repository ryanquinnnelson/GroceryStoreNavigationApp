/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;

import java.util.Comparator;

/**
 * Implements a comparator based on natural ordering of its element type.
 * 
 * 
 * @author Ryan
 * @param <E>
 */
public class DefaultComparator<E> implements Comparator<E>
{
    /**
     * Compares two Comparable objects.
     * @param a
     * @param b
     * @return
     * @throws ClassCastException 
     */
    @Override
    public int compare(E a, E b) throws ClassCastException
    {
        Comparable<E> comparableA = (Comparable<E>) a;
        return comparableA.compareTo(b);
    }
}
