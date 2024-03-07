package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferTypes;
import com.techelevator.tenmo.util.BasicLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer;
        String sql =  "SELECT * from transfer " +
                "JOIN transfer_status USING(transfer_status_id) " +
                "JOIN transfer_type USING (transfer_type_id) " +
                "WHERE transfer_id = ?" ;
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if(results.next()) {
                transfer = mapRowToCard(results);
            }else{
                throw new DaoException("Transfer not found");
            }
        }catch(DataAccessException e){
            BasicLogger.log(e.getMessage());
            throw new DaoException("Could not access database");
        }
        return transfer;
    }


    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        if(userId <= 0){
            throw new IllegalArgumentException("User Id cannot be negative");
        }
        List<Transfer> transferList = new ArrayList<>();
        Transfer transfer;
        String sql = "select * from transfer " +
                "JOIN account on transfer.account_from = account.account_id " +
                "JOIN transfer_status USING (transfer_status_id) " +
                "JOIN transfer_type USING (transfer_type_id) " +
                "WHERE user_id = ?";
        String sqlAccountTo = "select * from transfer " +
                "JOIN account on transfer.account_to = account.account_id " +
                "JOIN transfer_status USING (transfer_status_id) " +
                "JOIN transfer_type USING (transfer_type_id) " +
                "WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,  userId); // edit to fit the sql logic
            SqlRowSet accountToresults = jdbcTemplate.queryForRowSet(sqlAccountTo, userId);
            while (results.next()) {
                transfer = mapRowToCard(results);
                transferList.add(transfer);
            }
            while (accountToresults.next()){
                transfer = mapRowToCard(accountToresults);
                transferList.add(transfer);
            }
        } catch (DataAccessException e) {
            BasicLogger.log("Error retrieving transfers: " + e.getRootCause());
            throw new DaoException("Could not access database");
        }

        return transferList;
    }





    @Override
    public String requestTransfer(int accountFrom, int accountTo, BigDecimal amount) {
        if(accountFrom == accountTo){
            return "You can't send money to yourself.";
        }
        if (accountFrom <= 0 || accountTo <= 0) {
            throw new IllegalArgumentException("Account id cannot be less than 0.");
        }
        BigDecimal accountFromBalance = accountDao.getBalanceByAccountId(accountFrom);
        if (accountFromBalance.compareTo(amount) >= 0) {
            String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (1, 1, ?, ?, ?)";
            jdbcTemplate.update(sql, accountFrom, accountTo, amount);
            return "Request sent";
        }else{
            return "There was a problem sending the request.";
        }
    }

    @Override
    public String updateTransferRequests(Transfer transfer, int statusId) {
        if (statusId == 1) {
            String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
            jdbcTemplate.update(sql, statusId, transfer.getTransferId());
            accountDao.addToBalanceByUserId(transfer.getAmount(), transfer.getAccountTo());
            accountDao.subtractBalanceByAccountId(transfer.getAmount(), transfer.getAccountFrom());
            return "Transfer status has been updated.";
        } else {
            return "Insufficient funds for transfer.";

        }
    }



    @Override
    public Transfer createTransfer(Transfer transferRequest) {
        Transfer transfer = null;
        if (transferRequest.getAccountFrom() == transferRequest.getAccountTo()) {
            throw new IllegalArgumentException("You can't send money to yourself");
        }

        if (transferRequest.getAccountFrom() <= 0 || transferRequest.getAccountTo() <= 0){
            throw new IllegalArgumentException("Account id cannot be less than 0.");
        }
        if (transferRequest.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        BigDecimal accountFromBalance = accountDao.getBalanceByAccountId(transferRequest.getAccountFrom());
        if(accountFromBalance.compareTo(transferRequest.getAmount()) >= 0){
            transferRequest.setTransferStatusId(TransferStatus.APPROVED_ID);
            transferRequest.setTransferTypeId(TransferTypes.SEND_ID);
            String sql = "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount)"+
            "VALUES(?, ?, ?, ?, ?) RETURNING transfer_id";
            int value = jdbcTemplate.queryForObject(sql, int.class,
                    transferRequest.getTransferTypeId(),
                    transferRequest.getTransferStatusId(),
                    transferRequest.getAccountFrom(),
                    transferRequest.getAccountTo(),
                    transferRequest.getAmount());

            if (value > 0) {
                accountDao.subtractBalanceByAccountId(transferRequest.getAmount(), transferRequest.getAccountFrom());
                accountDao.addToBalanceByUserId(transferRequest.getAmount(), transferRequest.getAccountTo());
                transfer = getTransferByTransferId(value);
                return transfer;
            } else {
                throw new IllegalArgumentException("Value can't be processed.");
            }
        }
        return transfer;
    }




    private void addAccountUsernamesToTransfer(Transfer transfer){
        String usernameSql = "SELECT username FROM tenmo_user JOIN account USING(user_id) WHERE account_id = ?";

        SqlRowSet fromUsernameResult = jdbcTemplate.queryForRowSet(usernameSql, transfer.getAccountFrom());
        SqlRowSet toUsernameResult = jdbcTemplate.queryForRowSet(usernameSql, transfer.getAccountTo());
        if (fromUsernameResult.next()){
            transfer.setUserFrom(fromUsernameResult.getString("username"));
        }
        if (toUsernameResult.next()) {
            transfer.setUserTo(toUsernameResult.getString("username"));
        }
    }
    private Transfer mapRowToCard(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(results.getInt("transfer_type_id"));
        transfer.setTransferStatusId(results.getInt("transfer_status_id"));
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setTransferType(results.getString("transfer_type_desc"));
        transfer.setTransferStatus(results.getString("transfer_status_desc"));
        addAccountUsernamesToTransfer(transfer);
        return transfer;
    }
}