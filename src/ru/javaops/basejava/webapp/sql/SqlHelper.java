package ru.javaops.basejava.webapp.sql;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SqlHelper {
    private static final String DUPLICATE_KEY_ERROR = "23505";

    private SqlHelper() {
    }

    public static <T> T execute(ConnectionFactory connectionFactory, String query, QueryExecutor<T> executor) {
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
