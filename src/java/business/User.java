/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 *
 * @javabean for User Entity
 */
public final class User implements Serializable {
    //define attributes fullname, ...

    //define set/get methods for all attributes.
    private PublicUserInfo publicUserInfo;
    private String email;
    private String password;
    private LocalDate birthdate;
    private int questionNo;
    private String answer;
    private LocalDateTime lastLogin;

    public User() {
        publicUserInfo = new PublicUserInfo();
        email = "";
        password = "";
        birthdate = null;
        questionNo = -1;
        answer = "";
    }

    public User(String fullName, String email, String username, String password,
            LocalDate birthdate, int questionNo, String answer, LocalDateTime login) {
        this.publicUserInfo = new PublicUserInfo(fullName, username);
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.questionNo = questionNo;
        this.answer = answer;
        this.lastLogin = login;
    }

    public User(String fromString) throws NumberFormatException, ParseException {
        String[] data = fromString.replace("[", "").replace("]","").split(",");
        this.publicUserInfo = new PublicUserInfo(data[0], data[2]);
        this.setEmail(data[1]);
        this.setPassword(data[3]);
        this.setBirthdate(data[4]);
        this.setQuestionNo(data[5]);
        this.setAnswer(data[6]);
        DateTimeFormatter formatterNew = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        this.lastLogin = LocalDateTime.parse(data[7], formatterNew);

    }

    public PublicUserInfo getPublicUserInfo() {
        return this.publicUserInfo;
    }

    public String getFullName() {
        return this.publicUserInfo.fullName;
    }

    public void setFullName(String fullName) {
        this.publicUserInfo.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.publicUserInfo.username;
    }

    public void setUsername(String username) {
        this.publicUserInfo.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setBirthdate(String birthdate) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        this.birthdate = LocalDate.parse(birthdate, formatter);
    }

    public int getQuestionNo() {
        return this.questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = Integer.parseInt(questionNo);
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(String login) {
        this.lastLogin = LocalDateTime.parse(login);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s,%s,%s,%s,%s,%s,%s,%s]",
                this.getFullName(), this.getEmail(), this.getUsername(),
                this.getPassword(), this.getBirthdate(), this.getQuestionNo(),
                this.getAnswer(), this.getLastLogin().toString()));
        return sb.toString();
    }

    public boolean equals(User user) {
        return this.publicUserInfo.fullName.equals(user.getFullName()) && email.equals(user.getEmail())
                && this.publicUserInfo.username.equals(user.getUsername()) && password.equals(user.getPassword())
                && birthdate.equals(user.getBirthdate()) && questionNo == user.questionNo
                && answer.equals(user.getAnswer());
    }
}
