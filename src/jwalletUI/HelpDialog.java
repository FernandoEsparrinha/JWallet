/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jwalletUI;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author fernando
 */
public class HelpDialog extends JDialog{
    
    JLabel info, image;
    
    public HelpDialog() {
        super();
        setSize(250,330);
        setLocationRelativeTo(null);
        setTitle("Help");
        setLayout(new BorderLayout());
        
        Image img = Toolkit.getDefaultToolkit().createImage("src/resources/help.png");
        image = new JLabel(new ImageIcon(img));
        image.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        info = new JLabel("<html><center>JWallet is an application which helps you manage your money."
                + "First, you have to create the wallet, with your name, "
                + "password and balance. After that you can manage the wallet,"
                + "making deposits, withdraws and checking your logs.</center></html>");
        info.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(image, BorderLayout.NORTH);
        add(info, BorderLayout.CENTER);
        
        setModal(true);
        
        
        setVisible(true);
    }
}
