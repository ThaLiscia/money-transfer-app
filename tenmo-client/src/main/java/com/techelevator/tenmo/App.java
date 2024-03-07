package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountRestService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountRestService accountRestService = new AccountRestService(API_BASE_URL + "account", new RestTemplate());
    private final TransferService transferService = new TransferService(API_BASE_URL + "transfer", new RestTemplate());

    private AuthenticatedUser currentUser;
    private final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
        scanner.close();
    }


    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


    private void viewCurrentBalance() {
        System.out.println("current balance: $" + accountRestService.getUserBalance(currentUser.getUser(), currentUser.getToken()));
    }


    private void viewUsers() {
        System.out.println("Users ");
        System.out.printf("%-10s %-20s%n", "ID", "Name");
        System.out.println("-------------------------------------------");
        List<Account> accountList = accountRestService.getAllAccounts(currentUser.getUser(), currentUser.getToken());
        for (Account account : accountList) {
            System.out.printf("%-10s %-20s%n", account.getAccountId(), account.getUsername());
        }
    }

    private void viewTransferHistory() {
        List<Transfer> transfers = transferService.getAllTransfers(currentUser.getUser(), currentUser.getToken());
        transferPrintLayout();
        for (Transfer transfer : transfers) {
            System.out.printf("%-10d %-25s $%-10s%n",
                    transfer.getTransferId(),
                    transfer.getUserFrom() + "/" + transfer.getUserTo(),
                    transfer.getAmount());

        }

        System.out.println("---------");
        int transferIdRequest = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
        Transfer transfer = transferService.getTransferByTransferId(transferIdRequest, currentUser.getToken());
        if (transfer == null) {
            System.out.println("Transfer number not found");
        } else {
            System.out.println(transfer);
        }
    }

    private void viewPendingRequests() {
        System.out.println("This function is currently unavailable. \nPlease wait until developer finishes creating this method.\nThank you for your patience.");

//
    }

    private void transferPrintLayout() {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.printf("%-10s %-25s %-10s", "ID", "From/To", "Amount\n");
        System.out.println("-------------------------------------------");

    }
//

    private void sendBucks() {
        viewUsers();
//
        int answerInt = consoleService.promptForInt("What is the accountId of the intended recipient?");
        BigDecimal answerBig = consoleService.promptForBigDecimal("How much would you like to send?");
        if (answerBig.compareTo(BigDecimal.ZERO) <= 0) {
//            System.out.println("Get bent");
            System.out.println("You cannot send an amount less than 0");
        }
        Transfer transfer = new Transfer((accountRestService.getAccountByUsername(currentUser.getUser(), currentUser.getToken()).getAccountId()), answerInt, answerBig);
        transferService.createTransfer(transfer, currentUser.getToken());
    }

    private void requestBucks() {
        System.out.println("This function is currently unavailable. \nPlease wait until developer finishes creating this method.\nThank you for your patience.");
//


    }
}