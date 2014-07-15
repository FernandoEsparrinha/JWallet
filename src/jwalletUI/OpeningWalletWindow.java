/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwalletUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import jwalletController.Wallet;

/**
 *
 * @author fernando
 */
public class OpeningWalletWindow extends JDialog {

    JPanel passwordPanel, namePanel, balancePanel, buttonPanel;
    JLabel passwordLabel, nameLabel, balanceLabel;
    JTextField nameInput, balanceInput;
    JPasswordField password;
    JButton openButton, cancelButton;
    
    Wallet w;
    
    public OpeningWalletWindow(Wallet w) {
        super();
        setSize(600, 400);
        setLayout(new GridLayout(4,1));
        
        this.w = w;
        
        add(createNamePanel());
        add(createBalancePanel());
        add(createPasswordPanel());
        add(createPasswordPanel());
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel createNamePanel(){
        namePanel = new JPanel();
        nameLabel = new JLabel("Owner :");
        nameInput = new JTextField(20);
        nameInput.setText(w.getName());
        nameInput.setEditable(false);
        
        namePanel.add(nameLabel);
        namePanel.add(nameInput);
        
        return namePanel;
    }
    
    public JPanel createBalancePanel(){
        balancePanel = new JPanel();
        balanceLabel = new JLabel("Balance :");
        balanceInput = new JTextField(20);
        balanceInput.setText(Float.toString(w.getBalance()));
        balanceInput.setEditable(false);
        
        balancePanel.add(balanceLabel);
        balancePanel.add(balanceInput);
        
        return balancePanel;
    }
    
    public JPanel createPasswordPanel(){
        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Insert password ");
        password = new JPasswordField(15);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);
        
        return passwordPanel;
    }
    
    public JPanel createButtonPanel(){
        buttonPanel = new JPanel();
        
        openButton = new JButton("Open");
        openButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (password.getText().equals(w.getPassword())) {
                    
//                            addWalletMenu(w);
//                            open.setEnabled(false);
//                            isOpen = true;
//                            revalidate();
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Wrong Password!");
                        }
            }
        });
        cancelButton = new JButton("Cancel");
        
        buttonPanel.add(openButton);
        buttonPanel.add(cancelButton);
        
        return buttonPanel;
    }
    
}
