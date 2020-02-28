package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PageDao implements PageImpl {

    public PageDao() {}

    private static PageDao instance = null;
    public static PageDao getInstance() {
        if(instance == null) {
            instance = new PageDao();
        }
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Collection<Page> collection = new ArrayList<>();

    private final String CREATE_PAGE_FOR_WEBSITE = "INSERT INTO page(id, website_id, title, description, created, updated, views) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String FIND_ALL_PAGES = "SELECT * FROM page";
    private final String FIND_PAGE_BY_ID = "SELECT * FROM page WHERE id=?";
    private final String FIND_PAGE_FOR_WEBSITE = "SELECT * FROM page WHERE website_id = ?";
    private final String UPDATE_PAGE = "UPDATE `page` SET `website_id`=?, `title`=?, `description`=?, `created`=?, `updated`=?, `views`=? WHERE `id`=?";
    private final String DELETE_PAGE = "DELETE FROM page WHERE id=?";


    @Override
    public void createPageForWebsite(int websiteId, Page page) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(CREATE_PAGE_FOR_WEBSITE);
            preparedStatement.setInt(1,page.getId());
            preparedStatement.setInt(2,websiteId);
            preparedStatement.setString(3,page.getTitle());
            preparedStatement.setString(4,page.getDescription());
            preparedStatement.setDate(5,page.getCreated());
            preparedStatement.setDate(6,page.getUpdated());
            preparedStatement.setInt(7,page.getViews());
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
    public Collection<Page> findAllPages() {
        try {
            conn = Connection.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_PAGES);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date created = resultSet.getDate("created");
                Date updated = resultSet.getDate("updated");
                int views = resultSet.getInt("views");
                int website_id = resultSet.getInt("website_id");

                Page page = new Page(id, title, description, created, updated, views);
                page.setWebsite_id(website_id);
                collection.add(page);
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
    public Page findPageById(int pageId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_PAGE_BY_ID);
            preparedStatement.setInt(1,pageId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date created = resultSet.getDate("created");
                Date updated = resultSet.getDate("updated");
                int views = resultSet.getInt("views");
                int website_id = resultSet.getInt("website_id");

                Page page = new Page(id, title, description, created, updated, views);
                page.setWebsite_id(website_id);
                return page;
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
    public Collection<Page> findPagesForWebsite(int websiteId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_PAGE_FOR_WEBSITE);
            preparedStatement.setInt(1,websiteId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date created = resultSet.getDate("created");
                Date updated = resultSet.getDate("updated");
                int views = resultSet.getInt("views");

                Page page = new Page(id, title, description, created, updated, views);
                page.setWebsite_id(websiteId);
                collection.add(page);
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
    public int updatePage(int pageId, Page page) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(UPDATE_PAGE);
            preparedStatement.setInt(1,page.getWebsite_id());
            preparedStatement.setString(2, page.getTitle());
            preparedStatement.setString(3, page.getDescription());
            preparedStatement.setDate(4, page.getCreated());
            preparedStatement.setDate(5, page.getUpdated());
            preparedStatement.setInt(6, page.getViews());
            preparedStatement.setInt(7, pageId);
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
        return -1;
    }

    @Override
    public int deletePage(int pageId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_PAGE);
            preparedStatement.setInt(1,pageId);
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
