package by.hardzeyeu.libraryV2.services.impl;

import by.hardzeyeu.libraryV2.dao.BorrowDAO;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.dto.NotificationLists;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.notifications.MessageTemplate;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.Utils;
import org.apache.commons.collections4.MultiValuedMap;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class BorrowServicesImpl implements BorrowService {
    private static BorrowServicesImpl borrowServicesImpl;
    private BorrowDAO borrowDAO;


    private BorrowServicesImpl() {
        borrowDAO = new BorrowDAO();
    }

    public static BorrowServicesImpl getInstance() {
        if (borrowServicesImpl == null)
            borrowServicesImpl = new BorrowServicesImpl();
        return borrowServicesImpl;
    }


    public void addBorrow(int bookId, String userName, String userEmail, int timePeriod, String comment) {
        Date borrowDateSql = Utils.convertToSqlDateFromLocalDate(LocalDate.now());
            borrowDAO.addBorrow(bookId, userName, userEmail, borrowDateSql, timePeriod, comment);
    }

    public void changeBorrowStatusSetReturnDate(String status, int borrowId) {

        borrowDAO.changeBorrowStatusSetReturnDate(status, borrowId);
    }

    public List<Borrow> getListOfBorrows(int bookId) {
        List<Borrow> listOfBorrows = borrowDAO.getListOfBorrows(bookId);

        for (Borrow borrow: listOfBorrows) {
            borrow.setDueDate(countDueDate(borrow));
        }

        return listOfBorrows;
    }

    /**
     * Counts due date -> borrow date + period
     * @param borrow
     * @return LocalDate due date
     */

    public LocalDate countDueDate(Borrow borrow) {
        return borrow.getBorrowDate().plus(Period.ofMonths(borrow.getTimePeriod()));
    }

    public LocalDate countReturnDate(Borrow borrow) {
        return borrow.getChangedStatusDate();
    }


    public BookBorrowsInfo getBookBorrowsInfo(Book book) {
        return borrowDAO.getBookBorrowsInfo(book);
    }


    /**
     * Gets borrows to notify today and places them in accordance with dates to 3 different lists
     *
     * @return object containing these lists
     */

    public List<Borrow> getBorrowsToNotifyToday() {

        List<Borrow> borrowsToReturnInWeek = borrowDAO.getBorrowsToReturnInWeek();
        List<Borrow> borrowsToReturnTomorrow = borrowDAO.getBorrowsToReturnTomorrow();
        List<Borrow> borrowsToReturnYesterday = borrowDAO.getBorrowsToReturnYesterday();


        for (Borrow borrow:borrowsToReturnInWeek) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationWeek(borrow));
        }

        for (Borrow borrow:borrowsToReturnTomorrow) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationTomorrow(borrow));
        }

        for (Borrow borrow:borrowsToReturnYesterday) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationYesterday(borrow));
        }

        List<Borrow> allNotificationsForToday = new ArrayList<>(borrowsToReturnInWeek);
        allNotificationsForToday.addAll(borrowsToReturnTomorrow);
        allNotificationsForToday.addAll(borrowsToReturnYesterday);

        return allNotificationsForToday;
    }


}
