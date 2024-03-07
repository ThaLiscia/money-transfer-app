package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfersByUserId(int userId);


    Transfer getTransferByTransferId(int transferId);


    String requestTransfer(int accountFrom, int accountTo, BigDecimal amount);

    String updateTransferRequests(Transfer transfer, int statusId);

    Transfer createTransfer(Transfer transferRequest); //added

//    List<Transfer> getAllPendingTransfersByUserId(int userId);
}