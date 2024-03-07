package com.techelevator.controller;

import com.techelevator.tenmo.controller.TransferController;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferControllerTests {

    private TransferController transferController;
    private TransferDao transferDao;

    @Before
    public void setup() {
        transferDao = mock(TransferDao.class);
        transferController = new TransferController(transferDao, null);
    }

    @Test
    public void testGetTransferByTransferId() {
        // Arrange
        int transferId = 123;
        Transfer expectedTransfer = new Transfer();
        expectedTransfer.setTransferId(transferId);

        when(transferDao.getTransferByTransferId(transferId)).thenReturn(expectedTransfer);

        // Act
        ResponseEntity<Transfer> response = transferController.getTransferByTransferId(transferId);

        // Assert
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(expectedTransfer, response.getBody());
        verify(transferDao, times(1)).getTransferByTransferId(transferId);
    }

    //come back to fix this one

//    @Test(expected = ResponseStatusException.class)
//    public void testGetTransferByTransferIdDaoException() {
//        // Arrange
//        int transferId = 456;
//
//        when(transferDao.getTransferByTransferId(transferId)).thenThrow(DaoException.class);
//
//        // Act
//        transferController.getTransferByTransferId(transferId);
//    }
}
