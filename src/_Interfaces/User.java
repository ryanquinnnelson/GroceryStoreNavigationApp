/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import User.Address;

import java.io.Serializable;

/**
 *
 * @author garettcarlblom
 */
public interface User extends Serializable//change uml to extends 
{
    public Cart getCart();
    public String getUserName();
    public String getPassword();
    public String getEmail();
    public String getPhone();
    public boolean isAdministrator();
    public Store getCurrentStore();
    public Address getShippingAddress();
    public Card getCard();
    public void setCart(Cart cart);
    public void setUserName(String userName);
    public void setPassword(String password);
    public void setEmail(String email);
    public void setPhone(String phone);
    public void setIsAdministrator(boolean isAdministrator);
    public void setCurrentStore(Store store);
    public void setAddress(Address a);
    public void setCard(Card card);
}
