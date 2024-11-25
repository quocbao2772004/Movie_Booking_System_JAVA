package com.mycompany.UI;
import com.mycompany.movie.*;
import com.mycompany.database.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.mycompany.UI.Login.Login_Interface;
import static com.mycompany.UI.process_functions.*;
public class RegisterPage 
{
    public static JPanel Design_leftPanel()
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        
        leftPanel.setBounds(0,0,500, 750);
        ImageIcon originalImage = new ImageIcon("C:\\Users\\PC\\Downloads\\its-movie-time-vector.jpg");
        Image scaledImage = originalImage.getImage().getScaledInstance(leftPanel.getWidth(), leftPanel.getHeight(), Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(0, 0, leftPanel.getWidth(), leftPanel.getHeight());
        
        leftPanel.add(imageLabel);
        leftPanel.setBackground(Color.decode("#CCCCCC"));
        return leftPanel;
    }
    
    public static JPanel Design_RightPanel(JFrame myFrame)
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(530,10,580, 750);

        //Sub1 
        JPanel sub1 = new JPanel();
        sub1.setLayout(new GridLayout(6, 1));
        sub1.setBounds(0, 5, 650, 350);
        JLabel username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        JTextField text_username = new JTextField();
        text_username.setFont(new Font("Arial", Font.PLAIN, 20));
        
        JLabel email = new JLabel("Email");
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        JTextField text_email = new JTextField();
        text_email.setFont(new Font("Arial", Font.PLAIN, 20));
        
        JLabel Password = new JLabel("Password");
        Password.setFont(new Font("Arial", Font.PLAIN, 20));
        JPasswordField text_Password = new JPasswordField();
        text_Password.setFont(new Font("Arial", Font.PLAIN, 20));
        sub1.add(username);
        sub1.add(text_username);
        sub1.add(email);
        sub1.add(text_email);
        sub1.add(Password);
        sub1.add(text_Password);
        
        rightPanel.add(sub1);
        // Sub2
        JPanel sub2 = new JPanel();
        sub2.setLayout(null);
        sub2.setBounds(0, 350, 650, 400);
        
        JButton SignUpButton = new JButton("Sign up");
        
        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Account new_account = new Account(text_username.getText(), text_email.getText(), text_Password.getText());
                if(check_is_valid(new_account.getUsername())
                        && check_is_valid(new_account.getEmail())
                        && check_is_valid(new_account.getPassword()))
                {
                    AccountManager am = new AccountManager();
                    if(!am.check_exit(new_account.getUsername()))
                    {
                        am.createAccount(new_account.getUsername(), new_account.getPassword(), new_account.getEmail());
                        JOptionPane.showMessageDialog(myFrame, "Registration Successful!");
                        
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(myFrame,"Username is already taken!");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(myFrame,"Your information is invalid!");
                }
                
            }
        });
        SignUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        SignUpButton.setBounds(-20, 100, 650, 50); 
        SignUpButton.setBackground(Color.decode("#BB0000"));
        sub2.add(SignUpButton);
        
        JLabel ask = new JLabel("You already have an account?");
        ask.setFont(new Font("Arial", Font.BOLD, 14));
        ask.setBounds(200, 150, 300, 50);
        JButton LoginButton = new JButton("Log in");
        LoginButton.setFont(new Font("Arial", Font.BOLD, 20));
        LoginButton.setBounds(-20, 200, 650, 50);
        LoginButton.setBackground(Color.decode("#888888"));
        LoginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
                Login_Interface();
            }
            
        });
        sub2.add(ask);
        sub2.add(LoginButton);
        rightPanel.add(sub2);
        
        return rightPanel;
    }
    public static void Register() 
    {
        JFrame myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.add(Design_leftPanel());
        myFrame.add(Design_RightPanel(myFrame));
        myFrame.setVisible(true);
    }
    
    
}
