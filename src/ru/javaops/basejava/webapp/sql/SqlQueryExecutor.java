package ru.javaops.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlQueryExecutor<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
