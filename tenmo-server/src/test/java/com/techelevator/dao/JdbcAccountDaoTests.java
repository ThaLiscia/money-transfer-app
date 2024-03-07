package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@RunWith(SpringRunner.class)

public class JdbcAccountDaoTests{ //extends BaseDaoTests {

    /*
    1. balance is found by user id
    2. balance updates by the amount given for the send to
    3. balance updates by the amount given for the send from


     */
    private JdbcAccountDao jdbcAccountDao;

    @Before
    public void setup() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/your_database_name");
        dataSource.setUsername("your_username");
        dataSource.setPassword("your_password");
        jdbcAccountDao = new JdbcAccountDao(new JdbcTemplate(dataSource));
    }

    @Test
    public void testGetAllAccounts() {
        // Act
        List<Account> accountList = jdbcAccountDao.getAllAccounts();
        //Assert
        assertNotNull(jdbcAccountDao);
        assertNotNull(accountList);


     //   assertEquals(2, accountList.size());

//    @Before
//    public void setup() {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcAccountDao = new JdbcAccountDao(jdbcTemplate);
//    }
//
//    @Test
//    public void testGetBalanceByUsername() {
//        // Arrange
//        String username = " ";
//        BigDecimal expectedBalance = BigDecimal.valueOf(1000);
//
//        // Act
//        BigDecimal actualBalance = jdbcAccountDao.getBalanceByUsername(username);
//
//        // Assert
//        assertEquals(expectedBalance, actualBalance);
//    }
    }
}
