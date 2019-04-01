package Tikit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class Utilities {

    int GenerateRanNum(int max, int min) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        System.out.println("Generated random number: " + randomNum);
        return randomNum;
    }

    public void sendMail(String Email, String Subject, String MessageText) {

        final String username = "tikitresponse@gmail.com";
        final String password = "Movie123.";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tikitresponse@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Email));
            message.setSubject(Subject);
            message.setText(MessageText);

            Transport.send(message);

            System.out.println("Send mail to " + Email + " With Subject: " + Subject);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
