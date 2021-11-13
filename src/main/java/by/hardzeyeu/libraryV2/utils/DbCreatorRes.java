package by.hardzeyeu.libraryV2.utils;

public class DbCreatorRes {
    static String createDb = "CREATE DATABASE IF NOT EXISTS hardzeyeu;";

    static String createTableBooks = "CREATE TABLE IF NOT EXISTS hardzeyeu.books\n" +
            "(\n" +
            "    book_id    INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
            "    title      VARCHAR(100) NOT NULL,\n" +
            "    publisher  VARCHAR(100) NOT NULL,\n" +
            "    publ_date  DATE         NOT NULL,\n" +
            "    page_count INTEGER      NOT NULL,\n" +
            "    isbn       VARCHAR(100) NOT NULL,\n" +
            "    des        VARCHAR(300) NULL,\n" +
            "    status     VARCHAR(100) NULL,\n" +
            "    authors    VARCHAR(100) NOT NULL,\n" +
            "    genres     VARCHAR(100) NOT NULL,\n" +
            "    amount     INTEGER      NOT NULL,\n" +
            "    cover_ext  VARCHAR(45)  NULL\n" +
            ") ENGINE = InnoDB; ";

    static String createTableBorrows = "CREATE TABLE IF NOT EXISTS hardzeyeu.borrows\n" +
            "(\n" +
            "    book_id     INTEGER      NOT NULL,\n" +
            "    user_name   VARCHAR(45)  NOT NULL,\n" +
            "    user_email  VARCHAR(45)  NOT NULL,\n" +
            "    borrow_date DATE         NOT NULL,\n" +
            "    time_period INTEGER      NOT NULL,\n" +
            "    status      VARCHAR(45)  NULL,\n" +
            "    comment     VARCHAR(200) NULL,\n" +
            "    borrow_id   INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
            "    return_date DATE         NULL,\n" +
            "    due_date    DATE         NULL,\n" +
            "    CONSTRAINT fk_books FOREIGN KEY (book_id) REFERENCES hardzeyeu.books (book_id)\n" +
            "        ON DELETE CASCADE ON UPDATE CASCADE\n" +
            ") ENGINE = InnoDB;";
}
