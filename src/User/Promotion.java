/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.io.Serializable;

/**
 *
 * @author garettcarlblom
 */
public class Promotion implements Serializable {
    private String description;
    private String code;
    private double discount;

    public Promotion() {
    }

    public Promotion(String description, String code, double discount) {
        this.description = description;
        this.code = code;
        this.discount = discount;
    }
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    
}
