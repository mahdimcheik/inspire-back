package com.poec.projet_backend.domains.reservation;

import java.time.*;
import java.util.Date;

public class WeekUtil {

    public static LocalDate getStartOfWeek(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static LocalDate getEndOfWeek(LocalDate date) {
        return date.with(DayOfWeek.SUNDAY);
    }

    public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay(); // This combines the date with midnight (00:00)
    }

    public static LocalDateTime convertLocalDateToLocalDateTimeWithSpecificTime(LocalDate localDate, LocalTime localTime) {
        return localDate.atTime(localTime); // This combines the date with a specific time
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
