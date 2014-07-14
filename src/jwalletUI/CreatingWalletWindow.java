/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jwalletUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import jwalletController.Wallet;

/**
 *
 * @author fernando
 */
public class CreatingWalletWindow extends JDialog{
    
    JPanel namePanel, balancePanel,passwordPanel, buttonsPanel;
    JTextField nameInput, balanceInput;
    JPasswordField passwordInput;
    JButton create, cancel;
    
    Wallet w;
    
    CreatingWalletWindow(){
        super();
        super.setSize(250, 150);
        super.setTitle("Creating wallet");
        super.setLocationRelativeTo(null);
        super.setLayout(new GridLayout(4,1));
        super.setModal(true);
        
        super.add(createNamePanel());
        super.add(createPasswordPanel());
        super.add(createBalancePanel());
        super.add(createButtonsPanel());
 
        super.getRootPane().setDefaultButton(create);
        
        super.setVisible(true);
    }
    
    public JPanel createNamePanel(){
        namePanel = new JPanel();
        nameInput = new JTextField();
        nameInput.setText("John Doe");
        nameInput.setToolTipText("Your name !");
        nameInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if(nameInput.getText().equals("John Doe")) {
                nameInput.setText("");
                namePanel.setBorder(BorderFactory.createTitledBorder("Owner"));
                pack();
                } else {
                    nameInput.setText(nameInput.getText());
                    namePanel.setBorder(BorderFactory.createTitledBorder("Owner"));
                    
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(nameInput.getText() == null || nameInput.getText().equals("")){
                    nameInput.setText("John Doe");
                    namePanel.setBorder(null);
                    
                }
            }
        });
        nameInput.setPreferredSize(new Dimension(120,20));
        namePanel.setLayout(new FlowLayout());
        namePanel.add(nameInput);
        
        return namePanel;
    }
    
    public JPanel createBalancePanel(){
        balancePanel = new JPanel();
        balanceInput = new JTextField();
        balanceInput.setText("0.00 €");
        balanceInput.setToolTipText("The amount of money the wallet already has.");
        balanceInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if(balanceInput.getText().equals("0.00 €")){
                balanceInput.setText("");
                balancePanel.setBorder(BorderFactory.createTitledBorder("Balance"));
                pack();
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(balanceInput.getText() == null || balanceInput.getText().equals("")){
                    balanceInput.setText("0.00 €");
                    balancePanel.setBorder(null);
                    
                } else if (balanceInput.getText().contains("€")){
                    balanceInput.setText(balanceInput.getText().replaceAll("€", ""));
                }
            }
        });
        balanceInput.setPreferredSize(new Dimension(120,20));
        balancePanel.setLayout(new FlowLayout());
        balancePanel.add(balanceInput);
        
        return balancePanel;
    }
    
    public JPanel createPasswordPanel(){
        passwordPanel = new JPanel();
        passwordInput = new JPasswordField();
        passwordInput.setText("Password");
        passwordInput.setToolTipText("The password to open your wallet. Try something sneaky !");
        passwordInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if(passwordInput.getText().equals("Password")){
                passwordInput.setText("");
                passwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));
                pack();
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(passwordInput.getText() == null || passwordInput.getText().equals("")){
                    passwordInput.setText("Password");
                    passwordPanel.setBorder(null);
                    
                }
            }
        });
        passwordInput.setPreferredSize(new Dimension(120,20));
        passwordPanel.setLayout(new FlowLayout());
        passwordPanel.add(passwordInput);
        
        return passwordPanel;
    }
    
    public JPanel createButtonsPanel(){
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        
        create = new JButton("Create Wallet");
        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
                String name = nameInput.getText();
                String password = passwordInput.getText();
                float balance = Float.parseFloat(balanceInput.getText());

                w = new Wallet(name, password, balance);
                
                dispose();
                
            }
        });
        buttonsPanel.add(create);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                w = null;
            }
        });
        buttonsPanel.add(cancel);
        
        create.requestFocus();
        
        return buttonsPanel;
    }
    
   public Wallet getWallet(){
       return w;
   } 
   public void setWallet(Wallet w){
       this.w = w;
   }
}
