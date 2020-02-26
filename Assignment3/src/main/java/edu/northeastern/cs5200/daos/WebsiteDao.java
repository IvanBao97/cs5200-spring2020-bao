package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.Website;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class WebsiteDao implements WebsiteImpl {

    private WebsiteDao() { }

    private static WebsiteDao instance = null;
    public static WebsiteDao getInstance() {
        if(instance == null) {
            instance = new WebsiteDao();
        }
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Collection<Website> collection = new ArrayList<>();

    private static final String CREATE_WEBSITE_FOR_DEVELOPER = "INSERT INTO website(id, name, description, created, updated, visits, developer_id) VALUE(?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_WEBSITE = "SELECT * FROM website";
    private static final String FIND_WEBSITES_FOR_DEVELOPER = "SELECT * FROM website WHERE developer_id = ?";
    private static final String FIND_WEBSITES_BY_ID = "SELECT * FROM website WHERE id = ?";
    private static final String UPDATE_WEBSITE = "UPDATE website SET name=?, description=?, created=?, updated=?, visits=?, developer_id=? WHERE id = ?";
    private static final String DELETE_WEBSITE = "DELETE FROM website where id=?";


    @Override
    public void createWebsiteForDeveloper(int developerId, Website website) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(CREATE_WEBSITE_FOR_DEVELOPER);
            preparedStatement.setInt(1,website.getId());
            preparedStatement.setString(2,website.getName());
            preparedStatement.setString(3,website.getDescription());
            preparedStatement.setDate(4,website.getCreated());
            preparedStatement.setDate(5,website.getUpdated());
            preparedStatement.setInt(6,website.getVisits());
            preparedStatement.setInt(7,developerId);
            preparedStatement.executeUpdate();
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

    @Override
    public Collection<Website> findAllWebsite() {
        try {
            conn = Connection.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_WEBSITE);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date created = resultSet.getDate("created");
                Date updated = resultSet.getDate("updated");
                int visits = resultSet.getInt("visits");
                int developer_id = resultSet.getInt("developer_id");

                Website website = new Website(id, name, description, created, updated, visits);
                website.setDeveloper_id(developer_id);
                collection.add(website);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return collection;
    }

    @Override
    public Collection<Website> findWebsitesForDeveloper(int developerId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_WEBSITES_FOR_DEVELOPER);
            preparedStatement.setInt(1,developerId);
            resultSet = preparedStatement.executeQuery();

            while(this.resultSet.next()){
                int id = this.resultSet.getInt("id");
                String name = this.resultSet.getString("name");
                String description = this.resultSet.getString("description");
                Date created = this.resultSet.getDate("created");
                Date updated = this.resultSet.getDate("updated");
                int visits = this.resultSet.getInt("visits");

                Website website = new Website(id, name, description, created, updated, visits);
                website.setDeveloper_id(developerId);
                collection.add(website);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return collection;
    }

    @Override
    public Website findWebsiteById(int websiteId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_WEBSITES_BY_ID);
            preparedStatement.setInt(1,websiteId);
            resultSet = preparedStatement.executeQuery();

            while(this.resultSet.next()){
                String name = this.resultSet.getString("name");
                String description = this.resultSet.getString("description");
                Date created = this.resultSet.getDate("created");
                Date updated = this.resultSet.getDate("updated");
                int visits = this.resultSet.getInt("visits");

                Website website = new Website(websiteId, name, description, created, updated, visits);
                return website;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return null;
    }

    @Override
    public int updateWebsite(int websiteId, Website website) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(UPDATE_WEBSITE);
            preparedStatement.setString(1,website.getName());
            preparedStatement.setString(2, website.getDescription());
            preparedStatement.setDate(3, website.getCreated());
            preparedStatement.setDate(4, website.getUpdated());
            preparedStatement.setInt(5, website.getVisits());
            preparedStatement.setInt(6, website.getDeveloper_id());
            preparedStatement.setInt(7, websiteId);
            return preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return 0;
    }

    @Override
    public int deleteWebsite(int websiteId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_WEBSITE);
            preparedStatement.setInt(1,websiteId);
            return preparedStatement.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                Connection.closeConnection();
            }
        }
        return -1;
    }
}
