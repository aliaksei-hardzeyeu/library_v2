package by.hardzeyeu.libraryV2.listeners;

import by.hardzeyeu.libraryV2.notifications.NotificationTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import by.hardzeyeu.libraryV2.utils.DbCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebListener
public class BackgroundJobManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //create db
        DbCreator creator = new DbCreator();
        try {
            creator.createDbIfNotExists();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new NotificationTask(), 0, 1, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
