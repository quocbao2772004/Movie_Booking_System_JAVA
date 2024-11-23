
package movie;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Feedback 
{
    public static void SendFeedBack()
    {
        JFrame fb = new JFrame("Feedback");
        fb.setSize(600, 400);
        fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fb.setLayout(null);
        JLabel label = new JLabel("Feedback");
        label.setBounds(200, 5, 400, 50);
        label.setFont(new Font("MV Boli", Font.PLAIN, 50));
        JTextField urfb = new JTextField();
        urfb.setBounds(20, 60, 550, 150);
        urfb.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton send = new JButton("Send");
        send.setBounds(400, 230, 100, 30);
        send.setFont(new Font("Arial", Font.PLAIN, 14));
        send.setBackground(Color.decode("#FFCCFF"));
        send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PC\\Desktop\\B22DCVT050\\movie_ticket\\src\\movie_ticket\\feedback.txt", true))) 
                {
                    writer.write("Username: " + urfb.getText());
                    writer.newLine();
                    fb.dispose();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(Feedback.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        fb.add(label);
        fb.add(urfb);
        fb.add(send);
        fb.getContentPane().setBackground(Color.decode("#99CCFF"));
        fb.setVisible(true);
    }
}
