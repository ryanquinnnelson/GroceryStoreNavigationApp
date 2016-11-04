    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import _Interfaces.Card;
import _Interfaces.Store;
import _Interfaces.User;
import _Interfaces.Cart;

/**
 *
 * @author garettcarlblom
 */
public class AppUser implements User{
    
    private String userName;
    private String pasword;
    private String email;
    private String phone;
    private Cart cart;
    private boolean isAdministrator;
    private Store currentStore;
    private Address shippingAddress;
    private Card card; 

    public AppUser() {
    }
    
    

    public AppUser(String userName, String pasword, String email, String phone, Cart cart, boolean isAdministrator, Store currentStore, Address shippingAddress, Card card) {
        this.userName = userName;
        this.pasword = pasword;
        this.email = email;
        this.phone = phone;
        this.cart = cart;
        this.isAdministrator = isAdministrator;
        this.currentStore = currentStore;
        this.shippingAddress = shippingAddress;
        this.card = card;
    }
    
    
    
    
    
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return pasword;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public Store getCurrentStore() {
        return currentStore;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Card getCard() {
        return card;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String pasword) {
        this.pasword = pasword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setIsAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    public void setCurrentStore(Store currentStore) {
        this.currentStore = currentStore;
    }

    public void setAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setCard(Card Card) {
        this.card = Card;
    }
  

}
