package by.hardzeyeu.libraryV2.services.impl;

import by.hardzeyeu.libraryV2.dao.BookDAO;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        setBookStatusProperties(book, bookBorrowsInfo);
        setBookBorrowStatusAndAmounts(book);


        return book;
    }


    public List<Book> getListOfBooks() {
        BorrowService borrowService = BorrowServicesImpl.getInstance();
        List<Book> listOfBooks = bookDAO.getListOfBooks();

        for (Book book : listOfBooks) {
            BookBorrowsInfo bookBorrowsInfo = borrowService.getBookBorrowsInfo(book);
            setBookStatusProperties(book, bookBorrowsInfo);
            setBookBorrowStatusAndAmounts(book);
        }

        return listOfBooks;
    }


    /**
     * Searchs for books with given parameters
     *
     * @param searchedBook - book model with searched parameters
     * @return
     */

    public List<Book> getListOfBooks(Book searchedBook) {
        BorrowService borrowService = BorrowServicesImpl.getInstance();
        List<Book> listOfBooks;

        String title = searchedBook.getTitle();
        String authors = searchedBook.getAuthors();
        String genres = searchedBook.getGenres();
        String des = searchedBook.getDes();

        if (title.equals("") && authors.equals("") && genres.equals("") && des.equals("")) {
            listOfBooks = getListOfBooks();

            return listOfBooks;

        } else {

            HashMap<String, String> searchParameters = new HashMap<>();
            searchParameters.put("title", searchedBook.getTitle());
            searchParameters.put("authors", searchedBook.getAuthors());
            searchParameters.put("genres", searchedBook.getGenres());
            searchParameters.put("des", searchedBook.getDes());


            parseParametersToFitSqlStatement(searchParameters);

            listOfBooks = bookDAO.getListOfBooks(searchParameters);

            for (Book book : listOfBooks) {
                BookBorrowsInfo bookBorrowsInfo = borrowService.getBookBorrowsInfo(book);
                setBookStatusProperties(book, bookBorrowsInfo);
                setBookBorrowStatusAndAmounts(book);
            }
        }

        return listOfBooks;
    }


    public void addBook(Book book) {
        bookDAO.addBook(book);
    }


    public void updateBook(Book book) {
        bookDAO.updateBook(book);
    }


    public void removeBook(int bookId) {
        bookDAO.removeBook(bookId);
    }


    /**
     * Sets status properties fields in book in accordance with borrows table
     *
     * @param book
     */

    public void setBookStatusProperties(Book book, BookBorrowsInfo bookBorrowsInfo) {
        book.setDamaged(bookBorrowsInfo.getDamaged());
        book.setLost(bookBorrowsInfo.getLost());
        book.setReturned(bookBorrowsInfo.getReturned());
        book.setBorrowed(bookBorrowsInfo.getBorrowed());

        book.setDueDatesWithoutStatus(bookBorrowsInfo.getDueDatesWithoutStatus());
    }


    /**
     * Computes status of book: available/unavailable; counts available amount, closest due date if
     * unavailable. List dueDatesWithoutStatus is simple list with LocalDates, so to choose closest
     * we should sort it by natural ordering and get the first element.
     *
     * @param book
     */

    public void setBookBorrowStatusAndAmounts(Book book) {
        Collections.sort(book.getDueDatesWithoutStatus());

        book.setCopiesExistingAmount(book.getGivenAmount() - book.getDamaged() - book.getLost());
        book.setCurrentlyAvailableAmount(book.getCopiesExistingAmount() - book.getBorrowed());

        if (book.getCurrentlyAvailableAmount() == 0 && book.getBorrowed() != 0) {
            book.setStatus("UNAVAILABLE closest return date - " + book.getDueDatesWithoutStatus().get(0));
        } else {
            book.setStatus("AVAILABLE " + book.getCurrentlyAvailableAmount() + " of " + book.getCopiesExistingAmount());
        }
    }


    /**
     * Parses raw parameters from search form to fit sql statement
     *
     * @param searchParameters
     * @return
     */

    HashMap<String, String> parseParametersToFitSqlStatement(HashMap<String, String> searchParameters) {
        for (Map.Entry<String, String> entry : searchParameters.entrySet()) {
            if (entry.getValue().equals("")) {
                entry.setValue("%");
            } else {
                entry.setValue("%" + entry.getValue() + "%");
            }
        }

        return searchParameters;
    }


    public int getBookId(Book book) {
        return bookDAO.getBookId(book);
    }
}
