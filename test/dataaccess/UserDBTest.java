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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static dataaccess.UserDB.insert;

/**
 *
 * @author jdodso227
 */
public class UserDBTest {

    User user;
    static Connection connection;
    
    @BeforeClass
    public static void setUpClass() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/twitterdb?serverTimezone=America/Denver";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(dbURL, username, password);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        connection.close();
    }

    /**
     * Test of insert method, of class UserDB.
     */
    @Test
    public void testInsert() throws SQLException, IOException, ClassNotFoundException {

        PreparedStatement ps;
        
        // clear table and verify empty before test
        ps = connection.prepareStatement("TRUNCATE TABLE user_test");
        ps.executeQuery();
        
        ps = connection.prepareStatement("SELECT * from user_test");
        assertNull(ps.executeQuery());
        
        // insert user and verify user in database
        insert(user);

        ps = connection.prepareStatement("SELECT * from user_test");
        ResultSet result = ps.executeQuery();
        
        result.next();
        User resultUser = new User(result.getString("fullName"), result.getString("email"),
                        result.getString("username"), result.getString("password"),
                        result.getDate("birthdate"), result.getInt("questionNo"),
                        result.getString("answer"));

        assertEquals(user, resultUser);
        assertFalse(result.next());
    }

    /**
     * Test of searchByEmail method, of class UserDB.
     */
    @Test
    public void testSearchByEmail() throws Exception {
        System.out.println("searchByEmail");
        String email = "";
        User expResult = null;
        User result = UserDB.searchByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchByUsername method, of class UserDB.
     */
    @Test
    public void testSearchByUsername() throws Exception {
        System.out.println("searchByUsername");
        String username = "";
        User expResult = null;
        User result = UserDB.searchByUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
