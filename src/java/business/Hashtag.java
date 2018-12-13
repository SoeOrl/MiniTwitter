/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.UUID;
/**
 *
 * @author jdodso227
 */
public class Hashtag {
    private UUID uuid;
    private String text;
    
    public Hashtag(String text) {
        this.uuid = UUID.randomUUID();
        this.text = text;
    }
    
    public String getUuid() {
        return uuid.toString();
    }
    
    public String getText() {
        return this.text;
    }
}
