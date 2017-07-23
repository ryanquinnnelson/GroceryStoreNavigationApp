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
        Selection s6 = new CartSelection(store.getInventory().getItems(false).get(5),3);
        Selection s7 = new CartSelection(store.getInventory().getItems(false).get(6),2);
        Selection s8 = new CartSelection(store.getInventory().getItems(false).get(7),4);
        Selection s9 = new CartSelection(store.getInventory().getItems(false).get(8),1);
        Selection s10 = new CartSelection(store.getInventory().getItems(false).get(9),2);
        Selection s11 = new CartSelection(store.getInventory().getItems(false).get(10), 5);
        Selection s12 = new CartSelection(store.getInventory().getItems(false).get(11), 1);
        Selection s13 = new CartSelection(store.getInventory().getItems(false).get(12),15);
        Selection s14 = new CartSelection(store.getInventory().getItems(false).get(13), 2);
        Selection s15 = new CartSelection(store.getInventory().getItems(false).get(14),1);
        Selection s16 = new CartSelection(store.getInventory().getItems(false).get(15),3);
        Selection s17 = new CartSelection(store.getInventory().getItems(false).get(16),2);
        Selection s18 = new CartSelection(store.getInventory().getItems(false).get(17),4);
        Selection s19 = new CartSelection(store.getInventory().getItems(false).get(18),1);
        Selection s20 = new CartSelection(store.getInventory().getItems(false).get(19),2);
        
        Selection s21 = new CartSelection(store.getInventory().getItems(false).get(20), 5);
        Selection s22 = new CartSelection(store.getInventory().getItems(false).get(21), 1);
        Selection s23 = new CartSelection(store.getInventory().getItems(false).get(22),15);
        Selection s24 = new CartSelection(store.getInventory().getItems(false).get(23), 2);
        Selection s25 = new CartSelection(store.getInventory().getItems(false).get(24),1);
        Selection s26 = new CartSelection(store.getInventory().getItems(false).get(25),3);
        Selection s27 = new CartSelection(store.getInventory().getItems(false).get(26),2);
        Selection s28 = new CartSelection(store.getInventory().getItems(false).get(27),4);
        Selection s29 = new CartSelection(store.getInventory().getItems(false).get(28),1);
        Selection s30 = new CartSelection(store.getInventory().getItems(false).get(29),2);
        Selection s31 = new CartSelection(store.getInventory().getItems(false).get(30), 5);
        Selection s32 = new CartSelection(store.getInventory().getItems(false).get(31), 1);
        Selection s33 = new CartSelection(store.getInventory().getItems(false).get(32),15);
        Selection s34 = new CartSelection(store.getInventory().getItems(false).get(33), 2);
        Selection s35 = new CartSelection(store.getInventory().getItems(false).get(34),1);
        Selection s36 = new CartSelection(store.getInventory().getItems(false).get(35),3);
        Selection s37 = new CartSelection(store.getInventory().getItems(false).get(36),2);
        Selection s38 = new CartSelection(store.getInventory().getItems(false).get(37),4);
        Selection s39 = new CartSelection(store.getInventory().getItems(false).get(38),1);
        Selection s40 = new CartSelection(store.getInventory().getItems(false).get(39),2);
        
        
        
        List<Selection> temp = new ArrayList<>();
        temp.add(s1);
        temp.add(s2);
        temp.add(s3);
        temp.add(s4);
        temp.add(s5);
        temp.add(s6);
        temp.add(s7);
        temp.add(s8);
        temp.add(s9);
        temp.add(s10);
        temp.add(s11);
        temp.add(s12);
        temp.add(s13);
        temp.add(s14);
        temp.add(s15);
        temp.add(s16);
        temp.add(s17);
        temp.add(s18);
        temp.add(s19);
        temp.add(s20);
        
        temp.add(s21);
        temp.add(s22);
        temp.add(s23);
        temp.add(s24);
        temp.add(s25);
        temp.add(s26);
        temp.add(s27);
        temp.add(s28);
        temp.add(s29);
        temp.add(s30);
        temp.add(s31);
        temp.add(s32);
        temp.add(s33);
        temp.add(s34);
        temp.add(s35);
        temp.add(s36);
        temp.add(s37);
        temp.add(s38);
        temp.add(s39);
        temp.add(s40);
        
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
