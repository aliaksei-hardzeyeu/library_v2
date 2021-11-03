package by.hardzeyeu.libraryV2.dao;

import by.hardzeyeu.libraryV2.connection.C3P0DataSource;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.services.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {


    public Book getBook(int bookId) {
        Book book = new Book();
        String query = "SELECT * FROM books WHERE book_id = ?";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);

            ResultSet result = preparedStatement.executeQuery();

            result.next();
            Utils.writeParamsIntoBookFromDb(result, book);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }


    public List<Book> getListOfBooks() {
        List<Book> listOfBooks = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Book book = new Book();

                Utils.writeParamsIntoBookFromDb(result, book);

                listOfBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfBooks;
    }



    public void addBook(Book book) {

        String query = "INSERT INTO books (title, publisher, page_count, isbn, des, publ_date, authors, genres, amount)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setInt(3, book.getPageCount());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setString(5, book.getDes());
            preparedStatement.setDate(6, Utils.convertToSqlDateFromLocalDate(book.getPublDate()));
            preparedStatement.setString(7, book.getAuthors());
            preparedStatement.setString(8, book.getGenres());
            preparedStatement.setInt(9, book.getGivenAmount());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {

        String query = "UPDATE books SET title = ?, publisher = ?, page_count = ?, isbn = ?, des = ?, publ_date = ?, " +
                "authors =?, genres = ?, amount = ? WHERE book_id = ?";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setInt(3, book.getPageCount());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setString(5, book.getDes());
            preparedStatement.setDate(6, Utils.convertToSqlDateFromLocalDate(book.getPublDate()));
            preparedStatement.setString(7, book.getAuthors());
            preparedStatement.setString(8, book.getGenres());
            preparedStatement.setInt(9, book.getGivenAmount() + book.getChangeAmount());
            preparedStatement.setInt(10, book.getBookId());


            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeBook(int book_id) {
        String query = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, book_id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}