package nl.hhs.apep2122group1;

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import nl.hhs.apep2122group1.utils.Converter;

public class ConverterTests {

    @Test
    public void timeStampToReadableString_NL_returns_correct_string() {
        // ARRANGE:
        LocalDateTime timeStamp = LocalDateTime.of(2022, 6, 14, 14, 10);
        Locale.setDefault(new Locale("nl"));

        // ACT:
        String expected = "14 jun. 2022 14:10";
        String result = Converter.timeStampToReadableString(timeStamp);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void timeStampToReadableString_EN_returns_correct_string() {
        // ARRANGE:
        LocalDateTime timeStamp = LocalDateTime.of(2022, 6, 14, 14, 10);
        Locale.setDefault(new Locale("en"));

        // ACT:
        String expected = "Jun 14, 2022, 2:10 PM";
        String result = Converter.timeStampToReadableString(timeStamp);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void timeStampToReadableString_null_returns_null() {
        // ARRANGE:

        // ACT:

        // ASSERT:
        Assert.assertNull(Converter.timeStampToReadableString(null));
    }

    @Test
    public void timeStampToInputString_returns_correct_string() {
        // ARRANGE:
        LocalDateTime timeStamp = LocalDateTime.of( 2022,6, 14, 14, 10);

        // ACT:
        String expected = "2022-06-14 14:10";
        String result = Converter.timeStampToInputString(timeStamp);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void timeStampToInputString_null_returns_null() {
        // ARRANGE:

        // ACT:

        // ASSERT:
        Assert.assertNull(Converter.timeStampToInputString(null));
    }

    @Test
    public void inputStringToTimeStamp_returns_correct_time() {
        // ARRANGE:
        String input = "2022-06-14 14:10";

        // ACT:
        LocalDateTime expected = LocalDateTime.of( 2022,6, 14, 14, 10);
        LocalDateTime result = Converter.inputStringToTimeStamp(input);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void inputStringToTimeStamp_incorrect_returns_null() {
        // ARRANGE:
        String input = "2022-06-14";

        // ACT:
        LocalDateTime result = Converter.inputStringToTimeStamp(input);

        // ASSERT:
        Assert.assertNull(result);
    }

    @Test
    public void LocalDateTimeFromIso8601String_returns_correct_string() {
        //ARRANGE
        String input = "2022-06-14T14:10";

        // ACT:
        LocalDateTime expected = LocalDateTime.of(2022, 6, 14, 14, 10);
        LocalDateTime result = Converter.LocalDateTimeFromIso8601String(input);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void LocalDateTimeFromIso8601String_incorrect_format_throws_exception() {
        //ARRANGE
        String input = "12345";

        // ACT:

        // ASSERT:
        assertThrows(DateTimeParseException.class, () -> Converter.LocalDateTimeFromIso8601String(input));
    }

    @Test
    public void LocalDateTimeFromIso8601String_null_returns_null() {
        // ARRANGE:

        // ACT:

        // ASSERT:
        Assert.assertNull(Converter.LocalDateTimeFromIso8601String(null));
       }

    @Test
    public void iso8601StringFromLocalDateTime_returns_correct_string() {
        //ARRANGE
        LocalDateTime timeStamp = LocalDateTime.of(2022, 6, 14, 14, 10);

        // ACT:
        String expected = "2022-06-14T14:10";
        String result = Converter.iso8601StringFromLocalDateTime(timeStamp);

        // ASSERT:
        Assert.assertEquals(expected, result);
    }

    @Test
    public void iso8601StringFromLocalDateTime_null_returns_null() {
        // ARRANGE:

        // ACT:

        // ASSERT:
        Assert.assertNull(Converter.iso8601StringFromLocalDateTime(null));
    }
}
