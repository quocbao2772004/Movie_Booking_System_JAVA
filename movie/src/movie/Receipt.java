
package movie;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static movie.Login.Login_Interface;
import javax.swing.*;
import static movie.Menu.show_Menu;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import static movie.Init.arl_movie;
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
    public static void getReceipt(String x, String name, String usrn)
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(left_Panel(myFrame, usrn));
        myFrame.add(right_Panel(x, name, usrn));
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
                    movie.Menu.show_Menu(usrn1);
                } catch (IOException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
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
                Login_Interface();
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
    public static JLabel function_day(String day, int x, int y, int w, int h)
    {
        JLabel today = new JLabel(day);
        today.setForeground(Color.WHITE);
        today.setFont(new Font("Arial", Font.BOLD, 16));
        today.setBounds(x, y, w, h);
        return today;
    }
    public static JButton function_date(String date, int x, int y, int w, int h)
    {
        JButton day1 = new JButton(date);
        day1.setBounds(x, y, w, h);
        day1.setFont(new Font("Arial", Font.BOLD, 12));
        day1.setBackground(Color.WHITE);
        return day1;
    }
    public static JPanel right_Panel(String x, String name, String usrn)
    {
        
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
        lPanel.add(Menu.processing_image(x, 10, 20, 150, 200));
        lPanel.add(Menu.processing_name(name, 680, 190, 180, 50));
        rightPanel.add(lPanel);
        
        rPanel.setBounds(301, 0, 700, 750);
        rPanel.setBackground(Color.DARK_GRAY);
        rPanel.setLayout(null);
        // set day
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 0, 700, 150);
        sub0.setBackground(Color.BLACK);
        String[] day1 = arl_movie.get(0).getMovieDateList().get(0).split("\\s+");
        String[] day2 = arl_movie.get(0).getMovieDateList().get(1).split("\\s+");
        String[] day3 = arl_movie.get(0).getMovieDateList().get(2).split("\\s+");
        JButton date1 = function_date(day1[1], 10, 50, 50, 30);
        JButton date2 = function_date(day2[1], 120, 50, 50, 30);
        JButton date3 = function_date(day3[1], 230, 50, 50, 30);
        sub0.add(function_day(day1[0], 10, 10, 100, 20));
        sub0.add(date1);
        sub0.add(function_day(day2[0], 120, 10, 100, 20));
        sub0.add(date2);
        sub0.add(function_day(day3[0], 230, 10, 100, 20));
        sub0.add(date3);;
        save_day.add(date1);
        save_day.add(date2);
        save_day.add(date3);
        
        JPanel line = new JPanel();
        line.setLayout(null);
        line.setBounds(10, 120, 650, 1);
        line.setBackground(Color.GRAY);
        sub0.add(line);
        // set location
        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBounds(0, 150, 700, 650);
        sub1.setBackground(Color.BLACK);
        JLabel des1 = new JLabel(arl_movie.get(0).getCinemaName(0));
        JLabel des2 = new JLabel(arl_movie.get(0).getCinemaName(1));
        JLabel des3 = new JLabel(arl_movie.get(0).getCinemaName(2));
        des1.setForeground(Color.WHITE);
        des2.setForeground(Color.WHITE);
        des3.setForeground(Color.WHITE);
        des1.setFont(new Font("Arial", Font.BOLD, 16));
        des2.setFont(new Font("Arial", Font.BOLD, 16));
        des3.setFont(new Font("Arial", Font.BOLD, 16));
        JButton time1des1 = new JButton(arl_movie.get(0).getCinemaTime(0, 0));
        JButton time2des1 = new JButton(arl_movie.get(0).getCinemaTime(0, 1));
        JButton time3des1 = new JButton(arl_movie.get(0).getCinemaTime(0, 2));
        time1des1.setFont(new Font("Arial", Font.BOLD, 12));
        time2des1.setFont(new Font("Arial", Font.BOLD, 12));
        time3des1.setFont(new Font("Arial", Font.BOLD, 12));
        time1des1.setBackground(Color.WHITE);
        time2des1.setBackground(Color.WHITE);
        time3des1.setBackground(Color.WHITE);
        JButton time1des2 = new JButton(arl_movie.get(0).getCinemaTime(1, 0));
        JButton time2des2 = new JButton(arl_movie.get(0).getCinemaTime(1, 1));
        JButton time3des2 = new JButton(arl_movie.get(0).getCinemaTime(1, 2));
        time1des2.setFont(new Font("Arial", Font.BOLD, 12));
        time2des2.setFont(new Font("Arial", Font.BOLD, 12));
        time3des2.setFont(new Font("Arial", Font.BOLD, 12));
        time1des2.setBackground(Color.WHITE);
        time2des2.setBackground(Color.WHITE);
        time3des2.setBackground(Color.WHITE);
        JButton time1des3 = new JButton(arl_movie.get(0).getCinemaTime(2, 0));
        JButton time2des3 = new JButton(arl_movie.get(0).getCinemaTime(2, 1));
        time1des3.setFont(new Font("Arial", Font.BOLD, 12));
        time2des3.setFont(new Font("Arial", Font.BOLD, 12));
        time1des3.setBackground(Color.WHITE);
        time2des3.setBackground(Color.WHITE);
        buttonToCinemaMap.put(time1des1, arl_movie.get(0).getCinemaName(0));
        buttonToCinemaMap.put(time2des1, arl_movie.get(0).getCinemaName(0));
        buttonToCinemaMap.put(time3des1, arl_movie.get(0).getCinemaName(0));

        buttonToCinemaMap.put(time1des2, arl_movie.get(0).getCinemaName(1));
        buttonToCinemaMap.put(time2des2, arl_movie.get(0).getCinemaName(1));
        buttonToCinemaMap.put(time3des2, arl_movie.get(0).getCinemaName(1));

        buttonToCinemaMap.put(time1des3, arl_movie.get(0).getCinemaName(2));
        buttonToCinemaMap.put(time2des3, arl_movie.get(0).getCinemaName(2));
        save_button.add(time1des1);
        save_button.add(time2des1);
        save_button.add(time3des1);
        save_button.add(time1des2);
        save_button.add(time2des2);
        save_button.add(time3des2);
        save_button.add(time1des3);
        save_button.add(time2des3);
        //des1
        des1.setBounds(10, 10, 300, 30);
        time1des1.setBounds(10, 50, 100, 50);
        time2des1.setBounds(130, 50, 100, 50);
        time3des1.setBounds(250, 50, 100, 50);
        //des2
        des2.setBounds(10, 150, 300, 30);
        time1des2.setBounds(10, 200, 100, 50);
        time2des2.setBounds(130, 200, 100, 50);
        time3des2.setBounds(250, 200, 100, 50);
        //des3
        des3.setBounds(10, 320, 300, 30);
        time1des3.setBounds(10, 370, 100, 50);
        time2des3.setBounds(130, 370, 100, 50);
        
        sub1.add(des1);
        sub1.add(time1des1);
        sub1.add(time2des1);
        sub1.add(time3des1);
        JPanel line1 = new JPanel();
        line1.setLayout(null);
        line1.setBackground(Color.GRAY);
        line1.setBounds(10, 140, 650, 1);
        sub1.add(line1);
        sub1.add(des2);
        sub1.add(time1des2);
        sub1.add(time2des2);
        sub1.add(time3des2);
        JPanel line2 = new JPanel();
        line2.setLayout(null);
        line2.setBackground(Color.GRAY);
        line2.setBounds(10, 300, 650, 1);
        sub1.add(line2);
        sub1.add(des3);
        sub1.add(time1des3);
        sub1.add(time2des3);
        JPanel line3 = new JPanel();
        line3.setLayout(null);
        line3.setBackground(Color.GRAY);
        line3.setBounds(10, 460, 650, 1);
        sub1.add(line3);
        JButton selectButton = new JButton("Select");
        selectButton.setBounds(250, 500, 100, 30);
        selectButton.setBackground(Color.YELLOW);
        selectButton.setFont(new Font("Arial", Font.BOLD, 12));
        selectButton.setVisible(false);
        selectButton.addActionListener(new ActionListener() 
            {
                final String name1 = name;
                final String usrn1 = usrn;
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    
                    myFrame.dispose();
                    movie.Seat.chooseSeat(name1, usrn1);
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
                checkAndShowNewButton.run();
            });
        }

       
        for (JButton btn : save_day) {
            btn.addActionListener(e -> {
                for (JButton b : save_day) {
                    b.setBackground(Color.WHITE);
                    save_choosen_day.remove(b.getText());
                    selectedCinema.remove(buttonToCinemaMap.get(b));
                }
                btn.setBackground(Color.YELLOW);
                save_choosen_day.add(btn.getText());
                selectedCinema.add(buttonToCinemaMap.get(btn));
                checkAndShowNewButton.run();
            });
        }


        
        
        return rightPanel;
    }
    
}

