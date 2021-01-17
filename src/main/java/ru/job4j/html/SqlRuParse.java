package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.lineSeparator;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String url) {
        ArrayList<Post> list = new ArrayList<>();
        int index = 1;

        while (index < 6) {
            String string = url.replace(url, url + index);
            index++;
            try {
                Document doc = Jsoup.connect(string).get();
                Elements row = doc.select(".postslisttopic");
                for (int i = 3; i < row.size(); i++) {
                    Element td = row.get(i);
                    Element href = td.child(0);
                    Element elementDate = td.parent().child(5);
                    String link = href.attr("href");
                    String title = href.text();
                    LocalDateTime localDateTime = ParseDateFormat.parseDateTime(elementDate.text());
                    Timestamp timestamp = Timestamp.valueOf(localDateTime);
                    list.add(new Post(title, link, timestamp));
                    System.out.println(String.format("%s %s %s", title + lineSeparator(), link + lineSeparator(), elementDate.text()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Post detail(String url) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(url).get();
            String body = doc.select(".msgBody").get(1).text();
            String date = doc.select(".msgFooter").get(0).text().split("\\[", 2)[0];
            LocalDateTime localDateTime = ParseDateFormat.parseDateTime(date);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            post = new Post(body, timestamp);
            System.out.println(String.format("%s %s", body + lineSeparator(), date));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) throws Exception {
        String urlOffers  = "https://www.sql.ru/forum/job-offers/";
        String urlPost = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";

        new SqlRuParse().list(urlOffers);
        new SqlRuParse().detail(urlPost);
    }
}