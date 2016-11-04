/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cart;

import _Interfaces.Inventory;
import _Interfaces.Comparator;
import _Interfaces.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ryan.quinn.nelson
 */
public class StoreInventory implements Inventory {

    private List<Item> items;
    private int count;

    public StoreInventory(List<Item> items) {
        this.items = items;
        count = items.size();
    }
    
    public StoreInventory()
    {
        
    }

    @Override
    public List<Item> getItems(boolean onShelfOnly) {
        
        if(onShelfOnly) //gets list of items that are available for purchase
        {
            List<Item> onShelf = new ArrayList<>();
            for(Item item : items)
            {
                if(item.getAvailableForPurchase())
                {
                    onShelf.add(item);
                }
            }
            return onShelf;
        }
        else
        {
            return items;
        } 
    }

    @Override
    public int getCount() {
        return count;
    }
    
    

    @Override
    public void sort(Comparator<Item> comp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addItem(Item item) 
    {
        items.add(item);
    }

    @Override
    public void removeItem(Item item) 
    {
        items.remove(item);
    }

}
