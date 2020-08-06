package ru.javaops.basejava.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super(String.format("Resume #%s already exists in storage", uuid), uuid);
    }
}
