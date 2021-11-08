package by.hardzeyeu.libraryV2.services;

import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.dto.NotificationLists;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;

import java.util.HashMap;
import java.util.List;

public interface BorrowService {

    List<Borrow> getListOfBorrows(int bookId);


    void addBorrow(int bookId, String userName, String userEmail, int timePeriod, String comment);


    BookBorrowsInfo getBookBorrowsInfo(Book book);

    void changeBorrowStatusSetReturnDate(String status, int borrowId);

    List<Borrow> getBorrowsToNotifyToday();
}
