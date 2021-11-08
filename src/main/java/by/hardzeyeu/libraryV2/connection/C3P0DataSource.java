package by.hardzeyeu.libraryV2.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


public class C3P0DataSource implements AutoCloseable{
    private static final String URL = "jdbc:mysql://localhost:3306/library_v2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static C3P0DataSource dataSource;
    private ComboPooledDataSource comboPooledDataSource;

    private C3P0DataSource() {
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(DRIVER);
            comboPooledDataSource.setJdbcUrl(URL);
            comboPooledDataSource.setUser(USERNAME);
            comboPooledDataSource.setPassword(PASSWORD);
        } catch (PropertyVetoException ex1) {
            ex1.printStackTrace();
        }
    }

    public static C3P0DataSource getInstance() {
        if (dataSource == null)
            dataSource = new C3P0DataSource();
        return dataSource;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public void close() throws Exception {
        dataSource.close();
    }
}
