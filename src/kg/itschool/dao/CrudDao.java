package kg.itschool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface CrudDao<Model> {

    Model save(Model model);
    Model findById(Long id);

    default Connection getConnection() throws SQLException {

        final String URL = "jdbc:postgresql://localhost:1234/crm";
        final String USERNAME = "postgres";
        final String PASSWORD = "1234";

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    default void close(AutoCloseable closeable) {
        try {
            System.out.println(closeable.getClass().getSimpleName() + " closing...");
            closeable.close();
            System.out.println(closeable.getClass().getSimpleName() + " closed.");
        } catch (Exception e) {
            System.out.println("Could not close " + closeable.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

}

