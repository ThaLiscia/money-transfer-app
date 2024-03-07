package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {

    //instances
    private JdbcTemplate jdbcTemplate;
    //constructors
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //methods
    @Override
    public BigDecimal getBalanceByAccountId(int userId) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
            if(result.next()) {
                balance = result.getBigDecimal("balance");
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }
        return balance;

    }
    @Override
    public BigDecimal getBalanceByUsername(String username) {
        BigDecimal balance = null;
        String sql = "SELECT balance FROM account JOIN tenmo_user USING(user_id) WHERE username = ?";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if(result.next()) {
                balance = result.getBigDecimal("balance");
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to database", e);
        }
        return balance;

    }



    @Override
    public BigDecimal addToBalanceByUserId(BigDecimal amountToAdd, int accountId) {
        Account account = findAccountDetailsByAccountId(accountId);
        BigDecimal newBalance = account.getBalance().add(amountToAdd);
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        try{
            jdbcTemplate.update(sql, newBalance, accountId);
        }catch(DataAccessException dataAccessException){
            throw new DaoException("Error accessing database");
        }
        return account.getBalance();
    }

    @Override
    public BigDecimal subtractBalanceByAccountId(BigDecimal amountToSubtract, int accountId) {
        Account account = findAccountDetailsByAccountId(accountId);
        BigDecimal newBalance = account.getBalance().subtract(amountToSubtract);
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        try{
            jdbcTemplate.update(sql, newBalance, accountId);
        }catch(DataAccessException dataAccessException){
            throw new DaoException("Error accessing database");
        }
        return account.getBalance();
    }


    @Override
    public Account findAccountDetailsByAccountId(int accountId) {
        Account account = new Account();
        String sql = "SELECT * from account WHERE account_id = ?";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
            if(result.next()) {
                account = mapRowToAccount(result);
            }
        }catch(DataAccessException dataAccessException){
            throw new DaoException("Error accessing database");
        }
        return account;
    }
    @Override
    public Account getAccountByUsername(String username){
        Account account = new Account();
        String sql = "select * from account " +
                "JOIN tenmo_user USING (user_id) " +
                "WHERE username = ?";
        try{
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if (result.next()){
                account = mapRowToAccount(result);
            }
        }catch(DataAccessException dataAccessException){
            BasicLogger.log("jdbcaccountdao getAccountByUsername ERROR "+ dataAccessException.getMessage());
            throw new DaoException("Error accessing database");
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {

        List<Account> accountList = new ArrayList<>();
        try {
            String sql = "select * from account " +
                    "JOIN tenmo_user USING (user_id) ";
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
            while (result.next()) {
                Account account = mapRowToAccount(result);
                accountList.add(account);
            }
        } catch (DataAccessException e){
            BasicLogger.log("jdbcaccountdao getallaccounts error--" + e.getMessage());
            throw new DaoException("Could not access database");
        }
        return accountList;

    }


    //mapping
    private  Account mapRowToAccount(SqlRowSet rs) {
        Account acc = new Account();
        acc.setUserId(rs.getInt("user_id"));
        acc.setAccountId(rs.getInt("account_id"));
        acc.setBalance(rs.getBigDecimal("balance"));
        acc.setUsername(rs.getString("username"));
        return acc;
    }
}