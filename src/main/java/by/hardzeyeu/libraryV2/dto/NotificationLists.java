package by.hardzeyeu.libraryV2.dto;

import by.hardzeyeu.libraryV2.models.Borrow;

import java.util.ArrayList;
import java.util.List;

public class NotificationLists {
    List<Borrow> borrowsToReturnInWeek;
    List<Borrow> borrowsToReturnTomorrow;
    List<Borrow> borrowsToReturnYesterday;

    public NotificationLists() {
    }

    public NotificationLists(List<Borrow> borrowsToReturnInWeek, List<Borrow> borrowsToReturnTomorrow, List<Borrow> borrowsToReturnYesterday) {
        this.borrowsToReturnInWeek = borrowsToReturnInWeek;
        this.borrowsToReturnTomorrow = borrowsToReturnTomorrow;
        this.borrowsToReturnYesterday = borrowsToReturnYesterday;
    }

    public List<Borrow> getBorrowsToReturnInWeek() {
        return borrowsToReturnInWeek;
    }

    public void setBorrowsToReturnInWeek(List<Borrow> borrowsToReturnInWeek) {
        this.borrowsToReturnInWeek = borrowsToReturnInWeek;
    }

    public List<Borrow> getBorrowsToReturnTomorrow() {
        return borrowsToReturnTomorrow;
    }

    public void setBorrowsToReturnTomorrow(List<Borrow> borrowsToReturnTomorrow) {
        this.borrowsToReturnTomorrow = borrowsToReturnTomorrow;
    }

    public List<Borrow> getBorrowsToReturnYesterday() {
        return borrowsToReturnYesterday;
    }

    public void setBorrowsToReturnYesterday(List<Borrow> borrowsToReturnYesterday) {
        this.borrowsToReturnYesterday = borrowsToReturnYesterday;
    }
}
