/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Random;

/**
 * Singleton class to generate random alpha-numeric passwords
 * @author jdodso227
 */
public class PasswordGenerator {
    private final static int PASSWORD_LENGTH = 8;
    private final static String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private PasswordGenerator() {}
    
    static String generateRandom() {
        Random r = new Random();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; ++i) {
            password.append(SYMBOLS.charAt(r.nextInt(SYMBOLS.length())));
        }
        
        return password.toString();
    }
}
