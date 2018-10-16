/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.User;
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
import static dataaccess.UserDB.insert;

/**
 *
 * @author jdodso227
 */
public class UserDBTest {

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
     * Test of insert method, of class UserDB.
     */
    @Test
    public void testInsert() throws SQLException, IOException, ClassNotFoundException {

        // insert user and verify user in database
        insert(user);
        
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
     * Test of searchByEmail method, of class UserDB.
     */
    @Test
    public void testSearchByEmail() throws Exception {
        String email = "test@tester.test";
        User expResult = user;
        
        insert(user);
        User result = UserDB.searchByEmail(email);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of searchByUsername method, of class UserDB.
     */
    @Test
    public void testSearchByUsername() throws Exception {
        String username = "testuser";
        User expResult = user;
        
        insert(user);
        User result = UserDB.searchByUsername(username);
        assertTrue(expResult.equals(result));
    }
}
