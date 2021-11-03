package by.hardzeyeu.libraryV2.controllers;

import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.Utils;
import by.hardzeyeu.libraryV2.services.impl.BookServicesImpl;
import by.hardzeyeu.libraryV2.services.impl.BorrowServicesImpl;
import by.hardzeyeu.libraryV2.validators.BookValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/")
public class BookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("doPOST ACTION= " + action);

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
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("doGET ACTION= " + action);

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
        System.out.println("start of viewMainPage method");
        BookService bookServicesImpl = BookServicesImpl.getInstance();
        List<Book> listOfBooks = bookServicesImpl.getListOfBooks();
        request.setAttribute("listOfBooks", listOfBooks);
        System.out.println("end of viewMainPage method");

        System.out.println("end-end of viewMainPage method");
        request.getRequestDispatcher("WEB-INF/views/mainPage.jsp").forward(request, response);
    }

    void addNewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START OF addNew METHOD");
        request.getRequestDispatcher("WEB-INF/views/addBookPage.jsp").forward(request, response);
    }

    void viewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START OF view METHOD");

        int bookId = Integer.parseInt(request.getParameter("bookId"));

        BookService bookServicesImpl = BookServicesImpl.getInstance();
        BorrowService borrowServiceImpl = BorrowServicesImpl.getInstance();
        System.out.println("2 OF view METHOD");


        Book book = bookServicesImpl.getBook(bookId);

        System.out.println("3 OF view METHOD");

        List<Borrow> listOfBorrows = borrowServiceImpl.getListOfBorrows(bookId);

        request.setAttribute("book", book);
        request.setAttribute("listOfBorrows", listOfBorrows);
        System.out.println("end OF view METHOD");

        request.getRequestDispatcher("WEB-INF/views/bookPage.jsp").forward(request, response);
    }



    void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START OF UPDATE METHOD");
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();


        Book book = Utils.writeParamsIntoBookFromView(request, new Book());
        BookValidator bookValidator = new BookValidator(request, response, book);

        if (bookValidator.validateAddForm()) {
            bookServicesImpl.updateBook(book);
            System.out.println("END OF UPDATE METHOD");
            viewMainPage(request, response);

        } else {

            request.getRequestDispatcher("WEB-INF/views/bookPage.jsp").forward(request, response);
        }





    }

    void removeBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();

        bookServicesImpl.removeBook(Integer.parseInt(request.getParameter("bookId")));
        viewMainPage(request, response);
    }

    void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();
        System.out.println("START OF ADD METHOD");

        Book book = Utils.writeParamsIntoBookFromView(request, new Book());
        BookValidator bookValidator = new BookValidator(request, response, book);

        if (bookValidator.validateAddForm()) {
            bookServicesImpl.addBook(book);

            request.setAttribute("action", null);
            viewMainPage(request, response);

        } else {

            request.getRequestDispatcher("WEB-INF/views/addBookPage.jsp").forward(request, response);
        }
    }


}