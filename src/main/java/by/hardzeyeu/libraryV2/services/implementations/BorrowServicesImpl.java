package by.hardzeyeu.libraryV2.services.implementations;

import by.hardzeyeu.libraryV2.dao.BorrowDAO;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
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


}
