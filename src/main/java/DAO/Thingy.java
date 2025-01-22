package DAO;
import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Thingy {
    

    public List<Message> getAllmessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> message = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message mes = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                message.add(mes);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }

    public Message insertMessage(Message mes){
        if(mes.getMessage_text().length() == 0 || mes.getMessage_text().length() > 255 || Thingy2.chechkIfUserExists(mes.posted_by) == null) return null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, mes.getPosted_by());
            preparedStatement.setString(2, mes.getMessage_text());
            preparedStatement.setLong(3, mes.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                return new Message(rs.getInt(1), mes.getPosted_by(), mes.getMessage_text(), mes.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Message findMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Message mes = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return mes;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message mes = findMessageById(id);
            if(mes == null) return null;
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return mes;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Message updateMessageById(String text, int id){
        if(text.length() == 0 || text.length() > 255 || findMessageById(id) == null) return null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return findMessageById(id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> allPostsByUserId(int user){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> message = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message mes = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                message.add(mes);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return message;
    }
}
