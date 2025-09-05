package ford.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST) // Or HttpStatus.INTERNAL_SERVER_ERROR
public class WalletException extends Exception {
    public WalletException(String message) {
        super(message);
    }
}
