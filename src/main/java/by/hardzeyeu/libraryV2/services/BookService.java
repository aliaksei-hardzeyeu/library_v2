package by.hardzeyeu.libraryV2.services;

import by.hardzeyeu.libraryV2.models.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    Book getBook(int bookId);

    List<Book> getListOfBooks();

    void addBook(Book book);

    void updateBook(Book book);

    void removeBook(int book_id);



}
