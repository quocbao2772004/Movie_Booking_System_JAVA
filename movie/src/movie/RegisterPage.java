package movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static movie.Login.Login_Interface;
public class RegisterPage 
{
    public static ArrayList<String> save_username = new ArrayList<>();
    public static boolean check_is_valid(String s)
    {
        if(s.trim().length()<1) return false;
        return true;
    }
//    public static ArrayList<String> save_password = new ArrayList<>();
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
    public static boolean check_information(String username) throws FileNotFoundException, IOException 
    {
        boolean ok = true;
        username = username.trim();
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\PC\\Desktop\\B22DCVT050\\movie_ticket\\src\\movie_ticket\\information.txt"))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] sets = line.split("\\s+");
                if (sets[0].equals("Username:")) 
                {
                    count+=1;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < sets.length; i++) 
                    {
                        sb.append(sets[i]);
                        sb.append(" ");
                    }
                    String usrn2 = sb.toString().trim();
                    save_username.add(usrn2);
                }
                
            }
            for(int i = 0;i<=save_username.size()-1;i++)
            {
                if(save_username.get(i).equals(username))
                {
                    ok = false;
                    break;
                }
            }
        }   
        catch (IOException e) 
        {
            System.out.println("Error reading the file: " + e.getMessage());
        }
            return ok; 
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
            public void actionPerformed(ActionEvent e) {
                String get_username = text_username.getText();
                String get_email = text_email.getText();
                String get_Password = text_Password.getText();
                try {
                    if(check_is_valid(get_username) && check_is_valid(get_email) && check_is_valid(get_Password))
                    {
                            if(check_information(get_username))
                        {
    //                        JOptionPane.showMessageDialog(myFrame, "Sign up successfully!");
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PC\\Desktop\\B22DCVT050\\movie_ticket\\src\\movie_ticket\\information.txt", true))) {
                                writer.write("Username: " + get_username);
                                writer.newLine();
                                writer.write("Email: " + get_email);
                                writer.newLine();
                                writer.write("Password: " + get_Password);
                                writer.newLine();
                                JOptionPane.showMessageDialog(myFrame, "Registration Successful!");
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(myFrame, "Error saving information.");
                            }
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
                    
                } catch (IOException ex) {
                    Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
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
