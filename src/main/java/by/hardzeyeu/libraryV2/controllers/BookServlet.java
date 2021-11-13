package by.hardzeyeu.libraryV2.controllers;

import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.utils.Utils;
import by.hardzeyeu.libraryV2.services.impl.BookServicesImpl;
import by.hardzeyeu.libraryV2.services.impl.BorrowServicesImpl;
import by.hardzeyeu.libraryV2.validators.BookValidator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
@WebServlet(name = "BookServlet", value = "/")
public class BookServlet extends HttpServlet {
    Logger logger;
    private BookService bookServicesImpl;
    private BorrowService borrowServiceImpl;


    @Override
    public void init() {
        bookServicesImpl = BookServicesImpl.getInstance();
        borrowServiceImpl = BorrowServicesImpl.getInstance();
        BasicConfigurator.configure();
        logger = Logger.getLogger(BookServlet.class);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "mainPage" : action) {

            case "update":
                updateBook(request, response);
                break;

            case "remove":
                removeBook(request, response);
                break;

            case "add":
                addBook(request, response);
                break;

            case "search":
                searchBook(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "mainPage" : action) {

            case "mainPage":
                viewMainPage(request, response);
                break;

            case "viewExisting":
                viewBook(request, response);
                break;

            case "addNew":
                addNewBook(request, response);
                break;
        }
    }

    void viewMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Book> listOfBooks = bookServicesImpl.getListOfBooks();
        request.setAttribute("listOfBooks", listOfBooks);

        request.getRequestDispatcher("WEB-INF/views/mainPage.jsp").forward(request, response);
    }

    void addNewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("WEB-INF/views/addBookPage.jsp").forward(request, response);
    }

    void viewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        logger.info("viewing book");

        Book book = bookServicesImpl.getBook(bookId);

        List<Borrow> listOfBorrows = borrowServiceImpl.getListOfBorrows(bookId);

        request.setAttribute("book", book);
        request.setAttribute("listOfBorrows", listOfBorrows);

        request.getRequestDispatcher("WEB-INF/views/bookPage.jsp").forward(request, response);
    }


    void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = Utils.writeParamsIntoBookFromUpdateForm(request, new Book());
        BookValidator bookValidator = new BookValidator(request, book);

        if (bookValidator.validateUpdateForm()) {
            logger.info("updating book");

            bookServicesImpl.updateBook(book);
            Utils.saveCoverToDisc(request, book);

            logger.info("updated successfully");
            viewMainPage(request, response);

        } else {
            logger.info("cannot update due to invalid params");
            viewBook(request, response);
        }
    }

    void removeBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        bookServicesImpl.removeBook(Integer.parseInt(request.getParameter("bookId")));
        logger.info("removed successfully");

        viewMainPage(request, response);
    }

    void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Book book = Utils.writeParamsIntoBookFromAddForm(request, new Book());
        logger.info("going to add new book");

        BookValidator bookValidator = new BookValidator(request, book);

        if (bookValidator.validateAddForm()) {
            bookServicesImpl.addBook(book);
            book.setBookId(bookServicesImpl.getBookId(book));
            Utils.saveCoverToDisc(request, book);

            request.setAttribute("action", null);
            logger.info("added new book successfully");

            viewMainPage(request, response);

        } else {
            logger.info("cannot add new book - invalid params");

            request.getRequestDispatcher("WEB-INF/views/addBookPage.jsp").forward(request, response);
        }
    }

    void searchBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("starting search");

        Book book = Utils.writeParamsIntoBookFromViewForSearch(request, new Book());

        List<Book> listOfBooks = bookServicesImpl.getListOfBooks(book);

        request.setAttribute("listOfBooks", listOfBooks);
        request.setAttribute("searchExecuted", true);
        logger.info("search successful");

        request.getRequestDispatcher("WEB-INF/views/mainPage.jsp").forward(request, response);
    }
}