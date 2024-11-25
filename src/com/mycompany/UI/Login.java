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
import static com.mycompany.UI.RegisterPage.Register;

public class Login 
{
    public static ArrayList<String> save_username = new ArrayList<>();
    public static ArrayList<String> save_password = new ArrayList<>();
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
        rightPanel.setLayout(new GridLayout(4, 1));
        rightPanel.setBounds(530,10,580, 750);
        // Sub0
        JPanel sub0 = new JPanel();
        sub0.setLayout(new GridLayout(2,1));
        // Add Icon
        ImageIcon ticketIcon = new ImageIcon("C:\\Users\\PC\\Downloads\\heh.jpg");
        Image scaledImage = ticketIcon.getImage().getScaledInstance(306/2, 164/2, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        sub0.add(imageLabel);
        // Add Title
        JLabel Movie_Ticket_System = new JLabel("  Movie Ticket System");
        Movie_Ticket_System.setFont(new Font("MV Boli", 1, 48));
        sub0.add(Movie_Ticket_System);
        rightPanel.add(sub0);
        //Sub1 
        JPanel sub1 = new JPanel();
        sub1.setLayout(new GridLayout(4, 1));
        sub1.setBounds(530, 100, 650, 750);
        JLabel username = new JLabel("Username");
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        JTextField text_username = new JTextField();
        text_username.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel Password = new JLabel("Password");
        Password.setFont(new Font("Arial", Font.PLAIN, 20));
        JPasswordField text_Password = new JPasswordField();
        text_Password.setFont(new Font("Arial", Font.PLAIN, 20));
        sub1.add(username);
        sub1.add(text_username);
        sub1.add(Password);
        sub1.add(text_Password);
        rightPanel.add(sub1);
        // Sub2
        JPanel sub2 = new JPanel();
        sub2.setLayout(null);
//        sub2.setBounds(600, 100, 100, 100);
        JButton loginButton = new JButton("Login");
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountManager am = new AccountManager();
                String get_username = text_username.getText();
                String get_Password = text_Password.getText();
                try {
                    if(am.check_correct(get_username, get_Password))
                    {
                        JOptionPane.showMessageDialog(myFrame, "Login successfully!");
                        myFrame.dispose();
                        Menu.show_Menu(get_username);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(myFrame,"Wrong username or password!");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBounds(-20, 40, 650, 50); 
        loginButton.setBackground(Color.decode("#BB0000"));
        sub2.add(loginButton);
        rightPanel.add(sub2);
        //sub3
        JPanel sub3 = new JPanel();
        sub3.setLayout(new FlowLayout());
        JLabel ask = new JLabel("Don't have an account?               ");
        ask.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton create_new_account = new JButton("Sign up here");
        create_new_account.setFont(new Font("Arial", Font.BOLD, 14));
        create_new_account.setBorderPainted(false); 
        create_new_account.setFocusPainted(false);  
        create_new_account.setForeground(Color.decode("#BB0000")); 
        create_new_account.setBackground(Color.decode("#EEEEEE"));
        create_new_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    myFrame.dispose();
                    Register();
            }
        });
        sub3.add(ask);
        sub3.add(create_new_account);
        rightPanel.add(sub3);
        return rightPanel;
    }
    public static void Login_Interface() 
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

