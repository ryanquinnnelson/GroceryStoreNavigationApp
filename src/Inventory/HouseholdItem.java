/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import Navigate.Bin;

/**
 *
 * @author grund
 */
public class HouseholdItem extends StoreItem {
    private boolean hazardous;

    public HouseholdItem(String name, int productCategory, String supplier, int quantity, boolean availiableForPurchase, boolean onSale, double wholesaleCost, double retailPrice, Bin bin,boolean hazardous) {
        super(name, productCategory, supplier, quantity, availiableForPurchase, onSale, wholesaleCost, retailPrice, bin);
        this.hazardous=hazardous;
        
    }

    public HouseholdItem() {
    }
    
    public boolean isHazardous(){
        return hazardous;
    }

    public void setHazardous(boolean hazardous) {
        this.hazardous = hazardous;
    }
    
    
    
    
    
    
    
}
