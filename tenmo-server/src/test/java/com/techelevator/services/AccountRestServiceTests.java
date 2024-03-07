package com.techelevator.services;
import com.techelevator.tenmo.model.Account;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;

    public class AccountRestServiceTests{
        private Account account;
        @Before
        public void setUp() {
            account = new Account();

    }

    @Test
        public void getUserBalance(){
            BigDecimal expectedBalance = BigDecimal.valueOf(1000.00);
            account.setBalance(expectedBalance);

            BigDecimal actualBalance = account.getBalance();

            assertEquals(expectedBalance, actualBalance);
    }
}