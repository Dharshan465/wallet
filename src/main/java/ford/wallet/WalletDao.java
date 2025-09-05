package ford.wallet;

import java.util.Collection;

public interface WalletDao {
    //CRUD

    Wallet saveWallet(Wallet newWallet) throws WalletException;

    Wallet getWalletByEmailId(String emailId) throws  WalletException;

    Wallet updateWalletBalance(Wallet newWallet) throws  WalletException;

    Wallet updateWalletDetails(Wallet newWallet) throws  WalletException;

    String deleteWallet(String emailId) throws WalletNotFoundException;

    Collection<Wallet> getAllWallets() throws WalletNotFoundException;


    Wallet getWalletById(Integer walletid) throws WalletNotFoundException, WalletException;
}
