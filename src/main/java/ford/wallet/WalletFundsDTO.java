package ford.wallet;

public class WalletFundsDTO {
    private String email;
    private double balance;

    public WalletFundsDTO() {
    }

    public WalletFundsDTO(String email, double amount) {
        this.email = email;
        this.balance = amount;
    }

    public WalletFundsDTO(String email) {
        this.email = email;
        this.balance = 0.0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
