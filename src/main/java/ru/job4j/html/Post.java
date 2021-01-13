package ru.job4j.html;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private String title;
    private String url;
    private String description;
    private LocalDateTime date;

    public Post(String title, String url, LocalDateTime date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public Post(String description, LocalDateTime date) {
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(title, post.title)
                && Objects.equals(url, post.url)
                && Objects.equals(description, post.description)
                && Objects.equals(date, post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, description, date);
    }
}
