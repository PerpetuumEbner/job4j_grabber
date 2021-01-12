package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;

public class PostLoading {
    public static void main(String[] args) throws IOException, ParseException {

        Document doc = Jsoup.connect(
                "https://www.sql.ru/forum/1325330/"
                        + "lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").get();

        System.out.println(parseDesc(doc));
        System.out.println(ParseDateFormat.parseDateTime(parseDate(doc)));
    }

    public static String parseDesc(Document doc) {
        return doc.select(".msgBody").get(1).text();
    }

    public static String parseDate(Document doc) {
        return doc.select(".msgFooter").get(0).text().split("\\[", 2)[0];
    }
}