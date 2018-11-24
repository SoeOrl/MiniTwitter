/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Soeren
 */
public class Follow {
    public String fullName;
    public String username;
    public String following;
    
    public Follow() {
        this.fullName = "";
        this.username = "";
        this.following = "";
    }
    
    public Follow(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }
        
    public Follow(String fullName, String username, String following) {
        this.fullName = fullName;
        this.username = username;
        this.following = following;
    } 
    
    public String getFullName()
    {
        return this.fullName;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    public String getFollowing()
    {
        return this.following;
    }
}
