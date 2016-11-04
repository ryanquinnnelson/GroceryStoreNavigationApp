/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import User.Address;
import User.Promotion;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author garettcarlblom
 */
public interface Store extends Serializable{
    public String getName();
    public void setName( String name);
    public Inventory getInventory();
    public Address getAddress();
    public List<Promotion> getPromotions();
    public void setAddress(Address a);
    public void addPromotion(Promotion p);
    public void setInventory(Inventory invent);
  
}
