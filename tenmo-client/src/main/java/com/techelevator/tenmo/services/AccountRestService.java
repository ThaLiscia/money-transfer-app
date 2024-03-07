package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service

public class AccountRestService {


    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8080/account/";


    public AccountRestService(String urlBase ,RestTemplate restTemplate) {
        //this.restTemplate = restTemplate;
        this.urlBase = urlBase;
    }

    public BigDecimal getUserBalance(User user, String token){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity<BigDecimal> bigDecimalHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<BigDecimal> bigDecimalResponseEntity = restTemplate.exchange(urlBase,
                HttpMethod.GET, bigDecimalHttpEntity, BigDecimal.class);
        return bigDecimalResponseEntity.getBody();

    }

    public List<User> listUsers(User user, String bearerToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken); // Set your bearer token here

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                urlBase+"/users",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<User>>() {}
        );

        return responseEntity.getBody();
    }

    public List<Account> getAllAccounts(User user, String bearerToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<List<Account>> response = restTemplate.exchange(
                    urlBase + "/all", HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Account>>() {
                    });
            return response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log("AccountRest getAllAccounts error --" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public Account getAccountByUsername(User user, String bearerToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken); // Set your bearer token here
        HttpEntity<Account> entity = new HttpEntity<>(httpHeaders);
        try{
            ResponseEntity<Account>response =  restTemplate.exchange(urlBase + "/users/"+ user.getUsername(),
                    HttpMethod.GET, entity, Account.class);
            return response.getBody();
        }catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log("AccountRest getAccountbyUsername error --" + e.getMessage());

        }return null;

    }



    private HttpEntity createHttpEntity(AuthenticatedUser authenticatedUser){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authenticatedUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}


