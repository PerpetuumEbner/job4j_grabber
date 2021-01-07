package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers/";
        int index = 1;
        int amount = 0;

        while (index < 6) {
            String string = url.replace(url, url + index);
            index++;

            Document doc = Jsoup.connect(string).get();
            Elements row = doc.select(".postslisttopic");
            for (int i = 3; i < row.size(); i++) {
                Element td = row.get(i);
                Element href = td.child(0);
                String date = td.parent().child(5).text();
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(date);
                System.out.println();
                amount++;
            }
        }

        System.out.println("Общее количество вакансий: " + amount);
    }
}