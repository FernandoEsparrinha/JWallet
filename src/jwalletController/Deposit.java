/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jwalletController;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author fernando
 */
public class Deposit  implements Serializable{
    
    /*
    Array that stores information about the transition
        Position 0 - Type of transition
        Position 1 - Ammount of money
        Position 2 - Reason of transition
        Position 3 - Date of the transition
    */
    String[] depositInformation;
    
    float amount;
    String reason;
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
    
    public Deposit(float amount, String reason){
        this.amount = amount;
        this.reason = reason;
        this.date = new Date();
        
        depositInformation = new String[4];
        depositInformation[0] = "Deposit";
        depositInformation[1] = Float.toString(amount)+" €";
        depositInformation[2] = reason;
        depositInformation[3] = dateFormat.format(getDate());
        
    }
    
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String[] getDepositInformation(){
           return depositInformation;
    }
    
    public String toString(){
        return "Deposit: "+getAmount()+" €\n Reason: "+getReason()+ " \nDate: "+ dateFormat.format(getDate());
    }
    
}
