/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author jdodso227
 */
public class PublicUserInfo {
    public String fullName;
    public String username;
    
    public PublicUserInfo() {
        this.fullName = "";
        this.username = "";
    }
    
    public PublicUserInfo(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    } 
}
