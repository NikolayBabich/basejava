package ru.javaops.basejava.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class SqlHelper {
    private static final String DUPLICATE_KEY_ERROR = "23505";

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, SqlQueryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException && DUPLICATE_KEY_ERROR.equals(e.getSQLState())) {
                throw new ExistStorageException("");
            } else throw new StorageException(e);
        }
    }
}
