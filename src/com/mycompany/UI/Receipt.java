package com.mycompany.UI;
import com.mycompany.movie.*;
import com.mycompany.database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import static com.mycompany.UI.process_functions.*;
public class Receipt 
{
    public static JFrame myFrame = new JFrame("Movie Ticket System");
    public static ArrayList<JButton> save_button = new ArrayList<>();
    public static ArrayList<JButton> save_day = new ArrayList<>();
    public static ArrayList<String> save_choosen_day = new ArrayList<>();
    public static ArrayList<String> save_choosen_time = new ArrayList<>();
    public static ArrayList <JButton> save_buyButton = new ArrayList<>();
    public static Map<JButton, String> buttonToCinemaMap = new LinkedHashMap<>();
    public static ArrayList<String> selectedCinema = new ArrayList<>();
    public static void getReceipt(Movie moviee, String usrn)
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(left_Panel(moviee, myFrame, usrn));
        right_Panel(moviee, usrn);
//        myFrame.add(right_Panel(moviee, usrn));
        myFrame.setVisible(true);
    }
    public static JPanel left_Panel(Movie moviee, JFrame myFrame, String usrn)
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0,0,165, 750);
        // anh user
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 30, 150, 200);
        ImageIcon originalImage = new ImageIcon("C:\\Users\\PC\\Downloads\\user.png");
        Image scaledImage = originalImage.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(10, 0, 120, 120);
        sub0.add(imageLabel);
        JLabel username = new JLabel(usrn);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setBounds(10, 130, 100, 50);
        sub0.add(username);
        sub0.setBackground(Color.decode("#CCCCCC"));
        leftPanel.add(sub0);
        
        // Bound Menu
        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBounds(0, 150, 150, 500);
        // Menu Button
        JButton menu = new JButton("Menu");
        menu.setBounds(5, 100, 160, 30);
        menu.setFont(new Font("Arial", Font.BOLD, 14));
        menu.setBackground(Color.decode("#333333"));
        menu.setForeground(Color.WHITE);
        menu.addActionListener(new ActionListener(){
            final String usrn1 = usrn;
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
                try {
                    Menu.show_Menu(usrn1);
                } catch (IOException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        // FeedbackUI Button
        JButton sendFeedback = new JButton("Send Feedback");
        sendFeedback.setBounds(5, 160, 160, 30);
        sendFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        sendFeedback.setBackground(Color.decode("#333333"));
        sendFeedback.setForeground(Color.WHITE);
        sendFeedback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                FeedbackUI.SendFeedBack(myFrame, moviee, usrn);
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
    
    
    public static void right_Panel(Movie moviee, String usrn)
    {
        CinemaManager cm = new CinemaManager();
        ArrayList<Cinema> arl_cinema = new ArrayList<>(cm.getAllCinemas());
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        rightPanel.setBackground(Color.decode("#000000"));
        // left of right_Panel
        JPanel lPanel = new JPanel();
        JPanel rPanel = new JPanel();
        lPanel.setBounds(0, 0, 300, 750);
        lPanel.setBackground(Color.BLACK);
        // Lấy ảnh và tên của phim đã chọn
        lPanel.add(processing_image(moviee.getImagePath(), 10, 20, 150, 200));
        lPanel.add(processing_label(moviee.getTitle(), 680, 190, 180, 50));
        rightPanel.add(lPanel);
        
        rPanel.setBounds(301, 0, 700, 750);
        rPanel.setBackground(Color.DARK_GRAY);
        rPanel.setLayout(null);
        // set day
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 0, 700, 150);
        sub0.setBackground(Color.BLACK);
        ArrayList<String> dateList = new ArrayList<>(moviee.getShowDates());
        int x_day = 10 , y_day = 10, w_day = 100 , h_day = 20;
        int x_date = 10,y_date = 50, w_date = 50, h_date = 30;
        for(String i: dateList)
        {
            String[] day = i.split("\\s+");
            sub0.add(function_day(day[0], x_day, y_day, w_day, h_day));
            JButton date = function_date(day[1], x_date, y_date, w_date, h_date);
            sub0.add(date);
            save_day.add(date);
            x_day += 110;
            x_date += 110;
        }
        sub0.add(setLine(10, 120, 650, 1));
        // set location
        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBounds(0, 130, 700, 650);
        sub1.setBackground(Color.BLACK);
        ArrayList<String> cname = new ArrayList<>(moviee.getCinemas());
        
        int x_des = 10, y_des = 10, w_des = 300, h_des = 30;
        for(String i: cname)
        {
            sub1.add(function_day(i, x_des, y_des, w_des, h_des));
            y_des += 140;
        }
        
        int x_time = 10, y_time = 50, w_time = 100, h_time = 50;
        int y_line = 140;
        for(Cinema i: arl_cinema)
        {
            x_time = 10;
            for(String j: i.getShowHours())
            {
                JButton time = setButtonTime(j, x_time, y_time, w_time, h_time);
                sub1.add(time);
                save_button.add(time);
                buttonToCinemaMap.put(time, i.getName());
                x_time += 120;
            }
            sub1.add(setLine(10, y_line, 650, 1));
            y_line += 140;
            y_time += 150;
        }
        
        JButton selectButton = new JButton("Select");
        selectButton.setBounds(250, 500, 100, 30);
        selectButton.setBackground(Color.YELLOW);
        selectButton.setFont(new Font("Arial", Font.BOLD, 12));
        selectButton.setVisible(false);
        selectButton.addActionListener(new ActionListener() 
            {
                final String name1 = moviee.getTitle();
                final String usrn1 = usrn;
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    
                    myFrame.dispose();
                    com.mycompany.UI.SeatUI.chooseSeat(moviee, name1, usrn1);
                }
            });
        sub1.add(selectButton);
        rPanel.add(sub0);
        rPanel.add(sub1);
        
        rightPanel.add(rPanel);
        Runnable checkAndShowNewButton = () -> {
            boolean buttonSelected = save_button.stream()
                    .anyMatch(btn -> btn.getBackground() == Color.YELLOW);
            boolean daySelected = save_day.stream()
                    .anyMatch(btn -> btn.getBackground() == Color.YELLOW);

            if (buttonSelected && daySelected) {
                selectButton.setVisible(true); 
            } else {
                selectButton.setVisible(false); 
            }
        };

        
        for (JButton btn : save_button) {
            btn.addActionListener(e -> {
                for (JButton b : save_button) {
                    b.setBackground(Color.WHITE);
                    save_choosen_time.remove(b.getText());
                    selectedCinema.remove(buttonToCinemaMap.get(b));
                }
                btn.setBackground(Color.YELLOW);
                save_choosen_time.add(btn.getText());
                selectedCinema.add(buttonToCinemaMap.get(btn));
                System.out.println(buttonToCinemaMap.get(btn));
                checkAndShowNewButton.run();
            });
        }

       
        for (JButton btn : save_day) {
            btn.addActionListener(e -> {
                for (JButton b : save_day) {
                    b.setBackground(Color.WHITE);
                    save_choosen_day.remove(b.getText());
                    
                }
                btn.setBackground(Color.YELLOW);
                save_choosen_day.add(btn.getText());
                
                checkAndShowNewButton.run();
            });
        }

        myFrame.add(rightPanel);
//        return rightPanel;
    }
    
}

