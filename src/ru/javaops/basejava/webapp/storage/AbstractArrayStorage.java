package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT_SIZE = 10_000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT_SIZE];
    protected int size = 0;

    /**
     * Removes all of the Resumes from this storage, all elements
     * of array are set to null
     */
    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @param resume Resume to be saved to this storage
     * @throws ExistStorageException if the Resume already exists in this storage
     * @throws StorageException      if this storage reaches limit size
     */
    @Override
    public final void save(Resume resume) {
        if (size >= STORAGE_LIMIT_SIZE) {
            throw new StorageException("Resume storage limit size has been reached",
                    resume.getUuid());
        }

        insert(checkNotExist(resume.getUuid()), resume);
        size++;
    }

    /**
     * @param index  specify position in array to insert
     * @param resume Resume to be inserted to this storage
     */
    protected abstract void insert(int index, Resume resume);

    /**
     * @param resume Resume to replace one with the same uuid in this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void update(Resume resume) {
        int index = checkExist(resume.getUuid());
        storage[index] = resume;
    }

    /**
     * @param uuid identifier of Resume to be deleted from this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void delete(String uuid) {
        remove(checkExist(uuid));
        storage[size - 1] = null;
        size--;
    }

    /**
     * @param index specify position in array to remove Resume
     */
    protected abstract void remove(int index);

    /**
     * @param uuid identifier of Resume to be returned
     * @return Resume with given uuid
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final Resume get(String uuid) {
        int index = checkExist(uuid);
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in this storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the number of Resumes in this storage
     */
    @Override
    public final int size() {
        return size;
    }
}
