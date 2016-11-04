/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the inventory of a Store.
 * @author ryan.quinn.nelson
 */
public interface Inventory extends Serializable {

    public List<Item> getItems(boolean onShelfOnly);

    public int getCount();
    
    public void addItem(Item item);
    
    public void removeItem( Item item);
    
    void sort(Comparator<Item> comp);
}
