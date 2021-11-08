package by.hardzeyeu.libraryV2.notifications;

import by.hardzeyeu.libraryV2.models.Borrow;
import org.stringtemplate.v4.ST;


public class MessageTemplate {

    static ST weekMail = new ST("Hello, dear <user_name>! You should return <title> in a week - <dueDate>! Goodbye!" +
            " Contact us via lesnoylibrarybot@gmail.com");

    static ST tomorrowMail = new ST("Hello, dear <user_name>! You should return <title> tomorrow - <dueDate>! Goodbye! " +
            "Contact us via lesnoylibrarybot@gmail.com");

    static ST yesterdayMail = new ST("Hello, dear <user_name>! You should have returned <title> yesterday - <dueDate>! " +
            "We`re calling the police! Goodbye!");


    public static String returnCompletedNotificationWeek (Borrow borrow) {
        return returnEmailStInAccWithPeriod(weekMail, borrow);
    }


    public static String returnCompletedNotificationTomorrow (Borrow borrow) {
        return returnEmailStInAccWithPeriod(tomorrowMail, borrow);
    }


    public static String returnCompletedNotificationYesterday (Borrow borrow) {
        return returnEmailStInAccWithPeriod(yesterdayMail, borrow);
    }


    /**
     * Builds string message from ST template
     *
     * @param text
     * @param borrow
     * @return String message, which will be sent
     */

    private static String returnEmailStInAccWithPeriod(ST text, Borrow borrow) {
        text.add("user_name", borrow.getUserName());
        text.add("title", borrow.getTitle());
        text.add("dueDate", borrow.getDueDate());

        return text.render();
    }
}



