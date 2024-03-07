package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class TransferService {

    private  String urlBaseTransfer = "http://localhost:8080/transfer";
    private  RestTemplate restTemplate = new RestTemplate();

    public TransferService(String urlBaseTransfer, RestTemplate restTemplate) {
        this.urlBaseTransfer = urlBaseTransfer;
    }

    public List<Transfer> getAllTransfers(User user, String bearerToken) { //get list of all transfers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {
            ResponseEntity<List<Transfer>> response = restTemplate.exchange(
                    urlBaseTransfer + "/list", HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Transfer>>(){}
                    );
            return response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log("Error retrieving transfers: " + e.getMessage());
        } return Collections.emptyList();
    }


    public Transfer getTransferByTransferId(int transferId, String bearerToken){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        try{
            ResponseEntity<Transfer>transferResponseEntity= restTemplate.exchange(urlBaseTransfer+"/"+ transferId,
                    HttpMethod.GET, entity, Transfer.class);
            return transferResponseEntity.getBody();
        }catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log("Error retrieving transfers: " + e.getMessage());
        }return null;
    }

    public Transfer createTransfer(Transfer transferRequest, String bearerToken) { //create transfer
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(bearerToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transferRequest, httpHeaders);
        try {
            String url = urlBaseTransfer + "/send";
            return restTemplate.postForObject(url, entity, Transfer.class);
        }catch(RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log("Error sending transfer: " + e.getMessage());
        } return null;
    }
}