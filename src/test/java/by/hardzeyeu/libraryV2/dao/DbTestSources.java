package by.hardzeyeu.libraryV2.dao;

import by.hardzeyeu.libraryV2.models.Book;
import by.hardzeyeu.libraryV2.models.Borrow;

import java.time.LocalDate;
import java.util.List;

public class DbTestSources {
    public static String populateBooks = "INSERT INTO books (book_id, title, publisher, publ_date, page_count, isbn, des, authors, genres, amount)" +
            "VALUES (1, 'title1', 'publisher1', '2020-12-12', 123, 'ISBN-13:978-3-16-148410-0', 'description1', 'author1', 'genre1', 10)," +
            "(2, 'title2', 'publisher2', '2020-12-13', 124, 'ISBN-13:878-3-16-148410-0', 'description2', 'author2', 'genre2', 11)," +
            "(3, 'title3', 'publisher3', '2020-12-14', 125, 'ISBN-13:778-3-16-148410-0', 'description3', 'author3', 'genre3', 12)," +
            "(4, 'title4', 'publisher4', '2020-12-15', 126, 'ISBN-13:678-3-16-148410-0', 'description4', 'author4', 'genre4', 13);";


    public static String populateBorrows = "INSERT INTO borrows (book_id, user_name, user_email, borrow_date, time_period, status, comment, borrow_id, return_date, due_date)" +
            "VALUES (1, 'user_name1', 'mail1@tut.by', '2021-06-06', 2, 'returned', 'comment1', 1, '2021-07-06', '2021-08-06')," +
            "(1, 'user_name1', 'mail1@tut.by', '2021-05-05', 3, 'returned', 'comment2', 2, '2021-08-05', '2021-08-05')," +
            "(1, 'user_name2', 'mail2@tut.by', '2021-04-04', 6, 'returned', 'comment3', 3, '2021-09-04', '2021-10-04')," +
            "(1, 'user_name3', 'mail3@tut.by', '2021-03-03', 2, 'returned', 'comment4', 4, '2021-04-03', '2021-05-03')," +
            "(2, 'user_name1', 'mail1@tut.by', '2021-06-06', 2, 'returned', 'comment1', 5, '2021-07-06', '2021-08-06')," +
            "(2, 'user_name1', 'mail1@tut.by', '2021-05-05', 3, 'returned', 'comment2', 6, '2021-08-05', '2021-08-05')," +
            "(2, 'user_name2', 'mail2@tut.by', '2021-04-04', 6, 'returned', 'comment3', 7, '2021-09-04', '2021-10-04')," +
            "(2, 'user_name3', 'mail3@tut.by', '2021-03-03', 2, 'returned', 'comment4', 8, '2021-04-03', '2021-05-03')," +
            "(3,'user_name1', 'mail1@tut.by', '2021-06-06', 2, 'returned', 'comment1', 9, '2021-07-06', '2021-08-06')," +
            "(3, 'user_name1', 'mail1@tut.by', '2021-05-05', 3, 'returned', 'comment2', 10, '2021-08-05', '2021-08-05')," +
            "(3, 'user_name2', 'mail2@tut.by', '2021-04-04', 6, 'returned', 'comment3', 11, '2021-09-04', '2021-10-04')," +
            "(3, 'user_name3', 'mail3@tut.by', '2021-03-03', 2, 'returned', 'comment4', 12, '2021-04-03', '2021-05-03')," +
            "(4, 'user_name1', 'mail1@tut.by', '2021-06-06', 2, 'returned', 'comment1', 13, '2021-07-06', '2021-08-06')," +
            "(4, 'user_name1', 'mail1@tut.by', '2021-05-05', 3, 'returned', 'comment2', 14, '2021-08-05', '2021-08-05')," +
            "(4, 'user_name2', 'mail2@tut.by', '2021-04-04', 6, 'returned', 'comment3', 15, '2021-09-04', '2021-10-04')," +
            "(4, 'user_name3', 'mail3@tut.by', '2021-03-03', 2, 'returned', 'comment4', 16, '2021-04-03', '2021-05-03');";

    public static Book getBookByIdNumberOne() {
        return new Book("title1", "publisher1", 123, "ISBN-13:978-3-16-148410-0", "description1",
                LocalDate.of(2020, 12, 12), "author1", "genre1", 10, 1);
    }

    public static List<Book> getListOfAllBooks() {
        return List.of(new Book("title1", "publisher1", 123, "ISBN-13:978-3-16-148410-0", "description1",
                        LocalDate.of(2020, 12, 12), "author1", "genre1", 10, 1),

                new Book("title2", "publisher2", 124, "ISBN-13:878-3-16-148410-0", "description2",
                        LocalDate.of(2020, 12, 13), "author2", "genre2", 11, 2),

                new Book("title3", "publisher3", 125, "ISBN-13:778-3-16-148410-0", "description3",
                        LocalDate.of(2020, 12, 14), "author3", "genre3", 12, 3),

                new Book("title4", "publisher4", 126, "ISBN-13:678-3-16-148410-0", "description4",
                        LocalDate.of(2020, 12, 15), "author4", "genre4", 13, 4));
    }

    public static List<Book> getListOfSearchedBooks() {
        return List.of(new Book("title2", "publisher2", 124, "ISBN-13:878-3-16-148410-0", "description2",
                LocalDate.of(2020, 12, 13), "author2", "genre2", 11, 2));
    }


    public static Book getBookByIdNumberFive() {
        return new Book("title5", "publisher5", 123, "ISBN-13:978-3-16-148410-0", "description5",
                LocalDate.of(2020, 12, 12), "author5", "genre5", 10, 5);
    }


    public static Book getUpdatedBook() {
        return new Book("title555", "publisher555", 123, "ISBN-13:978-3-16-148410-0", "description555",
                LocalDate.of(2020, 12, 12), "author555", "genre555", 10, 1);
    }


    public static List<Book> getListOfAllBooksWithoutRemovedBook() {
        return List.of(new Book("title1", "publisher1", 123, "ISBN-13:978-3-16-148410-0", "description1",
                        LocalDate.of(2020, 12, 12), "author1", "genre1", 10, 1),

//                new Book("title2", "publisher2", 124, "ISBN-13:878-3-16-148410-0", "description2",
//                        LocalDate.of(2020, 12, 13), "author2", "genre2", 11, 2),

                new Book("title3", "publisher3", 125, "ISBN-13:778-3-16-148410-0", "description3",
                        LocalDate.of(2020, 12, 14), "author3", "genre3", 12, 3),

                new Book("title4", "publisher4", 126, "ISBN-13:678-3-16-148410-0", "description4",
                        LocalDate.of(2020, 12, 15), "author4", "genre4", 13, 4));
    }

    public static List<Borrow> getBorrowListBookIdTwo() {
        return List.of(new Borrow(2, "user_name1", "mail1@tut.by",
                        LocalDate.of(2021, 6, 6), 2, "comment1",
                        5, LocalDate.of(2021, 7, 6)),

                new Borrow(2, "user_name1", "mail1@tut.by",
                        LocalDate.of(2021, 5, 5), 3, "comment2",
                        6, LocalDate.of(2021, 8, 5)),

                new Borrow(2, "user_name2", "mail2@tut.by",
                        LocalDate.of(2021, 4, 4), 6, "comment3",
                        7, LocalDate.of(2021, 9, 4)),

                new Borrow(2, "user_name3", "mail3@tut.by",
                        LocalDate.of(2021, 3, 3), 2, "comment4",
                        8, LocalDate.of(2021, 4, 3)));
    }


    public static List<Borrow> getBorrowListWithAddedBorrow() {
        return List.of(new Borrow(2, "user_name1", "mail1@tut.by",
                        LocalDate.of(2021, 6, 6), 2, "comment1",
                        5, LocalDate.of(2021, 7, 6)),

                new Borrow(2, "user_name1", "mail1@tut.by",
                        LocalDate.of(2021, 5, 5), 3, "comment2",
                        6, LocalDate.of(2021, 8, 5)),

                new Borrow(2, "user_name2", "mail2@tut.by",
                        LocalDate.of(2021, 4, 4), 6, "comment3",
                        7, LocalDate.of(2021, 9, 4)),

                new Borrow(2, "user_name3", "mail3@tut.by",
                        LocalDate.of(2021, 3, 3), 2, "comment4",
                        8, LocalDate.of(2021, 4, 3)),

                new Borrow(2, "user_name4", "mail4@tut.by",
                        LocalDate.of(2021, 9, 9), 1, "comment5",
                        17, null ));
    }
}