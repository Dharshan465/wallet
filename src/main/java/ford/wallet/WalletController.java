package ford.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    //CRUD on Wallet Resource

    //Create a wallet resource

    @PostMapping
    public ResponseEntity<?> registerNewWalletUser(@RequestBody Wallet newWallet) throws WalletException {
        try {
            this.walletService.registerNewUserWallet(newWallet);
            return ResponseEntity.ok("User registered successfully with emailId: " + newWallet.getEmail());
        } catch (WalletException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occurred : " + e.getMessage());

        }
    }

    @GetMapping("/emailId")
    public ResponseEntity<?> getUserWalletByEmailId(@RequestBody WalletFundsDTO walletFundsDTO) throws WalletException {
        try {
            return ResponseEntity.ok(this.walletService.getWalletByEmailId(walletFundsDTO.getEmail()));
        } catch (WalletNotFoundException e) {
            //return ResponseEntity.ok("Error occurred : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error Occurred : " + e.getMessage());
        } catch (WalletException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error retrieving wallet: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/id/{walletId}")
    public Wallet getUserWalletById(@PathVariable Integer walletId) throws WalletException, WalletNotFoundException {
        return this.walletService.getWalletById(walletId);
    }


    @GetMapping("/allWallets")
    public Collection<Wallet> getAllWallets() throws WalletException, WalletNotFoundException {
        return this.walletService.getAllCustomerWallets();
    }


    @PutMapping("/{emailId}")
    public String updateUserWallet(@PathVariable String emailId, @RequestBody Wallet newWallet) throws WalletException, WalletNotFoundException {
        try {
            this.walletService.updateWalletByEmailId(emailId, newWallet);
            return "Updated Wallet details : "+ newWallet.getEmail();
        } catch (WalletNotFoundException | WalletException e) {
            return "Error occurred : " + e.getMessage();
        }
    }

    //DTO to get parameters
    @PatchMapping("/addfunds")
    public Double addFundsToWallet(@RequestBody WalletFundsDTO walletFundsDTO) throws WalletException, WalletNotFoundException, WalletAddFundException {
        return this.walletService.addFundsToWalletByEmailId(walletFundsDTO.getEmail(), walletFundsDTO.getBalance());
    }

    @PatchMapping("/withdrawfunds/{emailId}/{amount}")
    public Double withdrawFundsFromWallet(@PathVariable String emailId, @PathVariable Double amount) throws WalletException, WalletNotFoundException, WalletWithdrawFundException {
        return this.walletService.withdrawFundsFromWalletByEmailId(emailId, amount);
    }

    @PatchMapping("/transferfunds/{fromEmailId}/{toEmailId}/{amount}")
    public Boolean transferFundsBetweenWallets(@PathVariable String fromEmailId, @PathVariable String toEmailId, @PathVariable Double amount) throws WalletException, WalletNotFoundException, WalletTransferFundException {
        return this.walletService.transferFunds(fromEmailId, toEmailId, amount);
    }

    @DeleteMapping("/{emailId}")
    public String deleteUserWalletByEmailId(@PathVariable String emailId) throws WalletException, WalletNotFoundException {
        this.walletService.deleteWalletByEmailId(emailId);
        return "User wallet deleted successfully with emailId: " + emailId;

    }
    //@RequestParam
    //1.we can have default value
    //2.we can make it optional

    @GetMapping
    public String getInfo(@RequestParam(value = "name", defaultValue = "User", required = false) String name) {
        return "Hello, " + name + "! Welcome to the Wallet Service.";
    }









}
