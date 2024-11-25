package com.mycompany.UI;
import com.mycompany.movie.*;
import com.mycompany.database.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class process_functions 
{
    public static boolean check_is_valid(String s)
    {
        if(s.trim().length()<1) return false;
        return true;
    }
    public static JLabel processing_image(String link, int x, int y, int w, int h)
    {
        ImageIcon movie = new ImageIcon(link);
        Image scaledImage = movie.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(x, y, w, h);
        return imageLabel;
    }
    public static JLabel processing_label(String name, int x, int y, int w, int h)
    {
        JLabel movie_label = new JLabel(name);
        movie_label.setBounds(x,y,w,h);
        movie_label.setFont(new Font("Arial", Font.PLAIN, 14));
        movie_label.setForeground(Color.WHITE);
        return movie_label;
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
    public static JPanel setLine(int x, int y, int w, int h)
    {
        JPanel line = new JPanel();
        line.setLayout(null);
        line.setBounds(x, y, w, h);
        line.setBackground(Color.GRAY);
        return line;
    }    
    public static JButton setButtonTime(String time, int x, int y, int w, int h)
    {
        JButton time1des1 = new JButton(time);
        time1des1.setFont(new Font("Arial", Font.BOLD, 12));
        time1des1.setBackground(Color.WHITE);
        time1des1.setBounds(x, y, w, h);
        return time1des1;
    }
}
