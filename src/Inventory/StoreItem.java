/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import Navigate.Bin;
import _Interfaces.Item;

/**
 *
 * @author grund
 */
public class StoreItem implements Item {
    private String name;
    private int productCategory;
    private String supplier;
    private int quantity;
    private boolean availiableForPurchase;
    private boolean onSale;
    private double wholesaleCost;
    private double retailPrice;
    private Bin bin;

    public StoreItem(String name, int productCategory, String supplier, int quantity, boolean availiableForPurchase, boolean onSale, double wholesaleCost, double retailPrice, Bin bin) {
        this.name = name;
        this.productCategory = productCategory;
        this.supplier = supplier;    
        this.quantity = quantity;
        this.availiableForPurchase = availiableForPurchase;
        this.onSale = onSale;
        this.wholesaleCost = wholesaleCost;
        this.retailPrice = retailPrice;
        this.bin = bin;
    }
    
    public StoreItem(){
        
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getProductCategory() {
        return productCategory;
    }

    @Override
    public void setProductCategory(int productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public String getSupplier() {
        return supplier;
    }

    @Override
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public boolean getAvailableForPurchase() {
        return availiableForPurchase;
    }

    @Override
    public void setAvailableForPurchase(boolean availiableForPurchase) {
        this.availiableForPurchase = availiableForPurchase;
    }

    @Override
    public boolean getOnSale() {
        return onSale;
    }

    @Override
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    @Override
    public double getWholesaleCost() {
        return wholesaleCost;
    }

    @Override
    public void setWholesaleCost(double wholesaleCost) {
        this.wholesaleCost = wholesaleCost;
    }

    @Override
    public double getRetailPrice() {
        return retailPrice;
    }

    @Override
    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public Bin getBin() {
        return bin;
    }

    @Override
    public void setBin(Bin bin) {
        this.bin = bin;
    }   
}
