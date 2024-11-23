
package movie;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.*;
import static movie.Init.arl_movie;
public class Menu 
{
    public static JFrame myFrame = new JFrame("Movie Ticket System");
    public static ArrayList <JButton> save_buyButton = new ArrayList<>();
    public static JLabel processing_image(String link, int x, int y, int w, int h)
    {
        ImageIcon movie = new ImageIcon(link);
        Image scaledImage = movie.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(x, y, w, h);
        return imageLabel;
    }
    public static JLabel processing_name(String name, int x, int y, int w, int h)
    {
        JLabel movie_label = new JLabel(name);
        movie_label.setBounds(x,y,w,h);
        movie_label.setFont(new Font("Arial", Font.PLAIN, 14));
        movie_label.setForeground(Color.WHITE);
        return movie_label;
    }
    public static void show_Menu(String usrn) throws IOException
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(left_Panel(myFrame, usrn));
        myFrame.add(right_Panel(usrn));
        myFrame.setVisible(true);
    }
    public static JPanel left_Panel(JFrame myFrame, String usrn)
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0,0,165, 750);
        
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 30, 150, 200);
        sub0.add(processing_image("C:\\Users\\PC\\Downloads\\user.png", 10, 0, 120, 120));
        JLabel username = new JLabel(usrn);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setBounds(10, 130, 100, 50);
        sub0.add(username);
        sub0.setBackground(Color.decode("#CCCCCC"));
        leftPanel.add(sub0);
        
        
        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBounds(0, 150, 150, 500);
        // Menu Button
        JButton menu = new JButton("Menu");
        menu.setBounds(5, 100, 160, 30);
        menu.setFont(new Font("Arial", Font.BOLD, 14));
        menu.setBackground(Color.decode("#333333"));
        menu.setForeground(Color.WHITE);
        // Feedback Button
        JButton sendFeedback = new JButton("Send Feedback");
        sendFeedback.setBounds(5, 160, 160, 30);
        sendFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        sendFeedback.setBackground(Color.decode("#333333"));
        sendFeedback.setForeground(Color.WHITE);
        sendFeedback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                movie.Feedback.SendFeedBack();
            }
            
        });
        // Logout Button
        JButton logout = new JButton("Logout");
        logout.setBounds(5, 220, 160, 30);
        logout.setFont(new Font("Arial", Font.BOLD, 14));
        logout.setBackground(Color.decode("#333333"));
        logout.setForeground(Color.WHITE);
        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
                movie.Login.Login_Interface();
            }
            
        });
        sub1.add(menu);
        sub1.add(sendFeedback);
        sub1.add(logout);
        sub1.setBackground(Color.decode("#CCCCCC"));
        leftPanel.add(sub1);
        leftPanel.setBackground(Color.decode("#CCCCCC"));
        return leftPanel;
    }
    public static void readMovie(String filename)
    {
        try {
            Init init = new Init();
            init.Init_information();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static JPanel right_Panel(String usrn) throws IOException
    {
        readMovie("DATA_movie.txt");
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        rightPanel.setBackground(Color.decode("#000000"));
        
        JLabel movies = new JLabel("Movies");
        movies.setBounds(20, 10, 100, 50);
        movies.setFont(new Font("Arial", Font.PLAIN, 20));
        movies.setForeground(Color.WHITE);
        rightPanel.add(movies);
        
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 80, 1000, 650);
        sub0.setBackground(Color.decode("#000000"));
        
        int movie_width = 150;
        int movie_height = 200;
        // movie 1
        sub0.add(processing_image(arl_movie.get(0).getUrl(), 60, 0, movie_width, movie_height));
        sub0.add(processing_name(arl_movie.get(0).getName(), 20, 190, 250, 50));
        // buy 1
        JButton buy1 = new JButton("Buy Now");
        buy1.setBounds(80, 250, 100, 40);
        buy1.setBackground(Color.decode("#FFFF00"));
        buy1.setFont(new Font("Arial", Font.BOLD, 12));
        sub0.add(buy1);
        // movie 2
        sub0.add(processing_image(arl_movie.get(1).getUrl(), 350, 0, movie_width, movie_height));
        sub0.add(processing_name(arl_movie.get(1).getName(), 350, 190, 180, 50));
        rightPanel.add(sub0);
        // buy 2
        JButton buy2 = new JButton("Buy Now");
        buy2.setBounds(370, 250, 100, 40);
        buy2.setBackground(Color.decode("#FFFF00"));
        buy2.setFont(new Font("Arial", Font.BOLD, 12));
        sub0.add(buy2);
        //movie3
        sub0.add(processing_image(arl_movie.get(2).getUrl(), 680, 0, movie_width, movie_height));
        sub0.add(processing_name(arl_movie.get(2).getName(), 680, 190, 180, 50));
       
//         buy 3
        JButton buy3 = new JButton("Buy Now");
        buy3.setBounds(700, 250, 100, 40);
        buy3.setBackground(Color.decode("#FFFF00"));
        buy3.setFont(new Font("Arial", Font.BOLD, 12));
        sub0.add(buy3);
        
        save_buyButton.add(buy1);
        save_buyButton.add(buy2);
        save_buyButton.add(buy3);
        for (int i = 0; i < save_buyButton.size(); i++) 
        {
            JButton click = save_buyButton.get(i);
            final int index = i; 
            click.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    String x = arl_movie.get(index).getUrl(); 
                    String name = arl_movie.get(index).getName();
                    myFrame.dispose();
                    movie.Receipt.getReceipt(x, name, usrn); 
                }
            });
        }


        return rightPanel;
    }
    
}
