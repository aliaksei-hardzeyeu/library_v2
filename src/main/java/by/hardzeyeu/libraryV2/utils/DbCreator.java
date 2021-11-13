package by.hardzeyeu.libraryV2.utils;

import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String dbURL = "jdbc:mysql://localhost:3306/hardzeyeu";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    public void createDbIfNotExists() throws IOException, SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(DbCreatorRes.createDb);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(DbCreatorRes.createTableBooks);
            statement.execute();

            statement = connection.prepareStatement(DbCreatorRes.createTableBorrows);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
