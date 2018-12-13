/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.Twit;
import business.User;
import business.Hashtag;
import business.PublicUserInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import static dataaccess.HashtagUtil.*;

/**
 *
 * @author jdodso227
 */
public class TwitUtil {

    public static int insertTwit(User user, Twit twit) throws IOException, ClassNotFoundException {

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(dbURL, username, password);

            // insert twit first
            final StringBuilder preparedSQL = new StringBuilder();
            preparedSQL.append("INSERT INTO twit ");
            preparedSQL.append("VALUES (?, ?, ?, ?);");

            //add values to the above SQL statement and execute it.
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            ps.setString(1, twit.getUuid());
            ps.setString(2, user.getUuid());
            ps.setString(3, twit.getTwit());
            ps.setTimestamp(4, Timestamp.valueOf(twit.getPostedDateTime()));

            if (ps.executeUpdate() == 1) {
                int mentionResult = insertMention(user, twit, connection);
                int hashtagResult = insertTwitHashtags(twit);

                if (mentionResult == twit.getMentionedUsernames().size()
                        && hashtagResult == twit.getHashtags().size()) {
                    return 1;
                } else {
                    //user mention insert failed, delete twit
                    preparedSQL.setLength(0);
                    preparedSQL.append("DELETE FROM twit WHERE twitId = ?");

                    ps = connection.prepareStatement(preparedSQL.toString());
                    ps.setObject(1, twit.getUuid());
                    ps.executeUpdate();
                    ps.close();

                    return 0;
                }
            } else {
                return 0;
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
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

    private static int insertMention(User user, Twit twit, Connection connection) throws IOException, ClassNotFoundException, SQLException {
        final StringBuilder preparedSQL = new StringBuilder();

        // insert user mentions if any
        ArrayList<String> mentionedUsernames = twit.getMentionedUsernames();
        if (mentionedUsernames != null && mentionedUsernames.size() > 0) {
            preparedSQL.append("INSERT INTO userMention(mentionId, originUserId, mentionedUserId, twitId) VALUES ");

            for (int i = 0; i < mentionedUsernames.size(); ++i) {
                preparedSQL.append(String.format("('%s', (SELECT userId FROM user WHERE username = '%s'), "
                        + "(SELECT userId FROM user WHERE username = '%s'), '%s')",
                        UUID.randomUUID().toString(), user.getUsername(),
                        mentionedUsernames.get(i), twit.getUuid().toString()));

                if (i != mentionedUsernames.size() - 1) {
                    preparedSQL.append(", ");
                } else {
                    preparedSQL.append(";");
                }
            }
            PreparedStatement ps = connection.prepareStatement(preparedSQL.toString());
            int result = ps.executeUpdate();
            ps.close();
            connection.close();

            return result;
        }

        return 0;
    }

    public static ArrayList<Twit> getTwitsForUsername(String username) throws IOException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT DISTINCT originUsername, originFullname, named_twits.twitId AS twitId, twit, postedDateTime FROM "
                    + "(SELECT twitid, twit, twit.userid, username AS originUsername, fullname AS originFullname, postedDateTime "
                    + "    FROM twit "
                    + "    JOIN user ON twit.userid = user.userid) AS named_twits "
                    + "LEFT join "
                    + "    (SELECT username AS mentionedUsername, twitid FROM usermention "
                    + "     JOIN user ON usermention.mentionedUserId = user.userid) AS named_mentions "
                    + "ON named_twits.twitid = named_mentions.twitid "
                    + "WHERE originUsername = ? "
                    + "    OR mentionedUsername = ?"
                    + "    OR named_twits.userid IN "
                    + "         (SELECT followedId "
                    + "         FROM follows "
                    + "         WHERE userId = "
                    + "             (SELECT userId "
                    + "             FROM user"
                    + "             WHERE username = ?)) "
                    + "ORDER BY postedDateTime DESC";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, username);

            ResultSet results = ps.executeQuery();

            ArrayList<Twit> twits = new ArrayList<>();
            while (results.next()) {
                twits.add(new Twit(results.getString("originUsername"),
                        results.getString("originFullname"),
                        results.getString("twit"),
                        results.getString("twitId"),
                        results.getTimestamp("postedDateTime").toLocalDateTime()));
            }

            return twits;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }

            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static int getNumUserTwits(User user) throws IOException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "SELECT COUNT(*) as numTwits "
                    + "FROM twit "
                    + "JOIN user ON user.userid = twit.userid "
                    + "WHERE user.username = ?";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, user.getUsername());

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                return result.getInt("numTwits");
            }

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
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

    public static int deleteTwit(User user, String twitId, String twitText) throws IOException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
            String dbusername = "root";
            String dbpassword = "root";
            connection = DriverManager.getConnection(dbURL, dbusername, dbpassword);

            String query
                    = "DELETE FROM twit "
                    + "WHERE twitId = ? AND "
                    + "    userId = (SELECT userId FROM user WHERE username = ?)";

            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            ps.setString(1, twitId);
            ps.setString(2, user.getUsername());

            int deletedRows = ps.executeUpdate();
            if (deletedRows == 1) {
                Twit twit = new Twit();
                twit.setTwit(twitText);
                ArrayList<Hashtag> hashtags = twit.getHashtags();

                for (int i = 0; i < hashtags.size(); ++i) {
                    updateHashtagTimesUsed(hashtags.get(i), connection, CountUpdateMode.DECREMENT);
                }
            }

            return deletedRows;

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
    }

    public static ArrayList<Twit> findAllTwits(String searchText) throws IOException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver&useSSL=false";
            String username = "root";
            String password = "root";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            String query
                    = "SELECT username, fullName, twit "
                    + "FROM twit "
                    + "JOIN user "
                    + " ON twit.userId = user.userId "
                    + "WHERE twit like ? ";
            
            PreparedStatement ps = connection.prepareStatement(query);

            //add value to the above SQL statement and execute it.
            searchText = "%" + searchText + "%";
            ps.setString(1, searchText);

            ResultSet results = ps.executeQuery();

            ArrayList<Twit> twits = new ArrayList<>();
            while (results.next()) {
                twits.add(new Twit(results.getString("username"),
                        results.getString("fullName"),
                        results.getString("twit")));           
            }

            return twits;

        } catch (SQLException e) {
            for (Throwable t : e) {
                t.printStackTrace();
            }
        }

        return null;
    }
}
