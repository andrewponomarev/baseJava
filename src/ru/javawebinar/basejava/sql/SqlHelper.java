package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExceptionBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, SqlExecuteStatement<T> executeStatement) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executeStatement.execute(ps);
        } catch (SQLException e) {
            throw ExceptionBuilder.build(e);
        }
    }

    @FunctionalInterface
    public interface SqlExecuteStatement<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
