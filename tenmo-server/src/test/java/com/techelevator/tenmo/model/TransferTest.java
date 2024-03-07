package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TransferTest {
    Transfer transfer = new Transfer();

    @Test
    public void transferId_returns_5() {
        transfer.setTransferId(5);
        assertEquals(transfer.getTransferId(), 5);
    }

    @Test
    public void transferStatusId_returns_5() {
        transfer.setTransferStatusId(5);
        assertEquals(transfer.getTransferStatusId(), 5);
    }
    @Test
    public void accountTo_returns_5(){
        transfer.setAccountTo(5);
        assertEquals(transfer.getAccountTo(), 5);
    }
    @Test
    public void accountFrom_returns_5(){
        transfer.setAccountFrom(5);
        assertEquals(transfer.getAccountFrom(), 5);
    }
    @Test
    public void setTransferTypeId_returns_5(){
        transfer.setTransferTypeId(5);
        assertEquals(transfer.getTransferTypeId(), 5);
    }
    @Test
    public void setTransferTypeDesc_returns_hello(){
//        transfer.setTransferTypeDesc("Hello!");
//        assertEquals(transfer.getTransferTypeDesc(), "Hello!");
            transfer.setTransferType("Hello!");
            assertEquals(transfer.getTransferType(), "Hello!");
    }
    @Test
    public void setAmount_returns_BigDecimal(){
        BigDecimal bigDecimal = BigDecimal.valueOf(123897928);
        transfer.setAmount(bigDecimal);
       assertEquals(transfer.getAmount(), BigDecimal.valueOf(123897928));
   }

}