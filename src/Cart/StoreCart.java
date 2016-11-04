/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import _Interfaces.Selection;
import _Interfaces.Cart;
import _Interfaces.Comparator;
import _Interfaces.Item;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Concrete implementation of Cart interface for a Store.
 * @author ryan.quinn.nelson
 */
public class StoreCart implements Cart
{
    
    private List<Selection> selected;
    private int count;
    private double total;
    
    public StoreCart()
    {
        selected = new ArrayList<>();
    }
    
    public StoreCart(List<Selection> select)
    {
        selected = select;
        count = select.size();
        total = 0;
        
        for(Selection s : select)
        {
            total += s.getValue();
        }
    }

    @Override
    public List<Selection> getSelected() 
    {
        return selected;
    }

    @Override
    public int getCount() 
    {
        return selected.size();
    }

    @Override
    public double getTotal() 
    {        
        return total;
    }

    @Override
    public boolean addSelection(Selection s) 
    {
        total += s.getValue();  //update total
        
        return selected.add(s);
    }

    @Override
    public boolean removeSelection(Selection s) 
    {
        total -= s.getValue(); //update total
        
        return selected.remove(s);
    }

    @Override
    public void removeAll() 
    {
        total = 0;
        count = 0;
        selected = new ArrayList<>();
    }

    @Override
    public Iterator<Selection> iterator() {
        return selected.iterator();
    }

    @Override
    public void sort(Comparator<Item> comp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
