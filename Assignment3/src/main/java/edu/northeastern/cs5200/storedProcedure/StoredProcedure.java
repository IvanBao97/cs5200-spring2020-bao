package edu.northeastern.cs5200.storedProcedure;

import edu.northeastern.cs5200.Connection;

import java.sql.*;

public class StoredProcedure {

    java.sql.Connection connection;
    CallableStatement callableStatement;
    ResultSet resultSet;

    public void getUnansweredQuestions(){
        /*  Please run following sql in database before run JDBC

            DROP PROCEDURE IF EXISTS getUnansweredQuestions;
            DELIMITER //
            CREATE PROCEDURE getUnansweredQuestions()
            BEGIN
	        SELECT q.`id`, q.`text`, MAX(`ca`.`count`)
            FROM `question` q, `answer` a, (SELECT  COUNT(a.`id`) AS `count` FROM `question` q, `answer` a, WHERE a.`question's_id`=q.`id` AND a.`correct_answer` = FALSE) AS `ca`
            GROUP BY q.`module`;
            END //
            DELIMITER ;
        */
        try {
            connection = Connection.getConnection();
            String proStr = "{call getUnansweredQuestions}";
            callableStatement = connection.prepareCall(proStr);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                System.out.println("Result:" +
                        resultSet.getInt("id") + "__" +
                        resultSet.getString("text")+ "__" +
                        resultSet.getInt("number of answer"));
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null){
                Connection.closeConnection();
            }
        }
    }

    public void  endorsedUsersForWeek(Date week_begin, Date week_end){
        /*  Please run following sql in database before run JDBC

            DROP PROCEDURE IF EXISTS endorsedUsersForWeek;
            DELIMITER //
            CREATE PROCEDURE endorsedUsersForWeek(IN week_begin DATE, IN week_end DATE)
            BEGIN
	        SELECT p.`first_name` as `First Name`, p.`last_name` as `Last Name`, COUNT(a.`correct_answer`) AS `number of correct answer`
            FROM `person` p, `answer` a, `user` u
            WHERE a.`posted_on` > week_begin AND a.`posted_on` < week_end
		        AND p.`id` = u.`id` AND p.`id` = a.`posted_by_user_id`
                AND a.`correct_answer` = TRUE
	        GROUP BY(p.`first_name`)
            LIMIT 5;
            END //
            DELIMITER ;
         */
        try {
            connection = Connection.getConnection();
            String procStr = "{call endorsedUsersForWeek(?,?)}";
            callableStatement = connection.prepareCall(procStr);
            callableStatement.setDate(1, week_begin);
            callableStatement.setDate(2, week_end);
            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            while (resultSet.next()) {
                System.out.println("Result:" +
                        resultSet.getString("First Name") + "__" +
                        resultSet.getString("Last Name")+ "__" +
                        resultSet.getInt("number of correct answer"));
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(connection != null){
                Connection.closeConnection();
            }
        }
    }
}
