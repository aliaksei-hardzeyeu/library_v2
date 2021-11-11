package by.hardzeyeu.libraryV2.controllers;

import by.hardzeyeu.libraryV2.services.BorrowService;
import by.hardzeyeu.libraryV2.services.impl.BorrowServicesImpl;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "BorrowServlet", value = "/borrow")
public class BorrowServlet extends HttpServlet {
    Logger logger;

    @Override
    public void init() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(BookServlet.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "mainPage" : action) {

            case "addBorrow":
                addBorrow(request, response);
                break;

            case "changeBorrowStatus":
                changeBorrowStatus(request, response);

        }
    }

    void addBorrow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("adding new borrow");

        BorrowService borrowService = BorrowServicesImpl.getInstance();

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");
        int timePeriod = Integer.parseInt(request.getParameter("period"));
        String comment = request.getParameter("comment");

        borrowService.addBorrow(bookId, userName, userEmail, timePeriod, comment);

        logger.info("added new borrow successfully");
        response.sendRedirect(request.getContextPath() + "/?action=viewExisting&bookId=" + bookId);
    }


    void changeBorrowStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("changing borrow status");

        BorrowService borrowService = BorrowServicesImpl.getInstance();

        borrowService.changeBorrowStatusSetReturnDate(request.getParameter("status"), Integer.parseInt(request.getParameter("borrowId")));
        logger.info("changed borrow status");

        response.sendRedirect(request.getContextPath() + "/?action=viewExisting&bookId=" + request.getParameter("bookId"));
    }
}
