package org.elias;

import java.util.Random;

public class Account {
    private String stateID;
    private double checkingBalance = 0.0;
    private double savingBalance = 0.0;
    private final Long accountNumber = uniqueAccountNumber();
    private User nuser;

    public Account(User user, double initialBalance) {
        this.stateID = user.getStateID();
        this.checkingBalance = Math.max(initialBalance, 0.0);
        this.nuser = user;
    }

    protected double moveMoney(double amount) {
        if (amount <= this.checkingBalance && amount > 0 ) {
            this.checkingBalance -= amount;
            return amount;
        }
        return 0.0;
    }

    private Long uniqueAccountNumber() {
        StringBuilder number = new StringBuilder();
        for (int i=0; i<12; i++) {
            number.append(new Random().nextInt(10));
        }
        return Long.valueOf(number.toString());
    }

    public String getStateID() {
        return this.stateID;
    }

    public String getUserInfo() {
        return nuser.toString();
    }

    public String setStateID(String stateID) {
        if (stateID == null || stateID.trim().isEmpty() || stateID.equals(this.stateID)) {
            throw new IllegalArgumentException("State ID cannot be null or empty");
        } else {
            this.stateID = stateID;
            return stateID;
        }
    }

    @Override
    public String toString() {
        return "State_ID: " +this.stateID +", Checking balance: " +this.checkingBalance +", Saving balance: " +this.savingBalance;
    }

    public double getCheckingBalance() {
        return this.checkingBalance;
    }

    public double getSavingBalance() {
        return this.savingBalance;
    }

    public void depositToChecking(double amount) {
        if (amount > 0) {
            checkingBalance += amount;
            System.out.println("Deposit successful. New Checking Balance: " + checkingBalance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void depositToSaving(double amount) {
        if (amount > 0) {
            savingBalance += amount;
            System.out.println("Deposit successful. New Saving Balance: " + savingBalance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdrawFromChecking(double amount) {
        if (amount > 0 && amount <= checkingBalance) {
            checkingBalance -= amount;
            System.out.println("Withdrawal successful. New Checking Balance: " + checkingBalance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
            return false;
        }
    }

    public boolean withdrawFromSaving(double amount) {
        if (amount > 0 && amount <= savingBalance) {
            savingBalance -= amount;
            System.out.println("Withdrawal successful. New Saving Balance: " + savingBalance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
            return false;
        }
    }

    public void moveMoneyToSaving(double amount) {
        if (withdrawFromChecking(amount)) {
            savingBalance += amount;
            System.out.println("Transfer to Saving successful. New Saving Balance: " + savingBalance);
        }
    }

    public void moveMoneyToChecking(double amount) {
        if (withdrawFromSaving(amount)) {
            checkingBalance += amount;
            System.out.println("Transfer to Checking successful. New Checking Balance: " + checkingBalance);
        }
    }

    public Long getAccountNumber() {
        return this.accountNumber;
    }
}

