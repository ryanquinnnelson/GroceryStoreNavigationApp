/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _data;

import _Interfaces.Cart;
import Cart.CartSelection;
import _Interfaces.Selection;
import Cart.StoreCart;
import User.Address;
import User.AppUser;
import User.PaymentCard;
import _Interfaces.Card;
import _Interfaces.Store;
import _Interfaces.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan
 */
public class UserFactory 
{
    private UserFactory()
    {
        
    }
    
    /**
     * Returns a default User for use in GUI.
     * @param isAdmin if true, user can manage inventory in application
     * @return default user
     */
    public static User getUserInstance(boolean isAdmin)
    {
        Store storeTest = StoreFactory.getStoreInstance();
        User first;
        String username;
        String password;
        String email = "dArthur5@gmail.com";
        String phone = "(871) 917-0817";
        
        if(isAdmin)
        {
            username = "admin";
            password = "admin";
            first = new AppUser(username,password,email,phone,getCart(storeTest),isAdmin,storeTest,getAddress(),getCard());
        }
        else
        {
            username = "first";
            password = "first";
            
            
            first = new AppUser(username,password,email,phone,getCart(storeTest),isAdmin,storeTest,getAddress(),getCard());
        }
        
        return first;
    }
    
    /**
     * Returns a default Cart.
     * @param store
     * @return a default Cart.
     */
    public static Cart getCart(Store store)
    {
        Cart c = new StoreCart(getSelected(store));
        
        return c;
    }
    
    /**
     * Returns a default address.
     * @return Address with default values.
     */
    public static Address getAddress()
    {
        Address a = new Address("5th St. N", "Apt 4", "Fargo", "ND", 58102);
        return a;
    }
    
    /**
     * Returns a default list of Selections.
     * @param store
     * @return a default list of Selections
     */
    public static List<Selection> getSelected(Store store)
    {
        
        Selection s1 = new CartSelection(store.getInventory().getItems(false).get(0), 5);
        Selection s2 = new CartSelection(store.getInventory().getItems(false).get(1), 1);
        Selection s3 = new CartSelection(store.getInventory().getItems(false).get(2),15);
        Selection s4 = new CartSelection(store.getInventory().getItems(false).get(3), 2);
        Selection s5 = new CartSelection(store.getInventory().getItems(false).get(4),1);
        
        List<Selection> temp = new ArrayList<>();
        temp.add(s1);
        temp.add(s2);
        temp.add(s3);
        temp.add(s4);
        temp.add(s5);
        
        return temp;
    }
    
    /**
     * Returns a default Card.
     * @return a default Card
     */
    public static Card getCard()
    {
        Card c = new PaymentCard(getAddress(),8717282781728292L,"John Smith", "Credit",1000);
        
        return c;
    }

}
