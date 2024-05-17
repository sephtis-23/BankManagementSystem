package org.elias;

import java.util.regex.Pattern;

public class User {
    private String stateID;
    private String name = "Joe Doe";
    private String email = "joe@doe.com";
    private String password;

    public User(String stateID, String name, String email, String password) {
        this.stateID = this.setStateID(stateID);
        this.name = this.setName(name);
        this.email = this.setEmail(email);
        this.password = this.setPassword(password);
    }

    @Override
    public String toString() {
        return "Name: " + this.name +", " +
                "State_ID: "+this.stateID+
                ", Email: " + this.email +
                ", Password: " + this.password;
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        if (name == null || name.trim().isEmpty() || this.name.equals(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty or previous name");
        } else {
            this.name = name;
            return this.name;
        }
    }

    public String getPassword() {
        return this.password;
    }

    public String setPassword(String password) {
        if (password == null || password.trim().isEmpty() || password.equals(this.password)) {
            throw new IllegalArgumentException("Field password can't be null or empty or previous password.");
        } else {
            this.password = password;
            return password;
        }
    }

    public String getEmail() {
        return this.email;
    }

    public String setEmail(String email) {
        if (!isValidEmail(email) || this.email.equals(email)){
            throw new IllegalArgumentException("Email address is invalid.");
        } else {
            this.email = email;
            return email;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public String getStateID() {
        return this.stateID;
    }

    public String setStateID(String stateID) {
        if (stateID == null || stateID.trim().isEmpty() || stateID.equals(this.stateID)) {
            throw new IllegalArgumentException("State ID cannot be null or empty");
        } else {
            this.stateID = stateID;
            return stateID;
        }
    }
}

