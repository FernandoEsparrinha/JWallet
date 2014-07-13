/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jwalletUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import jwalletController.*;

/**
 *
 * @author fernando
 */
public class LogDialog extends JDialog {
    
    public Wallet w;
    
    JPanel top,bottom;
    JLabel spentLabel;
    JTextField spentInput;
    
    String[] columnNames = {"Type","Amount","Reason","Date"}; 
    Object[][] dataDeposits;
    JTable table;
    
    
    
    public LogDialog(Wallet w){
        super();
        super.setSize(600, 400);
        super.setLocationRelativeTo(null);
        super.setModal(true);
        super.setLayout(new BorderLayout());
        super.setTitle("Log");
        
        this.w = w;
        
        super.add(createTopPanel(),BorderLayout.NORTH);
        super.add(createBottomPanel(),BorderLayout.CENTER);
        
        super.setVisible(true);
    }
    
    public JPanel createTopPanel(){
        top = new JPanel();
        
        spentLabel = new JLabel("Money spent :");
        spentInput = new JTextField();
        spentInput.setText(Float.toString(w.getSpendings())+" €");
        spentInput.setEditable(false);
        
        top.add(spentLabel);
        top.add(spentInput);
        return top;
    }
    
    public JPanel createBottomPanel(){
        dataDeposits = new Object[1][4];
//        for(int i = 0; i < w.getDeposits().size(); i++){
//            dataDeposits[i] = w.getDeposits().get(i).getArrayDeposit();
//        }
        dataDeposits[0] = w.getDeposits().get(0).getArrayDeposit();
        
        
        table = new JTable(dataDeposits, columnNames);
        
        bottom = new JPanel();
//        JList listD = new JList(w.getDeposits().toArray());
//        JList listW = new JList(w.getWithdraws().toArray());
//        bottom.setLayout(new GridLayout(2,1));
//        bottom.add(listD);
//        bottom.add(listW);
        bottom.add(table);
        
        return bottom;
    }
}
