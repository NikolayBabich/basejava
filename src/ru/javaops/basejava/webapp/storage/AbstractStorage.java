package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {
    /**
     * Checks whether Resume with {@code uuid} doesn't exist in this storage
     *
     * @param uuid identifier of Resume to be searched for
     * @return position index to insert Resume if it doesn't exist (for ordered implementations),
     *         or -1 (for non-ordered implementations)
     * @see AbstractStorage#findByUuid(String)
     * @throws ExistStorageException if the Resume already exists in this storage
     */
    protected final int checkNotExist(String uuid) {
        int index = findByUuid(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    /**
     * Checks whether Resume with {@code uuid} already exists in this storage
     *
     * @param uuid identifier of Resume to be searched for
     * @return position index in this storage if the Resume exists (for ordered implementations),
     *         or 0 (for non-ordered implementations)
     * @see AbstractStorage#findByUuid(String)
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    protected final int checkExist(String uuid) {
        int index = findByUuid(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    /**
     * This helper method tries to find the position of Resume with {@code uuid} identifier
     *
     * @param uuid identifier of Resume to be searched for
     * @return for ordered implementations: position index in this storage,
     *         if the Resume is found, otherwise index where it should be inserted;
     *         for non-ordered implementations: 0 if the Resume is found, otherwise -1
     */
    protected abstract int findByUuid(String uuid);
}
