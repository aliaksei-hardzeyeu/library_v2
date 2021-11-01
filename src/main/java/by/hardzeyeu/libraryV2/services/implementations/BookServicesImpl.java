package by.hardzeyeu.libraryV2.services.implementations;

import by.hardzeyeu.libraryV2.dao.BookDAO;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BookServicesImpl implements BookService {
    private static BookServicesImpl bookServicesImpl;
    private BookDAO bookDAO;

    private BookServicesImpl() {
        bookDAO = new BookDAO();
    }

    public static BookServicesImpl getInstance() {
        if (bookServicesImpl == null)
            bookServicesImpl = new BookServicesImpl();
        return bookServicesImpl;
    }


    public Book getBook(int bookId) {
        Book book = bookDAO.getBook(bookId);
        BorrowService borrowService = BorrowServicesImpl.getInstance();

        BookBorrowsInfo bookBorrowsInfo = borrowService.getBookBorrowsInfo(book);
        setBookProperties(book, bookBorrowsInfo);


        return book;
    }

    public List<Book> getListOfBooks() {
        BorrowService borrowService = BorrowServicesImpl.getInstance();
        List<Book> listOfBooks = bookDAO.getListOfBooks();

        for (Book book:listOfBooks) {
            BookBorrowsInfo bookBorrowsInfo = borrowService.getBookBorrowsInfo(book);
            setBookProperties(book, bookBorrowsInfo);
        }

        return listOfBooks;
    }

    public void addBook(String title, String publisher, int page_count, String isbn, String des, String publDate,
                        String authors, String genres, int givenAmount) {
        bookDAO.addBook(title, publisher, page_count, isbn, des, publDate, authors, genres, givenAmount);
    }

    public void updateBook(String title, String publisher, int page_count, String isbn, String des, String publDate,
                           String authors, String genres, int amount, int book_id) {
        bookDAO.updateBook(title, publisher, page_count, isbn, des, publDate, authors, genres, amount, book_id);
    }

    public void removeBook(int bookId) {
        bookDAO.removeBook(bookId);
    }


    /**
     * Sets status field in books table in accordance with borrows table
     *
     * @param book
     */

    public void setBookProperties (Book book, BookBorrowsInfo bookBorrowsInfo) {
        int damaged = bookBorrowsInfo.getDamaged();
        int lost = bookBorrowsInfo.getLost();
        int returned = bookBorrowsInfo.getReturned();
        int borrowed = bookBorrowsInfo.getBorrowed();

        List<LocalDate> dueDates = bookBorrowsInfo.getDueDatesWithoutStatus();
        Collections.sort(dueDates);

        System.out.println("damaged = " + damaged + " lost = " + lost + " returned = " + returned + " borrowed = " + borrowed);

        book.setRealAmount(book.getGivenAmount() - damaged - lost);
        book.setCurrentlyAvailableAmount(book.getRealAmount() - borrowed);

        System.out.println("damaged = " + damaged + " lost = " + lost + " returned = " + returned + " borrowed = " + borrowed);

        if (book.getCurrentlyAvailableAmount() == 0) {
            book.setStatus("UNAVAILABLE closest return date - " + dueDates.get(0));
        } else {
            book.setStatus("AVAILABLE " + book.getCurrentlyAvailableAmount() + " of " + book.getRealAmount());
        }


    }



}
