
package com.mycompany.movie;

public class Feedback {
    private String user;
    private String movie;
    private String feedback;
    private String status;

    public Feedback(String user, String movie, String feedback)
    {
        this.user = user;
        this.movie = movie;
        this.feedback = feedback;
    }
    public Feedback(String user, String movie, String feedback, String status) {
        this.user = user;
        this.movie = movie;
        this.feedback = feedback;
        this.status = status;
    }

    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }
    
}