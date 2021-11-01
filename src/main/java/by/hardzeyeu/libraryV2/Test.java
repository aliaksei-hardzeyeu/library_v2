package by.hardzeyeu.libraryV2;

import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.implementations.BookServicesImpl;
import by.hardzeyeu.libraryV2.services.implementations.BorrowServicesImpl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.TreeSet;

public class Test {

    public static void main(String[] args) {
        BorrowService borrowService = BorrowServicesImpl.getInstance();
        BookService bookService = BookServicesImpl.getInstance();

        Book book = bookService.getBook(35);
        BookBorrowsInfo bookBorrowsInfo = borrowService.getBookBorrowsInfo(book);
        System.out.println(bookBorrowsInfo.toString());

    }
}