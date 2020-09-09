package ru.javaops.basejava.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        this(message, "", e);
    }

    public StorageException(String message) {
        this(message, "");
    }

    public StorageException(Exception e) {
        this(e.getMessage(), e);
    }

    public final String getUuid() {
        return uuid;
    }
}
