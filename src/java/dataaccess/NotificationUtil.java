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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Soeren
 */
public class NotificationUtil {

    public static ArrayList<Notifications> getNotifications(User user) throws IOException, ClassNotFoundException {
        ArrayList<Notifications> notifications = new ArrayList<>();
        getNewTweets(user, notifications);
        getNewFollows(user, notifications);
        Collections.sort(notifications);
        
        return notifications;
    }

    public static int getNewTweets(User user, ArrayList<Notifications> notifications) throws IOException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("SELECT * FROM usermention ");
            preparedSQL.append("INNER JOIN twit on (twit.twitId = usermention.twitId) ");
            preparedSQL.append("INNER JOIN user on (usermention.originUserId = user.userID) ");
            preparedSQL.append("WHERE (usermention.mentionedUserId=(Select user.userID from user where user.username = ?) AND twit.postedDateTime > ?);");
            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setTimestamp(2, Timestamp.valueOf(user.getLastLogin()));

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                notifications.add(new Notifications(new Twit(result.getString("username"), result.getString("fullname"),
                        result.getString("twit"), result.getTimestamp("postedDateTime").toLocalDateTime())));
            }
        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }

            return 1;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return 0;
    }

    public static int getNewFollows(User user, ArrayList<Notifications> notifications) throws IOException, ClassNotFoundException {
         Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("SELECT * FROM follows  ");
            preparedSQL.append("INNER JOIN (Select user.fullname, user.username, user.userID From user) s on (follows.userId = s.userID) ");
            preparedSQL.append("INNER JOIN (Select user.lastLogin, user.userID as ID From user) l on (follows.followedId=l.ID) ");
            preparedSQL.append("WHERE follows.followedId=(Select user.userID from user where user.username = ?) AND follows.followDate > ?;");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, user.getUsername());
            ps.setTimestamp(2, Timestamp.valueOf(user.getLastLogin()));
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                notifications.add(new Notifications(new Follow(result.getString("fullname"), result.getString("username")
                       ),result.getTimestamp("followDate").toLocalDateTime() ));
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }

            return 1;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return 0;
    }

}
