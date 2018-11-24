package util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import business.User;
import dataaccess.UserUtil;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Emailer {

    private final static String SERVER_USERNAME = "joesoetwit@gmail.com";
    private final static String SERVER_PASSWORD = "twitterserver";

    private static void sendEmail(String to,
            String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVER_USERNAME, SERVER_PASSWORD);
            }
        });
        session.setDebug(true);

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // 3 - address the message
        Address fromAddress = new InternetAddress(SERVER_USERNAME);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
        message.setText(body);

        // 4 - send the message
        Transport.send(message);
    }

    public static void sendForgotPasswordEmail(User user) throws MessagingException {
        String password = PasswordGenerator.generateRandom();

        String subject = "Forgot Password";
        StringBuilder body = new StringBuilder();
        try
        {
        UserUtil.updatePassword(user, password);
        }
        catch (IOException|ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        body.append(user.getFullName() + ",\n\n");
        body.append("Here is your updated password: " + password + "\n\n");
        body.append("We recommend changing this next time you log in.\n\n\n");
        body.append("Best,\n\n");
        body.append("The Twits");
        
        sendEmail(user.getEmail(), subject, body.toString(), false);
    }
}
