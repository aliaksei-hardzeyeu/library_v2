package by.hardzeyeu.libraryV2.services;

import by.hardzeyeu.libraryV2.dao.BookDAO;
import by.hardzeyeu.libraryV2.models.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
     * and updating existing
     *
     * @param request
     * @return
     */

    public static Book writeParamsIntoBookFromUpdateForm(HttpServletRequest request, Book book) throws IOException, ServletException {

        book.setBookId(Integer.parseInt(request.getParameter("bookId")));
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




        if (request.getPart("file").getSize() > 0) {
            book.setCoverExtension(Utils.getFileExtension(request.getPart("file")));
        } else {
            book.setCoverExtension(getCoverExtensionFromDb(book));
        }


        if (request.getParameter("action").equals("update")) {
            book.setChangeAmount(Integer.parseInt(request.getParameter("changeAmount")));
        }

        return book;
    }

    /**
     * Writes parameters from addBookPage.jsp into Book model for further validation
     * and updating existing
     *
     * @param request
     * @return
     */

    public static Book writeParamsIntoBookFromAddForm(HttpServletRequest request, Book book) throws IOException, ServletException {

        book.setTitle(request.getParameter("title"));
        book.setPublisher(request.getParameter("publisher"));
        book.setPageCount(Integer.parseInt(request.getParameter("pageCount")));
        book.setIsbn(request.getParameter("isbn"));
        book.setDes(request.getParameter("description"));
        book.setPublDate(LocalDate.parse(request.getParameter("publDate")));
        book.setAuthors(request.getParameter("authors"));
        book.setGenres(request.getParameter("genres"));
        book.setGivenAmount(Integer.parseInt(request.getParameter("givenAmount")));


        if (request.getPart("file").getSize() > 0) {
            book.setCoverExtension(Utils.getFileExtension(request.getPart("file")));
        }

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
        book.setCoverExtension(result.getString("cover_ext"));

    }

    public static Book writeParamsIntoBookFromViewForSearch(HttpServletRequest request, Book book) {
        book.setTitle(request.getParameter("title"));
        book.setAuthors(request.getParameter("authors"));
        book.setGenres(request.getParameter("genres"));
        book.setDes(request.getParameter("description"));

        return book;
    }


    /**
     * Gets submitted as part file extension from part header
     *
     * @param part
     * @return String extension -> .jpg
     */

    public static String getFileExtension(final Part part) {
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('.')).trim().replace("\"", "");
                }
            }
        return null;
    }


    public static void saveCoverToDisc(HttpServletRequest request, Book book) throws IOException, ServletException {
        Part filePart = request.getPart("file");

        if (filePart.getSize() > 0) {
            File uploads = new File("E:\\books_covers_server\\", book.getBookId() + book.getCoverExtension());
            Files.copy(filePart.getInputStream(), uploads.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.out.println("No cover");
        }
    }


    /**
     * Gets cover extension from db for particular book
     *
     * @param book
     * @return
     */

    public static String getCoverExtensionFromDb(Book book) {
        BookDAO bookDAO = new BookDAO();
        try {
            return bookDAO.getCoverExtensionFromDb(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Parses raw parameters from search form to fit sql statement
     *
     * @param searchParameters
     * @return
     */

    public static HashMap<String, String> parseParametersToFitSqlStatement(HashMap<String, String> searchParameters) {
        for (Map.Entry<String, String> entry : searchParameters.entrySet()) {
            if (entry.getValue().equals("")) {
                entry.setValue("%");
            } else {
                entry.setValue("%" + entry.getValue() + "%");
            }
        }

        return searchParameters;
    }
}
