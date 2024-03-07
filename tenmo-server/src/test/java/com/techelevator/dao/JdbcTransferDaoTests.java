package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcTransferDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
public class JdbcTransferDaoTests extends BaseDaoTests {

    private JdbcTransferDao jdbcTransferDao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTransferDao = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getAllTransfersByUserId_WithNullUserId_ShouldThrowIllegalArgumentException() {
        // Pass null userId to the method, and it should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            jdbcTransferDao.getAllTransfersByUserId(0);
        });
    }
}