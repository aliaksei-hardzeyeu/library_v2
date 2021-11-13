package by.hardzeyeu.libraryV2;

import by.hardzeyeu.libraryV2.utils.DbCreator;

import java.io.IOException;
import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws IOException, SQLException {
        DbCreator creator = new DbCreator();
        creator.createDbIfNotExists();

    }
}