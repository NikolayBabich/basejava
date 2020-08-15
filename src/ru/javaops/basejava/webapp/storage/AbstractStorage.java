package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    /**
     * @param resume Resume to be saved to this storage
     * @throws ExistStorageException if the Resume already exists in this storage
     * @throws StorageException if this storage reaches limit size (for limited implementations)
     */
    @Override
    public final void save(Resume resume) {
        int index = checkNotExist(resume.getUuid());
        saveImpl(index, resume);
    }

    protected abstract void saveImpl(int index, Resume resume);

    /**
     * @param resume Resume to replace one with the same uuid in this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void update(Resume resume) {
        int index = checkExist(resume.getUuid());
        updateImpl(index, resume);
    }

    protected abstract void updateImpl(int index, Resume resume);

    /**
     * @param uuid identifier of Resume to be deleted from this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void delete(String uuid) {
        deleteImpl(checkExist(uuid), uuid);
    }

    protected abstract void deleteImpl(int index, String uuid);

    /**
     * @param uuid identifier of Resume to be returned
     * @return Resume with {@code uuid} identifier
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final Resume get(String uuid) {
        return getImpl(checkExist(uuid), uuid);
    }

    protected abstract Resume getImpl(int index, String uuid);

    /**
     * Checks whether Resume with {@code uuid} doesn't exist in this storage
     *
     * @param uuid identifier of Resume to be searched for
     * @return position index to insert Resume if it doesn't exist in this
     *         storage (for ordered implementations), or -1 (for non-ordered implementations)
     * @see AbstractStorage#searchByUuid(String)
     * @throws ExistStorageException if the Resume already exists in this storage
     */
    private int checkNotExist(String uuid) {
        int index = searchByUuid(uuid);
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
     * @see AbstractStorage#searchByUuid(String)
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    private int checkExist(String uuid) {
        int index = searchByUuid(uuid);
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
    protected abstract int searchByUuid(String uuid);
}
