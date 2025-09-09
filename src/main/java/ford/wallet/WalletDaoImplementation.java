package ford.wallet;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class WalletDaoImplementation  implements WalletDao {

   private final Connection connection;

   public WalletDaoImplementation() throws WalletException {
       try {
           this.connection = DriverManager.getConnection("jdbc:h2:file:./data/wallet", "sa", "password");
       } catch (SQLException e) {
           throw new WalletException("Error Occured : "+e.getMessage());
       }

   }


    @Override
    public Wallet saveWallet(Wallet newWallet) throws WalletException {
        String insertQuery = "INSERT INTO wallet(id,name,email,password,balance) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, newWallet.getWalletId());
            preparedStatement.setString(2, newWallet.getCustomerName());
            preparedStatement.setString(3, newWallet.getEmail());
            preparedStatement.setString(4, newWallet.getPassword());
            preparedStatement.setDouble(5, newWallet.getBalance());
            int recordCount = preparedStatement.executeUpdate();
            if (recordCount > 0) {
                return newWallet;
            }
            else{
                throw new WalletException("Wallet could not be added to the database");
            }
        }
        catch(SQLException e){
                throw new WalletException("Can't Execute the SQL Query Due to ->: "+e.getMessage());
            }

    }

    @Override
    public Wallet getWalletByEmailId(String emailId) throws WalletException {
       String selectQuery="SELECT * FROM wallet WHERE email=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
            preparedStatement.setString(1,emailId);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                Wallet foundWallet=new Wallet();
                foundWallet.setWalletId(resultSet.getInt("id"));
                foundWallet.setCustomerName(resultSet.getString("name"));
                foundWallet.setEmail(resultSet.getString("email"));
                foundWallet.setPassword(resultSet.getString("password"));
                foundWallet.setBalance(resultSet.getDouble("balance"));
                return foundWallet;

            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new WalletException(e.getMessage());
        }

    }

    @Override
    public Wallet updateWalletBalance(Wallet newWallet) throws WalletException {
       String updateQuery="UPDATE wallet SET balance=? WHERE email=?";
       try{
           PreparedStatement preparedStatement=connection.prepareStatement(updateQuery);
           preparedStatement.setDouble(1,newWallet.getBalance());
           preparedStatement.setString(2,newWallet.getEmail());
           int recordCount=preparedStatement.executeUpdate();
           if(recordCount>0){
               return newWallet;
           }else{
               throw new WalletException("Wallet could not be updated in the database");
           }
       }catch(SQLException e){
           throw new WalletException(e.getMessage());
       }
    }

    @Override
    public Wallet updateWalletDetails(Wallet newWallet) throws WalletException {
        String updateDetailsQuery="UPDATE wallet SET name=? ,password=? WHERE email=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(updateDetailsQuery);
            preparedStatement.setString(1,newWallet.getCustomerName());
            preparedStatement.setString(2,newWallet.getPassword());
            preparedStatement.setString(3,newWallet.getEmail());
            int recordCount=preparedStatement.executeUpdate();
            if(recordCount>0){
                return newWallet;
            }else{
                throw new WalletException("Wallet details could not be updated in the database");
            }
        }catch(SQLException e) {
            throw new WalletException(e.getMessage());
        }

    }


    @Override
    public String deleteWallet(String emailId) throws WalletNotFoundException {
       String deleteQuery="DELETE FROM wallet WHERE email=?";
       try{
              PreparedStatement preparedStatement=connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1,emailId);
                int recordCount=preparedStatement.executeUpdate();
                if(recordCount>0){
                    return "Wallet Deleted Successfully for "+emailId;
                }
                else{
                    throw new WalletNotFoundException("Wallet could not be deleted from the database");
                }
       }catch(SQLException e){
              throw new WalletNotFoundException(e.getMessage());
       }

    }

    @Override
    public Collection<Wallet> getAllWallets() throws WalletNotFoundException {
       String selectAllQuery="SELECT * FROM wallet";
       try{
           PreparedStatement preparedStatement=connection.prepareStatement(selectAllQuery);
           ResultSet resultSet=preparedStatement.executeQuery();
           List<Wallet> allWallets =new ArrayList<>();

           while(resultSet.next()){
                Wallet foundWallets=new Wallet();
                foundWallets.setWalletId(resultSet.getInt("id"));
                foundWallets.setCustomerName(resultSet.getString("name"));
                foundWallets.setEmail(resultSet.getString("email"));
                foundWallets.setPassword(resultSet.getString("password"));
                foundWallets.setBalance(resultSet.getDouble("balance"));
                allWallets.add(foundWallets);
           }
           return allWallets;

       }catch(SQLException e){
              throw new WalletNotFoundException(e.getMessage());
       }


    }

    @Override
    public Wallet getWalletById(Integer walletId) throws WalletNotFoundException,WalletException {
        String selectQuery="SELECT * FROM wallet WHERE id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1,walletId);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                Wallet foundWallet=new Wallet();
                foundWallet.setWalletId(resultSet.getInt("id"));
                foundWallet.setCustomerName(resultSet.getString("name"));
                foundWallet.setEmail(resultSet.getString("email"));
                foundWallet.setPassword(resultSet.getString("password"));
                foundWallet.setBalance(resultSet.getDouble("balance"));
                return foundWallet;

            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new WalletNotFoundException(e.getMessage());
        }    }
}
