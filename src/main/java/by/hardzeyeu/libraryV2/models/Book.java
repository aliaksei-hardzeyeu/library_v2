package by.hardzeyeu.libraryV2.models;

import java.time.LocalDate;

public class Book {
    private int bookId;
    private String title;
    private String authors;
    private String publisher;
    private String genres;
    private int pageCount;
    private String isbn;
    private String des;
    private LocalDate publDate;
    private String status;
    private int givenAmount;

    private int currentlyAvailableAmount;
    private int realAmount;
    private int damaged;
    private int lost;
    private int returned;
    private int borrowed;
    private LocalDate closestAvailableDate;

    public Book() {
    }


    /**
     * Constructor for adding new book and its validation
     * Without id
     *
     * @param title
     * @param authors
     * @param publisher
     * @param genres
     * @param pageCount
     * @param isbn
     * @param des
     * @param publDate
     */

    public Book(String title, String publisher, int pageCount,
                String isbn, String des, LocalDate publDate, String authors, String genres, int amount) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.genres = genres;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.des = des;
        this.publDate = publDate;
        this.givenAmount = amount;
    }


    /**
     * Constructor for adding new book and its validation
     * With id
     *
     * @param title
     * @param authors
     * @param publisher
     * @param genres
     * @param pageCount
     * @param isbn
     * @param des
     * @param publDate
     */

    public Book(String title, String publisher, int pageCount,
                String isbn, String des, LocalDate publDate, String authors, String genres, int amount, int bookId) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.genres = genres;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.des = des;
        this.publDate = publDate;
        this.givenAmount = amount;
        this.bookId = bookId;
    }



    public int getCurrentlyAvailableAmount() {
        return currentlyAvailableAmount;
    }

    public void setCurrentlyAvailableAmount(int currentlyAvailableAmount) {
        this.currentlyAvailableAmount = currentlyAvailableAmount;
    }

    public int getDamaged() {
        return damaged;
    }

    public void setDamaged(int damaged) {
        this.damaged = damaged;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public int getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(int givenAmount) {
        this.givenAmount = givenAmount;
    }

    public int getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(int realAmount) {
        this.realAmount = realAmount;
    }

    public LocalDate getClosestAvailableDate() {
        return closestAvailableDate;
    }

    public void setClosestAvailableDate(LocalDate closestAvailableDate) {
        this.closestAvailableDate = closestAvailableDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public LocalDate getPublDate() {
        return publDate;
    }

    public void setPublDate(LocalDate publDate) {
        this.publDate = publDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "book_id= " + bookId + "| title = " + title +"| authors=" + authors + "| publisher= " + publisher + "| genre= " + genres + "| pageCount= " + pageCount +
                "| isbn= " + isbn + "| description=" + des + "| publDate= " + publDate + "| status= " + status +
                "| amount= " + givenAmount;
    }
}
