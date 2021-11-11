package by.hardzeyeu.libraryV2.dao;

import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;


@Testcontainers
class DaoTest {
    private static final String MYSQL_LATEST_IMAGE = "mysql:latest";
    private static Connection connection;
    private static BookDAO bookDAO;
    private static BorrowDAO borrowDAO;


    @Container
    private static JdbcDatabaseContainer mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE)
            .withInitScript("initDB.sql");


    @BeforeAll
    static void init() throws SQLException {
        connection = DriverManager.getConnection(mysql.getJdbcUrl(), mysql.getUsername(), mysql.getPassword());
        bookDAO = new BookDAO(connection);
        borrowDAO = new BorrowDAO(connection);
    }


    @BeforeEach
    public void refreshData() throws SQLException {
        Statement statement = connection.createStatement();
        statement.addBatch("DELETE FROM books;");
        statement.addBatch("ALTER TABLE books AUTO_INCREMENT = 1;");

        statement.addBatch(DbTestSources.populateBooks);

        statement.addBatch(DbTestSources.populateBorrows);
        statement.executeBatch();

    }



    @Test
    void getBook_ByExistingId_ReturnsBook() throws SQLException {
        Assertions.assertEquals(DbTestSources.getBookByIdNumberOne(), bookDAO.getBook(1));
    }



    @Test
    void getBook_ByNOTExistingId_ReturnsSqlException() {
        Assertions.assertThrows(Exception.class, () -> bookDAO.getBook(666));
    }



    @Test
    void getListOfBooks_ReturnsListOfAllExistingBooks() throws SQLException {
        Assertions.assertEquals(DbTestSources.getListOfAllBooks(), bookDAO.getListOfBooks());
    }



    @Test
    void getListOfBooks_ReturnsListOfSearchedBooks_ByAuthor() throws SQLException {
        Book searchedBook = new Book("", "thor2", "", "");
        HashMap<String, String> searchParameters = new HashMap<>();
        searchParameters.put("title", searchedBook.getTitle());
        searchParameters.put("authors", searchedBook.getAuthors());
        searchParameters.put("genres", searchedBook.getGenres());
        searchParameters.put("des", searchedBook.getDes());

        Utils.parseParametersToFitSqlStatement(searchParameters);

        Assertions.assertEquals(DbTestSources.getListOfSearchedBooks(), bookDAO.getListOfBooks(searchParameters));
    }



    @Test
    void getListOfBooks_SearchParametersAreEmpty_ReturnsListOfAllExistingBooks() throws SQLException {
        Book searchedBook = new Book("", "", "", "");
        HashMap<String, String> searchParameters = new HashMap<>();
        searchParameters.put("title", searchedBook.getTitle());
        searchParameters.put("authors", searchedBook.getAuthors());
        searchParameters.put("genres", searchedBook.getGenres());
        searchParameters.put("des", searchedBook.getDes());

        Utils.parseParametersToFitSqlStatement(searchParameters);

        Assertions.assertEquals(DbTestSources.getListOfAllBooks(), bookDAO.getListOfBooks(searchParameters));
    }



    @Test
    void addBook_ReturnAddedBook() throws SQLException {
        Book book = new Book("title5", "publisher5", 123, "ISBN-13:978-3-16-148410-0", "description5",
                LocalDate.of(2020, 12, 12), "author5", "genre5", 10);
        bookDAO.addBook(book);
        Assertions.assertEquals(DbTestSources.getBookByIdNumberFive(), bookDAO.getBook(5));
    }



    @Test
    void updateBook_ReturnUpdatedBook() throws SQLException {
        Book book =  new Book("title555", "publisher555", 123, "ISBN-13:978-3-16-148410-0", "description555",
                LocalDate.of(2020, 12, 12), "author555", "genre555", 10, 1);

        bookDAO.updateBook(book);
        Assertions.assertEquals(DbTestSources.getUpdatedBook(), bookDAO.getBook(1));
    }



    @Test
    void removeBook_ReturnListWithoutRemovedBook() throws SQLException {
        bookDAO.removeBook(2);
        Assertions.assertEquals(DbTestSources.getListOfAllBooksWithoutRemovedBook(), bookDAO.getListOfBooks());
    }



    @Test
    void getBookId_ReturnIdInAccordanceWithIsbn() throws SQLException {
        Book book = new Book("title4", "publisher4", 123, "ISBN-13:678-3-16-148410-0", "description4",
                LocalDate.of(2020, 12, 12), "author4", "genre4", 10);
        Assertions.assertEquals(4, bookDAO.getBookId(book));
    }



    @Test
    void getBookId_IfInvalidIsbn_ThrowsSqlException() {
        Book book = new Book("title4", "publisher4", 123, "!!!!!!!!!!!", "description4",
                LocalDate.of(2020, 12, 12), "author4", "genre4", 10);
        Assertions.assertThrows(SQLException.class, () -> bookDAO.getBookId(book));
    }


    @Test
    void getListOfBorrows_ByExistingBookId_ReturnsListOfBorrowsOfThisBook() throws SQLException {
        Assertions.assertEquals(DbTestSources.getBorrowListBookIdTwo(), borrowDAO.getListOfBorrows(2));
    }


    @Test
    void addBorrow_ReturnsListWithNewBorrow() throws SQLException {
//        Borrow borrow = new Borrow();
//        borrow.setBookId(2);
//        borrow.setUserName("user_name4");
//        borrow.setUserEmail("mail4@tut.by");
//        borrow.setBorrowDate(LocalDate.of(2021, 9, 9));
//        borrow.setTimePeriod(1);
//        borrow.setDueDate(LocalDate.of(2021, 10, 9));
//        borrow.setComment("comment5");

        borrowDAO.addBorrow(2, "user_name4", "mail4@tut.by",
                Utils.convertToSqlDateFromLocalDate(LocalDate.of(2021, 9, 9)), 1,
                "comment5");

        Assertions.assertEquals(DbTestSources.getBorrowListWithAddedBorrow(), borrowDAO.getListOfBorrows(2));
    }

}