package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    //variables
    private AccountDao accountDao;
    private UserDao userDao;


    //constructor
    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }


    @GetMapping()
    public ResponseEntity<BigDecimal> getBalanceByUserId(Principal principal) {
        try {

            BigDecimal balance = accountDao.getBalanceByUsername(principal.getName());

            if (balance != null) {
                return ResponseEntity.ok(balance);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DaoException e) {
            BasicLogger.log("AccountController Balance Method -- Error retrieving balance: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/users")
    public List<User> listUsers(){
        try{
            return userDao.getUsers();
        }catch(DaoException e){
            BasicLogger.log("Account Controller ListUsers -- Error retrieving transfers: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/users/{username}")
    public Account getAccountByUsername(@PathVariable String username){
        Account account = new Account();
        try{
            account = accountDao.getAccountByUsername(username);
        }catch(DaoException e){
            BasicLogger.log(("Account controller getAccountByUsername --Error: " + e.getMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return account;
    }
    @GetMapping(path = "/all")
    public List<Account> getAllAccounts(){
        try{
            return accountDao.getAllAccounts();
        }catch(DataAccessException e){
            BasicLogger.log("accountController getAllAccounts error --" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }






}
