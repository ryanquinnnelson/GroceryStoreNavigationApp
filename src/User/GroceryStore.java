/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import _Interfaces.Store;
import _Interfaces.Inventory;
import java.util.List;

/**
 *
 * @author garettcarlblom
 */
public class GroceryStore implements Store{
    private Inventory       inventory;
    private List<Promotion> promotions;
    private Address         address;
    private String          name;

    public GroceryStore() {
    }

    public GroceryStore(String name, Inventory inventory, List<Promotion> promotions, Address address) {
        this.name = name;
        this.inventory = inventory;
        this.promotions = promotions;
        this.address = address;
    }
    
    public void setInventory(Inventory invent)
    {
        inventory = invent;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void addPromotion(Promotion p)
    {
        promotions.add(p);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
