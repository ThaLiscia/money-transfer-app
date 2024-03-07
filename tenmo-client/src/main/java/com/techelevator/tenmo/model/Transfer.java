package com.techelevator.tenmo.model;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;

    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;
    private String transferType;
    private String transferStatus;
    private String userFrom;
    private String userTo;

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount, String transferType, String transferStatus) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
    }

    public Transfer(int accountFrom, int accountTo, BigDecimal amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    @Override
    public String toString() {
        return "Transfer ID: " + transferId +
                "\nTransfer Type: " + transferType +
                "\nTransfer Status: " + transferStatus +
                "\nAccount From: " + userFrom +
                "\nAccount To: " + userTo +
                "\nAmount: " + amount;
    }
}


//Constructor if it is needed

//    public Transfer(int transferId, int transferType, int transferStatus, User accountTo, User accountFrom, BigDecimal amount) {
//        this.transferId = transferId;
//        this.transferType = transferType;
//        this.transferStatus = transferStatus;
//        this.accountTo = accountTo;
//        this.accountFrom = accountFrom;
//        this.amount = amount;
//    }
    //pending transfer status is optional so testing it out
//    public boolean transferIsPending(){
//        return TransferStatus.PENDING.equals(this.transferStatus);
//    }
//

