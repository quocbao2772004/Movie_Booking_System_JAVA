package com.mycompany.movie;

public class Account {
    private String username;
    private String password;
    private String email;
    private String role;
<<<<<<< HEAD:src/com/mycompany/movie/Account.java
    public Account(String username, String password, String email)
    {
=======

    public Account(String username, String password, String email, String role) {
>>>>>>> eccd809701237b0b3c1ccddde85eb34f8ec4cc5a:src/com/mycompany/database/Account.java
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public Account(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
<<<<<<< HEAD:src/com/mycompany/movie/Account.java
    
    public String getRole()
    {
        return role;
    }
=======

    public String getRole() {
        return role;
    }

    
>>>>>>> eccd809701237b0b3c1ccddde85eb34f8ec4cc5a:src/com/mycompany/database/Account.java
}
