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
public class FoodItem extends StoreItem{
    private String nutritional;
    private String unit;
    private int count;
    private String foodCategory;
    private String expiration;
    private String dietaryCategory;

    public FoodItem(String name, int productCategory, String supplier, int quantity, boolean availiableForPurchase, boolean onSale, double wholesaleCost, double retailPrice, Bin bin, String nutritional, String unit, int count, String foodCategory, String expiration, String dietaryCategory) {
        super(name, productCategory, supplier, quantity, availiableForPurchase, onSale, wholesaleCost, retailPrice, bin);
        this.nutritional = nutritional;
        this.unit = unit;
        this.count = count;
        this.foodCategory = foodCategory;
        this.expiration = expiration;
        this.dietaryCategory = dietaryCategory;
    }
    
    public FoodItem(){
        
    }

    public String getNutritional() {
        return nutritional;
    }

    public void setNutritional(String nutritional) {
        this.nutritional = nutritional;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getDietaryCategory() {
        return dietaryCategory;
    }

    public void setDietaryCategory(String dietaryCategory) {
        this.dietaryCategory = dietaryCategory;
    }
    
    
    
    
    
    
    
    
    
}
