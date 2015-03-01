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
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class Withdraw implements Serializable{

    /*
    Array that stores information about the transition
        Position 0 - Type of transition
        Position 1 - Ammount of money
        Position 2 - Reason of transition
        Position 3 - Date of the transition
    */
    String[] withdrawInformation;
    
    float amount;
    String reason;
    Date date;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

    public Withdraw(float amount, String reason) {
            this.amount = amount;
            this.reason = reason;
            this.date = new Date();
            
            withdrawInformation = new String[4];
            withdrawInformation[0] = "Withdraw";
            withdrawInformation[1] = Float.toString(amount)+" €";
            withdrawInformation[2] = reason;
            withdrawInformation[3] = dateFormat.format(getDate());
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
    
    public String[] getWithdrawInformation(){
           return withdrawInformation;
    }
    
    public String toString(){
        return "Withdraw: "+getAmount()+" Reason: "+getReason()+ " Date: "+ dateFormat.format(getDate());
    }

}
