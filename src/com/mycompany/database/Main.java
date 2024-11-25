/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.database;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Main {
    public static void showImage(BufferedImage image) {
        if (image != null) {
            int newWidth = 300;
            int newHeight = 300;
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(image, 0, 0, newWidth, newHeight, null);
            g.dispose();

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(newWidth, newHeight);
            JLabel label = new JLabel(new ImageIcon(resizedImage));
            frame.add(label);
            frame.pack(); // Adjusts frame size to fit the image
            frame.setVisible(true);
        } else {
            System.err.println("No image to display");
        }
    }

    public static void main(String[] args) {


        
        // MovieDatabase movieDb = new MovieDatabase();
        // movieDb.addMovie("1", "Inception", Arrays.asList("Cinema 1", "Cinema 2"), Arrays.asList("2023-10-01", "2023-10-02"), "Sci-Fi", "path/to/image", "Christopher Nolan", "A mind-bending thriller", 148, "2010-07-16", "Leonardo DiCaprio, Joseph Gordon-Levitt");
        // movieDb.updateMovie("1", "Inception", Arrays.asList("Cinema 1", "Cinema 2", "Cinema 3"), Arrays.asList("2023-10-01", "2023-10-02", "2023-10-03"), "Sci-Fi", "path/to/image", "Christopher Nolan", "A mind-bending thriller", 148, "2010-07-16", "Leonardo DiCaprio, Joseph Gordon-Levitt");
        // movieDb.deleteMovie("1");
        // List<Movie> movies = movieDb.getAllMovies();
        // for (Movie movie : movies) {
        //     System.out.println(movie.getTitle());
        // }


        // AccountManager accountManager = new AccountManager();
        // accountManager.createAccount("dmhung1508a", "hung1234", "admin@hacker2k4.com");
        // System.out.println(accountManager.check_correct("dmhung1508", "hung12334"));
        // System.out.println(accountManager.getEmail("dmhung1508"));
        // accountManager.deleteAccount("user1");

        // TransactionHistory history = new TransactionHistory();
        // history.generateFakeTransactions(10);
        // history.updatePaymentStatus("user0", false);
        // history.getAllTransactions();

        // show ảnh QR
        // BufferedImage image = Utils.getQR(10000, "dmhung1508");
        // showImage(image);

        // // check thanh toán
        // System.out.println(Utils.checkTicketExists("DINH MANH HUNG"));

        // //send email
        // System.out.println(Utils.sendEmail("DINH MANH HUNG", "dmhung1508", "hung1234", "15:30", "Rạp 22, ghế G10, G11", "admin@hacker2k4.com"));

        // SeatsDatabase seatsDb = new SeatsDatabase();
        // seatsDb.addSeats("Cinema 1");
        // seatsDb.updateSeatStatus("Cinema 1", "G01");
        // seatsDb.updateSeatStatus("Cinema 1", "G02");
        // seatsDb.updateSeatStatus("Cinema 1", "G03");
        // seatsDb.updateSeatStatus("Cinema 1", "G04");

        // for (Seats seat : seatsDb.getSeats("Cinema 1")) {
        //     System.out.println(seat.getSeat() + " - " + seat.getStatus());
        // }

        FeedbackDatabase feedbackDb = new FeedbackDatabase();
        // feedbackDb.addFeedback("End game", "user1", "phim như con c", "negative");
        // feedbackDb.addFeedback("End game", "user2", "phim hay", "positive");
        // feedbackDb.addFeedback("End game", "user3", "phim hay", "positive");
        // feedbackDb.addFeedback("End game", "user4", "phim hay", "positive");
        // feedbackDb.addFeedback("End game", "user5", "phim hay", "positive");

        // feedbackDb.getFeedbacks("End game");
        for (Feedback feedback : feedbackDb.getFeedbacks("End game")) {
            System.out.println(feedback.getUser() + " - " + feedback.getFeedback() + " - " + feedback.getStatus() + " - " + feedback.getMovie());
        }



    }
}
