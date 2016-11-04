/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import _Interfaces.Selection;
import _Interfaces.Item;

/**
 * Concrete implementation of Selection interface for a selection added to a cart.
 * @author ryan.quinn.nelson
 */
public class CartSelection implements Selection {

    private Item item;
    private int count;
    private double total;

    public CartSelection(Item select, int count) {
        item = select;
        this.count = count;
        
        total = select.getRetailPrice() * count;
                
    }

    public CartSelection() {
        //empty on purpose
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public double getValue() {
        return item.getRetailPrice() * count;
    }
    
    
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof  CartSelection))
        {
            return false;
        }
        else
        {
            CartSelection other = (CartSelection) o;
            
            boolean b1 = this.item.getName().equals(other.item.getName());
            boolean b2 = this.count == other.count;
            
            return b1 && b2;
        }
    }

}
