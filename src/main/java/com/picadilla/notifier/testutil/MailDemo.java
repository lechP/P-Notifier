package com.picadilla.notifier.testutil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailDemo {
    Session mailSession;

    public static void main(String args[]) throws MessagingException {
        MailDemo javaEmail = new MailDemo();
        javaEmail.setMailServerProperties();
        javaEmail.draftEmailMessage();
        javaEmail.sendEmail();
    }

    private void setMailServerProperties() {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        mailSession = Session.getDefaultInstance(emailProperties, null);
    }

    private MimeMessage draftEmailMessage() throws MessagingException {
        String[] toEmails = {"lech.piechota@capgemini.com", "cardano.p@gmail.com"};
        String emailSubject = "Hello gamedev";
        String emailBody = "This is an email sent by <b>awesome game</b>";
        MimeMessage emailMessage = new MimeMessage(mailSession);
        /**
         * Set the mail recipients
         * */
        for (int i = 0; i < toEmails.length; i++) {
            emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
        }
        emailMessage.setSubject(emailSubject);
        /**
         * If sending HTML mail
         * */
        emailMessage.setContent(emailBody, "text/html");

        /**
         * If sending only text mail
         * */
        //emailMessage.setText(emailBody);// for a text email
        return emailMessage;
    }

    private void sendEmail() throws MessagingException {
        /**
         * Sender's credentials
         * */
        String fromUser = "gamedev.main@gmail.com";
        String fromUserEmailPassword = "f34afK#4a+TR";

        String emailHost = "smtp.gmail.com";
        Transport transport = mailSession.getTransport("smtp");
        System.out.println("Will connect with host...");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        /**
         * Draft the message
         * */
        MimeMessage emailMessage = draftEmailMessage();
        /**
         * Send the mail
         * */
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}