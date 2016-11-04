/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import _Interfaces.Item;
import java.io.Serializable;

/**
 * Represents a user's selection of an Item to put into his / her cart.
 * @author ryan.quinn.nelson
 */
public interface Selection extends Serializable {

    public Item getItem();

    public double getValue();

    public int getCount();

    @Override
    public boolean equals(Object o);
}


