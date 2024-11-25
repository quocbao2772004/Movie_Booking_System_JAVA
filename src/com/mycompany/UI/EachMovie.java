
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
import javax.swing.table.DefaultTableCellRenderer;
import static com.mycompany.UI.process_functions.*;

public class EachMovie 
{
    public static void EachMovie(Movie moviee, String usrn)
    {
        JFrame myFrame = new JFrame("Movie Ticket System");
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(Receipt.left_Panel(moviee, myFrame, usrn));
        myFrame.add(right_Panel(myFrame, moviee, usrn));
        check_size_feedback(moviee);
        myFrame.setVisible(true);
    }
    public static void check_size_feedback(Movie moviee)
    {
        FeedbackDatabase fbdtb = new FeedbackDatabase();
        ArrayList<Feedback> arl_feedback = new ArrayList<>(fbdtb.getFeedbacks(moviee.getTitle()));
        for(Feedback i: arl_feedback)
        {
            System.out.println(i.getUser());
            System.out.println(i.getFeedback());
        }
    }
    public static JPanel right_Panel(JFrame myFrame, Movie moviee, String usrn)
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        rightPanel.setBackground(Color.decode("#000000"));
        
        
        JPanel movie_infor = new JPanel();
        movie_infor.setLayout(null);
        movie_infor.setBounds(250, 20, 800, 300);
        movie_infor.setBackground(Color.black);
        JLabel movie_name = processing_label("<html><b>Movie name:</b> " + moviee.getTitle() + "</html>", 10, 0, 800, 30);
        movie_name.setFont(new Font("Arial", Font.BOLD, 16));
        movie_name.setForeground(Color.WHITE);

        JLabel genre = processing_label("<html><b>Genre:</b> " + moviee.getGenre() + "</html>", 10, 40, 800, 30);
        genre.setFont(new Font("Arial", Font.PLAIN, 14));
        genre.setForeground(Color.LIGHT_GRAY);

        JLabel director = processing_label("<html><b>Director:</b> " + moviee.getDirector() + "</html>", 10, 80, 800, 30);
        director.setFont(new Font("Arial", Font.PLAIN, 14));
        director.setForeground(Color.LIGHT_GRAY);

        JLabel description = processing_label("<html><b>Description:</b> " + moviee.getDescription() + "</html>", 10, 120, 800, 30);
        description.setFont(new Font("Arial", Font.ITALIC, 14));
        description.setForeground(Color.LIGHT_GRAY);

        JLabel duration = processing_label("<html><b>Duration:</b> " + moviee.getDuration() + " minutes</html>", 10, 160, 800, 30);
        duration.setFont(new Font("Arial", Font.PLAIN, 14));
        duration.setForeground(Color.LIGHT_GRAY);

        JLabel premiere_date = processing_label("<html><b>Release date:</b> " + moviee.getReleaseDate() + "</html>", 10, 200, 800, 30);
        premiere_date.setFont(new Font("Arial", Font.PLAIN, 14));
        premiere_date.setForeground(Color.LIGHT_GRAY);

        JLabel main_actor = processing_label("<html><b>Main actors:</b> " + moviee.getMainActors() + "</html>", 10, 240, 800, 30);
        main_actor.setFont(new Font("Arial", Font.PLAIN, 14));
        main_actor.setForeground(Color.LIGHT_GRAY);

        movie_infor.add(movie_name);
        movie_infor.add(genre);
        movie_infor.add(director);
        movie_infor.add(description);
        movie_infor.add(duration);
        movie_infor.add(premiere_date);
        movie_infor.add(main_actor);
        rightPanel.add(movie_infor);
        
        JPanel btn = new JPanel();
        btn.setLayout(null);
        btn.setBounds(780, 620, 100, 40);
        JButton buy_button  = new JButton("Buy now");
        buy_button.setLayout(null);
        buy_button.setBounds(0, 0, 100, 40);
        buy_button.setBackground(Color.yellow);
        buy_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
                com.mycompany.UI.Receipt.getReceipt(moviee, usrn);
            }
            
        });
        btn.add(buy_button);
        rightPanel.add(btn);
        
        FeedbackDatabase fbdtb = new FeedbackDatabase();
        ArrayList<Feedback> arl_feedback = new ArrayList<>(fbdtb.getFeedbacks(moviee.getTitle()));
        JPanel khung = new JPanel();
        khung.setLayout(null);
        khung.setBounds(40, 330, 600, 30);
        khung.setBackground(Color.pink);
        
        JLabel title = processing_label("Review of viewers", 220, 0, 200, 30);
        khung.add(title);

        String[] columnNames = {"User", "Review", "Status"}; 
        String[][] data = new String[arl_feedback.size()+1][3];
        int count_positive = arl_feedback.size();

        for (int i = 0; i < arl_feedback.size(); i++) 
        {
            data[i][0] = arl_feedback.get(i).getUser();
            data[i][1] = arl_feedback.get(i).getFeedback();
            if(arl_feedback.get(i).getStatus().equals("negative"))
            {
                data[i][2] = "0";
                count_positive --;
            }
            else data[i][2] = "1";
        }

        
        JTable reviewTable = new JTable(data, columnNames);
        reviewTable.setRowHeight(30);
        reviewTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        reviewTable.getColumnModel().getColumn(2).setMinWidth(0);
        reviewTable.getColumnModel().getColumn(2).setMaxWidth(0);
        reviewTable.getColumnModel().getColumn(2).setWidth(0);
        reviewTable.getColumnModel().getColumn(0).setPreferredWidth(100); 
        reviewTable.getColumnModel().getColumn(1).setPreferredWidth(500);
        DefaultTableCellRenderer reviewRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 1) {
                    String status = (String) table.getValueAt(row, 2); 

                    if ("1".equals(status)) {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLUE);
                    } else if ("0".equals(status)) {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLACK);
                    } else {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLACK);
                    }
                } else {
                    cell.setBackground(Color.WHITE); 
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        };
        
        JPanel avt = new JPanel();
        avt.setLayout(null);
        avt.setBounds(40, 20, 200, 300);
        avt.setBackground(Color.black);
        avt.add(processing_image(moviee.getImagePath(), 00, 0, 200, 250));
        JLabel reviews = processing_label("Review (" + String.valueOf(count_positive) + " positive reviews)", 0, 255, 200, 30);
        avt.add(reviews);
        rightPanel.add(avt);
        
        reviewTable.getColumnModel().getColumn(1).setCellRenderer(reviewRenderer);
        JScrollPane chat = new JScrollPane(reviewTable);
        chat.setBounds(40, 360, 600, 350);

        rightPanel.add(khung);
        rightPanel.add(chat);

        return rightPanel;
    }
}
