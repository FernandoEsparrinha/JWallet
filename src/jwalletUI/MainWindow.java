/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jwalletUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import jwalletController.*;

/**
 *
 * @author fernando
 */
public class MainWindow extends JFrame {

    JMenuBar mb;
    JMenu wallet, info, date;
    JMenuItem open, create, save, load, exit, help;
    Boolean isOpen = false;
    Boolean close = false;

    CreatingWalletWindow cw;
    Wallet w;
    Image img = Toolkit.getDefaultToolkit().createImage("src/resources/wallet.png");

    public MainWindow() {
        //basic window configuration
        super();
        super.setSize(600, 400);
        super.setLocationRelativeTo(null);
        super.setTitle("JWallet");
        super.setResizable(false);
        super.setUndecorated(true);
        
        //changing L&F
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Programming the closing of the app
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        super.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                sair();
            }

        });

        //setting the icon
        setIconImage(img);

        add(new JLabel(new ImageIcon(img)));

        //setting the menu
        super.setJMenuBar(createMenuBar());

        super.setVisible(true);
    }

    
    // Menu bar
    public JMenuBar createMenuBar() {
        mb = new JMenuBar();

        mb.add(createWalletMenu());
        mb.add(createInfoMenu());
        mb.add(Box.createHorizontalGlue());
        mb.add(createDateMenu());

        return mb;
    }

    // Menu that has all the wallet operations
    public JMenu createWalletMenu() {
        wallet = new JMenu("Wallet");

        wallet.add(createOpenItem());
        wallet.add(createCreateItem());
        wallet.add(createSaveItem());
        wallet.add(createLoadItem());
        wallet.add(createExitItem());

        return wallet;
    }

    // Button that opens a created wallet
    public JMenuItem createOpenItem() {
        open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!(w == null)) {
                    JPanel panel = new JPanel();
                    JLabel label = new JLabel("Insert password ");
                    JPasswordField pass = new JPasswordField(15);

                    panel.add(label);
                    panel.add(pass);

                    int op = JOptionPane.showConfirmDialog(null, panel, "Password", JOptionPane.OK_CANCEL_OPTION);
                    if (op == JOptionPane.YES_OPTION) {
                        if (pass.getText().equals(w.getPassword())) {
                            addWalletMenu(w);
                            open.setEnabled(false);
                            isOpen = true;
                            revalidate();
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong Password!");
                        }
                }

                } else {
                    JOptionPane.showMessageDialog(null, "Wallet not created yet !", "Error", JOptionPane.OK_OPTION);
                }

            }
        });
        open.setEnabled(false);
        return open;
    }

    // Button that creates a Wallet
    public JMenuItem createCreateItem() {
        create = new JMenuItem("Create");
        create.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                cw = new CreatingWalletWindow();
                if (cw.getWallet() == null) {
                    create.setEnabled(true);
                    save.setEnabled(false);
                } else {
                    w = cw.getWallet();
                    create.setEnabled(false);
                    open.setEnabled(true);
                    save.setEnabled(true);
                    fillingWalletDetails();
                    revalidate();
                }
            }
        });

        return create;
    }

    public void fillingWalletDetails(){
        JPanel wI = new JPanel();
        wI.setLayout(new GridLayout(5,1));
        
        JLabel title = new JLabel("Wallet Created !", SwingConstants.CENTER);
        JLabel name = new JLabel("Name: "+w.getName(), SwingConstants.CENTER);
        JLabel balance = new JLabel("Balance: "+w.getBalance(), SwingConstants.CENTER);
        
        wI.add(title);
        wI.add(name);
        wI.add(balance);
        
        super.add(wI);
        
    }
    
    //Button that saves the wallet in a file
    public JMenuItem createSaveItem() {
        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!(w == null)) {

                    FileOutputStream output;
                    File f;
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileFilter(new FileNameExtensionFilter("Wallet file", "wallet"));
                    chooser.setAcceptAllFileFilterUsed(false);

                    int op = chooser.showSaveDialog(null);
                    if (op == JFileChooser.APPROVE_OPTION) {
                        f = new File(chooser.getSelectedFile() + ".wallet");

                        try {
                            output = new FileOutputStream(f);
                            ObjectOutputStream out = new ObjectOutputStream(output);
                            out.writeObject(w);
                            close = true;
                            out.close();
                            output.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (op == JFileChooser.CANCEL_OPTION) {
                        close = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wallet not created !");
                }
            }
        });
        save.setEnabled(false);
        return save;
    }

    //Button that loads a wallet from a file
    public JMenuItem createLoadItem() {
        load = new JMenuItem("Load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                FileInputStream input;
                File f;
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Wallet file ", "wallet"));

                if (isOpen) {
                    JOptionPane.showMessageDialog(null, "A wallet is open, close it before loading another wallet please.");

                } else {

                    int op = chooser.showOpenDialog(null);
                    if (op == JFileChooser.APPROVE_OPTION) {
                        f = chooser.getSelectedFile();
                        try {
                            input = new FileInputStream(f);
                            ObjectInputStream in = new ObjectInputStream(input);
                            w = (Wallet) in.readObject();
                            if (w == null) {
                                open.setEnabled(false);
                                create.setEnabled(true);
                                save.setEnabled(false);
                            } else {
                                open.setEnabled(true);
                                create.setEnabled(false);
                                save.setEnabled(true);
                            }
                            in.close();
                            input.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        return load;
    }

    //Button to exit the program
    public JMenuItem createExitItem() {
        exit = new JMenuItem("Exit");

        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                sair();
            }
        });

        return exit;
    }

    //Menu that has info about the program
    public JMenu createInfoMenu() {
        info = new JMenu("Info");
        info.add(createHelpItem());
        return info;
    }

    //Button that shows how to use the program
    public JMenuItem createHelpItem() {
        help = new JMenuItem("Help");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        help.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                HelpDialog hd = new HelpDialog();
            }
        });
        return help;
    }

    //class that asks if you want to save you wallet when you exit
    public void sair() {

        if (w == null) {
            int op = JOptionPane.showOptionDialog(null, "You haven't even created a wallet yet ! Do you really wish to leave ?", "Exit", JOptionPane.YES_NO_OPTION, 3, null, null, null);
            if (op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else {
            int op = JOptionPane.showOptionDialog(null, "Do you wish to save before quiting ?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION, 3, null, null, null);
            if (op == JOptionPane.YES_OPTION) {
                save.getActionListeners()[0].actionPerformed(null);
                if (close) {
                    System.exit(0);
                }
            } else if (op == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    //Puts the date in the right size of the menu bar
    public JMenu createDateMenu() {
        date = new JMenu();
        date.setEnabled(false);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateText = new Date();

        date.setText(" " + dateFormat.format(dateText));

        return date;
    }

    JMenuItem balanceItem, addMoneyItem, takeMoneyItem, setSalaryItem, logItem, deleteItem, logOutItem;
    JMenu jm;

    public void addWalletMenu(final Wallet w) {

        jm = new JMenu(w.getName());

        balanceItem = new JMenuItem("Balance : " + w.getBalance() + "€");
        balanceItem.setEnabled(false);
        jm.add(balanceItem);

        addMoneyItem = new JMenuItem("Add money");
        addMoneyItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    JPanel panel = new JPanel();
                    JPanel ap = new JPanel();
                    JLabel amountLabel = new JLabel("Amount :");
                    JTextField amountInput = new JTextField(15);
                    ap.add(amountLabel);
                    ap.add(amountInput);
                    JPanel rp = new JPanel();
                    JLabel reasonLabel = new JLabel("Reason :");
                    JTextField reasonInput = new JTextField(15);
                    rp.add(reasonLabel);
                    rp.add(reasonInput);
                    panel.setLayout(new GridLayout(2, 1));
                    panel.add(ap);
                    panel.add(rp);

                    int op = JOptionPane.showConfirmDialog(null, panel, "Deposit", JOptionPane.OK_CANCEL_OPTION);

                    if (op == JOptionPane.OK_OPTION) {
                        float amount = Float.parseFloat(amountInput.getText());
                        String reason = reasonInput.getText();
                        w.addDeposit(amount, reason);
                    }

                } catch (NullPointerException e) {

                }
                balanceItem.setText("Balance : " + w.getBalance() + "€");
                revalidate();
            }
        });
        jm.add(addMoneyItem);

        takeMoneyItem = new JMenuItem("Take money");
        takeMoneyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    JPanel panel = new JPanel();
                    JPanel ap = new JPanel();
                    JLabel amountLabel = new JLabel("Amount :");
                    JTextField amountInput = new JTextField(15);
                    ap.add(amountLabel);
                    ap.add(amountInput);
                    JPanel rp = new JPanel();
                    JLabel reasonLabel = new JLabel("Reason :");
                    JTextField reasonInput = new JTextField(15);
                    rp.add(reasonLabel);
                    rp.add(reasonInput);
                    panel.setLayout(new GridLayout(2, 1));
                    panel.add(ap);
                    panel.add(rp);

                    if (w.getBalance() == 0) {
                        JOptionPane.showMessageDialog(null, "You don't have money to take away !! ");

                    } else {

                        int op = JOptionPane.showConfirmDialog(null, panel, "Withdraw", JOptionPane.OK_CANCEL_OPTION);
                        if (op == JOptionPane.OK_OPTION) {
                            float amount = Float.parseFloat(amountInput.getText());
                            String reason = reasonInput.getText();
                            if (amount <= w.getBalance()) {
                                w.addWithdraw(amount, reason);
                            } else {
                                JOptionPane.showMessageDialog(null, "Insuficient funds !\nCurrent Balance :" + w.getBalance() + "€");
                            }
                        }
                    }
                } catch (NullPointerException e) {

                }
                balanceItem.setText("Balance : " + w.getBalance() + "€");
                revalidate();

            }

        });
        jm.add(takeMoneyItem);

        setSalaryItem = new JMenuItem("Set a salary");
        jm.add(setSalaryItem);

        logItem = new JMenuItem("Log");
        logItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println(w.getSpendings());
                LogDialog ld = new LogDialog(w);
            }
        });
        jm.add(logItem);

        deleteItem = new JMenuItem("Delete wallet");
        deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Insert password ");
                JPasswordField pass = new JPasswordField(15);

                panel.add(label);
                panel.add(pass);

                int op = JOptionPane.showConfirmDialog(null, panel, "Password", JOptionPane.OK_CANCEL_OPTION);
                if (op == JOptionPane.YES_OPTION) {
                    if (pass.getText().equals(w.getPassword())) {
                        removeWallet();
                        mb.remove(jm);
                        create.setEnabled(true);
                        save.setEnabled(false);
                        isOpen = false;
                        revalidate();
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Password!");
                    }
                }

            }
        });
        jm.add(deleteItem);

        logOutItem = new JMenuItem("Close wallet");
        logOutItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                mb.remove(jm);
                open.setEnabled(true);
                isOpen = false;
                revalidate();
            }
        });
        jm.add(logOutItem);
        jm.setMnemonic('w');

        mb.add(jm);
    }

    public void removeWallet() {
        this.w = null;
    }

}
