package com.techelevator.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.services.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


    public class TransferServiceTests {
        private TransferService transferService;
        private RestTemplate restTemplate;
        private static final String URL_BASE_TRANSFER = "http://localhost:8080/transfer";

        @Before
        public void setUp() {
            transferService = new TransferService(URL_BASE_TRANSFER, restTemplate);
        }

        @Test
        public void getAllTransfersTest() {
            User user = new User();
            String BearerToken = "Token";
            List<Transfer> expectedTransfers = Arrays.asList(new Transfer(), new Transfer());
            ResponseEntity<List<Transfer>> expectedResponseEntity = new ResponseEntity<>(expectedTransfers, HttpStatus.OK);
            List<Transfer> actualTransfers = transferService.getAllTransfers(user, BearerToken);

            assertEquals(expectedTransfers, actualTransfers);
        }

    }