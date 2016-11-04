/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import _Interfaces.Card;

/**
 *
 * @author garettcarlblom
 */
public class PaymentCard implements Card{
    private Address billingAddress;
    private long cardNumber;
    private String customer;
    private String cardType;
    private int balance;//needed a new variable for the charge method
    
    public PaymentCard() {
    }

    public PaymentCard(Address billingAddress, long cardNumber, String customer,  String cardType,int balance) {
        this.billingAddress = billingAddress;
        this.cardNumber = cardNumber;
        this.customer = customer;
        this.cardType = cardType;
        this.balance=balance;
    }

    

    
    
    
    public Address getAddress() {
        return billingAddress;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public void setCardType(String cardType) {
        this.cardType = cardType;
    }


    public String getCardType() {
        return cardType;
    }

    public boolean charge(int amt) {
        if(amt> balance){
            return false;
        }
        else{
            return true;
        }
    }
    
    
    
}
