/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwalletUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jwalletController.*;

/**
 *
 * @author fernando
 */
public class LogDialog extends JDialog {

    public Wallet w;

    JPanel top, bottom, emptyPanel;
    JLabel spentLabel, emptyLabel;
    JTextField spentInput;

    JTable table;
    String data[][];
    String names[];

    public LogDialog(Wallet w) {
        super();
        super.setSize(700, 500);
        super.setLocationRelativeTo(null);
        super.setModal(true);
        super.setLayout(new BorderLayout());
        super.setTitle("Log");

        this.w = w;

        if (w.getDeposits().isEmpty() && w.getWithdraws().isEmpty()) {
            super.add(createEmptyPanel());
        } else {
            super.add(createTopPanel(), BorderLayout.NORTH);
            super.add(createBottomPanel(), BorderLayout.CENTER);
        }

        super.setVisible(true);
    }

    public JPanel createTopPanel() {
        top = new JPanel();

        spentLabel = new JLabel("Money spent :");
        spentInput = new JTextField();
        spentInput.setText(Float.toString(w.getSpendings()) + " €");
        spentInput.setEditable(false);

        top.add(spentLabel);
        top.add(spentInput);
        return top;
    }

    public JPanel createBottomPanel() {
        int size = w.getDeposits().size() + w.getWithdraws().size();
        data = new String[size][4];
        names = new String[4];
        names[0] = "Type";
        names[1] = "Amount";
        names[2] = "Reason";
        names[3] = "Date";

        for (int i = 0; i < w.getDeposits().size(); i++) {
            data[i] = w.getDeposits().get(i).getArrayDeposit();
        }
        for (int j = w.getDeposits().size(); j < size; j++) {
            data[j] = w.getWithdraws().get(j - w.getDeposits().size()).getArrayDeposit();
        }

        table = new JTable(data, names);
        table.setModel(new AbstractTableModel() {

            public String getColumnName(int column) {
                return names[column];
            }

            @Override
            public int getRowCount() {
                return data.length;
            }

            @Override
            public int getColumnCount() {
                return data[0].length;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                return data[i][i1];
            }

            public Class<?> getColumnClass(int columnIndex) {
                return Integer.class;
            }

            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });

        table.getColumnModel().getColumn(3).setPreferredWidth(90);
        table.getTableHeader().setReorderingAllowed(false);
        
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        bottom = new JPanel();
        bottom.add(scrollPane);

        return bottom;
    }

    public JPanel createEmptyPanel() {
        emptyPanel = new JPanel();
        
        Image img = Toolkit.getDefaultToolkit().createImage("src/resources/empty.png");
        JLabel image = new JLabel(new ImageIcon(img));
        emptyPanel.setLayout(new GridLayout(3,1));
        
        JButton exit = new JButton();
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        exit.setVisible(false);
        
        
        emptyLabel = new JLabel("<html><font size=\"3\"><center>You still haven't made "
                + "any transactions ! Go make some "
                + "right now !<br><br>"
                + "<br><font size=\"1\">press enter to close</font></center></font></html>");
        
        super.setSize(250, 330);
        super.setLocationRelativeTo(null);
        super.setUndecorated(true);
        
        emptyPanel.add(image);
        emptyPanel.add(emptyLabel);
        emptyPanel.add(exit);
        
        getRootPane().setDefaultButton(exit);
        
        return emptyPanel;
    }

}
