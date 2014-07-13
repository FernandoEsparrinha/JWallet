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

    float amount;
    String reason;
    Date date;
    Wallet w;

    public Withdraw(float amount, String reason, Wallet w) {
            this.amount = amount;
            this.reason = reason;
            date = new Date();
            w.setBalance(w.getBalance() - amount);
            w.getWithdraws().add(this);
            w.setSpendings(w.getSpendings() + amount);
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
    
    
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        return "Withdraw: "+getAmount()+" Reason: "+getReason()+ " Date: "+ dateFormat.format(getDate());
    }

}
