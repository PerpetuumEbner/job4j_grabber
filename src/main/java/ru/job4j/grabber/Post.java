package ru.job4j.grabber;

import java.sql.Timestamp;
import java.util.Objects;

public class Post  {
    private String title;
    private String url;
    private String description;
    private Timestamp date;
    private String id;

    public Post(String title, String description, String url, Timestamp date) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.date = date;
    }

    public Post(String title, String url, Timestamp date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public Post(String description, Timestamp date) {
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
