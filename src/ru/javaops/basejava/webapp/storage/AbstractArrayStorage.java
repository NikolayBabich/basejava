package ru.javaops.basejava.webapp.storage;

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

    @Override
    protected final void saveImpl(Object index, Resume resume) {
        if (size >= STORAGE_LIMIT_SIZE) {
            throw new StorageException("Resume storage limit size has been reached",
                    resume.getUuid());
        }
        insert((Integer) index, resume);
        size++;
    }

    /**
     * @param index  specify position in array to insert
     * @param resume Resume to be inserted to this storage
     */
    protected abstract void insert(int index, Resume resume);

    @Override
    protected final void updateImpl(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected final void deleteImpl(Object index) {
        remove((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @param index specify position in array to remove Resume
     */
    protected abstract void remove(int index);

    @Override
    protected final Resume getImpl(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected boolean isExists(Object index) {
        return (Integer) index >= 0;
    }
}
