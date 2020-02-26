package edu.northeastern.cs5200.modules;

import java.sql.Date;

public class User extends Person{

    private boolean user_agreement;

    public boolean isUser_agreement() {
        return user_agreement;
    }

    public void setUser_agreement(boolean user_agreement) {
        this.user_agreement = user_agreement;
    }

    public User() {}

    public User(int person_id, String first_name, String last_name, String username, String password, String email, Date dob) {
        super.setId(person_id);
        super.setFirst_name(first_name);
        super.setLast_name(last_name);
        super.setUsername(username);
        super.setPassword(password);
        super.setEmail(email);
        super.setDob(dob);
    }

    public User(int person_id, String first_name, String last_name) {
        super.setId(person_id);
        super.setFirst_name(first_name);
        super.setLast_name(last_name);

    }
}
