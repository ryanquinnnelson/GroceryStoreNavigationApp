/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;




import Navigate.GraphNavEngine;
import Navigate.StorePlan;
import User.AppUserBase;

import _Interfaces.NavEngine;
import _Interfaces.Plan;
import _Interfaces.Store;
import _Interfaces.User;
import _Interfaces.UserBase;
import _data.StoreFactory;
import _data.UserFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ryan
 */
public class FrameMain extends javax.swing.JFrame 
{

    //JFrames
    private FrameLogin frameLogin; //a login JFrame
   
    //other fields
    private Container   contents;   //container to hold Components of FrameMain
    private Plan        plan;       //floor plan of a store
    private NavEngine   nav;        //navigation system for routes in that store
    private User        user;       //current user of application
    private List<Store> stores;     //list of all available stores
    private UserBase    base;       //current UserBase object


    
    /**
     * Creates new form SearchFrame
     * @param user current user
     * @param ub current userBase
     */
    public FrameMain(User user, UserBase ub) 
    {
        super("Grocery Navigator");       
        initComponents();
        
        //initialize fields
        plan = new StorePlan(25,70,900, Color.LIGHT_GRAY);  //constructs Plan object
        nav = new GraphNavEngine(plan, 3);                  //constructs NavEngine object
        base = readUserBase("src\\_data\\user_accounts.txt");                   //TEMP until compatible
        stores = readStores("src\\_data\\stores.txt");      //gets list of stores from file
        
        this.user = user;
        updateUserStore();  //sets store for user as most recent version of store
       
        //display Add Grocery Page first
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelAddGroceries());
        displayPanel.validate();
        displayPanel.repaint(); 
        
        if(!user.isAdministrator()) 
        {
            inventoryButton.setEnabled(false); //only administrator can edit inventory for store
        }
    }
    
    /**
     * Sets the most recent version of store for the user.
     * @param user current user
     */
    private void updateUserStore()
    {
        String storeName = user.getCurrentStore().getName();

        for(Store s : stores)
        {
            if(s.getName().equals(storeName))
            {
                user.setCurrentStore(s);
            }
        }
        
    }
    
    /**
     * Outputs current list of all stores to file.
     * @param filename String representation of filename
     */
    private void saveStores(String filename)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(stores);    //writes stores object to a file
            oos.close();
            
        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File Not Found " + fnfe);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    /**
     * Inputs current list of all stores from file.
     * @param filename String representation of filename
     * @return list of all stores
     */
    private List<Store> readStores(String filename)
    {
        List<Store> list = new ArrayList<>();
        list.add(StoreFactory.getStoreInstance()); //just in case try doesn't work the first time
        
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            list = (List<Store>) ois.readObject();
            ois.close();
        } 
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File Not Found " + fnfe);
        }
        catch(IOException ioe)
        {
            System.out.println("IO Exception " + ioe);
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println("Class Not Found " + cnfe);
        }
        
        return list;
    }
    
    /**
     * Replaces old version of user in base with new version and outputs current UserBase object to file.
     * @param filename String representation of filename
     */
    private void saveUserBase(String filename)
    {
        base.replaceUser(user); //replaces old version of user in base with new version
        
        
        try
        {
            FileOutputStream fos = new FileOutputStream(filename, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(base);    //writes base object to a file
            oos.close();
            
        }
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File Not Found " + fnfe);
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }
    }
    
    /**
     * Inputs current UserBase of all Users from file.
     * @param filename String representation of filename
     * @return UserBase
     */
    private UserBase readUserBase(String filename)
    {
        List<User> temp = new ArrayList<>();
        temp.add(UserFactory.getUserInstance(false));
        UserBase b = new AppUserBase(temp); //failsafe if try doesn't work the first time
        
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            b = (UserBase) ois.readObject();
            ois.close();
        } 
        catch(FileNotFoundException fnfe)
        {
            System.out.println("File Not Found " + fnfe);
        }
        catch(IOException ioe)
        {
            System.out.println("IO Exception " + ioe);
        }
        catch(ClassNotFoundException cnfe)
        {
            System.out.println("Class Not Found " + cnfe);
        }
        
        return b;
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inventoryButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        groceryButton = new javax.swing.JButton();
        cartButton = new javax.swing.JButton();
        navButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        displayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1250, 600));
        setPreferredSize(new java.awt.Dimension(1250, 800));
        setResizable(false);

        inventoryButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        inventoryButton.setText("Manage Inventory");
        inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryButtonActionPerformed(evt);
            }
        });

        profileButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        profileButton.setText("User Profile");
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileButtonActionPerformed(evt);
            }
        });

        groceryButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        groceryButton.setText("Add Groceries");
        groceryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groceryButtonActionPerformed(evt);
            }
        });

        cartButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        cartButton.setText("Shopping Cart");
        cartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartButtonActionPerformed(evt);
            }
        });

        navButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        navButton.setText("Map & Grocery List");
        navButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navButtonActionPerformed(evt);
            }
        });

        logoutButton.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        displayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inventoryButton)
                .addGap(18, 18, 18)
                .addComponent(profileButton)
                .addGap(18, 18, 18)
                .addComponent(groceryButton)
                .addGap(18, 18, 18)
                .addComponent(cartButton)
                .addGap(18, 18, 18)
                .addComponent(navButton)
                .addGap(18, 18, 18)
                .addComponent(logoutButton)
                .addGap(33, 33, 33))
            .addComponent(displayPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(navButton)
                    .addComponent(inventoryButton)
                    .addComponent(cartButton)
                    .addComponent(groceryButton)
                    .addComponent(profileButton)
                    .addComponent(logoutButton))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Clicking on this button switches to the JPanel showing the Store Inventory.
     * @param evt 
     */
    private void inventoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryButtonActionPerformed

        displayPanel.removeAll();
        
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelInventory());
        displayPanel.validate();
        displayPanel.repaint();
    }//GEN-LAST:event_inventoryButtonActionPerformed

    /**
     * Clicking on this button switches to the JPanel showing the User Profile.
     * @param evt 
     */
    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed

        displayPanel.removeAll();
        
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelUserProfile(user));
        displayPanel.validate();
        displayPanel.repaint();
    }//GEN-LAST:event_profileButtonActionPerformed

    /**
     * Clicking on this button switches to the JPanel showing Add Groceries.
     * @param evt 
     */
    private void groceryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groceryButtonActionPerformed

        displayPanel.removeAll();
        
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelAddGroceries());
        displayPanel.validate();
        displayPanel.repaint();
    }//GEN-LAST:event_groceryButtonActionPerformed

    /**
     * Clicking on this button switches to the JPanel showing the Shopping Cart.
     * @param evt 
     */
    private void cartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartButtonActionPerformed

        displayPanel.removeAll();
        
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelShoppingCart());
        displayPanel.validate();
        displayPanel.repaint();
    }//GEN-LAST:event_cartButtonActionPerformed

    /**
     * Clicking on this button switches to the JPanel showing the Navigation and directions.
     * @param evt 
     */
    private void navButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navButtonActionPerformed
        displayPanel.removeAll();
        
        displayPanel.setLayout(new BorderLayout(0,0));

        displayPanel.add(new PanelMap(plan, nav, user));
        displayPanel.validate();
        displayPanel.repaint();
    }//GEN-LAST:event_navButtonActionPerformed

    /**
     * Clicking on this button closes the FrameMain and opens a new FrameLogin.
     * @param evt 
     */
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

        frameLogin = new FrameLogin();
        frameLogin.setVisible(true);
        saveStores("src\\_data\\stores.txt");   //saves the current list of stores
        saveUserBase("src\\_data\\user_accounts.txt");  //saves current list of AppUsers
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cartButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton groceryButton;
    private javax.swing.JButton inventoryButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton navButton;
    private javax.swing.JButton profileButton;
    // End of variables declaration//GEN-END:variables

    
    
}
