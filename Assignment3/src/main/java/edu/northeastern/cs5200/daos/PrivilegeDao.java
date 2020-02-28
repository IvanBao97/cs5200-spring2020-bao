package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrivilegeDao implements PrivilegeImpl {

    private PrivilegeDao(){}

    private static PrivilegeDao instance = null;
    public static PrivilegeDao getInstance(){
        if(instance == null)
            instance = new PrivilegeDao();
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;


    private static final String ASSIGN_WEBSITE_PRIVILEGE = "INSERT INTO website_privilege(developer_id, website_id, privilege) VALUES(?, ?, ?)";
    private static final String ASSIGN_PAGE_PRIVILEGE = "INSERT INTO page_privilege(developer_id, page_id, privilege) VALUES(?, ?, ?)";
    private static final String DELETE_WEBSITE_PRIVILEGE = "DELETE FROM website_privilege WHERE developer_id=? AND website_id=?  privilege=?)";
    private static final String DELETE_PAGE_PRIVILEGE = "DELETE FROM page_privilege WHERE developer_id=? AND page_id=?  privilege=?)";


    @Override
    public void assignWebsitePrivilege(int developerId, int websiteId, String privilege) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(ASSIGN_WEBSITE_PRIVILEGE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, websiteId);
            preparedStatement.setString(3, privilege);
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
    public void assignPagePrivilege(int developerId, int pageId, String privilege) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(ASSIGN_PAGE_PRIVILEGE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, pageId);
            preparedStatement.setString(3, privilege);
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
    public void deleteWebsitePrivilege(int developerId, int websiteId, String privilege) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_WEBSITE_PRIVILEGE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, websiteId);
            preparedStatement.setString(3, privilege);
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
    public void deletePagePrivilege(int developerId, int pageId, String privilege) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_PAGE_PRIVILEGE);
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, pageId);
            preparedStatement.setString(3, privilege);
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
}
