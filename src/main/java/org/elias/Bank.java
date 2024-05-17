package org.elias;

import java.sql.*;
import java.util.*;

public class Bank {
    private final Map<String, Account> userAccounts = new HashMap<>();
    private final Connection con;

    public Bank() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "macropenis");
    }

    public boolean sendMoney(String fromID, String toID, double amount) {
        Account fromAccount = getAccountFromDatabase(fromID);
        Account toAccount = getAccountFromDatabase(toID);

        if (fromAccount != null && toAccount != null) {
            toAccount.depositToChecking(fromAccount.moveMoney(amount));
            return true;
        } else {
            if (fromAccount == null) {
                System.out.println("Account with ID '" + fromID + "' not found.");
            }
            if (toAccount == null) {
                System.out.println("Account with ID '" + toID + "' not found.");
            }
            return false;
        }
    }

    public void createAccount(String stateID, String name, String email, String password, double initialBalance) {
        if (userAccounts.containsKey(stateID)) {
            System.out.println("An account with ID '" + stateID + "' already exists.");
            return;
        }

        User user = new User(stateID, name, email, password);
        Account account = new Account(user, initialBalance);
        userAccounts.put(stateID, account);

        try {
            String userInsertQuery = "INSERT INTO users (stateID, name, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement userStmt = con.prepareStatement(userInsertQuery)) {
                userStmt.setString(1, stateID);
                userStmt.setString(2, name);
                userStmt.setString(3, email);
                userStmt.setString(4, password);
                userStmt.executeUpdate();
            }

            String accountInsertQuery = "INSERT INTO accounts (account_number, state_id, checking_balance, saving_balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement accountStmt = con.prepareStatement(accountInsertQuery)) {
                accountStmt.setLong(1, account.getAccountNumber());
                accountStmt.setString(2, stateID);
                accountStmt.setDouble(3, initialBalance);
                accountStmt.setDouble(4, 0.0);
                accountStmt.executeUpdate();
            }

            System.out.println("Account created successfully for " + name);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void depositToChecking(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            account.depositToChecking(amount);
            System.out.println("Deposit to checking successful for account ID: " + stateID);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void depositToSaving(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            account.depositToSaving(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public boolean withdrawFromChecking(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            boolean success = account.withdrawFromChecking(amount);
            if (success) {
                updateAccountInDatabase(account);
            }
            return success;
        } else {
            System.out.println("Account not found.");
            return false;
        }
    }

    public boolean withdrawFromSaving(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            boolean success = account.withdrawFromSaving(amount);
            if (success) {
                updateAccountInDatabase(account);
            }
            return success;
        } else {
            System.out.println("Account not found.");
            return false;
        }
    }

    public void moveMoneyToSaving(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            account.moveMoneyToSaving(amount);
            updateAccountInDatabase(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void moveMoneyToChecking(String stateID, double amount) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            account.moveMoneyToChecking(amount);
            updateAccountInDatabase(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    public Account getAccount(String stateID) {
        return getAccountFromDatabase(stateID);
    }

    public String getUserInfo(String stateID) {
        Account account = getAccountFromDatabase(stateID);
        if (account != null) {
            return account.getUserInfo();
        }
        return null;
    }

    public void getAllUsers() {
        try {
            String query = "SELECT * FROM accounts";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String stateID = rs.getString("state_id");
                    Account account = getAccountFromDatabase(stateID);
                    if (account != null) {
                        System.out.println(account);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromDatabase(String stateID) {
        try {
            String userQuery = "SELECT * FROM users WHERE stateID = ?";
            String accountQuery = "SELECT * FROM accounts WHERE state_id = ?";
            User user = null;
            Account account = null;

            try (PreparedStatement userStmt = con.prepareStatement(userQuery)) {
                userStmt.setString(1, stateID);
                try (ResultSet rs = userStmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                                rs.getString("stateID"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                        );
                    }
                }
            }

            if (user != null) {
                try (PreparedStatement accountStmt = con.prepareStatement(accountQuery)) {
                    accountStmt.setString(1, stateID);
                    try (ResultSet rs = accountStmt.executeQuery()) {
                        if (rs.next()) {
                            account = new Account(
                                    user,
                                    rs.getDouble("checking_balance")
                            );
                            account.depositToSaving(rs.getDouble("saving_balance"));
                        }
                    }
                }
            }

            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateAccountInDatabase(Account account) {
        try {
            String query = "UPDATE accounts SET checking_balance = ?, saving_balance = ? WHERE account_number = ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setDouble(1, account.getCheckingBalance());
                stmt.setDouble(2, account.getSavingBalance());
                stmt.setLong(3, account.getAccountNumber());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


