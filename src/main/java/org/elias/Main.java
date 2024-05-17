package org.elias;


public class Main {
    public static void main(String[] args) {
        try {
            //SqlConnection sql = new SqlConnection();
            Bank bank = new Bank();

            bank.getAllUsers();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

