/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import _Interfaces.Comparator;
import _Interfaces.Item;

/**
 *
 * @author Default
 */
public class CategoryComparator implements Comparator<Item> {

    @Override
    public int compare(Item a, Item b) {
        if (a.getProductCategory() < b.getProductCategory()) {
            return -1;
        } else if (a.getProductCategory() == b.getProductCategory()) {
            return 0;
        } else {
            return 1;
        }

    }

}
