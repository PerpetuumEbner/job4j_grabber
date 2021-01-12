package ru.job4j.html;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class ParseDateFormatTest {
    @Test
    public void whenParseDateTimeNow() {
        assertThat(ParseDateFormat.parseDateTime("сегодня, 00:33"),
                is(LocalDate.now().atTime(00, 33)));
    }

    @Test
    public void whenParseDateTimeYesterday() {
        assertThat(ParseDateFormat.parseDateTime("вчера, 20:31"),
                is(LocalDate.now().minus(Period.ofDays(1)).atTime(20, 31)));
    }

    @Test
    public void whenParseDateTimeEarlier() {
        assertThat(ParseDateFormat.parseDateTime("10 янв 21, 21:45"),
                is(LocalDate.of(2021, 01, 10).atTime(21, 45)));
    }
}
