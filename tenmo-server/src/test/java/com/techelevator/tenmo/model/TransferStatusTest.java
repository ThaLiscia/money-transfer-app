package com.techelevator.tenmo.model;

import com.techelevator.dao.BaseDaoTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferStatusTest extends BaseDaoTests {
    TransferStatus transferStatus = new TransferStatus();

    @Test
    void transferStatusId_returns_5() {
        transferStatus.setTransferStatusId(5);
        assertEquals(transferStatus.getTransferStatusId(), 5);
    }

    @Test
    void transferStatusDesc_returns_hello() {
        transferStatus.setTransferStatusDesc("Hello");
        assertEquals(transferStatus.getTransferStatusDesc(), "Hello");
    }


}