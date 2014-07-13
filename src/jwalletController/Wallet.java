/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jwalletController;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author fernando
 */
public class Wallet implements Serializable{
    
    public ArrayList<Deposit> deposits;
    
    public ArrayList<Withdraw> withdraws;
    
    /**
     * Name of the owner of the Wallet
     */
    private String name;
    
    /**
     * Password of the wallet
     */
    private String password;
    
    /**
     * The amount of money that the Wallet has
     */
    private float balance;
    
    /**
     * The amount of money the Wallet has spent
     */
    private float spendings;
    
    /**
     * The amount of money that the Wallet receives every month
     */
    private float salary;
    
    /**
     * Default constructor
     */
    public Wallet(){
        name = "";
        password = "";
        balance = 0;
        spendings = 0;
        salary = 0;
    }
    
    /**
     * Normal constructor
     * @param name Name of the owner
     * @param balance Amount of money the wallet has
     */
    public Wallet(String name, String password, float balance){
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.spendings = 0;
        deposits = new ArrayList();
        withdraws = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public float getBalance() {
        return balance;
    }

    public float getSpendings() {
        return spendings;
    }

    public float getSalary() {
        return salary;
    }
    
    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setSpendings(float spendings) {
        this.spendings = spendings;
    }

    public ArrayList<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(ArrayList<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
    
    public void addDeposit(float amount, String reason){
        Deposit deposit = new Deposit(amount, reason, this);

    }
    
    
    public void addWithdraw(float amount, String reason){
        
            Withdraw withdraw = new Withdraw(amount, reason, this);
        
        
    }
    
    public String toString(){
        return ("Owner: "+ getName() + "\nBalance: "+ getBalance() +"€\nSpendings: "+ getSpendings()+"€");
    }
    
}
