package by.hardzeyeu.libraryV2.validators;

import by.hardzeyeu.libraryV2.models.Book;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidator {
    HttpServletRequest request;
    Book book;


    public BookValidator(HttpServletRequest request, Book book) {
        this.request = request;
        this.book = book;
    }


    public boolean validateAddForm() {
        System.out.println("1");
        int errorCount = 0;
        if (!areAuthorsValid(book)) {
            request.setAttribute("message2", "Error entering authors, must be comma separated unique");
            errorCount++;
        }
        System.out.println("2");

        if (!isPublicationDateValid(book)) {
            request.setAttribute("message4", "Date must not be after current date");
            errorCount++;
        }
        System.out.println("3");

        if (!areGenresValid(book)) {
            request.setAttribute("message5", "Error entering genres, must be comma separated unique");
            errorCount++;
        }
        System.out.println("4");

        if (!isPageCountValid(book)) {
            request.setAttribute("message6", "Must be positive number");
            errorCount++;
        }
        System.out.println("5");

        if (!isIsbnValid(book)) {
            request.setAttribute("message7", "Check wiki for ISBN patterns");
            errorCount++;
        }
        System.out.println("6");


        if (!isGivenAmountValid(book)) {
            request.setAttribute("message8", "Amount must be positive integer");
            errorCount++;

        }

        if (errorCount > 0) {
            request.setAttribute("book", book);
            return false;
        }

        return true;
    }


    public boolean validateUpdateForm() {
        int errorCount = 0;

        if (!areAuthorsValid(book)) {
            request.setAttribute("message2", "Error entering authors, must be comma separated unique");
            errorCount++;
        }
        System.out.println("2");


        if (!isPublicationDateValid(book)) {
            request.setAttribute("message4", "Date must not be after current date");
            errorCount++;
        }
        System.out.println("3");


        if (!areGenresValid(book)) {
            request.setAttribute("message5", "Error entering genres, must be comma separated unique");
            errorCount++;
        }
        System.out.println("4");


        if (!isPageCountValid(book)) {
            request.setAttribute("message6", "Must be positive number");
            errorCount++;
        }
        System.out.println("5");


        if (!isIsbnValid(book)) {
            request.setAttribute("message7", "Check wiki for ISBN patterns");
            errorCount++;
        }
        System.out.println("6");


        if (!isChangeAmountValid(book)) {
            request.setAttribute("message9", "Wrong changeAmount number");
            errorCount++;

        }

        if (!isFileImage(book)) {
            request.setAttribute("message10", "File isn`t image");
            errorCount++;
        }


        if (errorCount > 0) {
            request.setAttribute("book", book);
            return false;
        }

        return true;
    }

    boolean isPageCountValid(Book book) {
        return book.getPageCount() > 0;
    }


    boolean isIsbnValid(Book book) {
        String regex = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(book.getIsbn());

        return matcher.matches();
    }


    boolean isPublicationDateValid(Book book) {
        return !book.getPublDate().isAfter(LocalDate.now());
    }


    boolean areAuthorsValid(Book book) {
        List<String> splittedAuthors = Arrays.asList(book.getAuthors().split("\\s*,\\s*"));

        for (int i = 0; i < splittedAuthors.size(); i++) {
            splittedAuthors.set(i, splittedAuthors.get(i).toLowerCase());
        }

        Set<String> set = new HashSet<>(splittedAuthors);

        return set.size() == splittedAuthors.size();
    }


    boolean areGenresValid(Book book) {
        List<String> splittedGenres = Arrays.asList(book.getGenres().split("\\s*,\\s*"));

        for (int i = 0; i < splittedGenres.size(); i++) {
            splittedGenres.set(i, splittedGenres.get(i).toLowerCase());
        }

        Set<String> set = new HashSet<>(splittedGenres);

        return set.size() == splittedGenres.size();
    }


    boolean isGivenAmountValid(Book book) {
        return book.getGivenAmount() > 0;
    }


    boolean isChangeAmountValid(Book book) {
        if (book.getChangeAmount() < 0) {
            return Math.abs(book.getChangeAmount()) <= book.getCurrentlyAvailableAmount();
        }
        return true;
    }

    public static boolean isFileImage(Book book) {
        if (book.getCoverExtension() != null) return Arrays.asList(".png", ".jpg").contains(book.getCoverExtension());
        return true;
    }
}