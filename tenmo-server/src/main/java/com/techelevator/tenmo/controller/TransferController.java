package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao) { //constructor
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<Transfer>> getAllTransfersByUserId(Principal principal) {
        //retrieve a list of transfers for specific user
        try {
            List<Transfer> transfers = transferDao.getAllTransfersByUserId(userDao.getUserByUsername(principal.getName()).getId());
            if (transfers == null) {
                return ResponseEntity.ok().body(Collections.emptyList()); // Returns an empty list
            } else {
                return ResponseEntity.ok(transfers);
            }
        } catch (DaoException e) {
            BasicLogger.log("Transfer Controller List -- Error retrieving transfers: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/send")
    public Transfer createTransfer(@RequestBody Transfer transferRequest) {
        //creates new transfer
        return transferDao.createTransfer(transferRequest);
    }


    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{transferId}")
    public ResponseEntity<Transfer>getTransferByTransferId(@PathVariable int transferId) {
        Transfer result = transferDao.getTransferByTransferId(transferId);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }
}
