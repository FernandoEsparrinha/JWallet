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
    
    String array[];
    float amount;
    String reason;
    Date date;
    Wallet w;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
    
    public Deposit(float amount, String reason, Wallet w){
        this.amount = amount;
        this.reason = reason;
        date = new Date();
        w.setBalance(w.getBalance() + amount);
        w.getDeposits().add(this);
        array[0] = "Deposit";
        array[1] = Float.toString(amount);
        array[2] = reason;
        array[3] = dateFormat.format(getDate());
        
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
    
    public String[] getArrayDeposit(){
           return array;
    }
    
    public String toString(){
        return "Deposit: "+getAmount()+" Reason: "+getReason()+ " Date: "+ dateFormat.format(getDate());
    }
    
}
