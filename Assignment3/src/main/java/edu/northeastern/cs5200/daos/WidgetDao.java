package edu.northeastern.cs5200.daos;

import edu.northeastern.cs5200.Connection;
import edu.northeastern.cs5200.modules.Widget;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class WidgetDao implements WidgetImpl{

    private WidgetDao(){}

    private static WidgetDao instance = null;
    public static WidgetDao getInstance() {
        if(instance == null)
            instance = new WidgetDao();
        return instance;
    }

    java.sql.Connection conn;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    Collection<Widget> collection = new ArrayList<>();

    private static final String CREATE_WIDGET_FOR_PAGE = "INSERT INTO widget(`id`, `name`, `page_id`, `width`, `height`, `css_class`, `css_style`, `text`, `order`, `heading_size`, `html`, `image_src`, `youtube_url`, `youtube_shareable`, `youtube_expandable`, `dtype`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_WIDGETS = "SELECT * FROM `widget`";
    private static final String FIND_WIDGET_BY_ID = "SELECT * FROM `widget` WHERE `id`=?";
    private static final String FIND_WIDGETS_FOR_PAGE = "SELECT * FROM `widget` WHERE `page_id`=?";
    private static final String UPDATE_WIDGET = "UPDATE `widget` SET `name`=?, `page_id`=?, `width`=?, `height`=?, `css_class`=?, `css_style`=?, `text`=?, `order`=?, `heading_size`=?, `html`=?, `image_src`=?, `youtube_url`=?, `youtube_shareable`=?, `youtube_expandable`=?, `dtype`=? WHERE `id`=?";
    private static final String DELETE_WIDGET_BY_ID = "DELETE FROM `widget` WHERE `id`=?";

    @Override
    public void createWidgetForPage(int pageId, Widget widget) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(CREATE_WIDGET_FOR_PAGE);
            preparedStatement.setInt(1,widget.getId());
            preparedStatement.setString(2, widget.getName());
            preparedStatement.setInt(3, pageId);
            preparedStatement.setInt(4, widget.getWidth());
            preparedStatement.setInt(5, widget.getHeight());
            preparedStatement.setString(6, widget.getCssClass());
            preparedStatement.setString(7, widget.getCssStyle());
            preparedStatement.setString(8, widget.getText());
            preparedStatement.setInt(9, widget.getOrder());
            preparedStatement.setInt(10, widget.getOrder());
            preparedStatement.setString(11, widget.getHtml());
            preparedStatement.setString(12, widget.getSrc());
            preparedStatement.setString(13, widget.getUrl());
            preparedStatement.setBoolean(14, widget.isShareable());
            preparedStatement.setBoolean(15, widget.isExpandable());
            preparedStatement.setString(16, widget.getDtype().toString());
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
    public Collection<Widget> findAllWidgets() {
        try {
            conn = Connection.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_WIDGETS);
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int page_id = resultSet.getInt("page_id");
                String name = resultSet.getString("name");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                String css_class = resultSet.getString("css_class");
                String css_style = resultSet.getString("css_style");
                String text = resultSet.getString("text");
                int order = resultSet.getInt("order");
                int heading_size = resultSet.getInt("heading_size");
                String html = resultSet.getString("html");
                String image_src = resultSet.getString("image_src");
                String youtube_url = resultSet.getString("youtube_url");
                Boolean youtube_shareable = resultSet.getBoolean("youtube_shareable");
                Boolean youtube_expandable = resultSet.getBoolean("youtube_expandable");
                Widget.type type = Widget.type.valueOf(resultSet.getString("DTYPE"));

                Widget widget = new Widget(id, page_id, name, width, height, css_class, css_style, text, order, heading_size, html, image_src, youtube_url, youtube_shareable, youtube_expandable, type);
                collection.add(widget);
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
    public Widget findWidgetById(int widgetId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_WIDGET_BY_ID);
            preparedStatement.setInt(1,widgetId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int page_id = resultSet.getInt("page_id");
                String name = resultSet.getString("name");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                String css_class = resultSet.getString("css_class");
                String css_style = resultSet.getString("css_style");
                String text = resultSet.getString("text");
                int order = resultSet.getInt("order");
                int heading_size = resultSet.getInt("heading_size");
                String html = resultSet.getString("html");
                String image_src = resultSet.getString("image_src");
                String youtube_url = resultSet.getString("youtube_url");
                Boolean youtube_shareable = resultSet.getBoolean("youtube_shareable");
                Boolean youtube_expandable = resultSet.getBoolean("youtube_expandable");
                Widget.type type = Widget.type.valueOf(resultSet.getString("DTYPE"));

                Widget widget = new Widget(id, page_id, name, width, height, css_class, css_style, text, order, heading_size, html, image_src, youtube_url, youtube_shareable, youtube_expandable, type);
                return widget;
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
    public Collection<Widget> findWidgetsForPage(int pageId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(FIND_WIDGETS_FOR_PAGE);
            preparedStatement.setInt(1, pageId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int page_id = resultSet.getInt("page_id");
                String name = resultSet.getString("name");
                int width = resultSet.getInt("width");
                int height = resultSet.getInt("height");
                String css_class = resultSet.getString("css_class");
                String css_style = resultSet.getString("css_style");
                String text = resultSet.getString("text");
                int order = resultSet.getInt("order");
                int heading_size = resultSet.getInt("heading_size");
                String html = resultSet.getString("html");
                String image_src = resultSet.getString("image_src");
                String youtube_url = resultSet.getString("youtube_url");
                Boolean youtube_shareable = resultSet.getBoolean("youtube_shareable");
                Boolean youtube_expandable = resultSet.getBoolean("youtube_expandable");
                Widget.type type = Widget.type.valueOf(resultSet.getString("DTYPE"));

                Widget widget = new Widget(id, page_id, name, width, height, css_class, css_style, text, order, heading_size, html, image_src, youtube_url, youtube_shareable, youtube_expandable, type);
                collection.add(widget);
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
    public int updateWidget(int widgetId, Widget widget) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(UPDATE_WIDGET);
            preparedStatement.setString(1, widget.getName());
            preparedStatement.setInt(2, widget.getPage_id());
            preparedStatement.setInt(3, widget.getWidth());
            preparedStatement.setInt(4, widget.getHeight());
            preparedStatement.setString(5, widget.getCssClass());
            preparedStatement.setString(6, widget.getCssStyle());
            preparedStatement.setString(7, widget.getText());
            preparedStatement.setInt(8, widget.getOrder());
            preparedStatement.setInt(9, widget.getSize());
            preparedStatement.setString(10, widget.getHtml());
            preparedStatement.setString(11, widget.getSrc());
            preparedStatement.setString(12, widget.getUrl());
            preparedStatement.setBoolean(13, widget.isShareable());
            preparedStatement.setBoolean(14, widget.isExpandable());
            preparedStatement.setString(15,widget.getDtype().toString());
            preparedStatement.setInt(16, widgetId);

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

    @Override
    public int deleteWidget(int widgetId) {
        try {
            conn = Connection.getConnection();
            preparedStatement = conn.prepareStatement(DELETE_WIDGET_BY_ID);
            preparedStatement.setInt(1,widgetId);
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
