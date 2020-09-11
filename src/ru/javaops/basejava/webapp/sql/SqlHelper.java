package ru.javaops.basejava.webapp.sql;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SqlHelper {
    private static final String DUPLICATE_KEY_ERROR = "23505";

    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String query, QueryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (DUPLICATE_KEY_ERROR.equals(e.getSQLState())) {
                throw new ExistStorageException("");
            } else throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface QueryExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
