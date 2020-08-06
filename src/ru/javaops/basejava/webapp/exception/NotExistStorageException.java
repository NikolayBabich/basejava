package ru.javaops.basejava.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super(String.format("Resume #%s is not found in storage", uuid), uuid);
    }
}
