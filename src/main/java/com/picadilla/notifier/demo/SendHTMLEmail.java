package com.picadilla.notifier.demo;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendHTMLEmail {
    public static void main(String [] args)
    {

        // Recipient's email ID needs to be mentioned.
        String [] recipients = {"cardano.p@gmail.com", "jsienniak@gmail.com"};
        InternetAddress [] to = new InternetAddress[0];
        try {
            to = new InternetAddress[]{new InternetAddress(recipients[0]), new InternetAddress(recipients[1])};
        } catch (AddressException e) {
            e.printStackTrace();
        }

        // Sender's email ID needs to be mentioned
        String from = "jsienniak@gmail.com";

        // Assuming you are sending email from localhost
        String host = "outmail.f2s.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.starttls.enable", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, to);

            // Set Subject: header field
            message.setSubject("Uszanowanko");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Pozdro od naszych ruskich pszyjacieli</h1>", "text/html" );

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
