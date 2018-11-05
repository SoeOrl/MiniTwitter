/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.User;
import business.Twit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static dataaccess.DatabaseUtil.*;

/**
 *
 * @author jdodso227
 */
public class DatabaseUtilTests {

    static User user;
    static Connection connection;
    static PreparedStatement ps;
        

    @BeforeClass
    public static void setUpClass() throws ClassNotFoundException, SQLException, ParseException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver;useSSL=false";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(dbURL, username, password);

        user = new User("[Test Tester,test@tester.test,testuser,testpass,1991-01-30,1,Rascal]");

    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.close();
    }
    
    @Before
    public void setUpTest() throws SQLException {
        ps = connection.prepareStatement("SELECT * from user WHERE username = 'testuser'");
        assertFalse(ps.executeQuery().next());
    }
    
    @After
    public void tearDownTest() throws SQLException {
        // clear table and verify empty before test
        ps = connection.prepareStatement("DELETE FROM user WHERE username = 'testuser'");
        ps.executeUpdate();
    }

    /**
     * Test of insert method, of class DatabaseUtil.
     */
    @Test
    public void testInsertUser() throws SQLException, IOException, ClassNotFoundException {

        // insert user and verify user in database
        insertUser(user);
        
        ps = connection.prepareStatement("SELECT * from user");
        ResultSet result = ps.executeQuery();

        result.next();
        User resultUser = new User(result.getString("fullName"), result.getString("email"),
                result.getString("username"), result.getString("password"),
                result.getDate("birthdate"), result.getInt("questionNo"),
                result.getString("answer"));

        assertTrue(user.equals(resultUser));
        assertFalse(result.next());
    }

    /**
     * Test of searchByEmail method, of class DatabaseUtil.
     */
    @Test
    public void testSearchByEmail() throws Exception {
        String email = "test@tester.test";
        User expResult = user;
        
        insertUser(user);
        User result = DatabaseUtil.searchByEmail(email);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of searchByUsername method, of class DatabaseUtil.
     */
    @Test
    public void testSearchByUsername() throws Exception {
        String username = "testuser";
        User expResult = user;
        
        insertUser(user);
        User result = DatabaseUtil.searchByUsername(username);
        assertTrue(expResult.equals(result));
    }
    
    /**
     * Test of insertTwit method, of class DatabaseUtil.
     */
    @Test
    public void testInsertTwit() throws Exception {
        user = new User("[Test Tester,test@tester.test,testuser,testpass,1991-01-30,1,Rascal]");

        User originUser = new User("Joe Dodson,jodo@testing.com,jodouser,jodopass,1991-01-01,1,Spot]");
        User mentionedUser = new User("Soeren sdklfjasd,soeren@testing.com,soerenuser,soerenpass,1991-01-02,1,Rufus]");
        
        insertUser(originUser);
        insertUser(mentionedUser);
        
        Twit twit = new Twit(originUser, "Hey there @soerenuser, what's up?");
        insertTwit(originUser, twit);
        
        
    }
}
