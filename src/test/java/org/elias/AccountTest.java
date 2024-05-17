package org.elias;

import org.junit.jupiter.api.*;

class AccountTest {

    private final Account account = new Account(new User("ID230", "Elias Mapendo", "elias@bank.com", "password"), 20);

    @Test
    void getStateID() {
        Assertions.assertEquals("ID230", account.getStateID());
    }

    @Test
    void getUserInfo() {
        Assertions.assertEquals( "Name: Elias Mapendo, State_ID: ID230, " +
                "Email: elias@bank.com, Password: password",account.getUserInfo());
    }

    @Test
    void setStateID() {
        account.setStateID("ID232");
    }

    @Test
    void setStateID_ExceptionNullID() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.setStateID(null);
        });
    }

    @Test
    void setStateID_emptyID() {
        // an exception occurs
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.setStateID("");
        });
    }


    @Test
    void testToString() {
        //  return "State_ID: " +this.stateID +", Checking balance: " +this.checkingBalance +", Saving balance: " +this.savingBalance;
        // new Account(new User("ID230", "Elias Mapendo", "elias@bank.com", "password"), 20);
        Assertions.assertEquals("State_ID: ID230, Checking balance: 20.0, Saving balance: 0.0", account.toString());
    }

    @Test
    void getCheckingBalance() {

        Assertions.assertEquals(20.0, account.getCheckingBalance());
    }

    @Test
    void getSavingBalance() {
        Assertions.assertEquals(0.0, account.getSavingBalance());
    }

    @Test
    void getSavingBalance_afterDepositingToSaving() {
        account.depositToSaving(20);
        Assertions.assertEquals(20.0, account.getSavingBalance());
    }

    @Test
    void depositToChecking() {
        account.depositToChecking(20);
        // 20+20
        Assertions.assertEquals(40.0, account.getCheckingBalance());
    }

    @Test
    void depositToChecking_depositNegativeAmount() {
        account.depositToChecking(-100);
        // 20+(-100)
        Assertions.assertEquals(20.0, account.getCheckingBalance());
    }

    @Test
    void depositToSaving() {
        account.depositToSaving(80);
        Assertions.assertEquals(80.0, account.getSavingBalance());
    }

    @Test
    void withdrawFromChecking() {
        account.withdrawFromChecking(20);
        Assertions.assertEquals(0.0, account.getCheckingBalance());
    }

    @Test
    void withdrawFromChecking_negativeAmount() {
        account.withdrawFromChecking(-20);
        Assertions.assertEquals(20.0, account.getCheckingBalance());
    }

    @Test
    void withdrawFromSaving() {
        // can't withdraw from 0
        Assertions.assertEquals(false, account.withdrawFromSaving(20));
    }
}