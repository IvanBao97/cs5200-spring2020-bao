package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao implements UserImpl{

    public UserDao() { }

    private static UserDao instance = null;
    public static UserDao getInstance() {
        if(instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;

    private final String CREATE_PERSON = "INSERT INTO person (id, first_name, last_name, username, password, email, dob, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_USER = "INSERT INTO `user`(`id`, `user_agreement`) VALUE(?, ?)";

    @Override
    public void createUser(User user) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(CREATE_PERSON);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getFirst_name());
            preparedStatement.setString(3, user.getLast_name());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setDate(7, user.getDob());
            preparedStatement.setString(8, user.getAddress());
            preparedStatement.setString(9, user.getPhone());
            preparedStatement = conn.prepareStatement(CREATE_USER);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setBoolean(2,user.isUser_agreement());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
    }
}
