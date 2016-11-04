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
public interface Card extends Serializable{
    public boolean charge(int amt);
    
    public String getCustomer();
    public long getCardNumber();
    public Address getAddress();
    public String getCardType();
    public void setBillingAddress(Address billingAddress);

    public void setCardNumber(long cardNumber);

    public void setCustomer(String customer);


    public void setCardType(String cardType);
    
}
