package ru.javawebinar.basejava.exception;

import java.sql.SQLException;

public class ExceptionBuilder {


    public static StorageException build(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);
    }
}
