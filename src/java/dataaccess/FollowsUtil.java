/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Follow;
import business.Notifications;
import business.Twit;
import business.User;
import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Soeren
 */
public class FollowsUtil {

    public static int insertFollow(User user, String userFollow) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("INSERT INTO follows(userId, followedId) ");
            preparedSQL.append("VALUES ((SELECT userId FROM user WHERE username = ?),(SELECT userId FROM user WHERE username = ?));");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setString(2, userFollow);

            ps.executeUpdate();

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }
    
    //stop following
    public static int stopFollowing(User user, String userFollow) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("DELETE FROM follows ");
            preparedSQL.append("WHERE follows.userId =(SELECT user.userId FROM user WHERE user.username = ?) AND follows.followedId = (SELECT user.userId FROM user WHERE user.username = ?);");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setString(2, userFollow);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    //how many people you are following
    public static int getFollowing(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("SELECT COUNT(*) as following ");
            preparedSQL.append("FROM follows ");
            preparedSQL.append("WHERE follows.userId = (SELECT user.userID FROM user WHERE user.username = ?); ");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return result.getInt("following");
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    //how many people are following you
    public static int getFollowed(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("SELECT COUNT(*) as following ");
            preparedSQL.append("FROM follows ");
            preparedSQL.append("WHERE follows.followedId = (SELECT user.userID FROM user WHERE user.username = ?); ");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return result.getInt("following");
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList<Follow> getAllfollowing(User user) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("Select user.fullname ,user.username,  IF(follows.followedId is NULL, FALSE, TRUE) as following from User ");
            preparedSQL.append("LEFT JOIN follows on(user.userID = follows.followedId  AND follows.userID =(Select user.userID from user WHERE username = ?)) WHERE user.userID !=(Select user.userID from user WHERE username = ?);");
            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUsername());
            ResultSet results = ps.executeQuery();

            ArrayList<Follow> allUsers = new ArrayList<>();
            while (results.next()) {
                allUsers.add(new Follow(results.getString("fullName"), results.getString("username"), results.getString("following")));
            }

            return allUsers;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }
        return null;
    }
}
