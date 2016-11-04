/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import _Interfaces.Comparator;
import _Interfaces.Item;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of a Cart to store Selections.
 * @author ryan.quinn.nelson
 */
public interface Cart extends Iterable<Selection>, Serializable {

    public List<Selection> getSelected();
    
    public int getCount();
    
    public double getTotal();
    
    
    
    public boolean addSelection(Selection s);

    public boolean removeSelection(Selection s);
        
    public void removeAll();
    
    
    @Override
    public Iterator<Selection> iterator();

    public void sort(Comparator<Item> comp);
}
