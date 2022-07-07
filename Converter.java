package nl.hhs.apep2122group1.utils;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

public class Converter {
    public static String timeStampToReadableString(LocalDateTime timeStamp) {
        if (timeStamp != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
            return formatter.format(timeStamp);
        }
        return null;
    }

    public static String timeStampToInputString(LocalDateTime timeStamp) {
        if (timeStamp != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return formatter.format(timeStamp);
        }
        return null;
    }

    public static LocalDateTime inputStringToTimeStamp(String userInput) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(userInput, formatter);
        } catch (DateTimeParseException parseException) {
            return null;
        }
    }

    @TypeConverter
    public static LocalDateTime LocalDateTimeFromIso8601String(String dateTimeString) {
        if (dateTimeString == null) {
            return null;
        }
        return LocalDateTime.parse(dateTimeString);
    }

    @TypeConverter
    public static String iso8601StringFromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toString();
    }
}