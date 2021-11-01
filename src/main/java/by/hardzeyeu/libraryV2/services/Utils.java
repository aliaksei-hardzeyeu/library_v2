package by.hardzeyeu.libraryV2.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {
    /**
     * Converts To LocalDate from SqlDate
     * @param dateToConvert
     * @return LocalDate
     */
    public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        if (dateToConvert == null) return null;
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }


    /**
     * Converts To Date from LocalDate
     * @param dateToConvert
     * @return
     */

    public static Date convertToSqlDateFromLocalDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}
