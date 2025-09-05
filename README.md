# Online Digital Wallet Application (Layered Architecture with Spring Boot, Spring Data JPA(JDBC), and H2 Database)

---

This project implements an Online Digital Wallet Application using a layered architecture with Spring Boot, Spring Data JPA (JDBC), and H2 Database. The application allows users to register, add funds, withdraw funds, transfer funds, view all wallets, and delete wallets.

---

### Project Structure:
```
wallet/
├── Wallet.java
├── WalletAddFundException.java
├── WalletApplication.java
├── WalletController.java
├── WalletDao.java
├── WalletDaoImplementation.java
├── WalletException.java
├── WalletNewUserException.java
├── WalletNotFoundException.java
├── WalletService.java
├── WalletServiceImplementation.java
├── WalletTransferFundException.java
├── WalletWithdrawFundException.java
└── WalletExceptionHandler.java

```

---

### Key Components:

#### WalletController.java
- Handles HTTP requests and responses.
- Defines endpoints for wallet operations.
- Uses `@RestController` and `@RequestMapping("api/v1/wallet")` annotations.
- Injects `WalletService` using `@Autowired`.
- In WalletServiceImplementation, injects WalletDao using @Autowired.
- Endpoints:
    - `POST` : Register a new wallet.
    - `GET /allWallets`: Get all wallets.
    - `GET /{emailId}`: Get a wallet by email.
    - `GET /id/{WalletId}`: Get a wallet by ID.
    - `PUT /{emailId}`:Updates wallet details.
    - `PATCH /addFunds/{emailId}/{amount}`: Add funds to a wallet.
    - `PATCH /withdrawFunds/{emailId}/{amount}`: Withdraw funds from a wallet.
    - `PATCH /transferFunds/{fromEmailId}/{toEmailId}/{amount}`: Transfer funds between wallets.
    - `DELETE /{email}`: Delete a wallet by email.


#### WalletExceptionHandler.java
- Handles custom exceptions and returns appropriate HTTP responses.
- Uses `@ControllerAdvice` and `@ExceptionHandler` annotations.
- Custom exceptions handled:
    - `WalletNotFoundException`
    - `WalletAddFundException`
    - `WalletWithdrawFundException`
    - `WalletTransferFundException`
    - `WalletException`
- Returns `ResponseEntity<Object>` with error messages and HTTP status codes.


#### Service Methods:
- registerNewUserWallet
- addFundsToWalletByEmailID
- withdrawFundsFromWalletByEmailID
- getAllCustomerWallets
- deleteWalletByEmailID
- transferFunds
- getWalletByEmailID
- getWalletByID
- updateWalletByEmailID

#### DAO Methods:
- saveWallet
- getWalletByEmailID
- deleteWallet
- getAllWallets
- getWalletByID
- updateWalletBalance
- updateWalletDetails


#### H2 Database:
- TABLE WALLET
- COLUMNS: id, name, email, password, balance

## GitHub Repository: https://github.com/Dharshan465/wallet.git