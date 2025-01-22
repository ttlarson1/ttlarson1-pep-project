package DAO;
import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Thingy2 {
    public Account insertUser(Account acc){
        if(acc.getUsername().length() == 0 || acc.getPassword().length() < 4 || checkIfUsernameIsTaken(acc) != null) return null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                return new Account(rs.getInt(1), acc.getUsername(), acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account validateLogin(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Account checkIfUsernameIsTaken(Account acc){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, acc.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Account chechkIfUserExists(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
