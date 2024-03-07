package com.techelevator.tenmo.model;

public class TransferStatus {
    private int transferStatusId;
    private String transferStatusDesc;
    public static final String APPROVED = "Approved";
    public static final String REJECTED = "Rejected";
    public static final String PENDING = "Pending";
    public static final int PENDING_ID = 1;
    public static final int APPROVED_ID = 2;
    public static final int REJECTED_ID = 3;

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
