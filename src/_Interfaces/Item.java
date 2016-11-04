/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import Navigate.Bin;
import java.io.Serializable;

/**
 *
 * @author grund
 */
public interface Item extends Serializable {

    public String getName();

    public void setName(String name);

    public int getProductCategory();

    public void setProductCategory(int productCategory);

    public String getSupplier();

    public void setSupplier(String supplier);
    
    public int getQuantity();
    
    public void setQuantity(int quantity);

    public boolean getAvailableForPurchase();

    public void setAvailableForPurchase(boolean availiableForPurchase);

    public boolean getOnSale();

    public void setOnSale(boolean onSale);

    public double getWholesaleCost();

    public void setWholesaleCost(double wholesaleCost);

    public double getRetailPrice();

    public void setRetailPrice(double retailPrice);

    public Bin getBin();

    public void setBin(Bin bin);

}
