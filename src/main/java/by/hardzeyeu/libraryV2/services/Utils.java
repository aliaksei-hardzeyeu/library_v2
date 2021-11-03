package by.hardzeyeu.libraryV2.services;

import by.hardzeyeu.libraryV2.models.Book;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Utils {
    /**
     * Converts To LocalDate from SqlDate
     *
     * @param dateToConvert
     * @return LocalDate
     */
    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        if (dateToConvert == null) return null;
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }


    /**
     * Converts To Date from LocalDate
     *
     * @param dateToConvert
     * @return
     */

    public static Date convertToSqlDateFromLocalDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }


    /**
     * Writes parameters from addBookPage.jsp into Book model for further validation
     * and adding/updating existing
     *
     * @param request
     * @return
     */

    public static Book writeParamsIntoBookFromView(HttpServletRequest request, Book book) {

        book.setTitle(request.getParameter("title"));
        book.setPublisher(request.getParameter("publisher"));
        book.setPageCount(Integer.parseInt(request.getParameter("pageCount")));
        book.setIsbn(request.getParameter("isbn"));
        book.setDes(request.getParameter("description"));
        book.setPublDate(LocalDate.parse(request.getParameter("publDate")));
        book.setAuthors(request.getParameter("authors"));
        book.setGenres(request.getParameter("genres"));
        book.setGivenAmount(Integer.parseInt(request.getParameter("givenAmount")));

        book.setBorrowed(Integer.parseInt(request.getParameter("borrowed")));
        book.setDamaged(Integer.parseInt(request.getParameter("damaged")));
        book.setLost(Integer.parseInt(request.getParameter("lost")));
        book.setReturned(Integer.parseInt(request.getParameter("returned")));
        book.setCurrentlyAvailableAmount(Integer.parseInt(request.getParameter("currentlyAvailableAmount")));


        if (request.getParameter("bookId") != null) {
            book.setBookId(Integer.parseInt(request.getParameter("bookId")));
        }


        if (request.getParameter("action").equals("update")) {
            book.setChangeAmount(Integer.parseInt(request.getParameter("changeAmount")));
        }

        return book;
    }


    /**
     * Method for writing parameters from DB to book model
     *
     * @param result
     * @param book
     * @throws SQLException
     */
    public static void writeParamsIntoBookFromDb(ResultSet result, Book book) throws SQLException {
        book.setBookId(result.getInt("book_id"));
        book.setTitle(result.getString("title"));
        book.setPublisher(result.getString("publisher"));
        book.setPageCount(result.getInt("page_count"));
        book.setIsbn(result.getString("isbn"));
        book.setDes(result.getString("des"));
        book.setPublDate(Utils.convertToLocalDateViaSqlDate(result.getDate("publ_date")));
        book.setStatus(result.getString("status"));
        book.setAuthors(result.getString("authors"));
        book.setGenres(result.getString("genres"));
        book.setGivenAmount(result.getInt("amount"));
    }


}
