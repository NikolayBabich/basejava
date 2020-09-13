package ru.javaops.basejava.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;

import java.sql.SQLException;

final class ExceptionUtil {
    private static final String DUPLICATE_KEY_ERROR = "23505";

    private ExceptionUtil() {
    }

    static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (DUPLICATE_KEY_ERROR.equals(e.getSQLState())) {
                return new ExistStorageException("");
            }
        }
        return new StorageException(e);
    }
}
