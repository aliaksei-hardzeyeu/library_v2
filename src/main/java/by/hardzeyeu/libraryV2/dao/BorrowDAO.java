package by.hardzeyeu.libraryV2.dao;

import by.hardzeyeu.libraryV2.connection.C3P0DataSource;
import by.hardzeyeu.libraryV2.dto.BookBorrowsInfo;
import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.Utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.hardzeyeu.libraryV2.services.Utils.convertToLocalDateViaSqlDate;
import static by.hardzeyeu.libraryV2.services.Utils.convertToSqlDateFromLocalDate;

public class BorrowDAO {


    public List<Borrow> getListOfBorrows(int bookId) {
        List<Borrow> listOfBorrows = new ArrayList<>();
        String query = "SELECT * FROM borrows WHERE book_id = ?";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookId);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Borrow borrow = new Borrow();

                borrow.setBookId(result.getInt("book_id"));
                borrow.setUserName(result.getString("user_name"));
                borrow.setUserEmail(result.getString("user_email"));
                borrow.setBorrowDate(convertToLocalDateViaSqlDate(result.getDate("borrow_date")));
                borrow.setTimePeriod(result.getInt("time_period"));
                borrow.setComment(result.getString("comment"));
                borrow.setBorrowId(result.getInt("borrow_id"));
                borrow.setReturnDate(convertToLocalDateViaSqlDate(result.getDate("return_date")));


                listOfBorrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfBorrows;
    }


    private void writeParamsToBorrow(ResultSet result, Borrow borrow) throws SQLException {
        borrow.setBookId(result.getInt("book_id"));
        borrow.setUserName(result.getString("user_name"));
        borrow.setUserEmail(result.getString("user_email"));
        borrow.setBorrowDate(convertToLocalDateViaSqlDate(result.getDate("date")));
        borrow.setStatus(result.getString("status"));
        borrow.setBorrowId(result.getInt("borrow_id"));
    }


    public void addBorrow(int bookId, String userName, String userEmail, Date borrowDate, int timePeriod, String comment) {

        String query1 = "INSERT INTO borrows (book_id, user_name, user_email, borrow_date, time_period," +
                " comment, due_date) VALUES (?, ?, ?, ?, ?, ?, (date_add(borrow_date, interval time_period month)))";

        String query2 = "INSERT INTO library_v2.borrows (due_date) VALUES (date_add(borrow_date, INTERVAL time_period MONTH )) " +
                        "WHERE return_date IS NULL ";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);

            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, userEmail);
            preparedStatement.setDate(4, borrowDate);
            preparedStatement.setInt(5, timePeriod);
            preparedStatement.setString(6, comment);

            preparedStatement.execute();

            Statement statement = connection.createStatement();
            statement.executeUpdate(query2);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Changes borrow status and sets return date.
     *
     * @param status
     * @param borrowId
     */

    public void changeBorrowStatusSetReturnDate(String status, int borrowId) {

        String query = "UPDATE borrows SET status = ?, return_date = ? WHERE borrow_id = ?";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, convertToSqlDateFromLocalDate(LocalDate.now()));
            preparedStatement.setInt(3, borrowId);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves number of damaged, lost, returned, borrowed instances of book. And puts in borrowDates
     *
     * @param book
     * @return
     */

    public BookBorrowsInfo getBookBorrowsInfo(Book book) {

        BookBorrowsInfo bookBorrowsInfo = new BookBorrowsInfo();

        String query1 =
                "SELECT COUNT(IF(status='damaged', 1, null)) 'damaged', " +
                        "COUNT(IF(status ='lost', 1, null)) 'lost', " +
                        "COUNT(IF(status ='returned', 1, null)) 'returned', " +
                        "COUNT(IF(status is NULL, 1, null)) 'borrowed' FROM borrows WHERE book_id = ?;";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, book.getBookId());

            ResultSet result1 = preparedStatement.executeQuery();

            while (result1.next()) {
                bookBorrowsInfo.setDamaged(result1.getInt("damaged"));
                bookBorrowsInfo.setLost(result1.getInt("lost"));
                bookBorrowsInfo.setReturned(result1.getInt("returned"));
                bookBorrowsInfo.setBorrowed(result1.getInt("borrowed"));
            }

            List<LocalDate> dueDates = new ArrayList<>();

            String query2 = "SELECT date_add(borrow_date, INTERVAL time_period MONTH) AS due_date " +
                    "FROM borrows WHERE status IS NULL AND book_id = ?";

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, book.getBookId());

            ResultSet result2 = preparedStatement.executeQuery();

            while (result2.next()) {
                dueDates.add(Utils.convertToLocalDateViaSqlDate(result2.getDate("due_date")));
            }

            bookBorrowsInfo.setDueDatesWithoutStatus(dueDates);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookBorrowsInfo;
    }


    /**
     * Gets list of borrows to return in week
     *
     * @return
     */

    public List<Borrow> getBorrowsToReturnInWeek() {
        List<Borrow> borrowsToReturnInWeek = new ArrayList<>();

        String query = "SELECT borrows.book_id, books.title, borrows.user_name, borrows.user_email, borrows.due_date " +
                "FROM borrows  JOIN books ON books.book_id = borrows.book_id where borrows.return_date is null " +
                "and date_add(curdate(), INTERVAL 7 day) = due_date";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Borrow borrow = new Borrow();

                borrow.setBookId(result.getInt("book_id"));
                borrow.setTitle(result.getString("title"));
                borrow.setUserName(result.getString("user_name"));
                borrow.setUserEmail(result.getString("user_email"));
                borrow.setDueDate(convertToLocalDateViaSqlDate(result.getDate("due_date")));

                borrowsToReturnInWeek.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            return borrowsToReturnInWeek;
    }


    /**
     * Gets list of borrows to return tomorrow
     *
     * @return
     */

    public List<Borrow> getBorrowsToReturnTomorrow() {
        List<Borrow> borrowsToReturnTomorrow = new ArrayList<>();

        String query = "SELECT borrows.book_id, books.title, borrows.user_name, borrows.user_email, borrows.due_date " +
                "FROM borrows  JOIN books ON books.book_id = borrows.book_id where borrows.return_date is null " +
                "and date_add(curdate(), INTERVAL 1 day) = due_date";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Borrow borrow = new Borrow();

                borrow.setBookId(result.getInt("book_id"));
                borrow.setTitle(result.getString("title"));
                borrow.setUserName(result.getString("user_name"));
                borrow.setUserEmail(result.getString("user_email"));
                borrow.setDueDate(convertToLocalDateViaSqlDate(result.getDate("due_date")));

                borrowsToReturnTomorrow.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowsToReturnTomorrow;
    }


    /**
     * Gets list of expired borrows
     *
     * @return
     */

    public List<Borrow> getBorrowsToReturnYesterday() {
        List<Borrow> borrowsToReturnYesterday = new ArrayList<>();

        String query = "SELECT borrows.book_id, books.title, borrows.user_name, borrows.user_email, borrows.due_date " +
                "FROM borrows  JOIN books ON books.book_id = borrows.book_id where borrows.return_date is null " +
                "and date_sub(curdate(), INTERVAL 1 day) = due_date";

        try (Connection connection = C3P0DataSource.getInstance().getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                Borrow borrow = new Borrow();

                borrow.setBookId(result.getInt("book_id"));
                borrow.setTitle(result.getString("title"));
                borrow.setUserName(result.getString("user_name"));
                borrow.setUserEmail(result.getString("user_email"));
                borrow.setDueDate(convertToLocalDateViaSqlDate(result.getDate("due_date")));

                borrowsToReturnYesterday.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowsToReturnYesterday;
    }
}
