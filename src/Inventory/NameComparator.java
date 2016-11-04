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
 * @author grund
 */
public class NameComparator implements Comparator<Item> {

    @Override
    public int compare(Item a, Item b) {
        if (a.getName().compareTo(b.getName()) == 0) {
            return 0;
        } else if (a.getName().compareTo(b.getName()) < 0) {
            return -1;
        } else {
            return 1;
        }

    }
}
