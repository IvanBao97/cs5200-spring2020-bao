package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.Developer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DeveloperDao implements DeveloperImpl{

    public DeveloperDao() { }

    private static DeveloperDao instance = null;
    public static DeveloperDao getInstance() {
        if(instance == null) {
            instance = new DeveloperDao();
        }
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Collection<Developer> collection;

    private static final String INSERT_PERON = "INSERT INTO person (id, first_name, last_name, username, password, email, dob, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_DEVELOPER = "INSERT INTO developer (id, developer_key) VALUES (?, ?)";
    private static final String FIND_ALL_DEVELOPERS = "SELECT * FROM `developer`, `person` WHERE `developer`.`id`=`person`.`id`";
    private static final String FIND_DEVELOPER_BY_ID = "SELECT * FROM `developer`, `person` WHERE `developer`.`id`=`person`.`id` AND `developer`.`id`=?";
    private static final String FIND_DEVELOPER_BY_USERNAME = "SELECT * FROM `developer`, `person` WHERE `developer`.`id`=`person`.`id` AND `person`.`username`=?";
    private static final String FIND_DEVELOPER_BY_CREDENTIALS = "SELECT * FROM `developer`, `person` WHERE `developer`.id=`person`.`id` AND `person`.`username`=? AND `person`.`password`=?";
    private static final String UPDATE_PERSON = "UPDATE person SET id=?, first_name=?, last_name=?, username=?, password=?, email=?, dob=?, address=?, phone=? WHERE id=?";
    private static final String UPDATE_DEVELOPER = "UPDATE developer SET id=?, developer_key=? WHERE id=?";
    private static final String DELETE_DEVELOPER_BY_ID = "DELETE FROM `person` WHERE `id`=?";


    @Override
    public void createDeveloper(Developer developer) {
        try {
            conn = Connection.getConnection();
            //Insert Person
            preparedStatement = conn.prepareStatement(INSERT_PERON);
            preparedStatement.setInt(1,developer.getId());
            preparedStatement.setString(2,developer.getFirst_name());
            preparedStatement.setString(3,developer.getLast_name());
            preparedStatement.setString(4,developer.getUsername());
            preparedStatement.setString(5,developer.getPassword());
            preparedStatement.setString(6,developer.getEmail());
            preparedStatement.setDate(7,developer.getDob());
            preparedStatement.setString(8,developer.getAddress());
            preparedStatement.setString(9,developer.getPhone());
            preparedStatement.executeUpdate();
            //Insert Developer
            preparedStatement = conn.prepareStatement(INSERT_DEVELOPER);
            preparedStatement.setInt(1,developer.getId());
            preparedStatement.setString(2,developer.getDeveloper_key());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
    }

    @Override
    public Collection<Developer> findAllDevelopers() {
        try {
            conn = Connection.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_DEVELOPERS);
            collection = new ArrayList<>();
            while(resultSet.next()){
                //Get attribute values
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date dob = resultSet.getDate("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String developer_key = resultSet.getString("developer_key");
                //Create a new developer Object and Add it into Collection
                Developer new_developer = new Developer(id, first_name, last_name, username, password, email, dob, address, phone, developer_key);
                collection.add(new_developer);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }

        return collection;
    }

    @Override
    public Developer findDeveloperById(int developerId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_DEVELOPER_BY_ID);
            preparedStatement.setInt(1,developerId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date dob = resultSet.getDate("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String developer_key = resultSet.getString("developer_key");

                return new Developer(id, first_name, last_name, username, password, email, dob, address, phone, developer_key);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }

        return null;
    }

    @Override
    public Developer findDeveloperByUsername(String username) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_DEVELOPER_BY_USERNAME);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                Date dob = resultSet.getDate("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String developer_key = resultSet.getString("developer_key");

                return new Developer(id, first_name, last_name, username, password, email, dob, address, phone, developer_key);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return null;
    }

    @Override
    public Developer findDeveloperByCredentials(String username, String password) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_DEVELOPER_BY_CREDENTIALS);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Date dob = resultSet.getDate("dob");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String developer_key = resultSet.getString("developer_key");

                return new Developer(id, first_name, last_name, username, password, email, dob, address, phone, developer_key);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return null;
    }

    @Override
    public int updateDeveloper(int developerId, Developer developer) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(UPDATE_PERSON);
            preparedStatement.setInt(1,developer.getId());
            preparedStatement.setString(2,developer.getFirst_name());
            preparedStatement.setString(3, developer.getLast_name());
            preparedStatement.setString(4, developer.getUsername());
            preparedStatement.setString(5, developer.getPassword());
            preparedStatement.setString(6, developer.getEmail());
            preparedStatement.setDate(7, developer.getDob());
            preparedStatement.setString(8, developer.getAddress());
            preparedStatement.setString(9, developer.getPhone());
            preparedStatement.setInt(10,developerId);
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement(UPDATE_DEVELOPER);
            preparedStatement.setInt(1, developer.getId());
            preparedStatement.setString(2,developer.getDeveloper_key());
            preparedStatement.setInt(3,developerId);

            return preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return -1;
    }

    @Override
    public int deleteDeveloper(int developerId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_DEVELOPER_BY_ID);
            preparedStatement.setInt(1,developerId);
            return preparedStatement.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return -1;
    }
}
