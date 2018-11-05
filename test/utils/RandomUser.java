/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import business.User;
import business.SecurityQuestion;
import java.sql.Date;
import java.util.Random;
import java.time.LocalDate;

/**
 *
 * @author jdodso227
 */
public class RandomUser {
    private final static String ALPHAS = "abcdefghijklmnopqrstuvwxyz";
    private final static String NUMS = "0123456789";
    private final static int STRING_LENGTH = 8;
    
    private RandomUser() {
    }

    public static User generate() {
        User user = new User();
        Random r = new Random();

        user.setFullName(randomString());
        user.setUsername(randomString());
        user.setPassword(randomPassword());
        user.setEmail(randomString() + "@test.com");
        user.setBirthdate(randomDate());
        user.setQuestionNo(SecurityQuestion.values()[r.nextInt(SecurityQuestion.values().length)].getQuestionNo());
        user.setAnswer(randomString());
        
        return user;
    }

    private static String randomString() {
        Random r = new Random();
        StringBuilder s = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; ++i) {
            s.append(ALPHAS.charAt(r.nextInt(ALPHAS.length())));
        }

        return s.toString();
    }

    private static String randomPassword() {
        Random r = new Random();
        StringBuilder s = new StringBuilder(STRING_LENGTH);
        
        // meet password requirements of >8, 1 upper, 1 number
        s.append(randomString());
        s.append(ALPHAS.toUpperCase().charAt(r.nextInt(ALPHAS.length())));
        s.append(NUMS.charAt(r.nextInt(NUMS.length())));
        
        return s.toString();
    }
    
    private static LocalDate randomDate() {
        Random r = new Random();
        
        int year = (r.nextInt(100)) + 1900;
        int month = (r.nextInt(12)) + 1;
        int day = (r.nextInt(28)) + 1;
        
        return LocalDate.of(year, month, day);
    }
}
