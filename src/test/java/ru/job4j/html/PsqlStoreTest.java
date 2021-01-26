package ru.job4j.html;

import org.junit.Test;
import ru.job4j.grabber.ConnectionRollback;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.PsqlStore;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class PsqlStoreTest {
    public Connection init() {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("post.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenCreatePost() {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Post post = new Post("title", "description", "url", Timestamp.valueOf(localDateTime.withNano(0)));
            store.save(post);
            Post result = store.findById(post.getId());
            assertEquals(post.getTitle(), result.getTitle());
            assertEquals(post.getDescription(), result.getDescription());
            assertEquals(post.getUrl(), result.getUrl());
            assertEquals(post.getDate(), result.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAllItemsPost() {
        try (PsqlStore store = new PsqlStore(ConnectionRollback.create(this.init()))) {
            Post post = new Post("title", "description", "url", Timestamp.valueOf(LocalDateTime.now().withNano(0)));
            store.save(post);
            Post result = store.findById(post.getId());
            assertThat(result.getTitle(), is("title"));
            assertThat(result.getDescription(), is("description"));
            assertThat(result.getUrl(), is("url"));
            assertThat(result.getDate(), is(Timestamp.valueOf(LocalDateTime.now().withNano(0))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}