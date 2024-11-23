package movie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;

import javax.swing.*;


import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static movie.Receipt.*;
public class Seat {
    public static ArrayList <String> save_seat = new ArrayList<>();
    public static String tos = "";
    public static void chooseSeat(String mvname, String usrn)
    {
        JFrame myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.add(movie.Receipt.left_Panel(myFrame, usrn));
        myFrame.add(right_Panel(myFrame, mvname));
        myFrame.setVisible(true);
    }
    public static JPanel right_Panel(JFrame myFrame, String mvname)
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 1000, 200);
        topPanel.setBackground(Color.BLACK);
        CurveScreen screen = new CurveScreen();
        screen.setBackground(Color.BLACK);
        screen.setLayout(null);
        screen.setBounds(0,0,1000, 150);
        topPanel.add(screen);
        JLabel inf = new JLabel("Screen");
        inf.setBounds(430,150, 1000, 50);
        inf.setFont(new Font("Arial", Font.BOLD, 20));
        inf.setForeground(Color.WHITE);
        topPanel.add(inf);
        rightPanel.add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(0, 200, 1000, 550);
        bottomPanel.setBackground(Color.BLACK);
        int x = 70, y = 10, w = 60, ht = 50;
        ArrayList<JButton> h = new ArrayList<>();
        JLabel H = new JLabel("H");
        JLabel G = new JLabel("G");
        H.setForeground(Color.decode("#FFCC33"));
        H.setFont(new Font("Arial", Font.BOLD, 16));
        H.setBounds(20, 20, 30, 30);
        G.setForeground(Color.decode("#FFCC33"));
        G.setFont(new Font("Arial", Font.BOLD, 16));
        G.setBounds(20, 90, 30, 30);
        bottomPanel.add(H);
        bottomPanel.add(G);
        rightPanel.add(bottomPanel);
        for (int i = 1; i <= 12; i++) 
        {
            String name = String.format("H%02d", i);
            h.add(new JButton(name));
            h.get(i - 1).setBounds(x, y, w, ht);
            if (i == 2 || i == 10) x = x + 30;
            x = x + w + 10;
            bottomPanel.add(h.get(i - 1));

            final int index = i; 
            h.get(i - 1).addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    h.get(index - 1).setBackground(Color.YELLOW);
                    save_seat.add(String.format("H%02d", index));
                }
            });
        }

        x = 70; y = 80; w = 60; ht = 50;
        ArrayList<JButton> g = new ArrayList<>();
        for(int i=1;i<=12;i++)
        {
            String name = String.format("G%02d", i);
            g.add(new JButton(name));
            g.get(i-1).setBounds(x,y,w,ht);
            if(i==2 || i == 10) x = x + 30;
            x = x + w + 10;
            bottomPanel.add(g.get(i-1));
            final int index = i;
            g.get(i-1).addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    g.get(index-1).setBackground(Color.YELLOW);
                    save_seat.add(String.format("G%02d", index));
                }
                
            });
        }
        
        for(int i=0;i<save_seat.size();i++)
        {
            tos = tos + ", "+save_seat.get(i);
        }
        JButton confirm = new JButton("CONFIRM");
        confirm.setBounds(420, 400, 100, 30);
        confirm.setFont(new Font("Arial", Font.BOLD, 12));
        confirm.setBackground(Color.WHITE);
        
        bottomPanel.add(confirm);
        
        confirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                String content = String.valueOf(save_seat.size()) + " ticket(s)";
                JLabel total = new JLabel(content);
                
                total.setForeground(Color.WHITE);
                total.setBounds(600, 400, 100, 30);
                total.setFont(new Font("Arial", Font.BOLD, 16));
                bottomPanel.add(total);
                bottomPanel.revalidate();
                bottomPanel.setBounds(0, 200, 1000, 550);
                bottomPanel.repaint();
                myFrame.dispose();
                movie.PrintTicket.printTicket(mvname);
            }
                
        });
        return rightPanel;
    }
    public static class CurveScreen extends JPanel 
    {
        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.GRAY);

            QuadCurve2D curve = new QuadCurve2D.Float();
            curve.setCurve(20, 80, 360, 10, 975, 80); 
            g2d.draw(curve);
        }
    }
}
