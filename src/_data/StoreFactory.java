/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _data;

import Inventory.FoodItem;
import Inventory.HouseholdItem;
import Navigate.StorePlan;
import User.Address;
import User.GroceryStore;
import User.Promotion;
import _Interfaces.Item;
import _Interfaces.Plan;
import _Interfaces.Store;
import _Interfaces.Inventory;
import Cart.StoreInventory;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructs default example of a store, for use in GUI.
 * @author Ryan
 */
public class StoreFactory 
{
    
    
    private StoreFactory()
    {
        
    }
    
    /**
     * Factory method constructs and returns a default Store.
     * @return Store with default values. 
     */
    public static Store getStoreInstance()
    {
        Store store = new GroceryStore("The Market", getInventory(), getPromotions(), getAddress());
        
        return store;
    }
    
    
    /**
     * Returns a default address.
     * @return Address with default values.
     */
    private static Address getAddress()
    {
        Address a = new Address("15th St. S", "Suite 33", "Fargo", "ND", 58102);
        return a;
    }
    
    /**
     * Returns a list of default promotions.
     * @return List of promotions with default values.
     */
    private static List<Promotion> getPromotions()
    {
        List<Promotion> p = new ArrayList<>();
        p.add(new Promotion("New User", "WELCOME", 0.10));
        p.add(new Promotion("Fourth of July", "4THJULY", 0.20));
        return p;
    }
 
    
    /**
     * Returns a default Inventory.
     * @return Inventory with default values.
     */
    private static Inventory getInventory()
    {
        Inventory invent = new StoreInventory(getItems());
        
        return invent;
    }
    
    /**
     * Returns a list of default Items.
     * @return List of Items with default values.
     */
    private static List<Item> getItems()
    {
        List<Item> items = new ArrayList<>();
        
        Plan plan   = new StorePlan(25,70,900, Color.LIGHT_GRAY); //to know what the bins are
        String facts =  "Serving Size 		2 Cookies\n" +
                        "Servings Per Container	About 10\n" +
                        "____________________________________\n" +
                        "\n" +
                        "Calories		 140	\n" +
                        "Calories from Fat	   70\n" +
                        "___________________________________\n" +
                        "\n" +
                        "Total Fat	         7 g	11 %\n" +
                        "  Saturated Fat	         3 g	15 %\n" +
                        "  Trans Fat	         0 g	\n" +
                        "\n" +
                        "Cholesterol	         5 mg	  2 %\n" +
                        "\n" +
                        "Sodium	       70 mg	  3 %\n" +
                        "\n" +
                        "...";
        
        //food
        Item i1    = new FoodItem("Carrot", 1,"Johnson's Farm", 15, true, false, 0.03, 0.20, plan.getBin(44), facts, "pound", 10, "Produce", "July 1, 2016", "Vegan");
        Item i2    = new FoodItem("Bagel", 1, "Baker Five", 5, true, false, 0.30, 0.90, plan.getBin(68), facts, "unit", 15, "Breads","July 15, 2016", "Vegetarian");
        Item i3  = new FoodItem("Milk", 1, "Billy's Dairy" , 1, true, false, 0.50, 2.00, plan.getBin(14), facts, "ounce", 30, "Dairy", "June 29, 2016",  "Vegetarian");
        Item i4   = new FoodItem("Steak", 1, "Bob's Butcher", 1, true, false, 3.00, 10.00, plan.getBin(21), facts, "pound", 5,"Meats", "July 5, 2016", "Gluten-free");
        
        //household
        Item i5    = new HouseholdItem("Paper Towel", 0, "Supply Co", 50, true, true, 0.60, 1.40, plan.getBin(55),false);
        Item i6    = new HouseholdItem("Toilet Paper", 0, "Supply Co", 50, true, true, 0.50, 1.30, plan.getBin(56),false);
        Item i7    = new FoodItem("Spinach", 1,"Johnson's Farm", 15, true, false, 0.15, 0.50, plan.getBin(45), facts, "pound", 10, "Produce", "July 1, 2016", "Vegan");
        Item i8    = new FoodItem("Wheat Bread", 1, "Baker Five", 5, true, false, 1.00, 1.50, plan.getBin(69), facts, "unit", 15, "Breads","July 15, 2016", "Vegetarian");
        Item i9    = new FoodItem("Cheese", 1, "Billy's Dairy" , 1, true, false, 1.00, 3.00, plan.getBin(15), facts, "ounce", 30, "Dairy", "June 29, 2016",  "Vegetarian");
        Item i10   = new FoodItem("Chicken", 1, "Bob's Butcher", 1, true, false, 2.00, 8.00, plan.getBin(22), facts, "pound", 5,"Meats", "July 5, 2016", "Gluten-free");
        Item i11   = new HouseholdItem("SHAVING Cream", 0, "Supply Co", 50, true, true, 0.60, 1.40, plan.getBin(58),false);
        Item i12   = new HouseholdItem("Shaving Razers", 0, "Supply Co", 50, true, true, 2.00, 5.00, plan.getBin(53),false);
        Item i13   = new FoodItem("Lettuce", 1,"Johnson's Farm", 15, true, false, 0.20, 0.75, plan.getBin(41), facts, "pound", 10, "Produce", "July 1, 2016", "Vegan");
        Item i14   = new FoodItem("Donuts", 1, "Baker Five", 5, true, false, 1.50, 2.50, plan.getBin(70), facts, "unit", 15, "Breads","July 15, 2016", "Vegetarian");
        Item i15   = new FoodItem("Yogurt", 1, "Billy's Dairy" , 1, true, false, 1.50, 4.00, plan.getBin(1), facts, "ounce", 30, "Dairy", "June 29, 2016",  "Vegetarian");
        Item i16   = new FoodItem("Turkey", 1, "Bob's Butcher", 1, true, false, 1.50, 5.00, plan.getBin(20), facts, "pound", 5,"Meats", "July 5, 2016", "Gluten-free");
        Item i17   = new FoodItem("Sour Cream", 1, "Billy's Dairy" , 1, true, false, 1.00, 2.00, plan.getBin(12), facts, "ounce", 30, "Dairy", "June 29, 2016",  "Vegetarian");
        Item i18   = new FoodItem("Tilapia", 1, "Bob's Butcher", 1, true, false, 5.00, 11.50, plan.getBin(64), facts, "pound", 5,"Meats", "July 5, 2016", "Gluten-free");
        Item i19   = new HouseholdItem("Shampoo", 0, "Supply Co", 50, true, true, 5.00, 10.00, plan.getBin(37),false);
        Item i20   = new HouseholdItem("Deodorant", 0, "Supply Co", 50, true, true, 4.50, 7.50, plan.getBin(33),false);
        
        
        //household 2
        Item i21    =new HouseholdItem("Band-Aids", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(2), false); 
        Item i22    =new HouseholdItem("Stapler", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(3), false); 
        Item i23    =new HouseholdItem("Calculator", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(5), false); 
        Item i24    =new HouseholdItem("Napkins", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(8), false); 
        Item i25    =new HouseholdItem("Bug spray", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(19), false); 
        Item i26    =new HouseholdItem("Light bulb", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(24), false); 
        Item i27    =new HouseholdItem("Sunglasses", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(26), false); 
        Item i28    =new HouseholdItem("Paper plates", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(28), false); 
        Item i29    =new HouseholdItem("Oven rack", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(32), false); 
        Item i30    =new HouseholdItem("Scissors", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(38), false); 
        Item i31    =new HouseholdItem("Spatula", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(49), false); 
        Item i32    =new HouseholdItem("Frying pan", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(50), false); 
        Item i33    =new HouseholdItem("Parchment paper", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(51), false); 
        Item i34    =new HouseholdItem("Toilet bowl cleaner", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(60), false); 
        Item i35    =new HouseholdItem("Knife stand", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(63), false); 
        Item i36    =new HouseholdItem("Sharpies", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(72), false); 
        Item i37    =new HouseholdItem("Pens", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(77), false); 
        Item i38    =new HouseholdItem("Looseleaf paper", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(78), false); 
        Item i39    =new HouseholdItem("Pencils", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(79), false); 
        Item i40    =new HouseholdItem("Notebook", 0,"J&J", 50,true,true,0.80,2.10, plan.getBin(61), false); 
        
        
        items.add(i1);
        items.add(i2);
        items.add(i3);
        items.add(i4);
        items.add(i5);
        items.add(i6);
        items.add(i7);
        items.add(i8);
        items.add(i9);
        items.add(i10);
        items.add(i11);
        items.add(i12);
        items.add(i13);
        items.add(i14);
        items.add(i15);
        items.add(i16);
        items.add(i17);
        items.add(i18);
        items.add(i19);
        items.add(i20);
        
        items.add(i21);
        items.add(i22);
        items.add(i23);
        items.add(i24);
        items.add(i25);
        items.add(i26);
        items.add(i27);
        items.add(i28);
        items.add(i29);
        items.add(i30);
        items.add(i31);
        items.add(i32);
        items.add(i33);
        items.add(i34);
        items.add(i35);
        items.add(i36);
        items.add(i37);
        items.add(i38);
        items.add(i39);
        items.add(i40);
        
        
        
        
        
        return items;
    }
}
