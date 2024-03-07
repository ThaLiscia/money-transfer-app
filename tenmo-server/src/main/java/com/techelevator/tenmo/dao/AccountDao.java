package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;


public interface AccountDao {

    BigDecimal getBalanceByAccountId(int userId);

    BigDecimal getBalanceByUsername(String username);

    BigDecimal addToBalanceByUserId(BigDecimal amountToAdd, int userId);

    BigDecimal subtractBalanceByAccountId(BigDecimal amountToSubtract, int userId);

    Account findAccountDetailsByAccountId(int userId);


    Account getAccountByUsername(String username);

    List<Account> getAllAccounts();
}








