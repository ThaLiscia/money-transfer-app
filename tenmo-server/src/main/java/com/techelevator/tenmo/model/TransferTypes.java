package com.techelevator.tenmo.model;

public class TransferTypes {
    private int transferTypeId;
    private String transferTypeDesc;
    public static final String REQUEST = "Request";
    public static final String SEND = "Send";
    public static final int REQUEST_ID = 1;
    public static final int SEND_ID = 2;

    public int getTransferTypeId() {
        return transferTypeId;
    }
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }
    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
