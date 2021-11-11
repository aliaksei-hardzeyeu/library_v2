package by.hardzeyeu.libraryV2.models;

import java.time.*;
import java.util.Objects;

public class Borrow {
    int bookId;
    String userName;
    String userEmail;
    LocalDate borrowDate;
    int timePeriod;
    String status;
    String comment;
    int borrowId;
    LocalDate dueDate;
    LocalDate returnDate;
    LocalDate changedStatusDate;
    String title;
    String notification;

    public Borrow() {
    }

    public Borrow(int bookId, String userName, String userEmail, LocalDate borrowDate, int timePeriod, String status,
                  String comment, int borrowId, LocalDate dueDate, LocalDate returnDate,
                  LocalDate changedStatusDate, String title, String notification) {
        this.bookId = bookId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.borrowDate = borrowDate;
        this.timePeriod = timePeriod;
        this.status = status;
        this.comment = comment;
        this.borrowId = borrowId;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.changedStatusDate = changedStatusDate;
        this.title = title;
        this.notification = notification;
    }

    public Borrow(int bookId, String user_name, String userEmail, LocalDate of,
                  int timePeriod, String status, String comment, int borrowId, LocalDate returnDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.userName = user_name;
        this.userEmail = userEmail;
        this.borrowDate = of;
        this.timePeriod = timePeriod;
        this.status = status;
        this.comment = comment;
        this.borrowId = borrowId;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
    }



    public Borrow(int bookId, String userName, String userEmail, LocalDate borrowDate, int timePeriod, String comment,
                  int borrowId, LocalDate returnDate) {
        this.bookId = bookId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.borrowDate = borrowDate;
        this.timePeriod = timePeriod;
        this.comment = comment;
        this.borrowId = borrowId;
        this.returnDate = returnDate;
    }


    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getChangedStatusDate() {
        return changedStatusDate;
    }

    public void setChangedStatusDate(LocalDate changedStatusDate) {
        this.changedStatusDate = changedStatusDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return bookId == borrow.bookId
                && Objects.equals(userName, borrow.userName)
                && Objects.equals(userEmail, borrow.userEmail)
                && Objects.equals(borrowDate, borrow.borrowDate)
                && timePeriod == borrow.timePeriod
                && Objects.equals(status, borrow.status)
                && Objects.equals(comment, borrow.comment)
                && borrowId == borrow.borrowId
                && Objects.equals(dueDate, borrow.dueDate)
                && Objects.equals(returnDate, borrow.returnDate)
                && Objects.equals(changedStatusDate, borrow.changedStatusDate)
                && Objects.equals(title, borrow.title)
                && Objects.equals(notification, borrow.notification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userName, userName, borrowDate, timePeriod, status, comment, borrowId,
                dueDate, returnDate, changedStatusDate, title, notification);
    }


    @Override
    public String toString() {
        return "Borrow " + "|| bookId=" + bookId +
                "|| userName='" + userName + '\'' +
                "|| userEmail='" + userEmail + '\'' +
                "|| borrowDate=" + borrowDate +
                "|| timePeriod=" + timePeriod +
                "|| status='" + status + '\'' +
                "|| comment='" + comment + '\'' +
                "|| borrowId=" + borrowId +
                "|| dueDate=" + dueDate +
                "|| returnDate=" + returnDate +
                "|| changedStatusDate=" + changedStatusDate +
                "|| title='" + title + '\'' +
                "|| notification='" + notification + '\'' +
                '}';
    }
}