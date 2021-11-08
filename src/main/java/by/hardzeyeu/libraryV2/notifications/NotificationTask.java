package by.hardzeyeu.libraryV2.notifications;

import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.impl.BorrowServicesImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class NotificationTask implements Runnable{
    List<Borrow> allNotificationsForToday;
    BorrowService borrowService = BorrowServicesImpl.getInstance();

    public NotificationTask() {
        allNotificationsForToday = borrowService.getBorrowsToNotifyToday();
    }


    @Override
    public void run() {
        System.out.println("i`m in run()");
        for (Borrow borrow: allNotificationsForToday) {
            mailTask(borrow);
        }
    }


    private void mailTask(Borrow borrow) {
        System.out.println("in mailTask");
        final String username = "lesnoylibrarybot";
        final String password = "qwerty!!!@@@";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);}
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lesnoylibrarybot@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(borrow.getUserEmail()));

            message.setSubject("Lesnoy library bot is watching you!");
            message.setText(borrow.getNotification());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
