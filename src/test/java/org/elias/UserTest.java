package org.elias;

import org.junit.jupiter.api.Assertions;

class UserTest {

    private User user = new User("ID230", "Elias Mapendo", "elias@bank.com", "password");

    @org.junit.jupiter.api.Test
    public void testToString() {
        //"Name: " + this.name +", " +
        //                "State_ID: "+this.stateID+
        //                ", Email: " + this.email +
        //                ", Password: " + this.password;
        Assertions.assertEquals( "Name: Elias Mapendo, State_ID: ID230, " +
                "Email: elias@bank.com, Password: password",user.toString());
    }

    @org.junit.jupiter.api.Test
    public void testToString_WrongEmail() {
        // throw exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            User userWithInvalidEmail = new User("ID231", "John Doe", "invalidemail", "password");
        });
    }

    @org.junit.jupiter.api.Test
    void getName() {
        Assertions.assertEquals("Elias Mapendo", user.getName());
    }

    @org.junit.jupiter.api.Test
    void setName(){
        user.setName("Elias Sephtis");
    }

    @org.junit.jupiter.api.Test
    void setName_UsedName() {
        // throws an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.setName("Elias Mapendo");
        });
    }

    @org.junit.jupiter.api.Test
    void getPassword() {
        Assertions.assertEquals("password", user.getPassword());
    }

    @org.junit.jupiter.api.Test
    void setPassword() {
        user.setPassword("392rjhiefhjS");
    }

    @org.junit.jupiter.api.Test
    void setPassword_UsedPassword() {
        // previous used password, --- should throw an exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("password");
        });
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        Assertions.assertEquals("elias@bank.com", user.getEmail());
    }

    @org.junit.jupiter.api.Test
    void setEmail() {
        // can't invalid or previous
        user.setEmail("mapen@bank.com");
    }

    @org.junit.jupiter.api.Test
    void setEmail_invalidEmail() {
        // can't invalid or previous
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.setEmail("invalidemail");
        });
    }

    @org.junit.jupiter.api.Test
    void getStateID() {
        Assertions.assertEquals("ID230", user.getStateID());
    }

    @org.junit.jupiter.api.Test
    void setStateID() {
        user.setStateID("ID234");
    }

    @org.junit.jupiter.api.Test
    void setStateID_emptyID() {
        // sets of the exception handler
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.setStateID("");
        });
    }
}