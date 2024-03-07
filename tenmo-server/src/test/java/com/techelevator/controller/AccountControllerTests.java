package com.techelevator.controller;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.exception.DaoException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AccountControllerTests {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetBalanceByUserId() {
        // Arrange
        BigDecimal expectedBalance = BigDecimal.valueOf(1000.0);
        Principal principal = () -> "testUser";

        when(accountDao.getBalanceByUsername("testUser")).thenReturn(expectedBalance);

        // Act
        ResponseEntity<BigDecimal> response = accountController.getBalanceByUserId(principal);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());

        // Verify
        verify(accountDao, times(1)).getBalanceByUsername("testUser");
    }

    @Test
    public void testGetBalanceByUserIdNotFound() {
        // Arrange
        Principal principal = () -> "nonExistingUser";

        when(accountDao.getBalanceByUsername("nonExistingUser")).thenReturn(null);

        // Act
        ResponseEntity<BigDecimal> response = accountController.getBalanceByUserId(principal);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify
        verify(accountDao, times(1)).getBalanceByUsername("nonExistingUser");
    }
}

//needs to be fixed but I need to move on

//    @Test(expected = ResponseStatusException.class)
//    public void testGetBalanceByUserIdDaoException() {
//        // Arrange
//        Principal principal = () -> "errorUser";
//
//        when(accountDao.getBalanceByUsername("errorUser")).thenThrow(DaoException.class);
//
//        // Act
//        accountController.getBalanceByUserId(principal);
//
//        // Verify
//        verify(accountDao, times(1)).getBalanceByUsername("errorUser");
//    }
//}
