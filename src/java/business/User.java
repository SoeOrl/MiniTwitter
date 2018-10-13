/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @javabean for User Entity
 */
public class User implements Serializable {
    //define attributes fullname, ...
    
    //define set/get methods for all attributes.
    private String fullName;
    private String email;
    private String username;
    private String password;
    private Date birthdate;
    private int questionNo;
    private String answer;
    
    public User()
    {
        fullName = "";
        email = "";
    }
    public User(String fromString)
    {
        String[] data = fromString.replace("[", "").split(",");
        this.setFullName(data[0]);
        this.setEmail(data[1]);
    }
    public String getFullName()
    {
        return this.fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
        public String getUsername()
    {
        return this.username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    
        public String getPassword()
    {
        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    
        public Date getBirthdate()
    {
        return this.birthdate;
    }
    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }
    
        public int getquestionNo()
    {
        return this.questionNo;
    }
    public void setquestionNo(int questionNo)
    {
        this.questionNo = questionNo;
    }
    
        public String getAnswer()
    {
        return this.answer;
    }
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(String.format("[%s,%s,%s,%s,%s,%s]", this.getFullName(), this.getEmail(), this.getUsername(),this.getBirthdate(),this.getquestionNo(), this.getPassword(),this.getAnswer()));
      return sb.toString();
    }
    
    
}
