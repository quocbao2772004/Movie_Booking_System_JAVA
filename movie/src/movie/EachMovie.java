
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
public class EachMovie 
{
    public static void EachMovie(String x, String name, String usrn)
    {
        JFrame myFrame = new JFrame("Movie Ticket System");
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(Receipt.left_Panel(myFrame, usrn));
        myFrame.add(right_Panel(x, name, usrn));
        myFrame.setVisible(true);
    }
    public static JPanel right_Panel(String url, String name, String usrn)
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
        return rightPanel;
    }
}
