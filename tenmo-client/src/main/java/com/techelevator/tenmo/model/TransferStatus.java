package com.techelevator.tenmo.model;
public class TransferStatus {
    //There are three statuses of transfers:
    public static final String APPROVED = "Approved";
    public static final String REJECTED = "Rejected";
    public static final String PENDING = "Pending";
    public static final int PENDING_ID = 1;
    public static final int APPROVED_ID = 2;
    public static final int REJECTED_ID = 3;

    private TransferStatus(){} //can't be instantiated

    //if the transfer is true.
    public static boolean isTransferTrue(String transferStatus){
        return APPROVED.equals(transferStatus) ||
                REJECTED.equals(transferStatus)||
                PENDING.equals(transferStatus);

    }
}
