package by.hardzeyeu.libraryV2.validators;

import by.hardzeyeu.libraryV2.models.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidator {
    HttpServletRequest request;
    HttpServletResponse response;

    Book book;


    public BookValidator(HttpServletRequest request, HttpServletResponse response, Book book) throws ServletException, IOException {
        this.request = request;
        this.response = response;
        this.book = book;
    }


    public boolean validateAddForm() throws ServletException, IOException {

        System.out.println("1");
        int errorCount = 0;
        if (!areAuthorsValid(book.getAuthors())) {
            request.setAttribute("message2", "Error entering authors, must be comma separated unique");
            errorCount++;
        }
        System.out.println("2");

        if (!isPublicationDateValid(book.getPublDate())) {
            request.setAttribute("message4", "Date must not be after current date");
            errorCount++;
        }
        System.out.println("3");

        if (!areGenresValid(book.getGenres())) {
            request.setAttribute("message5", "Error entering genres, must be comma separated unique");
            errorCount++;
        }
        System.out.println("4");

        if (!isPageCountValid(book.getPageCount())) {
            request.setAttribute("message6", "Must be positive number");
            errorCount++;
        }
        System.out.println("5");

        if (!isIsbnValid(book.getIsbn())) {
            request.setAttribute("message7", "Check wiki for ISBN patterns");
            errorCount++;
        }
        System.out.println("6");

        if (!isAmountValid(book.getGivenAmount())) {
            request.setAttribute("message8", "Amount must be positive integer");
            errorCount++;
        }
        System.out.println(errorCount);
        System.out.println("7");



        if (errorCount > 0) {
            request.setAttribute("bookTemp", book);
        return false;
        }

        return true;
    }






    boolean isPageCountValid(int pageCount) {
        return pageCount > 0 && pageCount % 1 == 0;
    }

    boolean isIsbnValid(String isbn) {
        String regex = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(isbn);

        return matcher.matches();
    }

    boolean isPublicationDateValid(LocalDate publDate) {
        return !publDate.isAfter(LocalDate.now());
    }

    boolean areAuthorsValid(String authors) {
        List<String> splittedAuthors = Arrays.asList(authors.split("\\s*,\\s*"));

        for (int i = 0; i < splittedAuthors.size(); i++) {
            splittedAuthors.set(i, splittedAuthors.get(i).toLowerCase());
        }

        Set<String> set = new HashSet<>(splittedAuthors);

        return set.size() == splittedAuthors.size();
    }


    boolean areGenresValid(String genres) {
        List<String> splittedGenres = Arrays.asList(genres.split("\\s*,\\s*"));

        for (int i = 0; i < splittedGenres.size(); i++) {
            splittedGenres.set(i, splittedGenres.get(i).toLowerCase());
        }

        Set<String> set = new HashSet<>(splittedGenres);

        return set.size() == splittedGenres.size();
    }

    boolean isAmountValid(int amount) {
        return amount > 0;
    }
}
