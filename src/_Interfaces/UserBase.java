/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import User.AppUser;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author garettcarlblom
 */
public interface UserBase extends Serializable{
    public List<User> getUsers();
    public User validateUser(String username,String password);
    public void addUser(User u);
    public void removeUser(User u);
    public void replaceUser(User u);
}
