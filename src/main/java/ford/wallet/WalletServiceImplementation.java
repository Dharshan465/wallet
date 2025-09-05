package ford.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class WalletServiceImplementation implements WalletService{

    private final WalletDao walletDao;

    @Autowired
    public WalletServiceImplementation(WalletDao walletDao) {
        this.walletDao = walletDao;
    }

    @Override
    public Wallet registerNewUserWallet(Wallet newWallet) throws WalletException {
        //check user already exists with same email id

        Wallet foundWallet = walletDao.getWalletByEmailId(newWallet.getEmail());
        if (foundWallet != null) {
            throw new WalletException("User already exists with emailId: " + newWallet.getEmail());
        } else {
            walletDao.saveWallet(newWallet);
        }
        return newWallet;
    }

    @Override
    public Wallet deleteWalletByEmailId(String emailId) throws WalletNotFoundException, WalletException {
        try {
            Wallet foundWallet = walletDao.getWalletByEmailId(emailId);
            if (foundWallet == null) {
                throw new WalletNotFoundException("User not found with emailId: " + emailId);
            } else {
                walletDao.deleteWallet(emailId);
                return foundWallet;
            }
        } catch (WalletNotFoundException | WalletException e) {
            throw e;

        }

    }

    @Override
    public Double addFundsToWalletByEmailId(String emailId, Double amount) throws WalletAddFundException, WalletNotFoundException, WalletException {
        try {
            Wallet foundWallet = walletDao.getWalletByEmailId(emailId);
            if (foundWallet == null) {
                throw new WalletNotFoundException("User not found with emailId: " + emailId);
            } else {

                if (amount <= 0) {
                    throw new WalletAddFundException("Amount is less than 0, cannot add funds");
                } else {
                    double newBalance = foundWallet.getBalance() + amount;
                    foundWallet.setBalance(newBalance);
                    walletDao.updateWalletBalance(foundWallet);
                    return newBalance;
                }
            }
        } catch (WalletAddFundException | WalletNotFoundException e) {
            throw e;
        }
    }


    @Override
    public Double withdrawFundsFromWalletByEmailId(String emailId, Double amount) throws WalletWithdrawFundException, WalletNotFoundException, WalletException {
        try {
            Wallet foundWallet = walletDao.getWalletByEmailId(emailId);
            if (foundWallet == null) {
                throw new WalletNotFoundException("User not found with emailId: " + emailId);
            } else {
                if (amount <= 0) {
                    throw new WalletWithdrawFundException("Amount is less than 0, cannot add funds");
                } else if (foundWallet.getBalance() < amount) {
                    throw new WalletWithdrawFundException("Insufficient funds in the wallet to withdraw");
                } else {
                    double newBalance = foundWallet.getBalance() - amount;
                    foundWallet.setBalance(newBalance);
                    walletDao.updateWalletBalance(foundWallet);
                    return newBalance;
                }
            }
        } catch (WalletNotFoundException | WalletWithdrawFundException e) {
            throw e;
        }


    }

    @Override
    public Collection<Wallet> getAllCustomerWallets() throws WalletNotFoundException {
        try{
            Collection<Wallet> allWallets=walletDao.getAllWallets();
            if(!allWallets.isEmpty()){
                return allWallets;
            }else{
                throw new WalletNotFoundException("No wallets found in the database");
            }
        }catch(WalletNotFoundException e){
            throw e;
        }

    }

    @Override
    public Wallet getWalletByEmailId(String emailId) throws WalletNotFoundException, WalletException {
        try{
            Wallet foundWallet=walletDao.getWalletByEmailId(emailId);
            if(foundWallet==null){
                throw new WalletNotFoundException("User not found with emailId: "+emailId);
            }else{
                return foundWallet;
            }
        }catch(WalletNotFoundException | WalletException e){
            throw e;
        }
    }

    @Override
    public Wallet getWalletById(Integer walletid) throws WalletNotFoundException, WalletException {
        try{
            Wallet foundWallet=walletDao.getWalletById(walletid);
            if(foundWallet==null){
                throw new WalletNotFoundException("User not found with walletId: "+walletid);
            }else{
                return foundWallet;
            }
        }catch(WalletNotFoundException e){
            throw new WalletNotFoundException(e.getMessage());
        }catch(WalletException e){
            throw new WalletException(e.getMessage());
        }
    }

    @Override
    public Wallet updateWalletByEmailId(String emailId, Wallet newWallet) throws WalletNotFoundException, WalletException {
        try{
            Wallet foundWallet=walletDao.getWalletByEmailId(emailId);
            if(foundWallet==null){
                throw new WalletNotFoundException("User not found with emailId: "+emailId);
            }else{
                //update the wallet details
                foundWallet.setCustomerName(newWallet.getCustomerName());
                foundWallet.setPassword(newWallet.getPassword());
                walletDao.updateWalletDetails(foundWallet);
                return foundWallet;
            }
        }catch(WalletNotFoundException | WalletException e){
            throw e;
        }
    }

    @Override
    public Boolean transferFunds(String fromEmailId, String toEmailId, Double amount) throws WalletTransferFundException, WalletNotFoundException, WalletException {

        try{
            Wallet fromWallet=walletDao.getWalletByEmailId(fromEmailId);
            Wallet toWallet=walletDao.getWalletByEmailId(toEmailId);
            if(fromWallet==null){
                throw new WalletNotFoundException("Sender wallet not found with emailId: "+fromEmailId);
            }else if(toWallet==null){
                throw new WalletNotFoundException("Receiver wallet not found with emailId: "+toEmailId);
            }else{
                if(amount<=0){
                    throw new WalletTransferFundException("Amount is less than 0, cannot transfer funds");
                }else if(fromWallet.getBalance()<amount){
                    throw new WalletTransferFundException("Insufficient funds in the sender wallet to transfer");
                }else{
                    //withdraw from sender wallet
                    double newFromBalance=fromWallet.getBalance()-amount;
                    fromWallet.setBalance(newFromBalance);
                    walletDao.updateWalletBalance(fromWallet);

                    //add to receiver wallet
                    double newToBalance=toWallet.getBalance()+amount;
                    toWallet.setBalance(newToBalance);
                    walletDao.updateWalletBalance(toWallet);
                    return true;
                }
            }
        }catch(WalletNotFoundException | WalletTransferFundException | WalletException e){
throw e;        }

    }
}
