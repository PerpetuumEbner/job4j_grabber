package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private static int interval;

    private static Connection connection() {
        Properties properties = new Properties();
        Connection connection = null;
        try (FileInputStream fis = new FileInputStream("./src/main/resources/rabbit.properties")) {
            properties.load(fis);
            interval = Integer.parseInt(properties.getProperty("rabbit.interval"));
            try {
                Class.forName(properties.getProperty("driver"));
                connection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password"));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        try (Connection connection = connection()) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(connection);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            System.out.println(connection);
        }
    }
}