package com.techelevator.tenmo.model;

public class TransferType {
    //There are two types of Transfers:
    public static final String REQUEST = "Request";
    public static final String SEND = "Send";
    public static final int REQUEST_ID = 1;
    public static final int SEND_ID = 2;

    private TransferType(){} // can not be instantiated

    //if the transfer is true
    public static boolean isTransferTrue(String transferType){
        return REQUEST.equals(transferType)||
                SEND.equals(transferType);
    }
}
