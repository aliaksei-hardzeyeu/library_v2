package by.hardzeyeu.libraryV2.services.impl;

import by.hardzeyeu.libraryV2.dao.BorrowDAO;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.notifications.MessageTemplate;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.utils.Utils;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
        try {
            borrowDAO.addBorrow(bookId, userName, userEmail, borrowDateSql, timePeriod, comment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void changeBorrowStatusSetReturnDate(String status, int borrowId) {
        try {
            borrowDAO.changeBorrowStatusSetReturnDate(status, borrowId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Borrow> getListOfBorrows(int bookId) {
        List<Borrow> listOfBorrows = null;
        try {
            listOfBorrows = borrowDAO.getListOfBorrows(bookId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (Borrow borrow : listOfBorrows) {
            borrow.setDueDate(countDueDate(borrow));
        }

        return listOfBorrows;
    }


    /**
     * Counts due date -> borrow date + period
     *
     * @param borrow
     * @return LocalDate due date
     */

    public LocalDate countDueDate(Borrow borrow) {
        return borrow.getBorrowDate().plus(Period.ofMonths(borrow.getTimePeriod()));
    }


    public BookBorrowsInfo getBookBorrowsInfo(Book book) {
        try {
            return borrowDAO.getBookBorrowsInfo(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Gets borrows to notify today and places them in accordance with dates to 3 different lists
     *
     * @return object containing these lists
     */

    public List<Borrow> getBorrowsToNotifyToday() {

        List<Borrow> borrowsToReturnInWeek = null;
        List<Borrow> borrowsToReturnTomorrow = null;
        List<Borrow> borrowsToReturnYesterday = null;

        try {
            borrowsToReturnInWeek = borrowDAO.getBorrowsToReturnInWeek();
            borrowsToReturnTomorrow = borrowDAO.getBorrowsToReturnTomorrow();
            borrowsToReturnYesterday = borrowDAO.getBorrowsToReturnYesterday();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Borrow borrow : borrowsToReturnInWeek) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationWeek(borrow));
        }

        for (Borrow borrow : borrowsToReturnTomorrow) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationTomorrow(borrow));
        }

        for (Borrow borrow : borrowsToReturnYesterday) {
            borrow.setNotification(MessageTemplate.returnCompletedNotificationYesterday(borrow));
        }

        List<Borrow> allNotificationsForToday = new ArrayList<>(borrowsToReturnInWeek);
        allNotificationsForToday.addAll(borrowsToReturnTomorrow);
        allNotificationsForToday.addAll(borrowsToReturnYesterday);

        return allNotificationsForToday;
    }


}
