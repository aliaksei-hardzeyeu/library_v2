package by.hardzeyeu.libraryV2.controllers;

import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;
import by.hardzeyeu.libraryV2.services.BookService;
import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.implementations.BookServicesImpl;
import by.hardzeyeu.libraryV2.services.implementations.BorrowServicesImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/")
public class BookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("doGET ACTION= " + action);

        switch (action == null ? "mainPage" : action) {

            case "mainPage":
                viewMainPage(request, response);
                break;

            case "view":
                viewBook(request, response);
                break;

            case "update":
                updateBook(request, response);
                break;

            case "remove":
                removeBook(request, response);
                break;

            case "add":
                addBook(request, response);
                break;

            case "addBorrow":
                addBorrow(request, response);
                break;
        }
    }

    void viewMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("start of viewMainPage method");
        BookService bookServicesImpl = BookServicesImpl.getInstance();
        List<Book> listOfBooks = bookServicesImpl.getListOfBooks();
//        if (listOfBooks == null) {
//            System.out.println("listOfBooks = null");
//        } else {
//            for (Book b : listOfBooks) {
//                System.out.println(b.toString());
//            }
//        }
        request.setAttribute("listOfBooks", listOfBooks);
        System.out.println("end of viewMainPage method");

        System.out.println("end-end of viewMainPage method");
        request.getRequestDispatcher("WEB-INF/views/mainPage.jsp").forward(request, response);
    }

    void viewBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START OF view METHOD");


        String type = request.getParameter("type");

        if (type.equals("new")) {

            request.setAttribute("actionOnPage", "add");
            request.getRequestDispatcher("WEB-INF/views/bookPage.jsp").forward(request, response);

        } else if (type.equals("old")){
            System.out.println("1 OF view METHOD");


            int bookId = Integer.parseInt(request.getParameter("bookId"));

            BookService bookServicesImpl = BookServicesImpl.getInstance();
            BorrowService borrowService = BorrowServicesImpl.getInstance();
            System.out.println("2 OF view METHOD");


            Book book = bookServicesImpl.getBook(bookId);
            System.out.println("3 OF view METHOD");

            List<Borrow> listOfBorrows = borrowService.getListOfBorrows(bookId);

            request.setAttribute("book", book);
            request.setAttribute("listOfBorrows", listOfBorrows);
            request.setAttribute("actionOnPage", "update");
            System.out.println("end OF view METHOD");

            request.getRequestDispatcher("WEB-INF/views/bookPage.jsp").forward(request, response);
        }
    }

    void updateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("START OF UPDATE METHOD");
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();

        String title = request.getParameter("title");
        String publisher = request.getParameter("publisher");
        int pageCount = Integer.parseInt(request.getParameter("pageCount"));
        String isbn = request.getParameter("isbn");
        String des = request.getParameter("description");
        String publDate = request.getParameter("publDate");
        String authors = request.getParameter("authors");
        String genres = request.getParameter("genres");
        int amount = Integer.parseInt(request.getParameter("amount"));
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        System.out.println("MIDDLE OF UPDATE METHOD");

        bookServicesImpl.updateBook(title, publisher, pageCount, isbn, des, publDate, authors, genres, amount, bookId);
        System.out.println("END OF UPDATE METHOD");
        viewMainPage(request, response);
    }

    void removeBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();

        bookServicesImpl.removeBook(Integer.parseInt(request.getParameter("bookId")));
        viewMainPage(request, response);
    }

    void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServicesImpl bookServicesImpl = BookServicesImpl.getInstance();
        System.out.println("START OF ADD METHOD");


        String title = request.getParameter("title");
        String publisher = request.getParameter("publisher");
        int pageCount = Integer.parseInt(request.getParameter("pageCount"));
        String isbn = request.getParameter("isbn");
        String des = request.getParameter("description");
        String publDate = request.getParameter("publDate");
        String authors = request.getParameter("authors");
        String genres = request.getParameter("genres");
        int amount = Integer.parseInt(request.getParameter("amount"));
        System.out.println("middle OF ADD METHOD");

        bookServicesImpl.addBook(title, publisher, pageCount, isbn, des, publDate, authors, genres, amount);
        System.out.println("end OF ADD METHOD");

        request.setAttribute("action", null);
        viewMainPage(request, response);
    }


    void addBorrow(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        BorrowService borrowService = BorrowServicesImpl.getInstance();
        System.out.println("addborrow 1");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        int timePeriod = Integer.parseInt(request.getParameter("period"));
        String comment = request.getParameter("comment");
        System.out.println("addborrow 2");

        borrowService.addBorrow(bookId, userName, userEmail, timePeriod, comment);

        System.out.println("addborrow 3");

        viewBook(request, response);
    }


}