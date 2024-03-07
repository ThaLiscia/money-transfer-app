package com.techelevator.tenmo.model;

import com.techelevator.dao.BaseDaoTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferTypesTest extends BaseDaoTests {

    TransferTypes transferTypes = new TransferTypes();

    @Test
    void transferTypeId_returns_5() {
        transferTypes.setTransferTypeId(5);
        assertEquals(transferTypes.getTransferTypeId(), 5);
    }

    @Test
    void getTransferTypeDesc_returns_hello() {
        transferTypes.setTransferTypeDesc("Hello");
        assertEquals(transferTypes.getTransferTypeDesc(), "Hello");
    }
}