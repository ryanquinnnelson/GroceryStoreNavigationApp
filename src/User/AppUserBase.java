/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import _Interfaces.User;
import _Interfaces.UserBase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author garrett.t.carlblom
 */
public class AppUserBase implements UserBase
{
    private List<User> users;

    public AppUserBase()
    {
        users = new ArrayList<>();
    }
    
    public AppUserBase(List<User> list)
    {
        users = list;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public User validateUser(String username, String password){
        
        for(User au : users)
        {
            if(au.getUserName().equals(username) && au.getPassword().equals(password))
            {
                return au;
            }
        }
        
        return null;
    }
    
    public void addUser(User u){
        users.add(u);
    }
    public void removeUser(User u){
        users.remove(u);
    }
    
    public void replaceUser(User u)
    {
        for(User au : users)
        {
            if(au.getUserName().equals(u.getUserName()))
            {
                users.remove(au);
                users.add(u);
            }
        }
    }


}
