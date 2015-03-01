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

    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 500;
    public static final String WINDOW_TITLE = "Log";
    
    public Wallet w;

    JPanel moneySpentPanel, tablePanel, exit, emptyPanel;
    JLabel moneySpentLabel, emptyLabel;
    JTextField moneySpentInput;

    JTable logTable;
    String[][] transitions;
    String[] tableHeaders;

    public LogDialog(Wallet w) {
        super();
        super.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setModal(true);
        super.setLayout(new BorderLayout());
        super.setTitle(WINDOW_TITLE);

        this.w = w;

        if (w.getDeposits().isEmpty() && w.getWithdraws().isEmpty()) {
            super.add(createEmptyPanel());
        } else {
            super.add(createMoneySpentPanel(), BorderLayout.NORTH);
            super.add(createLogTablePanel(), BorderLayout.CENTER);
            super.add(createExit(), BorderLayout.SOUTH);
        }
        super.setVisible(true);
    }

    /**
     * Creates Panel with the amount of money spent
     *
     * @return
     */
    public JPanel createMoneySpentPanel() {
        moneySpentPanel = new JPanel();

        moneySpentLabel = new JLabel("Money spent :");
        moneySpentInput = new JTextField();
        moneySpentInput.setText(Float.toString(w.getSpendings()) + " €");
        moneySpentInput.setEditable(false);

        moneySpentPanel.add(moneySpentLabel);
        moneySpentPanel.add(moneySpentInput);
        return moneySpentPanel;
    }

    /**
     * Creates table with the different Deposits and Withdraws
     *
     * @return
     */
    public JPanel createLogTablePanel() {
        int size = w.getDeposits().size() + w.getWithdraws().size();
        transitions = new String[size][4];
        
        tableHeaders = new String[4];
        tableHeaders[0] = "Type";
        tableHeaders[1] = "Amount";
        tableHeaders[2] = "Reason";
        tableHeaders[3] = "Date";

        for (int i = 0; i < w.getDeposits().size(); i++) {
            transitions[i] = w.getDeposits().get(i).getDepositInformation();
        }
        for (int j = w.getDeposits().size(); j < size; j++) {
            transitions[j] = w.getWithdraws().get(j - w.getDeposits().size()).getWithdrawInformation();
        }

        logTable = new JTable(transitions, tableHeaders);
        logTable.setModel(new AbstractTableModel() {

            public String getColumnName(int column) {
                return tableHeaders[column];
            }

            @Override
            public int getRowCount() {
                return transitions.length;
            }

            @Override
            public int getColumnCount() {
                return transitions[0].length;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                return transitions[i][i1];
            }

            public Class<?> getColumnClass(int columnIndex) {
                return Integer.class;
            }

            public boolean isCellEditable(int row, int col) {
                return false;
            }
        });

        logTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        logTable.getTableHeader().setReorderingAllowed(false);

        logTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(logTable);
        logTable.setFillsViewportHeight(true);

        tablePanel = new JPanel();
        tablePanel.add(scrollPane);

        return tablePanel;
    }

    public JPanel createEmptyPanel() {
        emptyPanel = new JPanel();
        Image emptyImage = Toolkit.getDefaultToolkit().createImage("src/resources/empty.png");

        JLabel image = new JLabel(new ImageIcon(emptyImage));
        emptyPanel.setLayout(new GridLayout(3, 1));


        emptyLabel = new JLabel("<html><font size=\"3\"><center>You still haven't made "
                + "any transactions ! Go make some "
                + "right now !<br><br>");

        super.setSize(250, 330);
        super.setLocationRelativeTo(null);
        super.setUndecorated(false);

        emptyPanel.add(image);
        emptyPanel.add(emptyLabel);
        emptyPanel.add(createExit());

        return emptyPanel;
    }

    public JPanel createExit() {
        exit = new JPanel();
        JButton exitBtn = new JButton();
        exitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        exitBtn.setVisible(false);

        JLabel exitTxt = new JLabel("<html><font size=\"1\">Press enter to close</font></html>", SwingConstants.CENTER);
        
        exit.add(exitTxt);
        exit.add(exitBtn);
        
        getRootPane().setDefaultButton(exitBtn);
        return exit;
    }
}
