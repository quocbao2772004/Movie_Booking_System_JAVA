
package com.mycompany.UI;
import com.mycompany.movie.*;
import com.mycompany.database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.*;
import static com.mycompany.UI.process_functions.*;
public class Menu 
{ 
   
    public static void show_Menu(String usrn) throws IOException
    {
        JFrame myFrame = new JFrame("Movie Ticket System");
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(left_Panel(myFrame, usrn));
        myFrame.add(right_Panel(myFrame, usrn));
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
        // FeedbackUI Button
        JButton sendFeedback = new JButton("Send Feedback");
        sendFeedback.setBounds(5, 160, 160, 30);
        sendFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        sendFeedback.setBackground(Color.decode("#333333"));
        sendFeedback.setForeground(Color.WHITE);
//        sendFeedback.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                
//               com.mycompany.database.FeedbackUI.SendFeedBack(usrn);
//            }
//            
//        });
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
                Login.Login_Interface();
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
    

    
    public static JPanel right_Panel(JFrame myFrame, String usrn) throws IOException 
    {
        // lay du lieu trong database
        MovieDatabase mvb = new MovieDatabase();
        ArrayList<Movie> arl_movie = new ArrayList<>(mvb.getAllMovies());

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(166, 0, 985, 750);
        rightPanel.setBackground(Color.decode("#000000"));

        JLabel movies = new JLabel("Movies");
        movies.setBounds(20, 10, 100, 50);
        movies.setFont(new Font("Arial", Font.PLAIN, 20));
        movies.setForeground(Color.WHITE);
        rightPanel.add(movies);

        JPanel movieListPanel = new JPanel();
        movieListPanel.setLayout(null);
        movieListPanel.setPreferredSize(new Dimension(985, ((arl_movie.size() + 2) / 3) * 300)); 
        movieListPanel.setBackground(Color.decode("#000000"));

        int movieWidth = 150; 
        int movieHeight = 250; 
        int gapX = 150; 
        int gapY = 30; 
        int xOffset = 60; 
        int yOffset = 20; 

        ArrayList<JButton> saveBuyButtons = new ArrayList<>();

        for (int i = 0; i < arl_movie.size(); i++) 
        {
            Movie movie = arl_movie.get(i);
            int col = i % 3; 
            int row = i / 3; 
            int x = xOffset + col * (movieWidth + gapX);
            int y = yOffset + row * (movieHeight + gapY);

            movieListPanel.add(processing_image(movie.getImagePath(), x, y,
                    movieWidth, movieHeight - 50));
            
            movieListPanel.add(processing_label(movie.getTitle(), x, y + movieHeight - 40,
                    movieWidth + gapX/2, 30));

            JButton buyButton = new JButton("More details");
            buyButton.setBounds(x + movieWidth / 8, y + movieHeight - 10, 120, 30);
            buyButton.setBackground(Color.decode("#FFFF00"));
            buyButton.setFont(new Font("Arial", Font.BOLD, 12));
            movieListPanel.add(buyButton);
            saveBuyButtons.add(buyButton);
        }

        for (int i = 0; i < saveBuyButtons.size(); i++) 
        {
            JButton click = saveBuyButtons.get(i);
            final int index = i;
            click.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    myFrame.dispose();
                    EachMovie.EachMovie(arl_movie.get(index), usrn);
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(movieListPanel);
        scrollPane.setBounds(0, 80, 1000, 650);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); 
        scrollPane.setBackground(Color.decode("#000000"));

        rightPanel.add(scrollPane);

        return rightPanel;
    }

}
