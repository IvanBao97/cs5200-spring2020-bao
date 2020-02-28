package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao implements RoleImpl{

    private RoleDao() {}

    private static RoleDao instance = null;
    public static RoleDao getInstance() {
        if(instance == null)
            instance = new RoleDao();
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    private static final String ASSIGN_WEBSITE_ROLE = "INSERT INTO website_role(developer_id, website_id, role) VALUES (?,?,?) ";
    private static final String ASSIGN_PAGE_ROLE = "INSERT INTO page_role(developer_id, page_id, role) VALUES (?,?,?) ";
    private static final String DELETE_WEBSITE_ROLE = "DELETE FROM website_role WHERE developer_id=? AND website_id=? AND role=?";
    private static final String DELETE_PAGE_ROLE = "DELETE FROM page_role WHERE developer_id=? AND page_id=? AND role=? ";
    private static final String FIND_PAGE_ROLE_FOR_DEVELOPER = "SELECT * FROM `page_role` WHERE `developer_id`=? AND `page_id`=?";

    @Override
    public void assignWebsiteRole(int developerId, int websiteId, String role) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(ASSIGN_WEBSITE_ROLE);
            preparedStatement.setInt(1,developerId);
            preparedStatement.setInt(2,websiteId);
            preparedStatement.setString(3,role);
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
    public void assignPageRole(int developerId, int pageId, String role) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(ASSIGN_PAGE_ROLE);
            preparedStatement.setInt(1,developerId);
            preparedStatement.setInt(2,pageId);
            preparedStatement.setString(3,role);
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
    public void deleteWebsiteRole(int developerId, int websiteId, String role) {
        try{
            conn = Connection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_WEBSITE_ROLE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, websiteId);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePageRole(int developerId, int pageId, String role) {
        try{
            conn = Connection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PAGE_ROLE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, pageId);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role findPageRoleForDeveloper(int developerId, int pageId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_PAGE_ROLE_FOR_DEVELOPER);
            preparedStatement.setInt(1,developerId);
            preparedStatement.setInt(2,pageId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String role = resultSet.getString("role");
                return new Role(developerId,pageId,role);
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
}
